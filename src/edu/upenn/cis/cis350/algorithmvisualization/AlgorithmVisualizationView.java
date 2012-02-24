package edu.upenn.cis.cis350.algorithmvisualization;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class AlgorithmVisualizationView extends View {

	//Constructors
		public AlgorithmVisualizationView(Context c) {
			super(c);
		}
		public AlgorithmVisualizationView(Context c, AttributeSet a) {
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
