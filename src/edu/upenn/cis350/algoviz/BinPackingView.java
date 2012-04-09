package edu.upenn.cis350.algoviz;

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

import edu.upenn.cis350.algoviz.R;

/**
 * View where user packs objects into bins.
 */
public class BinPackingView extends View {
	
	///// Static variables /////
	private static BinPackingProblemFactory factory;
	private static ArrayList<BinObject> objects = new ArrayList<BinObject>();
	private static ArrayList<Bin> bins = new ArrayList<Bin>();
	private static BinObject objToMove = null;
	private static final int TEXT_X_OFFSET = 10;
	private static final int TEXT_Y_OFFSET = 25;
	
	///// Instance variables /////
	private boolean reset;
	private int current_value;
	private Toast toast_rightSolution;
	private Toast toast_wrongSolution;
	private BinObjectPaginator _currentPaginator, _unallocatedObjectsPaginator;

	
	///// Constructors /////
	public BinPackingView(Context c) {
		super(c);
		BinPackingView.factory = new BinPackingProblemFactory(c);
		reset=true;
	}
	
	public BinPackingView(Context c, AttributeSet a) {
		super(c,a);
		BinPackingView.factory = new BinPackingProblemFactory(c);
		reset=true;
	}
	
	
	///// Public methods /////
	public void initialize() {
		
		objects = new ArrayList<BinObject>();
		bins = new ArrayList<Bin>();
		
		bins.addAll(factory.getBins(((BinPackingActivity) this.getContext()).getProblemName()));
		objects.addAll(factory.getBinObjects(((BinPackingActivity) this.getContext()).getProblemName()));
		
		// Location of center of View
		int mid = this.getWidth() / 2;
		
		_unallocatedObjectsPaginator = new BinObjectPaginator(mid, "Unallocated Objects");
		_unallocatedObjectsPaginator.addAll(objects);
		
		_currentPaginator = _unallocatedObjectsPaginator;
				
		// Set locations for all Bins
		Bin b1, b2, b3;
		switch (BinPackingView.bins.size()) {
		case 1:
			b1 = BinPackingView.bins.get(0);
			b1.instantiatePaginator(mid, "Middle Bin");
			b1.setX(mid - (b1.getWidth() / 2));
			b1.setY(this.getHeight() - b1.getHeight());
			break;
		case 2:
			b1 = BinPackingView.bins.get(0);
			b1.instantiatePaginator(mid, "Left Bin");
			b2 = BinPackingView.bins.get(1);
			b2.instantiatePaginator(mid, "Right Bin");
			b1.setX(mid - b1.getWidth() - 10);
			b1.setY(this.getHeight() - b1.getHeight());
			b2.setX(mid + 10);
			b2.setY(this.getHeight() - b2.getHeight());
			break;
		case 3:
			b1 = BinPackingView.bins.get(0);
			b1.instantiatePaginator(mid, "Left Bin");
			b2 = BinPackingView.bins.get(1);
			b2.instantiatePaginator(mid, "Middle Bin");
			b3 = BinPackingView.bins.get(2);
			b3.instantiatePaginator(mid, "Right Bin");
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
		drawLevel(c);
	}
	
	protected void drawLevel(Canvas canvas){
		drawBins(canvas);
		drawPaginator(canvas);
		drawObjects(canvas);
	}
	
	private void drawBins(Canvas canvas) {
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
	}
	
	private void drawPaginator(Canvas canvas) {
		Paint paint = new Paint();
		paint.setColor(_currentPaginator.getColor());	
		int xPos = _currentPaginator.getX(), yPos = _currentPaginator.getY(),
				width =  _currentPaginator.getWidth(), height = _currentPaginator.getHeight();		
		canvas.drawRect(xPos, yPos, xPos + width, yPos + height, paint);
		
		paint.setColor(Color.WHITE);
		String title = _currentPaginator.getTitle();
		canvas.drawText(title, xPos + width/2 - title.length()/2 * 6, yPos, paint);
		String currentPage = _currentPaginator.getText();
		canvas.drawText(currentPage, xPos + width/2 - currentPage.length()/2 * 6, yPos+height-10, paint);
		
		canvas.drawText("Next Page >>", width/2 + 80, yPos+height-10, paint);
		canvas.drawText("<< Previous Page", width/2 - 80 - 16*6, yPos+height-10, paint);
		if (!_currentPaginator.equals(_unallocatedObjectsPaginator)) canvas.drawText("X", width-50, yPos, paint);
	}
	
	private void drawObjects(Canvas canvas) {
		Paint paint = new Paint();
		for (BinObject obj : _currentPaginator.getCurrentPageObjects()) {
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
		float x = event.getX(), y = event.getY();
		int action = event.getAction();
		
		if (action == MotionEvent.ACTION_DOWN) return handleClick(x, y);
		else if (action == MotionEvent.ACTION_MOVE) return handleDrag(x, y);
		else if (action == MotionEvent.ACTION_UP) return handleDrop(x, y);
		else return false;	
	}
	
	private boolean handleClick(float x, float y) {
		for (BinObject obj : _currentPaginator.getCurrentPageObjects()) {
			if (obj.containsPoint(x, y)) {
				obj.setXDiff(x - obj.getX());
				obj.setYDiff(y - obj.getY());
				obj.setColor(Color.YELLOW);
				objToMove = obj;
				invalidate();
				return true;
			}
		}
		objToMove = null;
		
		for (Bin bin : bins) {
			if (bin.containsPoint(x, y)) {
				_currentPaginator = bin.getPaginator();
				invalidate();
				return true;
			}
		}
		
		int xPos = _currentPaginator.getX(), yPos = _currentPaginator.getY(),
				width =  _currentPaginator.getWidth(), height = _currentPaginator.getHeight();
		
		ShapeObject prev = new ShapeObject(Color.BLACK, 12, 16*6, width/2 - 80 - 16*6, yPos+height-24, "");
		ShapeObject next = new ShapeObject(Color.BLACK, 12, 12*6, width/2 + 80, yPos+height-24, "");
		ShapeObject exit = new ShapeObject(Color.BLACK, 16, 12, width - 52, yPos-14, "");
		
		if (prev.containsPoint(x, y)) {
			_currentPaginator.previousPage();
			invalidate();
		} else if (next.containsPoint(x, y)) {
			_currentPaginator.nextPage();
			invalidate();
		} else if (exit.containsPoint(x,y)) {
			_currentPaginator = _unallocatedObjectsPaginator;
			invalidate();
		}
		
		return false;
	}
	
	private boolean handleDrag(float x, float y) {
		if (objToMove == null) return true;
		objToMove.setX((int)(x - objToMove.getXDiff()));
		objToMove.setY((int)(y - objToMove.getYDiff()));
		invalidate();
		return true;
	}
	
	private boolean handleDrop(float x, float y) {
		if (objToMove == null) return true;
		else objToMove.setColor(Color.BLUE);
		
		Bin currentBin = _currentPaginator.getBin();
		
		// If object collides with current paginator or current bin
		if (objToMove.collidesWith(_currentPaginator) || objToMove.collidesWith(currentBin)) { 
			objToMove.setX(objToMove.oldx);
			objToMove.setY(objToMove.oldy);
			invalidate();
			return true;
		}
		
		// If object collides with a different bin
		for (Bin bin : bins) {
			if (objToMove.collidesWith(bin)) {
				boolean inserted = bin.insert(objToMove);
				if (inserted) {
					if (currentBin != null) currentBin.remove(objToMove);
					else _unallocatedObjectsPaginator.remove(objToMove);
				} else {
					objToMove.setX(objToMove.oldx);
					objToMove.setY(objToMove.oldy);
				}
				invalidate();
				//TODO check if the solution is right
				return true;
			}
		}
		
		// If object was dropped outside current paginator and did not collide with a bin, remove it and
		// add to unallocatedObjectsPaginator
		if (currentBin != null) currentBin.remove(objToMove);
		else _unallocatedObjectsPaginator.remove(objToMove);
		_unallocatedObjectsPaginator.add(objToMove);
		invalidate();
		return true;
	}

	
	
	//TODO: fix this part
	public void updateValue(int value){
    	TextView count_text=(TextView)this.findViewById(R.id.textView4);
    	if (count_text!=null)//now it's returning null
    		count_text.setText(Integer.toString(value));
    }
	
	
	//when the user press "done" button
	public void submit(){
		//calculate the current value
		current_value=0;
		for (Bin bin : bins) 
			current_value += bin.getValue(); 	
		
		this.updateValue(current_value);
				
		
		if (BinPackingView.factory.getOptimalSolution(((BinPackingActivity) this.getContext()).getProblemName())==current_value){
			toast_rightSolution.show();
		}
		else{
			//supposedly if the user get a close answer, should show a toast
			toast_wrongSolution.show();
		}
		
	}
	
	public BinPackingProblemFactory getFactory() {
		return factory;
	}
		
} 