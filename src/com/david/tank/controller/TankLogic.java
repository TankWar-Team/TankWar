package com.david.tank.controller;

import java.awt.event.KeyEvent;
import java.util.Iterator;
import java.util.Map.Entry;

import javax.swing.JOptionPane;

import com.david.tank.model.Bullet;
import com.david.tank.model.Explode;
import com.david.tank.model.MapModel;
import com.david.tank.view.GamePanel;
import com.david.tank.view.MainFrame;
import com.david.tank.model.MediaPlayer;
import com.david.tank.model.Tank;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;

public class TankLogic {
	public TankLogic() {
	}
	
	
	
	public static void fire(String name) {
		
	}
	
	
	public static void destroyWall(int x, int y) {
		
	}
	
	
	public static void destroyTank(String name) {
		
	}
	
	
	public static boolean checkBulletCollide ( Bullet bullet) {
		int x = bullet.getPosition_x()/25 -1;
		int y = bullet.getPosition_y()/25 -1;
		MapModel map = MapModel.getInstance();
		if (x <0 || y <0) {
			return false;
		}
		int item = map.wallmap[x][y];
		if ( item == MapModel.BRICK || item == MapModel.STEEL){
			System.out.println("Collide with brick / STEEL");
			map.explodes.add(new Explode(x*25+25, y*25+25, 25));
			return true;
		}
		Iterator<Entry<String, Tank>> it = map.tanks.entrySet().iterator();
		while (it.hasNext()) {
			Tank tank1 = (Tank)it.next().getValue();
			if (tank1.name.equals(bullet.getName())) {
				continue; // skip itself
			}
			if ((tank1.position_x /25 -1) == x && (tank1.position_y /25 -1) == y ) {
				//collide with tank
				System.out.printf("Collide with tank %s \n" , tank1.name);
				map.explodes.add(new Explode(x*25+25, y*25+25, 25));
				
				return true;
			}
 		}
		
		return false;
	}
	
	
	
	public static boolean checkCollide(int targetx, int targety) {
		if(targetx <0 || targety <0) {
			return false;
		}
		MapModel map = MapModel.getInstance();
		int item = map.wallmap[targetx][targety];
		if (item == MapModel.SEA || item == MapModel.BRICK || item == MapModel.STEEL) {
			System.out.printf("Collide with %c \n" , item);
			return true;
		}
		Iterator<Entry<String, Tank>> it = map.tanks.entrySet().iterator();
		while (it.hasNext()) {
			Tank tank1 = (Tank)it.next().getValue();
			if (tank1 == map.mytank) {
				continue; // skip itself
			}
			if ((tank1.position_x /25 -1) == targetx && (tank1.position_y /25 -1) == targety ) {
				//collide with tank
				System.out.printf("Collide with tank %s \n" , tank1.name);
					
				return true;
			}
 		}
		return false;
	}
	
	public static boolean checkice (int targetx, int targety) {
		if (targetx <0 || targety <0) {
			 return false;
		}
		int item = MapModel.getInstance().wallmap[targetx][targety];
		if (item == MapModel.ICE) {
			return true;
		}
		return false;
	
	}
	
	
	/*
	 * 
	 * Message Protocol for Tank game :/tank/event
	 * 1. Event   From Terminal to Server:
	 * 	{"Event":"NewTank","Name":"David"}
	 *  {"Event":"TankMove","Name":"David","Direction":"U/D/L/R", "X":"150", "Y":"150","TargetX":"150","TargetY":"150"}
	 * 	{"Event":"Fire", "Name":"David", "Direction":"U/D/L/R", "X":"150", "Y":"150"}
	 * 
	 * 2. Notify  From Server to terminal:  "/tank/notify
	 * {"Event":"SyncMap","Map":["110000000","13400000"...], "Tanks":[{"Name":"coco","Name":"David","Direction":"U/D/L/R", "X":"150", "Y":"150","TargetX":"150","TargetY":"150"}, 
	 * 		{"Name":"coco","Name":"David","Direction":"U/D/L/R", "X":"150", "Y":"150","TargetX":"150","TargetY":"150"},{"Name":"coco","Name":"David","Direction":"U/D/L/R", "X":"150", "Y":"150","TargetX":"150","TargetY":"150"}]}
	 * 
	 * {"Event":"NewTank","Name":"David","Direction":"U/D/L/R", "X":"150", "Y":"150","TargetX":"150","TargetY":"150", "Life":"3","Blood":"10", "IsGod":"true"}
	 * {"Event":"TankMove","Name":"David","Direction":"U/D/L/R", "X":"150", "Y":"150","TargetX":"150","TargetY":"150", "Life":"3","Blood":"10", "IsGod":"false"}
	 * {"Event":"Fire", "Direction":"U/D/L/R", "X":"150", "Y":"150"}
	 * {"Event":"DestroyBrick", "X":"150", "Y":"150"}
	 * {"Event":"DestroyTank", "Name":"David", "X":"150", "Y":"150", "Life":"2", "Blood":"10"}
	 * 
	 * 3. message Peer to Peer    /tank/message
	 * {"From":"David", "Message":"Hello 123"}
	 *  
	 */
	
