package edu.upenn.cis.cis350.algorithmvisualization;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * View where user packs objects into bins.
 */
public class BinPackingView extends View {
	
	//factory generate the problems from xml file
	private static BinPackingProblemFactory factory;
	private static ArrayList<BinObject> objects = new ArrayList<BinObject>();
	private static ArrayList<Bin> bins = new ArrayList<Bin>();
	private static BinObject objToMove = null;

	private static final int TEXT_X_OFFSET = 10;
	private static final int TEXT_Y_OFFSET = 25;
	
	private boolean reset;
	
	private int current_value;
	private Toast toast_rightSolution;
	private Toast toast_wrongSolution;

	public BinPackingView(Context c) {
		super(c);
		this.factory = new BinPackingProblemFactory(c);
		reset=true;
	}
	
	public BinPackingView(Context c, AttributeSet a) {
		super(c,a);
		this.factory = new BinPackingProblemFactory(c);
		reset=true;
	}
	
	public void initialize() {
		
		objects = new ArrayList<BinObject>();
		bins = new ArrayList<Bin>();
		
		bins.addAll(factory.getBins(((BinPackingActivity) this.getContext()).getDifficulty()));
		objects.addAll(factory.getBinObjects(((BinPackingActivity) this.getContext()).getDifficulty()));		
		
		// Location of center of View
		int mid = this.getWidth() / 2;
				
		// Checks if number of BinObjects is greater than 16
		if (this.objects.size() > 16) {
			Log.v("Object number error", "Number of objects must be 16 or less!");
			return;
		}
				
		// Set locations for all BinObjects
		Iterator<BinObject> objIt = this.objects.iterator();
		for (int row = 0; row < 4; row++) {
			for (int col = 0; col < 4; col++) {
						if (objIt.hasNext()) {
							BinObject currObj = objIt.next();
							currObj.setX((mid - (2 * currObj.getWidth()) - 30) + col * (currObj.getWidth() + 20));
							currObj.setY(20 + (row * 70));
						}
					}
		}
				
				// Set locations for all Bins
		Bin b1, b2, b3;
		switch (this.bins.size()) {
		case 1:
			b1 = this.bins.get(0);
			b1.setX(mid - (b1.getWidth() / 2));
			b1.setY(this.getHeight() - b1.getHeight());
			break;
		case 2:
			b1 = this.bins.get(0);
			b2 = this.bins.get(1);
			b1.setX(mid - b1.getWidth() - 10);
			b1.setY(this.getHeight() - b1.getHeight());
			b2.setX(mid + 10);
			b2.setY(this.getHeight() - b2.getHeight());
			break;
		case 3:
			b1 = this.bins.get(0);
			b2 = this.bins.get(1);
			b3 = this.bins.get(2);
			b1.setX(mid - b2.getWidth() / 2 - b1.getWidth() - 20);
			b1.setY(this.getHeight() - b1.getHeight());
			b2.setX(mid - b2.getWidth() / 2);
			b2.setY(this.getHeight() - b2.getHeight());
			b3.setX(mid + b2.getWidth() / 2 + 20);
			b3.setY(this.getHeight() - b3.getHeight());
				break;
		default:
			Log.v("Bin number error", "Number of bins must be 1, 2, or 3!");
			break;
		} 
				
		//init the value
		current_value=0;
						
		//init the toast
		CharSequence text = "Great Job!!!";
		CharSequence text2 = "Try Again!!!";
		int duration = Toast.LENGTH_SHORT;

		toast_rightSolution = Toast.makeText(this.getContext(), text, duration);
		toast_rightSolution.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
		
		toast_wrongSolution = Toast.makeText(this.getContext(),text2, duration);
		toast_wrongSolution.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				
	}
	
	protected void onDraw(Canvas c) {
		
		// If drawing for the first time, get bins and objects from factory
		// and set their locations using initialize()		
		if (reset==true) {
			initialize();
			reset=false;
		}
		
		
		this.drawLevel(c);
		
		
	}
	
	
	//draw the bin and objects of the current level
	protected void drawLevel(Canvas canvas){
		Paint paint = new Paint();
		
		for (Bin bin : bins) {
			paint.setColor(bin.getColor());
			canvas.drawRect(bin.getX(), bin.getY(), bin.getX() + bin.getWidth(), bin.getY() + bin.getHeight(), paint);
			paint.setColor(Color.WHITE);
			if (bin.getText().indexOf('C') == -1)
				throw new UnsupportedOperationException("Unexpected Bin Text");
			String text1 = bin.getText().substring(0,bin.getText().indexOf('C'));
			String text2 = bin.getText().substring(bin.getText().indexOf('C'));
			canvas.drawText(text1, bin.getX() + TEXT_X_OFFSET, bin.getY() + bin.getHeight()/2, paint);
			canvas.drawText(text2, bin.getX() + TEXT_X_OFFSET, bin.getY() + bin.getHeight()/2 + TEXT_Y_OFFSET, paint);
		}
		
		for (BinObject obj : objects) {
			paint.setColor(obj.getColor());
			canvas.drawRect(obj.getX(), obj.getY(), obj.getX() + obj.getWidth(), obj.getY() + obj.getHeight(), paint);
			paint.setColor(Color.WHITE);
			if (obj.getText().indexOf('$') == -1)
				throw new UnsupportedOperationException("Unexpected Object Text");
			String text1 = obj.getText().substring(0,obj.getText().indexOf('$'));
			String text2 = obj.getText().substring(obj.getText().indexOf('$'));
			canvas.drawText(text1, obj.getX() + TEXT_X_OFFSET, obj.getY() + obj.getHeight()/2, paint);
			canvas.drawText(text2, obj.getX() + TEXT_X_OFFSET, obj.getY() + obj.getHeight()/2 + TEXT_Y_OFFSET, paint);
		}
		
	}
	
	
	
