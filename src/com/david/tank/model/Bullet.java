package com.david.tank.model;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Bullet {
	private int position_x =0;
	private int position_y =0 ;
	private char direction = 'L';
	private String name = "";
	public String getName() {
		return name;
	}




	public void setName(String name) {
		this.name = name;
	}




	public boolean isvalid = true;
	
	// 定义墙的类型
	
	public static Image img  = new ImageIcon(Bullet.class.getResource("/images/other/bullet.gif")).getImage();
	

	public Bullet (String fromtank, int x, int y ,char direction) {
		this.position_x = x;
		this.setPosition_y(y);
		this.direction = direction;
		this.name = fromtank;
	}
	
	
	
	
	public void MoveBullet () {
		if (isvalid) {
			if (direction == 'L') {
				position_x -=10;
				if (position_x <25) {
					isvalid = false;
				}
			}
			if (direction == 'R') {
				position_x +=10;
				if (position_x >650) {
					isvalid = false;
				}
			}
			if (direction == 'U') {
				position_y -=10;
				if (position_y <25) {
					isvalid = false;
				}
			}
			if (direction == 'D') {
				position_y +=10;
				if (position_y >650) {
					isvalid = false;
				}
			}
		}
	}




	public int getPosition_x() {
		return position_x;
	}




	public void setPosition_x(int position_x) {
		this.position_x = position_x;
	}
	
	public int getPosition_y() {
		return position_y;
	}




	public void setPosition_y(int position_y) {
		this.position_y = position_y;
	}

}
