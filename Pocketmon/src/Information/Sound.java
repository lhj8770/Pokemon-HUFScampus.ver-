package Information;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import Pocketmon.AI;
import Pocketmon.Battle;
import Pocketmon.Character;
import Pocketmon.Evolution;
import Pocketmon.GoldCharacter;
import Pocketmon.Map;
import Pocketmon.Menu;
import Pocketmon.Start;
import Pocketmon.Wild;

public class Sound {
	public static int NONE = -1;
	public static int YES = 1;

	public int titleRunState;
	public int mohyunRunState;
	public int campusRunState;
	public int wildBattleRunState;
	public int redBattleRunState;
	public int buildingRunState;
	public int emartRunState;
	public int evolRunState;
	
	public int mapChangeState;
	public int beepState;	
	public int menuOnState;
	public int potionState;

	public int bigfireState;
	public int bubbleState;
	public int bubblebeamState;
	public int dragonangryState;
	public int dungState;
	public int firepunchState;
	public int fireshotState;
	public int flameState;
	public int hydropumpState;
	public int icebeamState;
	public int icepunchState;
	public int leafState;
	public int lightningpunchState;
	public int momtongState;
	public int omulState;
	public int scratchState;
	public int solabeamState;
	public int suneatState;
	public int watergunState;
	

	Character redMove = null;
	Map map = null;
	Menu menu = null;
	RedHave redHave = null;
	GoldCharacter goldMove = null;
	Battle battle = null;
	AI ai = null;
	GoldHave goldHave = null;
	Wild wild = null;
	Evolution evolution = null;
	Start start = null;

	Clip title;
	Clip mohyun;
	Clip campus;
	Clip wildBattle;
	Clip redBattle;
	Clip building;
	Clip emart;
	Clip evol;

	Clip beep;
	Clip mapChange;
	Clip menuOn;
	Clip potion;

	Clip bigfire;
	Clip bubble;
	Clip bubblebeam;
	Clip dragonangry;
	Clip dung;
	Clip firepunch;
	Clip fireshot;
	Clip flame;
	Clip hydropump;
	Clip icebeam;
	Clip icepunch;
	Clip leaf;
	Clip lightningpunch;
	Clip momtong;
	Clip omul;
	Clip scratch;
	Clip solabeam;
	Clip suneat;
	Clip watergun;

	AudioInputStream titleName;
	AudioInputStream mohyunName;
	AudioInputStream campusName;
	AudioInputStream wildBattleName;
	AudioInputStream redBattleName;
	AudioInputStream buildingName;
	AudioInputStream emartName;
	AudioInputStream evolName;

	AudioInputStream beepName;
	AudioInputStream mapChangeName;
	AudioInputStream menuOnName;
	AudioInputStream potionName;

	AudioInputStream bigfireName;
	AudioInputStream bubbleName;
	AudioInputStream bubblebeamName;
	AudioInputStream dragonangryName;
	AudioInputStream dungName;
	AudioInputStream firepunchName;
	AudioInputStream fireshotName;
	AudioInputStream flameName;
	AudioInputStream hydropumpName;
	AudioInputStream icebeamName;
	AudioInputStream icepunchName;
	AudioInputStream leafName;
	AudioInputStream lightningpunchName;
	AudioInputStream momtongName;
	AudioInputStream omulName;
	AudioInputStream scratchName;
	AudioInputStream solabeamName;
	AudioInputStream suneatName;
	AudioInputStream watergunName;

	Runnable r;
	Thread t;

