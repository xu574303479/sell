package com.imooc.test;

/**
 * ����ʵ��PCI�ӿڹ淶
 * @author yanerkang
 *
 */
public class SoundCard implements PCI {
	public void start() {
		System.out.println("Du du...");
	}

	public void stop() {
		System.out.println("Sound stop!");
	}
}
