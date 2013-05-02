package vn.hust.zoo.logic;

import android.app.Activity;
import android.content.SharedPreferences;


public class Score {
    
    private static final String SCORE_PREF = "ZooScore";
    
    private static final int NUM_QUIZ = 27;
    private static int[] score = null;
    private static Activity act = null;
    
    public static void init(Activity act) {
	Score.act = act;
	
	if (score == null) score = new int[NUM_QUIZ];
	for (int i = 0; i < NUM_QUIZ; i++) score[i] = 0;
	
	// Load saved score
	loadScore();
    }
    
    public static int getScore(int level) {
	return score[level];
    }
    
    public static void setScore(int level, int sc) {
	if (sc >= 0 && sc <= 3) {
	    score[level] = sc;
	    saveScore();
	}
    }
    
    public static void loadScore() {
	SharedPreferences scorePref = act.getSharedPreferences(SCORE_PREF, 0);
	for (int i = 0; i < NUM_QUIZ; i++) {
	    score[i] = scorePref.getInt("level" + String.valueOf(i), 0);
	}
    }
    
    public static void saveScore() {
	if (act != null) {
	    SharedPreferences scorePref = act.getSharedPreferences(SCORE_PREF, 0);
	    SharedPreferences.Editor editor = scorePref.edit();
	    for (int i = 0; i < NUM_QUIZ; i++) {
		editor.putInt("level" + String.valueOf(i), score[i]);
	    }
	    editor.commit();
	}
    }
    
}
