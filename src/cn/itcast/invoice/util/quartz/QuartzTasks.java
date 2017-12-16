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
	//要求指定事件点上完成下列任务 
	public void setGoodsDao(GoodsDao goodsDao) {
		this.goodsDao = goodsDao;
	}
	public void goodsUseNumUpdate(){
		System.out.println(123);
		//调用goods模块的dao执行对应的update语句
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
			
		
		//获取报警数据
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
		//使用email发送数据到指定账户
		//使用mailsender对象
		SimpleMailMessage msg = new SimpleMailMessage();
		//设置发送人
		msg.setFrom("itcast0228@126.com");
		//设置接收人
		msg.setTo("1@126.com");
		//设置主题
		msg.setSubject("库存预警信息"+FormatUtil.formatDateTime(System.currentTimeMillis()));
		//设置正文
		StringBuilder sbf = new StringBuilder();
		//根据数据组织正文内容
		for(Object[] objs:temp){
			BigInteger flag = (BigInteger) objs[1];
			if(flag.intValue() == 1){
				//超过上限
				sbf.append("商品【"+objs[0].toString()+"】库存超过最大值，请停止补货，并进行促销！");
				sbf.append("\r\n");
				continue;
			}
			flag = (BigInteger) objs[2];
			if(flag.intValue() == 1){
				//低于下限
				sbf.append("商品【"+objs[0].toString()+"】库存不足，请及时补货！");
				sbf.append("\r\n");
			}
		}
		msg.setText(sbf.toString());
		//设置发送时间
		msg.setSentDate(new Date());
		mailSender.send(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
