package edu.upenn.cis.cis350.algorithmvisualization;
import android.graphics.Color;

/**
 * An object with a specified weight, value, and type.
 */
public class BinObject extends ShapeObject {
	
	private double weight, value;
	private String type;
	
	BinObject(double weight, double value, String type) {
		super(Color.BLUE, Color.GRAY, 40, 80, weight + ", " + value);
		this.weight = weight;
		this.value = value;
		this.type = type;
	}
	
	public double getWeight() { return weight; }
	public double getValue() { return value; }
	public String getType() { return type; }
	
}
