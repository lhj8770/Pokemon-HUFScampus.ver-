package Pocketmon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.DebugGraphics;

public class Battle {	
	//Singleton pattern
	private static Battle battleInstance;
	
	public static int BATTLE_IN = 1;
	public static int BATTLE_NO = 0;
	public static int INI_1 =0;
	public static int INI_2 =1;
	public static int INI_3 =2;
	public static int INI_4 =3;
	public static int START = -2;
	
	//playerDoneState にかかわるコンスタント(キャラクターとnpcの行動が決められる)
	public static int MY_ITEM_PRI =10;
	public static int MY_CHANGE1_PRI =11;
	public static int MY_CHANGE2_PRI =12;
	public static int MY_CHANGE3_PRI = 29;
	public static int MY_ATTACK_PRI = 13;
	public static int ENEMY_TURN =14;
	public static int MY_TURN = 25;
	//enemy priority 設定コンスタント
	public static int ENEMY_ATTACK_PRI =15;
	public static int ENEMY_ITEM1_PRI =16;
	public static int ENEMY_ITEM2_PRI =17;
	public static int ENEMY_CHANGE1_PRI =18;
	public static int ENEMY_CHANGE2_PRI =19;
	
	//気絶に関するコンスタント
	public static int ENEMY_DEAD =-13;
	public static int ENEMY_FILL1 =-14;
	public static int ENEMY_FILL2 =-15;
	public static int ENEMY_LOSE1 =-16;
	public static int ENEMY_LOSE2 =-17;
	public static int MY_DEAD=-10;
	public static int MY_FILL1=-9;
	public static int MY_FILL2=-8;
	public static int MY_LOSE1=-7;
	public static int MY_LOSE2=-6;
	
	public static int YES =1;
	public static int NO =0;
	public static int RUN_ABLE =1;
	public static int RUN_DISABLE =0;
	
	public static int COMMAND_SELECT = 4;
	
	public static int SKILL_SELECT =5;

	//npcが最初に出すポケモンのインデクス
	public static int ENEMY_FIRST_POKEMON = 0;
	
	//command select 選択目録
	public static int FIGHT =0;
	public static int BACK_PACK =1;
	public static int POKEMON =2;
	public static int RUN =3;
	
	public static int CANT_RUN_1 =1;
	public static int CANT_RUN_2 =2;
	public static int RUNABLE_1 =3;
	public static int RUNABLE_2 =4;
	
	public static int NONE =-1;
	public static int BATTLE_POSITION =0;
	

	public int stunState;

	//演出の途中に入力を防ぐためのstate
	public int inputBlock;
	
	//キャラクターの行動終了state
	public int PlayerDoneState;
	
	//このstateでマップ上のnpcが消えるようにできる。
	public int winState;
	
	//バトル最初にキャラクターが持っているポケモンの中でインデクスの上から調べて気絶していないポケモンを出す。
	public int startStillAliveIndex;
	public int myPokemonIndex;		
	public int startCommentState;
	
	//敵の現在出ているポケモンのindex
	public int enemyPokemonIndex;

	//アイテムの使用 state
	public int itemUsedNumState;
	
	public int runState;
	public int itemToPokemon;
	
	public int commandSelectPointer;
	public int skillSelectPointer;
	
	public int battleSkillState;
	
	public int battleLoopState;
	
	//バトルに使われるディレイ
	public int potionDelay;
	public int changeDelay;
	public int enemyDelay;
	public int attackDelay;
	public int deadDelay;
	//スキルエフェクトのディレイ
	public int skillEffectDelay;
	
	//敵が倒れた場合にai.stateを一度だけ受け取るstate
	public int enemyDeadChangeState;
	
	//スキルエフェクトのインデクス
	public int skillEffectIndex;
	
	//バトルの優先順位
	public int enemyPriority;
	
	public int battleState;
		
	public int wildPokemonMeetState;
	
	public Position position; 
	
	// フレームイメージ
	public SpriteImg spriteBall;
	public BufferedImage pokemonStatusFrame;
	public BufferedImage[] ballIamge;
	public BufferedImage myHpFrame;
	public BufferedImage enemyHpFrame;
	public BufferedImage commandFrameSkillSelect;
	public BufferedImage commandFrameItemUseSelect;
	public BufferedImage commandFramePokemonSelect;
	public BufferedImage battleComentFrame;
	public BufferedImage commandFrame;
	public BufferedImage commentFrame;
	public BufferedImage pointer;
	
