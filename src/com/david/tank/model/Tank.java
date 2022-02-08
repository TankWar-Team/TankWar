package com.david.tank.model;

import java.awt.Image;
import java.util.HashMap;


import javax.swing.ImageIcon;

public class Tank {
	// 设置基本常量
	public static final int SIZE = 36;
	public static final int STOP = 0;

	public static final int speed_x = 5;
	public static final int speed_y = 5;
	public static final int MAX_LIFE = 1;
	public static final int MAX_BLOOD = 3;
	
	
	public char dir = 'L';
	public String name = "default";
	public int position_x = 0;
	public int position_y = 0;
	public Boolean isSelf = false;
	public int target_x = 0;
	public int target_y = 0;
	// 无敌状态
	public int god = 1;
	public boolean islive;
	public int life = MAX_LIFE;
	public int blood = MAX_BLOOD;

	
	
	public static HashMap<String, Image> tankImg = new HashMap<String, Image>();
	static {
		tankImg.put("enemyD", 
				new ImageIcon(Tank.class.getResource("/images/enemy/enemyD.gif")).getImage());
		tankImg.put("enemyU",
				new ImageIcon(Tank.class.getResource("/images/enemy/enemyU.gif")).getImage());
		tankImg.put("enemyR",
				new ImageIcon(Tank.class.getResource("/images/enemy/enemyR.gif")).getImage());
		tankImg.put("enemyL",
				new ImageIcon(Tank.class.getResource("/images/enemy/enemyL.gif")).getImage());
		// 无敌效果
		tankImg.put("enemyGD",
				new ImageIcon(Tank.class.getResource("/images/enemy/enemyGD.gif")).getImage());
		tankImg.put("enemyGU",
				new ImageIcon(Tank.class.getResource("/images/enemy/enemyGU.gif")).getImage());
		tankImg.put("enemyGR",
				new ImageIcon(Tank.class.getResource("/images/enemy/enemyGR.gif")).getImage());
		tankImg.put("enemyGL",
				new ImageIcon(Tank.class.getResource("/images/enemy/enemyGL.gif")).getImage());
		
		tankImg.put("mytankD",
				new ImageIcon(Tank.class.getResource("/images/self/tankD.gif")).getImage());
		tankImg.put("mytankU",
				new ImageIcon(Tank.class.getResource("/images/self/tankU.gif")).getImage());
		tankImg.put("mytankL",		
				new ImageIcon(Tank.class.getResource("/images/self/tankL.gif")).getImage());
		tankImg.put("mytankR",
				new ImageIcon(Tank.class.getResource("/images/self/tankR.gif")).getImage());
		tankImg.put("god0", new ImageIcon(Tank.class.getResource("/images/other/god0.gif")).getImage());
	}
	

	public Tank(String name, int x, int y) {
		this.name = name;
		this.blood = MAX_BLOOD;
		this.life = MAX_LIFE;
		this.god = 0;
		this.islive = true;
		this.dir = 'L';
		this.position_x = x;
		this.position_y = y;
		this.target_x = x;
		this.target_y = y;
		
	}
	
	public Image GetImage() {
		String imgname = "";
		if (this.isSelf) {
			imgname = "mytank" + dir;
		}
		else {
			if (this.god >0) {
				imgname = "enemyG" + dir;
			}else {
				imgname = "enemy" + dir;
			}
		}
		
		return tankImg.get(imgname);
		
	}
	
	public void Move() {
	
		int xdiff = target_x - position_x;
		int ydiff = target_y - position_y; 
		if (this.dir == 'R' || this.dir == 'L') {
			ydiff =0;
			target_y = position_y/25*25;
			position_y = target_y;
		}
		if (this.dir == 'U' || this.dir == 'D') {
			xdiff =0;
			target_x = position_x/25*25;
			position_x = target_x;
		}
		
		int x = position_x;
		int y = position_y;
		if(xdiff >0) {
			x += speed_x;
		}else
		if(xdiff <0) {
			x -= speed_x;
		}else
		if(ydiff >0) {
			y += speed_y;
		}else
		if(ydiff <0) {
			y -= speed_y;
		}
		
		if (x < 25)
			x = 25;
		if (x > 625)
			x = 625;
		if (y < 25)
			y = 25;
		if (y > 625)
			y = 625;

		
	//	char item =  Map.getInstance().CheckCollide(x,y);
	//	if (item == Map.BRICK || item == Map.SEA || item == Map.STEEL) {
	//		return ; // do not move 
	//	}
		
		this.position_x = x;
		this.position_y = y;

	}

	public Tank(String name, int x, int y, int life, int blood, char direction, int god) {
		this.name = name;
		this.blood = blood;
		this.life = life;
		this.god = god;
		this.islive = true;
		this.dir = direction;
		this.position_x = x;
		this.position_y = y;		
		
	}

}
