package edu.upenn.cis.cis350.algorithmvisualization;

/**
 * An object with a specified weight, value, and type.
 */
public class BinObject {
	
	private double weight, value;
	private String type;
	
	BinObject(double weight, double value, String type) {
		this.weight = weight;
		this.value = value;
		this.type = type;
	}
	
	public double getWeight() { return weight; }
	public double getValue() { return value; }
	public String getType() { return type; }
	
}
