package com.example.traversing;



import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

	

		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(MainActivity.this, Homepage.class);
				MainActivity.this.startActivity(intent);
				MainActivity.this.finish();
			}
		}, 2000);
	}
		
		
		
	
	
}
