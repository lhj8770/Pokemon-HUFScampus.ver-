package Information;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Pokemon {
	public static int NONE = -1;
	public static int GGOBUGI_NUM = 1;
	public static int CHIKORITA_NUM = 2;
	public static int LEEAKO_NUM = 3;
	public static int LEESANG_NUM = 4;
	public static int PAEERI_NUM = 5;
	public static int VCAIN_NUM = 6;
	public static int PURIN_NUM = 7;
	public static int ONEYBUGI_NUM =8;
	public static int BAYLEAF_NUM =9;
	public static int ELIGAY_NUM = 10;
	public static int SANGFULL_NUM = 11;
	public static int ZARD_NUM =12;
	public static int MAGCAIN_NUM =13;
	public static int GGOBUKING_NUM =14;
	public static int MEGANIUM_NUM =15;
	public static int ZANGCRO_NUM =16;
	public static int SANGFLOWER_NUM =17;
	public static int ZAMONG_NUM =18;
	public static int BLAY_NUM =19;
	public static int POKEMON_ALL_NUM = 20;
	public static int FIRST_LEVEL = 10;
	
	public static int STUN = 21;
	public static int ALIVE = 20;
	
	public int increaseHPDelay;
		
	public int realDamage;
	public double damage;
	public Skill skill;
	
	public Type useTypeMethod;

	private PokemonKind ggobugi;
	private PokemonKind chikorita;
	private PokemonKind leeako;
	private PokemonKind leesang;
	private PokemonKind paeeri;
	private PokemonKind vcain;
	private PokemonKind purin;
	private PokemonKind oneybugi;
	private PokemonKind bayleaf;
	private PokemonKind eligay;
	private PokemonKind sangfull;
	private PokemonKind zard;
	private PokemonKind magcain;
	private PokemonKind ggobuking;
	private PokemonKind meganium;
	private PokemonKind zangcro;
	private PokemonKind sangflower;
	private PokemonKind zamong;
	private PokemonKind blay;
	private ArrayList<PokemonKind> pokemonKindArray;
	
	public Font levelFont;
	public Font fontBase;

	public Pokemon() throws IOException, FontFormatException {
		
		useTypeMethod = new Type(Type.NORMAL);
		skill= Skill.getSkillInstance();
		createPokemonInstance();
		pokemonKindArray = new ArrayList<PokemonKind>();
		pokemonKindArray.add(ggobugi);
		pokemonKindArray.add(chikorita);
		pokemonKindArray.add(leeako);
		pokemonKindArray.add(leesang);
		pokemonKindArray.add(paeeri);
		pokemonKindArray.add(vcain);
		pokemonKindArray.add(purin);
		pokemonKindArray.add(oneybugi);
		pokemonKindArray.add(bayleaf);
		pokemonKindArray.add(eligay);
		pokemonKindArray.add(sangfull);
		pokemonKindArray.add(zard);
		pokemonKindArray.add(magcain);
		pokemonKindArray.add(ggobuking);
		pokemonKindArray.add(meganium);
		pokemonKindArray.add(zangcro);
		pokemonKindArray.add(sangflower);
		pokemonKindArray.add(zamong);
		pokemonKindArray.add(blay);
		
		increaseHPDelay=0;
		fontBase = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream("8bit.[fontvir.us].ttf"));
		levelFont = fontBase.deriveFont(Font.PLAIN, 50);
	}
	void createPokemonInstance() throws IOException, FontFormatException{
		ggobugi = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.BUBBLE_NUM},Type.WATER,"ggobugi",44,48,65,43,18,GGOBUGI_NUM,ONEYBUGI_NUM,10);
		chikorita = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.LEAF_NUM},Type.GRASS,"chikorita",45,49,65,45,16,CHIKORITA_NUM,BAYLEAF_NUM,10);
		leeako = new PokemonKind(new int[]{Skill.SCRATCH_NUM,Skill.WATERGUN_NUM},Type.WATER,"leeako",50,65,64,43,18,LEEAKO_NUM,ELIGAY_NUM,10); 
		leesang = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.LEAF_NUM},Type.GRASS,"leesang",45,49,49,45,16,LEESANG_NUM,SANGFULL_NUM,10);
		paeeri = new PokemonKind(new int[]{Skill.SCRATCH_NUM,Skill.FIRESHOT_NUM},Type.FIRE,"paeeri",39,52,43,65,16,PAEERI_NUM,ZARD_NUM,10);
		vcain = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.FIRESHOT_NUM},Type.FIRE,"vcain",39,52,43,65,14,VCAIN_NUM,MAGCAIN_NUM,10);
		purin = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.SCRATCH_NUM},Type.NORMAL,"purin",115,45,20,20,NONE,PURIN_NUM,NONE,20);
		oneybugi = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.BUBBLE_NUM,Skill.BUBBLEBEAM_NUM},Type.WATER,"oneybugi",59,63,80,58,36,ONEYBUGI_NUM,GGOBUGI_NUM,18);
		bayleaf = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.LEAF_NUM,Skill.SUNEAT_NUM},Type.GRASS,"bayleaf",60,62,80,60,32,BAYLEAF_NUM,MEGANIUM_NUM,16);
		eligay = new PokemonKind(new int[]{Skill.SCRATCH_NUM,Skill.WATERGUN_NUM,Skill.ICEPUNCH_NUM},Type.WATER,"eligay",65,80,80,58,30,ELIGAY_NUM,ZANGCRO_NUM,18);
		sangfull = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.LEAF_NUM,Skill.OMUL_NUM},Type.GRASS,"sangfull",60,62,63,60,32,SANGFULL_NUM,SANGFLOWER_NUM,16);
		zard = new PokemonKind(new int[]{Skill.SCRATCH_NUM,Skill.FIRESHOT_NUM,Skill.DRAGONANGRY_NUM},Type.FIRE,"zard",58,64,58,80,36,ZARD_NUM,ZAMONG_NUM,16);
		magcain = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.FIRESHOT_NUM,Skill.LIGHTNINGPUNCH_NUM},Type.FIRE,"magcain",58,64,58,80,36,MAGCAIN_NUM,BLAY_NUM,14);		
		ggobuking = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.BUBBLE_NUM,Skill.BUBBLEBEAM_NUM,Skill.ICEBEAM_NUM},Type.WATER,"ggobuking",79,83,100,80,NONE,GGOBUKING_NUM,NONE,36);
		meganium = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.LEAF_NUM,Skill.SUNEAT_NUM,Skill.SOLABEAM_NUM},Type.GRASS,"meganium",80,82,100,80,NONE,MEGANIUM_NUM,NONE,32);
		zangcro = new PokemonKind(new int[]{Skill.SCRATCH_NUM,Skill.WATERGUN_NUM,Skill.ICEPUNCH_NUM,Skill.HYDROPUMP_NUM},Type.WATER,"zangcro",85,105,100,78,NONE,ZANGCRO_NUM,NONE,30);
		sangflower = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.LEAF_NUM,Skill.OMUL_NUM,Skill.SOLABEAM_NUM},Type.GRASS,"sangflower",80,82,83,80,NONE,SANGFLOWER_NUM,NONE,32);
		zamong = new PokemonKind(new int[]{Skill.SCRATCH_NUM,Skill.FIRESHOT_NUM,Skill.DRAGONANGRY_NUM,Skill.FLAME_NUM},Type.FIRE,"zamong",78,84,78,100,NONE,ZAMONG_NUM,NONE,36);
		blay = new PokemonKind(new int[]{Skill.MOMTONG_NUM,Skill.FIRESHOT_NUM,Skill.LIGHTNINGPUNCH_NUM,Skill.BIGFIRE_NUM},Type.FIRE,"blay",78,84,78,100,NONE,BLAY_NUM,NONE,36);

	}
	//(위력*공격*(레벨*2/5+2)/방어/50 +2)*(공격자속성,피해자속성))
	//매개 변수로 자기 자신의 포켓몬과 (~Have를 통해 접근 가능한 Pokemon 클래스) 포켓몬 넘버 , 스킬넘버, 적의 포켓몬 과 적의 포켓몬 넘버 가 
	//필요하다.  반환 되는 값은 공격받은 포켓몬이 기절 했나 안했나 이다
	public int getDamage(Pokemon self,int selfPokemonNum,int skillNum , Pokemon enemy, 
			int enemyPokemonNum){
		int stunState= ALIVE;
		double power = skill.getSkillPower(skillNum);
		int attack = self.getAttack(selfPokemonNum);
		int level = self.getLevel(selfPokemonNum);
		int enemyDefend = enemy.getdefend(enemyPokemonNum);
		double properties = useTypeMethod.getAttackRate
				(skill.getSkillType(skillNum),getSelfType(enemyPokemonNum));//(0.5 , 1 , 2)
		/*	
		// 2세대
		damage = (power*attack*(level*2/5+2)/enemyDefend/50+2)*properties;
		*/
		// 3세대
		damage = (((((level*2/5)+2)*power*attack/50)/enemyDefend)+2)*properties;
		realDamage=(int)damage;		
		
		for(int i=0;i<realDamage;i++)
			if(enemy.getCurrentHP(enemyPokemonNum)>0){	//0이 아니라면 decrease 메소드 부른다
				enemy.decreaseCurrentHP(enemyPokemonNum);				
			}else{	//만약에 적 포켓몬의 HP가 0에 도달했다면 기절
				stunState=STUN;
			}
		return stunState;	//확인용으로 반환 한다.
	}
	public void healingSkillUse(int pokemonNum,int skillNum){		
		for(int i=0;i<skill.eachSkillPowerHealPoint(skillNum);i++){
			if(pokemonKindArray.get(pokemonNum-1).currentHp<pokemonKindArray.get(pokemonNum-1).hp){
				pokemonKindArray.get(pokemonNum-1).currentHp++;
			}
		} 
	}
	public ArrayList<PokemonKind> getPokemonKindArray(){return pokemonKindArray;}
	public int getEvolLevel(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).evolLevel;}
	public BufferedImage getEvolImg(int pokemonNum){return pokemonKindArray.get(
			pokemonKindArray.get(pokemonNum-1).getEvolPokemonNum()-1).enemySide;}
	public BufferedImage getEvolName(int pokemonNum){return pokemonKindArray.get(
			pokemonKindArray.get(pokemonNum-1).getEvolPokemonNum()-1).battleComment;}
	
	public void increaseLevel(int pokemonNum){pokemonKindArray.get(pokemonNum-1).levelUp();}
	public int getAttack(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).attack;}	
	public void setCurrentHp(int pokemonNum,int hp){pokemonKindArray.get(pokemonNum-1).currentHp=hp;}	
	public ArrayList<Integer> getSkillList(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).skillList;}
	public int getSkillNum(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).skillList.size();}
	public BufferedImage getBattleName(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).getBattleComment();}
	public BufferedImage getEnemySide(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).getEnemySide();}
	public BufferedImage getMySide(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).getMySide();}
	public int getLevel(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).level;}
	public int getdefend(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).defend;}
	public void healThePokemon(int pokemonNum,int potionNum){	
		for(int i=0;i<Item.getHealVolume(potionNum);i++){
			if(pokemonKindArray.get(pokemonNum-1).currentHp<pokemonKindArray.get(pokemonNum-1).hp){
				pokemonKindArray.get(pokemonNum-1).currentHp++;
			}
		}
	}
	public int getCurrentHP(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).currentHp;}
	public void decreaseCurrentHP(int pokemonNum){pokemonKindArray.get(pokemonNum-1).currentHp--;}
	public int getSpeed(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).speed;}
	public int getHP(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).hp;}
	public Type getSelfType(int pokemonNum){return pokemonKindArray.get(pokemonNum-1).cType;}
	
	public class PokemonKind{
		public static final int SKILL_MAX_NUM=4;
		//battle ball의 state 이다 (몬스터 기절 상태도 나타낼수있다.)
		public static final int NOT_STUNED = 0;
		public static final int POISONED = 1;
		public static final int STUNED = 2;		
		public static final int NOEXIST = 3;
		
		private int evolLevel=-1;
		private int attributeHp;
		private int attributeAttack;
		private int attributeDefend;
		private int attributeSpeed;
		
		private int pokemonNum;
		private int evolPokemonNum;
		private int ballState;		
		private Type cType;
		private int currentHp;
		private int hp;
		private int level;
		private int attack;
		private int defend;
		private int speed;
		
		private String type;
		private BufferedImage stun;
		private BufferedImage enemySide;
		private BufferedImage mySide;
		private BufferedImage bagHpFrame;
		private BufferedImage bagComment;
		private BufferedImage battleComment;
		private ArrayList<Integer> skillList;
		private int[] firstSkillArray;

		public PokemonKind(int[] firstSkillArray, String type, String pokemonName,
				int attributeHp,int attributeAttack, int attributeDefend, int attributeSpeed,
				int evolLevel,int pokemonNum,int evolPokemonNum,int firstLevel) throws IOException, FontFormatException {
			
			//constructor
			this.firstSkillArray = firstSkillArray;
			this.attributeHp = attributeHp;
			this.attributeAttack = attributeAttack;
			this.attributeDefend = attributeDefend;
			this.attributeSpeed = attributeSpeed;
			this.type = type;
			this.evolLevel = evolLevel;
			this.pokemonNum = pokemonNum;
			this.evolPokemonNum = evolPokemonNum;
			this.level = firstLevel;
			//default
			ballState =NOT_STUNED;			
			cType = new Type(type);			
			stun = ImageIO.read(new File(".\\img\\pokemon\\"+pokemonName+"\\stun.png"));
			bagHpFrame = ImageIO.read(new File(".\\img\\pokemon\\"+pokemonName+"\\HPframe.png"));
			enemySide = ImageIO.read(new File(".\\img\\pokemon\\"+pokemonName+"\\"+pokemonName+"front.png"));
			mySide = ImageIO.read(new File(".\\img\\pokemon\\"+pokemonName+"\\"+pokemonName+"back.png"));
			bagComment = ImageIO.read(new File(".\\img\\pokemon\\"+pokemonName+"\\"+pokemonName+"icon.png"));
			battleComment = ImageIO.read(new File(".\\img\\pokemon\\"+pokemonName+"\\"+pokemonName+"name.png"));
//			System.out.println(enemySide);
			skillList=new ArrayList<Integer>();
			setFirstSkillList();
			setHp();
			setRest();
			currentHp=hp;
		}
		public int getPokemonNum(){return pokemonNum;}
		
		public int getBallState() {return ballState;}

		public void setBallState(int ballState) {this.ballState = ballState;}
		
		public int getHp() {return hp;}

		public void setHp(int hp) {this.hp = hp;}

		public int getCurrentHp() {return currentHp;}

		public void setCurrentHp(int currentHp) {this.currentHp = currentHp;}

		public int getLevel() {return level;}
		
		public int getEvolPokemonNum() {return evolPokemonNum;}
		
		public BufferedImage getEnemySide() {return enemySide;}
		
		public BufferedImage getMySide() {return mySide;}
		
		public BufferedImage getBagHpFrame() {return bagHpFrame;}
	
		public BufferedImage getBagComment() {return bagComment;}
	
		public BufferedImage getBattleComment() {return battleComment;}
		public BufferedImage getStun(){return stun;}	
		public void levelUp(){
			level++;
			setHp();
			currentHp = hp;
			setRest();
		}
		public void setRest(){
			attack = attributeAttack*2*level/100+5;
			defend = attributeDefend*2*level/100+5;
			speed =  attributeSpeed*2*level/100+5;
		}
		public void setHp(){hp =  attributeHp*level/50+10+level;}
		public void setLevel(int level){
			this.level = level;
			setHp();
			currentHp = hp;
			setRest();
		}
		private void setFirstSkillList (){
			for(int i : firstSkillArray){
				skillList.add(i);
			}
//			skillList.add(Skill.MOMTONG_NUM);
//			skillList.add(Skill.SCRATCH_NUM);
		}
		public void addSkill(int skillNum){
			if(skillList.size()!=SKILL_MAX_NUM)
				skillList.add(skillNum);
		}
	}
}
