package vn.hust.zoo.ui;

import vn.hust.zoo.R;
import vn.hust.zoo.logic.GameLogic;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GameActivity extends Activity  implements FragmentManager.OnBackStackChangedListener {
	public static int RIGHT2LEFT = 0;
	public static int LEFT2RIGHT = 1;
	
	public static int direction = RIGHT2LEFT;

    private boolean mShowingBack = false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_flip);

		if (savedInstanceState == null) {
			CardFrontFragment c = new CardFrontFragment();
			c.set(this);
			getFragmentManager().beginTransaction().add(R.id.container, c).commit();
		} else {
			mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
		}

		getFragmentManager().addOnBackStackChangedListener(this);
		
		//TODO gameLogic
		GameLogic.initCharacter();
	}
	
	public void onDeleteAnswer(View v){
		GameLogic.deleteAnswer((Button) v);
	}
	
	public void onAnswer(View v){
//		Log.d("Alphabet","" + ((Button)v).getText());
		if(GameLogic.answer((Button)v)){
			Log.d("Result", "True");
			((LinearLayout) findViewById(R.id.answerRow1)).setVisibility(View.INVISIBLE);
			((LinearLayout) findViewById(R.id.answerRow2)).setVisibility(View.INVISIBLE);
			((Button) findViewById(R.id.next)).setVisibility(View.VISIBLE);
			//GameLogic.clear();
			((LinearLayout) findViewById(R.id.swipe_answer)).setOnTouchListener(null);
		}else{
			Log.d("Result", "False");
		}
	}
	
	public void onNext(View v){
		Intent i = new Intent(this, GameActivity.class);
//		getFragmentManager().popBackStackImmediate();
		startActivity(i);
		finish();
	}
	public void onLevel(View v){}
	
	public void onDelete(View v){
		GameLogic.delete();
	}
	
	public void onSwitch(View v){
		switch(v.getId()){
		case R.id.game_question: 
		case R.id.game_image:
			//TODO switch to answer 
			flipCard(); 
//			Log.d("Flip", "answer");
			break;
		case R.id.game_answer:   
			//TODO switch to next question
			flipCard(); 
//			Log.d("Flip", "question"); 
			break;
		}
	}
	
	public void flipCard() {
//        if (mShowingBack) {
//            getFragmentManager().popBackStack();
//            return;
//        }
//
//        mShowingBack = true;
		Object fragment;

		mShowingBack = !mShowingBack; // track
		
		// Create card side
		if(mShowingBack){
			fragment = new CardBackFragment();
			((CardBackFragment) fragment).set(this);
		}else{
			fragment = new CardFrontFragment();
			((CardFrontFragment) fragment).set(this);
		}

		// Create animation flip card
		if (GameActivity.direction == LEFT2RIGHT) {
			getFragmentManager()
					.beginTransaction()
					.setCustomAnimations(R.animator.card_flip_left_in,
							R.animator.card_flip_left_out,
							R.animator.card_flip_right_in,
							R.animator.card_flip_right_out) 
					.replace(R.id.container, (Fragment) fragment).commit();
		} else if (GameActivity.direction == RIGHT2LEFT) {
			getFragmentManager()
					.beginTransaction()
					.setCustomAnimations(R.animator.card_flip_right_in,
							R.animator.card_flip_right_out,
							R.animator.card_flip_left_in,
							R.animator.card_flip_left_out) 
					.replace(R.id.container, (Fragment) fragment).commit();
		}
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
    }

    public static class SwingGestureDetection extends SimpleOnGestureListener{
    	private static int SWIPE_MIN_DISTANCE = 70;
    	private static int SWIPE_THRESHOLD_VELOCITY = 10;
    	
    	private ImageView i;
    	
    	public SwingGestureDetection(ImageView i){
    		this.i = i;
    	}
    	
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				GameActivity.direction = GameActivity.RIGHT2LEFT;
//				Log.d("Swing","Right2Left");
				i.performClick();
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				GameActivity.direction = GameActivity.LEFT2RIGHT;
//				Log.d("Swing","Left2Right");
				i.performClick();
			}
			return true;
		}
    }
    
    public static class CardFrontFragment extends Fragment {
    	private GameActivity mGameActivity;
		private GestureDetector gestureDetector;

        public CardFrontFragment() {
        }

        public void set(GameActivity mGameActivity) {
        	this.mGameActivity = mGameActivity;
        }
        
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.game_question, container, false);
        }

        @Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
			
			// Gesture
			LinearLayout l1 = (LinearLayout) mGameActivity.findViewById(R.id.swipe_question);
			ImageView i = (ImageView) mGameActivity.findViewById(R.id.game_image);
			gestureDetector = new GestureDetector(new SwingGestureDetection(i));
			l1.setOnTouchListener(new OnTouchListener() {
		         @Override
		            public boolean onTouch(View v, MotionEvent event) {
		        	 	gestureDetector.onTouchEvent(event);
		                return true;
		         }
		     });
        	
        }
    }

    public static class CardBackFragment extends Fragment {
    	private GameActivity mGameActivity;
		private GestureDetector gestureDetector;

    	public CardBackFragment(){
    	}
    	
        public void set(GameActivity mGameActivity) {
        	this.mGameActivity = mGameActivity;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.game_answer, container, false);
        }
        
        @Override
		public void onActivityCreated(Bundle savedInstanceState) {
			// Auto-generated method stub
			super.onActivityCreated(savedInstanceState);
			
			//TODO answer screen setup

			// prepare logic indicator
			GameLogic.clear();
			
			// view
			LinearLayout l = (LinearLayout) mGameActivity.findViewById(R.id.correct);
			
			// font
			Typeface t = Typeface.createFromAsset(mGameActivity.getAssets(), "fonts/mcfont.ttf");
			
			// correct non-white-char answer
			for(int i = 0; i < GameLogic.getAnswer().size(); i++){
				String button = "correct" + i;
				int buttonID = getResources().getIdentifier(button, "id", mGameActivity.getPackageName());
				Button b = (Button) mGameActivity.findViewById(buttonID);
				if(b == null) Log.d("Error", "Game Activity");
//				b.setText(GameLogic.getAnswer().get(i));
				try{
				b.setText(" ");

				b.setBackgroundResource(R.drawable.button_character_empty);
				b.setTypeface(t, Typeface.BOLD);
				
				if(!GameLogic.getAnswer().get(i).equals(" "))GameLogic.addPlayerAnswer(b);
				else										 b.setVisibility(View.INVISIBLE);
				}catch(Exception e){
				}
			}

			// redundant char
			for(int i = GameLogic.getAnswer().size(); i < 6; i++){
				String button = "correct" + i;
				int buttonID = getResources().getIdentifier(button, "id", mGameActivity.getPackageName());
				Button b = (Button) mGameActivity.findViewById(buttonID);
				if(b.getText().equals("")) l.removeView(b);
			}

			// answer char 
			for(int i = 0; i < 8; i++){
				String button = "answer" + i;
				int buttonID = getResources().getIdentifier(button, "id", mGameActivity.getPackageName());
				Button b = (Button) mGameActivity.findViewById(buttonID);
				b.setText(GameLogic.getAll().get(i));
				
				b.setBackgroundResource(R.drawable.button_character_empty);
				b.setTypeface(t, Typeface.BOLD);
			}
			
			// Gesture
			LinearLayout abc = (LinearLayout) mGameActivity.findViewById(R.id.swipe_answer);
			ImageView i = (ImageView) mGameActivity.findViewById(R.id.game_answer);
			gestureDetector = new GestureDetector(new SwingGestureDetection(i));
			abc.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					gestureDetector.onTouchEvent(event);
					return true;
				}
			});
		}
    }
}
