package com.imooc.test;

/**
 * ������
 * @author yanerkang
 *
 */
public class MainBoard {

	 /**
	  * ͨ���������,�����Ͽ��Բ����κ�ʵ��PCI�ӿڹ淶�Ŀ�
	  * 
	  * @param pci ��������Ϊ�ӿ�,�κ�ʵ�ֽӿڵ��඼���Դ�����.
	  */
	public void usePCICard(PCI pci) {
		pci.start();
		pci.stop();
	}

}
