package vn.hust.zoo.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.util.Log;

public class GameLogic {
	private static int level = 1;
	private static String name  = "su tu";
	private static ArrayList<String> answer = new ArrayList<String>();
	private static ArrayList<String> all = new ArrayList<String>();
	private static Random r = new Random();

	//TODO set correct Name + Level
	public void setName(int level){
		GameLogic.level = level;
		GameLogic.name = "cho";
	}
	
	public static void initCharactec(){
		String[] b = name.split("");
		for (String string : b) {
			Log.d("Answer",string);
			answer.add(string);
			if(!string.equals(" ")) all.add(string);
		}
		answer.remove(0);
		all.remove(0);
		
		for(int i = all.size(); i <= 8; i++){
			String c = ""  + ((char)(r.nextInt(26) + 'a'));
			all.add(c);
		}
		
		Collections.shuffle(all);
	}
	
	public static ArrayList<String> getAnswer(){return GameLogic.answer;}
	public static ArrayList<String> getAll(){return GameLogic.all;}
	public static int getLevel(){return GameLogic.level;}
}
