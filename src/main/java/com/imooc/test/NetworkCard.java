package com.imooc.test;

/**
 * ����ʵ��PCI�ӿڹ淶
 * @author yanerkang
 *
 */
public class NetworkCard implements PCI {

	public void start() {
		System.out.println("Send...");
	}

	public void stop() {
		System.out.println("Network stop!");
	}

}
