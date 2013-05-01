package vn.hust.zoo.ui;

import vn.hust.zoo.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class GameActivity extends Activity  implements FragmentManager.OnBackStackChangedListener {
    private boolean mShowingBack = false;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.game_question);
        setContentView(R.layout.activity_card_flip);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction().add(R.id.container, new CardFrontFragment()).commit();
		} else {
			mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
		}

		getFragmentManager().addOnBackStackChangedListener(this);
	}
	
	public void onAnswer(View v){
		Log.d("Alphabet","" + ((Button)v).getText());
		((Button)v).setText("C");
	}
	
	public void onSwitch(View v){
		switch(v.getId()){
		case R.id.game_question: 
		case R.id.game_Image:
			//TODO switch to answer
			Log.d("Flip", "1"); 
			flipCard(); 
			break;
		case R.id.game_answer:   
			//TODO switch to next question
			Log.d("Flip", "2"); 
			mShowingBack = true;
			flipCard(); 
			break;
		}
	}
	
	private void flipCard() {
//        if (mShowingBack) {
//            getFragmentManager().popBackStack();
//            return;
//        }
//
//        mShowingBack = true;
		mShowingBack = !mShowingBack; // track
		if(mShowingBack)
        getFragmentManager()
                .beginTransaction()
                .setCustomAnimations(
                        R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                        R.animator.card_flip_left_in, R.animator.card_flip_left_out)
                .replace(R.id.container, new CardBackFragment())
                .commit();
		else
			   getFragmentManager()
               .beginTransaction()
               .setCustomAnimations(
                       R.animator.card_flip_right_in, R.animator.card_flip_right_out,
                       R.animator.card_flip_left_in, R.animator.card_flip_left_out)
               .replace(R.id.container, new CardFrontFragment())
               .commit();
    }

    @Override
    public void onBackStackChanged() {
        mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
    }

    public static class CardFrontFragment extends Fragment {
        public CardFrontFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.game_question, container, false);
        }
    }

    public static class CardBackFragment extends Fragment {
        public CardBackFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            return inflater.inflate(R.layout.game_answer, container, false);
        }
    }
}
