package edu.upenn.cis.cis350.algorithmvisualization;

import android.graphics.Color;

public class ShapeObject 
{
	private int baseColor;
	private int selColor;	
	private int width;
	private int length;
	//top-left point
	private float locx;
	private float locy;
	

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

	public boolean collidesWith(ShapeObject s2) {
		
		/**
		if( ( (x>=s2.x && x<=s2.x+s2.width) || (x+width>=s2.x && x+width<=s2.x+s2.width) )
				&& ( (y>=s2.y && y<=s2.y+s2.width) || (y+width>=s2.y && y+width<=s2.y+s2.width) ))
			return true;*/
		return false;
	}
	

}
