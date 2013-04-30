package vn.hust.zoo.ui;

import vn.hust.zoo.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.activity_main);
    }

    public void onPlay(View v) {
	Intent intent = new Intent(this, LevelActivity.class);
	startActivity(intent);
    }

    public void onInfo(View v) {
	AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(R.string.app_name)
		.setMessage(R.string.author)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		    }
		}).create();
	alertDialog.show();
    }

    @Override
    public void onBackPressed() {
	// super.onBackPressed();
	AlertDialog alertDialog = new AlertDialog.Builder(this).setTitle(R.string.confirm)
		.setMessage(R.string.menu_confirm)
		.setPositiveButton("OK", new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
			System.exit(0);
		    }
		}).setNegativeButton("Cancle", new DialogInterface.OnClickListener() {

		    public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		    }
		}).create();

	alertDialog.show();
    }
}