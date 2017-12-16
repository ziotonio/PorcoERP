package cn.itcast.invoice.util.format;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatUtil {
	public static String formatDate(Long time){
		if(time == null) return "-";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return  df.format(new Date(time));
	}
	public static String formatTime(Long time){
		if(time == null) return "-";
		DateFormat df = new SimpleDateFormat("HH:mm:ss");
		return  df.format(new Date(time));
	}
	public static String formatDateTime(Long time){
		if(time == null) return "-";
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return  df.format(new Date(time));
	}
	//将一个小数显示两位小数
	public static String formatMoney(Double money){
		DecimalFormat df = new DecimalFormat("#0.00");
	    return df.format(money);
	}
	
}
