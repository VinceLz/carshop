package com.xawl.car.util;


public class PoiUtils {
		
//	public static void main(String[] args) throws FileNotFoundException {
//		InputStream in=new FileInputStream("d://text2.xls");
//		LinkedHashMap<String, LinkedHashMap<String, String>> execl2Map = execl2Map(in);
//		System.out.println(execl2Map);
//	}
//	
//	
//	public static LinkedHashMap<String,LinkedHashMap<String,String>> execl2Map(InputStream in){
//		LinkedHashMap<String, LinkedHashMap<String, String>> map = new LinkedHashMap<String, LinkedHashMap<String, String>>();
//
//		
//		Workbook wb = null;
//		try {
//			wb = (Workbook) new HSSFWorkbook(in);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		// ��õ�һ���?
//		Sheet sheet = wb.getSheetAt(0);
//		// ��õ�һ���?�ĵ����
//		Iterator<Row> rows = sheet.rowIterator();
//		LinkedHashMap<String, String> map2 = null;
//		String key = "";
//		while (rows.hasNext()) {
//			Row row = rows.next();
//			if (row.getPhysicalNumberOfCells() == 3&&row.getCell(1).getStringCellValue().trim().isEmpty()||row.getCell(0).getStringCellValue().contains("$")) {
//				// ����
//				if (map2 != null) {
//					map.put(key, map2);	
//					map2 = null;
//					key = "";
//				}
//				key = row.getCell(0).getStringCellValue().trim().replace("$", "");
//				map2 = new LinkedHashMap<String, String>();
//			} else {
//				// С��
//				String keys = getValue(row.getCell(0));
//				String value = getValue(row.getCell(1));
//				map2.put(keys, value);
//			}
//		}
//		if (map2 != null) {
//			map.put(key, map2);
//		}
//		
//	
//		return map;
//		
//	}
//	
//	
//	//���excel�������⣬�����ֵ  
//    public static  String getValue(Cell cell) {  
//        String value = "";  
//        if(null==cell){  
//            return value;  
//        }  
//        switch (cell.getCellType()) {  
//        //��ֵ��  
//        case Cell.CELL_TYPE_NUMERIC:  
//            if (HSSFDateUtil.isCellDateFormatted(cell)) {  
//                //�����date������ ����ȡ��cell��dateֵ  
//                Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());  
//                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
//                value = format.format(date);;  
//            }else {// ������  
//                double d=cell.getNumericCellValue();  
//                value = Double.toString(d);
//                //���1234.0  ȥ�������.0  
//              
//                if(null != value && !"".equals(value.trim())){  
//                     String[] item = value.split("[.]");  
//                     if(1<item.length&&"0".equals(item[1])){  
//                         value=item[0];  
//                     }  
//                }  
//            }  
//            break;  
//            //�ַ�����   
//        case Cell.CELL_TYPE_STRING:  
//            value = cell.getStringCellValue().toString();  
//            break;  
//        // ��ʽ����  
//        case Cell.CELL_TYPE_FORMULA:  
//            //����ʽ����ֵ  
//            value = String.valueOf(cell.getNumericCellValue());  
//            if (value.equals("NaN")) {// ����ȡ�����ֵΪ�Ƿ�ֵ,��ת��Ϊ��ȡ�ַ�  
//                value = cell.getStringCellValue().toString();  
//            }  
//            break;  
//        // ��������  
//        case Cell.CELL_TYPE_BOOLEAN:  
//            value = " "+ cell.getBooleanCellValue();  
//            break;  
//        // ��ֵ  
//        case Cell.CELL_TYPE_BLANK:   
//            value = "";  
//           
//            break;  
//        // ����  
//        case Cell.CELL_TYPE_ERROR:   
//            value = "";  
//          
//            break;  
//        default:  
//            value = cell.getStringCellValue().toString();  
//    }  
//        if("null".endsWith(value.trim())){  
//            value="";  
//        }  
//      return value;  
//    }  
}
		
		
		
	