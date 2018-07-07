package Pocketmon;

import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import Information.RedHave;

public class Menu {	
	private static Menu menuInstance;

	public static int MENU_DISABLE =0;
	public static int MAINMENU_ABLE =1;

	public static int DO = 2;
	public static int NONE =-1;
	public static int YES =1;

	public int MenuState;	
	public MainMenu mainMenu;
	public BackPackMenu backPackMenu;
	public PokemonMenu pokemonMenu;
	public RedHave redHave;
	public int itemToPokemon;
	public int useItemNum;
	
	private Menu() throws IOException, FontFormatException {
//		redHave = new RedHave();
		mainMenu = new MainMenu();
		backPackMenu = new BackPackMenu();
		pokemonMenu = new PokemonMenu();		
		MenuState= MENU_DISABLE;
		itemToPokemon = NONE;
		useItemNum = NONE;
	}
	
	public static Menu getInstance(){
		if(menuInstance==null){
			try{
				menuInstance = new Menu();
			}catch(IOException e1){
				e1.printStackTrace();
			}catch(FontFormatException e2){
				e2.printStackTrace();
			}
		}
		return menuInstance;
	}
	
	public class MainMenu{
		public static final int POSITION_NUM = 3;
		public static final int NONE = -1;
		public static final int POKEMONMENU_POSITION =0;
		public static final int BAGMENU_POSITION =1;	
		public static final int EXIT_POSITION =2;	
				
		private Position[] position;
		private ArrayList<Position> pointerPosition;
		
		public int subMenuState;
		public int pointerIndex;
		private BufferedImage pointer;
		private BufferedImage mainMenu;
		private BufferedImage bagTextField;
		private BufferedImage pocketmonTextField;
		private BufferedImage exitTextField;
		
		
		public MainMenu() throws IOException {
			subMenuState=NONE;	
			pointerIndex=0;	
			pointer = ImageIO.read(new File(".\\img\\menu\\main_menu\\pointer.png"));
			mainMenu = ImageIO.read(new File(".\\img\\menu\\main_menu\\mainmenu.png"));
			bagTextField = ImageIO.read(new File(".\\img\\menu\\main_menu\\bagtextfield.png"));
			pocketmonTextField = ImageIO.read(new File(".\\img\\menu\\main_menu\\pokemonlistcomment.png"));
			exitTextField = ImageIO.read(new File(".\\img\\menu\\main_menu\\exitcomment.png"));			
			
			position = new Position[POSITION_NUM];
			position[POKEMONMENU_POSITION] = new Position(420,64);
			position[BAGMENU_POSITION] = new Position(420,128);
			position[EXIT_POSITION] = new Position(420,512);
			
			pointerPosition = new ArrayList<Position>();
			for(int i=0;i<POSITION_NUM;i++){
				pointerPosition.add(position[i]);
			}
		}
		public ArrayList<Position> getpointerPosition(){
			return pointerPosition;
		}
		public BufferedImage getPointer() {
			return pointer;
		}
		public BufferedImage getMainMenu() {
			return mainMenu;
		}
		public BufferedImage getBagTextField() {
			return bagTextField;
		}
		public BufferedImage getPocketmonTextField() {
			return pocketmonTextField;
		}
		public BufferedImage getExitTextField() {
			return exitTextField;
		}		
	}
	public class BackPackMenu{
		public static final int POSITION_NUM = 4;
		
		private Position[] position;	
		private ArrayList<Position> pointerPosition;
		
		
		public int pointerIndex;
		private BufferedImage backPack;
		private BufferedImage backPackPointer;
		private BufferedImage exit;

		
		public BackPackMenu() throws IOException {
			//positionNum = 1;
			
			pointerIndex=0;	//메뉴 초기 팝업시 최초 포인터 위치
			backPack = ImageIO.read(new File(".\\img\\menu\\menu_backpack\\backpack.png"));
			backPackPointer = ImageIO.read(new File(".\\img\\menu\\menu_backpack\\backpackpointer.png"));
			exit = ImageIO.read(new File(".\\img\\menu\\menu_backpack\\exit.png"));
						
			
			position = new Position[POSITION_NUM];
			position[0] = new Position(260,96,288,68,544,124);
			position[1] = new Position(260,160,288,132,544,188);
			position[2] = new Position(260,224,288,196,544,252);
			position[3] = new Position(260,288,288,260,544,316);
			
			pointerPosition = new ArrayList<Position>();
			for(int i=0;i<POSITION_NUM;i++){
				pointerPosition.add(position[i]);
			}
		}
		public ArrayList<Position> getpointerPosition(){
			return pointerPosition;
		}
		public BufferedImage getBackPack() {
			return backPack;
		}
		public BufferedImage getBackPackPointer() {
			return backPackPointer;
		}
		public BufferedImage getExit() {
			return exit;
		}		
	}
	public class PokemonMenu{
		public static final int POSITION_NUM = 7;
		
