package com.xawl.car.util;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Set;

import com.xawl.car.webCategory.OrderedProperties;

public class MapUtil {

	public static Object[][] propertsToArray(OrderedProperties map) {
		Object obj[][] = new Object[2][map.keySet().size()]; 
		Set set = map.keySet(); 
		obj[0]=(Object[])set.toArray().clone(); 
		for(int i=0;i <obj[0].length;i++) 
		obj[1][i] = map.get(obj[0][i]); 
		return obj;
	}
}
