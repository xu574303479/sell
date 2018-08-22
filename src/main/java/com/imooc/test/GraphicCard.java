package com.imooc.test;
/**
 * �Կ�ʵ��PCI�ӿڹ淶
 * @author yanerkang
 *
 */
public class GraphicCard  implements PCI{
	
	public void start() {
		System.out.println("Display Graphic...");
	}

	public void stop() {
		System.out.println("Display Graphic stop!");
	}

}
