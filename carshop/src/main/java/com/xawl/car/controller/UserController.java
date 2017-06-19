package com.xawl.car.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.xawl.car.domain.JSON;
import com.xawl.car.domain.RollGrant;
import com.xawl.car.domain.SMS;
import com.xawl.car.domain.User;
import com.xawl.car.interceptor.Role;
import com.xawl.car.service.RollService;
import com.xawl.car.service.UserService;
import com.xawl.car.util.DateUtil;
import com.xawl.car.util.ExecutorUtil;
import com.xawl.car.util.IDUtils;
import com.xawl.car.util.PropertiesUtil;
import com.xawl.car.util.ResourceUtil;
import com.xawl.car.util.RollUtil;
import com.xawl.car.util.SMSUtil;
import com.xawl.car.util.TokenUtil;

@Controller
public class UserController {
	private PropertiesUtil proper = new PropertiesUtil(this.getClass()
			.getClassLoader().getResourceAsStream("config.properties"));// 读取配置文件
	public static String OK = "1";
	public static String STOP = "0";
	String accountSid = proper.getProperty("accountSid");
	String token = proper.getProperty("token");
	String appId = proper.getProperty("appId");
	String templateId = proper.getProperty("templateId");
	@Resource
	private UserService userService;

	@Resource
	private RollService rollService;

	@RequestMapping(value = "/user/login")
	public @ResponseBody
	Object login(@RequestParam() String ulogin,
			@RequestParam() String upassword, JSON json,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map map = new HashMap<String, Object>();
		map.put("ulogin", ulogin);
		map.put("upassword", TokenUtil.MD5(upassword));
		User user = userService.getUser(map);
		if (user != null) {
			// 被封杀
			if (user.getStatus() == 0) {
				json.add("status", 0);
				json.add("msg", "user error");
				return json + "";
			}
			String create = DateUtil.getSqlDate();

			// 更新登陆状态 和 最后登陆时间
			String token = TokenUtil.MD5(ulogin + upassword + create);
			User up = new User();
			up.setToken(token);
			up.setLasttime(create);
			up.setUid(user.getUid());
			userService.update(user);
			request.getSession().setAttribute(ResourceUtil.CURRENT_USER, user);
			// json.add("JSESSIONID", request.getSession().getId());//
			// 防止cookie不能使用，回显
			json.add("user", user);
			return json + "";
		} else {
			// 登陆失败没有相关的账号信息
			json.add("status", 0);
			json.add("msg", "user error");
			return json + "";
		}

	}

	@RequestMapping("/user/registfrist")
	public @ResponseBody
	Object regist(@RequestParam() String ulogin, JSON json,
			HttpServletRequest request) {

		User user = userService.getUserByUlogin(ulogin);
		if (user != null) {
			// 说明注册过了
			json.add("status", 0);
			json.add("msg", "this phone is registed");
			return json + "";
		}

		String para = SMSUtil.getRandNum(6);
		SMSUtil.testTemplateSMS(true, accountSid, token, appId, templateId,
				ulogin, para);
		String encode = UUID.randomUUID().toString();
		SMS sms = new SMS();
		sms.setPhone(ulogin);
		sms.setReturnCode(para);
		sms.setSendTime(DateUtil.getSqlDate());
		sms.setType(1);
		sms.setEncode(encode);
		json.add("ulogin", ulogin);
		json.add("code", para);
		request.getSession().setAttribute("VirCode", sms);
		// json.add("JSESSIONID", request.getSession().getId());//
		// 防止cookie不能使用，回显
		return json.toString();
	}

