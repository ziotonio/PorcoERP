package cn.itcast.invoice.util.jxl;

import java.io.File;

import jxl.Workbook;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class JxlDemo {
	public static void main(String[] args) throws Exception {
		//读取Excel
		/*
		Workbook workbook = Workbook.getWorkbook(new File("1.xls")); 
		Sheet sheet = workbook.getSheet(0); 
		Cell a1 = sheet.getCell(1,2); 
		String s = a1.getContents(); 
		System.out.println(s);
		*/
		//写Excel
		/*
		WritableWorkbook workbook = Workbook.createWorkbook(new File("2.xls")); 
		
		WritableSheet sheet = workbook.createSheet("嘿嘿", 0); 

		Label label2 = new Label( 1 , 0, "嘿嘿嘿嘿"); 
		sheet.addCell(label2); 
		
		workbook.write(); 
		workbook.close(); 
		 */
		
		WritableWorkbook b = Workbook.createWorkbook(new File("2.xls")); 
		
		//WritableSheet s = workbook.createSheet("总括", 0); 
		WritableSheet s = ExcelUtil.cSheet(b, 1, "总括");
		
		//设置行高
		ExcelUtil.sRowSize(s, 1, 15);
		ExcelUtil.sRowSize(s, 2, 37);
		ExcelUtil.sRowSize(s, 3, 6);
		ExcelUtil.sRowSize(s, 4, 23);
		
		//设置列宽
		ExcelUtil.sColumnSize(s, 1, 8);
		ExcelUtil.sColumnSize(s, 2, 8);
		ExcelUtil.sColumnSize(s, 3, 25);
		ExcelUtil.sColumnSize(s, 4, 25);
		ExcelUtil.sColumnSize(s, 5, 25);
		
		//合并
		ExcelUtil.sMerge(s, 2 , 2 , 2 , 4);
		ExcelUtil.sMerge(s, 3 , 2 , 3 , 5);
		
		//进货统计报表		
		Label lab22 = ExcelUtil.cLabel(2, 2, "进货统计报表");
		ExcelUtil.sLabelStyle(lab22, "黑体", 24, Colour.BLACK, Colour.LIGHT_BLUE, 1, "2020");
		ExcelUtil.aLabelToSheet(lab22, s);
		//不限
		Label lab25 = ExcelUtil.cLabel(2, 5, "不限");
		ExcelUtil.sLabelStyle(lab25, "黑体", 12, Colour.BLACK, Colour.LIGHT_BLUE, 1, "2002");
		ExcelUtil.aLabelToSheet(lab25, s);
		
		Label lab32 = ExcelUtil.cLabel(3, 2, "");
		ExcelUtil.sLabelStyle(lab32, "黑体", 1, Colour.BLACK, Colour.GRAY_25, 1, "2022");
		ExcelUtil.aLabelToSheet(lab32, s);
		
		Label lab42 = ExcelUtil.cLabel(4, 2, "编号");
		ExcelUtil.sLabelStyle(lab42, "黑体", 18, Colour.BLACK, null, 1, "2220");
		ExcelUtil.aLabelToSheet(lab42, s);
		
		Label lab43 = ExcelUtil.cLabel(4, 3, "厂商");
		ExcelUtil.sLabelStyle(lab43, "黑体", 18, Colour.BLACK, null, 1, "2220");
		ExcelUtil.aLabelToSheet(lab43, s);
		
		Label lab44 = ExcelUtil.cLabel(4, 4, "商品名");
		ExcelUtil.sLabelStyle(lab44, "黑体", 18, Colour.BLACK, null, 1, "2220");
		ExcelUtil.aLabelToSheet(lab44, s);
		
		Label lab45 = ExcelUtil.cLabel(4, 5, "数量");
		ExcelUtil.sLabelStyle(lab45, "黑体", 18, Colour.BLACK, null, 1, "2222");
		ExcelUtil.aLabelToSheet(lab45, s);
					
		int row = 4;
		//循环产生数据
		int i ;
		for(i = 1;i<=5;i++){
			Label lab_data_1 = ExcelUtil.cLabel(row+i, 2, i+"");
			ExcelUtil.sLabelStyle(lab_data_1, "宋体", 14, Colour.BLACK, null, 1, "0120");
			ExcelUtil.aLabelToSheet(lab_data_1, s);
			
			Label lab_data_2 = ExcelUtil.cLabel(row+i, 3, "厂商"+i);
			ExcelUtil.sLabelStyle(lab_data_2, "宋体", 14, Colour.BLACK, null, 1, "0110");
			ExcelUtil.aLabelToSheet(lab_data_2, s);
			
			Label lab_data_3 = ExcelUtil.cLabel(row+i, 4, "商品"+i);
			ExcelUtil.sLabelStyle(lab_data_3, "宋体", 14, Colour.BLACK, null, 1, "0110");
			ExcelUtil.aLabelToSheet(lab_data_3, s);
			
			Label lab_data_4 = ExcelUtil.cLabel(row+i, 5, i*i+"");
			ExcelUtil.sLabelStyle(lab_data_4, "宋体", 14, Colour.BLACK, null, 1, "0112");
			ExcelUtil.aLabelToSheet(lab_data_4, s);
			
		}
		
		//设置行高
		ExcelUtil.sRowSize(s, row+i, 24);
		//合并
		ExcelUtil.sMerge(s,row+i  , 2 , row+i , 4);
		
		Label lab_toatl_tital = ExcelUtil.cLabel(row+i, 2, "总计");
		ExcelUtil.sLabelStyle(lab_toatl_tital, "黑体", 18, Colour.BLACK, null, 1, "2220");
		ExcelUtil.aLabelToSheet(lab_toatl_tital, s);
		
		Label lab_toatl_data = ExcelUtil.cLabel(row+i, 5, "12345");
		ExcelUtil.sLabelStyle(lab_toatl_data, "黑体", 18, Colour.BLACK, null, 1, "2222");
		ExcelUtil.aLabelToSheet(lab_toatl_data, s);
					
		b.write(); 
		b.close(); 
	}
}
