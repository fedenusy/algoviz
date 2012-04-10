package edu.upenn.cis350.algoviz;

import edu.upenn.cis350.algoviz.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class BinPackingActivity extends Activity {
	
	String _problemName;
	int[] _finished;
	BinPackingProblemFactory _factory;
	
	private int _first_run;
	private double _top_score;
	private static final int READY_DIALOG = 1;
	private static final int CORRECT_DIALOG = 2;
	private static final int INCORRECT_DIALOG = 3;
	private ScoreBoard _sb;
	private long _mtime1, _mtime2;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
   		super.onCreate(savedInstanceState);
   		setContentView(R.layout.bin_packing);
   		_problemName = "Baby Packer";
   		_finished = new int[6];
   		showDialog(READY_DIALOG);
    }
    
    public String getProblemName() {
    	return _problemName;
    }
    
    public void onNextLevelClick(View v){
    	nextLevelHelper();
    }
    
    
    
    public void nextLevelHelper(){
    	if (!"Pack Master".equalsIgnoreCase(_problemName)){ //There's a next level to be played
    		BinPackingView view = ((BinPackingView) this.findViewById(R.id.binview));
    		_factory = view.getFactory();
    		
    		_problemName = nextProblem(_problemName);
    		
    		view.reset();
    	
    		TextView count_text=(TextView)findViewById(R.id.textView2);
    		count_text.setText(Integer.toString(getLevelCount()));
    		
    		((BinPackingView) this.findViewById(R.id.binview)).updateValue(0);
    	}
    	else
    	{
    		/*CharSequence text = "Congratulation! All levels finished!!!";
    		int duration = Toast.LENGTH_SHORT;
    		CharSequence text2 = "Not all level finished, go back to finish.";
    		Toast toast1 = new Toast(getApplicationContext());
    		toast1.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
    		toast1.setDuration(Toast.LENGTH_LONG);
    		toast1.setText(text);
    		toast1.show();*/
    		
    	}
    	
    	
    }
    
    
    public void onDoneClick(View v){
    	//click to show done
    	//To-do
    	int result=((BinPackingView) this.findViewById(R.id.binview)).submit();
    	if (result==1)
    		showDialog(CORRECT_DIALOG);
    	else
    		showDialog(INCORRECT_DIALOG);
    }
    
    public void onResetClick(View v){
    	((BinPackingView) this.findViewById(R.id.binview)).reset();
    	
    }
    
    public void onBackClick(View v){
    	String previousProblem = previousProblem(_problemName);
    	if (previousProblem != null){
    		_problemName = previousProblem;
        	
    		((BinPackingView) this.findViewById(R.id.binview)).reset();
    	
    		TextView count_text=(TextView)findViewById(R.id.textView2);
    		count_text.setText(Integer.toString(getLevelCount()));
    		
    		((BinPackingView) this.findViewById(R.id.binview)).updateValue(0);
    		
    	}
    	
    }
    
    
    private String nextProblem(String problemName) {
    	boolean sawProblem = false;
		for (String prob : _factory.getProblemNames()) {
			if (sawProblem) return prob;
			if (prob.equalsIgnoreCase(problemName)) sawProblem = true;
		}
		return null;
    }
    
    private String previousProblem(String problemName) {
    	String prevProblem = null;
		for (String prob : _factory.getProblemNames()) {
			if (prob.equalsIgnoreCase(problemName)) break;
			prevProblem = prob;
		}
		return prevProblem;
    }
    
    private int getLevelCount() {
    	int count = 0;
    	for (String prob : _factory.getProblemNames()) {
    		count++;
    		if (_problemName.equalsIgnoreCase(prob)) break;
    	}
    	return count;
    }
    
    
    protected Dialog onCreateDialog(int id) {
    	if (id == READY_DIALOG) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
                // this is the message to display
	    	builder.setMessage(R.string.ready); 
                // this is the button to display
	    	builder.setPositiveButton(R.string.yes,
	    		new DialogInterface.OnClickListener() {
                           // this is the method to call when the button is clicked 
	    	           public void onClick(DialogInterface dialog, int id) {
                                   // this will hide the dialog
	    	        	   dialog.cancel();
	    	        	   _mtime1=System.currentTimeMillis();
	    	           }
	    	         });
    		return builder.create();
    	}
    	
    	//create the correct dialog
    	if (id==CORRECT_DIALOG){
    		AlertDialog.Builder builder2 = new AlertDialog.Builder(this);
    		
    		_mtime2=System.currentTimeMillis();
        	double stime=(_mtime2-_mtime1)/1000.0;
        	
        	String str1=((Double)stime).toString();
        	
        	//this.sb.setScore(level, stime);
        	
        	if ((_first_run==1) && (stime<_top_score)){
        		_top_score=stime;
        		CharSequence text = "It's correct! You used "+str1+" seconds this time. You are the new top score! Click Yes to play again.";
        		builder2.setMessage(text); 
        	}
        	else{
        		if (_first_run==0){
        			_first_run=1;
        			_top_score=stime;
        			CharSequence text = "It's correct! You used"+str1+". seconds this time. Click Yes to next level.";
        			builder2.setMessage(text); 
        		}
        		else{
        			String str2=((Double)_top_score).toString();
        			CharSequence text = "It's correct! You used"+str1+" seconds this time. The top score is"+str2+". Click Yes to play again.";
        			builder2.setMessage(text); 
        		}
        	}
        	
            // this is the button to display
    		builder2.setPositiveButton(R.string.yes,
    		new DialogInterface.OnClickListener() {
                       // this is the method to call when the button is clicked 
    	           public void onClick(DialogInterface dialog, int id) {
                               // this will hide the dialog
    	        	   dialog.cancel();      	   
    	        	   showDialog(READY_DIALOG);
    	        	   nextLevelHelper();
    	           }
    	         });
    		return builder2.create();
    		
    	}
    	else
    		//create the incorrect dialog
    		if (id==INCORRECT_DIALOG){
    			_mtime2=System.currentTimeMillis();
            	double stime=(_mtime2-_mtime1)/1000.0;
            	String str1=((Double)stime).toString();
    			CharSequence text = "Wrong. Check again! Click on Yes to restart.";
    			
        		AlertDialog.Builder builder3 = new AlertDialog.Builder(this);
                // this is the message to display
        		builder3.setMessage(text); 
                // this is the button to display
        		builder3.setPositiveButton(R.string.yes,
        		new DialogInterface.OnClickListener() {
                           // this is the method to call when the button is clicked 
        	           public void onClick(DialogInterface dialog, int id) {
                                   // this will hide the dialog
        	        	   ((BinPackingView) findViewById(R.id.binview)).reset();
        	        	   dialog.cancel();
        	           }
        	         });
        		return builder3.create();
        		
        	}
    	
    	else return null;
    }

}