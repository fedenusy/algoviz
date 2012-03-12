package edu.upenn.cis.cis350.algorithmvisualization;

import android.graphics.Color;

public class ShapeObject 
{
	private int baseColor;
	private int selColor;	
	private int width;
	private int length;
	//top-left point
	protected float locx;
	protected float locy;
	

	public ShapeObject()
	{
		baseColor = Color.RED;
		selColor = Color.WHITE;
		width = 50;
		length = 50;
		locx=300;
		locy=300;
	}
	
	
	public ShapeObject(int basColor, int selectColor, int mywidth, int mylength)
	{
		baseColor = basColor;
		selColor = selectColor;
	
		width = mywidth;
		length = mylength;
		locx=300;
		locy=300;
	}
	
	public ShapeObject(int basColor, int selectColor, int mywidth, int mylength, int x, int y)
	{
		baseColor = basColor;
		selColor = selectColor;

		width = mywidth;
		length = mylength;
		locx=x;
		locy=y;
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

	public float getX(){
		return locx;
	}
	
	public float getY(){
		return locy;
	}
	
	public void setX(int x){
		locx=x;
	}
	
	public void getY(int y){
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
