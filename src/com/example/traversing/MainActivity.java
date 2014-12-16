package com.example.traversing;





import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;




public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		MyApplication.getInstance().addActivity(this);
		//PushManager.startWork(getApplicationContext(),PushConstants.LOGIN_TYPE_API_KEY, "8i2DTPU9DoE1KXKdyVMkmsb3");
		   PushManager.startWork(getApplicationContext(),
	                PushConstants.LOGIN_TYPE_API_KEY,
	                Utils.getMetaValue(MainActivity.this, "api_key"));

	

		new Handler().postDelayed(new Runnable() {
			public void run() {
			
				Intent intent = new Intent(MainActivity.this, Homepage.class);
				MainActivity.this.startActivity(intent);
				MainActivity.this.finish();
			}
		}, 2000);
	}
		
	
		
	
	
}
