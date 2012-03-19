package edu.upenn.cis.cis350.algorithmvisualization;

import edu.upenn.cis.cis350.algorithmvisualization.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

public class BinPackingActivity extends Activity {
	
	private static final int DIFFICULTY_DIALOG = 0;
	private String difficulty = "easy";
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
   		super.onCreate(savedInstanceState);
    	showDialog(DIFFICULTY_DIALOG);
    }
    
    public String getDifficulty() {
    	return difficulty;
    }
    
    protected Dialog onCreateDialog(int id) {
    	if (id == DIFFICULTY_DIALOG) {
	    	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    	builder.setMessage(R.string.difficulty); 
	    	builder.setPositiveButton(R.string.easy,
	    		new DialogInterface.OnClickListener() {
	    	           public void onClick(DialogInterface dialog, int id) {
	    	        	   dialog.cancel();
	    	        	   difficulty = "easy";
	    	           	   setContentView(R.layout.bin_packing);
	    	           }
	    	        });
	    	builder.setNeutralButton(R.string.medium,
		    	new DialogInterface.OnClickListener() {
		               public void onClick(DialogInterface dialog, int id) {
		   	        	   dialog.cancel();
		   	        	   difficulty = "medium";
		   	        	   setContentView(R.layout.bin_packing);
		   	           }
		   	        });
	    	builder.setNegativeButton(R.string.hard,
		    	new DialogInterface.OnClickListener() {
		    	       public void onClick(DialogInterface dialog, int id) {
		    	       	   dialog.cancel();
		    	       	   difficulty = "hard";
		    	       	   setContentView(R.layout.bin_packing);
		    	       }
		    	    });
    		return builder.create();
    	}
    	else return null;
    }

}