	public Sound(Character redMove, Map map, Menu menu, RedHave redHave,
			GoldCharacter goldMove, Battle battle, AI ai, GoldHave goldHave,
			Wild wild, Evolution evolution, Start start)
			throws UnsupportedAudioFileException,
			IOException, LineUnavailableException {
		this.redMove = redMove;
		this.map = map;
		this.menu = menu;
		this.redHave = redHave;
		this.goldMove = goldMove;
		this.battle = battle;
		this.ai = ai;
		this.goldHave = goldHave;
		this.wild = wild;
		this.evolution = evolution;
		this.start = start;

		mapChangeState = NONE;		
		beepState = NONE;	
		menuOnState = NONE;
		potionState = NONE;

		bigfireState = NONE;
		bubbleState = NONE;
		bubblebeamState = NONE;
		dragonangryState = NONE;
		dungState = NONE;
		firepunchState = NONE;
		fireshotState = NONE;
		flameState = NONE;
		hydropumpState = NONE;
		icebeamState = NONE;
		icepunchState = NONE;
		leafState = NONE;
		lightningpunchState = NONE;
		momtongState = NONE;
		omulState = NONE;
		scratchState = NONE;
		solabeamState = NONE;
		suneatState = NONE;
		watergunState = NONE;
		
		titleRunState = YES;
		mohyunRunState = YES;
		campusRunState = YES;
		wildBattleRunState = YES;
		redBattleRunState = YES;
		buildingRunState = YES;
		emartRunState = YES;
		evolRunState = YES;
		

		title = AudioSystem.getClip();
		titleName = AudioSystem.getAudioInputStream(new File(
				".\\bgm\\title.wav"));

		mohyun = AudioSystem.getClip();
		mohyunName = AudioSystem.getAudioInputStream(new File(
				".\\bgm\\mohyun.wav"));

		campus = AudioSystem.getClip();
		campusName = AudioSystem.getAudioInputStream(new File(
				".\\bgm\\campus.wav"));

		wildBattle = AudioSystem.getClip();
		wildBattleName = AudioSystem.getAudioInputStream(new File(
				".\\bgm\\wildbattle.wav"));

		redBattle = AudioSystem.getClip();
		redBattleName = AudioSystem.getAudioInputStream(new File(
				".\\bgm\\redbattle.wav"));

		building = AudioSystem.getClip();
		buildingName = AudioSystem.getAudioInputStream(new File(
				".\\bgm\\building.wav"));

		emart = AudioSystem.getClip();
		emartName = AudioSystem.getAudioInputStream(new File(
				".\\bgm\\emart.wav"));
		
		evol = AudioSystem.getClip();
		evolName = AudioSystem.getAudioInputStream(new File(
				".\\bgm\\evol.wav"));

		beep = AudioSystem.getClip();
		beepName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\menu\\beep.wav"));

		mapChange = AudioSystem.getClip();
		mapChangeName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\menu\\mapchange.wav"));

		menuOn = AudioSystem.getClip();
		menuOnName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\menu\\menuon.wav"));

		potion = AudioSystem.getClip();
		potionName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\menu\\potion.wav"));

		bigfire = AudioSystem.getClip();
		bigfireName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\bigfire.wav"));

		bubble = AudioSystem.getClip();
		bubbleName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\bubble.wav"));

		bubblebeam = AudioSystem.getClip();
		bubblebeamName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\bubblebeam.wav"));

		dragonangry = AudioSystem.getClip();
		dragonangryName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\dragonangry.wav"));

		dung = AudioSystem.getClip();
		dungName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\dung.wav"));

		firepunch = AudioSystem.getClip();
		firepunchName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\firepunch.wav"));

		fireshot = AudioSystem.getClip();
		fireshotName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\fireshot.wav"));

		flame = AudioSystem.getClip();
		flameName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\flame.wav"));

		hydropump = AudioSystem.getClip();
		hydropumpName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\hydropump.wav"));
		icebeam = AudioSystem.getClip();
		icebeamName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\icebeam.wav"));

		icepunch = AudioSystem.getClip();
		icepunchName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\icepunch.wav"));

		leaf = AudioSystem.getClip();
		leafName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\leaf.wav"));

		lightningpunch = AudioSystem.getClip();
		lightningpunchName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\lightningpunch.wav"));

		momtong = AudioSystem.getClip();
		momtongName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\momtong.wav"));

		omul = AudioSystem.getClip();
		omulName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\omul.wav"));

		scratch = AudioSystem.getClip();
		scratchName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\scratch.wav"));

		solabeam = AudioSystem.getClip();
		solabeamName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\solabeam.wav"));

		suneat = AudioSystem.getClip();
		suneatName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\suneat.wav"));

		watergun = AudioSystem.getClip();
		watergunName = AudioSystem.getAudioInputStream(new File(
				".\\sound\\skill\\watergun.wav"));

		title.open(titleName);
		mohyun.open(mohyunName);
		campus.open(campusName);
		wildBattle.open(wildBattleName);
		redBattle.open(redBattleName);
		building.open(buildingName);
		emart.open(emartName);
		evol.open(evolName);

		beep.open(beepName);
		mapChange.open(mapChangeName);
		menuOn.open(menuOnName);
		potion.open(potionName);

		bigfire.open(bigfireName);
		bubble.open(bubbleName);
		bubblebeam.open(bubblebeamName);
		dragonangry.open(dragonangryName);
		dung.open(dungName);
		firepunch.open(firepunchName);
		fireshot.open(fireshotName);
		flame.open(flameName);
		hydropump.open(hydropumpName);
		icebeam.open(icebeamName);
		icepunch.open(icepunchName);
		leaf.open(leafName);
		lightningpunch.open(lightningpunchName);
		momtong.open(momtongName);
		omul.open(omulName);
		scratch.open(scratchName);
		solabeam.open(solabeamName);
		suneat.open(suneatName);
		watergun.open(watergunName);

		r = new Runnable() {
			@Override
			public void run() {
				while (true) {
					// State로 스윗치를 만들어서 stop 메소드가 한번 씩 불리도록 한다.
					if (start.startState == Start.NONE) {
						titleRunState = NONE;
						title.start();
						title.loop(Clip.LOOP_CONTINUOUSLY);
					} else if (start.startState == Start.YES
							&& titleRunState == NONE) {
						title.stop();
						title.setFramePosition(0);
						titleRunState = YES;
					} else {
						if ( evolution.evolState == Evolution.NONE&&
								map.mapState == Map.ROOM_NUM
								|| map.mapState == Map.MOHYUN_NUM) {
							mohyunRunState = NONE;
							mohyun.start();
							mohyun.loop(Clip.LOOP_CONTINUOUSLY);
						} else if (evolution.evolState != Evolution.NONE||
								map.mapState != Map.ROOM_NUM
								&& map.mapState != Map.MOHYUN_NUM
								&& mohyunRunState == NONE) {
							mohyun.stop();
							mohyun.setFramePosition(0);
							mohyunRunState = YES;				
						}						
						// 야생 포켓몬과의 싸움일때
						if (battle.wildPokemonMeetState == Battle.YES) {
							wildBattleRunState = NONE;
							wildBattle.start();
							wildBattle.loop(Clip.LOOP_CONTINUOUSLY);
						} else if (battle.wildPokemonMeetState == Battle.NONE
								&& wildBattleRunState == NONE) {
							wildBattle.stop();
							wildBattle.setFramePosition(0);
							wildBattleRunState = YES;
						}
						// 야생 포켓몬과의 싸움은 campus 에서 이루어지므로 배틀 state로인한 블락을 추가해준다.
						if (evolution.evolState == Evolution.NONE&&
								battle.wildPokemonMeetState == Battle.NONE
								&& map.mapState == Map.CAMPUS_NUM) {
							campusRunState = NONE;
							campus.start();
							campus.loop(Clip.LOOP_CONTINUOUSLY);
						} else if (evolution.evolState != Evolution.NONE||
								battle.wildPokemonMeetState == Battle.YES
								|| map.mapState != Map.CAMPUS_NUM
								&& campusRunState == NONE) {
							campus.stop();
							campus.setFramePosition(0);
							campusRunState = YES;
						
						}
						// npc와의 싸움일때
						if (goldMove.state == GoldCharacter.MOVE_UNABLE) {
							redBattleRunState = NONE;
							redBattle.start();
							redBattle.loop(Clip.LOOP_CONTINUOUSLY);
						} else if (goldMove.state == GoldCharacter.MOVE_ABLE
								&& redBattleRunState == NONE) {
							redBattle.stop();
							redBattle.setFramePosition(0);
							redBattleRunState = YES;							
						}
						// npc의 싸움은 공대에서 이루어 지므로 배틀 state 블락을 추가해 준다.
						if (evolution.evolState == Evolution.NONE&&
							goldMove.state == GoldCharacter.MOVE_ABLE
								&& map.mapState == Map.BUILDING_NUM) {
							buildingRunState = NONE;
							building.start();
							building.loop(Clip.LOOP_CONTINUOUSLY);
						} else if (evolution.evolState != Evolution.NONE||
								goldMove.state == GoldCharacter.MOVE_UNABLE
								|| map.mapState != Map.BUILDING_NUM
								&& buildingRunState == NONE) {
							building.stop();
							building.setFramePosition(0);
							buildingRunState = YES;
						
						}
						if (evolution.evolState == Evolution.NONE&&
								map.mapState == Map.CAMPUS_HALF_NUM
								|| map.mapState == Map.MOHYUN_HALF_NUM) {
							emartRunState = NONE;
							emart.start();
							emart.loop(Clip.LOOP_CONTINUOUSLY);
						} else if (evolution.evolState != Evolution.NONE||
								map.mapState != Map.CAMPUS_HALF_NUM
								&& map.mapState != Map.MOHYUN_HALF_NUM
								&& emartRunState == NONE) {
							emart.stop();
							emart.setFramePosition(0);						
							emartRunState = YES;
						}
						if (evolution.evolState != Evolution.NONE) {
							evolRunState = NONE;
							evol.start();
							evol.loop(Clip.LOOP_CONTINUOUSLY);
						} else if (evolution.evolState == Evolution.NONE
								&& evolRunState == NONE) {
							evol.stop();
							evol.setFramePosition(0);						
							evolRunState = YES;
						}
						
						//효과음
						if(mapChangeState == YES){
							mapChange.start();
							mapChangeState = NONE;
						}if(beepState == YES){
							beep.start();
							beepState = NONE;
						}if(menuOnState == YES){
							menuOn.start();
							menuOnState = NONE;
						}if(potionState == YES){
							potion.start();
							potionState = NONE;
						}if(bigfireState == YES){
							bigfire.start();
							bigfireState = NONE;
						}if(bubbleState == YES){
							bubble.start();
							bubbleState = NONE;
						}if(bubblebeamState == YES){
							bubblebeam.start();
							bubblebeamState = NONE;
						}if(dragonangryState == YES){
							dragonangry.start();
							dragonangryState = NONE;
						}if(dungState == YES){
							dung.start();
							dungState = NONE;
						}if(firepunchState == YES){
							firepunch.start();
							firepunchState = NONE;
						}if(fireshotState == YES){
							fireshot.start();
							fireshotState = NONE;
						}if(flameState == YES){
							flame.start();
							flameState = NONE;
						}if(hydropumpState == YES){
							hydropump.start();
							hydropumpState = NONE;
						}if(icebeamState == YES){
							icebeam.start();
							icebeamState = NONE;
						}if(icepunchState == YES){
							icepunch.start();
							icepunchState = NONE;
						}if(leafState == YES){
							leaf.start();
							leafState = NONE;
						}if(lightningpunchState == YES){
							lightningpunch.start();
							lightningpunchState = NONE;
						}if(momtongState == YES){
							momtong.start();
							momtongState = NONE;
						}if(omulState == YES){
							omul.start();
							omulState = NONE;
						}if(scratchState == YES){
							scratch.start();
							scratchState = NONE;
						}if(solabeamState == YES){
							solabeam.start();
							solabeamState = NONE;
						}if(suneatState == YES){
							suneat.start();
							suneatState = NONE;
						}if(watergunState == YES){
							watergun.start();
							watergunState = NONE;
						}
						
						// 효과음의 리셋
						if (!beep.isRunning()) {
							beep.setFramePosition(0);
						}if (!mapChange.isRunning()) {
							mapChange.setFramePosition(0);							
						}if (!menuOn.isRunning()) {
							menuOn.setFramePosition(0);							
						}if (!potion.isRunning()) {
							potion.setFramePosition(0);							
						}if (!bigfire.isRunning()) {
							bigfire.setFramePosition(0);							
						}if (!bubble.isRunning()) {
							bubble.setFramePosition(0);							
						}if (!bubblebeam.isRunning()) {
							bubblebeam.setFramePosition(0);							
						}if (!dragonangry.isRunning()) {
							dragonangry.setFramePosition(0);							
						}if (!dung.isRunning()) {
							dung.setFramePosition(0);							
						}if (!firepunch.isRunning()) {
							firepunch.setFramePosition(0);							
						}if (!fireshot.isRunning()) {
							fireshot.setFramePosition(0);							
						}if (!flame.isRunning()) {
							flame.setFramePosition(0);							
						}if (!hydropump.isRunning()) {
							hydropump.setFramePosition(0);							
						}if (!icebeam.isRunning()) {
							icebeam.setFramePosition(0);							
						}if (!icepunch.isRunning()) {
							icepunch.setFramePosition(0);							
						}if (!leaf.isRunning()) {
							leaf.setFramePosition(0);							
						}if (!lightningpunch.isRunning()) {
							lightningpunch.setFramePosition(0);							
						}if (!momtong.isRunning()) {
							momtong.setFramePosition(0);							
						}if (!omul.isRunning()) {
							omul.setFramePosition(0);							
						}if (!scratch.isRunning()) {
							scratch.setFramePosition(0);							
						}if (!solabeam.isRunning()) {
							solabeam.setFramePosition(0);							
						}if (!suneat.isRunning()) {
							suneat.setFramePosition(0);							
						}if (!watergun.isRunning()) {
							watergun.setFramePosition(0);							
						}
					}
				}

			}
		};
		t = new Thread(r);
		t.start();
	}
	public void setEachSkillSoundState(int skillNum){		
		if(skillNum == Skill.BUBBLE_NUM){
			bubbleState = YES;
		}else if(skillNum == Skill.DUNG_NUM){
			dungState = YES;
		}else if(skillNum == Skill.FIRESHOT_NUM){
			fireshotState = YES;
		}else if(skillNum == Skill.LEAF_NUM){
			leafState = YES;
		}else if(skillNum == Skill.MOMTONG_NUM){
			momtongState = YES;
		}else if(skillNum ==Skill.SCRATCH_NUM){
			scratchState = YES;
		}else if(skillNum ==Skill. WATERGUN_NUM){
			watergunState = YES;
		}else if(skillNum == Skill.BIGFIRE_NUM){
			bigfireState = YES;
		}else if(skillNum ==Skill. BUBBLEBEAM_NUM){
			bubblebeamState = YES;
		}else if(skillNum ==Skill. DRAGONANGRY_NUM){
			dragonangryState = YES;
		}else if(skillNum == Skill.FLAME_NUM){
			flameState = YES;
		}else if(skillNum == Skill.HYDROPUMP_NUM){
			hydropumpState = YES;
		}else if(skillNum == Skill.ICEBEAM_NUM){
			icebeamState = YES; 
		}else if(skillNum ==Skill. ICEPUNCH_NUM){
			icepunchState = YES;
		}else if(skillNum == Skill.LIGHTNINGPUNCH_NUM){
			lightningpunchState = YES;
		}else if(skillNum == Skill.OMUL_NUM){
			omulState = YES;
		}else if(skillNum == Skill.SOLABEAM_NUM){
			solabeamState = YES;
		}else if(skillNum ==Skill. SUNEAT_NUM){
			suneatState = YES;
		}		
	}
}
