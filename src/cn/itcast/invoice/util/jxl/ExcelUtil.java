package cn.itcast.invoice.util.jxl;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

//操作Excel文件工具类
public class ExcelUtil {
	
	/**
	 * 创建Excel文件流
	 * @return 
	 */
	public static WritableWorkbook cWorkbook(OutputStream os){
		try {
			return Workbook.createWorkbook(os);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 
	}
	
	/**
	 * 创建工作表
	 * @param b
	 * @param idx 工作表索引,从1开始
	 * @param name 工作表显示名称
	 * @return
	 */
	public static WritableSheet cSheet(WritableWorkbook b,int idx,String name){
		return b.createSheet(name, idx-1); 
	}
	
	/**
	 * 创建单元格
	 * @param a 行
	 * @param b 列
	 * @param value 值
	 * @return
	 */
	public static Label cLabel(int a ,int b ,String value){
		return new Label(b-1, a-1, value); 
	}
	
	/**
	 * 添加Label到Sheet
	 * @param l label
	 * @param s sheet
	 */
	public static void aLabelToSheet(Label l,WritableSheet s){
		try {
			s.addCell(l);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	/**
	 * 设置工作表列宽
	 * @param s
	 * @param idx 索引从1开始
	 * @param width 字符宽度
	 */
	public static void sColumnSize(WritableSheet s,int idx,int width){
		s.setColumnView(idx-1, width);
	}
	
	/**
	 * 设置工作表高度
	 * @param s
	 * @param idx 索引从1开始
	 * @param height 字符高度
	 */
	public static void sRowSize(WritableSheet s,int idx,int height){
		try {
			s.setRowView(idx-1, height*20);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 设置单元格合并
	 * @param a 起始单元格行
	 * @param b 起始单元格列
	 * @param c 终止单元格行
	 * @param d 终止单元格列
	 */
	public static void sMerge(WritableSheet s,int a,int b,int c,int d){
		try {
			s.mergeCells(b-1, a-1, d-1, c-1);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Map<Integer, Alignment> alignMap = new HashMap<Integer, Alignment>();
	static{
		alignMap.put(0, Alignment.LEFT);
		alignMap.put(1, Alignment.CENTRE);
		alignMap.put(2, Alignment.RIGHT);
	}
	
	private static WritableCellFormat setBorderTop(char value, WritableCellFormat wcf) throws WriteException {
		if(value == '1'){
			wcf.setBorder(Border.TOP, BorderLineStyle.THIN,jxl.format.Colour.BLACK);
		}else if(value == '2'){
			wcf.setBorder(Border.TOP, BorderLineStyle.MEDIUM,jxl.format.Colour.BLACK);
		}
		return wcf;
	}
	
	private static WritableCellFormat setBorderBottom(char value, WritableCellFormat wcf) throws WriteException {
		if(value == '1'){
			wcf.setBorder(Border.BOTTOM, BorderLineStyle.THIN,jxl.format.Colour.BLACK);
		}else if(value == '2'){
			wcf.setBorder(Border.BOTTOM, BorderLineStyle.MEDIUM,jxl.format.Colour.BLACK);
		}
		return wcf;
	}
	
	private static WritableCellFormat setBorderLeft(char value, WritableCellFormat wcf) throws WriteException {
		if(value == '1'){
			wcf.setBorder(Border.LEFT, BorderLineStyle.THIN,jxl.format.Colour.BLACK);
		}else if(value == '2'){
			wcf.setBorder(Border.LEFT, BorderLineStyle.MEDIUM,jxl.format.Colour.BLACK);
		}
		return wcf;
	}
	
	private static WritableCellFormat setBorderRight(char value, WritableCellFormat wcf) throws WriteException {
		if(value == '1'){
			wcf.setBorder(Border.RIGHT, BorderLineStyle.THIN,jxl.format.Colour.BLACK);
		}else if(value == '2'){
			wcf.setBorder(Border.RIGHT, BorderLineStyle.MEDIUM,jxl.format.Colour.BLACK);
		}
		return wcf;
	}
	/**
	 * Imposta lo stile della cella
	 * @param l
	 * @param fontName Caratteri: stringhe, ad esempio "grassetto"
	 * @param fontSize Dimensione carattere: numero, ad esempio 24
	 * @param colour Colore carattere: costante colore
	 * @param bgColour Colore di sfondo della cella: costante colore
	 * @param align modalità di allineamento: 0- sinistra, 1- centro, 2 destra
	 * @param borderStyle border line stringa, ad esempio con 0000 non si sono bordi su e giù，
	 * 			1100 è un bordo dall'alto verso il basso，如0011代表左右要边框，如果0220代表左边和下边要粗边框
	 */
	public static void sLabelStyle(
			Label l,String fontName,int fontSize,
			Colour colour,Colour bgColour,
			int align,String borderStyle){
		
		try {
			if(colour == null) colour = Colour.BLACK;
			if(bgColour == null) bgColour = Colour.WHITE;
			WritableFont wf = new WritableFont(
					//Imposta il carattere
					WritableFont.createFont(fontName), 
					//Imposta dimensione del carattere
					fontSize,
					//Imposta il grassetto
					WritableFont.NO_BOLD,
					//Imposta l'italico
					false, 
					//Imposta sottolineato
					UnderlineStyle.NO_UNDERLINE,
					//Imposta colore del carattere
					colour);
			
			WritableCellFormat wcf = new WritableCellFormat(wf); 
			//Imposta il colore dello sfondo
			wcf.setBackground(bgColour); 
			//Imposta allineamento
			wcf.setAlignment(Alignment.CENTRE);
			
			//Imposta il bordo
			if(borderStyle != null && borderStyle.length() == 4){
				char[] bs = borderStyle.toCharArray();
				setBorderTop(bs[0], wcf);
				setBorderBottom(bs[1], wcf);
				setBorderLeft(bs[2], wcf);
				setBorderRight(bs[3], wcf);
			}
			
			l.setCellFormat(wcf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
