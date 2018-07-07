package Information;


import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.management.BadBinaryOpValueExpException;

public class RedHave {
	private static RedHave redHaveInstance;
	// private ArrayList<Item> itemCurrent;

	public Item item;
	public Pokemon pokemon;
	public HaveItem haveItem;
	public HavePokemon havePokemon;

	private RedHave() throws IOException, FontFormatException {
		haveItem = new HaveItem();
		item = new Item();
		pokemon = new Pokemon();
		havePokemon = new HavePokemon();
	}
	
	public static RedHave getInstance(){
		if(redHaveInstance==null){
			try{
				redHaveInstance=new RedHave();
			}catch(IOException e1){
				e1.printStackTrace();
			}catch(FontFormatException e2){
				e2.printStackTrace();
			}
		}
		return redHaveInstance;
	}

	public class HaveItem {
		// itemSequenceState 자체로 가방 내부의 나타내는 순서를 정한다
		// public int[] itemSequenceState; // 최대 아이템 갯수만큼 배열을 지정한다.

		public int currentState;
		public ArrayList<Integer> sequenceNum;
		private int itemNum;

		public HaveItem() {
			currentState = 0;
			itemNum = 0; // 현재 가방에 존재하는 아이템 갯수
			sequenceNum = new ArrayList<Integer>();
		}

		// 아래의 메소드는 아이템들이 사라지거나 생성될때의 배열 순서 변화를 나타낸다
		// 그러므로 사용될때, 상점에서 구매할때 순서가 바뀌게 된다.
		public void setSequence(int state) {
			// 만약 아래 메소드중 healing 포션에 관계된 메소드에 의한 콜이라면 진입
			if (state == Item.HEALING_POTION_NUM) {
				if (item.potion.number == 1 && item.potion.changeState == 1) {
					sequenceNum.add(Item.HEALING_POTION_NUM);
					item.potion.existState = currentState;
					currentState++; // stackpoint 같이 행동한다.
				} else if (item.potion.number == 0) {
					sequenceNum.remove(item.potion.existState);
					// 다른 아이템의 existState도 한개씩 줄여야 한다.(존재할때만)
					//---- 나중에 아이템 갯수가 추가 될때 사이에 있는 아이템이 삭제되는 경우도 고려해야함 (크기 비교)---- 
					if(item.ballNumState==1&&item.potion.existState < item.ball.existState)
						item.ball.existState--;
					if(item.rareCandyNumState==1&&item.potion.existState < item.rareCandy.existState)
						item.rareCandy.existState--;
					currentState--;
					item.potion.existState = -1;
				}
				// 만약 아래 메소드중 몬스터 볼에 관계된 메소드에 의한 콜이라면 진입
			} else if (state == Item.MONSTER_BALL_NUM) {
				if (item.ball.number == 1 && item.ball.changeState == 1) {
					sequenceNum.add(Item.MONSTER_BALL_NUM);
					item.ball.existState = currentState;
					currentState++; // stackpoint 같이 행동한다.
				} else if (item.ball.number == 0) {
					sequenceNum.remove(item.ball.existState);
					// 다른 아이템의 existState도 한개씩 줄여야 한다.(존재할때만)
					//---- 나중에 아이템 갯수가 추가 될때 사이에 있는 아이템이 삭제되는 경우도 고려해야함 (크기 비교)---- 
					if(item.potionNumState==1&&item.ball.existState < item.potion.existState)
						item.potion.existState--;	
					if(item.rareCandyNumState==1&&item.ball.existState < item.rareCandy.existState)
						item.rareCandy.existState--;	
					currentState--;
					item.ball.existState = -1;
				}
			} else if (state == Item.RARE_CANDY_NUM) {
				if (item.rareCandy.number == 1 && item.rareCandy.changeState == 1) {
					sequenceNum.add(Item.RARE_CANDY_NUM);
					item.rareCandy.existState = currentState;
					currentState++; // stackpoint 같이 행동한다.
				} else if (item.rareCandy.number == 0) {
					sequenceNum.remove(item.rareCandy.existState);
					// 다른 아이템의 existState도 한개씩 줄여야 한다.(존재할때만)
					//---- 나중에 아이템 갯수가 추가 될때 사이에 있는 아이템이 삭제되는 경우도 고려해야함 (크기 비교)---- 
					if(item.potionNumState==1&&item.rareCandy.existState < item.potion.existState)
						item.potion.existState--;
					if(item.ballNumState==1&&item.rareCandy.existState < item.ball.existState)
						item.ball.existState--;						
					currentState--;
					item.rareCandy.existState = -1;
				}
			}
		}

		public ArrayList<Integer> getItemPosition() {
			return sequenceNum;
		}

