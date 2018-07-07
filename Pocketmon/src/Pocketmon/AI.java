package Pocketmon;


import Information.GoldHave;
import Information.RedHave;
import Information.Skill;
import Information.Type;
import Information.Util;

public class AI {
	//Singleton Pattern
	private static AI aiInstance;
	
	public static int NONE =-1;
	public static int YES =1;
	//交代インデクス
	public static int POKEMON_1=0;
	public static int POKEMON_2=1;
	public static int POKEMON_3=2;
	public static int POKEMON_4=3;
	public static int POKEMON_5=4;
	public static int POKEMON_6=5;
	//HP 回復 (この時は回復薬が残っていることが判明されている状態)
	public static int HEALING = 6;
	
	public static int SKILL_1= 7;
	public static int SKILL_2= 8;
	public static int SKILL_3= 9;
	public static int SKILL_4= 10;
	
	
	private int aiState;
	private Battle battle;
	private RedHave redHave;
	private GoldHave goldHave;
	private Wild wild;
	
	private AI() {
		aiState=NONE;
	}
	
	public void InitObject(RedHave redHave,GoldHave goldHave,Battle battle,Wild wild){
		this.redHave= redHave;
		this.goldHave= goldHave;
		this.battle=battle;
		this.wild=wild;
	}
	
	public static AI getInstance(){
		if(aiInstance== null){
			aiInstance= new AI();			
		}
		return aiInstance;
	}
	
	public int wildEnemyDo(){
		int ret=NONE;
		
		while(ret==-1){
			for(int i=0;i<wild.pokemon.getSkillNum(
					wild.wildPokemonList.get(
							wild.randIndex));i++){
				//強い技術がもっと高い優先度を持つ。
				if(wild.pokemon.getSkillList(
						wild.wildPokemonList.get(
								wild.randIndex)).get(i)==Skill.SUNEAT_NUM){		
					if(Util.prob100(1)){
						ret=i+7;
					}
				}else if(Util.prob100(i*i)){
					ret=i+7;
				}
			}				
		}		
		return ret;
	}

	//1．交代をするかどうか決定(npc自身のポケモンのタイプとキャラクターの出ているポケモンのタイプを比較して不利な場合のみ
	//交代可能か不可能かを判別して可能なポケモンがいた場合に交代いなかった場合に二番に移行)
	//2．アイテムを使うかどうか決定
	//3．それとも攻撃
	//キャラクター側の行動に従って判断する(stateの値で判別)
	//return valueでnpcの行動を決める
	public int enemyDo(){
		//現在フィールドに出ているキャラクターのポケモンのタイプを確認
		Type playerType=redHave.pokemon.getSelfType(
				redHave.havePokemon.pokemonList.get(
				battle.startStillAliveIndex));
		int ret=NONE;
		int advantageRateState=NONE;
		//getAttackRate methodのリターン値の倍率が0.5ならnpc側のポケモンが不利なので交代モードに入る
		if(goldHave.pokemon.useTypeMethod.getAttackRate(
				goldHave.pokemon.getSelfType(
				goldHave.havePokemon.pokemonList.get(
				battle.enemyPokemonIndex)),playerType) ==0.5){
			//リストの中に生きていてタイプが有利なポケモンがある場合に交代
			for(int i=0;i<goldHave.havePokemon.pokemonList.size();i++){
				
				//まずぽけもんのhpが残っているか確認
				if(goldHave.pokemon.getCurrentHP(
						goldHave.havePokemon.pokemonList.get(i))>0){
					//そのあとに有利なポケモンを判別。
					
					//有利
					if(goldHave.pokemon.useTypeMethod.getAttackRate(goldHave.pokemon.getSelfType(
							goldHave.havePokemon.pokemonList.get(i)),playerType)==2.0){
						ret=i;
						advantageRateState=YES;
					}
					//同党
					if(goldHave.pokemon.useTypeMethod.getAttackRate(goldHave.pokemon.getSelfType(
							goldHave.havePokemon.pokemonList.get(i)),playerType)==1.0){
						if(advantageRateState!=YES){							
							ret=i;							
						}
					}					
				}
			}
			//現在 hp < 最大 hp * 25/100
			//ポケモンのHPが25%以下ならアイテム有無を確認
		}else if(goldHave.pokemon.getCurrentHP(
				goldHave.havePokemon.pokemonList.get(
						battle.enemyPokemonIndex)) 
						<		
								goldHave.pokemon.getHP(
										goldHave.havePokemon.pokemonList.get(
												battle.enemyPokemonIndex)) 
														*25/100){
			//アイテムが存在する時 
			if(goldHave.item.potion.number!=0){
				ret=HEALING;
			}
			//1と2ができない場合攻撃に移る
			//攻撃するときに有利なスキルを探して攻撃
		}
		
		while(ret==-1){
			for(int i=0;i<goldHave.pokemon.getSkillNum(
					goldHave.havePokemon.pokemonList.get(
							battle.enemyPokemonIndex));i++){
				//同じタイプのスキルが一つではないときには確率を強い方を優先してランダムに選択
				if(goldHave.pokemon.getSkillList(
						goldHave.havePokemon.pokemonList.get(
								battle.enemyPokemonIndex)).get(i)==Skill.SUNEAT_NUM){		
					if(Util.prob100(1)){
						ret=i+7;
					}
				}else if(Util.prob100(i*i)){
					ret=i+7;
				}
			}				
		}		
		return ret;
	}
	//敵のポケモンが気絶した場合
	//1．ポケモンの中にキャラクターのポケモンより有利なものがあるかどうか
	//2．あるなら有利なポケモンの値をリターン
	//3. ないなら同等なポケモンがあるかどうか
	//4. あるなら同等なポケモンの値をリターン
	//5. ないなら残りのどれかをリターン
	public int enemyDeadChange(){	
		int ret =NONE;
		int advantageRateState =NONE;
		int normalRateState =NONE;
		//現在出ているキャラクターのポケモンのタイプをリターン
		Type playerType=redHave.pokemon.getSelfType(
				redHave.havePokemon.pokemonList.get(
				battle.startStillAliveIndex));
		
		//リストの中に生きていてタイプが有利なポケモンがある場合に交代
		for(int i=0;i<goldHave.havePokemon.pokemonList.size();i++){
			//まずぽけもんのhpが残っているか確認
			if(goldHave.pokemon.getCurrentHP(
					goldHave.havePokemon.pokemonList.get(i))>0){
				//そのあとに有利なポケモンを判別。			
				if(goldHave.pokemon.useTypeMethod.getAttackRate(goldHave.pokemon.getSelfType(
						goldHave.havePokemon.pokemonList.get(i)),playerType)==2.0){
					ret=i;
					advantageRateState=YES;
				}
				if(goldHave.pokemon.useTypeMethod.getAttackRate(goldHave.pokemon.getSelfType(
						goldHave.havePokemon.pokemonList.get(i)),playerType)==1.0){
					if(advantageRateState!=YES){							
						ret=i;		
						normalRateState=YES;
					}
				}
				//何も選ばれてない場合
				if(advantageRateState==NONE&&normalRateState==NONE){
					ret = i;
				}
			}			
		}
		return ret;
	}
	
	public int getAiState() {
		return aiState;
	}

	public void setAiState(int aiState) {
		this.aiState = aiState;
	}
	
}











