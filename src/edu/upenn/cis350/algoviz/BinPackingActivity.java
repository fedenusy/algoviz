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
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
   		super.onCreate(savedInstanceState);
   		setContentView(R.layout.bin_packing);
   		_problemName = "Baby Packer";
   		_finished = new int[6];
    }
    
    public String getProblemName() {
    	return _problemName;
    }
    
    public void onNextLevelClick(View v){
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
    	((BinPackingView) this.findViewById(R.id.binview)).submit();
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

}