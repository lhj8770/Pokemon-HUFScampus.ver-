package Information;

import java.awt.FontFormatException;
import java.io.IOException;
import java.util.ArrayList;
//singleton 禁止
public class GoldHave {
	public static int FIRST_POTION_NUM=5;

	public Item item;
	public Pokemon pokemon;
	public HaveItem haveItem;
	public HavePokemon	havePokemon;

	public GoldHave() throws IOException, FontFormatException {
		haveItem = new HaveItem();
		item = new Item();
		pokemon = new Pokemon();
		havePokemon = new HavePokemon();
		haveItem.setFirstHealingPotionNum(2);
	}
	
	public class HaveItem {
		// itemSequenceStateでバックの中身の順番が決まる

		public int currentState;

		public HaveItem() {				
		}		
		public void increaseHealingPotionNum() {
			item.potion.number++;
		}
		public void decreaseHealingPotionNum() {
			if (item.potion.number != 0) { //アイテムの存在の判明
				item.potion.number--;
			}
		}
		public void setFirstHealingPotionNum(int num){
			item.potion.number=num;
		}
		
		public int getHealingPotionNum() {
			return item.potion.number;
		}
	}
	public class HavePokemon {
		public static final int POKEMON_MAX_NUM=6;

		public ArrayList<Integer> pokemonList;	//持っているポケモンのナンバーarraylist
		
		public HavePokemon() {
			pokemonList = new ArrayList<Integer>();
			setFirstPokemonList();
			//setSequence();
		}
		public int getPokemonNum(){
			return pokemonList.size();
		}
		//初期にnpcが持つ最初のポケモンを決める
		private void setFirstPokemonList(){
			pokemonList.add(Pokemon.SANGFLOWER_NUM);			
			pokemonList.add(Pokemon.ZAMONG_NUM);			
			pokemonList.add(Pokemon.GGOBUKING_NUM);
		}
		public void recoveryHp(){
			for(int i=0;i<pokemonList.size();i++){
				pokemon.setCurrentHp(
						pokemonList.get(i),pokemon.getHP(pokemonList.get(i)) );
			}
		}
	}
}
