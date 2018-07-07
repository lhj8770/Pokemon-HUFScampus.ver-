package Pocketmon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bus {
	//Singleton pattern
	private static Bus busInstance;
	
	public static int YES =1;
	public static int NONE =0;
	//mohyun最初の位置
	public static int START_X_POS_MOHYUN = 650;
	public static int START_Y_POS_MOHYUN = 315;
	//キャンパスに到着するときの最初位置
	public static int START_X_POS_CAMPUS = 865;
	public static int START_Y_POS_CAMPUS = 805;	
	
	//バスの移動を表現する pos
	public int x_pos = START_X_POS_MOHYUN;
	public int y_pos = START_Y_POS_MOHYUN;
	
	//最初のディレイ(演出)
	private int busDelay;
	
	private int stopState;
	private int speed;
	private int count;
	private int busExistState;
	private BufferedImage mohyunBus;
	private BufferedImage campusBus;
	
	private BoundingBox mohyunBusBB;
	private BoundingBox campusBusBB;
	
	private Bus() throws IOException {
		count = 0;
		speed = 5;		
		busDelay =0;
		stopState =NONE;
		busExistState = Map.MOHYUN_NUM;
		mohyunBus = ImageIO.read(new File(".\\img\\bus\\busmohyun.png"));
		campusBus = ImageIO.read(new File(".\\img\\bus\\buscampus.png"));
		//バスのサイズ
		mohyunBusBB= new BoundingBox(x_pos, y_pos, 156, 96);
		campusBusBB= new BoundingBox(x_pos, y_pos, 68, 156);
	}
	

	public static Bus getInstance(){
		if(busInstance==null){
			try{
				busInstance = new Bus();
			}catch(IOException e){
				e.printStackTrace();
			}			
		}
		return busInstance;
	}
	
	public void moveMohyun(int gameDelay){
		if(stopState==NONE){
			if(count!= gameDelay/10){
				count++;
			}
			else{
				x_pos -= speed;
				moveBB();
				count=0;
			}
		}
	}
	public void moveCampus(int gameDelay){
		if(stopState==NONE){
			if(count!= gameDelay/10){
				count++;
			}
			else{
				y_pos -= speed;
				moveBB();
				count =0 ;			
			}
		}
	}
	public void moveBB(){
		mohyunBusBB.setBoundingBox(x_pos, y_pos, 156, 96);
		campusBusBB.setBoundingBox(x_pos, y_pos, 68, 156);
	}
	public BufferedImage getMohyunBusImg() {
		return mohyunBus;
	}
	public BufferedImage getCampusBusImg() {
		return campusBus;
	}

	public int getBusDelay() {
		return busDelay;
	}

	public void setBusDelay(int busDelay) {
		this.busDelay = busDelay;
	}

	public int getStopState() {
		return stopState;
	}

	public void setStopState(int stopState) {
		this.stopState = stopState;
	}

	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public int getBusExistState() {
		return busExistState;
	}

	public void setBusExistState(int busExistState) {
		this.busExistState = busExistState;
	}

	public BufferedImage getMohyunBus() {
		return mohyunBus;
	}

	public void setMohyunBus(BufferedImage mohyunBus) {
		this.mohyunBus = mohyunBus;
	}

	public BufferedImage getCampusBus() {
		return campusBus;
	}

	public void setCampusBus(BufferedImage campusBus) {
		this.campusBus = campusBus;
	}

	public BoundingBox getMohyunBusBB() {
		return mohyunBusBB;
	}

	public void setMohyunBusBB(BoundingBox mohyunBusBB) {
		this.mohyunBusBB = mohyunBusBB;
	}

	public BoundingBox getCampusBusBB() {
		return campusBusBB;
	}

	public void setCampusBusBB(BoundingBox campusBusBB) {
		this.campusBusBB = campusBusBB;
	}
}
