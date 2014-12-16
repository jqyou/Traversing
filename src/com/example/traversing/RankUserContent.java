package com.example.traversing;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
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
import android.widget.TextView;
import android.widget.Toast;

public class RankUserContent extends Activity{
	private String URL_PATH = "http://1.traversingoceans.sinaapp.com/index.php/api/master/content";
	private JSONArray record;
	//定义Menu菜单选项的ItemId  
    final static int ONE = Menu.FIRST;  
    final static int TWO = Menu.FIRST+1;  
    final static int THREE = Menu.FIRST+2; 
    public String result;
    
    
	public void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.lastrankcontent);
	MyApplication.getInstance().addActivity(this);

	Intent intent = getIntent();
	result = intent.getStringExtra(RankUser.EXTRA_MESSAGE);
	/*TextView un = (TextView) findViewById(R.id.textView8);
	TextView cn = (TextView) findViewById(R.id.textView9);
	TextView rf = (TextView) findViewById(R.id.textView10);
	TextView tr = (TextView) findViewById(R.id.textView11);*/

	
	
	
	new UploadWebpageTask().execute(URL_PATH);

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
	        	 Intent homepage = new Intent(RankUserContent.this,
							Homepage.class);
	        	 RankUserContent.this.startActivity(homepage); 
	             break;  
	         case 2:  
	        	 Intent aboutus = new Intent(RankUserContent.this,
							AboutUs.class);
	        	 RankUserContent.this.startActivity(aboutus); 
	             break;  
	         case 3:  
	        	 RankUserContent.this.exitDialog(); 
	             break;
	         }  
	        return super.onOptionsItemSelected(item);
	    }
	    
	public void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(RankUserContent.this)
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
				return downloadUrl(URL_PATH);
			} catch (IOException e) {
				e.printStackTrace();
				return "URL maybe invalid";
			}
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {

			try {
				record = new JSONArray(result);
				String universityname = record.getJSONObject(0).getString("Name");
				String country = record.getJSONObject(0).getString("Country");
				String rainfall = record.getJSONObject(0).getString("Rainfall");
				String terrain = record.getJSONObject(0).getString("Terrain");
				String district = record.getJSONObject(0).getString("District");
				TextView un = (TextView) findViewById(R.id.textView2);
				TextView cn = (TextView) findViewById(R.id.textView4);
				TextView rf = (TextView) findViewById(R.id.textView5);
				TextView tr = (TextView) findViewById(R.id.textView6);
				TextView ds = (TextView) findViewById(R.id.textView7);
				un.setText(universityname);
				cn.setText(country);
				rf.setText(rainfall);
				tr.setText(terrain);
				ds.setText(district);
				

				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private String downloadUrl(String myurl) throws IOException {
		InputStream is = null;

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
					"application/x-www-form-urlencoded");

			conn.connect();

			DataOutputStream out = new DataOutputStream(conn.getOutputStream());

			JSONObject content = new JSONObject();

			try {
				content.put("UniversityName", result);
			
			} catch (JSONException e) {
				e.printStackTrace();
			}

			out.write(content.toString().getBytes());
			out.flush();
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

	public String readIt(InputStream stream, int len) throws IOException,
			UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];
		reader.read(buffer);
		return new String(buffer);
	}

}
