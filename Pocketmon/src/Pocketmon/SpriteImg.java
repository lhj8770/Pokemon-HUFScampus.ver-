package Pocketmon;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteImg {	//자르고 싶은 sprite의
	private int row;		//행의 갯수
	private int col;		//열의 갯수
	private int width;		//행의 길이
	private int height;		//열의 길이
	private String fileName;
	private BufferedImage biginningImg;
	
	public SpriteImg(int row, int col, int width, int height, String fileName) {	
		this.row=row;
		this.col=col;
		this.width=width;
		this.height=height;
		this.fileName=fileName;
	}	
	public BufferedImage[] makeSpriteImg() throws IOException{
		biginningImg = ImageIO.read(new File(fileName));	// Sprite 원본
		BufferedImage[] splitImg = new BufferedImage[row * col];
		for(int i=0; i<row; i++)
			for(int j=0; j<col ;j++) //아래와 같이 하면 각 배열에 자른 이미지가 들어가게 되며(i*col +j) 
				splitImg[i*col +j]= biginningImg.getSubimage( j*width, i*height, width, height);
				// 첫번째 인자와 두번째 인자로 upper left 의 x,y 의 좌표값을, 세번째 인자와 네번째 인자로 자를 길이를 넘겨 준다.				
		return splitImg;
	}
}
