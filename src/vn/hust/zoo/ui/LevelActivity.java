package vn.hust.zoo.ui;

import uk.co.jasonfry.android.tools.ui.PageControl;
import uk.co.jasonfry.android.tools.ui.SwipeView;
import uk.co.jasonfry.android.tools.ui.SwipeView.OnPageChangedListener;
import vn.hust.zoo.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

public class LevelActivity extends Activity {

    SwipeView mSwipeView;

    LinearLayout[] r = new LinearLayout[3];
    Button b[][] = new Button[3][9];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_level);

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
	    TableRow mTableRow[] = new TableRow[3];
	    for (int j = 0; j < 9; j++) {
		if (j % 3 == 0) {
		    mTableRow[j / 3] = new TableRow(this);
		    mTableRow[j / 3].setGravity(Gravity.CENTER_HORIZONTAL);
		}
		b[i][j] = new Button(this);

		final int level = i * 9 + j;

		b[i][j].setOnClickListener(new View.OnClickListener() {

		    @Override
		    public void onClick(View v) {
			// TODO choose stage
			Log.d("Button", "Press" + level);
		    }
		});
		b[i][j].setBackgroundResource(R.drawable.menu_lock);

		mTableRow[j / 3].addView(b[i][j]);
	    }

	    mTableLayout.addView(mTableRow[0]);
	    mTableLayout.addView(mTableRow[1]);
	    mTableLayout.addView(mTableRow[2]);

	    r[i].addView(mTableLayout);
	}

	((ViewGroup) mSwipeView.getChildContainer().getChildAt(0)).addView(r[0]);
	((ViewGroup) mSwipeView.getChildContainer().getChildAt(1)).addView(r[1]);
	((ViewGroup) mSwipeView.getChildContainer().getChildAt(2)).addView(r[2]);

	SwipeImageLoader mSwipeImageLoader = new SwipeImageLoader();

	mSwipeView.setOnPageChangedListener(mSwipeImageLoader);
	mSwipeView.setPageControl(mPageControl);
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
