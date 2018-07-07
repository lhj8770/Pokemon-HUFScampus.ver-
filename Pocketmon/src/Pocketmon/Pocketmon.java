package Pocketmon;

import java.awt.Color;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.SynchronousQueue;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.Timer;

import org.omg.Messaging.SyncScopeHelper;

import Information.GoldHave;
import Information.Item;
import Information.Pokemon;
import Information.RedHave;
import Information.Skill;
import Information.Sound;
import Information.Util;
import Pocketmon.Map.Building;
import Pocketmon.Map.Campus;
import Pocketmon.Map.Mohyun;
import Pocketmon.Map.Room;
import Pocketmon.Map.Town;
/*
 * KEY  :   backspace - cancel | enter - menu  |  spacebar - select   
 * ゲームの流れ：はじめての画面でenterを押してゲームスタ-ト家を出て道路の上側にあるバス乗り場の真ん中に立つとバスに乗ってキャンパス
 * に移動します。マップの左側にある橋を渡って ビルの入り口上下にある草で野生ポケモンとの戦闘が可能です。その戦闘で得たキャンディー(이상한캔디)
 * でレベルアープが可能です。ビル(工学館)のなかに入って真ん中の庭園に行けばボスnpcとの戦闘が可能です。
 */

class PocketmonComponent extends JComponent {
	//약 12845 줄
	
	//タイマー専用ディレイ
	public static int TIME_DELAY = 5;

	//ゲームエフェクト専用ディレイ
	public int GAME_DELAY =0;
	
	private Timer t;
	private Character redMove;
	private GoldCharacter goldMove;

	private ArrayList<Menu.Position> mainPointerPosition;
	private ArrayList<Menu.Position> backPackPointerPosition;
	private ArrayList<Menu.Position> pokemonMenuPointerPosition;
	private ArrayList<BoundingBox> list;
	private Map map;
	private Menu menu;
	private Battle battle;
	private RedHave redHave;
	private GoldHave goldHave;
	private Wild wild;
	private AI ai;	
	private Bus bus;
	private Evolution evolution;
	private Start start; 
	private Sound sound;
	private NpcCharacter npc;
	private MoveEvent moveEvent;
	
	private double currentTime;
	private double lastTime; 
	private double elapsed;
	private double avgElapsed;
	private int elapsedCounter;

	//　建物にいる敵が動くタイミング
	private int goldMoveStateTiming;

	// private Image icon;

	public PocketmonComponent() throws IOException, FontFormatException,  UnsupportedAudioFileException, LineUnavailableException {
		t = new Timer(TIME_DELAY, new TimerHandler());
		goldMoveStateTiming = 0;
		SpriteImg goldSpriteImg = new SpriteImg(4, 3, 64, 64,
				".\\img\\gold\\gold.png");
		SpriteImg spriteImg = new SpriteImg(4, 3, 64, 64,
				".\\img\\red\\red.png");
		
		redMove = Character.getInstance();
		
		avgElapsed=0;
		elapsedCounter=0;

		mainPointerPosition = new ArrayList<Menu.Position>();
		backPackPointerPosition = new ArrayList<Menu.Position>();
		pokemonMenuPointerPosition = new ArrayList<Menu.Position>();
		bus = Bus.getInstance();
		battle = Battle.getInstance();
		map = Map.getInstance();
		goldMove = new GoldCharacter(goldSpriteImg.makeSpriteImg(), map,this);
		menu = Menu.getInstance();
		redHave = RedHave.getInstance(); //　singleton instance of playable character
		goldHave= new GoldHave();// 敵のnpcが持っているアイテムとポケモンの情報　instance
		wild = new Wild(this);
		evolution = Evolution.getInstance();
		mainPointerPosition = menu.mainMenu.getpointerPosition();
		backPackPointerPosition = menu.backPackMenu.getpointerPosition();
		pokemonMenuPointerPosition = menu.pokemonMenu.getpointerPosition();
		ai = AI.getInstance();
		ai.InitObject(redHave, goldHave, battle, wild);
		start = new Start();
		npc = new NpcCharacter(this,map);
		
		sound = new Sound(redMove, map, menu, redHave,goldMove,battle,ai,goldHave,wild,evolution,start);
		moveEvent = MoveEvent.getInstance();
		moveEvent.initObject(redMove, map, menu, redHave,goldMove,battle,ai,goldHave,wild,evolution,start,sound);
		this.addKeyListener(moveEvent);
		this.setFocusable(true);
		
		lastTime=System.currentTimeMillis();
		t.start();
	}
	