	public static void ProcessMqttMsg(String topic, String msg) throws JSONException {
		//msg = "{\"Event\":\"TankMove\",\"Name\":\"David\",\"Direction\":\"U/D/L/R\", \"X\":\"150\", \"Y\":\"150\",\"TargetX\":\"150\",\"TargetY\":\"150\"}";
		//{
			//"Event": "SyncMap",
			//"Map": ["110000000", "13400000", "134555"]
		//}
		System.out.println("Processing message:" + topic + " " + msg);
		MapModel map = MapModel.getInstance();
		JSONObject event = new JSONObject(msg);
		if (topic.equals("/tank/event")) {
			//care tank move message
			
			
			if (event.get("Event").equals("Fire")) {
				System.out.println("FIRE!!!!!");
				String name = event.getString("Name");
				if (name.equals(map.mytank.name)) {
					return;
				}
				
				
				String direction = event.getString("Direction");
				int X = Integer.parseInt(event.getString("X"));
				int Y = Integer.parseInt(event.getString("Y"));
				
				System.out.println("Fire " + name + ","+ direction + "," + X + "," + Y);
				Bullet bullet = new Bullet(name, X, Y, direction.charAt(0));
				map.bullets.add(bullet);
			}
		}
		
		if (topic.equals("/tank/notify")) {
			if (event.getString("Event").equals("SyncMap")) {
				System.out.println("Find SyncMap");
			    JSONArray maparr = event.getJSONArray("Map");
			    if (maparr != null ) {
			    	for (int i=0; i<maparr.length();i++) {
			    		String value = (String) maparr.get(i);
			    		System.out.println(value);
			    		for( int j=0; j<MapModel.WIDTH && i <value.length() ; j++) {
			        		char item = value.charAt(j) ;
			        		if (item <= '6' && item >= '0') {
			        			map.wallmap[i][j] = item;	
			        		}
			        		
			        	}
			    		//System.out.println(value);
			    	}
			    }
			    JSONArray tanks = event.getJSONArray("Tanks");
			    for (int i=0; i< tanks.length(); i++) {
			    	JSONObject tank =tanks.getJSONObject(i);
			    	String name = tank.getString("Name");
					String direction = tank.getString("Direction");
					int X = Integer.parseInt(tank.getString("X"));
					int Y = Integer.parseInt(tank.getString("Y"));
					int targetX = Integer.parseInt(tank.getString("TargetX"));
					int targetY = Integer.parseInt(tank.getString("TargetY"));
					int Life = Integer.parseInt(tank.getString("Life"));
					int Blood = Integer.parseInt(tank.getString("Blood"));
					int God = Integer.parseInt(tank.getString("God"));
					Tank newTank = new Tank(name, X, Y);
					if (name.equals(map.mytank.name)) {
						// this is self
						newTank = map.mytank;
						newTank.isSelf = true;
					}
					newTank.blood = Blood;
					newTank.dir = direction.charAt(0);
					newTank.god = God;
					newTank.position_x = X;
					newTank.position_y = Y;
					newTank.target_x = targetX;
					newTank.target_y = targetY;
					newTank.life = Life;
					map.tanks.put(name, newTank);
			    }
			    
				
			}
			if (event.get("Event").equals("TankMove")) {
				String name = event.getString("Name");
				if (name.equals(map.mytank.name)) {
					// this is my tank, igonre move event
					return;
				}
				try {
					String direction = event.getString("Direction");
					int X = Integer.parseInt(event.getString("X"));
					int Y = Integer.parseInt(event.getString("Y"));
					int targetX = Integer.parseInt(event.getString("TargetX"));
					int targetY = Integer.parseInt(event.getString("TargetY"));
					
					int Life = Integer.parseInt(event.getString("Life"));
					int Blood = Integer.parseInt(event.getString("Blood"));
					int God = Integer.parseInt(event.getString("God"));
					
					Tank tank = map.tanks.get(name);
					if (tank != null) {
						tank.blood = Blood;
						tank.life = Life;
						tank.position_x = X;
						tank.position_y = Y;
						tank.target_x = targetX;
						tank.target_y = targetY;
						tank.dir = direction.charAt(0);
						tank.god = God;
						map.tanks.put(name, tank);	
					}else {
						System.out.println("can not find tank" + name);
					}
		
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
								
			}
			if (event.getString("Event").equals("DestroyBrick")) {
				int X = Integer.parseInt(event.getString("X"))/25-1;
				int Y = Integer.parseInt(event.getString("Y"))/25-1;
				if (X <=0 || Y<=0) {
					return;
				}
				map.wallmap[X][Y] = '0'; 
				
			}
			
			if (event.get("Event").equals("DestroyTank")) {
				String name = event.getString("Name");
				
				try {
					
					int Life = Integer.parseInt(event.getString("Life"));
					int Blood = Integer.parseInt(event.getString("Blood"));
					int God = Integer.parseInt(event.getString("God"));
					if (Life == 0 ) {
						map.tanks.remove(name);  // remove tank
						if (name.equals(map.mytank.name)) {
							// my tank destroied!
							System.out.println("My tank destroied!");
							map.mytank.life = 0;
							map.mytank.islive = false;
							JOptionPane.showMessageDialog(MainFrame.mainframe,"YOU ARE KILLED!!!!");
							//return;
						}
					}
					else {
						Tank tank = map.tanks.get(name);
						if (tank != null) {
							tank.blood = Blood;
							tank.life = Life;
							tank.god = God;
							map.tanks.put(name, tank);
							System.out.println("put tank " + name + " X:" + tank.position_x + " Y:" + tank.position_y);
						}else {
							System.out.println("can not find tank" + name);
						}
					}
					
		
				}catch (Exception e) {
					System.out.println(e.getMessage());
				}
				
								
			}
			
		}
		
		if (topic.equals("/tank/message")) {
			
		}
		
	}
	public static void KeyPressed(KeyEvent e) {
		// 按下键盘不放时
		MapModel map = MapModel.getInstance();
		Tank mytank= map.mytank;
		int key = e.getKeyCode();
		Mqtt mqtt = null;
		
			
		if (map.gamestatus == 1) {
			// input stage
			
			if( (key <= KeyEvent.VK_9 && key >= KeyEvent.VK_0) || (key <= KeyEvent.VK_Z && key >= KeyEvent.VK_A) ) {
				char keychar = e.getKeyChar();
				if (mytank.name.length() <10) {
					mytank.name = mytank.name + keychar;	
				}
				
			}
			if (key == KeyEvent.VK_BACK_SPACE) {
				mytank.name = "";
			}
		
			if (key == KeyEvent.VK_ENTER) {
				
				new Thread(new Runnable(){
					public void run() {
						MediaPlayer.play(MediaPlayer.START);
					}
				}).start();

				mqtt = Mqtt.getInstance(mytank.name);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				mqtt.SendMessage("/tank/event", " {\"Event\":\"NewTank\",\"Name\":\"" + mytank.name + "\"}");
				
				map.gamestatus = 2;
				
			}
			
		}else {
			if (mytank.islive == false || mytank.life ==0) {
				return;
			}
			switch (key) {
			case KeyEvent.VK_F1:
				break;
			case KeyEvent.VK_F2: 
			
				break;
//			case KeyEvent.VK_R: 
			case KeyEvent.VK_CONTROL: {
				if (mytank.islive && GamePanel.timer > 60) {
					Bullet bull = new Bullet(mytank.name, mytank.position_x+12,mytank.position_y+12, mytank.dir);
					map.bullets.add(bull);
					MediaPlayer.play(MediaPlayer.FIRE);
					mqtt = Mqtt.getInstance(mytank.name);
					mqtt.SendMessage("/tank/event", " {\"Event\":\"Fire\",\"Name\":\"" + mytank.name + "\",\"Direction\":\"" +String.valueOf( mytank.dir) + "\", \"X\":\"" +( mytank.position_x +12 )+ "\", \"Y\":\"" + (mytank.position_y + 12) + "\"}");
							
				}
			}
				break;
			case KeyEvent.VK_UP:
				mytank.dir = 'U';
				mytank.target_y = (mytank.position_y/25 -1) * 25 ;
				break;
			case KeyEvent.VK_DOWN:
				mytank.dir = 'D';
				mytank.target_y = (mytank.position_y/25 + 1) * 25 ;
				break;
			case KeyEvent.VK_LEFT:
				mytank.dir = 'L';
				mytank.target_x = (mytank.position_x/25 -1) * 25 ;
				break;
			case KeyEvent.VK_RIGHT:
				mytank.dir = 'R';
				mytank.target_x = (mytank.position_x/25 + 1) * 25 ;
				break;
			}
			
			if (checkice(mytank.target_x/25-1, mytank.target_y/25-1) ) {
				switch (key) {
				case KeyEvent.VK_UP:
					while (MapModel.getInstance().wallmap[mytank.target_x/25-1][mytank.target_y/25-1] == MapModel.ICE) {
						mytank.target_y -= 25;
					}
					break;
				case KeyEvent.VK_DOWN:
					while (MapModel.getInstance().wallmap[mytank.target_x/25-1][mytank.target_y/25-1] == MapModel.ICE) {
						mytank.target_y += 25;
					}
					break;
				case KeyEvent.VK_LEFT:
					while (MapModel.getInstance().wallmap[mytank.target_x/25-1][mytank.target_y/25-1] == MapModel.ICE) {
						mytank.target_x -= 25;
					}
					break;
				case KeyEvent.VK_RIGHT:
					while (MapModel.getInstance().wallmap[mytank.target_x/25-1][mytank.target_y/25-1] == MapModel.ICE) {
						mytank.target_x += 25;
					}
					break;
						
				}
			}
			if( checkCollide(mytank.target_x/25-1, mytank.target_y/25-1) ) {
				mytank.target_x = mytank.position_x/25 * 25;
				mytank.target_y = mytank.position_y/25 * 25;
			}else {
			}
			
			if (key ==KeyEvent.VK_UP ||key ==KeyEvent.VK_DOWN ||key ==KeyEvent.VK_LEFT ||key ==KeyEvent.VK_RIGHT  ) {
				mqtt = Mqtt.getInstance(mytank.name);
				mqtt.SendMessage("/tank/event", " {\"Event\":\"TankMove\",\"Name\":\"" + mytank.name + "\",\"Direction\":\"" +String.valueOf( mytank.dir) + "\", \"X\":\"" + mytank.position_x + "\", \"Y\":\"" + mytank.position_y + "\",\"TargetX\":\"" + mytank.target_x + "\",\"TargetY\":\"" + mytank.target_y + "\"}");
				System.out.printf("target X:%d y:%d", mytank.target_x, mytank.target_y);						
			}
			
		}
		
				
	
		
	}

	public static void KeyReleased(KeyEvent e) {
		// 松开键盘时
		int key = e.getKeyCode();
		switch (key) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_RIGHT:
			break;
		}
	}
	
	public static void KeyTyped(KeyEvent e) {
		MapModel map = MapModel.getInstance();
		if (map.gamestatus == 1) {
			 
		}
		
	}

}
