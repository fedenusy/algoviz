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
	
	//givens
	//factory
	private static BinPackingProblemFactory factory;
	
	//list of objects
	private static ArrayList<BinObject> objects = new ArrayList<BinObject>();
	
	//list of bins
	private static ArrayList<Bin> bins = new ArrayList<Bin>();
	
	//base colors
	private static ArrayList<Integer> bases = new ArrayList<Integer>();
	
	//selected colors
	private static ArrayList<Integer> sels = new ArrayList<Integer>();
	
	//shape widths
	private static ArrayList<Integer> widths = new ArrayList<Integer>();
	
	//shape lengths
	private static ArrayList<Integer> lengths = new ArrayList<Integer>();
	
	//currents	
	//colors
	private static ArrayList<Integer> colors = new ArrayList<Integer>();
	
	//positions object
	private static ArrayList<Integer> xpos = new ArrayList<Integer>();
	private static ArrayList<Integer> ypos = new ArrayList<Integer>();
	private static ArrayList<Float> xdiff = new ArrayList<Float>();
	private static ArrayList<Float> ydiff = new ArrayList<Float>();
	private static ArrayList<Integer> xposold = new ArrayList<Integer>();
	private static ArrayList<Integer> yposold = new ArrayList<Integer>();
	
	private static final int MAX_WIDTH = 100;
	private static final int MAX_LENGTH = 100;
	
	/**
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
	private static int squareXold = squareX;
	private static int squareYold = squareY;
	private static int rectXold = rectX;
	private static int rectYold = rectY;**/
	
	private static int moveflag = 0;
	//flag = 0 => no object
	//flag = 1 => square
	//flag = 2 => rect

	
	// you must implement these constructors!!
	//2/24/12: context and or attribute set should pass in an arraylist of shapeobjects to draw
	public BinPackingView(Context c) {
		super(c);
//		this.factory = new BinPackingProblemFactory(c);
//		this.bins = (ArrayList)factory.getBins("easy");
//		this.objects = (ArrayList)factory.getBinObjects("easy");
		this.bins.add(new Bin(10));
	}
	
	public BinPackingView(Context c, AttributeSet a) {
		super(c, a);
//		this.factory = new BinPackingProblemFactory(c);
//		this.bins = (ArrayList)factory.getBins("easy");
//		this.objects = (ArrayList)factory.getBinObjects("easy");
		this.bins.add(new Bin(10));
	}
	
		
	//Should add functionality to generalize with passed arraylist.  See shapeobject class for parameters.
	
	protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		int binWidth = bins.get(0).getWidth();
		int binHeight = bins.get(0).getLength();
		paint.setColor(bins.get(0).getBase());
		if (bins.size() < 1 || bins.size() > 3) {
			System.out.println("Number of bins must be 1, 2, or 3!");
		}
		switch (bins.size()) {
			case 1:	canvas.drawRect(this.getWidth()/2 - binWidth/2, binHeight,
							this.getWidth()/2 + binWidth/2, 0, paint);
			case 2: canvas.drawRect(this.getWidth()/2 - binWidth - 10, binHeight,
							this.getWidth()/2 - 10, 0, paint);
					canvas.drawRect(this.getWidth()/2 + 10, binHeight,
							this.getWidth()/2 + binWidth + 10, 0, paint);
			case 3: canvas.drawRect(this.getWidth()/2 - binWidth/2 - binWidth - 10, binHeight,
							this.getWidth()/2 - binWidth/2 - 10, 0, paint);
					canvas.drawRect(this.getWidth()/2 - binWidth - 10, binHeight,
							this.getWidth()/2 - 10, 0, paint);
					canvas.drawRect(this.getWidth()/2 + binWidth/2 + 10, binHeight,
							this.getWidth()/2 + binWidth/2 + binWidth + 10, 0, paint);
		}
		
	}
	
	/*protected void onDraw(Canvas canvas) 
	{
	
		// this is the "paintbrush"
		Paint paint = new Paint();		
		// draw objects
		for(int x = 0; x < shapelist.size(); x++)
		{
			paint.setColor(colors.get(x));
			//105 multiplier is outside maximum bounds on a shape objects width or length
			canvas.drawRect(xpos.get(x), ypos.get(x), xpos.get(x) + lengths.get(x), ypos.get(x) + widths.get(x), paint);
		}
	} */
	
/*	public boolean onTouchEvent(MotionEvent event)
	{
		int action = event.getAction();
		float xloc = event.getX();
		float yloc = event.getY();
		if (action == MotionEvent.ACTION_DOWN)
		{
			/**Log.v("test", "action detected");
			Log.v("xloc", xloc + "");
			Log.v("yloc", yloc + "");
			Log.v("sqX", xpos.get(0) + "");
			Log.v("sqY", ypos.get(0) + "");
			Log.v("rectX", xpos.get(1) + "");
			Log.v("rectY", ypos.get(1) + "");**/
			
/*			for (int n = 0; n < shapelist.size(); n ++)
			{
				if (xloc < xpos.get(n) + lengths.get(n) && xloc > xpos.get(n) && yloc < ypos.get(n) + widths.get(n) && yloc > ypos.get(n))
				{
					xdiff.remove(n);
					xdiff.add(n, xloc - xpos.get(n));
					ydiff.remove(n);
					ydiff.add(n, yloc - ypos.get(n));
					colors.remove(n);
					colors.add(n, sels.get(n));
					moveflag = n+1;
					invalidate();
					return true;
				}
			}
		}
		else if (action == MotionEvent.ACTION_MOVE)
		{
			if (moveflag == 0)
				throw new IndexOutOfBoundsException();
			xpos.remove(moveflag-1);
			xpos.add(moveflag-1, (int)(xloc - xdiff.get(moveflag-1)));
			
			ypos.remove(moveflag-1);
			ypos.add(moveflag-1, (int)(yloc - ydiff.get(moveflag-1)));
			
			invalidate();
			return true;
		}
		else if (action == MotionEvent.ACTION_UP)
		{
			for (int n = 0; n < shapelist.size(); n ++)
			{
				for (int m = 0; m < shapelist.size(); m++)
				{
					if (n != m)
					{
						if (xpos.get(n) < xpos.get(m) + lengths.get(m) && xpos.get(n) > xpos.get(m) - lengths.get(n) &&
							ypos.get(n) <  ypos.get(m) + widths.get(m) && ypos.get(n) > ypos.get(m) - widths.get(n))
						{
							xpos.remove(n);
							xpos.add(n, xposold.get(n));
							ypos.remove(n);
							ypos.add(n, yposold.get(n));
						}
					}
				}
			}
			xposold.clear();
			xposold.addAll(xpos);
			yposold.clear();
			yposold.addAll(ypos);
			colors.clear();
			colors.addAll(bases);
			moveflag = 0;
			invalidate();
			return true;
		}
		return false;
	}
	*/
} 
