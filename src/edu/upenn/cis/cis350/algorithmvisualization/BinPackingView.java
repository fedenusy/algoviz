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
	private static ArrayList<Integer> xpos = new ArrayList<Integer>(); //deprecated
	private static ArrayList<Integer> ypos = new ArrayList<Integer>(); //deprecated
	private static ArrayList<Float> xdiff = new ArrayList<Float>();
	private static ArrayList<Float> ydiff = new ArrayList<Float>();
	private static ArrayList<Integer> xposold = new ArrayList<Integer>(); //deprecated
	private static ArrayList<Integer> yposold = new ArrayList<Integer>(); //deprecated
	
	//text
	private static ArrayList<String> texts = new ArrayList<String>(); //deprecated
	
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
		
		//this.factory = new BinPackingProblemFactory(c);
		//this.bins = (ArrayList)factory.getBins("easy");
		//this.objects = (ArrayList)factory.getBinObjects("easy");
		
		Bin bin1 = new Bin(100);
		BinObject obj1 = new BinObject(10, 20, "type unknown");
		Bin bin2 = new Bin(50);
		BinObject obj2 = new BinObject(20,30, "type unknown");
		Bin bin3 = new Bin(20);
		
		bin1.setX(0);
		bin2.setX(102);
		bin3.setX(204);
		bin1.setY(300);
		bin2.setY(300);
		bin3.setY(300);
		obj1.setX(0);
		obj2.setX(100);
		obj1.setY(50);
		obj2.setY(50);
		obj1.oldx = obj1.locx;
		obj1.oldy = obj1.locy;
		obj2.oldx = obj2.locx;
		obj2.oldy = obj2.locy;
		
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
						
			colors.add(objects.get(x).getBase());
			xdiff.add((float)0.0);
			ydiff.add((float)0.0);					
		}
		
		for(int x = 0; x < bins.size(); x++)
		{
			widths.add(bins.get(x).getWidth());
			lengths.add(bins.get(x).getLength());
			bases.add(bins.get(x).getBase());
			sels.add(bins.get(x).getSel());
						
			colors.add(bins.get(x).getBase());
			xdiff.add((float)0.0);
			ydiff.add((float)0.0);				
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
		Paint paint = new Paint();		
		if (bins.size() < 1 || bins.size() > 3) 
		{
			Log.v("Bin file loading error", "Number of bins must be 1, 2, or 3!");
		}
			
		for(int x = 0; x < bins.size() + objects.size(); x++)
		{
			paint.setColor(colors.get(x));
			ShapeObject curobj = null;
			if (x < objects.size())
			{
				curobj = objects.get(x);
				canvas.drawRect(curobj.getX(), curobj.getY(), curobj.getX() + lengths.get(x), curobj.getY() + widths.get(x), paint);
				paint.setColor(Color.WHITE);
				canvas.drawText(curobj.getText(), curobj.getX()+TEXT_X_OFFSET, curobj.getY()+widths.get(x)/2, paint);
			}
			else
			{
				//Log.v("bin index", "" + (x - objects.size()));
				curobj = bins.get(x - objects.size());
				canvas.drawRect(curobj.getX(), curobj.getY(), curobj.getX() + lengths.get(x), curobj.getY() + widths.get(x), paint);
				paint.setColor(Color.WHITE);
				canvas.drawText(curobj.getText(), curobj.getX()+TEXT_X_OFFSET, curobj.getY()+widths.get(x)/2, paint);
			}

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
				BinObject curobj = objects.get(n);
				if (xloc < curobj.getX() + lengths.get(n) && xloc > curobj.getX() && 
						yloc < curobj.getY() + widths.get(n) && yloc > curobj.getY())
				{
					xdiff.remove(n);
					xdiff.add(n, xloc - curobj.getX());
					ydiff.remove(n);
					ydiff.add(n, yloc - curobj.getY());
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

			objects.get(moveID-1).setX((int)(xloc - xdiff.get(moveID-1)));
			objects.get(moveID-1).setY((int)(yloc - ydiff.get(moveID-1)));
			
			invalidate();
			return true;
		}
		else if (action == MotionEvent.ACTION_UP)
		{
			BinObject curobj = objects.get(moveID-1);
			Log.v("xpos", "" + curobj.getX());
			for (int n = 0; n < bins.size(); n++)
			{
				Bin curbin = bins.get(n);
				if (curobj.collidesWith(curbin))
				{
					curbin.insert(curobj);
					objects.remove(curobj);
					bases.remove(moveID - 1);
					sels.remove(moveID - 1);
					widths.remove(moveID - 1);
					lengths.remove(moveID - 1);
					colors.remove(moveID - 1);
					xdiff.remove(moveID - 1);
					ydiff.remove(moveID - 1);
				}
			}
			for (int n = 0; n < objects.size(); n++)
			{
				if (n != moveID-1)
				{
					BinObject checkobj = objects.get(n);
					if (curobj.collidesWith(checkobj))
					{
						curobj.setX(curobj.oldx);
						curobj.setY(curobj.oldy);
					}
				}
			}
			curobj.oldx = curobj.locx;
			curobj.oldy = curobj.locy;
			colors.clear();
			colors.addAll(bases);
			moveID = 0;
			invalidate();
			return true;
		}
		return false;
	}
} 
