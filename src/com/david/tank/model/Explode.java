package com.david.tank.model;

import java.awt.Graphics;
import java.awt.Image;
import java.util.Iterator;

import javax.swing.ImageIcon;

public class Explode {
	int x,y;
	// λ����Ϣ
	private int step = 0;
	private int eSize;
	
	public static Image[] imgs = {
		new ImageIcon(MapModel.class.getResource("/images/other/blast1.gif")).getImage(),
		new ImageIcon(MapModel.class.getResource("/images/other/blast2.gif")).getImage(),
		new ImageIcon(MapModel.class.getResource("/images/other/blast3.gif")).getImage(),
		new ImageIcon(MapModel.class.getResource("/images/other/blast4.gif")).getImage(),
		new ImageIcon(MapModel.class.getResource("/images/other/blast5.gif")).getImage(),
		new ImageIcon(MapModel.class.getResource("/images/other/blast6.gif")).getImage(),
		new ImageIcon(MapModel.class.getResource("/images/other/blast7.gif")).getImage(),
		new ImageIcon(MapModel.class.getResource("/images/other/blast5.gif")).getImage(),
		new ImageIcon(MapModel.class.getResource("/images/other/blast2.gif")).getImage(),
		new ImageIcon(MapModel.class.getResource("/images/other/blast6.gif")).getImage()
	};
	
	public Explode(int x,int y,int size){
		// 
		this.x = x;
		this.y = y;
		this.eSize = size;
		//MediaPlayer.play(MediaPlayer.BLAST);
	}
	
	public void draw(Graphics g, Iterator it){
		// ����ͼƬ��С˳�򻭳���ըЧ��
		if(this.step < Explode.imgs.length){
			g.drawImage(Explode.imgs[step], x, y, this.eSize, this.eSize, null);
		}else{
			it.remove();
		}
		
		step ++;
	}
	
	
}
