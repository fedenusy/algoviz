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
//import java.awt.*;

/**
 * View where user packs objects into bins.
 */
public class BinPackingView extends View {
	
	//givens
	//factory
	//private static BinPackingProblemFactory factory;
	
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
	
	//text
	private static ArrayList<String> texts = new ArrayList<String>();
	
	//private static final int MAX_WIDTH = 100;
	private static final int MAX_LENGTH = 100;
	private static final int TEXT_X_OFFSET = 10;
	
	private static int moveID = 0;
	//flag = 0 => no object
	//Otherwise, flag corresponds to an object that is defined

	

	public BinPackingView(Context c) {
		super(c);
	}
	public BinPackingView(Context c, AttributeSet a) 
	{
		super(c,a);
		//ShapeObject square = new ShapeObject();
		//ShapeObject rect = new ShapeObject(Color.CYAN, Color.GRAY, 80, 100, "test2");
		
//		this.factory = new BinPackingProblemFactory(c);
//		this.bins = (ArrayList)factory.getBins("easy");
//		this.objects = (ArrayList)factory.getBinObjects("easy");
		
		Bin bin1 = new Bin(100);
		BinObject obj1 = new BinObject(10, 20, "type unknown");
		Bin bin2 = new Bin(50);
		BinObject obj2 = new BinObject(20,30, "type unknown");
		Bin bin3 = new Bin(20);
		bins.add(bin1);
		bins.add(bin2);
		bins.add(bin3);
		objects.add(obj1);
		objects.add(obj2);

		
		for(int x = 0; x < objects.size(); x++)
		{
			widths.add(objects.get(x).getWidth());
			lengths.add(objects.get(x).getLength());
			bases.add(objects.get(x).getBase());
			sels.add(objects.get(x).getSel());
			texts.add(objects.get(x).getText());
						
			colors.add(objects.get(x).getBase());
			xpos.add(x*(MAX_LENGTH+1));
			ypos.add(10);
			xdiff.add((float)0.0);
			ydiff.add((float)0.0);			
			xposold.add(x*(MAX_LENGTH+1));
			yposold.add(10);		
		}
		
		for(int x = 0; x < bins.size(); x++)
		{
			widths.add(bins.get(x).getWidth());
			lengths.add(bins.get(x).getLength());
			bases.add(bins.get(x).getBase());
			sels.add(bins.get(x).getSel());
			texts.add(bins.get(x).getText());
						
			colors.add(bins.get(x).getBase());
			xpos.add(x*(MAX_LENGTH+1));
			ypos.add(350);
			xdiff.add((float)0.0);
			ydiff.add((float)0.0);			
			xposold.add(x*(MAX_LENGTH+1));
			yposold.add(350);			
		}


	}
	
	
	/**protected void onDraw(Canvas canvas) {
		Paint paint = new Paint();
		int binWidth = bins.get(0).getWidth();
		int binHeight = bins.get(0).getLength();
		paint.setColor(bins.get(0).getBase());
		if (bins.size() < 1 || bins.size() > 3) {
			Log.v("Bin file loading error", "Number of bins must be 1, 2, or 3!");
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
		
		
		
	}**/
	
	protected void onDraw(Canvas canvas) 
	{
	
		// this is the "paintbrush"
		Paint paint = new Paint();		
		if (bins.size() < 1 || bins.size() > 3) 
		{
			Log.v("Bin file loading error", "Number of bins must be 1, 2, or 3!");
		}
			
		for(int x = 0; x < bins.size() + objects.size(); x++)
		{
			paint.setColor(colors.get(x));
			canvas.drawRect(xpos.get(x), ypos.get(x), xpos.get(x) + lengths.get(x), ypos.get(x) + widths.get(x), paint);
			paint.setColor(Color.WHITE);
			canvas.drawText(texts.get(x), xpos.get(x)+TEXT_X_OFFSET, ypos.get(x)+widths.get(x)/2, paint);
		}
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
			Log.v("sqX", xpos.get(0) + "");
			Log.v("sqY", ypos.get(0) + "");
			Log.v("rectX", xpos.get(1) + "");
			Log.v("rectY", ypos.get(1) + "");**/
			
			for (int n = 0; n < objects.size(); n ++)
			{
				if (xloc < xpos.get(n) + lengths.get(n) && xloc > xpos.get(n) && yloc < ypos.get(n) + widths.get(n) && yloc > ypos.get(n))
				{
					xdiff.remove(n);
					xdiff.add(n, xloc - xpos.get(n));
					ydiff.remove(n);
					ydiff.add(n, yloc - ypos.get(n));
					colors.remove(n);
					colors.add(n, sels.get(n));
					moveID = n+1;
					invalidate();
					return true;
				}
			}
		}
		else if (action == MotionEvent.ACTION_MOVE)
		{
			if (moveID == 0)
				throw new IndexOutOfBoundsException();
			xpos.remove(moveID-1);
			xpos.add(moveID-1, (int)(xloc - xdiff.get(moveID-1)));
			
			ypos.remove(moveID-1);
			ypos.add(moveID-1, (int)(yloc - ydiff.get(moveID-1)));
			
			invalidate();
			return true;
		}
		else if (action == MotionEvent.ACTION_UP)
		{
			for (int n = 0; n < objects.size() + bins.size(); n ++)
			{
				for (int m = 0; m < objects.size() + bins.size(); m++)
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
			moveID = 0;
			invalidate();
			return true;
		}
		return false;
	}
	
	/**public boolean onTouchEvent(MotionEvent event)
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
			Log.v("rectY", ypos.get(1) + "");
			
			for (int n = 0; n < shapelist.size(); n ++)
			{
				if (xloc < xpos.get(n) + lengths.get(n) && xloc > xpos.get(n) && yloc < ypos.get(n) + widths.get(n) && yloc > ypos.get(n))
				{
					xdiff.remove(n);
					xdiff.add(n, xloc - xpos.get(n));
					ydiff.remove(n);
					ydiff.add(n, yloc - ypos.get(n));
					colors.remove(n);
					colors.add(n, sels.get(n));
					moveID = n+1;
					invalidate();
					return true;
				}
			}
		}
		else if (action == MotionEvent.ACTION_MOVE)
		{
			if (moveID == 0)
				throw new IndexOutOfBoundsException();
			xpos.remove(moveID-1);
			xpos.add(moveID-1, (int)(xloc - xdiff.get(moveID-1)));
			
			ypos.remove(moveID-1);
			ypos.add(moveID-1, (int)(yloc - ydiff.get(moveID-1)));
			
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
			moveID = 0;
			invalidate();
			return true;
		}
		return false;
	} **/
	
} 
