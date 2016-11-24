//package com.xawl.car.util;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import com.xawl.shop.domain.VO.CategoryVO;
//
//public class CategoryUtil {
//
//	public static List<CategoryVO> parse(List<CategoryVO> result) {
//
//		List<CategoryVO> root = new ArrayList<CategoryVO>();
//
//		for (CategoryVO c : result) {
//			if ("0".equals(c.getPid())) { // 封装1级菜单
//				root.add(c);
//			}
//			for (CategoryVO b : result) {
//				if (b.getPid().equals(c.getCid())) {
//					if (c.getChildren() == null) {
//						List<CategoryVO> my = new ArrayList<CategoryVO>();
//						my.add(b);
//						c.setChildren(my);
//					} else {
//						c.getChildren().add(b);
//					}
//				}
//			}
//		}
//		return root;
//	}
//
//}
