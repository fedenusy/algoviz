package edu.upenn.cis.cis350.algorithmvisualization;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;

/**
 * View where user packs objects into bins.
 */
public class BinPackingView extends View {
	
	//list of shapes
	private static ArrayList<ShapeObject> shapelist = new ArrayList<ShapeObject>();
	
	//colors
	private static int colorS = Color.CYAN;
	private static int colorR = Color.RED;
	
	//base colors
	private final static int bas_col1 = Color.CYAN;
	private final static int bas_col2 = Color.RED;
	
	//selected colors
	private final static int sel_col1 = Color.YELLOW;
	private final static int sel_col2 = Color.GREEN;
	
	//positions object 1
	private static int squareX = 100;
	private static int squareY = 100;
	private static float diffX;
	private static float diffY;
	
	//positions object 2
	private static int rectX = 400;
	private static int rectY = 400;
	private static float diffXr;
	private static float diffYr;
		
	//old positions of objects for snapback
	private static int squareXold = 100; 
	private static int squareYold = 100;
	private static int rectXold = 400;
	private static int rectYold = 400;
	
	private static int moveflag = 0;
	//flag = 0 => no object
	//flag = 1 => square
	//flag = 2 => rect

	
	// you must implement these constructors!!
	//2/24/12: context and or attribute set should pass in an arraylist of shapeobjects to draw
	public BinPackingView(Context c) 
	{
		super(c);
		ShapeObject square = new ShapeObject();
		ShapeObject rect = new ShapeObject(Color.CYAN, Color.GRAY, 80, 100);
		shapelist.add(square);
		shapelist.add(rect);
	}
	public BinPackingView(Context c, AttributeSet a) 
	{
		super(c, a);
	}
	
// This method is called when the View is displayed
	
	//Should add functionality to generalize with passed arraylist.  See shapeobject class for parameters.
	protected void onDraw(Canvas canvas) {
	
		// this is the "paintbrush"
		Paint paint = new Paint();

		
		// draw Rectangle with corners at (40, 20) and (90, 80)
		paint.setColor(colorR);
		canvas.drawRect(rectX, rectY, rectX + 60, rectY + 100, paint);
		
		paint.setColor(colorS);
		canvas.drawRect(squareX, squareY, squareX + 50, squareY + 50, paint);
		
		// change the color
		//paint.setColor(Color.BLUE);
		// set a shadow
		//paint.setShadowLayer(10, 10, 10, Color.GREEN);
				
		// create a bounding rectangle”
		//RectF rect = new RectF(150, 150, 280, 280);
		// draw an oval in the bounding rectangle
		//canvas.drawOval(rect, paint);
	} 
	
	public boolean onTouchEvent(MotionEvent event)
	{
		int action = event.getAction();
		float xloc = event.getX();
		float yloc = event.getY();
		if (action == MotionEvent.ACTION_DOWN)
		{
			/**Log.v("test", "action detected");
			Log.v("xloc", xloc + "");
			Log.v("yloc", yloc + "");
			Log.v("rectX", rectX + "");
			Log.v("rectY", rectY + "");**/
			
			if (xloc < squareX + 50 && xloc > squareX && yloc < squareY + 50 && yloc > squareY)
			{
				diffX = xloc - squareX;
				diffY = yloc - squareY;
				colorS = sel_col1;
				moveflag = 1;
				invalidate();
				return true;
			}
			else if (xloc < rectX + 60 && xloc > rectX && yloc < rectY + 100 && yloc > rectY)
			{
				diffXr = xloc - rectX;
				diffYr = yloc - rectY;
				colorR = sel_col2;
				moveflag = 2;
				invalidate();
				return true;
			}
			return false;			
		}
		else if (action == MotionEvent.ACTION_MOVE)
		{
			if (moveflag == 1)
			{
				squareX = (int)(xloc - diffX);
				squareY = (int)(yloc - diffY);
			}
			else if (moveflag == 2)
			{
				rectX = (int)(xloc - diffXr);
				rectY = (int)(yloc - diffYr);
			}
			invalidate();
			return true;
		}
		else if (action == MotionEvent.ACTION_UP)
		{
			if (rectX < squareX + 50 && rectX > squareX - 60 && rectY < squareY + 50 && rectY > squareY - 100 && moveflag == 2) 
			{
				rectX = rectXold;
				rectY = rectYold;
			}
			if (squareX < rectX + 60 && squareX > rectX - 50 && squareY < rectY + 100 && squareY > rectY - 50 && moveflag == 1)
			{
				squareX = squareXold;
				squareY = squareYold;
			}
			squareXold = squareX;
			squareYold = squareY;
			rectXold = rectX;
			rectYold = rectY;
			colorS = bas_col1;
			colorR = bas_col2;
			moveflag = 0;
			invalidate();
			return true;
		}
		return false;
	}
	
}
