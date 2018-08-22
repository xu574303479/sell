package com.imooc.test;

public class Assembler {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		MainBoard mb=new MainBoard(); 
		//�������ϲ�������
	    PCI nc=new NetworkCard(); 
	    mb.usePCICard(nc); 
	    
	   //�������ϲ�������
	    PCI sc=new SoundCard(); 
	    mb.usePCICard(sc); 
	    
		//�������ϲ����Կ�
	    PCI gc=new GraphicCard(); 
	    mb.usePCICard(gc); 
	    
	    
	    
	    //nc.start();
	   // sc.start();


	}

}
