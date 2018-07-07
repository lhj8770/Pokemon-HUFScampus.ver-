package Pocketmon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Collections;

import Information.GoldHave;
import Information.Item;
import Information.Pokemon;
import Information.RedHave;
import Information.Sound;
import Information.Util;
import Pocketmon.Menu.Position;

public class MoveEvent extends KeyAdapter {
	private static MoveEvent moveEventInstance;
	
	private Character redMove = null;
	private Map map = null;
	private Menu menu = null;
	private RedHave redHave = null;
	private GoldCharacter goldMove = null;
	private Battle battle = null;
	private AI ai = null;
	private GoldHave goldHave = null;
	private Wild wild = null;
	private Evolution evolution = null;
	private Start start = null;
	private Sound sound = null;

	private MoveEvent() {
	}
	
	public static MoveEvent getInstance(){
		if(moveEventInstance==null){
			moveEventInstance=new MoveEvent();
		}
		return moveEventInstance;
	}
	public void initObject(Character redMove, Map map, Menu menu, RedHave redHave,
			GoldCharacter goldMove, Battle battle,AI ai,GoldHave goldHave, 
			Wild wild, Evolution evolution,Start start,Sound sound){
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
		this.sound = sound;		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		//バウンディングボックス処理の時にキャラクターが方向を横に動く時にバウンディングボックスから脱出できるようにバウンディングボックスの反対の方向に
		//ある程度マップを動く。
		if(start.startState==Start.NONE){
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ENTER:
				start.startState=Start.YES;
			}
		}
		else if ((battle.inputBlock!=Battle.YES&&menu.MenuState == Menu.MENU_DISABLE
				&&battle.battleState==Battle.BATTLE_NO)){
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:	
				wild.walkState=Wild.YES;
				if (map.mapState == Map.ROOM_NUM) { 
					if (map.room.getLeftMoveState() == 0) {
						map.room.moveRight(5);
					}
					if (map.room.getRightMoveState() == 0) {
						map.room.moveLeft(5);
					}
					if (map.room.getDownMoveState() == 0) {
						map.room.moveUp(5);
					}
					redMove.setMoveState(Character.MOVE_UP);
					map.room.moveUp(0);
				} else if (map.mapState == Map.TOWN_NUM && 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.town.getLeftMoveState() == 0) {
						map.town.moveRight(5);
					}
					if (map.town.getRightMoveState() == 0) {
						map.town.moveLeft(5);
					}
					if (map.town.getDownMoveState() == 0) {
						map.town.moveUp(5);
					}
					redMove.setMoveState(Character.MOVE_UP);
					map.town.moveUp(0);
				}else if (map.mapState == Map.MOHYUN_NUM && 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.mohyun.getLeftMoveState() == 0) {
						map.mohyun.moveRight(5);
					}
					if (map.mohyun.getRightMoveState() == 0) {
						map.mohyun.moveLeft(5);
					}
					if (map.mohyun.getDownMoveState() == 0) {
						map.mohyun.moveUp(5);
					}
					redMove.setMoveState(Character.MOVE_UP);
					map.mohyun.moveUp(0);
				}else if (map.mapState == Map.CAMPUS_NUM && 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.campus.getLeftMoveState() == 0) {
						map.campus.moveRight(5);
					}
					if (map.campus.getRightMoveState() == 0) {
						map.campus.moveLeft(5);
					}
					if (map.campus.getDownMoveState() == 0) {
						map.campus.moveUp(5);
					}
					redMove.setMoveState(Character.MOVE_UP);
					map.campus.moveUp(0);
				}else if (map.mapState == Map.BUILDING_NUM && 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.building.getLeftMoveState() == 0) {
						map.building.moveRight(5);
					}
					if (map.building.getRightMoveState() == 0) {
						map.building.moveLeft(5);
					}
					if (map.building.getDownMoveState() == 0) {
						map.building.moveUp(5);
					}
					redMove.setMoveState(Character.MOVE_UP);
					map.building.moveUp(0);
				}
				if(redMove.getMeetState() == Character.MEET_NOT)
					redMove.move();
				break;
			case KeyEvent.VK_DOWN:
				wild.walkState=Wild.YES;
				if (map.mapState == Map.ROOM_NUM) {
					if (map.room.getLeftMoveState() == 0) {
						map.room.moveRight(5);
					}
					if (map.room.getRightMoveState() == 0) {
						map.room.moveLeft(5);
					}
					if (map.room.getUpMoveState() == 0) {
						map.room.moveDown(5);
					}
					redMove.setMoveState(Character.MOVE_DOWN);
					map.room.moveDown(0);
				} else if (map.mapState == Map.TOWN_NUM&& 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.town.getLeftMoveState() == 0) {
						map.town.moveRight(5);
					}
					if (map.town.getRightMoveState() == 0) {
						map.town.moveLeft(5);
					}
					if (map.town.getUpMoveState() == 0) {
						map.town.moveDown(5);
					}
					redMove.setMoveState(Character.MOVE_DOWN);
					map.town.moveDown(0);
				} else if (map.mapState == Map.MOHYUN_NUM&& 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.mohyun.getLeftMoveState() == 0) {
						map.mohyun.moveRight(5);
					}
					if (map.mohyun.getRightMoveState() == 0) {
						map.mohyun.moveLeft(5);
					}
					if (map.mohyun.getUpMoveState() == 0) {
						map.mohyun.moveDown(5);
					}
					redMove.setMoveState(Character.MOVE_DOWN);
					map.mohyun.moveDown(0);
				}else if (map.mapState == Map.CAMPUS_NUM&& 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.campus.getLeftMoveState() == 0) {
						map.campus.moveRight(5);
					}
					if (map.campus.getRightMoveState() == 0) {
						map.campus.moveLeft(5);
					}
					if (map.campus.getUpMoveState() == 0) {
						map.campus.moveDown(5);
					}
					redMove.setMoveState(Character.MOVE_DOWN);
					map.campus.moveDown(0);
				}else if (map.mapState == Map.BUILDING_NUM&& 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.building.getLeftMoveState() == 0) {
						map.building.moveRight(5);
					}
					if (map.building.getRightMoveState() == 0) {
						map.building.moveLeft(5);
					}
					if (map.building.getUpMoveState() == 0) {
						map.building.moveDown(5);
					}
					redMove.setMoveState(Character.MOVE_DOWN);
					map.building.moveDown(0);
				}
				if(redMove.getMeetState() == Character.MEET_NOT)
					redMove.move();
				break;
			case KeyEvent.VK_LEFT:
				wild.walkState=Wild.YES;
				if (map.mapState == Map.ROOM_NUM) {
					if (map.room.getDownMoveState() == 0) {
						map.room.moveUp(5);
					}
					if (map.room.getUpMoveState() == 0) {
						map.room.moveDown(5);
					}
					if (map.room.getRightMoveState() == 0) {
						map.room.moveLeft(5);
					}
					redMove.setMoveState(Character.MOVE_LEFT);
					map.room.moveLeft(0);
				} else if (map.mapState == Map.TOWN_NUM&& 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.town.getDownMoveState() == 0) {
						map.town.moveUp(5);
					}
					if (map.town.getUpMoveState() == 0) {
						map.town.moveDown(5);
					}
					if (map.town.getRightMoveState() == 0) {
						map.town.moveLeft(5);
					}
					redMove.setMoveState(Character.MOVE_LEFT);
					map.town.moveLeft(0);
				} else if (map.mapState == Map.MOHYUN_NUM&& 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.mohyun.getDownMoveState() == 0) {
						map.mohyun.moveUp(5);
					}
					if (map.mohyun.getUpMoveState() == 0) {
						map.mohyun.moveDown(5);
					}
					if (map.mohyun.getRightMoveState() == 0) {
						map.mohyun.moveLeft(5);
					}
					redMove.setMoveState(Character.MOVE_LEFT);
					map.mohyun.moveLeft(0);
				}else if (map.mapState == Map.CAMPUS_NUM&& 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.campus.getDownMoveState() == 0) {
						map.campus.moveUp(5);
					}
					if (map.campus.getUpMoveState() == 0) {
						map.campus.moveDown(5);
					}
					if (map.campus.getRightMoveState() == 0) {
						map.campus.moveLeft(5);
					}
					redMove.setMoveState(Character.MOVE_LEFT);
					map.campus.moveLeft(0);
				}else if (map.mapState == Map.BUILDING_NUM&& 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.building.getDownMoveState() == 0) {
						map.building.moveUp(5);
					}
					if (map.building.getUpMoveState() == 0) {
						map.building.moveDown(5);
					}
					if (map.building.getRightMoveState() == 0) {
						map.building.moveLeft(5);
					}
					redMove.setMoveState(Character.MOVE_LEFT);
					map.building.moveLeft(0);
				}
				if(redMove.getMeetState() == Character.MEET_NOT)
					redMove.move();
				break;
			case KeyEvent.VK_RIGHT:
				wild.walkState=Wild.YES;
				if (map.mapState == Map.ROOM_NUM) { 
					if (map.room.getDownMoveState() == 0) {
						map.room.moveUp(5);
					}
					if (map.room.getUpMoveState() == 0) {
						map.room.moveDown(5);
					}
					if (map.room.getLeftMoveState() == 0) {
						map.room.moveRight(5);
					}
					redMove.setMoveState(Character.MOVE_RIGHT);
					map.room.moveRight(0);
				} else if (map.mapState == Map.TOWN_NUM&& 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.town.getDownMoveState() == 0) {
						map.town.moveUp(5);
					}
					if (map.town.getUpMoveState() == 0) {
						map.town.moveDown(5);
					}
					if (map.town.getLeftMoveState() == 0) {
						map.town.moveRight(5);
					}
					redMove.setMoveState(Character.MOVE_RIGHT);
					map.town.moveRight(0);
				} else if (map.mapState == Map.MOHYUN_NUM&& 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.mohyun.getDownMoveState() == 0) {
						map.mohyun.moveUp(5);
					}
					if (map.mohyun.getUpMoveState() == 0) {
						map.mohyun.moveDown(5);
					}
					if (map.mohyun.getLeftMoveState() == 0) {
						map.mohyun.moveRight(5);
					}
					redMove.setMoveState(Character.MOVE_RIGHT);
					map.mohyun.moveRight(0);
				}else if (map.mapState == Map.CAMPUS_NUM&& 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.campus.getDownMoveState() == 0) {
						map.campus.moveUp(5);
					}
					if (map.campus.getUpMoveState() == 0) {
						map.campus.moveDown(5);
					}
					if (map.campus.getLeftMoveState() == 0) {
						map.campus.moveRight(5);
					}
					redMove.setMoveState(Character.MOVE_RIGHT);
					map.campus.moveRight(0);
				}else if (map.mapState == Map.BUILDING_NUM&& 
						redMove.getMeetState() == Character.MEET_NOT) {
					if (map.building.getDownMoveState() == 0) {
						map.building.moveUp(5);
					}
					if (map.building.getUpMoveState() == 0) {
						map.building.moveDown(5);
					}
					if (map.building.getLeftMoveState() == 0) {
						map.building.moveRight(5);
					}
					redMove.setMoveState(Character.MOVE_RIGHT);
					map.building.moveRight(0);
				}
				if(redMove.getMeetState() == Character.MEET_NOT)
					redMove.move();
				break;
			case KeyEvent.VK_ENTER:
				sound.menuOnState= Sound.YES;				
				menu.MenuState = Menu.MAINMENU_ABLE;
				break;
			case KeyEvent.VK_1:
				Pocketmon.f.setSize(Pocketmon.MOHYUN_HALF_W, Pocketmon.MOHYUN_HALF_H);
				break;
			case KeyEvent.VK_2:
				Pocketmon.f.setSize(Pocketmon.FRAME_W, Pocketmon.FRAME_H);
				break;
			case KeyEvent.VK_SPACE:	
				if(menu.MenuState == Menu.MAINMENU_ABLE){
					sound.beepState = Sound.YES;
				}
				if(goldMove.getBoundingBox().makeBoundingBox().intersects(
						redMove.getBoundingBox().makeBoundingBox())
						&& (Math.abs(goldMove.getY() - redMove.getY()) < 24
						|| Math.abs(goldMove.getX() - redMove.getX()) < 24)){
					redMove.setMeetState(Character.MEET);
					redMove.stop();
					if(redMove.getMoveState()==0){
						goldMove.moveState=2;
						goldMove.stop();
						goldMove.state=GoldCharacter.MOVE_UNABLE;
						//バトル突入
						battle.battleState=Battle.BATTLE_IN;
					}else if(redMove.getMoveState()==1){
						goldMove.moveState=3;
						goldMove.stop();
						goldMove.state=GoldCharacter.MOVE_UNABLE;
						//バトル突入
						battle.battleState=Battle.BATTLE_IN;
					}else if(redMove.getMoveState()==2){
						goldMove.moveState=0;
						goldMove.stop();
						goldMove.state=GoldCharacter.MOVE_UNABLE;
						//バトル突入
						battle.battleState=Battle.BATTLE_IN;
					}else if(redMove.getMoveState()==3){
						goldMove.moveState=1;
						goldMove.stop();
						goldMove.state=GoldCharacter.MOVE_UNABLE;
						//バトル突入
						battle.battleState=Battle.BATTLE_IN;
					}
				}
				break;
			}
		}
		//バトルの中でもサブメニュに接近できるようにする。
		else if (battle.inputBlock!=Battle.YES&&menu.MenuState == Menu.MAINMENU_ABLE
				||battle.battleSkillState==Battle.BACK_PACK
				||battle.battleSkillState==Battle.POKEMON) {
			if (menu.mainMenu.subMenuState == Menu.MainMenu.NONE) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					//メニュ選択のポインターを範囲を制限する。
					if (menu.mainMenu.pointerIndex != Menu.MainMenu.POKEMONMENU_POSITION) {
						menu.mainMenu.pointerIndex--;
					}
					break;
				case KeyEvent.VK_DOWN:
					if (menu.mainMenu.pointerIndex != Menu.MainMenu.EXIT_POSITION)
						menu.mainMenu.pointerIndex++;
					break;
				case KeyEvent.VK_SPACE:	
					sound.beepState = Sound.YES;
					if (menu.mainMenu.pointerIndex == Menu.MainMenu.POKEMONMENU_POSITION) { // 포켓몬
						menu.mainMenu.subMenuState = Menu.MainMenu.POKEMONMENU_POSITION;
					} else if (menu.mainMenu.pointerIndex == Menu.MainMenu.BAGMENU_POSITION) {// 가방
						menu.mainMenu.subMenuState = Menu.MainMenu.BAGMENU_POSITION;
					} else if (menu.mainMenu.pointerIndex == Menu.MainMenu.EXIT_POSITION) {
						menu.mainMenu.pointerIndex = Menu.MainMenu.POKEMONMENU_POSITION;
						menu.MenuState = Menu.MENU_DISABLE;
					}
					break;
				case KeyEvent.VK_ENTER: 
					sound.menuOnState = Sound.YES;
					menu.mainMenu.pointerIndex = Menu.MainMenu.POKEMONMENU_POSITION;
					menu.MenuState = Menu.MENU_DISABLE;
					break;
				case KeyEvent.VK_BACK_SPACE: 
					sound.beepState = Sound.YES;
					menu.mainMenu.pointerIndex = Menu.MainMenu.POKEMONMENU_POSITION;
					menu.MenuState = Menu.MENU_DISABLE;
					break;
				}
			} else if (menu.mainMenu.subMenuState == Menu.MainMenu.BAGMENU_POSITION
					||battle.battleSkillState==Battle.BACK_PACK) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					if (menu.backPackMenu.pointerIndex !=0) {
						menu.backPackMenu.pointerIndex--;
					}
					break;								
				case KeyEvent.VK_DOWN:
					if (menu.backPackMenu.pointerIndex <redHave.haveItem.getItemNum())
						menu.backPackMenu.pointerIndex++;
					break;
				case KeyEvent.VK_SPACE:
					sound.beepState = Sound.YES;
					if(menu.backPackMenu.pointerIndex == redHave.haveItem.currentState) {
						if(battle.battleSkillState==Battle.BACK_PACK){
							menu.backPackMenu.pointerIndex = 0;
							menu.mainMenu.subMenuState = Menu.MainMenu.NONE;
							battle.battleSkillState=Battle.NONE;	
							menu.MenuState = Menu.MENU_DISABLE;		
						}else{
							menu.backPackMenu.pointerIndex = 0;
							menu.mainMenu.subMenuState=Menu.MainMenu.NONE;
						}
					}
					else if(menu.backPackMenu.pointerIndex == redHave.item.rareCandy.existState
							&&battle.battleState!=Battle.BATTLE_IN){
						menu.mainMenu.subMenuState=Menu.MainMenu.POKEMONMENU_POSITION;
						menu.itemToPokemon=Menu.YES;
						menu.useItemNum=Item.RARE_CANDY_NUM;
					}
					else if(menu.backPackMenu.pointerIndex == redHave.item.potion.existState
							&&battle.battleState!=Battle.BATTLE_IN){
						menu.mainMenu.subMenuState=Menu.MainMenu.POKEMONMENU_POSITION;
						menu.itemToPokemon=Menu.YES;
						menu.useItemNum=Item.HEALING_POTION_NUM;
					}	
					if(battle.battleSkillState==Battle.BACK_PACK){
						if(menu.backPackMenu.pointerIndex==redHave.item.potion.existState){
								battle.battleSkillState=Battle.POKEMON;
								menu.mainMenu.subMenuState=Menu.MainMenu.POKEMONMENU_POSITION;
								battle.itemToPokemon=Battle.YES;	
								
								battle.itemUsedNumState=Item.HEALING_POTION_NUM;
						}
						//else if() 다른 물약이 존재할때 여기 추가
						//
						//
						//
						//
					}			
					break;
				case KeyEvent.VK_BACK_SPACE: //EXIT
					//バトル
					sound.beepState= Sound.YES;
					if(battle.battleSkillState==Battle.BACK_PACK){
						menu.backPackMenu.pointerIndex = 0;
						menu.mainMenu.subMenuState = Menu.MainMenu.NONE;
						battle.battleSkillState=Battle.NONE;	
						menu.MenuState = Menu.MENU_DISABLE;				
					}else{
					//一般
						menu.backPackMenu.pointerIndex = 0;
						menu.mainMenu.subMenuState = Menu.MainMenu.NONE;
					}
					break;					
				//コンソールキー
				case KeyEvent.VK_1:
					redHave.haveItem.increaseHealingPotionNum();
					break;
				case KeyEvent.VK_2:
					redHave.haveItem.increaseMonsterBall();
					break;
				case KeyEvent.VK_3:
					redHave.haveItem.increaseRareCandyNum();
					break;
				case KeyEvent.VK_4:
					redHave.haveItem.decreaseHealingPotionNum();
					break;
				case KeyEvent.VK_5:
					redHave.haveItem.decreaseMonsterBall();
					break;
				case KeyEvent.VK_6:
					redHave.haveItem.decreaseRareCandy();
					break;					
				} 
			} else if(menu.mainMenu.subMenuState == Menu.MainMenu.POKEMONMENU_POSITION
					||battle.battleSkillState==Battle.POKEMON||menu.itemToPokemon==Menu.YES){
				switch(e.getKeyCode()){
				case KeyEvent.VK_UP:
					if(menu.pokemonMenu.pointerIndex !=0){
						menu.pokemonMenu.pointerIndex--;
					}
					break;
				case KeyEvent.VK_DOWN:
					if(menu.pokemonMenu.pointerIndex < redHave.havePokemon.getPokemonNum()){
						menu.pokemonMenu.pointerIndex++;
					}
					break;
				case KeyEvent.VK_SPACE:
					sound.beepState = Sound.YES;
					if(menu.pokemonMenu.pointerIndex == redHave.havePokemon.getPokemonNum()){
						if(battle.battleSkillState==Battle.POKEMON&&battle.itemToPokemon==Battle.NO){
							menu.pokemonMenu.pointerIndex = 0;
							menu.mainMenu.subMenuState = Menu.MainMenu.NONE;
							battle.battleSkillState=Battle.NONE;	
							menu.MenuState = Menu.MENU_DISABLE;
						}else if(battle.battleSkillState==Battle.POKEMON&&battle.itemToPokemon==Battle.YES){							
							menu.pokemonMenu.pointerIndex = 0;
							menu.mainMenu.subMenuState = Menu.MainMenu.BAGMENU_POSITION;
							battle.battleSkillState=Battle.BACK_PACK;	
							battle.itemToPokemon=Battle.NO;
						}else if(battle.PlayerDoneState==Battle.ENEMY_FILL1){
						}else{
							menu.pokemonMenu.pointerIndex = 0;
							menu.mainMenu.subMenuState = Menu.MainMenu.NONE;
						}
					}else if(battle.battleSkillState==Battle.POKEMON&&battle.itemToPokemon==Battle.YES){//만약 그만두기가 아닐때						
						if(redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(menu.pokemonMenu.pointerIndex))
								>0&&redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(menu.pokemonMenu.pointerIndex))
								!=redHave.pokemon.getHP(redHave.havePokemon.pokemonList.get(menu.pokemonMenu.pointerIndex))){							
							redHave.haveItem.decreaseHealingPotionNum();
							//만약 사용된 것이 50힐링 포션이라면
							if(battle.itemUsedNumState==Item.HEALING_POTION_NUM){
								redHave.pokemon.healThePokemon( 
										redHave.havePokemon.pokemonList.get(
												menu.pokemonMenu.pointerIndex),Item.HEALING_POTION_NUM
												);
							}							
							// 포션으로 회복 시킬때 배틀인 경우에는 AI의 턴이 되므로 먼저 AI의 선택을 받아 놓는다.
							if(battle.wildPokemonMeetState==Battle.NONE){
								ai.setAiState(ai.enemyDo());			
							}else if(battle.wildPokemonMeetState==Battle.YES){
								ai.setAiState(ai.wildEnemyDo());
							}
							battle.PlayerDoneState=Battle.MY_ITEM_PRI;							
						}					
					}
					else if(menu.itemToPokemon==Menu.YES&&battle.battleState!=Battle.BATTLE_IN){
						if(menu.useItemNum == Item.HEALING_POTION_NUM){
							if(redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(menu.pokemonMenu.pointerIndex))
									>0&&redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(menu.pokemonMenu.pointerIndex))
									!=redHave.pokemon.getHP(redHave.havePokemon.pokemonList.get(menu.pokemonMenu.pointerIndex))){							
								redHave.haveItem.decreaseHealingPotionNum();
								if(menu.useItemNum==Item.HEALING_POTION_NUM){
									redHave.pokemon.healThePokemon( 
											redHave.havePokemon.pokemonList.get(
													menu.pokemonMenu.pointerIndex),Item.HEALING_POTION_NUM
													);
								}								
							}	
							menu.itemToPokemon=Menu.DO;
						}
						if(menu.useItemNum == Item.RARE_CANDY_NUM){
							if(redHave.pokemon.getCurrentHP(redHave.havePokemon.pokemonList.get(menu.pokemonMenu.pointerIndex))
									>0){							
								redHave.haveItem.decreaseRareCandy();
								if(menu.useItemNum==Item.RARE_CANDY_NUM){								
									redHave.pokemon.increaseLevel(redHave.havePokemon.pokemonList.get(menu.pokemonMenu.pointerIndex));
								}												
							}	
							menu.itemToPokemon=Menu.DO;							
						}
					}
					else{			
						if(redHave.pokemon.getCurrentHP(
								redHave.havePokemon.pokemonList.get(
										menu.pokemonMenu.pointerIndex))>0&&menu.pokemonMenu.pointerIndex!=0){
							if(battle.PlayerDoneState!=Battle.MY_FILL1){
								if(battle.wildPokemonMeetState==Battle.NONE){
									ai.setAiState(ai.enemyDo());					
								}else if(battle.wildPokemonMeetState ==Battle.YES){
									ai.setAiState(ai.wildEnemyDo());
								}
								battle.PlayerDoneState=Battle.MY_CHANGE1_PRI;
							}else{								
								battle.PlayerDoneState=Battle.MY_CHANGE3_PRI;
							}
						}
					}
					break;
				case KeyEvent.VK_BACK_SPACE:
					sound.beepState=Sound.YES;
					if(battle.battleSkillState==Battle.POKEMON){
						menu.pokemonMenu.pointerIndex = 0;
						menu.mainMenu.subMenuState = Menu.MainMenu.NONE;
						battle.battleSkillState=Battle.NONE;	
						menu.MenuState = Menu.MENU_DISABLE;	
						menu.itemToPokemon=Menu.NONE;
						if(battle.itemToPokemon==Battle.YES){//만약 백팩으로부터 포켓몬 메뉴로 넘어간 것이라면							
							battle.itemToPokemon=Battle.NO;
							menu.MenuState = Menu.MAINMENU_ABLE;
							battle.battleSkillState=Battle.BACK_PACK;
							menu.mainMenu.subMenuState = Menu.MainMenu.BAGMENU_POSITION;							
						}
					}else if(battle.PlayerDoneState==Battle.ENEMY_FILL1){
						//만약에 내 포켓몬이 죽었다면 포켓몬을 고르기 전까지 나가지 않게 한다
					}else{
						menu.pokemonMenu.pointerIndex = 0;
						menu.mainMenu.subMenuState = Menu.MainMenu.NONE;
						menu.itemToPokemon=Menu.NONE;
					}
					break;
				case KeyEvent.VK_S://swap
					Collections.swap(redHave.havePokemon.pokemonList, 0, menu.pokemonMenu.pointerIndex);
					break;		
				case KeyEvent.VK_LEFT:
					
