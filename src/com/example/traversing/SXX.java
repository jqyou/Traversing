package com.example.traversing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class SXX extends Activity{
//	private Storage storage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.sunxiaoxuan);
	/*	storage = (Storage) getApplication();
		TextView user_name = (TextView) findViewById(R.id.textView1);
		user_name.setText(storage.get_user_name());*/

		Button button1 = (Button) findViewById(R.id.button_master);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SXX.this, RankUser.class);
				SXX.this.startActivity(intent);
			}

		});

		Button button2 = (Button) findViewById(R.id.button_phd);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SXX.this, Link.class);
				SXX.this.startActivity(intent);
			}

		});

		Button button3 = (Button) findViewById(R.id.button_rank);
		button3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SXX.this, RankUser.class);
				SXX.this.startActivity(intent);
			}

		});
		
		Button button4 = (Button) findViewById(R.id.button_exit);
		button4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SXX.this, Homepage.class);
				SXX.this.startActivity(intent);
			}

		});
	}

}
