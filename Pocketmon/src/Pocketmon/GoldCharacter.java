package Pocketmon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
// キャラクターとは違ってnpcはキーボードではなくてタイマーによって動く。
public class GoldCharacter{
	public static int MOVE_STOP =4;
	public static int MOVE_DOWN = 2;
	public static int MOVE_LEFT = 3;
	public static int MOVE_RIGHT = 1;
	public static int MOVE_UP = 0;
	
	public static int MOVE_ABLE = 1;
	public static int MOVE_UNABLE = 0;
    
	public int startXMoveSpeed;
	public int goldFirstSpeed;//npcが最初にキャラクターを見て止まっている時間	
	public int state;
	public int goldBattleSpeed;//npcがキャラクターにぶつかった後に止まっている時間
	public int xMoveSpeed; //初期バトルのiniでイメージが動く速度
	public int firstGoldFrontX;
	
	private int moveSpeed;
	private int stepSpeed;
	private int[] stepState;	
	private int i=0;
	
	public PocketmonComponent pocketmon;
	private BoundingBox bounding;
	private BoundingBox upDirection;
	private BoundingBox downDirection;
	private BoundingBox leftDirection;
	private BoundingBox rightDirection;
	private BufferedImage[] goldImg;
	private BufferedImage goldFront;
	public int moveState=2;	 
	private int step=1;
	public int ImageNum=7; //0~2 までは上3~5は右 6~8下9~11左
	private int x;
	private int y;
	//512,y_pos+1536
	public GoldCharacter(BufferedImage[] goldImg, Map map, PocketmonComponent pocketmon) throws IOException {
		firstGoldFrontX=650;
		state=MOVE_ABLE;
		goldFirstSpeed=0;
		goldBattleSpeed=0;
		this.pocketmon=pocketmon;
		moveSpeed =0;
		stepSpeed =0;
		this.goldImg= goldImg;
		goldFront = ImageIO.read(new File("./img/gold/goldfront.png"));
		x=map.building.getX_pos()+1390;
		y=map.building.getY_pos()+1950;
		bounding = new BoundingBox(x, y, 64, 64);
		upDirection = new BoundingBox(map.building.getX_pos()+1390,map.building.getY_pos()+1835,64, 115);
		downDirection = new BoundingBox(map.building.getX_pos()+1390,map.building.getY_pos()+2015,64, 226);
		leftDirection = new BoundingBox(map.building.getX_pos()+1280,map.building.getY_pos()+1950,110, 64);
		rightDirection = new BoundingBox(map.building.getX_pos()+1455,map.building.getY_pos()+1950,80, 64);
		stepState=new int[4];
		stepState[0]=0;
		stepState[1]=1;
		stepState[2]=2;
		stepState[3]=1;
	}

	public BoundingBox getBoundingBox(){
		return bounding;
	}
	public BoundingBox getUpDirectionBB(){
		return upDirection;
	}
	public BoundingBox getDownDirectionBB(){
		return downDirection;
	}
	public BoundingBox getLeftDirectionBB(){
		return leftDirection;
	}
	public BoundingBox getRightDirectionBB(){
		return rightDirection;
	}
	public BufferedImage getGoldFrontImg(){
		return goldFront;
	}
	public void moveDirection(){
		ImageNum=(moveState *3)+step;
	}
	public void setXY(int locationX,int locationY){
		setX(locationX+1390);
		setY(locationY+1950);
		moveBB(locationX,locationY);
	}
	public void moveBB(int locationX , int locationY){
		bounding.setBoundingBox(locationX+1390, locationY+1950, 64, 64);
		upDirection.setBoundingBox(locationX+1390,locationY+1835,64, 115);
		downDirection.setBoundingBox(locationX+1390,locationY+2015,64, 226);
		leftDirection.setBoundingBox(locationX+1280,locationY+1950,110, 64);
		rightDirection.setBoundingBox(locationX+1455,locationY+1950,80, 64);
	}
	public void moveCharacterBB(){
		bounding.setBoundingBox(x, y, 64, 64);		
	}
	public void move()	{
		stepSpeed++;		
		if(stepSpeed == pocketmon.GAME_DELAY){
			step = stepState[i];			
			i++;
			if(i==4)
				i=0;
			stepSpeed =0;
			ImageNum = (moveState * 3)+step; //ここで方向を決めた足の動きを見せる。
		}		
	} 
	public void stop(){
		step = 1;	//動いて止まる時に足が並んだかたちのstep1に初期化
		ImageNum = (moveState * 3)+step; //方向を決める
	}
	public void moveUp(int add) {
		moveSpeed++;
		move();
		if(moveSpeed==pocketmon.GAME_DELAY/20){
			setY(getY()-1);
			setMoveSpeed(0);			
		}	
		moveCharacterBB();	
	}
	public void moveDown(int add) {
		moveSpeed++;
		move();
		if(moveSpeed==pocketmon.GAME_DELAY/20){
			setY(getY()+1);
			setMoveSpeed(0);
		}	
		moveCharacterBB();
	}
	public void moveLeft(int add) {
		moveSpeed++;
		move();
		if(moveSpeed==pocketmon.GAME_DELAY/20){
			setX(getX()-1);
			setMoveSpeed(0);			
		}	
		moveCharacterBB();
	}
	public void moveRight(int add) {
		moveSpeed++;
		move();
		if(moveSpeed==pocketmon.GAME_DELAY/20){
			setX(getX()+1);
			setMoveSpeed(0);
		}		
		moveCharacterBB();
	}
	public BufferedImage getBufferedImg(){
		return goldImg[ImageNum];	//ここにストライプイメージ配列がりターン	
	}	
	public int getMoveState() {
		return moveState;
	}
	public void setMoveState(int moveState) {
		this.moveState = moveState;
	}	
	public int getMoveSpeed() {
		return moveSpeed;
	}
	public void setMoveSpeed(int moveSpeed) {
		this.moveSpeed = moveSpeed;
	}
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}	
}