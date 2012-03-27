package edu.upenn.cis.cis350.algorithmvisualization;

import edu.upenn.cis.cis350.algorithmvisualization.R;
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
	
	int level;
	int[] finished;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
   		super.onCreate(savedInstanceState);
   		setContentView(R.layout.bin_packing);
   		level=1;
   		finished=new int[6];
    }
    
    public int getDifficulty() {
    	return this.level;
    }
    
    public void onNextLevelClick(View v){
    	if (level<=6){
    		level++;
    	
    		((BinPackingView) this.findViewById(R.id.binview)).reset();
    	
    		TextView count_text=(TextView)findViewById(R.id.textView2);
    		count_text.setText(Integer.toString(level));
    		
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
    	if (level>1){
    		level--;
        	
    		((BinPackingView) this.findViewById(R.id.binview)).reset();
    	
    		TextView count_text=(TextView)findViewById(R.id.textView2);
    		count_text.setText(Integer.toString(level));
    		
    		((BinPackingView) this.findViewById(R.id.binview)).updateValue(0);
    		
    	}
    	
    }
    
    
    
    

}