package edu.upenn.cis.cis350.algorithmvisualization;

import android.graphics.Color;

public class ShapeObject {
	
	private int color;	
	private int height;
	private int width;
	protected String message;
	
	// stores the position of the top-left point
	protected int locx;
	protected int locy;
	
	// records the position before ACTION_MOVE
	protected int oldx;
	protected int oldy;

	public ShapeObject() {
		color = Color.RED;
		height = 50;
		width = 50;
		message = "Null";
		locx=300;
		locy=300;
		oldx=300;
		oldy=300;
	}
	
	public ShapeObject(int color, int myheight,
			int mywidth, int x, int y, String mymessage) {
		this.color = color;
		this.height = myheight;
		this.width = mywidth;
		this.locx = x;
		this.locy = y;
		this.oldx = locx;
		this.oldy = locy;
		this.message = mymessage;
	}
	
	public int getColor() { return color; }
	public int getHeight() { return height; }
	public int getWidth() { return width; }
	public String getText() { return message; }
	public int getX(){ return locx; }
	public int getY(){ return locy; }

	public void setText(String text) {
		message = text;
	}

	public void setX(int x){
		oldx = locx;
		locx = x;
	}
	
	public void setY(int y){
		oldy = locy;
		locy = y;
	}
	
	public void setColor(int color) {
		this.color = color;
	}

	//check if two shapes collides
	public boolean collidesWith(ShapeObject s2) {
		if( ( (locx>=s2.locx && locx<=s2.locx+s2.height) || (locx+height>=s2.locx && locx+height<=s2.locx+s2.height) )
				&& ( (locy>=s2.locy && locy<=s2.locy+s2.height) || (locy+height>=s2.locy && locy+height<=s2.locy+s2.height) ))
			return true;
		return false;
	}
	
	//check if the shape contains the given point
	public boolean containsPoint(float x, float y) {
		if((x>locx)&&(x<locx+width)&&(y>locy)&&(y<locy+height))
			return true;
		return false;
	}
}
