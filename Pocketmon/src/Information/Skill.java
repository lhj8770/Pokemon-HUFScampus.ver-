package Information;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

//스킬은 각각의 포켓몬이 공유하기때문에 각 포켓몬에게 보유 state를 주어서 관리한다.
public class Skill {

	public static int BUBBLE_NUM = 1;
	public static int DUNG_NUM = 2;
	public static int FIRESHOT_NUM = 3;
	public static int LEAF_NUM = 4;
	public static int MOMTONG_NUM = 5;
	public static int SCRATCH_NUM = 6;
	public static int WATERGUN_NUM = 7;
	public static int BIGFIRE_NUM = 8;
	public static int BUBBLEBEAM_NUM = 9;
	public static int DRAGONANGRY_NUM = 10;
	public static int FLAME_NUM = 11;
	public static int HYDROPUMP_NUM = 12;
	public static int ICEBEAM_NUM = 13;
	public static int ICEPUNCH_NUM = 14;
	public static int LIGHTNINGPUNCH_NUM = 15;
	public static int OMUL_NUM = 16;
	public static int SOLABEAM_NUM = 17;
	public static int SUNEAT_NUM = 18;
	
	private SkillKind bubble;
	private SkillKind dung;
	private SkillKind fireShot;
	private SkillKind leaf;
	private SkillKind momtong;
	private SkillKind scratch;
	private SkillKind waterGun;
	private SkillKind bigFire;
	private SkillKind bubbleBeam;
	private SkillKind dragonAngry;
	private SkillKind flame;
	private SkillKind hydroPump;
	private SkillKind iceBeam;
	private SkillKind icePunch;
	private SkillKind lightningPunch;
	private SkillKind omul;
	private SkillKind solaBeam;
	private SkillKind sunEat;
	private ArrayList<SkillKind> skillArray;
	private static Skill instance ;
	
	Runnable r;
	Thread t;

