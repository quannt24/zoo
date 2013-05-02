package vn.hust.zoo.ui;

import uk.co.jasonfry.android.tools.ui.PageControl;
import uk.co.jasonfry.android.tools.ui.SwipeView;
import uk.co.jasonfry.android.tools.ui.SwipeView.OnPageChangedListener;
import vn.hust.zoo.R;
import vn.hust.zoo.logic.Score;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;

public class LevelActivity extends Activity {

    SwipeView mSwipeView;

    LinearLayout[] r = new LinearLayout[3];
    Button b[][] = new Button[3][9];
    ImageView[][] imgStar = new ImageView[3][9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_level);
	
	// Init score
	Score.init();

	PageControl mPageControl = (PageControl) findViewById(R.id.page_control);
	SwipeView mSwipeView = (SwipeView) findViewById(R.id.swipe_view);

	int margin = mSwipeView.setPageWidth(getWindowManager().getDefaultDisplay().getWidth());

	for (int i = 0; i < 3; i++) {
	    FrameLayout frame = new FrameLayout(this);
	    if (i == 0)// first page
	    {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT,
			LinearLayout.LayoutParams.FILL_PARENT);
		params.leftMargin = margin;
		frame.setLayoutParams(params);
	    } else if (i == 2)// last page
	    {
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
			LinearLayout.LayoutParams.FILL_PARENT,
			LinearLayout.LayoutParams.FILL_PARENT);
		params.rightMargin = margin;
		frame.setLayoutParams(params);
	    }

	    mSwipeView.addView(frame);
	}

	// TODO set up each screen
	for (int i = 0; i < 3; i++) {
	    r[i] = new LinearLayout(this);
	    r[i].setGravity(Gravity.CENTER);
	    r[i].setBackgroundResource(R.drawable.menu_background);
	}

	for (int i = 0; i < 3; i++) {
	    TableLayout mTableLayout = new TableLayout(this);
	    TableRow mTableRow[] = new TableRow[6];
	    
	    LayoutParams buttonParams = new TableRow.LayoutParams();
	    buttonParams.setMargins(20, 5, 20, 5);
	    
	    for (int j = 0; j < 6; j++) {
		mTableRow[j] = new TableRow(this);
		mTableRow[j].setGravity(Gravity.CENTER_HORIZONTAL);
		mTableRow[j].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	    }
	    for (int j = 0; j < 9; j++) {
		final int level = i * 9 + j;
		
		b[i][j] = new Button(this);

		b[i][j].setOnClickListener(new View.OnClickListener() {

		    @Override
		    public void onClick(View v) {
			Intent intent = new Intent(LevelActivity.this, GameActivity.class);
			intent.putExtra("level", level);
			startActivity(intent);
		    }
		});
		
		b[i][j].setBackgroundResource(R.drawable.button_level);
		b[i][j].setTextSize(20);
		b[i][j].setTextColor(Color.WHITE);
		b[i][j].setText(String.valueOf(level + 1));

		imgStar[i][j] = new ImageView(this);
		imgStar[i][j].setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		switch (Score.getScore(level)) {
		case 1:
		    imgStar[i][j].setBackgroundResource(R.drawable.star1);
		    break;

		case 2:
		    imgStar[i][j].setBackgroundResource(R.drawable.star2);
		    break;

		case 3:
		    imgStar[i][j].setBackgroundResource(R.drawable.star3);
		    break;

		default:
		    imgStar[i][j].setBackgroundResource(R.drawable.star0);
		    break;
		}
		mTableRow[j / 3 * 2].addView(b[i][j], buttonParams);
		mTableRow[j / 3 * 2 + 1].addView(imgStar[i][j], buttonParams);
	    }

	    for (int j = 0; j < 6; j++) {
		mTableLayout.addView(mTableRow[j]);
	    }

	    r[i].addView(mTableLayout);
	}

	((ViewGroup) mSwipeView.getChildContainer().getChildAt(0)).addView(r[0]);
	((ViewGroup) mSwipeView.getChildContainer().getChildAt(1)).addView(r[1]);
	((ViewGroup) mSwipeView.getChildContainer().getChildAt(2)).addView(r[2]);

	SwipeImageLoader mSwipeImageLoader = new SwipeImageLoader();

	mSwipeView.setOnPageChangedListener(mSwipeImageLoader);
	mSwipeView.setPageControl(mPageControl);
    }

    public void onBack(View v) {
	super.onBackPressed();
    }
    
    private class SwipeImageLoader implements OnPageChangedListener {

	public void onPageChanged(int oldPage, int newPage) {
	    if (mSwipeView == null) return;
	    if (newPage > oldPage) {
		if (newPage != (mSwipeView.getPageCount() - 1))
		    ((ViewGroup) mSwipeView.getChildContainer().getChildAt(newPage + 1))
			    .addView(r[newPage + 1]);
		if (oldPage != 0)
		    ((ViewGroup) mSwipeView.getChildContainer().getChildAt(oldPage - 1))
			    .removeAllViews();
	    } else {
		if (newPage != 0)
		    ((ViewGroup) mSwipeView.getChildContainer().getChildAt(newPage - 1))
			    .addView(r[newPage - 1]);
		if (oldPage != (mSwipeView.getPageCount() - 1))
		    ((ViewGroup) mSwipeView.getChildContainer().getChildAt(oldPage + 1))
			    .removeAllViews();
	    }

	}

    }

}