	@RequestMapping("/user/registlast")
	public @ResponseBody
	Object regist2(@RequestParam() String upassword,
			@RequestParam() String code, JSON json, HttpServletRequest request)
			throws ParseException {
		SMS sms = (SMS) request.getSession().getAttribute("VirCode");
		if (sms == null) {
			System.out.println("-----session问题");
			json.add("status", 0);
			json.add("msg", "time over or error");
			return json + "";
		}
		if (sms.getType() != 1) {
			// 不是注册返回
			json.add("status", 0);
			return json.toString();
		}

		String sendTime = sms.getSendTime();
		// 比较时间
		if (!DateUtil.compTo(sendTime, 10) || !code.equals(sms.getReturnCode())) {
			// 超过10分钟了或者验证码错误
			json.add("status", 0);
			System.out.println("-----验证码问题");
			json.add("msg", "time over or code error");
			return json + "";
		}
		String phone = sms.getPhone();
		final User user = new User();
		user.setUlogin(phone);
		user.setUphone(phone);
		String create = DateUtil.getSqlDate();
		user.setUcreate(create);
		user.setUpassword(TokenUtil.MD5(upassword));
		user.setStatus(User.USER_STATUS_OK);
		// 并使用ulogin+upassword生成一串token。
		String token = TokenUtil.MD5(phone + upassword + create);
		user.setToken(token);
		userService.insertregist(user);
		// 插入完成后获取最新的用户数据

		User userByUlogin = userService.getUserByUlogin(phone);
		request.getSession().removeAttribute("VirCode");
		json.add("user", userByUlogin);
		final int uid = userByUlogin.getUid();
		// 默认是注册完毕后直接登录。
		// json.add("JSESSIONID", request.getSession().getId());//
		// 防止cookie不能使用，回显
		request.getSession().setAttribute(ResourceUtil.CURRENT_USER, user);

		// 在这里启动一个线程去执行注册赠送优惠劵的逻辑

		// 考虑是否使用线程池
		// todo:后期考虑做成动态（优惠劵放发地点全由后台控制)
		ExecutorUtil.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				// 获取注册规则类
				RollGrant rollGrant = rollService
						.getRollGrant(RollGrant.USER_REGIST);
				RollUtil.insert(rollGrant, rollService, uid, user);// 根据规则进行放发优惠劵
			}
		});

		return json + "";
	}

	// 退出登录
	@RequestMapping("/user/demo")
	public @ResponseBody
	Object logout44(JSON json, HttpServletRequest request) {
		ExecutorUtil.getInstance().execute(new Runnable() {
			@Override
			public void run() {
				// 获取注册规则类
				RollGrant rollGrant = rollService
						.getRollGrant(RollGrant.USER_PAY);
				RollUtil.insert(rollGrant, rollService, 11, 300F);// 根据规则进行放发优惠劵
			}
		});
		return json.toString();
	}

	// 退出登录
	@RequestMapping("/user/logout")
	public @ResponseBody
	Object logout2(JSON json, HttpServletRequest request) {
		request.getSession().invalidate();
		return json.toString();
	}

	// 找回密码
	@RequestMapping(value = "/user/findpwd")
	@ResponseBody
	public String findpwd(JSON json, @RequestParam() String ulogin,
			HttpServletRequest request) {
		User user = userService.getUserByUlogin(ulogin);
		if (user == null) {
			json.add("status", 0);// 没有注册
			return json.toString();
		}
		// 找回密码逻辑
		String para = SMSUtil.getRandNum(6);
		SMSUtil.testTemplateSMS(true, accountSid, token, appId, templateId,
				ulogin, para);
		String encode = UUID.randomUUID().toString();
		SMS sms = new SMS();
		sms.setPhone(ulogin);
		sms.setReturnCode(para);
		sms.setSendTime(DateUtil.getSqlDate());
		sms.setType(2);
		sms.setEncode(encode);
		json.add("ulogin", ulogin);
		json.add("code", para);
		request.getSession().setAttribute("VirCode", sms);
		return json + "";
	}

	// 设置密码
	@RequestMapping(value = "/user/setpwd")
	@ResponseBody
	public String findpwd2(JSON json, @RequestParam() String upassword,
			@RequestParam() String code, HttpServletRequest request)
			throws ParseException {

		SMS sms = (SMS) request.getSession().getAttribute("VirCode");
		if (sms == null) {
			json.add("status", 0);
			json.add("msg", "time over or error");
			return json + "";
		}
		if (sms.getType() != 2) {
			// 不是找回中的设置密码
			json.add("status", 0);
			return json.toString();
		}

		String sendTime = sms.getSendTime();
		// 比较时间
		if (!DateUtil.compTo(sendTime, 10) || !code.equals(sms.getReturnCode())) {
			// 超过10分钟了或者验证码错误
			json.add("status", 0);
			System.out.println("-----验证码问题");
			json.add("msg", "time over or code error");
			return json + "";
		}
		String phone = sms.getPhone();
		User user = new User();
		user.setUlogin(phone);
		user.setUpassword(TokenUtil.MD5(upassword));
		userService.updatePwd(user);// 更新密码
		request.getSession().removeAttribute("VirCode");
		return json + "";

	}

	// 更新个人信息
	@Role
	@RequestMapping(value = "/user/updateInfo")
	@ResponseBody
	public String updateInfo(JSON json, int uid, String uphone, String uname,
			String uaddress, String uemail, String gname, String mname) {
		User user = new User();
		user.setUid(uid);
		user.setUphone(uphone);
		user.setUname(uname);
		user.setUaddress(uaddress);
		user.setUemail(uemail);
		user.setGname(gname);
		user.setMname(mname);
		userService.update(user);
		return json.toString();
	}

	// 上传头像
	@RequestMapping("/user/upload")
	@ResponseBody
	public Object uploadImage(String uid, JSON json,
			HttpServletRequest request, MultipartFile file) {
		Map uploadPicture = uploadPicture(file, request);
		String url = uploadPicture.get("url").toString();
		Map map = new HashMap<String, Object>();
		map.put("uid", uid);
		map.put("uimage", url);
		userService.updateImage(map);
		json.add("url", url);
		return json.toString();
	}

	public Map uploadPicture(MultipartFile uploadFile,
			HttpServletRequest request) {
		Map resultMap = new HashMap();
		try {
			// 重新生成一个新的文件名
			// 取原始文件名
			String oldName = uploadFile.getOriginalFilename();
			// 生成新的文件名
			// 使用id生成策略生成图片名称
			String newName = IDUtils.getImageName();
			newName = newName + oldName.substring(oldName.lastIndexOf("."));
			// 图片上传
			System.out.print("xuzi" + newName);
			// String imagePath = new DateTime().toString("/yyyy/MM/dd");'
			ServletContext sc = request.getSession().getServletContext();
			String path = sc.getRealPath("img") + "/";
			File f = new File(path);
			if (!f.exists()) {
				f.mkdirs();
			}
			FileOutputStream fos = new FileOutputStream(path + newName);
			int b1 = 0;
			InputStream in = uploadFile.getInputStream();
			while ((b1 = in.read()) != -1) {
				fos.write(b1);
			}
			fos.close();
			in.close();
			resultMap.put("error", 0);
			resultMap.put("url", "/img/" + newName);
			// 返回结果
			return resultMap;
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("error", 1);
			resultMap.put("message", "exception");
			return resultMap;
		}
	}

	// 上传头像
	@Role
	@RequestMapping("/user/updatePwd")
	@ResponseBody
	public String updatPwd(JSON json, User user, String old, String news) {
		System.out.println(user);
		Map map = new HashMap<String, String>();
		map.put("old", TokenUtil.MD5(old));
		map.put("news", TokenUtil.MD5(news));
		map.put("uid", user.getUid());
		int sum = userService.updatePwdByOld(map);
		if (sum == 0) {
			json.add("status", 0);
		}
		return json.toString();
	}
}