	// 각각의 넘버에 알맞는 기술 객체를 생성한다.
	private Skill() throws IOException {		
		bubble = new SkillKind("bubble", Type.WATER,BUBBLE_NUM,40);
		dung = new SkillKind("dung",Type.GRASS,DUNG_NUM,45);
		fireShot = new SkillKind("fireShot",Type.FIRE,FIRESHOT_NUM,40);
		leaf= new SkillKind("leaf", Type.GRASS,LEAF_NUM,55);
		momtong= new SkillKind("momtong",Type.NORMAL,MOMTONG_NUM,50);
		scratch= new SkillKind("scratch",Type.NORMAL,SCRATCH_NUM,40);
		waterGun= new SkillKind("waterGun",Type.WATER,WATERGUN_NUM,40);
		bigFire= new SkillKind("bigFire",Type.FIRE,BIGFIRE_NUM,110);
		bubbleBeam= new SkillKind("bubbleBeam",Type.WATER,BUBBLEBEAM_NUM,90);
		dragonAngry= new SkillKind("dragonAngry",Type.NORMAL,DRAGONANGRY_NUM,110);
		flame= new SkillKind("flame",Type.FIRE,FLAME_NUM,90);
		hydroPump= new SkillKind("hydroPump",Type.WATER,HYDROPUMP_NUM,110);
		iceBeam= new SkillKind("iceBeam",Type.WATER,ICEBEAM_NUM,90);
		icePunch= new SkillKind("icePunch",Type.NORMAL,ICEPUNCH_NUM,75);
		lightningPunch= new SkillKind("lightningPunch",Type.NORMAL,LIGHTNINGPUNCH_NUM,75);
		omul= new SkillKind("omul",Type.NORMAL,OMUL_NUM,90);
		solaBeam= new SkillKind("solaBeam",Type.GRASS,SOLABEAM_NUM,110);
		sunEat= new SkillKind("sunEat",Type.SPECIAL,SUNEAT_NUM,50);
		
		skillArray = new ArrayList<SkillKind>();
		skillArray.add(bubble);
		skillArray.add(dung);
		skillArray.add(fireShot);
		skillArray.add(leaf);
		skillArray.add(momtong);
		skillArray.add(scratch);
		skillArray.add(waterGun);
		skillArray.add(bigFire);
		skillArray.add(bubbleBeam);
		skillArray.add(dragonAngry);
		skillArray.add(flame);
		skillArray.add(hydroPump);
		skillArray.add(iceBeam);
		skillArray.add(icePunch);
		skillArray.add(lightningPunch);
		skillArray.add(omul);
		skillArray.add(solaBeam);
		skillArray.add(sunEat);
//		System.out.println("3");
		
		//skill loading thread
		r = new Runnable() {

			@Override
			public void run() {
					try {
						for(SkillKind i : skillArray){
							loadingSkillImage(i.skillName);
//							System.out.println("33");
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
			}

		};
		t = new Thread(r);
		t.start();

	}
	//singleton
	public static Skill getSkillInstance() throws IOException{
		if(instance == null){
			instance = new Skill();
		}
		return instance;
	}
	
	public class SkillKind{
		public BufferedImage [] mySideEffect;
		public BufferedImage [] enemySideEffect;
		public BufferedImage skillImage;
		public int effectNum;
		public int typeNum;
		public double damage;
		public Type type;
		File file;
		public String skillName;
		public SkillKind(String skillName,String skillType, int typeNum,int damage) throws IOException{
			
			this.skillName = skillName;
			file = new File(".\\img\\skill\\"+skillName+"\\enemy");			
			effectNum = file.listFiles().length;
//			System.out.println(effectNum);
			mySideEffect = new BufferedImage[effectNum];
			enemySideEffect = new BufferedImage[effectNum];
			skillImage = ImageIO.read(new File(".\\img\\skill\\"+skillName+".png"));
//			System.out.println(skillImage);
			type = new Type(skillType);
			this.damage = damage; 
			this.typeNum  =  typeNum;
		}
	}


	public double eachSkillPowerHealPoint(int skillNum){return skillArray.get(skillNum-1).damage;}

	public BufferedImage getSkillName(int skillNum){return skillArray.get(skillNum-1).skillImage;}

	public BufferedImage[] getMySideEffect(int skillNum) throws IOException {return skillArray.get(skillNum-1).mySideEffect;}

	public BufferedImage[] getEnemySideEffect(int skillNum) throws IOException {return skillArray.get(skillNum-1).enemySideEffect;}

	public int getEffectNum(int skillNum) {return skillArray.get(skillNum-1).effectNum;}
	public SkillKind getSkillKindInstance(String skillName){
		SkillKind tmp=null;
		for(SkillKind i : skillArray){
			if(i.skillName.equals(skillName)){
				tmp = i;
			}
		}
		return tmp;
	}
	public void loadingSkillImage(String skillName) throws IOException{
//		System.out.println(getSkillKindInstance(skillName).effectNum);
		for(int i=0;i<getSkillKindInstance(skillName).effectNum;i++){
//			System.out.println(getSkillKindInstance(skillName).effectNum);
			if(new File(".\\img\\skill\\"+skillName+"\\my\\"+(i+1)+".png").isFile()){
				getSkillKindInstance(skillName).mySideEffect[i] = ImageIO.read(new File(".\\img\\skill\\"+skillName+"\\my\\"+(i+1)+".png"));
				getSkillKindInstance(skillName).enemySideEffect[i] = ImageIO.read(new File(".\\img\\skill\\"+skillName+"\\enemy\\"+(i+1)+".png"));
//				System.out.println(bubble.mySideEffect[i]);
			}
		}
	}
	public Type getSkillType(int skillNum) {return skillArray.get(skillNum-1).type;}
	public double getSkillPower(int skillNum) {return skillArray.get(skillNum-1).damage;}
}