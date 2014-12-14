package com.example.traversing;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SXX extends Activity{
	private NameStore usernamestore;
	//定义Menu菜单选项的ItemId  
    final static int ONE = Menu.FIRST;  
    final static int TWO = Menu.FIRST+1;  
    final static int THREE = Menu.FIRST+2;  
    private String URL_PATH = "http://1.traversingoceans.sinaapp.com/index.php/api/lastrank/lr";
    private String URL_PATH_NEW;
    public final static String EXTRA_MESSAGE = "com.example.traversing.MESSAGE";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		MyApplication.getInstance().addActivity(this);
		
		setContentView(R.layout.sunxiaoxuan);
		usernamestore = (NameStore) getApplication();
		TextView user_name = (TextView) findViewById(R.id.textView1);
		user_name.setText(usernamestore.getText());
	/*	String username="";
		username=this.getIntent().getStringExtra("Name");
		TextView user_name=(TextView) findViewById(R.id.textView1);
		user_name.setText(username);*/

		Button button1 = (Button) findViewById(R.id.button_master);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SXX.this, Masterrank.class);
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
				/*Intent intent = new Intent();
				intent.setClass(SXX.this, LastRank.class);
				SXX.this.startActivity(intent);*/
				/*EditText UserName = (EditText) findViewById(R.id.editText2);
				username = UserName.getText().toString();
				EditText UserPwd = (EditText) findViewById(R.id.editText1);
				userpwd = UserPwd.getText().toString();*/
				URL_PATH_NEW = URL_PATH + "/" + usernamestore.getText() ;
				new UploadWebpageTask().execute(URL_PATH_NEW);
			}

		});
		
	/*	Button button4 = (Button) findViewById(R.id.button_exit);
		button4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				System.exit(0);
				
			}

		});*/
		
		Button button5 = (Button) findViewById(R.id.button_back);
		button5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(SXX.this, Homepage.class);
				SXX.this.startActivity(intent);
			}

		});
	}
	
	 public boolean onCreateOptionsMenu(Menu menu) {
	        // Inflate the menu; this adds items to the action bar if it is present.
	      //  getMenuInflater().inflate(R.menu.main, menu);
	    	menu.add(0, ONE, 0, "Home Page");
	        menu.add(0, TWO, 1, "About Us");  
	        menu.add(0, THREE, 2, "Exit");  
	        return super.onCreateOptionsMenu(menu); 
	    
	    }

	    
	    public boolean onOptionsItemSelected(MenuItem item) {
	        // Handle action bar item clicks here. The action bar will
	        // automatically handle clicks on the Home/Up button, so long
	        // as you specify a parent activity in AndroidManifest.xml.
	    	 switch(item.getItemId()){  
	         case 1:  
	        	 Intent homepage = new Intent(SXX.this,
							Homepage.class);
					SXX.this.startActivity(homepage); 
	             break;  
	         case 2:  
	        	 Intent aboutus = new Intent(SXX.this,
							AboutUs.class);
					SXX.this.startActivity(aboutus); 
	             break;  
	         case 3:  
	        	 SXX.this.exitDialog(); 
	             break;
	         }  
	        return super.onOptionsItemSelected(item);
	    }
	    
	public void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(SXX.this)
				.setTitle("Leave?").setMessage("Are You Sure to Exit?")
				.setPositiveButton("Sure", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						// 确定的话就表示退出，此时我们结束我们程序
						// 使用我们Activity提供的finish方法
						MyApplication.getInstance().exit();
					//	Homepage.this.finish();// 操作结束
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					}
				}).create();
		dialog.show();
	}
	
	private class UploadWebpageTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			try {
				return downloadUrl(URL_PATH_NEW);//ʹ�������downloadurl�������ص�string
			} catch (IOException e) {
				e.printStackTrace();
				return "URL maybe invalid";
			}
		}

		// onPostExecute displays the results of the AsyncTask. ���ص����߳�
		@Override
		protected void onPostExecute(String result) {//��һ���������ص�string  json
			Intent lastrank = new Intent(SXX.this, LastRank.class);
			lastrank.putExtra(EXTRA_MESSAGE, result);//ʵ��activity֮�����Ϣ���� char����
			SXX.this.startActivity(lastrank);
		}
	}

	private String downloadUrl(String myurl) throws IOException {//����һ��downurl����
		InputStream is = null;//����input

		int len = 5000;

		try {
			URL url = new URL(myurl);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");//���ò���

			conn.connect();

			DataOutputStream out = new DataOutputStream(conn.getOutputStream());//out ������ data��output������

			JSONObject content = new JSONObject();

			try {
				content.put("UserName",usernamestore.getText());
				
			
			} catch (JSONException e) {
				e.printStackTrace();
			}

			out.write(content.toString().getBytes());// ת��string ���ֽ�������
			out.flush();//�������Ƴ�
			out.close();

			is = conn.getInputStream();
			String contentAsString = readIt(is, len);
			conn.disconnect();
			return contentAsString;

		} finally {
			if (is != null) {
				is.close();
			}
		}

	}

	public String readIt(InputStream stream, int len) throws IOException,//�������Ļ�����
			UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];//ת����string
		reader.read(buffer);
		return new String(buffer);
	}

}