	// コメントイメージ
	public BufferedImage beHeal;
	public BufferedImage blind;
	public BufferedImage cantRunComment1;
	public BufferedImage cantRunComment2;
	public BufferedImage comeBack;
	public BufferedImage enemy;
	public BufferedImage enemyLoseComment;
	public BufferedImage exclamationText;
	public BufferedImage falling;
	public BufferedImage goText;
	public BufferedImage hpGa;
	public BufferedImage loser;
	public BufferedImage no;
	public BufferedImage nomore;
	public BufferedImage OOeega;
	public BufferedImage OOege;
	public BufferedImage OOeul;
	public BufferedImage OOis;
	public BufferedImage OOui;
	public BufferedImage redName;
	public BufferedImage sequenceText;
	public BufferedImage showBooText;
	public BufferedImage takeOut;
	public BufferedImage trainerText;
	public BufferedImage use;
	public BufferedImage winText;
	
	
	private Battle() throws IOException {
		enemyDeadChangeState=NONE;
		skillEffectIndex=0;
		runState=NONE;
		skillEffectDelay=0;
		startCommentState=NONE;
		battleLoopState=NONE;
		battleSkillState=NONE;
		battleState=BATTLE_NO;
		enemyPokemonIndex=ENEMY_FIRST_POKEMON;
		itemToPokemon=NO;
		PlayerDoneState=NO;
		commandSelectPointer=0;
		skillSelectPointer=0;
		itemUsedNumState=NONE;
		enemyPriority=NONE;
		winState=NONE;
		potionDelay=0;
		changeDelay=0;
		attackDelay=0;
		deadDelay=0;
		inputBlock=NONE;
		stunState=NONE;
		myPokemonIndex=BATTLE_POSITION;
		wildPokemonMeetState=NONE;
		startStillAliveIndex=0;
		enemyDelay=0;
		// frame Image load
		spriteBall= new SpriteImg(4,1,28,28,"./img/battle/frame/monsterstatus.png");
		ballIamge=spriteBall.makeSpriteImg();
		battleComentFrame=ImageIO.read(new File("./img/battle/frame/battlecomentFrame.png"));
		commandFrame=ImageIO.read(new File("./img/battle/frame/commandframe.png"));
		commandFrameItemUseSelect=ImageIO.read(new File("./img/battle/frame/commandframe_ItemUseselect.png"));;
		commandFramePokemonSelect=ImageIO.read(new File("./img/battle/frame/commandframe_pokemonselect.png"));;
		commandFrameSkillSelect=ImageIO.read(new File("./img/battle/frame/commandframe_skillselect.png"));
		commentFrame=ImageIO.read(new File("./img/battle/frame/comentframe.png"));
		enemyHpFrame=ImageIO.read(new File("./img/battle/frame/enemyHPframe.png"));
		myHpFrame=ImageIO.read(new File("./img/battle/frame/myHPframe.png"));
		pokemonStatusFrame=ImageIO.read(new File("./img/battle/frame/pokemonstatusframe.png"));
		pointer=ImageIO.read(new File("./img/battle/frame/pointer.png"));
		// comment Image load
		beHeal=ImageIO.read(new File("./img/battle/comment/Beheal.png"));
		blind=ImageIO.read(new File("./img/battle/comment/blind.png"));
		cantRunComment1=ImageIO.read(new File("./img/battle/comment/cantruncomment1.png"));
		cantRunComment2=ImageIO.read(new File("./img/battle/comment/cantruncomment2.png"));
		comeBack=ImageIO.read(new File("./img/battle/comment/comeback.png"));
		enemy=ImageIO.read(new File("./img/battle/comment/enemy.png"));
		enemyLoseComment=ImageIO.read(new File("./img/battle/comment/enemylosecomment.png"));
		exclamationText=ImageIO.read(new File("./img/battle/comment/exclamationtext.png"));
		falling=ImageIO.read(new File("./img/battle/comment/falling.png"));
		goText=ImageIO.read(new File("./img/battle/comment/gotext.png"));
		hpGa=ImageIO.read(new File("./img/battle/comment/HpGa.png"));
		loser=ImageIO.read(new File("./img/battle/comment/loser.png"));
		no=ImageIO.read(new File("./img/battle/comment/no.png"));
		nomore=ImageIO.read(new File("./img/battle/comment/nomore.png"));
		OOeega= ImageIO.read(new File("./img/battle/comment/OOeega.png"));
		OOege=ImageIO.read(new File("./img/battle/comment/OOege.png"));
		OOeul=ImageIO.read(new File("./img/battle/comment/OOeul.png"));
		OOis=ImageIO.read(new File("./img/battle/comment/OOis.png"));
		OOui=ImageIO.read(new File("./img/battle/comment/OOui.png"));
		redName=ImageIO.read(new File("./img/battle/comment/redname.png"));
		sequenceText=ImageIO.read(new File("./img/battle/comment/sequencetext.png"));
		showBooText=ImageIO.read(new File("./img/battle/comment/showbootext.png"));
		takeOut=ImageIO.read(new File("./img/battle/comment/takeout.png"));
		trainerText=ImageIO.read(new File("./img/battle/comment/trainertext.png"));
		use=ImageIO.read(new File("./img/battle/comment/Use.png"));
		winText=ImageIO.read(new File("./img/battle/comment/wintext.png"));
		//各 Imgの差表をもっているstruct class
		position= new Position();
	}
	