//					redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(menu.pokemonMenu.pointerIndex)-1).setCurrentHp(
//							redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(menu.pokemonMenu.pointerIndex)-1).getCurrentHp()-1);
					break;	
				case KeyEvent.VK_RIGHT:
//					redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(menu.pokemonMenu.pointerIndex)-1).setCurrentHp(
//					redHave.pokemon.getPokemonKindArray().get(redHave.havePokemon.pokemonList.get(menu.pokemonMenu.pointerIndex)-1).getCurrentHp()+1);
					break;					
				}
				
			}
			
		}else if(battle.inputBlock!=Battle.YES&&battle.battleState==Battle.BATTLE_IN&&battle.inputBlock==Battle.NONE){
			//만약 배틀 처음 진입 중이라면 처음 배틀 코멘트를 먼저 출력해준다
			if(battle.startCommentState!=Battle.NONE&&battle.battleLoopState==Battle.NONE){		
				switch(e.getKeyCode()){
				case KeyEvent.VK_SPACE:
					sound.beepState = Sound.YES;
					if(battle.startCommentState==Battle.INI_3&&
					battle.wildPokemonMeetState==Battle.YES){
						//만약 야생 포켓몬과의 조우일때는 ini 가 3까지만 작동하고 command_select로 넘어간다
						battle.battleLoopState=Battle.COMMAND_SELECT;					
						break;
					}
					
					if(battle.startCommentState!=Battle.INI_4)
						battle.startCommentState++;
					else if(battle.startCommentState==Battle.INI_4){
						battle.battleLoopState=Battle.COMMAND_SELECT;
					}
					break;
				}
			}else if(battle.battleLoopState==Battle.COMMAND_SELECT){
				//정식 배틀 루프
				if(battle.battleSkillState==Battle.NONE&&battle.enemyPriority==Battle.NONE){
					switch(e.getKeyCode()){
					case KeyEvent.VK_UP:
						if(battle.commandSelectPointer==2){		
							battle.commandSelectPointer=0;
						}else if(battle.commandSelectPointer==3){
							battle.commandSelectPointer=1;
						}
						break;
					case KeyEvent.VK_DOWN:
						if(battle.commandSelectPointer==0){
							battle.commandSelectPointer=2;
						}else if(battle.commandSelectPointer==1){
							battle.commandSelectPointer=3;						
						}
						break;
					case KeyEvent.VK_LEFT:
						if(battle.commandSelectPointer==1){
							battle.commandSelectPointer=0;
						}else if(battle.commandSelectPointer==3){
							battle.commandSelectPointer=2;
						}
						break;
					case KeyEvent.VK_RIGHT:
						if(battle.commandSelectPointer==0){
							battle.commandSelectPointer=1;
						}else if(battle.commandSelectPointer==2){
							battle.commandSelectPointer=3;
						}
						break;
					case KeyEvent.VK_SPACE:
						sound.beepState = Sound.YES;
						//battleskillState를 사용하여 commandselect에서 선택하는 것의
						//state 4개를 모두 담당하게 한다.
						if(battle.commandSelectPointer==Battle.FIGHT){//싸우다 일때
							battle.battleSkillState=Battle.SKILL_SELECT;
						}else if(battle.commandSelectPointer==Battle.BACK_PACK){//가방일때
							battle.battleSkillState=Battle.BACK_PACK;
							menu.mainMenu.subMenuState=Menu.MainMenu.BAGMENU_POSITION;
							menu.MenuState = Menu.MAINMENU_ABLE;
							//서브 메뉴의 백팩 메뉴로 가게 된다.
						}else if(battle.commandSelectPointer==Battle.POKEMON){//가방일때
							battle.battleSkillState=Battle.POKEMON;
							menu.mainMenu.subMenuState=Menu.MainMenu.POKEMONMENU_POSITION;
							menu.MenuState = Menu.MAINMENU_ABLE;
							//서브 메뉴의 백팩 메뉴로 가게 된다.							
						}else if(battle.commandSelectPointer==Battle.RUN){
							//만약 골드(npc)와의 만남이라면
							if(goldMove.state==GoldCharacter.MOVE_UNABLE){
								battle.battleSkillState=Battle.RUN;
								battle.runState=Battle.CANT_RUN_1;
							}else{
								//여기는 야생포켓몬의 만남
								battle.battleSkillState=Battle.RUN;															
							}
						}
						break;
					}
				}else if(battle.battleSkillState==Battle.SKILL_SELECT){//싸우다 state
					switch(e.getKeyCode()){
					case KeyEvent.VK_UP:
						if(battle.skillSelectPointer!=0){		
							battle.skillSelectPointer--;
						}
						break;
					case KeyEvent.VK_DOWN:
						if(redHave.pokemon.getSkillList(
								redHave.havePokemon.pokemonList.get(
										battle.startStillAliveIndex)).size()-1
										!= battle.skillSelectPointer){
							battle.skillSelectPointer++;
						}
						break;	
					case KeyEvent.VK_SPACE:	//스킬 클릭
						sound.beepState = Sound.YES;
						if(battle.wildPokemonMeetState==Battle.NONE){
							ai.setAiState(ai.enemyDo());
						}else if(battle.wildPokemonMeetState==Battle.YES){
							ai.setAiState(ai.wildEnemyDo());
						}
						//7 이상부터 스킬 이기때문에 상대방도 공격이라 간주한다.
						if(ai.getAiState()>6){
							//만약 goldHave의 포켓몬보다 내 포켓몬이 빠르다면(speed 비교)
							if(battle.wildPokemonMeetState==Battle.NONE){
								if(redHave.pokemon.getSpeed(
										redHave.havePokemon.pokemonList.get(battle.myPokemonIndex
												))
												>
								goldHave.pokemon.getSpeed(
										goldHave.havePokemon.pokemonList.get(battle.enemyPokemonIndex         
												))){
									battle.PlayerDoneState=Battle.MY_ATTACK_PRI;	
									battle.enemyPriority=Battle.MY_TURN;	//이 것때문에 내가 더 빨리 때린 것을 알수 있다.
									// 	만약 속도가 같다면 확률적으로 선공을 정해 준다.
								}else if(redHave.pokemon.getSpeed(
										redHave.havePokemon.pokemonList.get(battle.myPokemonIndex
												))
												==
												goldHave.pokemon.getSpeed(
														goldHave.havePokemon.pokemonList.get(battle.enemyPokemonIndex         
																))){
									if(Util.prob100(50)){
										battle.PlayerDoneState=Battle.MY_ATTACK_PRI;	
										battle.enemyPriority=Battle.MY_TURN;
									}else{
										battle.PlayerDoneState=Battle.ENEMY_TURN;
										battle.enemyPriority=Battle.ENEMY_ATTACK_PRI;
									}
								}else{ //내가 느리다면
									//	에너미 턴으로 설정
									battle.PlayerDoneState=Battle.ENEMY_TURN;	//이 state 때문에 적이 먼저 때린것을 앐수 있다.
									battle.enemyPriority=Battle.ENEMY_ATTACK_PRI;
								}
							}else{
								if(redHave.pokemon.getSpeed(
										redHave.havePokemon.pokemonList.get(battle.myPokemonIndex
												))
												>
								wild.pokemon.getSpeed(
										wild.wildPokemonList.get(wild.randIndex         
												))){
									battle.PlayerDoneState=Battle.MY_ATTACK_PRI;	
									battle.enemyPriority=Battle.MY_TURN;	//이 것때문에 내가 더 빨리 때린 것을 알수 있다.
									// 	만약 속도가 같다면 확률적으로 선공을 정해 준다.
								}else if(redHave.pokemon.getSpeed(
										redHave.havePokemon.pokemonList.get(battle.myPokemonIndex
												))
												==
												wild.pokemon.getSpeed(
														wild.wildPokemonList.get(wild.randIndex         
																))){
									if(Util.prob100(50)){
										battle.PlayerDoneState=Battle.MY_ATTACK_PRI;	
										battle.enemyPriority=Battle.MY_TURN;
									}else{
										battle.PlayerDoneState=Battle.ENEMY_TURN;
										battle.enemyPriority=Battle.ENEMY_ATTACK_PRI;
									}
								}else{ //내가 느리다면
									//	에너미 턴으로 설정
									battle.PlayerDoneState=Battle.ENEMY_TURN;	//이 state 때문에 적이 먼저 때린것을 앐수 있다.
									battle.enemyPriority=Battle.ENEMY_ATTACK_PRI;
								}
							}
						}else{//그 반대라면 항상 포켓몬이 먼저이므로 앞서 했던 것 같은 알고리즘을 똑같이 적용한다.							
							battle.PlayerDoneState=Battle.ENEMY_TURN;	//적이 먼저 행동 함을 나타낸다.
							if(ai.getAiState()==AI.HEALING){//적이 healing일때								
								battle.enemyPriority=Battle.ENEMY_ITEM1_PRI;
							}
							else{//적이 포켓몬 교체 일때
								battle.enemyPriority=Battle.ENEMY_CHANGE1_PRI;
							}
						}
						break;
					case KeyEvent.VK_BACK_SPACE:
						sound.beepState = Sound.YES;
						battle.battleSkillState=Battle.NONE;
						battle.skillSelectPointer=0;
						break;
					}	
				}else if(battle.battleSkillState==Battle.RUN){//도망 state 일때
					switch(e.getKeyCode()){					
					case KeyEvent.VK_SPACE:
						sound.beepState = Sound.YES;
						if(battle.runState==Battle.CANT_RUN_1){
							battle.runState=Battle.CANT_RUN_2;
						}else if(battle.runState==Battle.CANT_RUN_2){
							battle.runState=Battle.NONE;
							battle.battleSkillState=Battle.NONE;
						}						
						break;
					}
				}
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		redMove.stop();
	}
}
