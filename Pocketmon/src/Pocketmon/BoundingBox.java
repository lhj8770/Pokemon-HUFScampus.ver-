package Pocketmon;

import java.awt.Rectangle;

//intersect演算に使われる四角形
public class BoundingBox {

	int x, y, width, height;

	public BoundingBox(int x, int y, int width, int height) { 
																
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public Rectangle makeBoundingBox() {
		return new Rectangle(x, y, width, height);
	}

	public void setBoundingBox(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
}