	public static Battle getInstance(){
		if(battleInstance==null){
			try{
				battleInstance= new Battle();
			}catch (IOException e) {
				e.printStackTrace();
			}
			
		}
		return battleInstance;
	}
	
	class Position{
		
		//フレーム位置(ボールを除いてすべて0,0)
		public int[] enemyBallX;
		public int[] enemyBallY;
		public int[] myBallX;
		public int[] myBallY;
		//commandselect ポインターの位置
		public int[][] commandSelectX;
		public int[][] commandSelectY;
		//skillselect ポインターの位置
		public int[] skillPointerX;
		public int[] skillPointerY;	
		
		//skillText ポインターの位置
		public int[] skillTextX;
		public int[] skillTextY;
		
		//コメントの位置
		public int BehealX;
		public int BehealY;
		public int blindX;
		public int blindY;
		public int cantRunComment1X;
		public int cantRunComment1Y;
		public int cantRunComment2X;
		public int cantRunComment2Y;
		public int comeBackX;
		public int comeBackY;
		public int enemyX;
		public int enemyY;
		public int enemyLoseCommentX;
		public int enemyLoseCommentY;
		public int exclamationTextX;
		public int exclamationTextY;
		public int fallingX;
		public int fallingY;
		public int goTextX;
		public int goTextY;
		public int healPoint50X;
		public int healPoint50Y;
		public int HPGaX;
		public int HPGaY;
		public int loserX;
		public int loserY;
		public int noX;
		public int noY;
		public int nomoreX;
		public int nomoreY;
		public int OOeegaX;
		public int OOeegaY;
		public int OOegeX;
		public int OOegeY;
		public int OOeulX;
		public int OOeulY;
		public int OOisX;
		public int OOisY;
		public int OOuiX;
		public int OOuiY;
		public int redNameX;
		public int redNameY;
		public int sequnceTextX;
		public int sequnceTextY;
		public int showBooTextX;
		public int showBooTextY;
		public int takOutX;
		public int takOutY;
		public int trainerTextX;
		public int trainerTextY;
		public int useX;
		public int useY;
		public int winTextX;
		public int winTextY;
		
		Position(){
			enemyBallX=new int[6];
			enemyBallY=new int[6];
			myBallX=new int[6];
			myBallY=new int[6];
			
			makeBallPosition();
			skillPointerX= new int[4];
			skillPointerY= new int[4];
			commandSelectX= new int[2][2];
			commandSelectY= new int[2][2];
			makePointerPosition();
			
			skillTextX=new int[4];
			skillTextY=new int[4];	
			makeSkillTextposition();
		
		}
		public void makeSkillTextposition(){
			int skillX=64;
			int skillY=300;
			for(int i=0;i<4;i++){
				skillTextX[i]=skillX;
				skillTextY[i]=skillY;
				skillY+=64;
			}
		}
		public void makePointerPosition(){
			int skillX=36;
			int skillY=320;
			for(int i=0;i<4;i++){
				skillPointerX[i]=skillX;
				skillPointerY[i]=skillY;
				skillY+=64;
			}
			//戦う
			commandSelectX[0][0]=292;
			commandSelectY[0][0]=448;
			//バック
			commandSelectX[0][1]=452;
			commandSelectY[0][1]=448;
			//ポケモン
			commandSelectX[1][0]=292;
			commandSelectY[1][0]=512;
			//逃げる
			commandSelectX[1][1]=452;
			commandSelectY[1][1]=512;
		}
		
		public void makeBallPosition(){
			int enemyX=100;
			int enemyY=68;
			int myX=356;
			int myY=324;
			for(int i=0;i<6;i++){
				enemyBallX[i]=enemyX;
				enemyBallY[i]=enemyY;
				myBallX[i]=myX;
				myBallY[i]=myY;
				enemyX+=32;
				myX+=32;
			}
		}
	}	
}
