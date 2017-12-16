package cn.itcast.invoice.util.jfreechart;

import java.awt.Font;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartDemo1 {
	private static JFreeChart createChart() {
		DefaultPieDataset localDefaultPieDataset = new DefaultPieDataset();
		localDefaultPieDataset.setValue("Three", new Double(1D));
		localDefaultPieDataset.setValue("Four", new Double(1D));
		localDefaultPieDataset.setValue("Five", new Double(1D));
		localDefaultPieDataset.setValue("Six", new Double(1D));
		
		JFreeChart localJFreeChart = ChartFactory.createPieChart("采购统计", localDefaultPieDataset, true, true, false);
		PiePlot localPiePlot = (PiePlot) localJFreeChart.getPlot();
		localPiePlot.setLabelFont(new Font("SansSerif", 0, 12));
		localPiePlot.setNoDataMessage("No data available");
		localPiePlot.setLabelGap(0.02D);
		return localJFreeChart;
	}
	public static void main(String[] args) throws IOException {
		JFreeChart jfc = PieChartDemo1.createChart();
		//转换成图片
		RenderedImage im = jfc.createBufferedImage(500, 370);
		ImageIO.write(im, "png", new File("1.png"));
	}
}