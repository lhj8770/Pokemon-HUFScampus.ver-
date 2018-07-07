package Pocketmon;

import java.awt.Image;
import java.awt.Toolkit;

public class Start{
	public static int NONE =0;
	public static int YES =1;
	
	public int startState;
	private Image image;
	public Start() {
		startState=NONE;
		image = Toolkit.getDefaultToolkit().createImage(".\\img\\start\\title.gif");
	}
	
	public Image getStartImage(){
		return image;
	}
}
