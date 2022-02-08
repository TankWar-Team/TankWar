package com.david.tank.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import com.david.tank.controller.Mqtt;
import com.david.tank.controller.TankLogic;
import com.david.tank.model.*;

/**
 * 
 * 作者：蔡锡华
 * 时间：2015/09/12
 *
 */

@SuppressWarnings("serial")
public class MainFrame extends JFrame {
	//  主窗口
	private GamePanel gamePanel;
	static int ticker =0;	
	public static final int GAME_WIDTH = 1000;
	public static final int GAME_HEIGHT = 800;
	public static MainFrame mainframe; 
	
	
	public static void main(String[] args){
		new MainFrame().createFrame();
		
	   
	}


	public void createFrame() {
		mainframe = this;
		this.setTitle("Tank War created by David Qian");
		this.setIconImage(Tank.tankImg.get("mytankU"));
		this.setBackground(Color.BLACK);
		this.setLocation(120, 0);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.addWindowListener(new WindowAdapter() {
			// Add Windows Close Event
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		this.setResizable(false);
//		this.setLookAndFeel();
		
		// Add Panel to Window
		gamePanel = new GamePanel();
		this.add(gamePanel);
		this.setVisible(true);
		this.requestFocus();
		this.addKeyListener(new KeyMonitor());
		new UpdateThread().start();
	}
	
	public class UpdateThread extends Thread {
		//Flush Screen
		 
		public void run() {
			while(true) {
				ticker ++;
				//  repaint every 50 ms
				gamePanel.repaint(ticker);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void setLookAndFeel() {


		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
			SwingUtilities.updateComponentTreeUI(this);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

	}
	
	class KeyMonitor extends KeyAdapter {

		@Override
		public void keyTyped(KeyEvent e) {
			System.out.println(e.getKeyChar());
			TankLogic.KeyTyped(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			System.out.println("key pressed " + e.getKeyChar());
			TankLogic.KeyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			System.out.println("key released " + e.getKeyChar());
			TankLogic.KeyReleased(e);
		}
		
	}

}
