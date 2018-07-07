package Information;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Item {	
	//상점을 만들수도 있겠다
	
	//아래의 넘버로 가방 창에서의 순서를 나타낼수 있다.
	//(아래의 넘버는 순서가 아닌 고유한 아이템 번호이다.
	//그리고 가방의 순서는 RedHave 클래스 에서 정해진다)
	public static int HEALING_POTION_NUM=1;
	public static int MONSTER_BALL_NUM=2;
	public static int RARE_CANDY_NUM =3;
	
	public int potionDelay;
	public int rareCandyDelay;
	public int itemSelectedState;
	public int potionNumState;
	public int ballNumState;
	public int rareCandyNumState;	
	public HealingPotion potion;
	public MonsterBall ball;
	public RareCandy rareCandy;
	public Font itemFont;
	public Font fontBase;
	
	//포션 종류가 추가 될수 있으므로 복잡하더라도 이렇게 하자
	public static int getHealVolume(int potionNum){
		int im=0;
		if(potionNum == HEALING_POTION_NUM){
			im=HealingPotion.HEALING_VALUE;
		}
		return im;
	}

	public Item() throws IOException, FontFormatException {
		potionDelay=0;
		rareCandyDelay=0;
		itemSelectedState =0;
		potionNumState =0;
		ballNumState =0;
		rareCandyNumState =0;
		potion = new HealingPotion();
		ball = new MonsterBall();
		rareCandy = new RareCandy();
		fontBase = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("8bit.[fontvir.us].ttf"));
		itemFont = fontBase.deriveFont(Font.PLAIN, 50);
	}
	public class HealingPotion{
		public static final int HEALING_VALUE= 30;	
		public int number;
		public int changeState;	//감소하는지 증가하는지(RedHave에서 사용)
		public int existState;	//또한 redHave에서 백팩에서의 현재 위치를 표시
		
		public BufferedImage healingPotionVolume;		
		public BufferedImage healingPotionImage; //가방속 이미지
		public BufferedImage healPotionComment;		
		
		public HealingPotion() throws IOException {
			healingPotionVolume = ImageIO.read(new File(".\\img\\item\\healpoint50.png"));
			healingPotionImage= ImageIO.read(new File(".\\img\\item\\healpotion.png"));
			healPotionComment = ImageIO.read(new File(".\\img\\item\\healpotioncomment.png"));
			existState=-1;
			changeState=0;
			number=0;
		}
	}
	public class RareCandy{
		
		public int number;
		public int changeState;
		public int existState;
		
		public BufferedImage rareCandyImage;
		public BufferedImage rareCandyComment;
		public RareCandy() throws IOException {
			// TODO Auto-generated constructor stub
			rareCandyImage = ImageIO.read(new File(".\\img\\item\\rarecandy.png"));
			rareCandyComment = ImageIO.read(new File(".\\img\\item\\rarecandycomment.png"));
			existState=-1;
			changeState=0;
			number=0;
		}
		
	}
	public class MonsterBall{
		
		public int number;
		public int changeState; //감소하는지 증가하는지		
		public int existState;
		
		public BufferedImage monsterBallImage; //가방속 이미지
		public BufferedImage monsterBallComment;
		
		public MonsterBall() throws IOException {
			monsterBallImage= ImageIO.read(new File(".\\img\\item\\monsterball.png"));
			monsterBallComment = ImageIO.read(new File(".\\img\\item\\monsterballcomment.png"));
			existState=-1;
			changeState=0;
			number=0;
		}
	}
}