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
	private int _objWidth;
	private int _objHeight;
	private String _title;
	private Bin _bin;
	
	
	///// Constructors /////
	public BinObjectPaginator(int mid, int objWidth, int objHeight, String title) {
		super(Color.BLACK, 20+(objHeight+20)*4+30, mid*2, 0, 40, "");
		_mid = mid;
		_title = title;
		_objects = new ArrayList<BinObject>();
		_objWidth = objWidth;
		_objHeight = objHeight;
		_currentPage = 1;
		updatePaginator();
	}
	
	
	///// Setter methods /////
	public void setBin(Bin bin) { _bin = bin; }
	
	
	///// Getter methods /////
	public String getTitle() { return _title; }
	public Bin getBin() { return _bin; }
	
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
		if (_currentPage < 1) _currentPage = _numPages;
		updatePaginator();
	}
	
	
	///// Private methods /////
	private void updatePaginator() {
		double numObjects = _objects.size();
		_numPages = (int) Math.ceil(numObjects / 16);
		if (_numPages == 0) _numPages = 1;
		message = _currentPage + " / " + _numPages;
		updateObjectPositions();
	}
	
	private void updateObjectPositions() {
		int row = 0, col = 0;
		for (BinObject obj : getCurrentPageObjects()) {
			if (col==4) { col = 0; row++; }
			
			int x = _mid - 2 * _objWidth - 30 + (_objWidth + 20) * col;
			int y = 20 + getY() + (_objHeight + 20) * row;
			obj.setX(x); obj.setOldX(x);
			obj.setY(y); obj.setOldY(y);
			
			col++;
		}
	}
	
}
