package edu.upenn.cis.cis350.algorithmvisualization;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Main activity. User chooses between Bin-Packing and Max-Flow here.
 */
public class AlgorithmVisualizationActivity extends Activity {
    
	public static final int ACTIVITY_BinPackingActivity = 1;
	public static final int ACTIVITY_MaxFlowActivity = 2;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }
    
    public void onBinPackingButtonClick(View v) { 	
    	Intent i = new Intent(this, BinPackingActivity.class);
    	startActivityForResult(i,
    			AlgorithmVisualizationActivity.ACTIVITY_BinPackingActivity);
    }
    
    public void onMaxFlowButtonClick(View v) {
    	Intent i = new Intent(this, MaxFlowActivity.class);
    	startActivityForResult(i,
    			AlgorithmVisualizationActivity.ACTIVITY_MaxFlowActivity);
    }
    
}