package edu.upenn.cis.cis350.algorithmvisualization;

import android.graphics.Color;

public class ShapeObject 
{
	private int baseColor;
	private int selColor;	
	private int width;
	private int length;
	protected String message;
	//top-left point
	protected int locx;
	protected int locy;
	//record the old positions
	protected int oldx;
	protected int oldy;
	

	public ShapeObject()
	{
		baseColor = Color.RED;
		selColor = Color.WHITE;
		width = 50;
		length = 50;
		message = "Null";
		locx=300;
		locy=300;
		oldx=300;
		oldy=300;
	}
	
	public ShapeObject(int basColor, int selectColor, int mywidth, int mylength, int x, int y, String mymessage)
	{
		baseColor = basColor;
		selColor = selectColor;

		width = mywidth;
		length = mylength;
		locx=x;
		locy=y;
		oldx = locx;
		oldy = locy;
		message = mymessage;
	}
	
	public int getBase()
	{
		return baseColor;
	}
	
	public int getSel()
	{
		return selColor;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getLength()
	{
		return length;
	}
	
	public String getText()
	{
		return message;
	}
	
	public void setText(String text)
	{
		message = text;
	}
	
	//public abstract void takeAction()

	public int getX(){
		return locx;
	}
	
	public int getY(){
		return locy;
	}
	
	public void setX(int x){
		locx=x;
	}
	
	public void setY(int y){
		locy=y;
	}

	//check if two shapes collides
	public boolean collidesWith(ShapeObject s2) {

		if( ( (locx>=s2.locx && locx<=s2.locx+s2.width) || (locx+width>=s2.locx && locx+width<=s2.locx+s2.width) )
				&& ( (locy>=s2.locy && locy<=s2.locy+s2.width) || (locy+width>=s2.locy && locy+width<=s2.locy+s2.width) ))
			return true;
		return false;
	}
	
	//check if the shape contains the given point
	public boolean containsPoint(float x, float y) {
		if((x>locx)&&(x<locx+length)&&(y>locy)&&(y<locy+width))
			return true;
		return false;
	}
	
	

}
