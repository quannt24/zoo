package vn.hust.zoo.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import android.util.Log;
import android.widget.Button;

public class GameLogic {
	private static int level = 1;
	private static String name  = "sutu";
	
	private static ArrayList<String> answer = new ArrayList<String>();
	private static ArrayList<String> all = new ArrayList<String>();

	private static Random r = new Random();
	private static Indicator mIndicator = new Indicator();
	
	private static int correctCharToGo;
	
	//TODO set correct Name + Level
	public void setName(int level){
		GameLogic.level = level;
		GameLogic.name = "cho";
	}
	
	public static void initCharacter(){
		String[] b = name.split("");
		correctCharToGo = 0;
		
		// TODO Not yet check if exceed total amount of 6 characters
		for (String string : b) {
			answer.add(string);
			if(!string.equals(" ") && !string.equals("")) {
				all.add(string);
				correctCharToGo++;
			}
		}
		answer.remove(0);
		
		for(int i = all.size(); i < 8; i++){
			String c = ""  + ((char)(r.nextInt(26) + 'a'));
			all.add(c);
		}
		
		Log.d("Choice","" + all.size());
		for(int i = 0; i < all.size(); i++)
			Log.d("Choice","" + all.get(i));
		
		Collections.shuffle(all);
	}
	
	public static void addPlayerAnswer(Button b){
		mIndicator.addPlayerAnswerButton(b);
	}
	public static boolean compare(){
		if(answer.size() != mIndicator.getCharAnswer().size()) return false;
		for(int i = 0; i < answer.size(); i++)
			if(!answer.get(i).equals((((Button)mIndicator.getCharAnswer().get(i)).getText()))) return false;
		return true;
	}
	public static boolean answer(Button b){
		if(mIndicator.getCharAnswer().size() == correctCharToGo) return false; //TODO full char
		mIndicator.addCharAnswerButton(b);
		Log.d("Answer","" + b.getText());
		return compare();
	}
	public static void delete(){
		mIndicator.removeCharAnswerButton();
	}
	public static void deleteAnswer(Button b){
		mIndicator.deleteAnswer(b);
	}
	public static void clear(){
		mIndicator.clear();
	}
	
	public static ArrayList<String> getAnswer(){return GameLogic.answer;}
	public static ArrayList<String> getAll(){return GameLogic.all;}
	public static int getLevel(){return GameLogic.level;}
	
}
