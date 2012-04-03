package edu.upenn.cis350.algoviz;

import java.util.ArrayList;

import android.graphics.Color;



/**
 * A Bin with a specified capacity. Objects can be inserted to or removed from the bin.
 */
public class Bin extends ShapeObject {

	
	private double capacity;
	private double weight;
	private double value;
	
	private ArrayList<BinObject> contents;
	
	
	Bin(double capacity) {

		super(Color.RED, 80, 60, 0, 0, "0, 0, Cap:" + capacity);

		this.capacity = capacity;
		this.contents = new ArrayList<BinObject>();
		this.weight = 0;
		this.value = 0;
	}
	
	public double getCapacity() { return capacity; }
	public double getWeight() { return weight; }
	public double getValue() { return value; }
	public ArrayList<BinObject> getContents() { return contents; }
	
	/**
	 * Inserts an object into the bin.
	 * @param obj The object to be inserted.
	 * @return true if the object was inserted correctly, false otherwise.
	 */
	public boolean insert(BinObject obj) {
		if (weight + obj.getWeight() > capacity || contents.contains(obj)) return false;
		else {
			weight += obj.getWeight();
			value += obj.getValue();
			contents.add(obj);
			message = weight + " ," + value + " , Cap: " + capacity; 
			return true;
		}
	}
	
	/**
	 * Removes an object from the bin.
	 * @param obj The object to be removed.
	 * @return true if the object was removed, or false if the object wasn't in this bin.
	 */
	public boolean remove(BinObject obj) {
		if (contents.contains(obj)) {
			weight -= obj.getWeight();
			value -= obj.getValue();
			contents.remove(obj);
			message = weight + " ," + value + " , Cap: " + capacity; 
			return true;
		} else return false;
	}
	
}