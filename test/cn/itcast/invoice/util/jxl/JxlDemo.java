package cn.itcast.invoice.util.jxl;

import java.io.File;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class JxlDemo {
	public static void main(String[] args) throws Exception {
		//è¯»å�–Excel
		/*
		Workbook workbook = Workbook.getWorkbook(new File("1.xls")); 
		Sheet sheet = workbook.getSheet(0); 
		Cell a1 = sheet.getCell(1,2); 
		String s = a1.getContents(); 
		System.out.println(s);
		*/
		//å†™Excel
		/*
		WritableWorkbook workbook = Workbook.createWorkbook(new File("2.xls")); 
		
		WritableSheet sheet = workbook.createSheet("å˜¿å˜¿", 0); 

		Label label2 = new Label( 1 , 0, "å˜¿å˜¿å˜¿å˜¿"); 
		sheet.addCell(label2); 
		
		workbook.write(); 
		workbook.close(); 
		 */
		
		/*WritableWorkbook b = Workbook.createWorkbook(new File("2.xls")); 
		
		//WritableSheet s = workbook.createSheet("æ€»æ‹¬", 0); 
		WritableSheet s = ExcelUtil.cSheet(b, 1, "æ€»æ‹¬");
		
		//è®¾ç½®è¡Œé«˜
		ExcelUtil.sRowSize(s, 1, 15);
		ExcelUtil.sRowSize(s, 2, 37);
		ExcelUtil.sRowSize(s, 3, 6);
		ExcelUtil.sRowSize(s, 4, 23);
		
		//è®¾ç½®åˆ—å®½
		ExcelUtil.sColumnSize(s, 1, 8);
		ExcelUtil.sColumnSize(s, 2, 8);
		ExcelUtil.sColumnSize(s, 3, 25);
		ExcelUtil.sColumnSize(s, 4, 25);
		ExcelUtil.sColumnSize(s, 5, 25);
		
		//å�ˆå¹¶
		ExcelUtil.sMerge(s, 2 , 2 , 2 , 4);
		ExcelUtil.sMerge(s, 3 , 2 , 3 , 5);
		
		//è¿›è´§ç»Ÿè®¡æŠ¥è¡¨		
		Label lab22 = ExcelUtil.cLabel(2, 2, "è¿›è´§ç»Ÿè®¡æŠ¥è¡¨");
		ExcelUtil.sLabelStyle(lab22, "é»‘ä½“", 24, Colour.BLACK, Colour.LIGHT_BLUE, 1, "2020");
		ExcelUtil.aLabelToSheet(lab22, s);
		//ä¸�é™�
		Label lab25 = ExcelUtil.cLabel(2, 5, "ä¸�é™�");
		ExcelUtil.sLabelStyle(lab25, "é»‘ä½“", 12, Colour.BLACK, Colour.LIGHT_BLUE, 1, "2002");
		ExcelUtil.aLabelToSheet(lab25, s);
		
		Label lab32 = ExcelUtil.cLabel(3, 2, "");
		ExcelUtil.sLabelStyle(lab32, "é»‘ä½“", 1, Colour.BLACK, Colour.GRAY_25, 1, "2022");
		ExcelUtil.aLabelToSheet(lab32, s);
		
		Label lab42 = ExcelUtil.cLabel(4, 2, "ç¼–å�·");
		ExcelUtil.sLabelStyle(lab42, "é»‘ä½“", 18, Colour.BLACK, null, 1, "2220");
		ExcelUtil.aLabelToSheet(lab42, s);
		
		Label lab43 = ExcelUtil.cLabel(4, 3, "åŽ‚å•†");
		ExcelUtil.sLabelStyle(lab43, "é»‘ä½“", 18, Colour.BLACK, null, 1, "2220");
		ExcelUtil.aLabelToSheet(lab43, s);
		
		Label lab44 = ExcelUtil.cLabel(4, 4, "å•†å“�å��");
		ExcelUtil.sLabelStyle(lab44, "é»‘ä½“", 18, Colour.BLACK, null, 1, "2220");
		ExcelUtil.aLabelToSheet(lab44, s);
		
		Label lab45 = ExcelUtil.cLabel(4, 5, "æ•°é‡�");
		ExcelUtil.sLabelStyle(lab45, "é»‘ä½“", 18, Colour.BLACK, null, 1, "2222");
		ExcelUtil.aLabelToSheet(lab45, s);
					
		int row = 4;
		//å¾ªçŽ¯äº§ç”Ÿæ•°æ�®
		
		int i ;
		
		for(i = 1;i<=5;i++){
			Label lab_data_1 = ExcelUtil.cLabel(row+i, 2, i+"");
			ExcelUtil.sLabelStyle(lab_data_1, "å®‹ä½“", 14, Colour.BLACK, null, 1, "0120");
			ExcelUtil.aLabelToSheet(lab_data_1, s);
			
			Label lab_data_2 = ExcelUtil.cLabel(row+i, 3, "åŽ‚å•†"+i);
			ExcelUtil.sLabelStyle(lab_data_2, "å®‹ä½“", 14, Colour.BLACK, null, 1, "0110");
			ExcelUtil.aLabelToSheet(lab_data_2, s);
			
			Label lab_data_3 = ExcelUtil.cLabel(row+i, 4, "å•†å“�"+i);
			ExcelUtil.sLabelStyle(lab_data_3, "å®‹ä½“", 14, Colour.BLACK, null, 1, "0110");
			ExcelUtil.aLabelToSheet(lab_data_3, s);
			
			Label lab_data_4 = ExcelUtil.cLabel(row+i, 5, i*i+"");
			ExcelUtil.sLabelStyle(lab_data_4, "å®‹ä½“", 14, Colour.BLACK, null, 1, "0112");
			ExcelUtil.aLabelToSheet(lab_data_4, s);
			
		}
		
		//è®¾ç½®è¡Œé«˜
		ExcelUtil.sRowSize(s, row+i, 24);
		//å�ˆå¹¶
		ExcelUtil.sMerge(s,row+i  , 2 , row+i , 4);
		
		Label lab_toatl_tital = ExcelUtil.cLabel(row+i, 2, "æ€»è®¡");
		ExcelUtil.sLabelStyle(lab_toatl_tital, "é»‘ä½“", 18, Colour.BLACK, null, 1, "2220");
		ExcelUtil.aLabelToSheet(lab_toatl_tital, s);
		
		Label lab_toatl_data = ExcelUtil.cLabel(row+i, 5, "12345");
		ExcelUtil.sLabelStyle(lab_toatl_data, "é»‘ä½“", 18, Colour.BLACK, null, 1, "2222");
		ExcelUtil.aLabelToSheet(lab_toatl_data, s);
					
		b.write(); 
		b.close(); 
		*/
	}
	
}
