package edu.upenn.cis350.algoviz;

import java.util.ArrayList;
import java.util.HashMap;


public class ScoreBoard {
	HashMap<Integer,Integer> highscores;
	
	public ScoreBoard(){
		highscores=new HashMap<Integer,Integer>();
	}
	
	
	public int setScore(int level,int score){
		if (this.highscores.get(level)==null){
			highscores.put(level,score);	
			return 1;
		}
		else{
			int time=highscores.get(level);
			if (time<score){
				highscores.put(level,score);
				return 2;}
			else
				return 1;
		}
	}
	
	public Integer getScore(int level){
		if (highscores.get(level)==null)
			return -1;
		else
			return highscores.get(level);		
	}
	
	
}
