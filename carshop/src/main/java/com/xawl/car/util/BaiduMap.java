package com.xawl.car.util;

public class BaiduMap {

	private static double EARTH_RADIUS = 6378.137D;

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	public static double GetDistance(double lat1, double lng1, double lat2,
			double lng2) {
		double radLat1 = rad(lat1);
		System.out.println(radLat1);
		double radLat2 = rad(lat2);
		System.out.println(radLat2);
		double a = radLat1 - radLat2;
		System.out.println(a);
		double b = rad(lng1) - rad(lng2);
		System.out.println(b);
		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
				+ Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		System.out.println(s);
		s = Math.round(s * 10000) / 10000.0;
		return s;
	}

	public static long aa(double lat1, double lng1, double lat2, double lng2) {
		return (long) ((6378.138 * 2.0 * Math.asin(Math.sqrt(Math.pow(Math
				.sin((lat1 * Math.PI / 180.0 - lat2 * Math.PI / 180.0) / 2.0),
				2.0)
				+ Math.cos(lat1 * Math.PI / 180.0)
				* Math.cos(lat2 * Math.PI / 180.0)
				* Math.pow(
						Math.sin((lng1 * Math.PI / 180.0 - lng2 * Math.PI
								/ 180.0) / 2.0), 2.0))) * 1000));

	}

	public static void main(String[] args) {
		// System.out.println(Math.pow(Math.sin((108.9017651829*Math.PI/180.0-108.9056641829*Math.PI/180.0)/2.0),2.0));
		// System.out.println(Math.cos(108.9017651829 * Math.PI / 180.0));
		//System.out.println(Math.cos(108.9056641829 * Math.PI / 180.0));

//		System.out.println(Math.pow(Math.sin((34.2137709662 * Math.PI / 180.0 - 34.2143599662
			//	* Math.PI / 180.0) / 2.0), 2.0));
		
		
//		System.out.println(Math.asin(Math.sqrt(Math.pow(Math
//				.sin((108.9017651829 * Math.PI / 180.0 - 108.9056641829 * Math.PI / 180.0) / 2.0),
//				2.0)
//				+ Math.cos(108.9017651829 * Math.PI / 180.0)
//				* Math.cos(108.9056641829 * Math.PI / 180.0)
//				* Math.pow(
//						Math.sin((34.2137709662 * Math.PI / 180.0 - 34.2143599662 * Math.PI
//								/ 180.0) / 2.0), 2.0)))*1000);
//		

		System.out.println(Math.asin(1));
		
		// System.out.println(aa(34.2137709662, 108.9017651829,
		// 34.2143599662, 108.9056641829));

	}
}
