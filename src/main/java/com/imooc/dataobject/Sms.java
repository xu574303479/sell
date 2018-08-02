package com.imooc.dataobject;

import java.io.Serializable;

/**
 * 阿里云短信
 * 短信模板类型：
 * 1-传值内容：captcha
 * 2-传值内容：orderName
 * 3-传值内容：orderBuyTime、orderName、orderCountDownMinute
 * @author YinRenMing
 * @date 2018年4月8日 上午11:30:00
 * @version 1.0.0
 */
public class Sms implements Serializable {

	private static final long serialVersionUID = 30291838777211111L;

	//验证码
	private int captcha;

	//订单名称
	private String orderName;

	//订单购买时间
	private String orderBuyTime;

	//一事一测倒计时时间，单位分钟
	private String orderCountDownMinute;

	public int getCaptcha() {
		return captcha;
	}

	public void setCaptcha(int captcha) {
		this.captcha = captcha;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getOrderBuyTime() {
		return orderBuyTime;
	}

	public void setOrderBuyTime(String orderBuyTime) {
		this.orderBuyTime = orderBuyTime;
	}

	public String getOrderCountDownMinute() {
		return orderCountDownMinute;
	}

	public void setOrderCountDownMinute(String orderCountDownMinute) {
		this.orderCountDownMinute = orderCountDownMinute;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
