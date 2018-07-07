package Pocketmon;

import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Information.Pokemon;

public class Wild {
	public static int YES = 1;
	public static int NONE = -1;
	
	//야생포켓몬 랜덤 인덱스
	public int randIndex; 
	public int wildPickedState;
	
	public int xMoveSpeed; //초기 화면 캐릭터 움직임 속도
	public int startXMoveSpeed;
	public int gainItemState;
	public PocketmonComponent pocketmon;
	public Pokemon pokemon;
	public BufferedImage appear;
	public BufferedImage at;
	public BufferedImage run;
	public BufferedImage wilds;	
	public int delay;
	//주인공이 걸을때만 확률이 발동한다.
	public int walkState;
	
	public int firstWildPokemonFrontX;
	public ArrayList<Integer> wildPokemonList; 
	public Wild(PocketmonComponent pocketmon) throws IOException, FontFormatException {
		this.pocketmon = pocketmon;
		gainItemState=NONE;
		walkState=NONE;
		wildPickedState=NONE;
		delay =0;		
		firstWildPokemonFrontX = 650;
		appear = ImageIO.read(new File(".\\img\\wild\\appear.png"));
		at =ImageIO.read(new File(".\\img\\wild\\at.png"));
		run = ImageIO.read(new File(".\\img\\wild\\run.png"));
		wilds = ImageIO.read(new File(".\\img\\wild\\wilds.png"));			

		wildPokemonList = new ArrayList<Integer>();		
		pokemon = new Pokemon();
		
		setWildPokemon();
	}
	
	public BufferedImage getAppear() {
		return appear;
	}
	public BufferedImage getAt() {
		return at;
	}
	public BufferedImage getRun() {
		return run;
	}
	public BufferedImage getWilds() {
		return wilds;
	}
	public ArrayList<Integer> getWildPokemonList(){
		return wildPokemonList;
	}
	public void setWildPokemon(){
		wildPokemonList.add(Pokemon.PURIN_NUM);		
		wildPokemonList.add(Pokemon.GGOBUGI_NUM);
		wildPokemonList.add(Pokemon.LEESANG_NUM);		
	}
}
