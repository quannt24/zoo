package vn.hust.zoo.ui;

import vn.hust.zoo.R;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

public class HelpActivity extends Activity implements FragmentManager.OnBackStackChangedListener {
	public static boolean touchable = true;
	
	private boolean mShowingBack = false;
	public int direction = GameActivity.RIGHT2LEFT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_card_flip);

		if (savedInstanceState == null) {
			HelpFrontFragment c = new HelpFrontFragment();
			c.set(this);
			getFragmentManager().beginTransaction().add(R.id.container, c).commit();
		} else {
			mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
		}

		getFragmentManager().addOnBackStackChangedListener(this);
	}

	public void onBack(final View v){
		if(!HelpActivity.touchable) return;
		else HelpActivity.touchable = false;
		v.setBackgroundResource(R.drawable.button_back_pressed);
    	(new Handler()).postDelayed(new Runnable() {
			@Override
			public void run() {
				HelpActivity.super.onBackPressed();
				HelpActivity.touchable = true;
			}
		}, MainActivity.BUTTON_DELAY);
	}
	
	public void onSwipe(View v) {
		switch (v.getId()) {
		case R.id.help_back:
		case R.id.help_front:
			flipCard();
			break;
		}
	}

	private void flipCard() {
		Object fragment;

		mShowingBack = !mShowingBack; // track

		// Create card side
		if (mShowingBack) {
			fragment = new HelpBackFragment();
			((HelpBackFragment) fragment).set(this);
		} else {
			fragment = new HelpFrontFragment();
			((HelpFrontFragment) fragment).set(this);
		}

		// Create animation flip card
		if (direction == GameActivity.LEFT2RIGHT) {
			getFragmentManager()
					.beginTransaction()
					.setCustomAnimations(R.animator.card_flip_left_in,
							R.animator.card_flip_left_out, 0, 0)
					.replace(R.id.container, (Fragment) fragment).commit();
		} else if (direction == GameActivity.RIGHT2LEFT) {
			getFragmentManager()
					.beginTransaction()
					.setCustomAnimations(R.animator.card_flip_right_in,
							R.animator.card_flip_right_out, 0, 0)
					.replace(R.id.container, (Fragment) fragment).commit();
		}
	}

	@Override
	public void onBackStackChanged() {
		mShowingBack = (getFragmentManager().getBackStackEntryCount() > 0);
	}

	public static class SwingGestureDetection extends SimpleOnGestureListener {
		private static int SWIPE_MIN_DISTANCE = 70;
		private static int SWIPE_THRESHOLD_VELOCITY = 10;

		private Button i;
		private HelpActivity mActivity;

		public SwingGestureDetection(HelpActivity mActivity, Button i) {
			this.i = i;
			this.mActivity = mActivity;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				mActivity.direction = GameActivity.RIGHT2LEFT;
				Log.d("Swing", "Right2Left");
				i.performClick();
			} else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE
					&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
				mActivity.direction = GameActivity.LEFT2RIGHT;
				Log.d("Swing", "Left2Right");
				i.performClick();
			}
			return true;
		}
	}

	public static class HelpFrontFragment extends Fragment {
		private HelpActivity helpActivity;
		private GestureDetector gestureDetector;

		public HelpFrontFragment() {
		}

		public void set(HelpActivity helpActivity) {
			this.helpActivity = helpActivity;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			return inflater.inflate(R.layout.help_front, container, false);
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			// Gesture
			FrameLayout l1 = (FrameLayout) helpActivity.findViewById(R.id.help1);
			Button i = (Button) helpActivity.findViewById(R.id.help_front);
			gestureDetector = new GestureDetector(new SwingGestureDetection(helpActivity, i));
			l1.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					gestureDetector.onTouchEvent(event);
					return true;
				}
			});

		}
	}

	public static class HelpBackFragment extends Fragment {
		private HelpActivity helpActivity;
		private GestureDetector gestureDetector;

		public HelpBackFragment() {
		}

		public void set(HelpActivity helpActivity) {
			this.helpActivity = helpActivity;
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			return inflater.inflate(R.layout.help_back, container, false);
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);

			// Gesture
			FrameLayout l1 = (FrameLayout) helpActivity.findViewById(R.id.help2);
			Button i = (Button) helpActivity.findViewById(R.id.help_back);
			gestureDetector = new GestureDetector(new SwingGestureDetection(helpActivity, i));
			l1.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					gestureDetector.onTouchEvent(event);
					return true;
				}
			});

		}
	}
}