	public void reset(){
		reset=true;
		invalidate();
	}
	

	public boolean onTouchEvent(MotionEvent event) {
		
		int action = event.getAction();
		float xloc = event.getX();
		float yloc = event.getY();
		
		if (action == MotionEvent.ACTION_DOWN) 
		{
			for (BinObject obj : objects) 
			{
				if (xloc < obj.getX() + obj.getWidth() && xloc > obj.getX() && 
						yloc < obj.getY() + obj.getHeight() && yloc > obj.getY()) 
				
				{
					obj.setXDiff(xloc - obj.getX());
					obj.setYDiff(yloc - obj.getY());
					obj.setColor(Color.YELLOW);
					objToMove = obj;
					invalidate();
					return true;
				}
			}
			//deals with removal
			for (int i = 0; i < bins.size(); i++)
			{
				Bin bin = bins.get(i);
				if (xloc < bin.getX() + bin.getWidth() && xloc > bin.getX() && 
						yloc < bin.getY() + bin.getHeight() && yloc > bin.getY()) 
				{
					ArrayList<BinObject> contents = new ArrayList<BinObject>(bin.getContents());
					for (BinObject obj: contents)
					{
						objToMove = obj;
						objects.add(objToMove);
						bin.remove(objToMove);
						objToMove.setX(0);
						objToMove.setY(0);
						resolveCollision(0, 0, true);
						invalidate();
					}
				}
			}			
		}
		
		// Upon ACTION_MOVE event
		else if (action == MotionEvent.ACTION_MOVE) {
			objToMove.setX((int)(xloc - objToMove.getXDiff()));
			objToMove.setY((int)(yloc - objToMove.getYDiff()));
			invalidate();
			return true;
		}
		
		// Upon ACTION_UP event
		else if (action == MotionEvent.ACTION_UP) 
		{
			//check if object is on a bin
			for (Bin bin : bins) 
			{
				if (objToMove.collidesWith(bin))
				{
					boolean inserted = bin.insert(objToMove);
					if (inserted)
					{
						objects.remove(objToMove);
						break;
					}
					else
					{
						objToMove.setX(objToMove.oldx);
						objToMove.setY(objToMove.oldy);
						resolveCollision(0, 0, true);
					}
				}
			}
			//check if object is on another object
			resolveCollision(0, 0, true);
			

			objToMove.setOldX(objToMove.locx);
			objToMove.setOldY(objToMove.locy);
			
			objToMove.setColor(Color.BLUE);
			
			//check if the solution is right
			
			
			
			invalidate();
			return true;
		}
		return false;
	}
	
	//To-Do: fix this part
	public void updateValue(int value){
    	TextView count_text=(TextView)this.findViewById(R.id.textView4);
    	if (count_text!=null)//now it's returning null
    		count_text.setText(Integer.toString(value));
    }
	
	
	private void resolveCollision(int xoffset, int yoffset, boolean col_detected)
	{
		if (objects == null || objects.isEmpty() || !col_detected)
			return;
		col_detected = false;
		for(BinObject obj: objects)
		{
			while (objToMove.collidesWith(obj) && !objToMove.equals(obj))
			{
				col_detected = true;
				if (xoffset != 4)
					xoffset++;
				else
				{
					yoffset++;
					xoffset = 0;
				}
				objToMove.setX((objToMove.getWidth()+5)*xoffset);
				objToMove.setY((objToMove.getHeight()+5)*yoffset);
			}
		}
		resolveCollision(xoffset, yoffset, col_detected);
	}
	
	
	//when the user press "done" button
	public void submit(){
		//calculate the current value
		current_value=0;
		for (Bin bin : bins) 
			current_value += bin.getValue(); 	
		
		this.updateValue(current_value);
				
		
		if (this.factory.getOptimalSolution(((BinPackingActivity) this.getContext()).getDifficulty())==current_value){
			toast_rightSolution.show();
		}
		else{
			//supposedly if the user get a close answer, should show a toast
			toast_wrongSolution.show();
		}
		
	}
		
} 