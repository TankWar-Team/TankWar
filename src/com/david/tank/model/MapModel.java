package com.david.tank.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.ImageIcon;

import com.david.tank.controller.TankLogic;



public class MapModel {
	public static final int WIDTH = 25;
	public static final int HEIGHT = 25;
	public static final char BRICK = '1';
	public static final char STEEL = '2';
	public static final char SEA = '3';
	public static final char ICE = '4';
	public static final char FOREST = '5';
	public static final char STAR = '6';
	public static final char TANK = '7';
	public static int gamestatus = 1;  // 1: input stage  2: game stage 	

	
	public static HashMap<Character, Image> wallImg = new HashMap<Character, Image>();
	// 初始化图片资源
	static {
		wallImg.put(BRICK,new ImageIcon(MapModel.class.getResource("/images/other/brick.gif")).getImage());
		wallImg.put(STEEL,new ImageIcon(MapModel.class.getResource("/images/other/steel.gif")).getImage());
		wallImg.put(FOREST,new ImageIcon(MapModel.class.getResource("/images/other/forest.gif")).getImage());
		wallImg.put(ICE,new ImageIcon(MapModel.class.getResource("/images/other/ice.gif")).getImage());
		wallImg.put(SEA,new ImageIcon(MapModel.class.getResource("/images/other/sea.gif")).getImage());
		wallImg.put(STAR,new ImageIcon(MapModel.class.getResource("/images/other/star.gif")).getImage());
	}

	
	public HashMap <String, Tank> tanks = new HashMap<String, Tank>();
	public ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public ArrayList<Explode> explodes = new ArrayList<Explode>();
	public Tank mytank = new Tank("", 0,0);
	
	
	private static MapModel instance;

	public char[][] wallmap = new char [WIDTH+1][HEIGHT+1];
	
	public static MapModel getInstance() {
		if (instance == null ) {
			instance = new MapModel();
		}
		return instance;
	}
	public MapModel() {
		for (int i=0; i< WIDTH; i++) {
	        	for (int j=0; j< HEIGHT ; j++) {
	        		wallmap[i][j] = '0';
	        	}
	    }
	}
	
	
	public void DestryWall(int x, int y) {
		if (wallmap[x][y] == BRICK) {
			wallmap[x][y] = '0';
		}
	}
	
	/*
	public char CheckCollide( int ox, int oy) {
		
		int nextx = (ox+24)/25 -1;
		int nexty = (oy+24)/25 -1;
		
		char item = MapModel.getInstance().wallmap[nextx][nexty];
		return item;
	}*/
	
	public void ReadMap( String fileName) {
		try {
			
			File file = new File(fileName);
	        FileReader fr = new FileReader(file);
	        BufferedReader br = new BufferedReader(fr);
	        String line;
	        System.out.println("Read text file using BufferedReader");
	        int lineid = 0;
	       
	        
	        while((line = br.readLine()) != null){
	            //process the line
	        	System.out.println(line);
	        	for( int i=0; i<WIDTH && i <line.length() ; i++) {
	        		char item = line.charAt(i) ;
	        		if (item <= '6' && item >= '0') {
	        			wallmap[i][lineid] = item;	
	        		}
	        		
	        	}
	        	lineid ++;
	        }
	        //close resources
	        br.close();
	        fr.close();
	    }catch(IOException e) {
	    	System.out.println("Can not read file:" + fileName);
	    	
	    }
	}


	public void draw(Graphics g){
		// 按照图片大小顺序画出爆炸效果
		g.setColor(new Color(0, 255, 0));
		g.drawLine(25, 25, 650, 25);
		g.drawLine(25, 25, 25, 650);
		g.drawLine(25, 650, 650, 650);
		g.drawLine(650, 25, 650, 650);
		
		for (int i=0; i< MapModel.WIDTH; i++) {
			for (int j=0; j< MapModel.HEIGHT; j++) {
				char item = wallmap[i][j];
				if (item>'0') {
					Image img =wallImg.get(item);
					if(img != null) {
						g.drawImage(img, i*25+25, j*25+25, 25, 25, null);	
					}
					
				}
				
			}
		}
		
		for (Entry<String, Tank> entry: tanks.entrySet()) {
			String name = entry.getKey();
			Tank tank = entry.getValue();
			if (tank.islive && tank.blood>0) {
				tank.Move();
				Image img = tank.GetImage();
				if(img != null) {
					if (tank.god == 1) {
						g.drawImage(tank.tankImg.get("god0"), tank.position_x, tank.position_y, 25, 25, null);
					}
					
					if (wallmap[tank.position_x/25-1][tank.position_y/25-1] == MapModel.FOREST ) {
						if (!tank.name.equals(mytank.name)) {
							continue; // do not show tank if behind tree		
						}
						
					}
					g.drawImage(img, tank.position_x, tank.position_y, 25, 25, null);	
					if(tank.blood >0) {
						g.setColor(new Color(255, 0, 0));
						int bl = 25 * tank.blood / tank.MAX_BLOOD; 
						g.drawLine(tank.position_x, tank.position_y-5, tank.position_x+bl, tank.position_y-5);
						g.drawLine(tank.position_x, tank.position_y-4, tank.position_x+bl, tank.position_y-4);
						g.drawLine(tank.position_x, tank.position_y-3, tank.position_x+bl, tank.position_y-3);
						g.setColor(new Color(255, 255, 255));
						g.drawString(tank.name, tank.position_x,tank.position_y-10);
					}
				}
			}
		}
		
		 for(Iterator<Bullet> it = bullets.iterator();it.hasNext();){
             Bullet bullet = (Bullet)it.next();
            
             bullet.MoveBullet();
             boolean collide = TankLogic.checkBulletCollide(bullet);
             if (bullet.isvalid == false || collide == true) {
            	 it.remove();   //remove bullet if collide with something or reach edge
            	 continue;
             }
             g.drawImage(bullet.img, bullet.getPosition_x(), bullet.getPosition_y(),5,5, null);
             
        }
		 
		 for(Iterator<Explode> it = explodes.iterator();it.hasNext();){
             Explode ex = (Explode)it.next();
             ex.draw(g, it); 
              
        }
		
		
		
	}
	
	public char getItem( int x, int y) {
		
		return wallmap[x][y];
	}


	
	


}