		private Position[] position;	
		private ArrayList<Position> pointerPosition;	
				
		public int pointerIndex;
		//private BufferedImage level;
		//private BufferedImage hpFrame;
		private BufferedImage frame;
		private BufferedImage exit;		
		private BufferedImage pointer;
		private BufferedImage gain;
		private BufferedImage be;
		private BufferedImage OOuro;
		private BufferedImage levelee;
		
		public PokemonMenu() throws IOException {
			//positionNum = 1;
			
			pointerIndex=0;	
			gain = ImageIO.read(new File(".\\img\\menu\\menu_pokemonlist\\gain.png"));
			be = ImageIO.read(new File(".\\img\\menu\\menu_pokemonlist\\Be.png"));
			OOuro = ImageIO.read(new File(".\\img\\menu\\menu_pokemonlist\\OOuro.png"));
			levelee = ImageIO.read(new File(".\\img\\menu\\menu_pokemonlist\\levelee.png"));
			frame = ImageIO.read(new File(".\\img\\menu\\menu_pokemonlist\\frame.png"));
			exit = ImageIO.read(new File(".\\img\\menu\\menu_pokemonlist\\exit.png"));
			pointer = ImageIO.read(new File(".\\img\\menu\\menu_pokemonlist\\pointer.png"));
						
			position = new Position[POSITION_NUM];
			position[0] = new Position(4,32,32,4,260,64,352,36,416,44,268,0);
			position[1] = new Position(4,32+(64*1),32,4+(64*1),260,64+(64*1),352,36+(64*1),416,44+(64*1),268,(64*1));
			position[2] = new Position(4,32+(64*2),32,4+(64*2),260,64+(64*2),352,36+(64*2),416,44+(64*2),268,(64*2));
			position[3] = new Position(4,32+(64*3),32,4+(64*3),260,64+(64*3),352,36+(64*3),416,44+(64*3),268,(64*3));
			position[4] = new Position(4,32+(64*4),32,4+(64*4),260,64+(64*4),352,36+(64*4),416,44+(64*4),268,(64*4));
			position[5] = new Position(4,32+(64*5),32,4+(64*5),260,64+(64*5),352,36+(64*5),416,44+(64*5),268,(64*5));
			
			position[6] = new Position(4,32+(64*6),32,4+(64*6),260,64+(64*6),352,36+(64*6),416,44+(64*6),268,(64*6));
			
			pointerPosition = new ArrayList<Position>();
			for(int i=0;i<POSITION_NUM;i++){
				pointerPosition.add(position[i]);
			}			
		}
		public ArrayList<Position> getpointerPosition(){
			return pointerPosition;
		}
		public BufferedImage getPointer() {
			return pointer;
		}
		public BufferedImage getFrame() {
			return frame;
		}
		public BufferedImage getExit() {
			return exit;
		}
		public BufferedImage getGain() {
			return gain;
		}
		public BufferedImage getBe() {
			return be;
		}
		public BufferedImage getOOuro() {
			return OOuro;
		}
		public BufferedImage getLevelee() {
			return levelee;
		}
	}
	class Position{
		public int x;
		public int y;
		//item
		public int itemX;
		public int itemY;
		public int numX;
		public int numY;
		//pokemon
		public int iconNameX;
		public int iconNameY;
		public int levelX;
		public int levelY;
		public int hpFrameX;
		public int hpFrameY;
		public int hpBarX;
		public int hpBarY;	
		public int stunX;
		public int stunY;

		
		Position(int x, int y){
			this.x= x;
			this.y= y;
		}
		Position(int x, int y,int itemX ,int itemY,int numX,int numY){
			this.x = x; 
			this.y = y;
			this.itemX = itemX;
			this.itemY = itemY;
			this.numX = numX;
			this.numY = numY;			
		}
		Position(int x, int y,int iconNameX,int iconNameY, int levelX,int levelY,
				int hpFrameX,int hpFrameY, int hpBarX, int hpBarY, int stunX, int stunY){
			this.x = x; 
			this.y = y;
			this.iconNameX=iconNameX;
			this.iconNameY=iconNameY;
			this.levelX=levelX;
			this.levelY=levelY;
			this.hpFrameX=hpFrameX;
			this.hpFrameY=hpFrameY;
			this.hpBarX=hpBarX;
			this.hpBarY=hpBarY;
			this.stunX = stunX;
			this.stunY = stunY;
		}			
	}	
}
