package edu.upenn.cis350.algoviz;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class HighScoreActivity extends Activity {
	public static final String PREFS_NAME = "MyPrefsFile";
	
	 @Override
	  public void onCreate(Bundle savedInstanceState) {
	   	super.onCreate(savedInstanceState);	  
	   	setContentView(R.layout.high_score);
	   
	   	
	   	setScores();
	      
	    }
	 
	 public void onReturnClick(View v){
		 super.onBackPressed();
	 }
	 
	 private void setScores(){
		SharedPreferences scores = getSharedPreferences(PREFS_NAME, 0);

		 TextView count_text=(TextView)findViewById(R.id.textViewL1);
		
		 int currScore=scores.getInt("Baby Packer", -1);	
		 if (currScore!=-1)
			 count_text.setText(((Integer)currScore).toString());
 		 
 		 TextView count_text2=(TextView)findViewById(R.id.textViewL3);

 		 int currScore2=scores.getInt("Pack Rat", -1);	
 		 if (currScore2!=-1)
 			 count_text2.setText(((Integer)currScore2).toString());
 		 
 		 TextView count_text3=(TextView)findViewById(R.id.textViewL5);
 		 int currScore3=scores.getInt("Experienced Packer", -1);		 
 		 if (currScore3!=-1)
 			count_text3.setText(((Integer)currScore3).toString());
		 
 		 TextView count_text4=(TextView)findViewById(R.id.textViewL7);
 		 int currScore4=scores.getInt("Packing Connoisseur", -1);	
 		 if (currScore4!=-1)
 			 count_text4.setText(((Integer)currScore4).toString());
		 
 		 TextView count_text5=(TextView)findViewById(R.id.textViewL9);
 		 int currScore5=scores.getInt("Pack Master", -1);	
 		 if (currScore5!=-1)
 			 count_text5.setText(((Integer)currScore5).toString());

		 
	 }
	 
	 
	
	
}
