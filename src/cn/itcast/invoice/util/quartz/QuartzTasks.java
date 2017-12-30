package cn.itcast.invoice.util.quartz;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.transaction.annotation.Transactional;

import cn.itcast.invoice.invoice.goods.dao.dao.GoodsDao;
import cn.itcast.invoice.util.format.FormatUtil;

@Transactional
public class QuartzTasks {
	private GoodsDao goodsDao;
	private JavaMailSenderImpl mailSender;
	
	public void setMailSender(JavaMailSenderImpl mailSender) {
		this.mailSender = mailSender;
	}
	//è¦�æ±‚æŒ‡å®šäº‹ä»¶ç‚¹ä¸Šå®Œæˆ�ä¸‹åˆ—ä»»åŠ¡ 
	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}
	public void goodsUseNumUpdate(){
		System.out.println(123);
		//è°ƒç”¨goodsæ¨¡å�—çš„daoæ‰§è¡Œå¯¹åº”çš„updateè¯­å�¥
		/*
		update 
			tbl_goods g
		set
			useNum =
		(
		select 
			count(goodsUuid)
		from 
			tbl_orderdetail
		where 
			goodsUuid = g.uuid
		)
		*/
		goodsDao.updateUseNum();
	}
	
	public void storeWarn(){
		try {
			
		
		//èŽ·å�–æŠ¥è­¦æ•°æ�®
		/*
		select
			gm.name,sum(sdm.num)>gm.maxNum,sum(sdm.num)<gm.minNum
		from 
			tbl_storedetail sdm,
			tbl_goods gm
		where
			gm.uuid = sdm.goodsUuid
		group by
			goodsUuid
		*/
		List<Object[]> temp = goodsDao.getStoreWarnInfo();
		//ä½¿ç”¨emailå�‘é€�æ•°æ�®åˆ°æŒ‡å®šè´¦æˆ·
		//ä½¿ç”¨mailsenderå¯¹è±¡
		SimpleMailMessage msg = new SimpleMailMessage();
		//è®¾ç½®å�‘é€�äºº
		msg.setFrom("itcast0228@126.com");
		//è®¾ç½®æŽ¥æ”¶äºº
		msg.setTo("1@126.com");
		//è®¾ç½®ä¸»é¢˜
		msg.setSubject("åº“å­˜é¢„è­¦ä¿¡æ�¯"+FormatUtil.formatDateTime(System.currentTimeMillis()));
		//è®¾ç½®æ­£æ–‡
		StringBuilder sbf = new StringBuilder();
		//æ ¹æ�®æ•°æ�®ç»„ç»‡æ­£æ–‡å†…å®¹
		for(Object[] objs:temp){
			BigInteger flag = (BigInteger) objs[1];
			if(flag.intValue() == 1){
				//è¶…è¿‡ä¸Šé™�
				sbf.append("å•†å“�ã€�"+objs[0].toString()+"ã€‘åº“å­˜è¶…è¿‡æœ€å¤§å€¼ï¼Œè¯·å�œæ­¢è¡¥è´§ï¼Œå¹¶è¿›è¡Œä¿ƒé”€ï¼�");
				sbf.append("\r\n");
				continue;
			}
			flag = (BigInteger) objs[2];
			if(flag.intValue() == 1){
				//ä½ŽäºŽä¸‹é™�
				sbf.append("å•†å“�ã€�"+objs[0].toString()+"ã€‘åº“å­˜ä¸�è¶³ï¼Œè¯·å�Šæ—¶è¡¥è´§ï¼�");
				sbf.append("\r\n");
			}
		}
		msg.setText(sbf.toString());
		//è®¾ç½®å�‘é€�æ—¶é—´
		msg.setSentDate(new Date());
		mailSender.send(msg);
		} catch (Exception e) {
			 System.out.println("Something was wrong");
		}
	}
}
