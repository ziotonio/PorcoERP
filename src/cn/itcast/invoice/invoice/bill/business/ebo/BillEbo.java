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
		theme.setExtraLargeFont(new Font("宋体", Font.PLAIN, 20));
		theme.setLargeFont(new Font("宋体", Font.PLAIN, 14));
		theme.setRegularFont(new Font("宋体", Font.PLAIN, 12));
		theme.setSmallFont(new Font("宋体", Font.PLAIN, 10));
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
		//将饼图内容转入os对象
		//准备数据
		DefaultPieDataset localDefaultPieDataset = new DefaultPieDataset();
		for(Object[] objs:billList){
			GoodsModel gm = (GoodsModel) objs[0];
			Long num = (Long) objs[1];
			localDefaultPieDataset.setValue(gm.getName(), new Double(num));
		}
		//数据转化为jfreechart对象
		JFreeChart localJFreeChart = ChartFactory.createPieChart("采购统计", localDefaultPieDataset, true, true, false);
		PiePlot localPiePlot = (PiePlot) localJFreeChart.getPlot();
		localPiePlot.setLabelFont(new Font("SansSerif", 0, 12));
		localPiePlot.setNoDataMessage("No data available");
		localPiePlot.setLabelGap(0.02D);
		//jfreechart对象装入os对象
		try {
			ImageIO.write(localJFreeChart.createBufferedImage(456, 360), "png", os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public InputStream getExcelBill(List<Object[]> billList) throws Exception{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		//写Excel文件
		WritableWorkbook b = ExcelUtil.cWorkbook(os); 
		
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
		int i = 1;
		Long sum = 0L;
		for(Object[] objs:billList){
			GoodsModel gm = (GoodsModel)objs[0];
			Long num = (Long)objs[1];
			
			Label lab_data_1 = ExcelUtil.cLabel(row+i, 2, i+"");
			ExcelUtil.sLabelStyle(lab_data_1, "宋体", 14, Colour.BLACK, null, 1, "0120");
			ExcelUtil.aLabelToSheet(lab_data_1, s);
			
			Label lab_data_2 = ExcelUtil.cLabel(row+i, 3, gm.getGtm().getSm().getName());
			ExcelUtil.sLabelStyle(lab_data_2, "宋体", 14, Colour.BLACK, null, 1, "0110");
			ExcelUtil.aLabelToSheet(lab_data_2, s);
			
			Label lab_data_3 = ExcelUtil.cLabel(row+i, 4, gm.getName());
			ExcelUtil.sLabelStyle(lab_data_3, "宋体", 14, Colour.BLACK, null, 1, "0110");
			ExcelUtil.aLabelToSheet(lab_data_3, s);
			
			Label lab_data_4 = ExcelUtil.cLabel(row+i, 5, num.toString());
			ExcelUtil.sLabelStyle(lab_data_4, "宋体", 14, Colour.BLACK, null, 1, "0112");
			ExcelUtil.aLabelToSheet(lab_data_4, s);
			
			sum += num;
			i++;
		}
		
		//设置行高
		ExcelUtil.sRowSize(s, row+i, 24);
		//合并
		ExcelUtil.sMerge(s,row+i  , 2 , row+i , 4);
		
		Label lab_toatl_tital = ExcelUtil.cLabel(row+i, 2, "总计");
		ExcelUtil.sLabelStyle(lab_toatl_tital, "黑体", 18, Colour.BLACK, null, 1, "2220");
		ExcelUtil.aLabelToSheet(lab_toatl_tital, s);
		
		Label lab_toatl_data = ExcelUtil.cLabel(row+i, 5, sum.toString());
		ExcelUtil.sLabelStyle(lab_toatl_data, "黑体", 18, Colour.BLACK, null, 1, "2222");
		ExcelUtil.aLabelToSheet(lab_toatl_data, s);
					
		b.write(); 
		b.close(); 
		//数据已经进入os对象，需要将os对象转换成输入流对象
		InputStream is = new ByteArrayInputStream(os.toByteArray());
		return is;
	}


}
