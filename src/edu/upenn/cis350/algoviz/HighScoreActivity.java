package edu.upenn.cis350.algoviz;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HighScoreActivity extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";
	private SharedPreferences _scores;
	
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	   	super.onCreate(savedInstanceState);	  
	   	setContentView(R.layout.high_score);
	    
	    _scores = getSharedPreferences(PREFS_NAME, 0);
	   	setScores();
	      
	    }
	 
	 public void onReturnClick(View v){
		 super.onBackPressed();
	 }
	 
	 public void onResetClick(View v){
		 _scores = getSharedPreferences(PREFS_NAME, 0);
		 Editor e=_scores.edit();
		 e.clear();
		 e.commit();
		 setScores();
	 }
	 
	 
	 private void setText(String probName, TextView count_text){
		 int currScore=_scores.getInt(probName, -1);	
		 if (currScore!=-1)
			 count_text.setText(((Integer)currScore).toString());
		 else
			 count_text.setText("--");		 
	 }
	 
	 private void setScores(){
		
		 TextView count_text=(TextView)findViewById(R.id.textViewL1);
		 setText("Baby Packer", count_text);
 		 
 		 TextView count_text2=(TextView)findViewById(R.id.textViewL3);
 		 setText("Pack Rat", count_text2);	
 		
 		 
 		 TextView count_text3=(TextView)findViewById(R.id.textViewL5);
 		 setText("Experienced Packer", count_text3);		 
 		
		 
 		 TextView count_text4=(TextView)findViewById(R.id.textViewL7);
 		 setText("Packing Connoisseur", count_text4);	
 		
 		 
 		 TextView count_text5=(TextView)findViewById(R.id.textViewL9);
 		 setText("Pack Master", count_text5);	

		 
	 }
	 
	 
	
	
}
