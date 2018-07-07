package Pocketmon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
//각자 다른 맵에서 재활용 하자.
public class NpcCharacter {
	
	public PocketmonComponent pocketmon;
	Map map;
	Girl1 girl1;
	Girl2 girl2;
	Man1 man1;
	Man2 man2;
	
	public NpcCharacter(PocketmonComponent pocketmon,Map map) throws IOException {		
		this.pocketmon=pocketmon;
		this.map = map;
		girl1 = new Girl1(map);
		girl2 = new Girl2(map);
		man1= new Man1(map);
		man2= new Man2(map);
	}	
	public class Girl1{
		final static int MOVE_STOP =4;
		final static int MOVE_DOWN = 2;
		final static int MOVE_LEFT = 3;
		final static int MOVE_RIGHT = 1;
		final static int MOVE_UP = 0;
		
		final static int MOVE_ABLE = 1;
		final static int MOVE_UNABLE = 0;
	    
		public int moveDirection;
		public int upState;
		public int downState;
		public int leftState;
		public int rightState;	
		
		public int startXMoveSpeed;
		public int npcFirstSpeed;
		private int moveSpeed;
		private int stepSpeed;
		private int[] stepState;	
		private int i=0;
		public int x_pos;
		public int y_pos;		
		private SpriteImg sprite;
		private BoundingBox bounding;
		private BufferedImage[] npcImg;
		private BufferedImage girl1Front;
		public int moveState=2;	// 이변수는 바운딩 박스 처리할때 방향에 대한 바운딩 또한 처리한다.(평소에는 캐릭터의 움직임 방향)
		private int step=1;
		public int ImageNum=7; //0~2 까지는 위 3~5는 오른쪽 6~8 아래 9~11 왼쪽
		private int x;
		private int y;
		//512,y_pos+1536
		public Girl1(Map map) throws IOException {	
			moveDirection=MOVE_STOP;
			SpriteImg spriteImg = new SpriteImg(4, 3, 64, 64,
					".\\img\\npc\\girl1.png");
			upState=MOVE_ABLE;
			downState=MOVE_ABLE;
			leftState=MOVE_ABLE;
			rightState=MOVE_ABLE;
			npcFirstSpeed=0;			
			moveSpeed =0;
			stepSpeed =0;
			npcImg= spriteImg.makeSpriteImg();
			girl1Front = ImageIO.read(new File(".\\img\\npc\\girl1.png"));
			//npc 재활용을 위해.. 각 맵 넘버에 따라 위치가 따로 받아진다(첫 출연 위치는 무조건 모현)	
			x_pos = 335;
			y_pos = 355;
			x=map.building.getX_pos()+x_pos;
			
			y=map.building.getY_pos()+y_pos;
			bounding = new BoundingBox(x, y, 64, 64);
			stepState=new int[4];
			stepState[0]=0;
			stepState[1]=1;
			stepState[2]=2;
			stepState[3]=1;
			stop();
		}
		public BoundingBox getBoundingBox(){
			return bounding;
		}
		public BufferedImage getGoldFrontImg(){
			return girl1Front;
		}
		public void moveDirection(){
			ImageNum=(moveState *3)+step;
		}
		public void setXY(){
			//모현
			if(map.mapState == Map.MOHYUN_NUM){
				setX(map.mohyun.getX_pos()+x_pos);
				setY(map.mohyun.getY_pos()+y_pos);
				moveBB(map.mohyun.getX_pos()+x_pos,map.mohyun.getY_pos()+y_pos);
			}
			//campus
			if(map.mapState == Map.CAMPUS_NUM){
				setX(map.campus.getX_pos()+1390);
				setY(map.campus.getY_pos()+1950);
				moveBB(map.campus.getX_pos()+1390,map.campus.getY_pos()+1950);
			}
			//building
			if(map.mapState == Map.BUILDING_NUM){
				setX(map.building.getX_pos()+1390);
				setY(map.building.getY_pos()+1950);
				moveBB(map.building.getX_pos()+1390,map.building.getY_pos()+1950);
			}
		}
		public void moveBB(int locationX , int locationY){
			bounding.setBoundingBox(locationX, locationY, 64, 64);
		}
		public void moveCharacterBB(){
			bounding.setBoundingBox(x, y, 64, 64);		
		}
		public void move()	{
			//step=++step%12;	//step은 계속 0부터 2까지 움직임을 반복한다.		
			stepSpeed++;		
			if(stepSpeed == pocketmon.GAME_DELAY){
				step = stepState[i];			
				i++;
				if(i==4)
					i=0;
				stepSpeed =0;
				ImageNum = (moveState * 3)+step; //여기서 방향을 정한 발 움직임을 보여준다
			}		
		} 
		public void stop(){
			step = 1;	//움직이다 멈췄을때 다리가 정렬된 상태인 step 1로 바꾸어 준다.
			ImageNum = (moveState * 3)+step; //방향을 정해준다.			
		}
		public void moveUp(int add) {
			moveSpeed++;
			move();
			if(moveSpeed>=pocketmon.GAME_DELAY/20&&upState == MOVE_ABLE){
				y_pos-=1;
				setMoveSpeed(0);			
			}	
			moveCharacterBB();	
		}
		public void moveDown(int add) {
			moveSpeed++;
			move();
			if(moveSpeed>=pocketmon.GAME_DELAY/20&&downState == MOVE_ABLE){
				y_pos+=1;
				setMoveSpeed(0);
			}	
			moveCharacterBB();
		}
		public void moveLeft(int add) {
			moveSpeed++;
			move();
			if(moveSpeed>=pocketmon.GAME_DELAY/20&&leftState == MOVE_ABLE){
				x_pos-=1;
				setMoveSpeed(0);			
			}	
			moveCharacterBB();
		}
		public void moveRight(int add) {
			moveSpeed++;
			move();
			if(moveSpeed>=pocketmon.GAME_DELAY/20&&rightState == MOVE_ABLE){
				x_pos+=1;
				setMoveSpeed(0);
			}		
			moveCharacterBB();
		}
		public BufferedImage getBufferedImg(){
			
			return npcImg[ImageNum];	//여기서 움직이는 잘라진 객체가 반환된다.			
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
	public class Girl2{
		final static int MOVE_STOP =4;
		final static int MOVE_DOWN = 2;
		final static int MOVE_LEFT = 3;
		final static int MOVE_RIGHT = 1;
		final static int MOVE_UP = 0;
		
		final static int MOVE_ABLE = 1;
		final static int MOVE_UNABLE = 0;
	    
		public int upState;
		public int downState;
		public int leftState;
		public int rightState;
		
		public int startXMoveSpeed;
		public int npcFirstSpeed;
		private int moveSpeed;
		private int stepSpeed;
		private int[] stepState;	
		private int i=0;
		
		private SpriteImg sprite;
		private BoundingBox bounding;
		private BufferedImage[] npcImg;
		private BufferedImage girl2Front;
		public int moveState=2;	// 이변수는 바운딩 박스 처리할때 방향에 대한 바운딩 또한 처리한다.(평소에는 캐릭터의 움직임 방향)
		private int step=1;
		public int ImageNum=7; //0~2 까지는 위 3~5는 오른쪽 6~8 아래 9~11 왼쪽
		private int x;
		private int y;
		//512,y_pos+1536
		public Girl2(Map map) throws IOException {		
			SpriteImg spriteImg = new SpriteImg(4, 3, 64, 64,
					".\\img\\npc\\girl2.png");
			upState=MOVE_ABLE;
			downState=MOVE_ABLE;
			leftState=MOVE_ABLE;
			rightState=MOVE_ABLE;
			
			npcFirstSpeed=0;			
			moveSpeed =0;
			stepSpeed =0;
			npcImg= spriteImg.makeSpriteImg();
			girl2Front = ImageIO.read(new File(".\\img\\npc\\girl2.png"));
			
			x=map.building.getX_pos()+1390;
			y=map.building.getY_pos()+1950;
			bounding = new BoundingBox(x, y, 64, 64);
			stepState=new int[4];
			stepState[0]=0;
			stepState[1]=1;
			stepState[2]=2;
			stepState[3]=1;
		}
		public BoundingBox getBoundingBox(){
			return bounding;
		}
		public BufferedImage getGoldFrontImg(){
			return girl2Front;
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
		}
		public void moveCharacterBB(){
			bounding.setBoundingBox(x, y, 64, 64);		
		}
		public void move()	{
			//step=++step%12;	//step은 계속 0부터 2까지 움직임을 반복한다.		
			stepSpeed++;		
			if(stepSpeed == pocketmon.GAME_DELAY){
				step = stepState[i];			
				i++;
				if(i==4)
					i=0;
				stepSpeed =0;
				ImageNum = (moveState * 3)+step; //여기서 방향을 정한 발 움직임을 보여준다
			}		
		} 
		public void stop(){
			step = 1;	//움직이다 멈췄을때 다리가 정렬된 상태인 step 1로 바꾸어 준다.
			ImageNum = (moveState * 3)+step; //방향을 정해준다.
		}
		public void moveUp(int add) {
			moveSpeed++;
			move();
			if(moveSpeed>=pocketmon.GAME_DELAY/20&&upState == MOVE_ABLE){
				setY(getY()-1);
				setMoveSpeed(0);			
			}	
			moveCharacterBB();	
		}
		public void moveDown(int add) {
			moveSpeed++;
			move();
			if(moveSpeed>=pocketmon.GAME_DELAY/20&&downState == MOVE_ABLE){
				setY(getY()+1);
				setMoveSpeed(0);
			}	
			moveCharacterBB();
		}
		public void moveLeft(int add) {
			moveSpeed++;
			move();
			if(moveSpeed>=pocketmon.GAME_DELAY/20&&leftState == MOVE_ABLE){
				setX(getX()-1);
				setMoveSpeed(0);			
			}	
			moveCharacterBB();
		}
		public void moveRight(int add) {
			moveSpeed++;
			move();
			if(moveSpeed>=pocketmon.GAME_DELAY/20&&rightState == MOVE_ABLE){
				setX(getX()+1);
				setMoveSpeed(0);
			}		
			moveCharacterBB();
		}
		public BufferedImage getBufferedImg(){
			return npcImg[ImageNum];	//여기서 움직이는 잘라진 객체가 반환된다.	
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
	}public class Man1{
		final static int MOVE_STOP =4;
		final static int MOVE_DOWN = 2;
		final static int MOVE_LEFT = 3;
		final static int MOVE_RIGHT = 1;
		final static int MOVE_UP = 0;
		
		final static int MOVE_ABLE = 1;
		final static int MOVE_UNABLE = 0;
	    
		public int state;		
		public int startXMoveSpeed;
		public int npcFirstSpeed;
		private int moveSpeed;
		private int stepSpeed;
		private int[] stepState;	
		private int i=0;
		
		private SpriteImg sprite;
		private BoundingBox bounding;
		private BufferedImage[] npcImg;
		private BufferedImage man1Front;
		public int moveState=2;	// 이변수는 바운딩 박스 처리할때 방향에 대한 바운딩 또한 처리한다.(평소에는 캐릭터의 움직임 방향)
		private int step=1;
		public int ImageNum=7; //0~2 까지는 위 3~5는 오른쪽 6~8 아래 9~11 왼쪽
		private int x;
		private int y;
		//512,y_pos+1536
		public Man1(Map map) throws IOException {		
			SpriteImg spriteImg = new SpriteImg(4, 3, 64, 64,
					".\\img\\npc\\man1.png");
			state=MOVE_ABLE;
			npcFirstSpeed=0;			
			moveSpeed =0;
			stepSpeed =0;
			npcImg= spriteImg.makeSpriteImg();
			man1Front = ImageIO.read(new File(".\\img\\npc\\man1.png"));
			
			x=map.building.getX_pos()+1390;
			y=map.building.getY_pos()+1950;
			bounding = new BoundingBox(x, y, 64, 64);
			stepState=new int[4];
			stepState[0]=0;
			stepState[1]=1;
			stepState[2]=2;
			stepState[3]=1;
		}
		public BoundingBox getBoundingBox(){
			return bounding;
		}
		public BufferedImage getGoldFrontImg(){
			return man1Front;
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
		}
		public void moveCharacterBB(){
			bounding.setBoundingBox(x, y, 64, 64);		
		}
		public void move()	{
			//step=++step%12;	//step은 계속 0부터 2까지 움직임을 반복한다.		
			stepSpeed++;		
			if(stepSpeed == pocketmon.GAME_DELAY){
				step = stepState[i];			
				i++;
				if(i==4)
					i=0;
				stepSpeed =0;
				ImageNum = (moveState * 3)+step; //여기서 방향을 정한 발 움직임을 보여준다
			}		
		} 
		public void stop(){
			step = 1;	//움직이다 멈췄을때 다리가 정렬된 상태인 step 1로 바꾸어 준다.
			ImageNum = (moveState * 3)+step; //방향을 정해준다.
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
			return npcImg[ImageNum];	//여기서 움직이는 잘라진 객체가 반환된다.	
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
	}public class Man2{
		final static int MOVE_STOP =4;
		final static int MOVE_DOWN = 2;
		final static int MOVE_LEFT = 3;
		final static int MOVE_RIGHT = 1;
		final static int MOVE_UP = 0;
		
		final static int MOVE_ABLE = 1;
		final static int MOVE_UNABLE = 0;
	    
		public int state;		
		public int startXMoveSpeed;
		public int npcFirstSpeed;
		private int moveSpeed;
		private int stepSpeed;
		private int[] stepState;	
		private int i=0;
		
		private SpriteImg sprite;
		private BoundingBox bounding;
		private BufferedImage[] npcImg;
		private BufferedImage man2Front;
		public int moveState=2;	// 이변수는 바운딩 박스 처리할때 방향에 대한 바운딩 또한 처리한다.(평소에는 캐릭터의 움직임 방향)
		private int step=1;
		public int ImageNum=7; //0~2 까지는 위 3~5는 오른쪽 6~8 아래 9~11 왼쪽
		private int x;
		private int y;
		//512,y_pos+1536
		public Man2(Map map) throws IOException {		
			SpriteImg spriteImg = new SpriteImg(4, 3, 64, 64,
					".\\img\\npc\\man2.png");
			state=MOVE_ABLE;
			npcFirstSpeed=0;			
			moveSpeed =0;
			stepSpeed =0;
			npcImg= spriteImg.makeSpriteImg();
			man2Front = ImageIO.read(new File(".\\img\\npc\\man2.png"));
			
			x=map.building.getX_pos()+1390;
			y=map.building.getY_pos()+1950;
			bounding = new BoundingBox(x, y, 64, 64);
			stepState=new int[4];
			stepState[0]=0;
			stepState[1]=1;
			stepState[2]=2;
			stepState[3]=1;
		}
		public BoundingBox getBoundingBox(){
			return bounding;
		}
		public BufferedImage getGoldFrontImg(){
			return man2Front;
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
		}
		public void moveCharacterBB(){
			bounding.setBoundingBox(x, y, 64, 64);		
		}
		public void move()	{
			//step=++step%12;	//step은 계속 0부터 2까지 움직임을 반복한다.		
			stepSpeed++;		
			if(stepSpeed == pocketmon.GAME_DELAY){
				step = stepState[i];			
				i++;
				if(i==4)
					i=0;
				stepSpeed =0;
				ImageNum = (moveState * 3)+step; //여기서 방향을 정한 발 움직임을 보여준다
			}		
		} 
		public void stop(){
			step = 1;	//움직이다 멈췄을때 다리가 정렬된 상태인 step 1로 바꾸어 준다.
			ImageNum = (moveState * 3)+step; //방향을 정해준다.
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
			return npcImg[ImageNum];	//여기서 움직이는 잘라진 객체가 반환된다.	
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

}

