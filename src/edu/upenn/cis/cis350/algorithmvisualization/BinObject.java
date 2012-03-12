package edu.upenn.cis.cis350.algorithmvisualization;
import android.graphics.Color;

public class BinObject extends ShapeObject{
	
	private double weight;
	private double value;
	
	BinObject(double weight, double value) {
		super(Color.BLUE, Color.GRAY, 40, 80, weight + ", " + value);
		this.weight = weight;
		this.value = value;
	}
	
	public double getWeight() { return weight; }
	public double getValue() { return value; }
	
}
