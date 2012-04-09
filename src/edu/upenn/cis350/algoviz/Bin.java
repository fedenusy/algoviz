package edu.upenn.cis350.algoviz;

import java.util.ArrayList;

import android.graphics.Color;



/**
 * A Bin with a specified capacity. Objects can be inserted to or removed from the bin.
 */
public class Bin extends ShapeObject {

	///// Instance variable /////
	private double _capacity;
	private double _weight;
	private double _value;
	private ArrayList<BinObject> _contents;
	private BinObjectPaginator _paginator;
	
	
	///// Constructors /////
	Bin(double capacity) {
		super(Color.RED, 80, 60, 0, 0, "0, 0, Cap:" + capacity);
		_capacity = capacity;
		_weight = 0;
		_value = 0;	
		_contents = new ArrayList<BinObject>();
	}
	
	
	///// Getter methods /////
	public double getCapacity() { return _capacity; }
	public double getWeight() { return _weight; }
	public double getValue() { return _value; }
	public ArrayList<BinObject> getContents() { return _contents; }
	
	
	///// Public methods /////
	/**
	 * Inserts an object into the bin.
	 * @param obj The object to be inserted.
	 * @return true if the object was inserted correctly, false otherwise.
	 */
	public boolean insert(BinObject obj) {
		if (_weight + obj.getWeight() > _capacity || _contents.contains(obj)) return false;
		else {
			_weight += obj.getWeight();
			_value += obj.getValue();
			_contents.add(obj);
			_paginator.add(obj);
			message = _weight + " ," + _value + " , Cap: " + _capacity; 
			return true;
		}
	}
	
	/**
	 * Removes an object from the bin.
	 * @param obj The object to be removed.
	 * @return true if the object was removed, or false if the object wasn't in this bin.
	 */
	public boolean remove(BinObject obj) {
		if (_contents.contains(obj)) {
			_weight -= obj.getWeight();
			_value -= obj.getValue();
			_contents.remove(obj);
			_paginator.remove(obj);
			message = _weight + " ," + _value + " , Cap: " + _capacity; 
			return true;
		} else return false;
	}
	
	public void instantiatePaginator(int mid, String title) {
		_paginator = new BinObjectPaginator(mid, title);
	}
	
}
