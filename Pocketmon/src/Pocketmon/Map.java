package Pocketmon;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Map { 
	private static Map mapInstance;
	
	public static int MOVE_ABLE = 1;
	public static int MOVE_UNABLE = 0;
	public static int SPEED = 5;
	public static int DOOR_INDEX= 0;
	
	public static int ROOM_NUM=1;
	public static int TOWN_NUM=2;	
	public static int MOHYUN_NUM=3;
	public static int CAMPUS_NUM=4;
	public static int BUILDING_NUM=5;
	
	public static int MOHYUN_HALF_NUM =10;
	public static int CAMPUS_HALF_NUM =11;
	

	public int mapState;
	
	public Room room;
	public Town town;
	public Mohyun mohyun;
	public Campus campus;
	public Building building;
	private Map() throws IOException {
		// TODO Auto-generated constructor stub
		room=new Room();
		town=new Town();
		campus=new Campus();
		mohyun=new Mohyun();
		building = new Building();
		
		//Default map number
		mapState=ROOM_NUM;
	}	
	
	public static Map getInstance(){
		if(mapInstance==null){
			try{
				mapInstance=new Map();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return mapInstance;
	}
	
	public class Building {
		static final int START_X_POS = -2699;
		static final int START_Y_POS = -951;

		//バウンディングボックスstate
		private int upMoveState = 1; 
		private int downMoveState = 1; 
		private int leftMoveState = 1; 
		private int rightMoveState = 1; 
		//キャラクターのムーブイベントで座標が決まる。
		private int x_pos = START_X_POS;
		private int y_pos = START_Y_POS;
		private BufferedImage building;
		
		
		private BoundingBox	door;	//나오는 문
		private BoundingBox box;
		private BoundingBox doorUpWall;
		private BoundingBox leftStairDownFlower1;
		private BoundingBox rightStairDownFlower1;
		private BoundingBox rightStairDownFlower2;
		private BoundingBox rightStairDownFlower3;
		private BoundingBox buildingDownWall;
		private BoundingBox upClassUpWall;
		private BoundingBox upClassLeftWall;
		private BoundingBox upClassRightWall;
		private BoundingBox upClassDownLeftWall;
		private BoundingBox upClassDownRightWall;
		private BoundingBox upClassBox;
		private BoundingBox upClassEquip;
		private BoundingBox upClassProfDesk;
		private BoundingBox upClassStudDesk1;
		private BoundingBox upClassStudDesk2;
		private BoundingBox upClassStudDesk3;
		private BoundingBox upClassStudDesk4;
		private BoundingBox leftClass1UpWall;
		private BoundingBox leftClass1LeftWall;
		private BoundingBox leftClass1RightWall;
		private BoundingBox leftClass1DownWall;
		private BoundingBox leftClass1ProfDesk;
		private BoundingBox leftClass1StudDesk1;
		private BoundingBox leftClass1StudDesk2;
		private BoundingBox leftClass1StudDesk3;
		private BoundingBox leftClass1StudDesk4;
		private BoundingBox leftClass1Equip;		
		private BoundingBox leftClass2RightWall;
		private BoundingBox leftClass2ownWall;
		private BoundingBox leftClass2ProfDesk;
		private BoundingBox leftClass2StudDesk1;
		private BoundingBox leftClass2StudDesk2;
		private BoundingBox leftClass2StudDesk3;
		private BoundingBox leftClass2StudDesk4;
		private BoundingBox leftClass2Equip;
		private BoundingBox rightClassUpWall;
		private BoundingBox rightClassRightWall;
		private BoundingBox rightClassLeftWall1;		
		private BoundingBox rightClassLeftWall2;
		private BoundingBox rightClassProfDesk;
		private BoundingBox rightClassStudDesk1;
		private BoundingBox rightClassStudDesk2;
		private BoundingBox rightClassStudDesk3;
		private BoundingBox rightClassStudDesk4;
		private BoundingBox rightClassEquip;
		private BoundingBox leftDownStairLeftWall;
		private BoundingBox leftDownStairRightWall;
		private BoundingBox leftDownLeftStair;
		private BoundingBox rightCenterStairUpWall;
		private BoundingBox rightCenterStairLeftWall;
		private BoundingBox rightStairRightStair;
		private BoundingBox rightStairRightWall;
		private BoundingBox enterFlower1;
		private BoundingBox enterFlower2;
		private BoundingBox enterFlower3;
		private BoundingBox enterFlower4;
		private BoundingBox enterFlower5;
		private BoundingBox enterFlower6;
		private BoundingBox enterFlower7;
		private BoundingBox enterFlower8;
		private BoundingBox enterFlower9;
		private BoundingBox centerUpFlower1;
		private BoundingBox centerUpFlower2;
		private BoundingBox centerDownFlower1;
		private BoundingBox centerDownFlower2;
		private BoundingBox centerDownFlower3;		
		private BoundingBox centerFlowerUpWall;
		private BoundingBox centerFlowerLeftWall;
		private BoundingBox centerFlowerRightWall;
		private BoundingBox centerFlowerDownLeftWall;
		private BoundingBox centerFlowerDownRightWall;
		private BoundingBox centerFlowerLeftFence;
		private BoundingBox centerFlowerRightFence;
		private BoundingBox centerFlowerDownLeftFence;
		private BoundingBox centerFlowerDownRightFence;		
		private BoundingBox centerFlowerSmallWood1;
		private BoundingBox centerFlowerSmallWood2;
		private BoundingBox centerFlowerSmallWood3;
		private BoundingBox centerFlowerSmallWood4;
		private BoundingBox centerFlowerSmallWood5;
		private BoundingBox centerFlowerSmallWood6;
		private BoundingBox centerFlowerSmallWood7;
		private BoundingBox centerFlowerSmallWood8;
		private BoundingBox centerFlowerChair1;
		private BoundingBox centerFlowerChair2;
		private BoundingBox centerFlowerDesk;
		private BoundingBox centerFlowerWood1;
		private BoundingBox centerFlowerWood2;
		private BoundingBox centerFlowerWood3;
		private BoundingBox centerFlowerWood4;
		private ArrayList<BoundingBox> boundList;
		
		public Building() throws IOException {
			building = ImageIO.read(new File(".\\img\\map\\building.png"));		
			door= new BoundingBox(x_pos+ 3056 , y_pos+ 1120 ,16 ,160 );	//나오는 문
			box= new BoundingBox(x_pos+ 2112 , y_pos+ 960 ,128 ,96 );
			doorUpWall= new BoundingBox(x_pos+ 3008 , y_pos+ 988 ,64 ,128 );
			leftStairDownFlower1= new BoundingBox(x_pos+ 192 , y_pos+ 2992 ,64 ,112 );
			rightStairDownFlower1= new BoundingBox(x_pos+ 2176 , y_pos+ 1712 ,64 ,112 );
			rightStairDownFlower2= new BoundingBox(x_pos+ 2304 , y_pos+ 1712 ,64 ,112 );
			rightStairDownFlower3= new BoundingBox(x_pos+ 2816 , y_pos+ 1840 ,64 ,112 );
			buildingDownWall= new BoundingBox(x_pos+ 0 , y_pos+ 3104 ,3072 ,32 );
			upClassUpWall= new BoundingBox(x_pos+ 928 , y_pos+0  ,1216 , 160);
			upClassLeftWall= new BoundingBox(x_pos+ 928 , y_pos+ 160 ,32 ,672 );
			upClassRightWall= new BoundingBox(x_pos+ 2112 , y_pos+ 160 , 32,678 );
			upClassDownLeftWall= new BoundingBox(x_pos+ 832 , y_pos+832  , 640, 160);
			upClassDownRightWall= new BoundingBox(x_pos+ 1600 , y_pos+ 832 , 1408,160 );			
			upClassBox= new BoundingBox(x_pos+ 960 , y_pos+108  ,128 ,116 );
			upClassEquip= new BoundingBox(x_pos+ 1984 , y_pos+ 108 ,128 ,88 );
			upClassProfDesk= new BoundingBox(x_pos+ 1440 , y_pos+ 236 ,192 ,88 );
			upClassStudDesk1= new BoundingBox(x_pos+ 1088 , y_pos+ 428 ,384 ,80 );
			upClassStudDesk2= new BoundingBox(x_pos+  1600, y_pos+ 428 ,384 ,80 );
			upClassStudDesk3= new BoundingBox(x_pos+ 1088 , y_pos+ 620 ,384 ,80 );
			upClassStudDesk4= new BoundingBox(x_pos+ 1600 , y_pos+ 620 ,384 ,80 );			
			leftClass1UpWall= new BoundingBox(x_pos+ 0 , y_pos+ 672 , 864, 128);
			leftClass1LeftWall= new BoundingBox(x_pos+ 0 , y_pos+ 800 ,64 ,2336 );
			leftClass1RightWall= new BoundingBox(x_pos+ 832 , y_pos+ 1088 ,128 , 928);
			leftClass1DownWall= new BoundingBox(x_pos+ 64 , y_pos+ 1664 , 768,160 );
			leftClass1ProfDesk= new BoundingBox(x_pos+ 352 , y_pos+ 912 ,192 ,88 );
			leftClass1StudDesk1= new BoundingBox(x_pos+ 192 , y_pos+ 1132 ,192 ,128 );
			leftClass1StudDesk2= new BoundingBox(x_pos+ 512 , y_pos+ 1132 ,192 ,128 );
			leftClass1StudDesk3= new BoundingBox(x_pos+ 192 , y_pos+ 1388 ,192 ,128 );
			leftClass1StudDesk4= new BoundingBox(x_pos+ 512 , y_pos+ 1388 ,192 ,128 );
			leftClass1Equip= new BoundingBox(x_pos+ 704 , y_pos+ 744 ,132 , 88);				
			leftClass2RightWall= new BoundingBox(x_pos+  832, y_pos+ 2112 ,128 ,864 );
			leftClass2ownWall= new BoundingBox(x_pos+  64, y_pos+ 2688 ,768 , 160);
			leftClass2ProfDesk= new BoundingBox(x_pos+ 352 , y_pos+ 1936 ,192 , 88);
			leftClass2StudDesk1= new BoundingBox(x_pos+ 192 , y_pos+  2156, 192,128 );
			leftClass2StudDesk2= new BoundingBox(x_pos+ 512 , y_pos+ 2156 ,192 ,128 );
			leftClass2StudDesk3= new BoundingBox(x_pos+192  , y_pos+ 2412 ,192 ,128 );
			leftClass2StudDesk4= new BoundingBox(x_pos+ 512 , y_pos+ 2412 ,192 ,128 );
			leftClass2Equip= new BoundingBox(x_pos+  704, y_pos+ 1768 ,132 ,88 );			
			rightClassUpWall= new BoundingBox(x_pos+ 2112 , y_pos+ 1984 ,896 ,288 );
			rightClassRightWall= new BoundingBox(x_pos+ 3008 , y_pos+ 1280 ,64 , 1856);
			rightClassLeftWall1= new BoundingBox(x_pos+ 2112 , y_pos+ 2272 ,128 ,192 );		
			rightClassLeftWall2= new BoundingBox(x_pos+ 2112 , y_pos+ 2560 ,128 ,576 );
			rightClassProfDesk= new BoundingBox(x_pos+ 2528 , y_pos+ 2384 , 192, 88);
			rightClassStudDesk1= new BoundingBox(x_pos+ 2368 , y_pos+ 2604 ,192 ,128 );
			rightClassStudDesk2= new BoundingBox(x_pos+ 2688 , y_pos+ 2604 ,192 ,128 );
			rightClassStudDesk3= new BoundingBox(x_pos+ 2368 , y_pos+ 2860 ,192 , 128);
			rightClassStudDesk4= new BoundingBox(x_pos+ 2688 , y_pos+ 2860 , 192,128 );
			rightClassEquip= new BoundingBox(x_pos+ 2880 , y_pos+ 2216 , 132,88 );			
			leftDownStairLeftWall= new BoundingBox(x_pos+ 64 , y_pos+ 2848 ,128 ,288 );
			leftDownStairRightWall= new BoundingBox(x_pos+500  , y_pos+ 2848 , 332,128 );
			leftDownLeftStair= new BoundingBox(x_pos+ 192 , y_pos+ 2848 ,136 ,128 );			
			rightCenterStairUpWall= new BoundingBox(x_pos+ 2572 , y_pos+ 1440 , 172, 192);
			rightCenterStairLeftWall= new BoundingBox(x_pos+  2112, y_pos+1440  ,640 ,320 );
			rightStairRightStair= new BoundingBox(x_pos+ 2744 , y_pos+ 1440 , 136, 320);
			rightStairRightWall= new BoundingBox(x_pos+ 2880 , y_pos+ 1440 ,128 ,544 );			
			enterFlower1= new BoundingBox(x_pos+ 2368 , y_pos+ 944 , 64, 112);
			enterFlower2= new BoundingBox(x_pos+ 2560 , y_pos+ 944 , 64, 112);
			enterFlower3= new BoundingBox(x_pos+2752 , y_pos+ 944 ,64 ,112 );
			enterFlower4= new BoundingBox(x_pos+ 2944 , y_pos+944  ,64 ,112 );			
			enterFlower5= new BoundingBox(x_pos+ 2176 , y_pos+ 1328 ,64 ,112 );			
			enterFlower6= new BoundingBox(x_pos+  2368, y_pos+ 1328 ,64 ,112 );
			enterFlower7= new BoundingBox(x_pos+ 2560 , y_pos+ 1328 , 64, 112);
			enterFlower8= new BoundingBox(x_pos+ 2752 , y_pos+ 1328 ,64 , 112);
			enterFlower9= new BoundingBox(x_pos+ 2944 , y_pos+ 1328 ,64 ,112 );			
			centerUpFlower1= new BoundingBox(x_pos+ 1344 , y_pos+ 944 ,64 ,112 );
			centerUpFlower2= new BoundingBox(x_pos+  1664, y_pos+ 944 ,64 , 112);			
			centerDownFlower1= new BoundingBox(x_pos+1280  , y_pos+ 2800 ,64 ,112 );
			centerDownFlower2= new BoundingBox(x_pos+ 1728 , y_pos+ 2800 ,64 ,112);
			centerDownFlower3= new BoundingBox(x_pos+ 2048 , y_pos+ 2992 , 64, 112);				
			centerFlowerUpWall= new BoundingBox(x_pos+ 1216 , y_pos+ 1248 ,640 ,228 );
			centerFlowerLeftWall= new BoundingBox(x_pos+1216  , y_pos+ 1476 ,64 ,1372 );
			centerFlowerRightWall= new BoundingBox(x_pos+ 1792 , y_pos+1476  , 64,1372 );
			centerFlowerDownLeftWall= new BoundingBox(x_pos+ 1280 , y_pos+2688  ,192 , 160);
			centerFlowerDownRightWall= new BoundingBox(x_pos+ 1600 , y_pos+ 2688 ,192 , 160);
			centerFlowerLeftFence= new BoundingBox(x_pos+ 1288 , y_pos+1476  ,16 , 264);
			centerFlowerRightFence= new BoundingBox(x_pos+ 1768 , y_pos+ 1476 ,16 ,264 );
			centerFlowerDownLeftFence= new BoundingBox(x_pos+ 1284 , y_pos+ 1740 ,124 ,52 );
			centerFlowerDownRightFence= new BoundingBox(x_pos+ 1536 , y_pos+ 1740 ,252 ,52 );			
			centerFlowerSmallWood1= new BoundingBox(x_pos+ 1280 , y_pos+ 1824 , 64, 64);
			centerFlowerSmallWood2= new BoundingBox(x_pos+ 1280 , y_pos+ 2052 ,64 , 64);
			centerFlowerSmallWood3= new BoundingBox(x_pos+1280  , y_pos+  2272,64 , 64);
			centerFlowerSmallWood4= new BoundingBox(x_pos+1280  , y_pos+  2496, 64, 64);
			centerFlowerSmallWood5= new BoundingBox(x_pos+ 1732 , y_pos+ 1824 ,64,64 );
			centerFlowerSmallWood6= new BoundingBox(x_pos+ 1732 , y_pos+ 2052 ,64 , 64);
			centerFlowerSmallWood7= new BoundingBox(x_pos+ 1732 , y_pos+ 2272 ,64 ,64 );
			centerFlowerSmallWood8= new BoundingBox(x_pos+ 1732 , y_pos+ 2496 ,64 ,64 );			
			centerFlowerChair1= new BoundingBox(x_pos+ 1532 , y_pos+ 1820 ,128 ,28 );
			centerFlowerChair2= new BoundingBox(x_pos+ 1728 , y_pos+ 1920 ,24 , 108);
			centerFlowerDesk= new BoundingBox(x_pos+ 1536 , y_pos+ 1920 ,120 , 100);
			centerFlowerWood1= new BoundingBox(x_pos+ 1608 , y_pos+ 2120 ,48 , 180);
			centerFlowerWood2= new BoundingBox(x_pos+ 1568 , y_pos+ 2160 , 128, 140);			
			centerFlowerWood3= new BoundingBox(x_pos+ 1608 , y_pos+2400 ,48 , 180);
			centerFlowerWood4= new BoundingBox(x_pos+ 1568 , y_pos+ 2440 ,128 ,140 );
			
			
			boundList = new ArrayList<>();
			
			boundList.add(door);	//나오는 문
			boundList.add(box);
			boundList.add(doorUpWall);
			boundList.add(leftStairDownFlower1);
			boundList.add(rightStairDownFlower1);
			boundList.add(rightStairDownFlower2);
			boundList.add(rightStairDownFlower3);
			boundList.add(buildingDownWall);
			boundList.add(upClassUpWall);
			boundList.add(upClassLeftWall);
			boundList.add(upClassRightWall);
			boundList.add(upClassDownLeftWall);
			boundList.add(upClassDownRightWall);
			boundList.add(upClassBox);
			boundList.add(upClassEquip);
			boundList.add(upClassProfDesk);
			boundList.add(upClassStudDesk1);
			boundList.add(upClassStudDesk2);
			boundList.add(upClassStudDesk3);
			boundList.add(upClassStudDesk4);
			
			boundList.add(leftClass1UpWall);
			boundList.add(leftClass1LeftWall);
			boundList.add(leftClass1RightWall);
			boundList.add(leftClass1DownWall);
			boundList.add(leftClass1ProfDesk);
			boundList.add(leftClass1StudDesk1);
			boundList.add(leftClass1StudDesk2);
			boundList.add(leftClass1StudDesk3);
			boundList.add(leftClass1StudDesk4);
			boundList.add(leftClass1Equip);		
			boundList.add(leftClass2RightWall);
			boundList.add(leftClass2ownWall);
			boundList.add(leftClass2ProfDesk);
			boundList.add(leftClass2StudDesk1);
			boundList.add(leftClass2StudDesk2);
			boundList.add(leftClass2StudDesk3);
			boundList.add(leftClass2StudDesk4);
			boundList.add(leftClass2Equip);
			boundList.add(rightClassUpWall);
			boundList.add(rightClassRightWall);
			boundList.add(rightClassLeftWall1);		
			boundList.add(rightClassLeftWall2);
			boundList.add(rightClassProfDesk);
			boundList.add(rightClassStudDesk1);
			boundList.add(rightClassStudDesk2);
			boundList.add(rightClassStudDesk3);
			boundList.add(rightClassStudDesk4);
			boundList.add(rightClassEquip);
			boundList.add(leftDownStairLeftWall);
			boundList.add(leftDownStairRightWall);
			boundList.add(leftDownLeftStair);
			boundList.add(rightCenterStairUpWall);
			boundList.add(rightCenterStairLeftWall);
			boundList.add(rightStairRightStair);
			boundList.add(rightStairRightWall);
			boundList.add(enterFlower1);
			boundList.add(enterFlower2);
			boundList.add(enterFlower3);
			boundList.add(enterFlower4);
			boundList.add(enterFlower5);
			boundList.add(enterFlower6);
			boundList.add(enterFlower7);
			boundList.add(enterFlower8);
			boundList.add(enterFlower9);
			boundList.add(centerUpFlower1);
			boundList.add(centerUpFlower2);
			boundList.add(centerDownFlower1);
			boundList.add(centerDownFlower2);
			boundList.add(centerDownFlower3);		
			boundList.add(centerFlowerUpWall);
			boundList.add(centerFlowerLeftWall);
			boundList.add(centerFlowerRightWall);
			boundList.add(centerFlowerDownLeftWall);
			boundList.add(centerFlowerDownRightWall);
			boundList.add(centerFlowerLeftFence);
			boundList.add(centerFlowerRightFence);
			boundList.add(centerFlowerDownLeftFence);			
			boundList.add(centerFlowerDownRightFence);		
			boundList.add(centerFlowerSmallWood1);
			boundList.add(centerFlowerSmallWood2);
			boundList.add(centerFlowerSmallWood3);
			boundList.add(centerFlowerSmallWood4);
			boundList.add(centerFlowerSmallWood5);
			boundList.add(centerFlowerSmallWood6);
			boundList.add(centerFlowerSmallWood7);
			boundList.add(centerFlowerSmallWood8);
			boundList.add(centerFlowerChair1);
			boundList.add(centerFlowerChair2);
			boundList.add(centerFlowerDesk);
			boundList.add(centerFlowerWood1);
			boundList.add(centerFlowerWood2);			
			boundList.add(centerFlowerWood4);
			boundList.add(centerFlowerWood3);
			
		}

		public BufferedImage getBufferedImg() {
			return building;
		}

		public ArrayList<BoundingBox> getBoundList() {
			return boundList;
		}

		// 下の四つのメソッドによってバウンディングボックスも一緒に動く。
		public void moveBounding() {
			
			door.setBoundingBox(x_pos+ 3056 , y_pos+ 1120 ,16 ,160 );	//나오는 문
			box.setBoundingBox(x_pos+ 2112 , y_pos+ 960 ,128 ,96 );
			doorUpWall.setBoundingBox(x_pos+ 3008 , y_pos+ 988 ,64 ,128 );
			leftStairDownFlower1.setBoundingBox(x_pos+ 192 , y_pos+ 2992 ,64 ,112 );
			rightStairDownFlower1.setBoundingBox(x_pos+ 2176 , y_pos+ 1712 ,64 ,112 );
			rightStairDownFlower2.setBoundingBox(x_pos+ 2304 , y_pos+ 1712 ,64 ,112 );
			rightStairDownFlower3.setBoundingBox(x_pos+ 2816 , y_pos+ 1840 ,64 ,112 );
			buildingDownWall.setBoundingBox(x_pos+ 0 , y_pos+ 3104 ,3072 ,32 );
			upClassUpWall.setBoundingBox(x_pos+ 928 , y_pos+0  ,1216 , 160);
			upClassLeftWall.setBoundingBox(x_pos+ 928 , y_pos+ 160 ,32 ,672 );
			upClassRightWall.setBoundingBox(x_pos+ 2112 , y_pos+ 160 , 32,678 );
			upClassDownLeftWall.setBoundingBox(x_pos+ 832 , y_pos+832  , 640, 160);
			upClassDownRightWall.setBoundingBox(x_pos+ 1600 , y_pos+ 832 , 1408,160 );			
			upClassBox.setBoundingBox(x_pos+ 960 , y_pos+108  ,128 ,116 );
			upClassEquip.setBoundingBox(x_pos+ 1984 , y_pos+ 108 ,128 ,88 );
			upClassProfDesk.setBoundingBox(x_pos+ 1440 , y_pos+ 236 ,192 ,88 );
			upClassStudDesk1.setBoundingBox(x_pos+ 1088 , y_pos+ 428 ,384 ,80 );
			upClassStudDesk2.setBoundingBox(x_pos+  1600, y_pos+ 428 ,384 ,80 );
			upClassStudDesk3.setBoundingBox(x_pos+ 1088 , y_pos+ 620 ,384 ,80 );
			upClassStudDesk4.setBoundingBox(x_pos+ 1600 , y_pos+ 620 ,384 ,80 );			
			leftClass1UpWall.setBoundingBox(x_pos+ 0 , y_pos+ 672 , 864, 128);
			leftClass1LeftWall.setBoundingBox(x_pos+ 0 , y_pos+ 800 ,64 ,2336 );
			leftClass1RightWall.setBoundingBox(x_pos+ 832 , y_pos+ 1088 ,128 , 928);
			leftClass1DownWall.setBoundingBox(x_pos+ 64 , y_pos+ 1664 , 768,160 );
			leftClass1ProfDesk.setBoundingBox(x_pos+ 352 , y_pos+ 912 ,192 ,88 );
			leftClass1StudDesk1.setBoundingBox(x_pos+ 192 , y_pos+ 1132 ,192 ,128 );
			leftClass1StudDesk2.setBoundingBox(x_pos+ 512 , y_pos+ 1132 ,192 ,128 );
			leftClass1StudDesk3.setBoundingBox(x_pos+ 192 , y_pos+ 1388 ,192 ,128 );
			leftClass1StudDesk4.setBoundingBox(x_pos+ 512 , y_pos+ 1388 ,192 ,128 );
			leftClass1Equip.setBoundingBox(x_pos+ 704 , y_pos+ 744 ,132 , 88);				
			leftClass2RightWall.setBoundingBox(x_pos+  832, y_pos+ 2112 ,128 ,864 );
			leftClass2ownWall.setBoundingBox(x_pos+  64, y_pos+ 2688 ,768 , 160);
			leftClass2ProfDesk.setBoundingBox(x_pos+ 352 , y_pos+ 1936 ,192 , 88);
			leftClass2StudDesk1.setBoundingBox(x_pos+ 192 , y_pos+  2156, 192,128 );
			leftClass2StudDesk2.setBoundingBox(x_pos+ 512 , y_pos+ 2156 ,192 ,128 );
			leftClass2StudDesk3.setBoundingBox(x_pos+192  , y_pos+ 2412 ,192 ,128 );
			leftClass2StudDesk4.setBoundingBox(x_pos+ 512 , y_pos+ 2412 ,192 ,128 );
			leftClass2Equip.setBoundingBox(x_pos+  704, y_pos+ 1768 ,132 ,88 );			
			rightClassUpWall.setBoundingBox(x_pos+ 2112 , y_pos+ 1984 ,896 ,288 );
			rightClassRightWall.setBoundingBox(x_pos+ 3008 , y_pos+ 1280 ,64 , 1856);
			rightClassLeftWall1.setBoundingBox(x_pos+ 2112 , y_pos+ 2272 ,128 ,192 );		
			rightClassLeftWall2.setBoundingBox(x_pos+ 2112 , y_pos+ 2560 ,128 ,576 );
			rightClassProfDesk.setBoundingBox(x_pos+ 2528 , y_pos+ 2384 , 192, 88);
			rightClassStudDesk1.setBoundingBox(x_pos+ 2368 , y_pos+ 2604 ,192 ,128 );
			rightClassStudDesk2.setBoundingBox(x_pos+ 2688 , y_pos+ 2604 ,192 ,128 );
			rightClassStudDesk3.setBoundingBox(x_pos+ 2368 , y_pos+ 2860 ,192 , 128);
			rightClassStudDesk4.setBoundingBox(x_pos+ 2688 , y_pos+ 2860 , 192,128 );
			rightClassEquip.setBoundingBox(x_pos+ 2880 , y_pos+ 2216 , 132,88 );			
			leftDownStairLeftWall.setBoundingBox(x_pos+ 64 , y_pos+ 2848 ,128 ,288 );
			leftDownStairRightWall.setBoundingBox(x_pos+500  , y_pos+ 2848 , 332,128 );
			leftDownLeftStair.setBoundingBox(x_pos+ 192 , y_pos+ 2848 ,136 ,128 );			
			rightCenterStairUpWall.setBoundingBox(x_pos+ 2572 , y_pos+ 1440 , 172, 192);
			rightCenterStairLeftWall.setBoundingBox(x_pos+  2112, y_pos+1440  ,640 ,320 );
			rightStairRightStair.setBoundingBox(x_pos+ 2744 , y_pos+ 1440 , 136, 320);
			rightStairRightWall.setBoundingBox(x_pos+ 2880 , y_pos+ 1440 ,128 ,544 );			
			enterFlower1.setBoundingBox(x_pos+ 2368 , y_pos+ 944 , 64, 112);
			enterFlower2.setBoundingBox(x_pos+ 2560 , y_pos+ 944 , 64, 112);
			enterFlower3.setBoundingBox(x_pos+2752 , y_pos+ 944 ,64 ,112 );
			enterFlower4.setBoundingBox(x_pos+ 2944 , y_pos+944  ,64 ,112 );			
			enterFlower5.setBoundingBox(x_pos+ 2176 , y_pos+ 1328 ,64 ,112 );			
			enterFlower6.setBoundingBox(x_pos+  2368, y_pos+ 1328 ,64 ,112 );
			enterFlower7.setBoundingBox(x_pos+ 2560 , y_pos+ 1328 , 64, 112);
			enterFlower8.setBoundingBox(x_pos+ 2752 , y_pos+ 1328 ,64 , 112);
			enterFlower9.setBoundingBox(x_pos+ 2944 , y_pos+ 1328 ,64 ,112 );			
			centerUpFlower1.setBoundingBox(x_pos+ 1344 , y_pos+ 944 ,64 ,112 );
			centerUpFlower2.setBoundingBox(x_pos+  1664, y_pos+ 944 ,64 , 112);			
			centerDownFlower1.setBoundingBox(x_pos+1280  , y_pos+ 2800 ,64 ,112 );
			centerDownFlower2.setBoundingBox(x_pos+ 1728 , y_pos+ 2800 ,64 ,112);
			centerDownFlower3.setBoundingBox(x_pos+ 2048 , y_pos+ 2992 , 64, 112);				
			centerFlowerUpWall.setBoundingBox(x_pos+ 1216 , y_pos+ 1248 ,640 ,228 );
			centerFlowerLeftWall.setBoundingBox(x_pos+1216  , y_pos+ 1476 ,64 ,1372 );
			centerFlowerRightWall.setBoundingBox(x_pos+ 1792 , y_pos+1476  , 64,1372 );
			centerFlowerDownLeftWall.setBoundingBox(x_pos+ 1280 , y_pos+2688  ,192 , 160);
			centerFlowerDownRightWall.setBoundingBox(x_pos+ 1600 , y_pos+ 2688 ,192 , 160);
			centerFlowerLeftFence.setBoundingBox(x_pos+ 1288 , y_pos+1476  ,16 , 264);
			centerFlowerRightFence.setBoundingBox(x_pos+ 1768 , y_pos+ 1476 ,16 ,264 );
			centerFlowerDownLeftFence.setBoundingBox(x_pos+ 1284 , y_pos+ 1740 ,124 ,52 );
			centerFlowerDownRightFence.setBoundingBox(x_pos+ 1536 , y_pos+ 1740 ,252 ,52 );			
			centerFlowerSmallWood1.setBoundingBox(x_pos+ 1280 , y_pos+ 1824 , 64, 64);
			centerFlowerSmallWood2.setBoundingBox(x_pos+ 1280 , y_pos+ 2052 ,64 , 64);
			centerFlowerSmallWood3.setBoundingBox(x_pos+1280  , y_pos+  2272,64 , 64);
			centerFlowerSmallWood4.setBoundingBox(x_pos+1280  , y_pos+  2496, 64, 64);
			centerFlowerSmallWood5.setBoundingBox(x_pos+ 1732 , y_pos+ 1824 ,64,64 );
			centerFlowerSmallWood6.setBoundingBox(x_pos+ 1732 , y_pos+ 2052 ,64 , 64);
			centerFlowerSmallWood7.setBoundingBox(x_pos+ 1732 , y_pos+ 2272 ,64 ,64 );
			centerFlowerSmallWood8.setBoundingBox(x_pos+ 1732 , y_pos+ 2496 ,64 ,64 );			
			centerFlowerChair1.setBoundingBox(x_pos+ 1532 , y_pos+ 1820 ,128 ,28 );
			centerFlowerChair2.setBoundingBox(x_pos+ 1728 , y_pos+ 1920 ,24 , 108);
			centerFlowerDesk.setBoundingBox(x_pos+ 1536 , y_pos+ 1920 ,120 , 100);
			centerFlowerWood1.setBoundingBox(x_pos+ 1608 , y_pos+ 2120 ,48 , 180);
			centerFlowerWood2.setBoundingBox(x_pos+ 1568 , y_pos+ 2160 , 128, 140);			
			centerFlowerWood3.setBoundingBox(x_pos+ 1608 , y_pos+2400 ,48 , 180);
			centerFlowerWood4.setBoundingBox(x_pos+ 1568 , y_pos+ 2440 ,128 ,140 );
			
		}
		public void resetMoveState(){
			upMoveState = MOVE_ABLE;
			downMoveState = MOVE_ABLE;
			leftMoveState = MOVE_ABLE;
			rightMoveState = MOVE_ABLE;
			moveBounding();
		}

		public void moveUp(int add) {
			if (upMoveState == MOVE_ABLE) {
				y_pos += SPEED +add;
				moveBounding();
			}
		}
		public void moveDown(int add) {
			if (downMoveState == MOVE_ABLE) {
				y_pos -= SPEED +add;
				moveBounding();
			}
		}
		public void moveLeft(int add) {
			if (leftMoveState == MOVE_ABLE) {
				x_pos += SPEED +add;
				moveBounding();
			}
		}
		public void moveRight(int add) {
			if (rightMoveState == MOVE_ABLE) {
				x_pos -= SPEED +add;
				moveBounding();
			}
		}
		public int getUpMoveState() {
			return upMoveState;
		}
		public void setUpMoveState(int upMoveState) {
			this.upMoveState = upMoveState;
		}
		public int getDownMoveState() {
			return downMoveState;
		}
		public void setDownMoveState(int downMoveState) {
			this.downMoveState = downMoveState;
		}
		public int getLeftMoveState() {
			return leftMoveState;
		}
		public void setLeftMoveState(int leftMoveState) {
			this.leftMoveState = leftMoveState;
		}
		public int getRightMoveState() {
			return rightMoveState;
		}
		public void setRightMoveState(int rightMoveState) {
			this.rightMoveState = rightMoveState;
		}
		public int getX_pos() {
			return x_pos;
		}
		public int getY_pos() {
			return y_pos;
		}
		public void setX_pos(int x_pos) {
			this.x_pos = x_pos;
		}

		public void setY_pos(int y_pos) {
			this.y_pos = y_pos;
		}
	}
	public class Campus {
		//バスから最初に下りる時
		static final int START_X_POS_BUS = -1624;
		static final int START_Y_POS_BUS = -876;
		
		//野生ポケモンとの戦闘イベントが起こるバウンディングボックス
		static final int UP_GRASS_BOX=33;
		static final int DOWN_GRASS_BOX=34;
		
		//ビルの入り口
		static final int START_X_POS_DOOR = -209;
		static final int START_Y_POS_DOOR = -496;
		
		private int upMoveState = 1; 
		private int downMoveState = 1; 
		private int leftMoveState = 1; 
		private int rightMoveState = 1; 
		private int x_pos = START_X_POS_BUS; 
		private int y_pos = START_Y_POS_BUS; 		
		private BufferedImage campus;
		private BufferedImage campusHalf;
		private BoundingBox door;
		
		private BoundingBox euLeftWall; 
		private BoundingBox euUpWall;
		private BoundingBox euRightWall;
		private BoundingBox euDownWall1;
		private BoundingBox euDownWall2;
		private BoundingBox euWood1;
		private BoundingBox euWood2;
		private BoundingBox eubench;
		
		private BoundingBox eEnterance;

		private BoundingBox edLeftWall;
		private BoundingBox edUpWall1;
		private BoundingBox edUpwall2;
		private BoundingBox edRightWall;
		private BoundingBox edDownWall;
		private BoundingBox edGrass1;
		private BoundingBox edGrass2;

		private BoundingBox bridgeUp; 
		private BoundingBox bridgeDown;

		private BoundingBox waterUpGrass;
		private BoundingBox grassDownBlock;
		private BoundingBox waterDownGrass;
		private BoundingBox leftBusStopRoof;
		private BoundingBox leftBusStopPilar;
		private BoundingBox rightBusStopRoof;
		private BoundingBox rightBusStopPilar;
		private BoundingBox roadDownBlock;
		private BoundingBox roadUpBlock;
		private BoundingBox roadUpLeftLight1;
		private BoundingBox roadUpRightLight1;
		private BoundingBox roadUpRightLight2;
		private BoundingBox roadUpRightLight3;
		private BoundingBox roadRightWall;	
		
		
		private BoundingBox upGrassBox;
		private BoundingBox downGrassBox;
		
		private BoundingBox busIntersectsBox;

		private ArrayList<BoundingBox> boundList;

		public Campus() throws IOException {
			campus = ImageIO.read(new File(".\\img\\map\\campus.png"));
			campusHalf = ImageIO.read(new File(".\\img\\map\\campus_half.png"));
			
			door = new BoundingBox(x_pos + 439, y_pos + 723, 11, 85);			
			euLeftWall = new BoundingBox(x_pos+324, y_pos+380, 28, 260);
			euUpWall = new BoundingBox(x_pos+324, y_pos+324, 548, 52);
			euRightWall = new BoundingBox(x_pos + 744, y_pos+376, 24, 268);
			euDownWall1 = new BoundingBox(x_pos+444, y_pos+644, 100, 52);
			euDownWall2 = new BoundingBox(x_pos+672, y_pos+644, 96, 52);
			euWood1 = new BoundingBox(x_pos+872, y_pos+244, 124, 164);
			eubench = new BoundingBox(x_pos+944, y_pos+408, 64, 136);
			euWood2 = new BoundingBox(x_pos+872, y_pos+540, 124, 164);
			eEnterance = new BoundingBox(x_pos+324, y_pos+656, 124, 214);
			edLeftWall = new BoundingBox(x_pos+324, y_pos+952, 20, 320);
			edUpWall1 = new BoundingBox(x_pos+324, y_pos+900, 224, 52);
			edUpwall2 = new BoundingBox(x_pos+668, y_pos+900, 96, 52);
			edRightWall = new BoundingBox(x_pos+744, y_pos+952, 20, 268);
			edDownWall = new BoundingBox(x_pos+344, y_pos+1220, 420, 52);
			edGrass1 = new BoundingBox(x_pos+784, y_pos+1280, 96, 256);
			edGrass2 = new BoundingBox(x_pos+880, y_pos+900, 96, 636);
			bridgeUp = new BoundingBox(x_pos+948, y_pos+708, 416, 40);
			bridgeDown = new BoundingBox(x_pos+948, y_pos+828, 416, 60);
			waterUpGrass = new BoundingBox(x_pos+1344, y_pos+0, 56, 608);
			grassDownBlock = new BoundingBox(x_pos+1284, y_pos+604, 48, 104);
			waterDownGrass = new BoundingBox(x_pos+1344, y_pos+912, 56, 612);
			leftBusStopRoof = new BoundingBox(x_pos+1404, y_pos+964, 96, 160);
			leftBusStopPilar = new BoundingBox(x_pos+1416, y_pos+1124, 40, 120);
			rightBusStopRoof = new BoundingBox(x_pos+1956, y_pos+964, 84, 160);
			rightBusStopPilar = new BoundingBox(x_pos+2000, y_pos+1124, 40, 120);
			roadDownBlock = new BoundingBox(x_pos+1400, y_pos+1256, 640, 280);
			roadUpBlock = new BoundingBox(x_pos+1400, y_pos+0, 640, 280);
			roadUpLeftLight1 = new BoundingBox(x_pos+1536, y_pos+384, 44, 120);
			roadUpRightLight1 = new BoundingBox(x_pos+1876, y_pos+256, 44, 120);
			roadUpRightLight2 = new BoundingBox(x_pos+1876, y_pos+512, 44, 120);
			roadUpRightLight3 = new BoundingBox(x_pos+1876, y_pos+768, 44, 120);
			roadRightWall = new BoundingBox(x_pos+2040, y_pos+0, 64, 1536);
			upGrassBox = new BoundingBox(x_pos+390, y_pos+385, 310, 250);
			downGrassBox = new BoundingBox(x_pos+390, y_pos+960, 310, 250);
			
			
			busIntersectsBox = new BoundingBox(865, 490, 68, 10);
			
			boundList = new ArrayList<BoundingBox>();
			boundList.add(door);
			boundList.add(euLeftWall);
			boundList.add(euUpWall);
			boundList.add(euRightWall);
			boundList.add(euDownWall1);
			boundList.add(euDownWall2);
			boundList.add(euWood1);
			boundList.add(eubench);
			boundList.add(euWood2);
			boundList.add(eEnterance);
			boundList.add(edLeftWall);
			boundList.add(edUpWall1);
			boundList.add(edUpwall2);
			boundList.add(edRightWall);
			boundList.add(edDownWall);
			boundList.add(edGrass1);
			boundList.add(edGrass2);
			boundList.add(bridgeUp);
			boundList.add(bridgeDown);
			boundList.add(waterUpGrass);
			boundList.add(grassDownBlock);
			boundList.add(waterDownGrass);
			boundList.add(leftBusStopRoof);
			boundList.add(leftBusStopPilar);
			boundList.add(rightBusStopRoof);
			boundList.add(rightBusStopPilar);
			boundList.add(roadDownBlock);
			boundList.add(roadUpBlock);
			boundList.add(roadUpLeftLight1);
			boundList.add(roadUpRightLight1);
			boundList.add(roadUpRightLight2);
			boundList.add(roadUpRightLight3);
			boundList.add(roadRightWall);
			boundList.add(upGrassBox);
			boundList.add(downGrassBox);
					
		}
		public BufferedImage getBufferedImg() {
			return campus;
		}
		public BufferedImage getHalfBufferedImg() {
			return campusHalf;
		}
		public BoundingBox getBusIntersectsBox(){
			return busIntersectsBox;
		}

		public ArrayList<BoundingBox> getBoundList() {
			return boundList;			
		}

		public void moveBounding() {
			door.setBoundingBox(x_pos + 439, y_pos + 723, 11, 85);			
			euLeftWall.setBoundingBox(x_pos+324, y_pos+380, 28, 260);
			euUpWall.setBoundingBox(x_pos+324, y_pos+324, 548, 52);
			euRightWall.setBoundingBox(x_pos + 744, y_pos+376, 24, 268);
			euDownWall1.setBoundingBox(x_pos+444, y_pos+644, 100, 52);
			euDownWall2.setBoundingBox(x_pos+672, y_pos+644, 96, 52);
			euWood1.setBoundingBox(x_pos+872, y_pos+244, 124, 164);
			eubench.setBoundingBox(x_pos+944, y_pos+408, 64, 136);
			euWood2.setBoundingBox(x_pos+872, y_pos+540, 124, 164);
			eEnterance.setBoundingBox(x_pos+324, y_pos+656, 124, 214);
			edLeftWall.setBoundingBox(x_pos+324, y_pos+952, 20, 320);
			edUpWall1.setBoundingBox(x_pos+324, y_pos+900, 224, 52);
			edUpwall2.setBoundingBox(x_pos+668, y_pos+900, 96, 52);
			edRightWall.setBoundingBox(x_pos+744, y_pos+952, 20, 268);
			edDownWall.setBoundingBox(x_pos+344, y_pos+1220, 420, 52);
			edGrass1.setBoundingBox(x_pos+784, y_pos+1280, 96, 256);
			edGrass2.setBoundingBox(x_pos+880, y_pos+900, 96, 636);
			bridgeUp.setBoundingBox(x_pos+948, y_pos+708, 416, 40);
			bridgeDown.setBoundingBox(x_pos+948, y_pos+828, 416, 60);
			waterUpGrass.setBoundingBox(x_pos+1344, y_pos+0, 56, 608);
			grassDownBlock.setBoundingBox(x_pos+1284, y_pos+604, 48, 104);
			waterDownGrass.setBoundingBox(x_pos+1344, y_pos+912, 56, 612);
			leftBusStopRoof.setBoundingBox(x_pos+1404, y_pos+964, 96, 160);
			leftBusStopPilar.setBoundingBox(x_pos+1416, y_pos+1124, 40, 120);
			rightBusStopRoof.setBoundingBox(x_pos+1956, y_pos+964, 84, 160);
			rightBusStopPilar.setBoundingBox(x_pos+2000, y_pos+1124, 40, 120);
			roadDownBlock.setBoundingBox(x_pos+1400, y_pos+1256, 640, 280);
			roadUpBlock.setBoundingBox(x_pos+1400, y_pos+0, 640, 280);
			roadUpLeftLight1.setBoundingBox(x_pos+1536, y_pos+384, 44, 120);
			roadUpRightLight1.setBoundingBox(x_pos+1876, y_pos+256, 44, 120);
			roadUpRightLight2.setBoundingBox(x_pos+1876, y_pos+512, 44, 120);
			roadUpRightLight3.setBoundingBox(x_pos+1876, y_pos+768, 44, 120);
			roadRightWall.setBoundingBox(x_pos+2040, y_pos+0, 64, 1536);
			upGrassBox.setBoundingBox(x_pos+390, y_pos+385, 310, 250);
			downGrassBox.setBoundingBox(x_pos+390, y_pos+960, 310, 250);
		}
		public void resetMoveState(){
			upMoveState = MOVE_ABLE;
			downMoveState = MOVE_ABLE;
			leftMoveState = MOVE_ABLE;
			rightMoveState = MOVE_ABLE;
			moveBounding();
		}

		public void moveUp(int add) {
			if (upMoveState == MOVE_ABLE) {
				y_pos += SPEED +add;
				moveBounding();
			}
		}
		public void moveDown(int add) {
			if (downMoveState == MOVE_ABLE) {
				y_pos -= SPEED +add;
				moveBounding();
			}
		}
		public void moveLeft(int add) {
			if (leftMoveState == MOVE_ABLE) {
				x_pos += SPEED +add;
				moveBounding();
			}
		}
		public void moveRight(int add) {
			if (rightMoveState == MOVE_ABLE) {
				x_pos -= SPEED +add;
				moveBounding();
			}
		}
		public int getUpMoveState() {
			return upMoveState;
		}
		public void setUpMoveState(int upMoveState) {
			this.upMoveState = upMoveState;
		}
		public int getDownMoveState() {
			return downMoveState;
		}
		public void setDownMoveState(int downMoveState) {
			this.downMoveState = downMoveState;
		}
		public int getLeftMoveState() {
			return leftMoveState;
		}
		public void setLeftMoveState(int leftMoveState) {
			this.leftMoveState = leftMoveState;
		}
		public int getRightMoveState() {
			return rightMoveState;
		}
		public void setRightMoveState(int rightMoveState) {
			this.rightMoveState = rightMoveState;
		}
		public int getX_pos() {
			return x_pos;
		}
		public int getY_pos() {
			return y_pos;
		}
		public void setX_pos(int x_pos) {
			this.x_pos = x_pos;
		}

		public void setY_pos(int y_pos) {
			this.y_pos = y_pos;
		}		
	}
	public class Town {
		static final int START_X_POS = -256;
		static final int START_Y_POS = -1344;
		
		private int upMoveState = 1; 
		private int downMoveState = 1;
		private int leftMoveState = 1; 
		private int rightMoveState = 1; 
		private int x_pos = START_X_POS;
		private int y_pos = START_Y_POS;
		private BufferedImage town;
		
		
		private BoundingBox	door;	//나오는 문
		private BoundingBox fieldLeftWall;
		private BoundingBox fieldUpWall;
		private BoundingBox	fieldRightWall;
		private BoundingBox	fieldUpWood;
		private BoundingBox	fieldUpJump;
		private BoundingBox	fieldLeftJump;
		private BoundingBox	fieldRightJump;
		private BoundingBox	fieldSign;
		private BoundingBox	fieldLeftWoodWall;
		private BoundingBox	fieldRightWoodWall;
		private BoundingBox	fieldEnteranceLeftWall;
		private BoundingBox	fieldEnteranceRightWall;		
		private BoundingBox	townLeftWall;
		private BoundingBox	townLeftDownWall;
		private BoundingBox	townLeftBlockFirst;
		private BoundingBox	townRightWall;
		private BoundingBox	townRightDownWall;
		private BoundingBox	townLake;
		private BoundingBox	leftHome;
		private BoundingBox	leftSign;
		private BoundingBox	rightHome;
		private BoundingBox	rightSign;
		private BoundingBox	laboratorySign;
		private BoundingBox	laboratory;
		private BoundingBox	laboratoryFrontFence;
		private ArrayList<BoundingBox> boundList;
		
		public Town() throws IOException {
			town = ImageIO.read(new File(".\\img\\map\\town.png"));
			door= new BoundingBox(x_pos+512,y_pos+1536,64,64);	//나오는 문
			fieldLeftWall = new BoundingBox(x_pos+256,y_pos+192,64,512);
			fieldUpWall= new BoundingBox(x_pos+320,y_pos+192,896,64);
			fieldRightWall= new BoundingBox(x_pos+1216,y_pos+192,64,512);
			fieldUpWood= new BoundingBox(x_pos+320,y_pos+384,512,64);
			fieldUpJump= new BoundingBox(x_pos+1088,y_pos+384,128,64);
			fieldLeftJump= new BoundingBox(x_pos+320,y_pos+640,128,64);
			fieldRightJump= new BoundingBox(x_pos+704,y_pos+640,512,64);
			fieldSign= new BoundingBox(x_pos+640,y_pos+640,64,46);
			fieldLeftWoodWall= new BoundingBox(x_pos+256,y_pos+704,64,256);
			fieldRightWoodWall= new BoundingBox(x_pos+1216,y_pos+704,64,256);
			fieldEnteranceLeftWall= new BoundingBox(x_pos+192,y_pos+960,512,384);
			fieldEnteranceRightWall= new BoundingBox(x_pos+832,y_pos+960,640,384);			
			townLeftWall= new BoundingBox(x_pos+192,y_pos+1344,64,1088);
			townLeftDownWall= new BoundingBox(x_pos+256,y_pos+2368,192,64);
			townLeftBlockFirst= new BoundingBox(x_pos+256,y_pos+2304,64,64);
			townRightWall= new BoundingBox(x_pos+1408,y_pos+1344,64,960);
			townRightDownWall= new BoundingBox(x_pos+704,y_pos+2304,768,64);
			townLake= new BoundingBox(x_pos+448,y_pos+2112,256,512);
			leftHome= new BoundingBox(x_pos+448,y_pos+1408,256,192);
			leftSign= new BoundingBox(x_pos+384,y_pos+1536,64,64);
			rightHome= new BoundingBox(x_pos+960,y_pos+1408,256,192);
			rightSign= new BoundingBox(x_pos+896,y_pos+1536,64,64);
			laboratorySign= new BoundingBox(x_pos+448,y_pos+1792,256,64);
			laboratory= new BoundingBox(x_pos+832,y_pos+1728,384,256);
			laboratoryFrontFence= new BoundingBox(x_pos+832,y_pos+2048,384,64);
			
			boundList = new ArrayList<>();
			
			boundList.add(door);
			boundList.add(fieldLeftWall);
			boundList.add(fieldUpWall);
			boundList.add(fieldRightWall);
			boundList.add(fieldUpWood);
			boundList.add(fieldUpJump);
			boundList.add(fieldLeftJump);
			boundList.add(fieldRightJump);
			boundList.add(fieldSign);
			boundList.add(fieldLeftWoodWall);
			boundList.add(fieldRightWoodWall);
			boundList.add(fieldEnteranceLeftWall);
			boundList.add(fieldEnteranceRightWall);			
			boundList.add(townLeftWall);
			boundList.add(townLeftDownWall);
			boundList.add(townLeftBlockFirst);			
			boundList.add(townRightWall);
			boundList.add(townRightDownWall);
			boundList.add(townLake);
			boundList.add(leftHome);
			boundList.add(leftSign);
			boundList.add(rightHome);
			boundList.add(rightSign);
			boundList.add(laboratorySign);
			boundList.add(laboratory);
			boundList.add(laboratoryFrontFence);	
			
		}

		public BufferedImage getBufferedImg() {
			return town;
		}

		public ArrayList<BoundingBox> getBoundList() {
			return boundList;
		}

		public void moveBounding() {
	
			door.setBoundingBox(x_pos+512,y_pos+1536,64,64);	//나오는 문
			fieldLeftWall.setBoundingBox(x_pos+256,y_pos+192,64,512);
			fieldUpWall.setBoundingBox(x_pos+320,y_pos+192,896,64);
			fieldRightWall.setBoundingBox(x_pos+1216,y_pos+192,64,512);
			fieldUpWood.setBoundingBox(x_pos+320,y_pos+384,512,64);
			fieldUpJump.setBoundingBox(x_pos+1088,y_pos+384,128,64);
			fieldLeftJump.setBoundingBox(x_pos+320,y_pos+640,128,64);
			fieldRightJump.setBoundingBox(x_pos+704,y_pos+640,512,64);
			fieldSign.setBoundingBox(x_pos+640,y_pos+640,64,46);
			fieldLeftWoodWall.setBoundingBox(x_pos+256,y_pos+704,64,256);
			fieldRightWoodWall.setBoundingBox(x_pos+1216,y_pos+704,64,256);
			fieldEnteranceLeftWall.setBoundingBox(x_pos+192,y_pos+960,512,384);
			fieldEnteranceRightWall.setBoundingBox(x_pos+832,y_pos+960,640,384);			
			townLeftWall.setBoundingBox(x_pos+192,y_pos+1344,64,1088);
			townLeftDownWall.setBoundingBox(x_pos+256,y_pos+2368,192,64);
			townLeftBlockFirst.setBoundingBox(x_pos+256,y_pos+2304,64,64);
			townRightWall.setBoundingBox(x_pos+1408,y_pos+1344,64,960);
			townRightDownWall.setBoundingBox(x_pos+704,y_pos+2304,768,64);
			townLake.setBoundingBox(x_pos+448,y_pos+2112,256,512);
			leftHome.setBoundingBox(x_pos+448,y_pos+1408,256,192);
			leftSign.setBoundingBox(x_pos+384,y_pos+1536,64,64);
			rightHome.setBoundingBox(x_pos+960,y_pos+1408,256,192);
			rightSign.setBoundingBox(x_pos+896,y_pos+1536,64,64);
			laboratorySign.setBoundingBox(x_pos+448,y_pos+1792,256,64);
			laboratory.setBoundingBox(x_pos+832,y_pos+1728,384,256);
			laboratoryFrontFence.setBoundingBox(x_pos+832,y_pos+2048,384,64);
		}
		public void resetMoveState(){
			upMoveState = MOVE_ABLE;
			downMoveState = MOVE_ABLE;
			leftMoveState = MOVE_ABLE;
			rightMoveState = MOVE_ABLE;
			moveBounding();
		}

		public void moveUp(int add) {
			if (upMoveState == MOVE_ABLE) {
				y_pos += SPEED +add;
				moveBounding();
			}
		}
		public void moveDown(int add) {
			if (downMoveState == MOVE_ABLE) {
				y_pos -= SPEED +add;
				moveBounding();
			}
		}
		public void moveLeft(int add) {
			if (leftMoveState == MOVE_ABLE) {
				x_pos += SPEED +add;
				moveBounding();
			}
		}
		public void moveRight(int add) {
			if (rightMoveState == MOVE_ABLE) {
				x_pos -= SPEED +add;
				moveBounding();
			}
		}
		public int getUpMoveState() {
			return upMoveState;
		}
		public void setUpMoveState(int upMoveState) {
			this.upMoveState = upMoveState;
		}
		public int getDownMoveState() {
			return downMoveState;
		}
		public void setDownMoveState(int downMoveState) {
			this.downMoveState = downMoveState;
		}
		public int getLeftMoveState() {
			return leftMoveState;
		}
		public void setLeftMoveState(int leftMoveState) {
			this.leftMoveState = leftMoveState;
		}
		public int getRightMoveState() {
			return rightMoveState;
		}
		public void setRightMoveState(int rightMoveState) {
			this.rightMoveState = rightMoveState;
		}
		public int getX_pos() {
			return x_pos;
		}
		public int getY_pos() {
			return y_pos;
		}
		public void setX_pos(int x_pos) {
			this.x_pos = x_pos;
		}

		public void setY_pos(int y_pos) {
			this.y_pos = y_pos;
		}
	}
	
	public class Mohyun{
		static final int START_X_POS = -316;
		static final int START_Y_POS = -10;
		
		static final int BUS_BROAD_INDEX = 15;
		
		private int upMoveState = 1; 
		private int downMoveState = 1; 
		private int leftMoveState = 1; 
		private int rightMoveState = 1; 
		private int x_pos = START_X_POS;
		private int y_pos = START_Y_POS;
		private BufferedImage mohyun;
		private BufferedImage mohyunHalf;

		private BoundingBox door;
		private BoundingBox upBuildingRight;
		private BoundingBox upBuildingLeft;
		private BoundingBox downBlock;
		private BoundingBox leftBlock;
		private BoundingBox rightBlock;
		private BoundingBox firstTree;
		private BoundingBox secondLight;
		private BoundingBox thirdTree;
		private BoundingBox forthLight;
		private BoundingBox fifthTree;
		private BoundingBox busStopUp;
		private BoundingBox busStopDownLeft;
		private BoundingBox busStopDownRight;
		private BoundingBox lastTree;
		private BoundingBox busBroad;
		private ArrayList<BoundingBox> boundList;
		
		private BoundingBox busIntersectsBox;

		public Mohyun() throws IOException {
			mohyunHalf = ImageIO.read(new File(".\\img\\map\\mohyun_half.png"));
			mohyun = ImageIO.read(new File(".\\img\\map\\mohyun1.png"));
			door = new BoundingBox(x_pos+575, y_pos+240, 64, 10);
			upBuildingLeft = new BoundingBox(x_pos+0, y_pos+0, 560, 256);
			upBuildingRight = new BoundingBox(x_pos+649, y_pos+0, 1101, 256);
			downBlock = new BoundingBox(x_pos+256, y_pos+968, 381, 504);
			leftBlock = new BoundingBox(x_pos+0, y_pos+256, 256, 1216);
			rightBlock = new BoundingBox(x_pos+1780, y_pos+0, 328, 1456);
			firstTree = new BoundingBox(x_pos+252, y_pos+452, 128, 180);
			secondLight = new BoundingBox(x_pos+508, y_pos+512, 48, 124);
			thirdTree = new BoundingBox(x_pos+700, y_pos+452, 128, 180);
			forthLight = new BoundingBox(x_pos+956, y_pos+512, 48, 124);
			fifthTree = new BoundingBox(x_pos+1144, y_pos+452, 128, 180);
			busStopUp = new BoundingBox(x_pos+1272, y_pos+448, 320, 136);
			busStopDownLeft = new BoundingBox(x_pos+1284, y_pos+584, 40, 52);
			busStopDownRight = new BoundingBox(x_pos+1540, y_pos+584, 40, 52);
			lastTree = new BoundingBox(x_pos+1592, y_pos+452, 128, 180);
			busBroad = new BoundingBox(x_pos+1410, y_pos+650, 35, 20);
			
			
			busIntersectsBox = new BoundingBox(-150, 315, 2, 90);
			
			boundList = new ArrayList<>();			
			boundList.add(door);
			boundList.add(upBuildingLeft);
			boundList.add(upBuildingRight);
			boundList.add(downBlock);
			boundList.add(leftBlock);
			boundList.add(rightBlock);
			boundList.add(firstTree);
			boundList.add(secondLight);
			boundList.add(thirdTree);
			boundList.add(forthLight);
			boundList.add(fifthTree);
			boundList.add(busStopUp);
			boundList.add(busStopDownLeft);
			boundList.add(busStopDownRight);
			boundList.add(lastTree);	
			boundList.add(busBroad);
			
		}
		public BufferedImage getBufferedImg() {
			return mohyun;
		}
		public BufferedImage getHalfBufferedImg() {
			return mohyunHalf;
		}
		
		public BoundingBox getBusIntersectsBox(){
			return busIntersectsBox;
		}

		public ArrayList<BoundingBox> getBoundList() {
			return boundList;			
		}

		public void moveBounding() {
			door.setBoundingBox(x_pos+575, y_pos+240, 64, 10);
			upBuildingLeft.setBoundingBox(x_pos+0, y_pos+0, 560, 256);
			upBuildingRight.setBoundingBox(x_pos+649, y_pos+0, 1101, 256);
			downBlock.setBoundingBox(x_pos+256, y_pos+968, 1524, 504);
			leftBlock.setBoundingBox(x_pos+0, y_pos+256, 256, 1216);
			rightBlock.setBoundingBox(x_pos+1780, y_pos+0, 328, 1456);
			firstTree.setBoundingBox(x_pos+252, y_pos+452, 128, 180);
			secondLight.setBoundingBox(x_pos+508, y_pos+512, 48, 124);
			thirdTree.setBoundingBox(x_pos+700, y_pos+452, 128, 180);
			forthLight.setBoundingBox(x_pos+956, y_pos+512, 48, 124);
			fifthTree.setBoundingBox(x_pos+1144, y_pos+452, 128, 180);
			busStopUp.setBoundingBox(x_pos+1272, y_pos+448, 320, 136);
			busStopDownLeft.setBoundingBox(x_pos+1284, y_pos+584, 40, 52);
			busStopDownRight.setBoundingBox(x_pos+1540, y_pos+584, 40, 52);
			lastTree.setBoundingBox(x_pos+1592, y_pos+452, 128, 180);
			busBroad.setBoundingBox(x_pos+1410, y_pos+650, 35, 20);
		}
		public void resetMoveState(){
			upMoveState = MOVE_ABLE;
			downMoveState = MOVE_ABLE;
			leftMoveState = MOVE_ABLE;
			rightMoveState = MOVE_ABLE;
			moveBounding();
		}

		public void moveUp(int add) {
			if (upMoveState == MOVE_ABLE) {
				y_pos += SPEED +add;
				moveBounding();
			}
		}
		public void moveDown(int add) {
			if (downMoveState == MOVE_ABLE) {
				y_pos -= SPEED +add;
				moveBounding();
			}
		}
		public void moveLeft(int add) {
			if (leftMoveState == MOVE_ABLE) {
				x_pos += SPEED +add;
				moveBounding();
			}
		}
		public void moveRight(int add) {
			if (rightMoveState == MOVE_ABLE) {
				x_pos -= SPEED +add;
				moveBounding();
			}
		}
		public int getUpMoveState() {
			return upMoveState;
		}
		public void setUpMoveState(int upMoveState) {
			this.upMoveState = upMoveState;
		}
		public int getDownMoveState() {
			return downMoveState;
		}
		public void setDownMoveState(int downMoveState) {
			this.downMoveState = downMoveState;
		}
		public int getLeftMoveState() {
			return leftMoveState;
		}
		public void setLeftMoveState(int leftMoveState) {
			this.leftMoveState = leftMoveState;
		}
		public int getRightMoveState() {
			return rightMoveState;
		}
		public void setRightMoveState(int rightMoveState) {
			this.rightMoveState = rightMoveState;
		}
		public int getX_pos() {
			return x_pos;
		}
		public int getY_pos() {
			return y_pos;
		}
		public void setX_pos(int x_pos) {
			this.x_pos = x_pos;
		}

		public void setY_pos(int y_pos) {
			this.y_pos = y_pos;
		}

	}
	public class Room {
		static final int START_X_POS = 128;
		static final int START_Y_POS = -192;
		
		private int upMoveState = 1; 
		private int downMoveState = 1; 
		private int leftMoveState = 1; 
		private int rightMoveState = 1; 
		private int x_pos = START_X_POS;
		private int y_pos = START_Y_POS;
		private BufferedImage room;

		private BoundingBox leftUpComputer;
		private BoundingBox rightUpBox;
		private BoundingBox centerTable;

		private BoundingBox upWall;
		private BoundingBox leftWall;
		private BoundingBox rightWall;
		private BoundingBox downWall1;
		private BoundingBox downWall2;
		private BoundingBox door;
		private ArrayList<BoundingBox> boundList;

		public Room() throws IOException {
			room = ImageIO.read(new File(".\\img\\map\\room.png"));
			door = new BoundingBox(x_pos + 128, y_pos + 520, 128, 3);
			leftUpComputer = new BoundingBox(0 + x_pos, 32 + y_pos, 64, 128);
			rightUpBox = new BoundingBox(256 + x_pos, 32 + y_pos, 256, 96);
			centerTable = new BoundingBox(192 + x_pos, 192 + y_pos, 128, 128);
			upWall = new BoundingBox(x_pos + 64, y_pos - 1, 192, 64);
			leftWall = new BoundingBox(x_pos - 4, y_pos, 3, 512);
			rightWall = new BoundingBox(x_pos + 513, y_pos, 3, 512);
			downWall1 = new BoundingBox(x_pos, y_pos + 513, 128, 3);
			downWall2 = new BoundingBox(x_pos + 256, y_pos + 513, 256, 3);
			boundList = new ArrayList<>();
			
			boundList.add(door);
			boundList.add(leftUpComputer);
			boundList.add(rightUpBox);
			boundList.add(centerTable);
			boundList.add(upWall);
			boundList.add(leftWall);
			boundList.add(rightWall);
			boundList.add(downWall1);
			boundList.add(downWall2);
			
		}

		public BufferedImage getBufferedImg() {
			return room;
		}

		public ArrayList<BoundingBox> getBoundList() {

			return boundList;			
		}

		public void moveBounding() {
			door.setBoundingBox(x_pos + 128, y_pos + 513, 128, 3);
			leftUpComputer.setBoundingBox(0 + x_pos, 32 + y_pos, 64, 128);
			rightUpBox.setBoundingBox(256 + x_pos, 32 + y_pos, 256, 96);
			centerTable.setBoundingBox(192 + x_pos, 192 + y_pos, 128, 128);
			upWall.setBoundingBox(x_pos + 64, y_pos - 1, 192, 64);
			leftWall.setBoundingBox(x_pos - 4, y_pos, 3, 512);
			rightWall.setBoundingBox(x_pos + 513, y_pos, 3, 512);
			downWall1.setBoundingBox(x_pos, y_pos + 513, 128, 3);
			downWall2.setBoundingBox(x_pos + 256, y_pos + 513, 256, 3);			
		}
		public void resetMoveState(){
			upMoveState = MOVE_ABLE;
			downMoveState = MOVE_ABLE;
			leftMoveState = MOVE_ABLE;
			rightMoveState = MOVE_ABLE;
			moveBounding();
		}

		public void moveUp(int add) {
			if (upMoveState == MOVE_ABLE) {
				y_pos += SPEED +add;
				moveBounding();
			}
		}
		public void moveDown(int add) {
			if (downMoveState == MOVE_ABLE) {
				y_pos -= SPEED +add;
				moveBounding();
			}
		}
		public void moveLeft(int add) {
			if (leftMoveState == MOVE_ABLE) {
				x_pos += SPEED +add;
				moveBounding();
			}
		}
		public void moveRight(int add) {
			if (rightMoveState == MOVE_ABLE) {
				x_pos -= SPEED +add;
				moveBounding();
			}
		}
		public int getUpMoveState() {
			return upMoveState;
		}
		public void setUpMoveState(int upMoveState) {
			this.upMoveState = upMoveState;
		}
		public int getDownMoveState() {
			return downMoveState;
		}
		public void setDownMoveState(int downMoveState) {
			this.downMoveState = downMoveState;
		}
		public int getLeftMoveState() {
			return leftMoveState;
		}
		public void setLeftMoveState(int leftMoveState) {
			this.leftMoveState = leftMoveState;
		}
		public int getRightMoveState() {
			return rightMoveState;
		}
		public void setRightMoveState(int rightMoveState) {
			this.rightMoveState = rightMoveState;
		}
		public int getX_pos() {
			return x_pos;
		}
		public int getY_pos() {
			return y_pos;
		}

		public void setX_pos(int x_pos) {
			this.x_pos = x_pos;
		}

		public void setY_pos(int y_pos) {
			this.y_pos = y_pos;
		}
	}	
}