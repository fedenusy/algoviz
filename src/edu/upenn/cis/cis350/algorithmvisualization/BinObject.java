package edu.upenn.cis.cis350.algorithmvisualization;
import android.graphics.Color;

/**
 * An object with a specified weight, value, and type.
 */
public class BinObject extends ShapeObject {
	
	private double weight, value, xdiff, ydiff;
	private String type;
	
	BinObject(double weight, double value, String type) {
		super(Color.BLUE, 50, 50, 0, 0, weight + ", $" + value);
		this.weight = weight;
		this.value = value;
		this.type = type;
	}
	
	public double getWeight() { return weight; }
	public double getValue() { return value; }
	public double getXDiff() { return xdiff; }
	public double getYDiff() { return ydiff; }
	public String getType() { return type; }
	
	// Set the xdiff value, used to correct snapping
	public void setXDiff(double xdiff) {
		this.xdiff = xdiff;
	}
	
	// Set the ydiff value, used to correct snapping
	public void setYDiff(double ydiff) {
		this.ydiff = ydiff;
	}
}