		public void increaseHealingPotionNum() {
			// 최초 아이템 이 존재하지 않으면 증가시킬때 state를 존재함으로 만든다.
			if (item.potion.number == 0) 
				item.potionNumState = 1; //아이템이 존재하는가 여부
			item.potion.changeState = 1; // 증가
			item.potion.number++;
			setSequence(Item.HEALING_POTION_NUM);
		}
		public void decreaseHealingPotionNum() {
			if (item.potion.number != 0) { //아이템이 존재하는가 여부
				item.potion.number--;
				if (item.potion.number == 0)
					item.potionNumState = 0;
				item.potion.changeState = 0; // 감소
				setSequence(Item.HEALING_POTION_NUM);
			}
		}
		public void increaseRareCandyNum() {
			// 최초 아이템 이 존재하지 않으면 증가시킬때 state를 존재함으로 만든다.
			if (item.rareCandy.number == 0) 
				item.rareCandyNumState = 1; //아이템이 존재하는가 여부
			item.rareCandy.changeState = 1; // 증가
			item.rareCandy.number++;
			setSequence(Item.RARE_CANDY_NUM);
		}
		public void decreaseRareCandy() {
			if (item.rareCandy.number != 0) { //아이템이 존재하는가 여부
				item.rareCandy.number--;
				if (item.rareCandy.number == 0)
					item.rareCandyNumState = 0;
				item.rareCandy.changeState = 0; // 감소
				setSequence(Item.RARE_CANDY_NUM);
			}
		}
		public void increaseMonsterBall() {
			if (item.ball.number == 0)	
				item.ballNumState = 1; //아이템이 존재하는가 여부
			item.ball.changeState = 1; // 증가
			item.ball.number++;
			setSequence(Item.MONSTER_BALL_NUM);
		}
		public void decreaseMonsterBall() {
			if (item.ball.number != 0) { 
				item.ball.number--;
				if (item.ball.number == 0)
					item.ballNumState = 0;
				item.ball.changeState = 0; // 감소
				setSequence(Item.MONSTER_BALL_NUM);
			}
		}
		public int getAllHealingPotionNum() {
			return item.potion.number;
		}

		public int getAllMonsterBallNum() {
			return item.ball.number;
		}

		// 단순한 존재 아이템 갯수를 받는다.
		public int getItemNum() {
			itemNum = item.ballNumState + item.potionNumState + item.rareCandyNumState;
			return itemNum;
		}
	}

	public class HavePokemon {
		public static final int POKEMON_MAX_NUM=6;
		//public int CurrenState;

		public ArrayList<Integer> pokemonList;	//포켓몬 보유
		
		public HavePokemon() {
			pokemonList = new ArrayList<Integer>();
			setFirstPokemonList();
			//setSequence();
		}
		public int getPokemonNum(){
			return pokemonList.size();
		}
		//초기에 주인공이 가질 포켓몬을 설정한다.
		private void setFirstPokemonList(){
			//아이템과 다르게 포켓몬 같은 경우는 없어지는 경우가 없기때문에 포켓몬 객체 자체에
			//indexState를 보유한다.(swap 할때 훨씬 편리 할듯)
			pokemonList.add(Pokemon.BAYLEAF_NUM);
			pokemon.getPokemonKindArray().get(Pokemon.BAYLEAF_NUM-1).setLevel(31);
			pokemonList.add(Pokemon.BLAY_NUM);
			pokemon.getPokemonKindArray().get(Pokemon.BLAY_NUM-1).setLevel(40);
			pokemonList.add(Pokemon.ZANGCRO_NUM);			
			pokemon.getPokemonKindArray().get(Pokemon.ZANGCRO_NUM-1).setLevel(40);
		}

		public void recoveryHp(){
			for(int i=0;i<pokemonList.size();i++){
				pokemon.setCurrentHp(
						pokemonList.get(i),pokemon.getHP(pokemonList.get(i)) );
			}
		}
		//add 할때 sequence 도 같이 설정 해 준다.
		//문제 발견(한가지 트레이너가 같은 포켓몬 은 한종류 밖에 가질수 없음->add 는 가능하나 어짜피
		//포켓몬 내부 변수는 공유하기때문에 문제가 됨-> Pokemon 객체로 내부 클래스를 묶
		//은 결과 -> 일단 중복 포켓몬 무시 후 이대로 진행 하기로 결정)
		//반환 값을 통해 1이면 포켓몬 잡을때 거부 메시지가 뜨도록 하고 0이면 잡도록 함
		public int addPokemon(int pokemonNum){
			if(pokemonList.size() != POKEMON_MAX_NUM){
				for(int i=0;i<pokemonList.size();i++){
					if(pokemonList.get(i)==pokemonNum)
						return 1;
				}
				pokemonList.add(pokemonNum);		
			}
			return 0;
		}
		//아래도 가방의 포켓몬 위치 시퀀스를 조절해준다(라인을 하나의 객체로 보고 움직인다.)		
	}
	
	public void evolution(int pokemonNum, int evolBagPoint){
		havePokemon.pokemonList.add(pokemon.getPokemonKindArray().get(pokemonNum-1).getEvolPokemonNum());
		Collections.swap(havePokemon.pokemonList, havePokemon.pokemonList.size()-1, evolBagPoint);
		havePokemon.pokemonList.remove(havePokemon.pokemonList.size()-1);
	}
	
}
