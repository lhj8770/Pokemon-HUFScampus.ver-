package Pocketmon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Character{
	final static int MOVE_STOP =4;
	final static int MOVE_DOWN = 2;
	final static int MOVE_LEFT = 3;
	final static int MOVE_RIGHT = 1;
	final static int MOVE_UP = 0;
	final static int MEET= 1;
	final static int MEET_NOT =0;
	
	private static Character characterInstance;
	
	public int startXMoveSpeed;
	public int xMoveSpeed; //초기 화면 캐릭터 움직임 속도
	public int firstRedBackX;

	private int stepSpeed;
	private int[] stepState;
	private int i;
	private BoundingBox bounding;
	private BufferedImage[] redImg;
	private BufferedImage redBack;
	private int moveState;	
	private int step;
	private int ImageNum;
	private int x;
	private int y;
	private int meetState;//npc와 만났을 경우
	private SpriteImg spriteImg;
	
	private Character() throws IOException {
		moveState=2; // 이변수는 바운딩 박스 처리할때 방향에 대한 바운딩 또한 처리한다.(평소에는 캐릭터의 움직임 방향)
		step=1;
		ImageNum=7; //0~2 까지는 위 3~5는 오른쪽 6~8 아래 9~11 왼쪽
		firstRedBackX=-30;
		stepSpeed=0;
		i=0;
		spriteImg = new SpriteImg(4, 3, 64, 64,
				".\\img\\red\\red.png");
		redImg = spriteImg.makeSpriteImg();
		redBack=ImageIO.read(new File("./img/red/redback.png"));
		meetState= MEET_NOT;
		x=Pocketmon.FRAME_H/2-32;
		y=Pocketmon.FRAME_W/2-80;	//64는 캐릭터의 크기이고 16은 바운딩 박스 이다.(바운딩 박스가 캐릭터 보다 조금 아래 있다.크기는 동일)
		bounding = new BoundingBox(x+8, y+16, 50, 50);
		stepState=new int[4];
		stepState[0]=0;
		stepState[1]=1;
		stepState[2]=2;
		stepState[3]=1;
	}
	
	public static Character getInstance(){
		if(characterInstance==null){
			try{
				characterInstance= new Character();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return characterInstance;
	}
	
	public void InitStartXMoveSpeed(int gameDelay){
		xMoveSpeed=startXMoveSpeed=100/gameDelay;
	}
	public BoundingBox getBoundingBox(){
		return bounding;
	}

	public void move()	{
		//step=++step%12;	//step은 계속 0부터 2까지 움직임을 반복한다.
		stepSpeed++;
		if(stepSpeed == 4){
			step = stepState[i];
			i++;
			if(i==4)
				i=0;
			stepSpeed =0;
		}
		ImageNum = (moveState * 3)+step; //여기서 방향을 정한 발 움직임을 보여준다		
	} 
	public void stop(){
		step = 1;	//움직이다 멈췄을때 다리가 정렬된 상태인 step 1로 바꾸어 준다.
		ImageNum = (moveState * 3)+step; //방향을 정해준다.
	}
	public BufferedImage getBufferedImg(){
		return redImg[ImageNum];	//여기서 움직이는 잘라진 객체가 반환된다.	
	}	
	public BufferedImage getRedBackImg(){
		return redBack;
	}
	public int getMoveState() {
		return moveState;
	}
	public void setMoveState(int moveState) {
		this.moveState = moveState;
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
	public void setMeetState(int State){
		meetState = State;
	}
	public int getMeetState(){
		return meetState;
	}
}
