package edu.upenn.cis350.algoviz;

import java.util.ArrayList;
import java.util.HashMap;


public class ScoreBoard {
	HashMap<String,Double> highscores;
	
	public ScoreBoard(){
		highscores=new HashMap<String,Double>();
	}
	
	
	public int setScore(String level,double score){
		if (this.highscores.get(level)==null){
			highscores.put(level,score);	
			return 1;
		}
		else{
			double time=highscores.get(level);
			if (time<score){
				highscores.put(level,score);
				return 2;}
			else
				return 1;
		}
	}
	
	public Double getScore(String level){
		if (highscores.get(level)==null)
			return -1.0;
		else
			return highscores.get(level);		
	}
	
	
}
