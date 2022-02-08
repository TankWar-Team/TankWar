package com.david.tank.view;

import java.awt.Color;
import java.awt.BasicStroke;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import com.david.tank.controller.TankLogic;
import com.david.tank.model.*;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	// 负责游戏画面的绘制
	public int TANK_NUM = 0;
	public static int TANK_MAX_NUM = 5;
	public int bo = 0;
	public int kill = 0;
	public int die = 0;
	private static Image hello = new ImageIcon("images/other/hello.jpg").getImage();
	public static int GP_WIDTH = 1000;
	public static int GP_HEIGHT = 800;
	public static int timer = 0;
	
	private static String help1 = 	"UP  : Upper Key               DOWN:  Down Key\n" ;	
	private static String help2 =	"LEFT: Left key                 RIGHT:  Right Key\n" ;
    private static String help3 =	"Attack: Space";
	
	public GamePanel() {
		this.setOpaque(false);
		this.setBackground(Color.BLACK);
		this.setBounds(0, 0, GP_WIDTH, GP_HEIGHT);
		
	}

	public void paint(Graphics g) {
		// 游戏进程控制
		g.setColor(Color.black);
		g.fillRect(0, 0, GP_WIDTH, GP_HEIGHT);
		
		timer ++;
		
		if(MapModel.gamestatus == 1){
			// 打印品牌
			g.setColor(new Color(0, 200, 0));
			g.setFont(new Font("Times New Roman", Font.BOLD, 50));
			g.drawString("Welcome to Tank War", 50, GP_HEIGHT/2-50);
			g.drawString("Please Name your Tank:", 50, GP_HEIGHT/2+50);
			String tankname = MapModel.getInstance().mytank.name;
			g.setColor(new Color(200, 200, 0));
			g.drawString(tankname, 50, GP_HEIGHT/2+150);
			g.drawLine(50, GP_HEIGHT/2+150, 350, GP_HEIGHT/2+150);
			
			
			return;
		}
		
		MapModel map = MapModel.getInstance();
		map.draw(g);
		g.setColor(new Color(0, 200, 0));
		g.setFont(new Font("Times New Roman", Font.BOLD, 20));
		g.drawString(help1, 50, GP_HEIGHT-100);
		g.drawString(help2, 50, GP_HEIGHT-75);
		g.drawString(help3, 50, GP_HEIGHT-50);
	}
	


}
