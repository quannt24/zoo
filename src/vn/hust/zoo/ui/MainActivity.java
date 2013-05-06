package vn.hust.zoo.ui;

import vn.hust.zoo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

public class MainActivity extends Activity {
	public static int BUTTON_DELAY = 400;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onPlay(final View v) {
		v.setBackgroundResource(R.drawable.button_play_pressed);
		(new Handler()).postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(MainActivity.this, LevelActivity.class);
				startActivity(intent);
				v.setBackgroundResource(R.drawable.button_play);
//				finish();
			}
		}, MainActivity.BUTTON_DELAY);
	}

	public void onHelp(final View v) {
		v.setBackgroundResource(R.drawable.button_help_pressed);
		(new Handler()).postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(MainActivity.this, HelpActivity.class);
				startActivity(intent);
				v.setBackgroundResource(R.drawable.button_help);
			}
		}, MainActivity.BUTTON_DELAY);
	}

	public void onExit(final View v) {
		v.setBackgroundResource(R.drawable.button_exit_pressed);
		(new Handler()).postDelayed(new Runnable() {
			@Override
			public void run() {
				onBackPressed();
				v.setBackgroundResource(R.drawable.button_exit);
			}
		}, MainActivity.BUTTON_DELAY);
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		AlertDialog alertDialog = new AlertDialog.Builder(this)
				.setTitle(R.string.confirm)
				.setMessage(R.string.menu_confirm)
				.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						System.exit(0);
					}
				})
				.setNegativeButton("Hủy",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).create();

		alertDialog.show();
	}
}