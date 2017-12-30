package cn.itcast.invoice.invoice.bill.business.ebo;

import java.awt.Font;
import java.awt.RenderingHints;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;

import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import cn.itcast.invoice.invoice.bill.business.ebi.BillEbi;
import cn.itcast.invoice.invoice.bill.dao.dao.BillDao;
import cn.itcast.invoice.invoice.bill.vo.BillQueryModel;
import cn.itcast.invoice.invoice.goods.vo.GoodsModel;
import cn.itcast.invoice.invoice.order.vo.OrderDetailModel;
import cn.itcast.invoice.util.jxl.ExcelUtil;

public class BillEbo implements BillEbi{
	private static final GoodsModel[] objs = null;

	static {
		StandardChartTheme theme = new StandardChartTheme("unicode") {
			public void apply(JFreeChart chart) {
				chart.getRenderingHints().put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
				super.apply(chart);
			}
		};
		theme.setExtraLargeFont(new Font("å®‹ä½“", Font.PLAIN, 20));
		theme.setLargeFont(new Font("å®‹ä½“", Font.PLAIN, 14));
		theme.setRegularFont(new Font("å®‹ä½“", Font.PLAIN, 12));
		theme.setSmallFont(new Font("å®‹ä½“", Font.PLAIN, 10));
		ChartFactory.setChartTheme(theme);
	}
	private BillDao billDao;
	public void setBillDao(BillDao billDao) {
		this.billDao = billDao;
	}

	public List<Object[]> getBillByGoods(BillQueryModel bqm) {
		return billDao.getBillByGoods(bqm);
	}

	public List<OrderDetailModel> getBillDetailByGoods(BillQueryModel bqm) {
		return billDao.getBillDetailByGoods(bqm);
	}

	public void getBillForPie(OutputStream os, List<Object[]> billList) {
		//å°†é¥¼å›¾å†…å®¹è½¬å…¥oså¯¹è±¡
		//å‡†å¤‡æ•°æ�®
		DefaultPieDataset localDefaultPieDataset = new DefaultPieDataset();
		for(Object[] objs:billList){
			GoodsModel gm = (GoodsModel) objs[0];
			Long num = (Long) objs[1];
			localDefaultPieDataset.setValue(gm.getName(), new Double(num));
		}
		//æ•°æ�®è½¬åŒ–ä¸ºjfreechartå¯¹è±¡
		JFreeChart localJFreeChart = ChartFactory.createPieChart("é‡‡è´­ç»Ÿè®¡", localDefaultPieDataset, true, true, false);
		PiePlot localPiePlot = (PiePlot) localJFreeChart.getPlot();
		localPiePlot.setLabelFont(new Font("SansSerif", 0, 12));
		localPiePlot.setNoDataMessage("No data available");
		localPiePlot.setLabelGap(0.02D);
		//jfreechartå¯¹è±¡è£…å…¥oså¯¹è±¡
		try {
			ImageIO.write(localJFreeChart.createBufferedImage(456, 360), "png", os);
		} catch (IOException e) {
			 System.out.println("Something was wrong");
		}
	}

	public InputStream getExcelBill(List<Object[]> billList) throws Exception{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		//å†™Excelæ–‡ä»¶
		WritableWorkbook b = ExcelUtil.cWorkbook(os); 
		
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
		int i = 1;
		Long sum = 0L;
		for(Object[] objs:billList){
			GoodsModel gm = (GoodsModel)objs[0];
			Long num = (Long)objs[1];
			
			Label lab_data_1 = ExcelUtil.cLabel(row+i, 2, i+"");
			ExcelUtil.sLabelStyle(lab_data_1, "å®‹ä½“", 14, Colour.BLACK, null, 1, "0120");
			ExcelUtil.aLabelToSheet(lab_data_1, s);
			
			Label lab_data_2 = ExcelUtil.cLabel(row+i, 3, gm.getGtm().getSm().getName());
			ExcelUtil.sLabelStyle(lab_data_2, "å®‹ä½“", 14, Colour.BLACK, null, 1, "0110");
			ExcelUtil.aLabelToSheet(lab_data_2, s);
			
			Label lab_data_3 = ExcelUtil.cLabel(row+i, 4, gm.getName());
			ExcelUtil.sLabelStyle(lab_data_3, "å®‹ä½“", 14, Colour.BLACK, null, 1, "0110");
			ExcelUtil.aLabelToSheet(lab_data_3, s);
			
			Label lab_data_4 = ExcelUtil.cLabel(row+i, 5, num.toString());
			ExcelUtil.sLabelStyle(lab_data_4, "å®‹ä½“", 14, Colour.BLACK, null, 1, "0112");
			ExcelUtil.aLabelToSheet(lab_data_4, s);
			
			sum += num;
			i++;
		}
		
		//è®¾ç½®è¡Œé«˜
		ExcelUtil.sRowSize(s, row+i, 24);
		//å�ˆå¹¶
		ExcelUtil.sMerge(s,row+i  , 2 , row+i , 4);
		
		Label lab_toatl_tital = ExcelUtil.cLabel(row+i, 2, "æ€»è®¡");
		ExcelUtil.sLabelStyle(lab_toatl_tital, "é»‘ä½“", 18, Colour.BLACK, null, 1, "2220");
		ExcelUtil.aLabelToSheet(lab_toatl_tital, s);
		
		Label lab_toatl_data = ExcelUtil.cLabel(row+i, 5, sum.toString());
		ExcelUtil.sLabelStyle(lab_toatl_data, "é»‘ä½“", 18, Colour.BLACK, null, 1, "2222");
		ExcelUtil.aLabelToSheet(lab_toatl_data, s);
					
		b.write(); 
		b.close(); 
		//æ•°æ�®å·²ç»�è¿›å…¥oså¯¹è±¡ï¼Œéœ€è¦�å°†oså¯¹è±¡è½¬æ�¢æˆ�è¾“å…¥æµ�å¯¹è±¡
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		return is;
	}


}
