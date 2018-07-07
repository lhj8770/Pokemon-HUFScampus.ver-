package Pocketmon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Evolution {
	public static int NONE = -1;
	public static int EVOL_1 = 1;
	public static int EVOL_2 = 2;
	public static int EVOL_3 = 3;
	
	private static Evolution evolutionInstance;
	
	//ここに回答するポケモンが入っていたbagpointが入る。
	public int evolBagPoint ;
	public int evolState;
	public int evolDelay;
	public BufferedImage congrat;
	public BufferedImage evol;
	public BufferedImage oing;
	public BufferedImage stateis;
	private Evolution() throws IOException {
		evolBagPoint = NONE;
		evolState = NONE;
		evolDelay=0;
		congrat = ImageIO.read(new File(".\\img\\evol\\congrat.png"));
		evol = ImageIO.read(new File(".\\img\\evol\\evol.png"));
		oing = ImageIO.read(new File(".\\img\\evol\\oing.png"));
		stateis = ImageIO.read(new File(".\\img\\evol\\stateis.png"));
		// TODO Auto-generated constructor stub
	}
	
	public static Evolution getInstance(){
		if(evolutionInstance==null){
			try{
				evolutionInstance = new Evolution();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return evolutionInstance;
	}
	
}
