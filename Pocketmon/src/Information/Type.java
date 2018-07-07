


package Information;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

// 공격할때 호출되며 기술과 몬스터와의 상성 비교를 한다

public class Type { 
	public static int TYPE_NUM=1;
	public static String NONE = "none";
	public static String NORMAL = "normal";
	public static String WATER = "water";
	public static String FIRE = "fire";
	public static String GRASS = "grass";
	public static String SPECIAL = "special";
	public String self;
	public ArrayList<String>advantage;
	public ArrayList<String>disadvantage;
	
	private BufferedImage fire;
	private BufferedImage water;
	private BufferedImage normal;
	private BufferedImage grass;
	
	public Type(String self) throws IOException {
		this.self = new String(self);
		advantage = new ArrayList<String>();
		disadvantage = new ArrayList<String>();
		fire= ImageIO.read(new File(".\\img\\type\\fire.png"));
		water= ImageIO.read(new File(".\\img\\type\\water.png"));
		normal= ImageIO.read(new File(".\\img\\type\\normal.png"));
		grass= ImageIO.read(new File(".\\img\\type\\grass.png"));
		setTypeDifference();
	}
	public BufferedImage getTypeImage(){
		BufferedImage state = null;
		if(self.equals(FIRE))
			state = fire;
		if(self.equals(NORMAL))
			state = normal;
		if(self.equals(WATER))
			state = water;
		if(self.equals(GRASS))
			state = grass;
		return state;
	}
	//기술 입장에서의 상성 역상성이다.(데미지 계산이 기술 입장에서 이루어지기 때문)
	//ArrayList를 사용한 이유는 나중에 타입이 추가될수 있기 때문이다.
	public void setTypeDifference(){
		if(self.equals(WATER)){			
			advantage.add(FIRE);
			disadvantage.add(GRASS);
		}else if(self.equals(FIRE)){
			advantage.add(GRASS);
			disadvantage.add(WATER);
		}else if(self.equals(GRASS)){
			advantage.add(WATER);
			disadvantage.add(FIRE);
		}else if(self.equals(NORMAL)){
			advantage.add(NONE);
			disadvantage.add(NONE);
		}
	}
	//여기서 몬스터는 공격을 받는 입장이고 스킬은 공격하는 입장이 된다.
	//리턴값으로는 각각 상성의 배율을 리턴한다.
	public double getAttackRate(Type skill, Type monster){
		double rate=1.0;	// for 문에 검출이 되지 않으면 normal 속성 배율이 리턴
		//skill의 어드벤티지 항목에 monster 자신의 타입이 들어 가 있다면 2배의 배율을, 반대는 0.5
		for(int i=0;i<TYPE_NUM;i++){
			if(skill.self.equals(monster.advantage.get(i))){
				rate = 0.5;
				break;
			}
			if(skill.self.equals(monster.disadvantage.get(i))){
				rate = 2.0;
				break;
			}
		}
		return rate;
	}	
}
