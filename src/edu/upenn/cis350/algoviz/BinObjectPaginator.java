package edu.upenn.cis350.algoviz;

import java.util.ArrayList;
import java.util.Collection;

import android.graphics.Color;

public class BinObjectPaginator extends ShapeObject {

	///// Instance variables /////
	private ArrayList<BinObject> _objects;
	private int _numPages;
	private int _currentPage;
	private int _mid;
	private String _title;
	
	
	///// Constructors /////
	public BinObjectPaginator(int mid, String title) {
		super(Color.BLACK, 15+70*4+15, mid*2, 0, 40, "");
		_mid = mid;
		_title = title;
		_objects = new ArrayList<BinObject>();
		_currentPage = 1;
		updatePaginator();
	}
	
	
	///// Getter methods /////
	public String getTitle() { return _title; }
	
	public Collection<BinObject> getCurrentPageObjects() {
		ArrayList<BinObject> result = new ArrayList<BinObject>();
		int firstObjectIndex = (_currentPage - 1) * 16;
		int lastObjectIndex = firstObjectIndex + 15;
		for (int i=firstObjectIndex; i<=lastObjectIndex; i++) {
			if (i == _objects.size()) break;
			result.add(_objects.get(i));
		}
		return result;
	}
	
	
	///// Public methods /////
	public void add(BinObject object) {
		_objects.add(object);
		updatePaginator();
	}
	
	public void addAll(Collection<BinObject> objects) {
		_objects.addAll(objects);
		updatePaginator();
	}
	
	public void remove(BinObject object) {
		_objects.remove(object);
		updatePaginator();
	}
	
	public void nextPage() {
		_currentPage++;
		if (_currentPage > _numPages) _currentPage = 1;
		updatePaginator();
	}
	
	public void previousPage() {
		_currentPage--;
		if (_currentPage < _numPages) _currentPage = _numPages;
		updatePaginator();
	}
	
	
	///// Private methods /////
	private void updatePaginator() {
		int numObjects = _objects.size();
		_numPages = (int) Math.ceil(numObjects / 16);
		if (_numPages == 0) _numPages = 1;
		message = _currentPage + " / " + _numPages;
		updateObjectPositions();
	}
	
	private void updateObjectPositions() {
		int row = 0, col = 0;
		for (BinObject obj : getCurrentPageObjects()) {
			if (col==4) { col = 0; row++; }
			
			int x = _mid - 135 + 70 * col;
			int y = getY() + 15 + 70 * row;
			obj.setX(x); obj.setOldX(x);
			obj.setY(y); obj.setOldY(y);
			
			col++;
		}
	}
	
}
