package vn.hust.zoo.logic;

import java.util.ArrayList;

import android.util.Log;

public class GameLogic {
	private static int level = 1;
	private static String name  = "su tu";
	private static ArrayList<String> answer = new ArrayList<String>();
	
	//TODO set correct Name + Level
	public void setName(int level){
		GameLogic.level = level;
		GameLogic.name = "cho";
	}
	
	public static ArrayList<String> setCorrectAnswer(){
		String[] b = name.split("");
		for (String string : b) {
			Log.d("Answer",string);
			answer.add(string);
		}
		answer.remove(0);
		return null;
	}
	
	public static ArrayList<String> getAnswer(){return GameLogic.answer;}
	public static int getLevel(){return GameLogic.level;}
}
