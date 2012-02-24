package edu.upenn.cis.cis350.algorithmvisualization;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * View where user packs objects into bins.
 */
public class BinPackingView extends View {

	//Constructors
	public BinPackingView(Context c) {
		super(c);
	}
	public BinPackingView(Context c, AttributeSet a) {
		super(c, a);
	}
	
	//Called when the view is displayed
	public void onDraw(Canvas canvas) {
		//TODO Draw initial layout
	}
	
	//Event handler
	public boolean onTouchEvent(MotionEvent event) {
		//TODO Handle user input
		return false;
	}
	
}