	class TimerHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			
			//stateの変化でマップが変わる。
			if (map.mapState == Map.ROOM_NUM) { 		
				//キャラクターの移動できる範囲をマップの状態に会うように指定。
				list = map.room.getBoundList();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i)
							.makeBoundingBox()
							.intersects(
									redMove.getBoundingBox().makeBoundingBox())) {
						switch (redMove.getMoveState()) {
						case Character.MOVE_UP:
							map.room.setUpMoveState(Map.MOVE_UNABLE);
							break;
						case Character.MOVE_DOWN:
							map.room.setDownMoveState(Map.MOVE_UNABLE);
							//キャラクターがぶっつかったそのボックスのインデックスを判別してイベント発生
							if (i == Map.DOOR_INDEX) {
								map.mapState = Map.MOHYUN_NUM;								
								sound.mapChangeState = Sound.YES;
								map.room.setX_pos(Room.START_X_POS);
								map.room.setY_pos(Room.START_Y_POS);
								map.room.resetMoveState();
							}
							break;
						case Character.MOVE_LEFT:
							map.room.setLeftMoveState(Map.MOVE_UNABLE);
							break;
						case Character.MOVE_RIGHT:
							map.room.setRightMoveState(Map.MOVE_UNABLE);
							break;
						}
						break;
					} else {
						map.room.resetMoveState();
					}					
				}			
			}else if (map.mapState == Map.TOWN_NUM) { 
				list = map.town.getBoundList();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i)
							.makeBoundingBox()
							.intersects(
									redMove.getBoundingBox().makeBoundingBox())) {
						switch (redMove.getMoveState()) {
						case Character.MOVE_UP:
							map.town.setUpMoveState(Map.MOVE_UNABLE);
							if (i == Map.DOOR_INDEX) {
								map.mapState = Map.ROOM_NUM;
								sound.mapChangeState = Sound.YES;
								map.town.setX_pos(Town.START_X_POS);
								map.town.setY_pos(Town.START_Y_POS);
								map.town.resetMoveState();
							}
							break;
						case Character.MOVE_DOWN:
							map.town.setDownMoveState(Map.MOVE_UNABLE);
							break;
						case Character.MOVE_LEFT:
							map.town.setLeftMoveState(Map.MOVE_UNABLE);
							break;
						case Character.MOVE_RIGHT:
							map.town.setRightMoveState(Map.MOVE_UNABLE);
							break;
						}
						break;

					}  else {
						map.building.resetMoveState();
					}
				}
			}else if(map.mapState==Map.MOHYUN_NUM){	
				list = map.mohyun.getBoundList();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i)
							.makeBoundingBox()
							.intersects(
									redMove.getBoundingBox().makeBoundingBox())) {
						switch (redMove.getMoveState()) {
						case Character.MOVE_UP:
							map.mohyun.setUpMoveState(Map.MOVE_UNABLE);
							if (i == Map.DOOR_INDEX) {
								sound.mapChangeState = Sound.YES;
								map.mapState = Map.ROOM_NUM;
								map.mohyun.setX_pos(Mohyun.START_X_POS);
								map.mohyun.setY_pos(Mohyun.START_Y_POS);
								map.mohyun.resetMoveState();
							}
							break;
						case Character.MOVE_DOWN:
							map.mohyun.setDownMoveState(Map.MOVE_UNABLE);
							break;
						case Character.MOVE_LEFT:
							map.mohyun.setLeftMoveState(Map.MOVE_UNABLE);
							break;
						case Character.MOVE_RIGHT:
							map.mohyun.setRightMoveState(Map.MOVE_UNABLE);
							break;
						}
						//町の上にある停留所の真ん中でバスに乗れるイベントが発生する。
						if(i==Mohyun.BUS_BROAD_INDEX){
							map.mapState=Map.MOHYUN_HALF_NUM;
							sound.mapChangeState = Sound.YES;
							battle.inputBlock=Battle.YES;	
							map.mohyun.setX_pos(Mohyun.START_X_POS);
							map.mohyun.setY_pos(Mohyun.START_Y_POS);
							map.mohyun.resetMoveState();
						}
						break;
					}else{
						map.mohyun.resetMoveState();
					}
					/*
					//npc 와 골드의 충돌
					else if(redMove.getBoundingBox().makeBoundingBox().intersects(npc.girl1.getBoundingBox().makeBoundingBox())){
						
						System.out.println(3);
						switch (redMove.getMoveState()) {						
						case Character.MOVE_UP:
							map.mohyun.setUpMoveState(Map.MOVE_UNABLE);	
							npc.girl1.upState=NpcCharacter.Girl1.MOVE_UNABLE;
							npc.girl1.downState=NpcCharacter.Girl1.MOVE_UNABLE;
							npc.girl1.rightState=NpcCharacter.Girl1.MOVE_UNABLE;
							npc.girl1.leftState=NpcCharacter.Girl1.MOVE_UNABLE;
							break;
						case Character.MOVE_DOWN:
							map.mohyun.setDownMoveState(Map.MOVE_UNABLE);
							npc.girl1.upState=NpcCharacter.Girl1.MOVE_UNABLE;
							npc.girl1.downState=NpcCharacter.Girl1.MOVE_UNABLE;
							npc.girl1.rightState=NpcCharacter.Girl1.MOVE_UNABLE;
							npc.girl1.leftState=NpcCharacter.Girl1.MOVE_UNABLE;
							break;
						case Character.MOVE_LEFT:
							map.mohyun.setLeftMoveState(Map.MOVE_UNABLE);
							npc.girl1.upState=NpcCharacter.Girl1.MOVE_UNABLE;
							npc.girl1.downState=NpcCharacter.Girl1.MOVE_UNABLE;
							npc.girl1.rightState=NpcCharacter.Girl1.MOVE_UNABLE;
							npc.girl1.leftState=NpcCharacter.Girl1.MOVE_UNABLE;
							break;
						case Character.MOVE_RIGHT:
							map.mohyun.setRightMoveState(Map.MOVE_UNABLE);
							npc.girl1.upState=NpcCharacter.Girl1.MOVE_UNABLE;
							npc.girl1.downState=NpcCharacter.Girl1.MOVE_UNABLE;
							npc.girl1.rightState=NpcCharacter.Girl1.MOVE_UNABLE;
							npc.girl1.leftState=NpcCharacter.Girl1.MOVE_UNABLE;
							break;
						}
						break;
					} else {
						npc.girl1.upState=NpcCharacter.Girl1.MOVE_ABLE;
						npc.girl1.downState=NpcCharacter.Girl1.MOVE_ABLE;
						npc.girl1.rightState=NpcCharacter.Girl1.MOVE_ABLE;
						npc.girl1.leftState=NpcCharacter.Girl1.MOVE_ABLE;
						map.mohyun.resetMoveState();
					} 
					//npc와 맵의 충돌
					if(list.get(i)
							.makeBoundingBox()
							.intersects(npc.girl1.getBoundingBox().makeBoundingBox())){
						System.out.println(npc.girl1.moveDirection);
						switch(npc.girl1.moveDirection){
						case NpcCharacter.Girl1.MOVE_UP:
							npc.girl1.upState=NpcCharacter.Girl1.MOVE_UNABLE;
							break;
						case NpcCharacter.Girl1.MOVE_DOWN:
							npc.girl1.downState=NpcCharacter.Girl1.MOVE_UNABLE;
							break;
						case NpcCharacter.Girl1.MOVE_LEFT:
							npc.girl1.leftState=NpcCharacter.Girl1.MOVE_UNABLE;
							break;
						case NpcCharacter.Girl1.MOVE_RIGHT:
							npc.girl1.rightState=NpcCharacter.Girl1.MOVE_UNABLE;
							break;
						}
					}else{
						npc.girl1.upState=NpcCharacter.Girl1.MOVE_ABLE;
						npc.girl1.downState=NpcCharacter.Girl1.MOVE_ABLE;
						npc.girl1.rightState=NpcCharacter.Girl1.MOVE_ABLE;
						npc.girl1.leftState=NpcCharacter.Girl1.MOVE_ABLE;
					}
					*/
				}
			}
			if (map.mapState == Map.BUILDING_NUM) {
				list = map.building.getBoundList();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i)
							.makeBoundingBox()
							.intersects(
									redMove.getBoundingBox().makeBoundingBox())) {						
						switch (redMove.getMoveState()) {
						case Character.MOVE_UP:
							map.building.setUpMoveState(Map.MOVE_UNABLE);
							break;
						case Character.MOVE_DOWN:
							map.building.setDownMoveState(Map.MOVE_UNABLE);
							
							break;	
						case Character.MOVE_LEFT:
							map.building.setLeftMoveState(Map.MOVE_UNABLE);
							break;
						case Character.MOVE_RIGHT:
							map.building.setRightMoveState(Map.MOVE_UNABLE);
							if (i == Map.DOOR_INDEX) {
								map.mapState = Map.CAMPUS_NUM;
								sound.mapChangeState = Sound.YES;
								map.building.setX_pos(Building.START_X_POS);
								map.building.setY_pos(Building.START_Y_POS);
								map.building.resetMoveState();
							}
							break;
						}
						break;
					//このマップには敵npcが存在するのでnpcとそのnpcが認識する範囲(視界はnpcが向いている方向のみ動作する)
					//に合うときキャラクターを停止させる。(イベントが起こるまで)
					}else if (goldMove
							.getBoundingBox()
							.makeBoundingBox()
							.intersects(
									redMove.getBoundingBox().makeBoundingBox())) {
						switch (redMove.getMoveState()) {
						case Character.MOVE_UP:
							map.building.setUpMoveState(Map.MOVE_UNABLE);
							break;
						case Character.MOVE_DOWN:
							map.building.setDownMoveState(Map.MOVE_UNABLE);
							break;
						case Character.MOVE_LEFT:
							map.building.setLeftMoveState(Map.MOVE_UNABLE);
							break;
						case Character.MOVE_RIGHT:
							map.building.setRightMoveState(Map.MOVE_UNABLE);
							break;
						}
					} else {
						map.building.resetMoveState();
					}
				}
			}			
			else if(map.mapState==Map.CAMPUS_NUM){	
				list = map.campus.getBoundList();
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i)
							.makeBoundingBox()
							.intersects(
									redMove.getBoundingBox().makeBoundingBox())) {
						//キャンパスビルの入口の上下にある草は野生ポケモンイベントが起こるボックスを作る。
						if(i!=Campus.UP_GRASS_BOX&&i!=Campus.DOWN_GRASS_BOX){
							switch (redMove.getMoveState()) {
							case Character.MOVE_UP:
								map.campus.setUpMoveState(Map.MOVE_UNABLE);
								break;
							case Character.MOVE_DOWN:
								map.campus.setDownMoveState(Map.MOVE_UNABLE);
								break;
							case Character.MOVE_LEFT:
								map.campus.setLeftMoveState(Map.MOVE_UNABLE);
								if (i == Map.DOOR_INDEX) {
									map.mapState = Map.BUILDING_NUM;
									sound.mapChangeState = Sound.YES;
									map.campus.setX_pos(Campus.START_X_POS_DOOR);
									map.campus.setY_pos(Campus.START_Y_POS_DOOR);
									map.campus.resetMoveState();
								}
								break;
							case Character.MOVE_RIGHT:
								map.campus.setRightMoveState(Map.MOVE_UNABLE);
								break;
							}
							break;
						}else{
						//キャラクターがボックスで動く時だけ戦闘イベントの確立を計算する。
							if(battle.battleState==Battle.BATTLE_NO&&wild.walkState==Wild.YES){
								//(double)TIME_DELAY/5.0)
								if(Util.prob100(1)){									
									battle.battleState=Battle.BATTLE_IN;								
									battle.wildPokemonMeetState=Battle.YES;
									break;
								}
							}
							wild.walkState=Wild.NONE;
						}
					} else {
						map.campus.resetMoveState();
					}
				}
			}else if(map.mapState==Map.MOHYUN_HALF_NUM){
				//バスイベントの際にマップの縁にたどり着く瞬間、マップの移動イベントがおこる。
				if(bus.getMohyunBusBB().makeBoundingBox().intersects(
						map.mohyun.getBusIntersectsBox().makeBoundingBox())){
					bus.setBusDelay(0);
					bus.x_pos=Bus.START_X_POS_CAMPUS;
					bus.y_pos=Bus.START_Y_POS_CAMPUS;
					map.mapState=Map.CAMPUS_HALF_NUM;					
				}
			}else if(map.mapState==Map.CAMPUS_HALF_NUM){
				if(bus.getCampusBusBB().makeBoundingBox().intersects(						
						map.campus.getBusIntersectsBox().makeBoundingBox())){
					bus.setStopState(Bus.YES);
					//バスが最初にすこしだけ止まっている演出のディレイを決める。
					if(bus.getBusDelay()!=GAME_DELAY*5){
						bus.setBusDelay(bus.getBusDelay()+1);
					}else{		
						bus.x_pos=Bus.START_X_POS_MOHYUN;
						bus.y_pos=Bus.START_Y_POS_MOHYUN;
						Pocketmon.f.setSize(Pocketmon.FRAME_W+16,Pocketmon.FRAME_H+38);
						sound.mapChangeState = Sound.YES;
						map.mapState=Map.CAMPUS_NUM;
						bus.setBusDelay(0);
						bus.setStopState(Bus.NONE);
						battle.inputBlock=Battle.NONE;	
					}					
				}
			}
			repaint();
			
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		
		currentTime = System.currentTimeMillis();
		//기기의 성능에 따른 GAME_DELAY 설정
		elapsed= currentTime - lastTime;
		if(elapsed!=currentTime&&GAME_DELAY==0){
			if(elapsedCounter++<200){
				avgElapsed+=elapsed;
			}else{
				avgElapsed/=200;
				GAME_DELAY = (int) (250/avgElapsed);
				if(GAME_DELAY<25){
					GAME_DELAY=25;
				}
				redMove.InitStartXMoveSpeed(GAME_DELAY);
			}
		}
		
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
		g.fillRect(0, 0, Pocketmon.FRAME_W, Pocketmon.FRAME_H);
		g.setColor(new Color(0,0,0));
		
		
		if (map.mapState == Map.ROOM_NUM) { 
			g.drawImage(map.room.getBufferedImg(), map.room.getX_pos(),
					map.room.getY_pos(), this);
			//バウンディングボックスのテストコード
			/*
			 for(BoundingBox index : map.room.getBoundList()) {
			 g.setColor(Color.BLACK);
			 g.drawRect((int)index.makeBoundingBox().getX(
			 ),(int)index.makeBoundingBox().getY(),
			 (int)index.makeBoundingBox().getWidth(),
			 (int)index.makeBoundingBox().getHeight()); }
			 g.setColor(Color.RED); g.
			 fillRect((int)redMove.getBoundingBox
			 ().makeBoundingBox().getX(),(int)redMove
			 .getBoundingBox().makeBoundingBox().getY(),
			 (int)redMove.getBoundingBox().makeBoundingBox().getWidth(),
			 (int)redMove.getBoundingBox().makeBoundingBox().getHeight());
			 */
			/*
			g.setColor(Color.CYAN);
			g.fillRect((int)map.room.getBoundList().get(0).x, (int)map.room.getBoundList().get(0).y
					, (int)map.room.getBoundList().get(0).width, (int)map.room.getBoundList().get(0).height);
					*/
		}
		//バスに乗るイベントの際にマップの全体の縮小版をみせる。
		if (map.mapState == Map.MOHYUN_HALF_NUM) {
			Pocketmon.f.setSize(Pocketmon.MOHYUN_HALF_W, Pocketmon.MOHYUN_HALF_H);
			g.drawImage(map.mohyun.getHalfBufferedImg(), 0,0, this);
			g.drawImage(bus.getMohyunBusImg(), bus.x_pos, bus.y_pos, this);
			if(bus.getBusDelay()!=GAME_DELAY*5){
				bus.setBusDelay(bus.getBusDelay()+1);
			}else{				
				bus.moveMohyun(GAME_DELAY);
			}
		}	
		//バスに乗るイベントの際にマップの全体の縮小版をみせる。
		if (map.mapState == Map.CAMPUS_HALF_NUM) {
			Pocketmon.f.setSize(Pocketmon.CAMPUS_HALF_W, Pocketmon.CAMPUS_HALF_H);
			g.drawImage(map.campus.getHalfBufferedImg(), 0,0, this);
			g.drawImage(bus.getCampusBusImg(),bus.x_pos, bus.y_pos, this);
			bus.moveCampus(GAME_DELAY);			
		}	
		if (map.mapState == Map.CAMPUS_NUM) {
			g.drawImage(map.campus.getBufferedImg(), map.campus.getX_pos(),
					map.campus.getY_pos(), this);		
			//バウンディングボックスのテストコード
			/*
			 for(BoundingBox index : map.campus.getBoundList()) {
				 g.setColor(Color.BLACK);
				 g.drawRect((int)index.makeBoundingBox().getX(),(int)index.makeBoundingBox().getY(),
				 (int)index.makeBoundingBox().getWidth(),
				 (int)index.makeBoundingBox().getHeight());
				 }
				 g.setColor(Color.RED); g.
				 fillRect((int)redMove.getBoundingBox
				 ().makeBoundingBox().getX(),(int)redMove
				 .getBoundingBox().makeBoundingBox().getY(),
				 (int)redMove.getBoundingBox().makeBoundingBox().getWidth(),
				 (int)redMove.getBoundingBox().makeBoundingBox().getHeight());
			
			 g.setColor(Color.RED); 				
			 g.fillRect((int)redMove.getBoundingBox().makeBoundingBox().getX(),(int)redMove
					 .getBoundingBox().makeBoundingBox().getY(),
					 (int)redMove.getBoundingBox().makeBoundingBox().getWidth(),
					 (int)redMove.getBoundingBox().makeBoundingBox().getHeight());
				 */

		}
		if (map.mapState == Map.BUILDING_NUM) {
			g.drawImage(map.building.getBufferedImg(), map.building.getX_pos(),
					map.building.getY_pos(), this);		
			//バウンディングボックスのテストコード
			/*
			 for(BoundingBox index : map.building.getBoundList()) {
				 g.setColor(Color.magenta);
				 g.fillRect((int)index.makeBoundingBox().getX(),(int)index.makeBoundingBox().getY(),
				 (int)index.makeBoundingBox().getWidth(),
				 (int)index.makeBoundingBox().getHeight());
				 }
				 g.setColor(Color.RED); g.
				 fillRect((int)redMove.getBoundingBox
				 ().makeBoundingBox().getX(),(int)redMove
				 .getBoundingBox().makeBoundingBox().getY(),
				 (int)redMove.getBoundingBox().makeBoundingBox().getWidth(),
				 (int)redMove.getBoundingBox().makeBoundingBox().getHeight());
			
			 g.setColor(Color.RED); 				
			 g.fillRect((int)redMove.getBoundingBox().makeBoundingBox().getX(),(int)redMove
					 .getBoundingBox().makeBoundingBox().getY(),
					 (int)redMove.getBoundingBox().makeBoundingBox().getWidth(),
					 (int)redMove.getBoundingBox().makeBoundingBox().getHeight());
				 */
						// キャラクターがいつも画面の中央にいるのでNPCはイベントが起きる前までマップに固定されている状態。
						if(redMove.getMeetState()==Character.MEET_NOT&&battle.winState==Battle.NONE){
							goldMove.setXY(map.building.getX_pos(), map.building.getY_pos());
						}
						goldMoveStateTiming++;
						//マップでキャラクタを待っている時にNPCが向いている方向をランダムに変える。
						if (goldMoveStateTiming > GAME_DELAY&&menu.MenuState==Menu.MENU_DISABLE) {
							if (redMove.getMeetState() == Character.MEET_NOT) {
								if (Util.prob100(3)) {
									// 上
									goldMove.moveState = GoldCharacter.MOVE_UP;
									goldMove.moveDirection();
								}
								if (Util.prob100(3)) {
									// 右
									goldMove.moveState = GoldCharacter.MOVE_RIGHT;
									goldMove.moveDirection();
								}
								if (Util.prob100(3)) {
									// 下
									goldMove.moveState = GoldCharacter.MOVE_DOWN;
									goldMove.moveDirection();
								}
								if (Util.prob100(3)) {
									// 左
									goldMove.moveState = GoldCharacter.MOVE_LEFT;
									goldMove.moveDirection();
								}
							}
							goldMoveStateTiming = 0;
						}
						
						//バウンディングボックスのテストコード
						/*
						g.setColor(Color.RED);
						g.drawRect(goldMove.getBoundingBox().x,
						goldMove.getBoundingBox().y, 64, 64);			
						g.drawRect(goldMove.getUpDirectionBB().x,
						goldMove.getUpDirectionBB().y, 64, 64);
						g.drawRect(goldMove.getDownDirectionBB().x,
						goldMove.getDownDirectionBB().y, 64, 128);
						g.drawRect(goldMove.getLeftDirectionBB().x,
						goldMove.getLeftDirectionBB().y, 64, 64);
						g.drawRect(goldMove.getRightDirectionBB().x,
						goldMove.getRightDirectionBB().y, 192, 64);
						*/
						
						//　NPCが見ている範囲のボックスにキャラクターが入って来れば戦闘イベントに突入(NPCが歩いてキャラクターにぶつかるイベントが先)。
						if (menu.MenuState==Menu.MENU_DISABLE&&goldMove.moveState == GoldCharacter.MOVE_UP&&goldMove.state==GoldCharacter.MOVE_ABLE) {
							if (goldMove.getUpDirectionBB().makeBoundingBox()
									.intersects(redMove.getBoundingBox().makeBoundingBox())
									&& Math.abs(goldMove.getX() - redMove.getX()) < 24) {
								//　視界に取られたらキャラクターがNPCの方向を見て動きが止まる。
								//	そのあとにNPCがキャラクターにぶつかってくる。
								redMove.setMoveState(Character.MOVE_DOWN);
								redMove.stop();
								redMove.setMeetState(Character.MEET);
								if(redMove.getMeetState()==Character.MEET){
									if(goldMove.goldFirstSpeed!=GAME_DELAY*7){
										goldMove.goldFirstSpeed++;
									}
									//NPCがキャラクターの位置から外れている場合に佐生にある時にはNPCのYを上下の時はXを先に調整する。
									if(goldMove.getX()<redMove.getX()&&goldMove.goldFirstSpeed==GAME_DELAY*7){
										goldMove.moveRight(0);
									}else if(goldMove.getX()>redMove.getX()&&goldMove.goldFirstSpeed==GAME_DELAY*7){
										goldMove.moveLeft(0);
									}else if(goldMove.goldFirstSpeed==GAME_DELAY*7){
										if(goldMove.getBoundingBox().makeBoundingBox().intersects(
												redMove.getBoundingBox().makeBoundingBox())){	
											goldMove.stop();
											goldMove.state=GoldCharacter.MOVE_UNABLE;
											//バトルに突入。
											battle.battleState=Battle.BATTLE_IN;
										}else
											goldMove.moveUp(0);
									}
								}
							}
						}else if (menu.MenuState==Menu.MENU_DISABLE&&goldMove.moveState == GoldCharacter.MOVE_RIGHT&&goldMove.state==GoldCharacter.MOVE_ABLE) {
							if (goldMove.getRightDirectionBB().makeBoundingBox()
									.intersects(redMove.getBoundingBox().makeBoundingBox())
									&& Math.abs(goldMove.getY() - redMove.getY()) < 24) {
								redMove.setMoveState(Character.MOVE_LEFT);
								redMove.stop();
								redMove.setMeetState(Character.MEET);	
								if(redMove.getMeetState()==Character.MEET){
									if(goldMove.goldFirstSpeed!=GAME_DELAY*7){
										goldMove.goldFirstSpeed++;
									}
									//npcがキャラクターより上にある場合。
									if(goldMove.getY()<redMove.getY()&&goldMove.goldFirstSpeed==GAME_DELAY*7){
										goldMove.moveDown(0);
									}else if(goldMove.getY()>redMove.getY()&&goldMove.goldFirstSpeed==GAME_DELAY*7){
										goldMove.moveUp(0);
									}else if(goldMove.goldFirstSpeed==GAME_DELAY*7){
										if(goldMove.getBoundingBox().makeBoundingBox().intersects(
												redMove.getBoundingBox().makeBoundingBox())){	
											goldMove.stop();
											goldMove.state=GoldCharacter.MOVE_UNABLE;
											//バトルに突入
											battle.battleState=Battle.BATTLE_IN;
										}else
											goldMove.moveRight(0);
									}
								}
							}	
						}else if (menu.MenuState==Menu.MENU_DISABLE&&goldMove.moveState == GoldCharacter.MOVE_DOWN&&goldMove.state==GoldCharacter.MOVE_ABLE) {
							if (goldMove.getDownDirectionBB().makeBoundingBox()
									.intersects(redMove.getBoundingBox().makeBoundingBox())
									&& Math.abs(goldMove.getX() - redMove.getX()) < 24) {
								redMove.setMoveState(Character.MOVE_UP);
								redMove.stop();
								redMove.setMeetState(Character.MEET);
								if(redMove.getMeetState()==Character.MEET){
									if(goldMove.goldFirstSpeed!=GAME_DELAY*7){
										goldMove.goldFirstSpeed++;
									}
									//NPCがキャラクターの位置から外れている場合に佐生にある時にはNPCのYを上下の時はXを先に調整する。
									if(goldMove.getX()<redMove.getX()&&goldMove.goldFirstSpeed==GAME_DELAY*7){
										goldMove.moveRight(0);
									}else if(goldMove.getX()>redMove.getX()&&goldMove.goldFirstSpeed==GAME_DELAY*7){
										goldMove.moveLeft(0);
									}else if(goldMove.goldFirstSpeed==GAME_DELAY*7){
										if(goldMove.getBoundingBox().makeBoundingBox().intersects(
												redMove.getBoundingBox().makeBoundingBox())){	
											goldMove.stop();
											goldMove.state=GoldCharacter.MOVE_UNABLE;
											//バトル突入
											battle.battleState=Battle.BATTLE_IN;
										}else
											goldMove.moveDown(0);
									}
								}
							}
						}else if (menu.MenuState==Menu.MENU_DISABLE&&goldMove.moveState == GoldCharacter.MOVE_LEFT&&goldMove.state==GoldCharacter.MOVE_ABLE) {
							if (goldMove.getLeftDirectionBB().makeBoundingBox()
									.intersects(redMove.getBoundingBox().makeBoundingBox())
									&& Math.abs(goldMove.getY() - redMove.getY()) < 24) {
								redMove.setMoveState(Character.MOVE_RIGHT);
								redMove.stop();
								redMove.setMeetState(Character.MEET);
								if(redMove.getMeetState()==Character.MEET){
									if(goldMove.goldFirstSpeed!=GAME_DELAY*7){
										goldMove.goldFirstSpeed++;
									}
									if(goldMove.getY()<redMove.getY()&&goldMove.goldFirstSpeed==GAME_DELAY*7){
										goldMove.moveDown(0);
									}else if(goldMove.getY()>redMove.getY()&&goldMove.goldFirstSpeed==GAME_DELAY*7){
										goldMove.moveUp(0);
									}else if(goldMove.goldFirstSpeed==GAME_DELAY*7){
										if(goldMove.getBoundingBox().makeBoundingBox().intersects(
												redMove.getBoundingBox().makeBoundingBox())){	
											goldMove.stop();
											goldMove.state=GoldCharacter.MOVE_UNABLE;
											//バトル突入					
											battle.battleState=Battle.BATTLE_IN;					
										}else
											goldMove.moveLeft(0);
									}
								}					
							}
						}

		}
		if (map.mapState == Map.MOHYUN_NUM) {
			g.drawImage(map.mohyun.getBufferedImg(), map.mohyun.getX_pos(),
					map.mohyun.getY_pos(), this);		
			//バウンディングボックスのテストコード
			/*
			 for(BoundingBox index : map.mohyun.getBoundList()) {
				 g.setColor(Color.BLACK);
				 g.drawRect((int)index.makeBoundingBox().getX(
				 ),(int)index.makeBoundingBox().getY(),
				 (int)index.makeBoundingBox().getWidth(),
				 (int)index.makeBoundingBox().getHeight()); }
				 g.setColor(Color.RED); g.
				 fillRect((int)redMove.getBoundingBox
				 ().makeBoundingBox().getX(),(int)redMove
				 .getBoundingBox().makeBoundingBox().getY(),
				 (int)redMove.getBoundingBox().makeBoundingBox().getWidth(),
				 (int)redMove.getBoundingBox().makeBoundingBox().getHeight());
			
			 g.setColor(Color.RED); 				
			 g.fillRect((int)redMove.getBoundingBox().makeBoundingBox().getX(),(int)redMove
					 .getBoundingBox().makeBoundingBox().getY(),
					 (int)redMove.getBoundingBox().makeBoundingBox().getWidth(),
					 (int)redMove.getBoundingBox().makeBoundingBox().getHeight());
					 */

		}

		if (map.mapState == Map.TOWN_NUM) {
			g.drawImage(map.town.getBufferedImg(), map.town.getX_pos(),
					map.town.getY_pos(), this);
			//バウンディングボックスのテストコード
			/*
			 for(BoundingBox index : map.town.getBoundList()) {
			 g.setColor(Color.BLACK);
			 g.fillRect((int)index.makeBoundingBox().getX(
			 ),(int)index.makeBoundingBox().getY(),
			 (int)index.makeBoundingBox().getWidth(),
			 (int)index.makeBoundingBox().getHeight()); }
			 g.setColor(Color.RED);
			 g.fillRect((int)redMove.getBoundingBox(
			 ).makeBoundingBox().getX(),(int)redMove
			 .getBoundingBox().makeBoundingBox().getY(),
			 (int)redMove.getBoundingBox().makeBoundingBox().getWidth(),
			 (int)redMove.getBoundingBox().makeBoundingBox().getHeight());
			*/
			/*
			g.setColor(Color.CYAN);
			g.fillRect((int)map.town.getBoundList().get(0).x, (int)map.town.getBoundList().get(0).y
					, (int)map.town.getBoundList().get(0).width, (int)map.town.getBoundList().get(0).height);
					*/

			
		}
		//여기서 레벨업에 따른 진화를 그려준다.
		//ここでレベルに従う進化の過程を見せる。
		if(evolution.evolState==Evolution.EVOL_1){
			battle.inputBlock = Battle.YES;
			g.drawImage(battle.battleComentFrame, 0, 0, this);
			g.drawImage(redHave.pokemon.getEnemySide(
					redHave.havePokemon.pokemonList.get(evolution.evolBagPoint)), 192, 64, this);
			g.drawImage(evolution.oing, 32, 428, this);
			g.drawImage(redHave.pokemon.getBattleName(
					redHave.havePokemon.pokemonList.get(
							evolution.evolBagPoint)), 32, 492, this);
			g.drawImage(battle.OOui, 32+redHave.pokemon.getBattleName(
					redHave.havePokemon.pokemonList.get(
							evolution.evolBagPoint)).getWidth(), 492, this);
			g.drawImage(evolution.stateis, 96+redHave.pokemon.getBattleName(
					redHave.havePokemon.pokemonList.get(
							evolution.evolBagPoint)).getWidth(), 492, this);
			if(evolution.evolDelay!=GAME_DELAY*5){
				evolution.evolDelay++;
			}else{
				evolution.evolDelay=0;
				evolution.evolState=Evolution.EVOL_2;
			}
		}else if(evolution.evolState==Evolution.EVOL_2){
			g.drawImage(battle.battleComentFrame, 0, 0, this);
			g.drawImage(redHave.pokemon.getEvolImg(
					redHave.havePokemon.pokemonList.get(evolution.evolBagPoint)), 192, 64, this);
			g.drawImage(evolution.congrat, 32, 428, this);
			g.drawImage(redHave.pokemon.getBattleName(
					redHave.havePokemon.pokemonList.get(
							evolution.evolBagPoint)),256, 428, this);
			g.drawImage(battle.OOis, 256+redHave.pokemon.getBattleName(
					redHave.havePokemon.pokemonList.get(
							evolution.evolBagPoint)).getWidth(), 428, this);
			g.drawImage(redHave.pokemon.getEvolName(
					redHave.havePokemon.pokemonList.get(
							evolution.evolBagPoint)), 32, 492, this);
			g.drawImage(menu.pokemonMenu.getOOuro(), 32+redHave.pokemon.getEvolName(
					redHave.havePokemon.pokemonList.get(
							evolution.evolBagPoint)).getWidth()	, 492, this);
			if(evolution.evolDelay!=GAME_DELAY*5){
				evolution.evolDelay++;
			}else{
				evolution.evolDelay=0;
				evolution.evolState=Evolution.EVOL_3;
			}			
		}else if(evolution.evolState==Evolution.EVOL_3){
			g.drawImage(battle.battleComentFrame, 0, 0, this);
			g.drawImage(redHave.pokemon.getEvolImg(
					redHave.havePokemon.pokemonList.get(evolution.evolBagPoint)), 192, 64, this);
			g.drawImage(redHave.pokemon.getEvolName(
					redHave.havePokemon.pokemonList.get(
							evolution.evolBagPoint)), 32, 428, this);
			g.drawImage(menu.pokemonMenu.getOOuro(), 32+redHave.pokemon.getEvolName(
					redHave.havePokemon.pokemonList.get(
							evolution.evolBagPoint)).getWidth()	, 428, this);
			g.drawImage(evolution.evol, 32, 492, this);
			if(evolution.evolDelay!=GAME_DELAY*5){
				evolution.evolDelay++;
			}else{
				//肉眼で見える進化の過程ではなくて進化するポケモンのインデクスのswap
				redHave.evolution(
							redHave.havePokemon.pokemonList.get(
										evolution.evolBagPoint), evolution.evolBagPoint);
				evolution.evolState = Evolution.NONE;
				battle.inputBlock = Battle.NONE;
				evolution.evolBagPoint = Evolution.NONE;
				evolution.evolDelay=0;
			}
		}
		//野生ポケモンを撃破した時に得られるアイテムをここで解決
		if(wild.gainItemState !=Wild.NONE){
			battle.inputBlock=Battle.YES;
			g.drawImage(battle.commentFrame, 0,0,this);
			g.drawImage(battle.redName, 32,428, this);			
			if(wild.gainItemState==Item.HEALING_POTION_NUM){
				g.drawImage(battle.OOis, 96, 428, this);
				g.drawImage(redHave.item.potion.healingPotionImage, 32,488, this);
				g.drawImage(battle.OOeul, 32+96, 492, this);
				g.drawImage(menu.pokemonMenu.getGain(), 
						32+32+128+96, 492, this);
				
				if(wild.delay!=GAME_DELAY*5){
					wild.delay++;
				}else {
					redHave.haveItem.increaseHealingPotionNum();
					battle.inputBlock=Battle.NONE;
					wild.gainItemState=Wild.NONE;
					wild.delay=0;
				}
			}else if(wild.gainItemState==Item.RARE_CANDY_NUM){	
				g.drawImage(battle.OOis, 96, 428, this);
				g.drawImage(redHave.item.rareCandy.rareCandyImage, 32,480, this);
				g.drawImage(battle.OOeul, 32+redHave.item.rareCandy.rareCandyImage.getWidth(), 492, this);
				g.drawImage(menu.pokemonMenu.getGain(), 
						32+32+128+redHave.item.rareCandy.rareCandyImage.getWidth(), 492, this);
				if(wild.delay!=GAME_DELAY*5){
					wild.delay++;
				}else {
					redHave.haveItem.increaseRareCandyNum();
					battle.inputBlock=Battle.NONE;
					wild.gainItemState=Wild.NONE;
					wild.delay=0;
				}
			}
			
		}
		/*
		//여기서 npc 그려준다	
		npc.girl1.setXY();
		if (menu.MenuState==Menu.MENU_DISABLE&&map.mapState==Map.MOHYUN_NUM&&start.startState!=Start.NONE) {			
			// 시야에 잡히면 주인공이 골드를 바라보고 움직임이 막히게 되며, 골드가 주인공 바운딩박스 쪽으로 다가가
			// 부딪히게 된다.(노가다)			
			
			if(Util.prob100(0.1)){
				npc.girl1.moveDirection = NpcCharacter.Girl1.MOVE_RIGHT;
			}else if(Util.prob100(0.1)){
				npc.girl1.moveDirection = NpcCharacter.Girl1.MOVE_LEFT;
			}else if(Util.prob100(0.1)){
				npc.girl1.moveDirection = NpcCharacter.Girl1.MOVE_UP;
			}else if(Util.prob100(0.1)){
				npc.girl1.moveDirection = NpcCharacter.Girl1.MOVE_DOWN;
			}
		
			if(npc.girl1.moveDirection == NpcCharacter.Girl1.MOVE_RIGHT){
				npc.girl1.setMoveState(NpcCharacter.Girl1.MOVE_RIGHT);
				npc.girl1.moveDirection();
				npc.girl1.moveRight(0);
			}else if(npc.girl1.moveDirection == NpcCharacter.Girl1.MOVE_LEFT){
				npc.girl1.setMoveState(NpcCharacter.Girl1.MOVE_LEFT);
				npc.girl1.moveDirection();
				npc.girl1.moveLeft(0);	
			}else if(npc.girl1.moveDirection == NpcCharacter.Girl1.MOVE_UP){
				npc.girl1.setMoveState(NpcCharacter.Girl1.MOVE_UP);
				npc.girl1.moveDirection();
				npc.girl1.moveUp(0);
			}else if(npc.girl1.moveDirection == NpcCharacter.Girl1.MOVE_DOWN){
				npc.girl1.setMoveState(NpcCharacter.Girl1.MOVE_DOWN);
				npc.girl1.moveDirection();
				npc.girl1.moveDown(0);
			}		
		}		
		g.drawImage(npc.girl1.getBufferedImg(), npc.girl1.getX(), npc.girl1.getY(), this);
		g.drawRect(npc.girl1.getBoundingBox().makeBoundingBox().x,npc.girl1.getBoundingBox().makeBoundingBox().y,npc.girl1.getBoundingBox().makeBoundingBox().width,
				npc.girl1.getBoundingBox().makeBoundingBox().height);
				*/
		//バスに乗る時と進化の際にはキャラクターを外す。
		if(map.mapState!=Map.CAMPUS_HALF_NUM&&map.mapState!=Map.MOHYUN_HALF_NUM&&evolution.evolState==Evolution.NONE){
			g.drawImage(redMove.getBufferedImg(), redMove.getX(), redMove.getY(),
					this);
		}
		g.drawImage(goldMove.getBufferedImg(), goldMove.getX(),
				goldMove.getY(), this);
		// バトルに突入
		if(battle.battleState==Battle.BATTLE_IN) {	
			if(goldMove.goldBattleSpeed!=GAME_DELAY*7)
				goldMove.goldBattleSpeed++;
			if(goldMove.goldBattleSpeed==GAME_DELAY*7){
			//もしNPCとの出会いなら
				if(goldMove.state==GoldCharacter.MOVE_UNABLE){
					g.drawImage(battle.battleComentFrame,0,0,this);
					//もしバトルループに突入する前なら
					if(battle.battleLoopState!=Battle.COMMAND_SELECT){	
					//最初にキャラクターの後ろ姿とNPCの姿が動きながら現れる。
						if(redMove.firstRedBackX<64){
							redMove.firstRedBackX+=100/GAME_DELAY;
							g.drawImage(redMove.getRedBackImg(),redMove.firstRedBackX, 192, this);
						}else if(battle.startCommentState==Battle.INI_1||battle.startCommentState==Battle.INI_2
								||battle.startCommentState==Battle.INI_3||battle.startCommentState==Battle.NONE){
							g.drawImage(redMove.getRedBackImg(), 64, 192, this);
							//一度だけ始まりのstateを変える。
							if(redMove.xMoveSpeed==redMove.startXMoveSpeed){
								battle.startCommentState=Battle.INI_1;
							}
							redMove.xMoveSpeed=redMove.startXMoveSpeed+1;					
						}
						//キャラクターとnpcのイメージはINI(2と４)が過ぎるときに消えるようにする。
						if(goldMove.firstGoldFrontX>384){
							goldMove.firstGoldFrontX-=100/GAME_DELAY;
							g.drawImage(goldMove.getGoldFrontImg(), goldMove.firstGoldFrontX, 0, this);
						}else if(battle.startCommentState==Battle.INI_1||
								battle.startCommentState==Battle.NONE){
							g.drawImage(goldMove.getGoldFrontImg(), 384, 0, this);
							goldMove.xMoveSpeed=goldMove.startXMoveSpeed+1;							
						}
						//そのあとにコメントが出る。
						if(goldMove.xMoveSpeed==goldMove.startXMoveSpeed+1&&redMove.xMoveSpeed==redMove.startXMoveSpeed+1){							
							if(battle.startCommentState==Battle.INI_1){
								g.drawImage(battle.trainerText, 32,428, this);
								g.drawImage(battle.redName,64+battle.trainerText.getWidth(),428,this);
								g.drawImage(battle.OOeega,64+battle.trainerText.getWidth()
										+battle.redName.getWidth(),428,this);
								g.drawImage(battle.showBooText, 32,492, this);
								//各自が持っているポケモンの状態と数通りにポケモンボールを表現する。
								g.drawImage(battle.pokemonStatusFrame,0,0,this);
								for(int i=0;i<goldHave.havePokemon.pokemonList.size();i++){
									g.drawImage(battle.ballIamge[goldHave.pokemon.getPokemonKindArray().get(goldHave.havePokemon.pokemonList.get(i)-1).getBallState()]
											,battle.position.enemyBallX[i], battle.position.enemyBallY[i], this);									
								}
								for(int i=0;i<redHave.havePokemon.pokemonList.size();i++){
									g.drawImage(battle.ballIamge[redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getBallState()]
											,battle.position.myBallX[i], battle.position.myBallY[i], this);	
								}								
							}else if(battle.startCommentState==Battle.INI_2){							
							//	ポケモントレーナーは～～の部分
								g.drawImage(battle.trainerText, 32,428, this);
								g.drawImage(battle.redName,64+battle.trainerText.getWidth(),428,this);
								g.drawImage(battle.OOis,64+battle.trainerText.getWidth()
										+battle.redName.getWidth(),428,this);
								g.drawImage(goldHave.pokemon.getBattleName(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))
										,32,492,this);
								g.drawImage(battle.OOeul, 32+
										goldHave.pokemon.getBattleName(
												goldHave.havePokemon.pokemonList.get(
														battle.enemyPokemonIndex)).getWidth()
												,492 ,this);								
							}else if(battle.startCommentState==Battle.INI_3){								
								//～～を次に出した。の部分
								g.drawImage(goldHave.pokemon.getBattleName(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))
										,128,12,this);
								g.drawImage(goldHave.pokemon.getBattleName(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))
										,32,428,this);
								g.drawImage(battle.OOeul,32+
										goldHave.pokemon.getBattleName
										(goldHave.havePokemon.pokemonList.get(
												battle.enemyPokemonIndex)).getWidth(),428,this);
								g.drawImage(battle.sequenceText,32,492,this);
								g.drawImage(goldHave.pokemon.getEnemySide(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex)), 384, 0, this);
								g.drawImage(battle.enemyHpFrame, 0, 0,this);
								//HPが落ちるときの色の変化を表現
								if (goldHave.pokemon.getCurrentHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))>
								goldHave.pokemon.getHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex)) / 2)
									g.setColor(new Color(0, 184, 0));
								else if (goldHave.pokemon.getCurrentHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))>
								goldHave.pokemon.getHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex)) / 5)
									g.setColor(new Color(248, 168, 0));
								else
									g.setColor(new Color(248, 0, 0));
								g.fillRect(128,76, 192*goldHave.pokemon.getCurrentHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))/
										goldHave.pokemon.getHP(goldHave.havePokemon.pokemonList.get(
												battle.enemyPokemonIndex)), 8);							
							}else if(battle.startCommentState==Battle.INI_4){							
								//行け！～～！の部分
								g.drawImage(goldHave.pokemon.getBattleName(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))
										,128,12,this);
								g.drawImage(goldHave.pokemon.getEnemySide(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex)), 384, 0, this);
								g.drawImage(battle.enemyHpFrame, 0, 0,this);
								//HPが落ちるときの色の変化を表現
								if (goldHave.pokemon.getCurrentHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))>
								goldHave.pokemon.getHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex)) / 2)
									g.setColor(new Color(0, 184, 0));
								else if (goldHave.pokemon.getCurrentHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))>
								goldHave.pokemon.getHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex)) / 5)
									g.setColor(new Color(248, 168, 0));
								else
									g.setColor(new Color(248, 0, 0));
								g.fillRect(128,76, 192*goldHave.pokemon.getCurrentHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))/
										goldHave.pokemon.getHP(goldHave.havePokemon.pokemonList.get(
												battle.enemyPokemonIndex)), 8);
								
								g.drawImage(battle.goText,32,428,this);
								for(int i=0;i< redHave.havePokemon.pokemonList.size();i++){
									if(redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(i))>0){										
										Collections.swap(redHave.havePokemon.pokemonList,
												battle.startStillAliveIndex,i);
										break;
									}								
								}
								g.drawImage(redHave.pokemon.getBattleName(
										redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex))			
										,160,428,this);	
								g.drawImage(battle.exclamationText,160+redHave.pokemon.getBattleName(redHave.havePokemon.pokemonList.get(
										battle.startStillAliveIndex)).getWidth(), 428,this);
								g.drawImage(battle.myHpFrame, 0, 0, this);
								
								//HPが落ちるときの色の変化を表現
								if (redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex))>
								redHave.pokemon.getHP(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)) / 2)
									g.setColor(new Color(0, 184, 0));
								else if (redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex))>
								redHave.pokemon.getHP(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)) / 5)
									g.setColor(new Color(248, 168, 0));
								else
									g.setColor(new Color(248, 0, 0));
								g.fillRect(384, 300, 
										192*redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(
														battle.startStillAliveIndex))/	redHave.pokemon.getHP(
																redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)), 8);
								g.drawImage(redHave.pokemon.getBattleName(
										redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex))
										, 384, 236, this);	
								g.drawImage(redHave.pokemon.getMySide(
										redHave.havePokemon.pokemonList.get(
												battle.startStillAliveIndex)), 64, 192, this);						
							}
						}
						//if(battle.battleLoopState!=Battle.COMMAND_SELECT){
					}else if(battle.battleLoopState==Battle.COMMAND_SELECT){
						//もしバトルが終わったらnpcの姿だけが見えるので条件を追加する。
						if(battle.enemyPriority!=Battle.ENEMY_LOSE2){
								g.drawImage(goldHave.pokemon.getBattleName(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))
										,128,12,this);						
								g.drawImage(goldHave.pokemon.getEnemySide(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex)), 384, 0, this);
								g.drawImage(battle.enemyHpFrame, 0, 0,this);
								// HPが落ちるときの色の変化を表現
								if (goldHave.pokemon.getCurrentHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))>
								goldHave.pokemon.getHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex)) / 2)
									g.setColor(new Color(0, 184, 0));
								else if (goldHave.pokemon.getCurrentHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))>
								goldHave.pokemon.getHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex)) / 5)
									g.setColor(new Color(248, 168, 0));
								else
									g.setColor(new Color(248, 0, 0));
								g.fillRect(128,76, 192*goldHave.pokemon.getCurrentHP(goldHave.havePokemon.pokemonList.get(
										battle.enemyPokemonIndex))/
										goldHave.pokemon.getHP(goldHave.havePokemon.pokemonList.get(
												battle.enemyPokemonIndex)), 8);
						}
						if(battle.PlayerDoneState!=Battle.MY_LOSE2){
							g.drawImage(battle.myHpFrame, 0, 0, this);						
						
							//HPが落ちるときの色の変化を表現
							if (redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex))>
							redHave.pokemon.getHP(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)) / 2)
								g.setColor(new Color(0, 184, 0));
							else if (redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex))>
							redHave.pokemon.getHP(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)) / 5)
								g.setColor(new Color(248, 168, 0));
							else
								g.setColor(new Color(248, 0, 0));
							g.fillRect(384, 300, 
									192*redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex))/	redHave.pokemon.getHP(
															redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)), 8);
							g.drawImage(redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex))
									, 384, 236, this);	
							g.drawImage(redHave.pokemon.getMySide(
									redHave.havePokemon.pokemonList.get(
											battle.startStillAliveIndex)), 64, 192, this);
						}
						if(battle.battleSkillState!=Battle.RUN&&battle.PlayerDoneState!=Battle.MY_CHANGE1_PRI
								&&battle.PlayerDoneState!=Battle.MY_CHANGE2_PRI&&battle.enemyPriority==Battle.NONE
								&&battle.PlayerDoneState!=Battle.MY_ATTACK_PRI){
							g.drawImage(battle.commandFrame, 0, 0, this);
							//戦う
							if(battle.commandSelectPointer==Battle.FIGHT){
								g.drawImage(battle.pointer, battle.position.commandSelectX[0][0], 
										battle.position.commandSelectY[0][0], this);
								
							}
							//バック
							else if(battle.commandSelectPointer==Battle.BACK_PACK){
								g.drawImage(battle.pointer, battle.position.commandSelectX[0][1],
										battle.position.commandSelectY[0][1], this);
							}
							//ポケモン
							else if(battle.commandSelectPointer==Battle.POKEMON){
								g.drawImage(battle.pointer, battle.position.commandSelectX[1][0], 
										battle.position.commandSelectY[1][0], this);
							}
							//逃げる
							else if(battle.commandSelectPointer==Battle.RUN){
								g.drawImage(battle.pointer, battle.position.commandSelectX[1][1],
										battle.position.commandSelectY[1][1], this);
							}
							if(battle.battleSkillState==Battle.SKILL_SELECT){
								if(battle.PlayerDoneState!=Battle.MY_ATTACK_PRI
										&&battle.PlayerDoneState!=Battle.ENEMY_TURN){
									//	ポケモンが持っているスキルの数だけ見せる。							
									g.drawImage(battle.commandFrameSkillSelect, 0, 0, this);
									for(int i=0;i<redHave.pokemon.getSkillList(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)).size();i++){
										g.drawImage(redHave.pokemon.skill.getSkillName(
												redHave.pokemon.getSkillList(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)).get(i)), 
												battle.position.skillTextX[i],battle.position.skillTextY[i], this);								
									}
									g.drawImage(battle.pointer, battle.position.skillPointerX[battle.skillSelectPointer]
											,battle.position.skillPointerY[battle.skillSelectPointer] , this);
									//該当するスキルのタイプイメージを描くコード
									g.drawImage(redHave.pokemon.skill.getSkillType(
											redHave.pokemon.getSkillList(
													redHave.havePokemon.pokemonList.get(
															battle.startStillAliveIndex)).get(
																	battle.skillSelectPointer)).getTypeImage(),476,496, this);
								}
							}		
							//ポケモンの気絶
						//1. キャラクター側のポケモンが気絶する場合
						}else if(battle.PlayerDoneState==Battle.MY_DEAD&&
								battle.enemyPriority==Battle.MY_DEAD){						
							g.drawImage(redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											battle.startStillAliveIndex)), 32, 428, this);
							g.drawImage(battle.OOis, 32+redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											battle.startStillAliveIndex)).getWidth(), 428, this);
							g.drawImage(battle.falling, 32, 492, this);
							//初期条件を分ける部分
							if(battle.deadDelay!=GAME_DELAY*8){
								battle.deadDelay++;
							}else{
								for(int i=0;i<redHave.havePokemon.pokemonList.size();i++){
									if(redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(i))!=0){
										//もし持っているポケモンの中に生きているポケモンがいる場合(戦闘続行可能)
										battle.PlayerDoneState=Battle.MY_FILL1;									
									}else if(battle.PlayerDoneState!=Battle.MY_FILL1){
										//そうでなければ戦闘で負けたことになる。(state変更)
										battle.PlayerDoneState=Battle.MY_LOSE1;
									}
								}
								battle.deadDelay=0;
							}
						}else if(battle.PlayerDoneState==Battle.MY_FILL1&&
								battle.enemyPriority==Battle.MY_DEAD){							
							//残っているポケモンがある場合
							//まずポケモンメニュを出力。(stateで移動)	
							menu.MenuState=Menu.MAINMENU_ABLE;
							menu.mainMenu.subMenuState=Menu.MainMenu.POKEMONMENU_POSITION;
							battle.inputBlock=Battle.NONE;
							battle.battleSkillState=Battle.POKEMON;							
						}else if(battle.PlayerDoneState==Battle.MY_CHANGE3_PRI&&
								battle.enemyPriority==Battle.MY_DEAD){							
							
							//メニュstateをここで初期化。
							menu.mainMenu.subMenuState = Menu.MainMenu.NONE;
							battle.battleSkillState = Battle.NONE;	
							menu.MenuState = Menu.MENU_DISABLE;
							menu.useItemNum=Menu.NONE;
							
							g.drawImage(battle.goText, 32, 428, this);
							g.drawImage(redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											menu.pokemonMenu.pointerIndex)), 160, 428, this);
							if(battle.changeDelay!=GAME_DELAY*4){
								battle.changeDelay++;
							}else{
								//実際のポケモンswapがここで起こる
								Collections.swap(redHave.havePokemon.pokemonList, 0, menu.pokemonMenu.pointerIndex);
								//pointerIndexはポケモンの交換が済んだ後に初期化。
								menu.pokemonMenu.pointerIndex = 0;
								battle.changeDelay = 0;
								battle.enemyPriority=Battle.NONE;
								battle.PlayerDoneState=Battle.NONE;								
								battle.inputBlock=Battle.NONE;
							}
							
						}else if(battle.PlayerDoneState==Battle.MY_LOSE1&&
								battle.enemyPriority==Battle.MY_DEAD){
							//mylose1(残っているポケモンがない場合)
							g.drawImage(battle.loser, 32, 428, this);
							if(battle.deadDelay!=GAME_DELAY*8){
								battle.deadDelay++;
							}else{
								battle.PlayerDoneState=Battle.MY_LOSE2;
								battle.deadDelay=0;
							}								
						}else if(battle.PlayerDoneState==Battle.MY_LOSE2&&
								battle.enemyPriority==Battle.MY_DEAD){
							//mylose2
							g.drawImage(battle.blind, 32, 428, this);
							
							if(battle.deadDelay!=GAME_DELAY*8){
								battle.deadDelay++;
							}else{
								//バトルから出て家へ戻る。
								//マップの移動
								redMove.setMeetState(Character.MEET_NOT);
								map.building.setUpMoveState(Map.MOVE_UNABLE);	
								map.mapState = Map.ROOM_NUM;
								map.building.setX_pos(Building.START_X_POS);
								map.building.setY_pos(Building.START_Y_POS);
								map.campus.setX_pos(Campus.START_X_POS_BUS);
								map.campus.setY_pos(Campus.START_Y_POS_BUS);
								map.building.resetMoveState();
								
								//NPC初期化
								goldMove.state=GoldCharacter.MOVE_ABLE;								
								goldMove.setXY(map.building.getX_pos(), map.building.getY_pos());
								goldMove.xMoveSpeed=goldMove.startXMoveSpeed;
								goldMove.firstGoldFrontX=630;
								goldMove.goldBattleSpeed=0;
								goldMove.goldFirstSpeed=0;								
								
								//キャラクター初期化
								redMove.xMoveSpeed=redMove.startXMoveSpeed;
								redMove.firstRedBackX=-30;
								
								//バトル状態初期化						
								goldHave.havePokemon.recoveryHp();
								redHave.havePokemon.recoveryHp();
								battle.inputBlock=Battle.NONE;
								battle.startCommentState=Battle.NONE;
								battle.PlayerDoneState=Battle.NONE;
								battle.battleLoopState=Battle.NONE;
								battle.battleSkillState=Battle.NONE;
								battle.battleState=Battle.BATTLE_NO;
								battle.enemyPriority=Battle.NONE;								
								battle.deadDelay=0;								
							}													
						}
						//2. npcのポケモンが気絶した場合 (myAttack)
						else if(battle.PlayerDoneState==Battle.ENEMY_DEAD&&
								battle.enemyPriority==Battle.ENEMY_DEAD){
							//初期条件を分ける部分
							g.drawImage(battle.enemy, 32, 428, this);
							g.drawImage(battle.OOui, 64, 428, this);
							g.drawImage(goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											battle.enemyPokemonIndex)), 128, 428, this);
							g.drawImage(battle.OOis, 32+96+goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											battle.enemyPokemonIndex)).getWidth(), 428, this);
							g.drawImage(battle.falling, 32, 492, this);
							if(battle.deadDelay!=GAME_DELAY*8){
								battle.deadDelay++;
							}else{
								for(int i=0;i<goldHave.havePokemon.pokemonList.size();i++){
									if(goldHave.pokemon.getCurrentHP(goldHave.havePokemon.pokemonList.get(i))!=0){
										//もし持っているポケモンの中に生きているポケモンがいる場合(戦闘続行可能)
										battle.enemyPriority=Battle.ENEMY_FILL1;									
									}else if(battle.enemyPriority!=Battle.ENEMY_FILL1){
										battle.enemyPriority=Battle.ENEMY_LOSE1;
									}
								}
								battle.deadDelay=0;
							}
							
						}else if(battle.PlayerDoneState==Battle.ENEMY_DEAD&&
								battle.enemyPriority==Battle.ENEMY_FILL1){
							//enemyfill1(npcのポケモンが残っている場合にai.stateを活用)	
							if(battle.enemyDeadChangeState==Battle.NONE){
								ai.setAiState(ai.enemyDeadChange());
								battle.enemyDeadChangeState=Battle.YES;
							}							
							g.drawImage(battle.trainerText, 32, 428, this);
							g.drawImage(battle.redName, 64+battle.trainerText.getWidth(), 428, this);
							g.drawImage(battle.OOis, 64+battle.trainerText.getWidth()+battle.redName.getWidth(), 428, this);
							g.drawImage(goldHave.pokemon.getBattleName(					
									goldHave.havePokemon.pokemonList.get(
											ai.getAiState())), 32, 492, this);							
							g.drawImage(battle.OOeul, 32+goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											ai.getAiState())).getWidth(), 492, this);
							if(battle.deadDelay!=GAME_DELAY*8){
								battle.deadDelay++;
							}else{
								battle.enemyPriority=Battle.ENEMY_FILL2;
								battle.enemyDeadChangeState=Battle.NONE;
								battle.deadDelay=0;
								//次のstateにnpcが右から現れることのためにnpcイメージの初期化をする。
								goldMove.firstGoldFrontX=650;
							}							
						}else if(battle.PlayerDoneState==Battle.ENEMY_DEAD&&
								battle.enemyPriority==Battle.ENEMY_FILL2){
							//enemyfill2
							g.drawImage(goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											ai.getAiState())), 32, 428, this);
							g.drawImage(battle.OOeul, 32+goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											ai.getAiState())).getWidth(), 428, this);
							g.drawImage(battle.takeOut, 32, 492, this);
							
							if(battle.deadDelay!=GAME_DELAY*8){
								battle.deadDelay++;
							}else{								
								Collections.swap(goldHave.havePokemon.pokemonList, 0, ai.getAiState());
								battle.deadDelay=0;															
								battle.enemyPriority=Battle.NONE;
								battle.PlayerDoneState=Battle.NONE;								
								battle.inputBlock=Battle.NONE;								
								battle.battleSkillState=Battle.NONE;							
							}
							
						}else if(battle.PlayerDoneState==Battle.ENEMY_DEAD&&
								battle.enemyPriority==Battle.ENEMY_LOSE1){
							//ENEMY_LOSE1
							g.drawImage(battle.trainerText,32, 428, this);
							g.drawImage(battle.OOeul, 32+battle.trainerText.getWidth(), 428, this);
							g.drawImage(battle.winText, 32, 492, this);
							
							if(battle.deadDelay!=GAME_DELAY*8){
								battle.deadDelay++;
							}else{
								battle.enemyPriority=Battle.ENEMY_LOSE2;
								battle.deadDelay=0;
								goldMove.firstGoldFrontX=650;
							}
						}else if(battle.PlayerDoneState==Battle.ENEMY_DEAD&&
								battle.enemyPriority==Battle.ENEMY_LOSE2){
							//ENEMY_LOSE2
							g.drawImage(battle.enemyLoseComment, 32, 428, this);
							
							if(goldMove.firstGoldFrontX>384){
								goldMove.firstGoldFrontX-=goldMove.xMoveSpeed;
								g.drawImage(goldMove.getGoldFrontImg(), goldMove.firstGoldFrontX, 0, this);
							}else{
								g.drawImage(goldMove.getGoldFrontImg(), 384, 0, this);							
							}
							if(battle.deadDelay!=GAME_DELAY*8){
								battle.deadDelay++;
							}else{								
								//まっぽ移動
								redMove.setMeetState(Character.MEET_NOT);
								map.building.setUpMoveState(Map.MOVE_UNABLE);								
								map.building.resetMoveState();
								
								//NPC初期化
								goldMove.state=GoldCharacter.MOVE_ABLE;								
								goldMove.setXY(map.building.getX_pos()-1000, map.building.getY_pos()-1000);
								goldMove.xMoveSpeed=goldMove.startXMoveSpeed;
								goldMove.firstGoldFrontX=630;
								goldMove.goldBattleSpeed=0;
								goldMove.goldFirstSpeed=0;
								//npcが消えるようにするためのstate変更
								battle.winState=Battle.YES;
								
								//キャラクターイメージの位置初期化
								redMove.xMoveSpeed=redMove.startXMoveSpeed;
								redMove.firstRedBackX=-30;
								
								//バトル初期化								
								redHave.havePokemon.recoveryHp();
								battle.inputBlock=Battle.NONE;
								battle.startCommentState=Battle.NONE;
								battle.PlayerDoneState=Battle.NONE;
								battle.battleLoopState=Battle.NONE;
								battle.battleSkillState=Battle.NONE;
								battle.battleState=Battle.BATTLE_NO;
								battle.enemyPriority=Battle.NONE;								
								battle.deadDelay=0;								
							}	
						}else if(battle.PlayerDoneState==Battle.MY_ATTACK_PRI){
							battle.inputBlock=Battle.YES;
							//キャラクターの攻撃
							//ポケモンの名前						
							g.drawImage(
									redHave.pokemon.getBattleName(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex)), 32, 428, this);
							//の
							g.drawImage(battle.OOui, 32+redHave.pokemon.getBattleName(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex)).getWidth(), 428, this);							
							//スキルネーム
							g.drawImage(redHave.pokemon.skill.getSkillName(
									redHave.pokemon.getSkillList(
									redHave.havePokemon.pokemonList.get(
											battle.startStillAliveIndex)).get(
													battle.skillSelectPointer)), 32	,492, this);							
							//!
							g.drawImage(battle.exclamationText, 32+redHave.pokemon.skill.getSkillName(
									redHave.pokemon.getSkillList(
									redHave.havePokemon.pokemonList.get(
											battle.startStillAliveIndex)).get(
													battle.skillSelectPointer)).getWidth(), 492,this);
							
							//スキルエフェクト	
							//各スキルのEffectNumの値だけスキルエフェクトを出力する。
							if(battle.skillEffectIndex!=redHave.pokemon.skill.getEffectNum(
									redHave.pokemon.getSkillList(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex)).get(
															battle.skillSelectPointer))){
								try {
									g.drawImage(redHave.pokemon.skill.getMySideEffect(
											redHave.pokemon.getSkillList(
													redHave.havePokemon.pokemonList.get(
															battle.startStillAliveIndex)).get(
																	battle.skillSelectPointer))[battle.skillEffectIndex], 0, 0, this);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}						
							}
							sound.setEachSkillSoundState(redHave.pokemon.getSkillList(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex)).get(
															battle.skillSelectPointer));
							//ディレイに従って間の変化が必要(危機の性能の問題)
							if(battle.skillEffectDelay!=GAME_DELAY/4){
								battle.skillEffectDelay++;
							}else{
								//ディレイが1過ぎたらindexを増加させたり初期化させて次に移行。
								if(battle.skillEffectIndex!=redHave.pokemon.skill.getEffectNum(
									redHave.pokemon.getSkillList(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex)).get(
															battle.skillSelectPointer))){
									battle.skillEffectIndex++;
								}
								battle.skillEffectDelay=0;
							}
							//スキルエフェクトの長さにしたかってディレイも変化する。
							if(battle.attackDelay!=(GAME_DELAY/4)*redHave.pokemon.skill.getEffectNum(
									redHave.pokemon.getSkillList(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex)).get(
															battle.skillSelectPointer))+GAME_DELAY*2){
								battle.attackDelay++;
							}else{
								//ダメージをここで与える(攻撃の場合)
								if( redHave.pokemon.getSkillList(
												redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)).get(
														battle.skillSelectPointer)!=Skill.SUNEAT_NUM){
									battle.stunState=redHave.pokemon.getDamage(redHave.pokemon, redHave.havePokemon.pokemonList.get(
											battle.startStillAliveIndex), redHave.pokemon.getSkillList(
													redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)).get(
															battle.skillSelectPointer), goldHave.pokemon, 
															goldHave.havePokemon.pokemonList.get(battle.enemyPokemonIndex));
								}
								//回復スキルのばあい
								else if(redHave.pokemon.getSkillList(
												redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)).get(
														battle.skillSelectPointer)==Skill.SUNEAT_NUM){
								
									redHave.pokemon.healingSkillUse(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex), Skill.SUNEAT_NUM);								
								}
								//else if() //追加可能
								
								
								
								//スキルエフェクトのインデクスを初期化する。
								battle.skillEffectIndex=0;
								//ダメージを与えた敵のポケモンの体力がゼロになったことの判明。
								if(goldHave.pokemon.getCurrentHP(
										goldHave.havePokemon.pokemonList.get(
												battle.enemyPokemonIndex))==0){
									battle.PlayerDoneState=Battle.ENEMY_DEAD;
									battle.enemyPriority=Battle.ENEMY_DEAD;
									battle.attackDelay=0;
									battle.skillSelectPointer=0;									
								}else{
									//初期化
									battle.battleSkillState=Battle.NONE;
									battle.PlayerDoneState=Battle.NONE;
									battle.attackDelay=0;
									battle.skillSelectPointer=0;
									battle.inputBlock=Battle.NONE;
									if(battle.enemyPriority!=Battle.MY_TURN){	//もしキャラクターが先にターンを捕まえたら
										battle.enemyPriority=Battle.NONE;	
									}else{
										battle.enemyPriority=Battle.ENEMY_ATTACK_PRI;
									}									
								}
							}
							
						}else if(battle.enemyPriority==Battle.ENEMY_ATTACK_PRI){
							battle.inputBlock=Battle.YES;
							//敵の攻撃
							//敵
							g.drawImage(battle.enemy,32,428, this);
							//の
							g.drawImage(battle.OOui,64,428, this);
							//ポケモンの名前
							g.drawImage(goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											battle.enemyPokemonIndex)),128,428, this);
							//の
							g.drawImage(battle.OOui,128+goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											battle.enemyPokemonIndex)).getWidth(),428, this);
							//スキルネーム
							//ここでaiのmethodで決まったスキルを呼んでくる
							g.drawImage(goldHave.pokemon.skill.getSkillName(
									goldHave.pokemon.getSkillList(
											goldHave.havePokemon.pokemonList.get(
													battle.enemyPokemonIndex)).get(
															ai.getAiState()-7)), 32, 492, this);
							//!
							g.drawImage(battle.exclamationText,32+
									goldHave.pokemon.skill.getSkillName(
											goldHave.pokemon.getSkillList(
													goldHave.havePokemon.pokemonList.get(
															battle.enemyPokemonIndex)).get(
																	ai.getAiState()-7)).getWidth() , 492,this);
							
							//スキルエフェクトの部分	
							//各スキルのEffectNumの値だけスキルエフェクトを出力する。
							if(battle.skillEffectIndex!=goldHave.pokemon.skill.getEffectNum(
									goldHave.pokemon.getSkillList(
											goldHave.havePokemon.pokemonList.get(
													battle.enemyPokemonIndex)).get(
															ai.getAiState()-7))){
								try {
									g.drawImage(goldHave.pokemon.skill.getEnemySideEffect(
											goldHave.pokemon.getSkillList(
													goldHave.havePokemon.pokemonList.get(
															battle.enemyPokemonIndex)).get(
																	ai.getAiState()-7))[battle.skillEffectIndex], 0, 0, this);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}						
							}
							sound.setEachSkillSoundState(goldHave.pokemon.getSkillList(
											goldHave.havePokemon.pokemonList.get(
													battle.enemyPokemonIndex)).get(
															ai.getAiState()-7));
							
							//ディレイに従って間の変化が必要(危機の性能の問題)
							if(battle.skillEffectDelay!=GAME_DELAY/4){
								battle.skillEffectDelay++;
							}else{
								//ディレイが1過ぎたらindexを増加させたり初期化させて次に移行。
								if(battle.skillEffectIndex!=redHave.pokemon.skill.getEffectNum(
									goldHave.pokemon.getSkillList(
											goldHave.havePokemon.pokemonList.get(
													battle.enemyPokemonIndex)).get(
															ai.getAiState()-7))){
									battle.skillEffectIndex++;
								}
								battle.skillEffectDelay=0;
							}
							
							if(battle.enemyDelay!=(GAME_DELAY/4)*goldHave.pokemon.skill.getEffectNum(
									goldHave.pokemon.getSkillList(
											goldHave.havePokemon.pokemonList.get(
													battle.enemyPokemonIndex)).get(
															ai.getAiState()-7))+GAME_DELAY*2){
								battle.enemyDelay++;								
							}else{
								//ダメージをここで与える(攻撃の場合)
								if(goldHave.pokemon.getSkillList(
												goldHave.havePokemon.pokemonList.get(battle.enemyPokemonIndex)).get(
														ai.getAiState()-7)!=Skill.SUNEAT_NUM){
									battle.stunState=goldHave.pokemon.getDamage(goldHave.pokemon, goldHave.havePokemon.pokemonList.get(
											battle.enemyPokemonIndex), goldHave.pokemon.getSkillList(
													goldHave.havePokemon.pokemonList.get(battle.enemyPokemonIndex)).get(
															ai.getAiState()-7), redHave.pokemon, 
															redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex));
								}else if(goldHave.pokemon.getSkillList(
												goldHave.havePokemon.pokemonList.get(battle.enemyPokemonIndex)).get(
														ai.getAiState()-7)==Skill.SUNEAT_NUM){
									goldHave.pokemon.healingSkillUse(goldHave.havePokemon.pokemonList.get(
											battle.enemyPokemonIndex), Skill.SUNEAT_NUM);
								}
								
								
								battle.skillEffectIndex=0;
								//ダメージをもらったキャラクターのポケモンの体力がゼロになったことの判明。
								if(redHave.pokemon.getCurrentHP(
										redHave.havePokemon.pokemonList.get(
												battle.startStillAliveIndex))==0){
									battle.PlayerDoneState=Battle.MY_DEAD;
									battle.enemyPriority=Battle.MY_DEAD;
									battle.enemyDelay=0;
								}else{															
									//初期化
									battle.battleSkillState=Battle.NONE;
									battle.enemyPriority=Battle.NONE;
									battle.enemyDelay=0;
									ai.setAiState(AI.NONE);									
									if(battle.PlayerDoneState==Battle.ENEMY_TURN){	//もし摘が先にターンを捕まえたら
										battle.PlayerDoneState=Battle.MY_ATTACK_PRI;		
									}else{
										battle.PlayerDoneState=Battle.NONE;
									}
									battle.inputBlock=Battle.NONE;
								}
							}							
						}else if(battle.enemyPriority==Battle.ENEMY_ITEM1_PRI){	
							battle.inputBlock=Battle.YES;					
							//敵のアイテム使用
							//敵の回復は出ているポケモンだけさせることが可能
							// “ポケモントレーナー”
							g.drawImage(battle.trainerText, 32, 428,this);
							//“レッド”
							g.drawImage(battle.redName, 64+battle.trainerText.getWidth(), 428,this);
							//“は”
							g.drawImage(battle.OOis,64+battle.redName.getWidth()+battle.trainerText.getWidth(), 428,this);
							//“ポケモンの名前”
							g.drawImage(goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											battle.enemyPokemonIndex)), 32, 492,this);
							//“に”
							g.drawImage(battle.OOege, 32+goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											battle.enemyPokemonIndex)).getWidth(), 492, this);
							
							if(battle.enemyDelay!=GAME_DELAY*4){
								battle.enemyDelay++;
							}else{
								battle.enemyPriority=Battle.ENEMY_ITEM2_PRI;
								battle.enemyDelay=0;
							}
						}else if(battle.enemyPriority==Battle.ENEMY_ITEM2_PRI){							
							//“アイテムの種類”(今は回復薬だけ)
								g.drawImage(redHave.item.potion.healingPotionImage, 32, 420,this);
							//“を”
								g.drawImage(battle.OOeul, 128, 428, this);							
							//“使った！” 
								g.drawImage(battle.use, 32, 492, this);
							if(battle.enemyDelay!=GAME_DELAY*4){
								battle.enemyDelay++;
							}else{
								goldHave.pokemon.healThePokemon( 
										goldHave.havePokemon.pokemonList.get(
												battle.enemyPokemonIndex),Item.HEALING_POTION_NUM
												);
								goldHave.haveItem.decreaseHealingPotionNum();
								//初期化
								battle.battleSkillState=Battle.NONE;
								battle.enemyPriority=Battle.NONE;
								battle.enemyDelay=0;
								ai.setAiState(AI.NONE);								
								if(battle.PlayerDoneState==Battle.ENEMY_TURN){	//も指摘が先にターンを捕まえたら
									battle.PlayerDoneState=Battle.MY_ATTACK_PRI;		
								}else{
									battle.PlayerDoneState=Battle.NONE;
								}
								battle.inputBlock=Battle.NONE;
							}
						}else if(battle.enemyPriority==Battle.ENEMY_CHANGE1_PRI){	
							battle.inputBlock=Battle.YES;
							//敵の交代	
							// “ポケモントレーナー”
							g.drawImage(battle.trainerText, 32, 428, this);
							//“レッド”
							g.drawImage(battle.redName, 64+battle.trainerText.getWidth(), 428, this);
							//“は”
							g.drawImage(battle.OOis, 64+battle.trainerText.getWidth()+battle.redName.getWidth(), 428, this);
							//“ポケモンの名前”						
							g.drawImage(goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											ai.getAiState())), 32, 492, this);
							//“を”
							g.drawImage(battle.OOeul,32+goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											ai.getAiState())).getWidth(),492,this);							
							if(battle.enemyDelay!=GAME_DELAY*4){
								battle.enemyDelay++;
							}else{
								battle.enemyPriority=Battle.ENEMY_CHANGE2_PRI;
								battle.enemyDelay=0;
							}
						}else if(battle.enemyPriority==Battle.ENEMY_CHANGE2_PRI){
							//“ポケモンの名前”
							g.drawImage(goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											ai.getAiState())),32 ,428, this);
							//“を”
							g.drawImage(battle.OOeul, 32+goldHave.pokemon.getBattleName(
									goldHave.havePokemon.pokemonList.get(
											ai.getAiState())).getWidth(), 428, this);
							//“次に出した！”
							g.drawImage(battle.takeOut,32,492, this);							
							if(battle.enemyDelay!=GAME_DELAY*4){
								battle.enemyDelay++;
							}else{			
								Collections.swap(goldHave.havePokemon.pokemonList, 0, ai.getAiState());
								//初期化
								battle.battleSkillState=Battle.NONE;
								battle.enemyPriority=Battle.NONE;
								battle.enemyDelay=0;
								ai.setAiState(AI.NONE);							
								if(battle.PlayerDoneState==Battle.ENEMY_TURN){	//も指摘が先にターンを捕まえたら
									battle.PlayerDoneState=Battle.MY_ATTACK_PRI;		
								}else{
									battle.PlayerDoneState=Battle.NONE;
								}
								battle.inputBlock=Battle.NONE;
							}
						}						
						else if(battle.battleSkillState==Battle.RUN){
							//“逃げる”はnpcとの戦闘では選ぶことができないようにする。
							if(battle.runState==Battle.CANT_RUN_1){
								g.drawImage(battle.no, 32, 428,this);
								g.drawImage(battle.cantRunComment1, 32, 492,this);
							}else if(battle.runState==Battle.CANT_RUN_2){
								g.drawImage(battle.cantRunComment1, 32, 428, this);
								g.drawImage(battle.cantRunComment2, 32, 492, this);
							}							
						}
						//ここがポケモン交代stateの状態の出力
						//メニュはdiableさせる。(メニュが一番下に書いているため)
						else if(battle.PlayerDoneState==Battle.MY_CHANGE1_PRI){								
							battle.inputBlock=Battle.YES;
							//メニュstateをここで初期化					
							menu.mainMenu.subMenuState = Menu.MainMenu.NONE;
							battle.battleSkillState = Battle.NONE;	
							menu.MenuState = Menu.MENU_DISABLE;
							g.drawImage(redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											battle.myPokemonIndex)), 32, 428, this);
							g.drawImage(battle.nomore,64+ redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											battle.myPokemonIndex)).getWidth(), 428, this);
							g.drawImage(battle.comeBack,32 , 492,this);
							g.drawImage(battle.exclamationText, 128, 492,this);
							//MY_CHANGE1と MY_CHAGE2もディレイが存在 
							if(battle.changeDelay!=GAME_DELAY*4){
								battle.changeDelay++;
							}else{
								battle.PlayerDoneState=Battle.MY_CHANGE2_PRI;
								battle.changeDelay=0;								
							}
						}else if(battle.PlayerDoneState==Battle.MY_CHANGE2_PRI){
							g.drawImage(battle.goText, 32, 428, this);
							g.drawImage(redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											menu.pokemonMenu.pointerIndex)), 160, 428, this);
							if(battle.changeDelay!=GAME_DELAY*4){
								battle.changeDelay++;
							}else{
								//気絶したポケモンとのswap
								Collections.swap(redHave.havePokemon.pokemonList, 0, menu.pokemonMenu.pointerIndex);
								//pointerIndexはポケモンの交換が済んだ後に初期化。
								menu.pokemonMenu.pointerIndex = 0;
								battle.changeDelay = 0;
								battle.battleSkillState=Battle.NONE;
								//敵が攻撃側ならもうキャラクターのターンが終わった後なので敵が攻撃をする。
								if(ai.getAiState()>6){
									battle.enemyPriority=Battle.ENEMY_ATTACK_PRI;								
								}else if(ai.getAiState()!=AI.NONE){
									//battle.PlayerDoneState=Battle.ENEMY_TURN;
									if(ai.getAiState()==AI.HEALING){//敵がヒール
										battle.enemyPriority=Battle.ENEMY_ITEM1_PRI;
									}
									else{//敵がポケモンを交代する時									
										battle.enemyPriority=Battle.ENEMY_CHANGE1_PRI;
									}									
									//battle.PlayerDoneState=Battle.NONE;
								}									
								battle.inputBlock=Battle.NONE;
							}
						}
					}
				}
				//野生ポケモンとの戦闘
				else if(battle.wildPokemonMeetState==Battle.YES){

					//ランダムでポケモンを選ぶ
					if(wild.wildPickedState==Wild.NONE){	
						wild.randIndex = Util.randWild(wild.wildPokemonList.size());
						wild.wildPickedState =Wild.YES;
					}
					g.drawImage(battle.battleComentFrame,0,0,this);
					//バトル突入する前にini(戦闘の最初の演出)を実行
					if(battle.battleLoopState!=Battle.COMMAND_SELECT){						
						//最初に野生ポケモンの姿とキャラクターの後ろ姿がでる。
						if(redMove.firstRedBackX<64){
							redMove.firstRedBackX+=100/GAME_DELAY;
							g.drawImage(redMove.getRedBackImg(),redMove.firstRedBackX, 192, this);
						}else if(battle.startCommentState==Battle.INI_1||battle.startCommentState==Battle.INI_2
								||battle.startCommentState==Battle.INI_3||battle.startCommentState==Battle.NONE){
							g.drawImage(redMove.getRedBackImg(), 64, 192, this);
							if(redMove.xMoveSpeed==redMove.startXMoveSpeed){
								battle.startCommentState=Battle.INI_1;
							}
							redMove.xMoveSpeed=redMove.startXMoveSpeed+1;					
						}
						// npcとの戦闘とは違ってキャラクターの姿だけini(2)の後に消す。
						if(wild.firstWildPokemonFrontX>384){
							wild.firstWildPokemonFrontX-=100/GAME_DELAY;							
							g.drawImage(wild.pokemon.getEnemySide(
									wild.wildPokemonList.get(wild.randIndex)), wild.firstWildPokemonFrontX, 0, this);							
						}else{
							g.drawImage(wild.pokemon.getEnemySide(
									wild.wildPokemonList.get(wild.randIndex)), 384, 0, this);
							wild.xMoveSpeed=wild.startXMoveSpeed+1;
						}
						
						//そのあとにコメント
						if(wild.xMoveSpeed==wild.startXMoveSpeed+1&&redMove.xMoveSpeed==redMove.startXMoveSpeed+1){							
							if(battle.startCommentState==Battle.INI_1){
								
								//キャラクターのポケモンボール状態だけ出力
								g.drawImage(battle.pokemonStatusFrame,0,0,this);								
								g.setColor(Color.WHITE);
								//バトルのpokemonstatusframeイメージの再活用のためにボールプレームを白いボックスで消す。
								g.fillRect(0, 0, 360, 160);
								g.setColor(Color.BLACK);
								for(int i=0;i<redHave.havePokemon.pokemonList.size();i++){
									g.drawImage(battle.ballIamge[redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getBallState()]
											,battle.position.myBallX[i], battle.position.myBallY[i], this);	
								}
								g.drawImage(wild.at, 32,428, this);
								g.drawImage(wild.wilds, 128, 428, this);
								g.drawImage(wild.pokemon.getBattleName(
										wild.wildPokemonList.get(wild.randIndex)), 32, 492, this);
								g.drawImage(battle.OOeega, 32+wild.pokemon.getBattleName(
										wild.wildPokemonList.get(wild.randIndex)).getWidth(), 492, this);
								
							}else if(battle.startCommentState==Battle.INI_2){							
							//	“ポケモンの名前が出た！”の部分
								//キャラクターのポケモンボール状態だけ出力
								g.drawImage(battle.pokemonStatusFrame,0,0,this);								
								g.setColor(Color.WHITE);
								//バトルのpokemonstatusframeイメージの再活用のためにボールプレームを白いボックスで消す。
								g.fillRect(0, 0, 360, 160);
								g.setColor(Color.BLACK);
								for(int i=0;i<redHave.havePokemon.pokemonList.size();i++){
									g.drawImage(battle.ballIamge[redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getBallState()]
											,battle.position.myBallX[i], battle.position.myBallY[i], this);	
								}
								g.drawImage(wild.pokemon.getBattleName(
										wild.wildPokemonList.get(wild.randIndex)), 32, 428, this);
								g.drawImage(battle.OOeega, 32+wild.pokemon.getBattleName(
										wild.wildPokemonList.get(wild.randIndex)).getWidth(), 428, this);
								
								g.drawImage(wild.appear, 32, 492, this);
																
							}else if(battle.startCommentState==Battle.INI_3){								
								//“行け！～ポケモンの名前～”の部分
								g.drawImage(battle.goText, 32, 428, this);
								g.drawImage(redHave.pokemon.getBattleName(
										redHave.havePokemon.pokemonList.get(
												battle.startStillAliveIndex)), 160, 428, this);
								g.drawImage(battle.exclamationText, 160 +redHave.pokemon.getBattleName(
										redHave.havePokemon.pokemonList.get(
												battle.startStillAliveIndex)).getWidth(), 428, this);
								g.drawImage(battle.enemyHpFrame, 0, 0, this);
								// 野生ポケモンの最初の体力は100％
								
								g.setColor(new Color(0, 184, 0));								
								
								g.fillRect(128,76, 192*wild.pokemon.getCurrentHP(wild.wildPokemonList.get(
										wild.randIndex))/
										wild.pokemon.getHP(wild.wildPokemonList.get(
												wild.randIndex)), 8);	
								g.drawImage(wild.pokemon.getBattleName(wild.wildPokemonList.get(
										wild.randIndex))
										,128,12,this);						
								g.drawImage(wild.pokemon.getEnemySide(wild.wildPokemonList.get(
										wild.randIndex)), 384, 0, this);
								g.drawImage(battle.enemyHpFrame, 0, 0,this);
								if (wild.pokemon.getCurrentHP(wild.wildPokemonList.get(
										wild.randIndex))>
								wild.pokemon.getHP(wild.wildPokemonList.get(
										wild.randIndex)) / 2)
									g.setColor(new Color(0, 184, 0));
								else if (wild.pokemon.getCurrentHP(wild.wildPokemonList.get(
										wild.randIndex))>
								wild.pokemon.getHP(wild.wildPokemonList.get(
										wild.randIndex)) / 5)
									g.setColor(new Color(248, 168, 0));
								else
									g.setColor(new Color(248, 0, 0));
								g.fillRect(128,76, 192*wild.pokemon.getCurrentHP(wild.wildPokemonList.get(
										wild.randIndex))/
										wild.pokemon.getHP(wild.wildPokemonList.get(
												wild.randIndex)), 8);
						
							}
						}
						//if(battle.battleLoopState!=Battle.COMMAND_SELECT){
					}else if(battle.battleLoopState==Battle.COMMAND_SELECT){
						//バトルが終わったら敵の姿だけ見えるように条件を追加
						if(battle.enemyPriority!=Battle.ENEMY_LOSE2){
								g.drawImage(wild.pokemon.getBattleName(wild.wildPokemonList.get(
										wild.randIndex))
										,128,12,this);						
								g.drawImage(wild.pokemon.getEnemySide(wild.wildPokemonList.get(
										wild.randIndex)), 384, 0, this);
								g.drawImage(battle.enemyHpFrame, 0, 0,this);
								if (wild.pokemon.getCurrentHP(wild.wildPokemonList.get(
										wild.randIndex))>
								wild.pokemon.getHP(wild.wildPokemonList.get(
										wild.randIndex)) / 2)
									g.setColor(new Color(0, 184, 0));
								else if (wild.pokemon.getCurrentHP(wild.wildPokemonList.get(
										wild.randIndex))>
								wild.pokemon.getHP(wild.wildPokemonList.get(
										wild.randIndex)) / 5)
									g.setColor(new Color(248, 168, 0));
								else
									g.setColor(new Color(248, 0, 0));
								g.fillRect(128,76, 192*wild.pokemon.getCurrentHP(wild.wildPokemonList.get(
										wild.randIndex))/
										wild.pokemon.getHP(wild.wildPokemonList.get(
												wild.randIndex)), 8);
						}
						if(battle.PlayerDoneState!=Battle.MY_LOSE2){
							g.drawImage(battle.myHpFrame, 0, 0, this);						
						
							if (redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex))>
							redHave.pokemon.getHP(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)) / 2)
								g.setColor(new Color(0, 184, 0));
							else if (redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex))>
							redHave.pokemon.getHP(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)) / 5)
								g.setColor(new Color(248, 168, 0));
							else
								g.setColor(new Color(248, 0, 0));
							g.fillRect(384, 300, 
									192*redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex))/	redHave.pokemon.getHP(
															redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)), 8);
							g.drawImage(redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex))
									, 384, 236, this);	
							g.drawImage(redHave.pokemon.getMySide(
									redHave.havePokemon.pokemonList.get(
											battle.startStillAliveIndex)), 64, 192, this);
						}
						// 	^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
						// 	이 위는 ini4의 초기 포켓몬 배치 복사 코드 이다.(야생으로 약간의 수정을 가했다) 아래는 메뉴와 같다
						//
						//
						if(battle.battleSkillState!=Battle.RUN&&battle.PlayerDoneState!=Battle.MY_CHANGE1_PRI
								&&battle.PlayerDoneState!=Battle.MY_CHANGE2_PRI&&battle.enemyPriority==Battle.NONE
								&&battle.PlayerDoneState!=Battle.MY_ATTACK_PRI){
							g.drawImage(battle.commandFrame, 0, 0, this);
							if(battle.commandSelectPointer==Battle.FIGHT){//戦う
								g.drawImage(battle.pointer, battle.position.commandSelectX[0][0], 
										battle.position.commandSelectY[0][0], this);
								
							}
							else if(battle.commandSelectPointer==Battle.BACK_PACK){//バック
								g.drawImage(battle.pointer, battle.position.commandSelectX[0][1],
										battle.position.commandSelectY[0][1], this);
							}
							else if(battle.commandSelectPointer==Battle.POKEMON){//ポケモン
								g.drawImage(battle.pointer, battle.position.commandSelectX[1][0], 
										battle.position.commandSelectY[1][0], this);
							}
							else if(battle.commandSelectPointer==Battle.RUN){//逃げる
								g.drawImage(battle.pointer, battle.position.commandSelectX[1][1],
										battle.position.commandSelectY[1][1], this);
							}
							if(battle.battleSkillState==Battle.SKILL_SELECT){
								if(battle.PlayerDoneState!=Battle.MY_ATTACK_PRI
										&&battle.PlayerDoneState!=Battle.ENEMY_TURN){
									//	ポケモンが持っているスキル出力								
									g.drawImage(battle.commandFrameSkillSelect, 0, 0, this);
									for(int i=0;i<redHave.pokemon.getSkillList(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)).size();i++){
										g.drawImage(redHave.pokemon.skill.getSkillName(
												redHave.pokemon.getSkillList(redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)).get(i)), 
												battle.position.skillTextX[i],battle.position.skillTextY[i], this);								
									}
									g.drawImage(battle.pointer, battle.position.skillPointerX[battle.skillSelectPointer]
											,battle.position.skillPointerY[battle.skillSelectPointer] , this);
									//該当するスキルのタイプイメージを描くコード
									g.drawImage(redHave.pokemon.skill.getSkillType(
											redHave.pokemon.getSkillList(
													redHave.havePokemon.pokemonList.get(
															battle.startStillAliveIndex)).get(
																	battle.skillSelectPointer)).getTypeImage(),476,496, this);
								}
							}		
							//気絶
						//1. キャラクターのポケモンが倒れた場合(enemyAttack)
						}else if(battle.PlayerDoneState==Battle.MY_DEAD&&
								battle.enemyPriority==Battle.MY_DEAD){						
							g.drawImage(redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											battle.startStillAliveIndex)), 32, 428, this);
							g.drawImage(battle.OOis, 32+redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											battle.startStillAliveIndex)).getWidth(), 428, this);
							g.drawImage(battle.falling, 32, 492, this);
							//初期条件を分ける部分
							if(battle.deadDelay!=GAME_DELAY*8){
								battle.deadDelay++;
							}else{
								for(int i=0;i<redHave.havePokemon.pokemonList.size();i++){
									if(redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(i))!=0){
										//もし持っているポケモンの中に生きているポケモンがいる場合(戦闘続行可能)
										battle.PlayerDoneState=Battle.MY_FILL1;									
									}else if(battle.PlayerDoneState!=Battle.MY_FILL1){
										//そうでなければ戦闘で負けたことになる。(state変更)
										battle.PlayerDoneState=Battle.MY_LOSE1;
									}
								}
								battle.deadDelay=0;
							}
						}else if(battle.PlayerDoneState==Battle.ENEMY_DEAD&&
								battle.enemyPriority==Battle.ENEMY_LOSE2){							
							//初期化
							//マップの移動
							battle.wildPokemonMeetState=Battle.NONE;														
							map.campus.resetMoveState();
							
							//野生ポケモンの初期化
							wild.pokemon.setCurrentHp(
									wild.wildPokemonList.get(
											wild.randIndex), wild.pokemon.getHP(
													wild.wildPokemonList.get(wild.randIndex)));
							wild.randIndex=Wild.NONE;
							wild.randIndex=Wild.NONE;
							wild.wildPickedState=Wild.NONE;									
							wild.xMoveSpeed=wild.startXMoveSpeed;
							wild.firstWildPokemonFrontX = 650;
							
							//キャラクターイメージ初期化						
							redMove.xMoveSpeed=redMove.startXMoveSpeed;
							redMove.firstRedBackX=-30;
						
							//	バトル初期化
							goldMove.goldBattleSpeed=0;
							battle.inputBlock=Battle.NONE;
							battle.startCommentState=Battle.NONE;
							battle.PlayerDoneState=Battle.NONE;
							battle.battleLoopState=Battle.NONE;
							battle.battleSkillState=Battle.NONE;
							battle.battleState=Battle.BATTLE_NO;
							battle.enemyPriority=Battle.NONE;	
							//ここでランダムにアイテムをもらえるようにする。
							if(Util.prob100(50)){
								wild.gainItemState=Item.RARE_CANDY_NUM;
							}else if(Util.prob100(50)){
								wild.gainItemState=Item.HEALING_POTION_NUM;
							}							
						}
						else if(battle.PlayerDoneState==Battle.MY_FILL1&&
								battle.enemyPriority==Battle.MY_DEAD){							
							//myfill1(キャラクターのポケモンが残っている場合)
							//ポケモンメニュ出力
							menu.MenuState=Menu.MAINMENU_ABLE;
							menu.mainMenu.subMenuState=Menu.MainMenu.POKEMONMENU_POSITION;
							battle.inputBlock=Battle.NONE;
							battle.battleSkillState=Battle.POKEMON;							
						}else if(battle.PlayerDoneState==Battle.MY_CHANGE3_PRI&&
								battle.enemyPriority==Battle.MY_DEAD){							
							
							//メニュstate初期化
							menu.mainMenu.subMenuState = Menu.MainMenu.NONE;
							battle.battleSkillState = Battle.NONE;	
							menu.MenuState = Menu.MENU_DISABLE;
							
							g.drawImage(battle.goText, 32, 428, this);
							g.drawImage(redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											menu.pokemonMenu.pointerIndex)), 160, 428, this);
							if(battle.changeDelay!=GAME_DELAY*4){
								battle.changeDelay++;
							}else{
								//ポケモンのswap
								Collections.swap(redHave.havePokemon.pokemonList, 0, menu.pokemonMenu.pointerIndex);
								//pointerIndexはポケモンの交換の後に初期化
								menu.pokemonMenu.pointerIndex = 0;
								battle.changeDelay = 0;
								battle.enemyPriority=Battle.NONE;
								battle.PlayerDoneState=Battle.NONE;								
								battle.inputBlock=Battle.NONE;
							}
							
						}else if(battle.PlayerDoneState==Battle.MY_LOSE1&&
								battle.enemyPriority==Battle.MY_DEAD){
							//mylose1(キャラクターのポケモンが残っていない場合)
							g.drawImage(battle.loser, 32, 428, this);
							if(battle.deadDelay!=GAME_DELAY*8){
								battle.deadDelay++;
							}else{
								battle.PlayerDoneState=Battle.MY_LOSE2;
								battle.deadDelay=0;
							}								
						}else if(battle.PlayerDoneState==Battle.MY_LOSE2&&
								battle.enemyPriority==Battle.MY_DEAD){
							//mylose2
							g.drawImage(battle.blind, 32, 428, this);
							
							if(battle.deadDelay!=GAME_DELAY*8){
								battle.deadDelay++;
							}else{
								//バトルから出て家へ移動
								//マップの移動				
								map.building.setUpMoveState(Map.MOVE_UNABLE);	
								map.mapState = Map.ROOM_NUM;
								map.campus.setX_pos(Campus.START_X_POS_BUS);
								map.campus.setY_pos(Campus.START_Y_POS_BUS);
								map.campus.resetMoveState(); 
								
								//野生ポケモンの初期化
								wild.pokemon.setCurrentHp(
										wild.wildPokemonList.get(
												wild.randIndex), wild.pokemon.getHP(
														wild.wildPokemonList.get(wild.randIndex)));
								wild.randIndex=Wild.NONE;
								wild.wildPickedState=Wild.NONE;									
								wild.xMoveSpeed=wild.startXMoveSpeed;
								wild.firstWildPokemonFrontX = 650;
								
								//キャラクター初期化
								redMove.xMoveSpeed=redMove.startXMoveSpeed;
								redMove.firstRedBackX=-30;
								
								//バトル初期化
								goldMove.goldBattleSpeed=0;
								redHave.havePokemon.recoveryHp();
								battle.inputBlock=Battle.NONE;
								battle.startCommentState=Battle.NONE;
								battle.PlayerDoneState=Battle.NONE;
								battle.battleLoopState=Battle.NONE;
								battle.battleSkillState=Battle.NONE;
								battle.battleState=Battle.BATTLE_NO;
								battle.enemyPriority=Battle.NONE;								
								battle.deadDelay=0;								
							}													
						}
						//2. 敵が倒れた場合 (myAttack)
						else if(battle.PlayerDoneState==Battle.ENEMY_DEAD&&
								battle.enemyPriority==Battle.ENEMY_DEAD){
							//初期条件を分ける部分
							g.drawImage(battle.enemy, 32, 428, this);
							g.drawImage(battle.OOui, 64, 428, this);
							g.drawImage(wild.pokemon.getBattleName(
									wild.wildPokemonList.get(
											wild.randIndex)), 128, 428, this);
							g.drawImage(battle.OOis, 32+96+wild.pokemon.getBattleName(
									wild.wildPokemonList.get(
											wild.randIndex)).getWidth(), 428, this);
							g.drawImage(battle.falling, 32, 492, this);
							if(battle.deadDelay!=GAME_DELAY*8){
								battle.deadDelay++;
							}else{							
								battle.enemyPriority=Battle.ENEMY_LOSE2;								
								battle.deadDelay=0;
							}							
						}else if(battle.PlayerDoneState==Battle.MY_ATTACK_PRI){
							battle.inputBlock=Battle.YES;
							//キャラクターの攻撃
							//“ポケモンの名前”						
							g.drawImage(
									redHave.pokemon.getBattleName(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex)), 32, 428, this);
							//“の”
							g.drawImage(battle.OOui, 32+redHave.pokemon.getBattleName(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex)).getWidth(), 428, this);							
							//“スキルネーム”
							g.drawImage(redHave.pokemon.skill.getSkillName(
									redHave.pokemon.getSkillList(
									redHave.havePokemon.pokemonList.get(
											battle.startStillAliveIndex)).get(
													battle.skillSelectPointer)), 32	,492, this);							
							//!
							g.drawImage(battle.exclamationText, 32+redHave.pokemon.skill.getSkillName(
									redHave.pokemon.getSkillList(
									redHave.havePokemon.pokemonList.get(
											battle.startStillAliveIndex)).get(
													battle.skillSelectPointer)).getWidth(), 492,this);
		
							
							//スキルエフェクト
							//各スキルのEffectNumの値だけスキルエフェクトを出力する。
							if(battle.skillEffectIndex!=redHave.pokemon.skill.getEffectNum(
									redHave.pokemon.getSkillList(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex)).get(
															battle.skillSelectPointer))){
								try {
									g.drawImage(redHave.pokemon.skill.getMySideEffect(
											redHave.pokemon.getSkillList(
													redHave.havePokemon.pokemonList.get(
															battle.startStillAliveIndex)).get(
																	battle.skillSelectPointer))[battle.skillEffectIndex], 0, 0, this);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}						
							}
							//スキルが出力されると同時にサウンドstateも変わる。
							sound.setEachSkillSoundState(redHave.pokemon.getSkillList(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex)).get(
															battle.skillSelectPointer));
							if(battle.skillEffectDelay!=GAME_DELAY/4){
								battle.skillEffectDelay++;
							}else{
								if(battle.skillEffectIndex!=redHave.pokemon.skill.getEffectNum(
									redHave.pokemon.getSkillList(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex)).get(
															battle.skillSelectPointer))){
									battle.skillEffectIndex++;
								}
								battle.skillEffectDelay=0;
							}
							if(battle.attackDelay!=(GAME_DELAY/4)*redHave.pokemon.skill.getEffectNum(
									redHave.pokemon.getSkillList(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex)).get(
															battle.skillSelectPointer))+GAME_DELAY*2){
								battle.attackDelay++;
							}else{								
								//ダメージを与える(攻撃)
								if( redHave.pokemon.getSkillList(
												redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)).get(
														battle.skillSelectPointer)!=Skill.SUNEAT_NUM){
									battle.stunState=redHave.pokemon.getDamage(redHave.pokemon, redHave.havePokemon.pokemonList.get(
											battle.startStillAliveIndex), redHave.pokemon.getSkillList(
													redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)).get(
															battle.skillSelectPointer), wild.pokemon, 
															wild.wildPokemonList.get(wild.randIndex));
								}
								//回復スキル
								else if(redHave.pokemon.getSkillList(
												redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex)).get(
														battle.skillSelectPointer)==Skill.SUNEAT_NUM){
								
									redHave.pokemon.healingSkillUse(
											redHave.havePokemon.pokemonList.get(
													battle.startStillAliveIndex), Skill.SUNEAT_NUM);								
								}						
								
								//スキルエフェクトのインデクスを初期化
								battle.skillEffectIndex=0;
								//ダメージを与えて敵の体力がゼロになったことを判明する。
								if(wild.pokemon.getCurrentHP(
										wild.wildPokemonList.get(
												wild.randIndex))==0){
									battle.PlayerDoneState=Battle.ENEMY_DEAD;
									battle.enemyPriority=Battle.ENEMY_DEAD;
									battle.attackDelay=0;
									battle.skillSelectPointer=0;									
								}else{
									//初期化
									battle.battleSkillState=Battle.NONE;
									battle.PlayerDoneState=Battle.NONE;
									battle.attackDelay=0;
									battle.skillSelectPointer=0;
									battle.inputBlock=Battle.NONE;
									if(battle.enemyPriority!=Battle.MY_TURN){	//もしキャラクターが先行したら 
										battle.enemyPriority=Battle.NONE;	
									}else{
										battle.enemyPriority=Battle.ENEMY_ATTACK_PRI;
									}									
								}
							}							
						}else if(battle.enemyPriority==Battle.ENEMY_ATTACK_PRI){
							battle.inputBlock=Battle.YES;
							//敵の攻撃
							//“敵”
							g.drawImage(battle.enemy,32,428, this);
							//“の”
							g.drawImage(battle.OOui,64,428, this);
							//“ポケモンの名前”
							g.drawImage(wild.pokemon.getBattleName(
									wild.wildPokemonList.get(
											wild.randIndex)),128,428, this);
							//“の”
							g.drawImage(battle.OOui,128+wild.pokemon.getBattleName(
									wild.wildPokemonList.get(
											wild.randIndex)).getWidth(),428, this);
							//“スキルの名前”
							//aiのmethodで決まった行動をする。
							g.drawImage(wild.pokemon.skill.getSkillName(
									wild.pokemon.getSkillList(
											wild.wildPokemonList.get(
													wild.randIndex)).get(
															ai.getAiState()-7)), 32, 492, this);
							//!
							g.drawImage(battle.exclamationText,32+
									wild.pokemon.skill.getSkillName(
											wild.pokemon.getSkillList(
													wild.wildPokemonList.get(
															wild.randIndex)).get(
																	ai.getAiState()-7)).getWidth() , 492,this);
							
							//スキルエフェクト
							//各スキルのEffectNumの値だけスキルエフェクトを出力する。
							if(battle.skillEffectIndex!=wild.pokemon.skill.getEffectNum(
									wild.pokemon.getSkillList(
											wild.wildPokemonList.get(
													wild.randIndex)).get(
															ai.getAiState()-7))){
								try {
									g.drawImage(wild.pokemon.skill.getEnemySideEffect(
											wild.pokemon.getSkillList(
													wild.wildPokemonList.get(
															wild.randIndex)).get(
																	ai.getAiState()-7))[battle.skillEffectIndex], 0, 0, this);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}						
							}
							sound.setEachSkillSoundState(wild.pokemon.getSkillList(
											wild.wildPokemonList.get(
													wild.randIndex)).get(
															ai.getAiState()-7));
							if(battle.skillEffectDelay!=GAME_DELAY/4){
								battle.skillEffectDelay++;
							}else{
								if(battle.skillEffectIndex!=wild.pokemon.skill.getEffectNum(
									wild.pokemon.getSkillList(
											wild.wildPokemonList.get(
													wild.randIndex)).get(
															ai.getAiState()-7))){
									battle.skillEffectIndex++;
								}
								battle.skillEffectDelay=0;
							}
							
							if(battle.enemyDelay!=(GAME_DELAY/4)*wild.pokemon.skill.getEffectNum(
									wild.pokemon.getSkillList(
											wild.wildPokemonList.get(
													wild.randIndex)).get(
															ai.getAiState()-7))+GAME_DELAY*2){
								battle.enemyDelay++;								
							}else{
								if( wild.pokemon.getSkillList(
												wild.wildPokemonList.get(wild.randIndex)).get(
														ai.getAiState()-7)!=Skill.SUNEAT_NUM){
									battle.stunState=wild.pokemon.getDamage(wild.pokemon, wild.wildPokemonList.get(
											wild.randIndex), wild.pokemon.getSkillList(
													wild.wildPokemonList.get(wild.randIndex)).get(
															ai.getAiState()-7), redHave.pokemon, 
															redHave.havePokemon.pokemonList.get(battle.startStillAliveIndex));
								}else if(wild.pokemon.getSkillList(
												wild.wildPokemonList.get(wild.randIndex)).get(
														ai.getAiState()-7)==Skill.SUNEAT_NUM){
									wild.pokemon.healingSkillUse(wild.wildPokemonList.get(wild.randIndex), Skill.SUNEAT_NUM);
								}
								
								battle.skillEffectIndex=0;
								if(redHave.pokemon.getCurrentHP(
										redHave.havePokemon.pokemonList.get(
												battle.startStillAliveIndex))==0){
									battle.PlayerDoneState=Battle.MY_DEAD;
									battle.enemyPriority=Battle.MY_DEAD;
									battle.enemyDelay=0;
								}else{															
									//初期化
									battle.battleSkillState=Battle.NONE;
									battle.enemyPriority=Battle.NONE;
									battle.enemyDelay=0;
									ai.setAiState(AI.NONE);									
									if(battle.PlayerDoneState==Battle.ENEMY_TURN){	//も指摘が先行したら
										battle.PlayerDoneState=Battle.MY_ATTACK_PRI;		
									}else{
										battle.PlayerDoneState=Battle.NONE;
									}
									battle.inputBlock=Battle.NONE;
								}
							}							
						}else if(battle.battleSkillState==Battle.RUN){
							//野生ポケモンとの戦闘では100％逃げることができる。
							g.drawImage(wild.run, 32, 428, this);
							if(wild.delay!=GAME_DELAY*4){
								wild.delay++;
							}else{
								//初期化
								//マップの移動
								battle.wildPokemonMeetState=Battle.NONE;														
								map.campus.resetMoveState();
						
								//野生ポケモンの初期化
								wild.pokemon.setCurrentHp(
										wild.wildPokemonList.get(
												wild.randIndex), wild.pokemon.getHP(
														wild.wildPokemonList.get(wild.randIndex)));
								wild.randIndex=Wild.NONE;
								wild.wildPickedState=Wild.NONE;									
								wild.xMoveSpeed=wild.startXMoveSpeed;
								wild.firstWildPokemonFrontX = 650;
								
								//キャラクターの初期化
								redMove.xMoveSpeed=redMove.startXMoveSpeed;
								redMove.firstRedBackX=-30;
							
								//	バトル初期化				
								goldMove.goldBattleSpeed=0;
								battle.inputBlock=Battle.NONE;
								battle.startCommentState=Battle.NONE;
								battle.PlayerDoneState=Battle.NONE;
								battle.battleLoopState=Battle.NONE;
								battle.battleSkillState=Battle.NONE;
								battle.battleState=Battle.BATTLE_NO;
								battle.enemyPriority=Battle.NONE;								
								wild.delay=0;
							}							
						}
						//ここがポケモン交代stateの状態の出力
						//メニュはdiableさせる。(メニュが一番下に書いているため)
						else if(battle.PlayerDoneState==Battle.MY_CHANGE1_PRI){								
							battle.inputBlock=Battle.YES;
							//ここでメニュstateを初期化				
							menu.mainMenu.subMenuState = Menu.MainMenu.NONE;
							battle.battleSkillState = Battle.NONE;	
							menu.MenuState = Menu.MENU_DISABLE;
							g.drawImage(redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											battle.myPokemonIndex)), 32, 428, this);
							g.drawImage(battle.nomore,64+ redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											battle.myPokemonIndex)).getWidth(), 428, this);
							g.drawImage(battle.comeBack,32 , 492,this);
							g.drawImage(battle.exclamationText, 128, 492,this);
							//MY_CHANGE1、 MY_CHAGE2 状態でディレイ
							if(battle.changeDelay!=GAME_DELAY*4){
								battle.changeDelay++;
							}else{
								battle.PlayerDoneState=Battle.MY_CHANGE2_PRI;
								battle.changeDelay=0;								
							}
						}else if(battle.PlayerDoneState==Battle.MY_CHANGE2_PRI){
							g.drawImage(battle.goText, 32, 428, this);
							g.drawImage(redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											menu.pokemonMenu.pointerIndex)), 160, 428, this);
							if(battle.changeDelay!=GAME_DELAY*4){
								battle.changeDelay++;
							}else{
								Collections.swap(redHave.havePokemon.pokemonList, 0, menu.pokemonMenu.pointerIndex);
								menu.pokemonMenu.pointerIndex = 0;
								battle.changeDelay = 0;
								battle.battleSkillState=Battle.NONE;
								if(ai.getAiState()>6){//敵の攻撃ならキャラクターのターンはすんだあとだから攻撃
									battle.enemyPriority=Battle.ENEMY_ATTACK_PRI;								
								}else if(ai.getAiState()!=AI.NONE){
									//battle.PlayerDoneState=Battle.ENEMY_TURN;
									if(ai.getAiState()==AI.HEALING){
										battle.enemyPriority=Battle.ENEMY_ITEM1_PRI;
									}
									else{									
										battle.enemyPriority=Battle.ENEMY_CHANGE1_PRI;
									}									
									//battle.PlayerDoneState=Battle.NONE;
								}									
								battle.inputBlock=Battle.NONE;
							}
						}
					}
				}
			}		
		}
		// メニュの出力
		if (menu.MenuState == Menu.MAINMENU_ABLE) {
			g.drawImage(menu.mainMenu.getMainMenu(), 0, 0, this);
			if (menu.mainMenu.pointerIndex == Menu.MainMenu.POKEMONMENU_POSITION)
				g.drawImage(menu.mainMenu.getPocketmonTextField(), 0, 0, this);
			else if (menu.mainMenu.pointerIndex == Menu.MainMenu.BAGMENU_POSITION)
				g.drawImage(menu.mainMenu.getBagTextField(), 0, 0, this);
			else if (menu.mainMenu.pointerIndex == Menu.MainMenu.EXIT_POSITION)
				g.drawImage(menu.mainMenu.getExitTextField(), 0, 0, this);

			g.drawImage(menu.mainMenu.getPointer(),
					mainPointerPosition.get(menu.mainMenu.pointerIndex).x,
					mainPointerPosition.get(menu.mainMenu.pointerIndex).y, this);
			// サブメニュ
			if (menu.mainMenu.subMenuState == Menu.MainMenu.POKEMONMENU_POSITION) {	
			
				//この条件で各バトルの状況と一般状況でのポケモンコメントが出力
				if(battle.battleSkillState==Battle.POKEMON&&battle.itemToPokemon==Battle.YES){
					g.drawImage(battle.commandFrameItemUseSelect, 0, 0, this);
					//itemtopokemon 状況でアイテムを使う場合
					if(battle.PlayerDoneState==Battle.MY_ITEM_PRI){
						//50回復薬
						if(battle.itemUsedNumState==Item.HEALING_POTION_NUM){
							battle.inputBlock=Battle.YES;
							//~~
							g.drawImage(battle.commentFrame, 0, 0, this);
							g.drawImage(redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											menu.pokemonMenu.pointerIndex)), 32, 428, this);
							//の
							g.drawImage(battle.OOui,32+ redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											menu.pokemonMenu.pointerIndex)).getWidth() ,428 , this);
							//体力が
							g.drawImage(battle.hpGa,96 + redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											menu.pokemonMenu.pointerIndex)).getWidth() ,428 , this);
														
							g.drawImage(redHave.item.potion.healingPotionVolume, 32, 492, this);
							g.drawImage(battle.beHeal, 128, 492, this);
							if(battle.potionDelay!=GAME_DELAY*7){
								battle.potionDelay++;
							}							
							else{
								//ポケモンバックから出る								
								menu.pokemonMenu.pointerIndex=0;
								menu.backPackMenu.pointerIndex=0;
								menu.mainMenu.subMenuState=Menu.MainMenu.NONE;																
								menu.MenuState = Menu.MENU_DISABLE;
								menu.useItemNum=Menu.NONE;
								
								battle.battleSkillState=Battle.NONE;					
								battle.itemUsedNumState=Battle.NONE;								
								battle.potionDelay=0;
								battle.PlayerDoneState=Battle.NONE;							
								battle.itemToPokemon=Battle.NONE;
								battle.inputBlock=Battle.NONE;
								battle.enemyPriority=Battle.ENEMY_ATTACK_PRI;
								menu.itemToPokemon=Menu.NONE;
							}
							
						}
						//else if(){ //他の回復の場合
					}
				}else if(menu.itemToPokemon==Menu.DO){
					g.drawImage(battle.commandFrameItemUseSelect, 0, 0, this);
					if(menu.useItemNum==Item.HEALING_POTION_NUM){
						battle.inputBlock=Battle.YES;
						//~~
						g.drawImage(battle.commentFrame, 0, 0, this);
						g.drawImage(redHave.pokemon.getBattleName(
								redHave.havePokemon.pokemonList.get(
										menu.pokemonMenu.pointerIndex)), 32, 428, this);
						//の
						g.drawImage(battle.OOui,32+ redHave.pokemon.getBattleName(
								redHave.havePokemon.pokemonList.get(
										menu.pokemonMenu.pointerIndex)).getWidth() ,428 , this);
						//体力が
						g.drawImage(battle.hpGa,96 + redHave.pokemon.getBattleName(
								redHave.havePokemon.pokemonList.get(
										menu.pokemonMenu.pointerIndex)).getWidth() ,428 , this);
													
						g.drawImage(redHave.item.potion.healingPotionVolume, 32, 492, this);
						g.drawImage(battle.beHeal, 128, 492, this);
						if(redHave.item.potionDelay!=GAME_DELAY*7){
							redHave.item.potionDelay++;
						}else{
							//ポケモンバックから出る							
							menu.pokemonMenu.pointerIndex=0;
							menu.backPackMenu.pointerIndex=0;
							menu.mainMenu.subMenuState=Menu.MainMenu.NONE;
							menu.itemToPokemon=Menu.NONE;
							menu.MenuState = Menu.MENU_DISABLE;	
							menu.useItemNum=Menu.NONE;
							
							battle.inputBlock=Battle.NONE;						
							redHave.item.potionDelay=0;
						}
					}else if(menu.useItemNum==Item.RARE_CANDY_NUM){
						g.drawImage(battle.commandFrameItemUseSelect, 0, 0, this);
						if(menu.useItemNum==Item.RARE_CANDY_NUM){
							battle.inputBlock=Battle.YES;
							//~~
							g.drawImage(battle.commentFrame, 0, 0, this);
							g.drawImage(redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											menu.pokemonMenu.pointerIndex)), 32, 428, this);
							//の
							g.drawImage(battle.OOui,32+ redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											menu.pokemonMenu.pointerIndex)).getWidth() ,428 , this);
							//レベルが
							g.drawImage(menu.pokemonMenu.getLevelee(),96 + redHave.pokemon.getBattleName(
									redHave.havePokemon.pokemonList.get(
											menu.pokemonMenu.pointerIndex)).getWidth() ,428 , this);

							//レベルのフォント
							g.setFont(redHave.pokemon.levelFont);
							
							// レベルの数字
							g.setColor(Color.BLACK);
							g.drawString(String.valueOf(redHave.pokemon.getLevel(
									redHave.havePokemon.pokemonList.get(
											menu.pokemonMenu.pointerIndex))),32,540);							
							//で
							//10以上なら10の段に10以下なら1の段を上げる。
							if(redHave.pokemon.getLevel(
									redHave.havePokemon.pokemonList.get(
											menu.pokemonMenu.pointerIndex))>=10){
								g.drawImage(menu.pokemonMenu.getOOuro(), 32+64, 492, this);
								
								g.drawImage(menu.pokemonMenu.getBe(), 184+64, 492, this);
							}else{
								g.drawImage(menu.pokemonMenu.getOOuro(), 32+32, 492, this);
								
								g.drawImage(menu.pokemonMenu.getBe(), 184+32, 492, this);
							}		
							
							if(redHave.item.rareCandyDelay!=GAME_DELAY*7){
								redHave.item.rareCandyDelay++;
							}else{
								//ポケモンバックから出る								
								
								menu.backPackMenu.pointerIndex=0;
								menu.mainMenu.subMenuState=Menu.MainMenu.NONE;
								menu.itemToPokemon=Menu.NONE;
								menu.MenuState = Menu.MENU_DISABLE;	
								menu.useItemNum=Menu.NONE;
								
								battle.inputBlock=Battle.NONE;						
								redHave.item.rareCandyDelay=0;
								//ここでアイテムを使ったポケモンのレベルを確認した後進化するポケモンだと判明した場合にstateを変更する。
								if(redHave.pokemon.getLevel(
										redHave.havePokemon.pokemonList.get(
												menu.pokemonMenu.pointerIndex))==
												redHave.pokemon.getEvolLevel(
														redHave.havePokemon.pokemonList.get(
																menu.pokemonMenu.pointerIndex))){
									evolution.evolState=Evolution.EVOL_1;
									evolution.evolBagPoint=menu.pokemonMenu.pointerIndex;
								}
								
								menu.pokemonMenu.pointerIndex=0;
							}
						}
					}
				}else if(battle.battleSkillState==Battle.POKEMON&&battle.itemToPokemon==Battle.NO){
					g.drawImage(battle.commandFramePokemonSelect, 0, 0, this);
				}else{
					g.drawImage(menu.pokemonMenu.getFrame(), 0, 0, this);
				}
				g.drawImage(menu.pokemonMenu.getPointer(),
						pokemonMenuPointerPosition
								.get(menu.pokemonMenu.pointerIndex).x,
						pokemonMenuPointerPosition
								.get(menu.pokemonMenu.pointerIndex).y, this);				
				if(battle.PlayerDoneState!=Battle.MY_ITEM_PRI){
					g.drawImage(
							menu.pokemonMenu.getExit(),
							pokemonMenuPointerPosition
							.get(redHave.havePokemon.pokemonList.size()).iconNameX,
							pokemonMenuPointerPosition
									.get(redHave.havePokemon.pokemonList.size()).iconNameY,
									this);
				}

				//ポケモンがあるだけポケモンバック出力
				
				//レベルのフォント
				g.setFont(redHave.pokemon.levelFont);
				for (int i = 0; i < redHave.havePokemon.pokemonList.size(); i++) {					
					// 各ポケモンをバックメニュに出力
					g.setColor(Color.BLACK);
					g.drawString(String.valueOf(redHave.pokemon.getLevel(redHave.havePokemon.pokemonList.get(i))), 
							pokemonMenuPointerPosition.get(i).levelX,
							pokemonMenuPointerPosition.get(i).levelY);
					// hpの色表現
					if (redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getCurrentHp() 
							> redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getHp() / 2) {
						g.setColor(new Color(0, 184, 0));
					}
					else if (redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getCurrentHp() 
							> redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getHp() / 5) {
						g.setColor(new Color(248, 168, 0));
					}
					else{
						g.setColor(new Color(248, 0, 0));
					}
					//気絶したポケモンの状態を変更
					if (redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getCurrentHp() <= 0) {
						redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).setBallState(Pokemon.PokemonKind.STUNED);
						g.drawImage(redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getStun(),
								pokemonMenuPointerPosition.get(i).stunX,
								pokemonMenuPointerPosition.get(i).stunY,
								this);
					}else{
						redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).setBallState(Pokemon.PokemonKind.NOT_STUNED);
					}
					g.fillRect(pokemonMenuPointerPosition.get(i).hpBarX,
							pokemonMenuPointerPosition.get(i).hpBarY, 192
									* redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getCurrentHp()
									/ redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getHp(), 8);
					g.drawImage(redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getBagHpFrame(),
							pokemonMenuPointerPosition.get(i).hpFrameX,
							pokemonMenuPointerPosition.get(i).hpFrameY,
							this);
					g.drawImage(redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(i)-1).getBagComment(),
							pokemonMenuPointerPosition.get(i).iconNameX,
							pokemonMenuPointerPosition.get(i).iconNameY,
							this);
				}								
			} else if (menu.mainMenu.subMenuState == Menu.MainMenu.BAGMENU_POSITION) {
				
				g.setFont(redHave.item.itemFont);
				
				g.drawImage(menu.backPackMenu.getBackPack(), 0, 0, this);
				g.drawImage(menu.backPackMenu.getBackPackPointer(),
						backPackPointerPosition
								.get(menu.backPackMenu.pointerIndex).x,
						backPackPointerPosition
								.get(menu.backPackMenu.pointerIndex).y, this);
				g.drawImage(menu.backPackMenu.getExit(),
						backPackPointerPosition
								.get(redHave.haveItem.currentState).itemX,
						backPackPointerPosition
								.get(redHave.haveItem.currentState).itemY, this);
				if (redHave.item.potion.existState != -1) {
					//アイテムが存在する場合
					g.drawImage(redHave.item.potion.healingPotionImage,
							backPackPointerPosition
									.get(redHave.item.potion.existState).itemX,
							backPackPointerPosition
									.get(redHave.item.potion.existState).itemY,
							this);
					if (menu.backPackMenu.pointerIndex == redHave.item.potion.existState)
						g.drawImage(redHave.item.potion.healPotionComment, 0,
								0, this);
					g.setColor(Color.BLACK);
					g.drawString("x"+String.valueOf(redHave.item.potion.number), 
							backPackPointerPosition
							.get(redHave.item.potion.existState).numX,
							backPackPointerPosition
							.get(redHave.item.potion.existState).numY);
				}
				if (redHave.item.rareCandy.existState != -1) {
					g.drawImage(redHave.item.rareCandy.rareCandyImage,
							backPackPointerPosition
									.get(redHave.item.rareCandy.existState).itemX,
							backPackPointerPosition
									.get(redHave.item.rareCandy.existState).itemY,
							this);
					g.setColor(Color.BLACK);
					g.drawString("x"+String.valueOf(redHave.item.rareCandy.number), 
							backPackPointerPosition
							.get(redHave.item.rareCandy.existState).numX,
							backPackPointerPosition
							.get(redHave.item.rareCandy.existState).numY);
					if (menu.backPackMenu.pointerIndex == redHave.item.rareCandy.existState)
						g.drawImage(redHave.item.rareCandy.rareCandyComment, 0,
								0, this);
				}
				if (redHave.item.ball.existState != -1) {
					g.drawImage(redHave.item.ball.monsterBallImage,
							backPackPointerPosition
									.get(redHave.item.ball.existState).itemX,
							backPackPointerPosition
									.get(redHave.item.ball.existState).itemY,
							this);
					g.setColor(Color.BLACK);
					g.drawString("x"+String.valueOf(redHave.item.ball.number), 
							backPackPointerPosition
							.get(redHave.item.ball.existState).numX,
							backPackPointerPosition
							.get(redHave.item.ball.existState).numY);
					if (menu.backPackMenu.pointerIndex == redHave.item.ball.existState)
						g.drawImage(redHave.item.ball.monsterBallComment, 0, 0,
								this);
				}
			}
		}
		//ゲーム最初の鳥の場面が出る。(キーの入力前まで)
		if(start.startState==Start.NONE){			
			g.drawImage(start.getStartImage(), 0, 0, this);			
		}
		// g.drawImage(icon,map.getX_pos(),map.getY_pos(),this);
		
		lastTime=currentTime;
	}
}

public class Pocketmon {
	public static int FRAME_W = 640;
	public static int FRAME_H = 576;
	// mohyun X2
	public static int MOHYUN_HALF_W = 1054+16;
	public static int MOHYUN_HALF_H = 736+38;
	// campus X2
	public static int CAMPUS_HALF_W = 1184+16;
	public static int CAMPUS_HALF_H = 768+38;
	
	public static JFrame f;

	public static void main(String[] args) throws IOException, FontFormatException,UnsupportedAudioFileException, LineUnavailableException{
		f = new JFrame("포켓몬스터");
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setSize(FRAME_W +16, FRAME_H+38 );
		PocketmonComponent pp = new PocketmonComponent();
		f.add(pp);
		f.setVisible(true);

	}
}
