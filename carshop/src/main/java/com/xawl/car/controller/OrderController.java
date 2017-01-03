package com.xawl.car.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.xawl.car.domain.JSON;
import com.xawl.car.domain.Order;
import com.xawl.car.service.ModelService;
import com.xawl.car.service.OrderService;
import com.xawl.car.util.DateUtil;
import com.xawl.car.util.JsonUtils;
import com.xawl.car.util.PoiUtils;

/*
 * 订单
 */
@Controller
public class OrderController {
	@Resource
	private ModelService modelService;
	@Resource
	private OrderService orderService;

	// 生成订单
	@RequestMapping("/order/up")
	@ResponseBody
	public String getTop5(JSON json, String mid, String total, Integer uid,
			String uname, String color, String buyWay, String city,
			String cardCity) {
		// 通过mid 获取一些gid bid 等信息
		Order order = modelService.getbyMid2All(mid);
		order.setOrdertime(DateUtil.getSqlDate());
		order.setTotal(Double.parseDouble(total));
		order.setStatus(-1);
		order.setUid(uid);
		order.setUname(uname);
		order.setColor(color);
		order.setBuyWay(buyWay);
		order.setCity(city);
		order.setCardCity(cardCity);
		orderService.insert(order);
		return json + "";
	}
	
	public static void main(String[] args) throws IOException {

		LinkedHashMap<String, LinkedHashMap<String, String>> map = new LinkedHashMap<String, LinkedHashMap<String, String>>();

		// 对读取Excel表格标题测试
		InputStream is = new FileInputStream("d:\\text2.xls");
		LinkedHashMap<String, LinkedHashMap<String, String>> execl2Map = PoiUtils
				.execl2Map(is);
		
		String str = JsonUtils.objectToJson(execl2Map);
		JSONObject jsonObject=JSONObject.parseObject(str);
		
		
		
		
		LinkedHashMap<String, LinkedHashMap<String, String>> jsonToPojo = (LinkedHashMap<String, LinkedHashMap<String, String>>) JsonUtils
				.jsonToPojo(jsonObject.toJSONString(), LinkedHashMap.class);
		
		Set<String> keySet = jsonToPojo.keySet();
		for(String st1r:keySet){
			System.out.println("---------"+st1r);
			LinkedHashMap<String, String> linkedHashMap = jsonToPojo.get(st1r);
			Set<String> keySet2 = linkedHashMap.keySet();
			for(String str2:keySet2){
				System.out.println("key"+str2+"value"+linkedHashMap.get(str2));
				
			}
		}
		
		
		
	}

	public static double getDistance(double long1, double lat1, double long2,
			double lat2) {
		double a, b, R;
		R = 6378137; // 地球半径
		lat1 = lat1 * Math.PI / 180.0;
		lat2 = lat2 * Math.PI / 180.0;
		a = lat1 - lat2;
		b = (long1 - long2) * Math.PI / 180.0;
		double d;
		double sa2, sb2;
		sa2 = Math.sin(a / 2.0);
		sb2 = Math.sin(b / 2.0);
		d = 2
				* R
				* Math.asin(Math.sqrt(sa2 * sa2 + Math.cos(lat1)
						* Math.cos(lat2) * sb2 * sb2));
		return d;
	}

	// 解决excel类型问题，获得数值
	public static String getValue(Cell cell) {
		String value = "";
		if (null == cell) {
			return value;
		}
		switch (cell.getCellType()) {
		// 数值型
		case Cell.CELL_TYPE_NUMERIC:
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				// 如果是date类型则 ，获取该cell的date值
				Date date = HSSFDateUtil
						.getJavaDate(cell.getNumericCellValue());
				SimpleDateFormat format = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				value = format.format(date);
				;
			} else {// 纯数字
				double d = cell.getNumericCellValue();
				value = Double.toString(d);
				// 解决1234.0 去掉后面的.0

				if (null != value && !"".equals(value.trim())) {
					String[] item = value.split("[.]");
					if (1 < item.length && "0".equals(item[1])) {
						value = item[0];
					}
				}
			}
			break;
		// 字符串类型
		case Cell.CELL_TYPE_STRING:
			value = cell.getStringCellValue().toString();
			break;
		// 公式类型
		case Cell.CELL_TYPE_FORMULA:
			// 读公式计算值
			value = String.valueOf(cell.getNumericCellValue());
			if (value.equals("NaN")) {// 如果获取的数据值为非法值,则转换为获取字符串
				value = cell.getStringCellValue().toString();
			}
			break;
		// 布尔类型
		case Cell.CELL_TYPE_BOOLEAN:
			value = " " + cell.getBooleanCellValue();
			break;
		// 空值
		case Cell.CELL_TYPE_BLANK:
			value = "";

			break;
		// 故障
		case Cell.CELL_TYPE_ERROR:
			value = "";

			break;
		default:
			value = cell.getStringCellValue().toString();
		}
		if ("null".endsWith(value.trim())) {
			value = "";
		}
		return value;
	}
}
