package edu.upenn.cis.cis350.algorithmvisualization;

import android.graphics.Color;

public class ShapeObject 
{
	private int baseColor;
	private int selColor;	
	private int width;
	private int length;
	private String message;
	
	public ShapeObject()
	{
		baseColor = Color.RED;
		selColor = Color.WHITE;
		width = 50;
		length = 50;
		message = "Null";
	}
	
	public ShapeObject(int basColor, int selectColor, int mywidth, int mylength, String mymessage)
	{
		baseColor = basColor;
		selColor = selectColor;
		if (mywidth < 0 || mywidth > 100)
			throw new IllegalArgumentException("Width out of bounds");
		if (mylength < 0 || mylength > 100)
			throw new IllegalArgumentException("Length out of bounds");
		width = mywidth;
		length = mylength;
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

}
