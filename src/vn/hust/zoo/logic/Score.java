package vn.hust.zoo.logic;


public class Score {
    
    private static final int NUM_QUIZ = 27;
    private static int[] score = null;
    
    public static void init() {
	score = new int[NUM_QUIZ];
	for (int i = 0; i < NUM_QUIZ; i++) score[i] = 0;
	// TODO Load saved score
    }
    
    public static int getScore(int level) {
	return score[level];
    }
    
    public static void setScore(int level, int sc) {
	if (sc >= 0 && sc <= 3) score[level] = sc;
    }
    
}
