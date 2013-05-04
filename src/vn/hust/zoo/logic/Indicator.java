package vn.hust.zoo.logic;

import java.util.ArrayList;
import java.util.Stack;

import android.view.View;
import android.widget.Button;

public class Indicator {
	private Stack<Button> charAnswer = new Stack<Button>();
	private ArrayList<Button> playerAnswer = new ArrayList<Button>(); 
	
	public void addPlayerAnswerButton(Button b){
		playerAnswer.add(b);
	}
	public void addCharAnswerButton(Button b){
		playerAnswer.get(charAnswer.size()).setText(b.getText());
		b.setVisibility(View.INVISIBLE);
		charAnswer.push(b); 
	}
	public void removeCharAnswerButton(){
		if(charAnswer.isEmpty()) return;
		Button b = charAnswer.pop();
		playerAnswer.get(charAnswer.size()).setText(" ");
		b.setVisibility(View.VISIBLE);
	}
	public void deleteAnswer(Button b){
		Button b1;
		int i = charAnswer.size();
		try{
		do{
			 b1 = playerAnswer.get(--i);
			 removeCharAnswerButton();
		}while(b1 != b);
		}catch (Exception e) {
		}
	}
	public void clear(){
		charAnswer.clear();
		playerAnswer.clear();
	}
	public void hide(){
		for(Button b : playerAnswer){
			b.setVisibility(View.INVISIBLE);
		}
	}
	
	public Stack<Button> getCharAnswer(){return charAnswer;}
}
