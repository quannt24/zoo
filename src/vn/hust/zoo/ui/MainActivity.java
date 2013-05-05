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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onPlay(View v) {
		v.setBackgroundResource(R.drawable.button_play_pressed);
		(new Handler()).postDelayed(new Runnable() {
			@Override
			public void run() {
				Intent intent = new Intent(MainActivity.this, LevelActivity.class);
				startActivity(intent);
				finish();
			}
		}, 800);
	}

	public void onHelp(View v) {
	}

	public void onExit(View v) {
	}

	@Override
	public void onBackPressed() {
		// super.onBackPressed();
		AlertDialog alertDialog = new AlertDialog.Builder(this)
				.setTitle(R.string.confirm)
				.setMessage(R.string.menu_confirm)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						System.exit(0);
					}
				})
				.setNegativeButton("Bỏ",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).create();

		alertDialog.show();
	}
}