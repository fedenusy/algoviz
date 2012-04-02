package edu.upenn.cis350.algoviz;

import edu.upenn.cis350.algoviz.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AlgovizActivity extends Activity {
    /** Called when the activity is first created. */
	ScoreBoard board;
	int level;
	public static final int ACTIVITY_BinPackingActivity = 1;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        ScoreBoard board = new ScoreBoard();
        level=0;
    }
    
    public void onStartClick(View v){
    	this.level=1;
    	showLevel(this.level);
    }
    
   
    public void showLevel(int level){
    	Intent i = new Intent(this, BinPackingActivity.class);
    	startActivityForResult(i,
    			AlgovizActivity.ACTIVITY_BinPackingActivity);
    }
  
    
}