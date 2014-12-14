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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Link extends Activity{
	private String URL_PATH = "http://1.traversingoceans.sinaapp.com/index.php/api/phd/pa";
	private String URL_PATH_NEW;
	private JSONArray record;
	private String majorchoose;
	
	String university1, university2, university3, university4, university5;
	String link1,link2,link3,link4,link5;
	TextView un1,un2,un3,un4,un5;
	TextView l1,l2,l3,l4,l5;
	  
	
	
	
	//定义Menu菜单选项的ItemId  
    final static int ONE = Menu.FIRST;  
    final static int TWO = Menu.FIRST+1;  
    final static int THREE = Menu.FIRST+2;  
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.link);
		MyApplication.getInstance().addActivity(this);
		
		Button button1 = (Button) findViewById(R.id.button_return);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent visitor_assess = new Intent(Link.this,
						SXX.class);
				Link.this.startActivity(visitor_assess);
			}

		});
		
		
		
	String[] major = { "Business", "Law", "Engineering", "Science", "Medical" };
	final Spinner spinner;
	ArrayAdapter<String> adapter;

	spinner = (Spinner) findViewById(R.id.spinner_major);

	// ����ѡ������ArrayAdapter��������
	adapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, major);

	// ���������б�ķ��
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	// ��adapter ��ӵ�spinner��
	spinner.setAdapter(adapter);
	
	// ����Ĭ��ֵ
	spinner.setVisibility(View.VISIBLE);
	
	un1 = (TextView) findViewById(R.id.textView1);
    un2 = (TextView) findViewById(R.id.textView3);
    un3 = (TextView) findViewById(R.id.textView5);
    un4 = (TextView) findViewById(R.id.textView7);
    un5 = (TextView) findViewById(R.id.textView9);
    l1 = (TextView) findViewById(R.id.textView2);
    l2 = (TextView) findViewById(R.id.textView4);
    l3 = (TextView) findViewById(R.id.textView6);
    l4= (TextView) findViewById(R.id.textView8);
    l5 = (TextView) findViewById(R.id.textView10);
	
	Button button2 = (Button) findViewById(R.id.Button01);
	
	button2.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			majorchoose = spinner.getSelectedItem().toString();
			URL_PATH_NEW = URL_PATH + "/" + majorchoose;
		    new UploadWebpageTask().execute(URL_PATH_NEW);
		  
		    
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
	        	 Intent homepage = new Intent(Link.this,
							Homepage.class);
					Link.this.startActivity(homepage); 
	             break;  
	         case 2:  
	        	 Intent aboutus = new Intent(Link.this,
							AboutUs.class);
					Link.this.startActivity(aboutus); 
	             break;  
	         case 3:  
	        	 Link.this.exitDialog(); 
	             break;
	         }  
	        return super.onOptionsItemSelected(item);
	    }
	    
	public void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(Link.this)
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
				return downloadUrl(URL_PATH_NEW);
			} catch (IOException e) {
				e.printStackTrace();
				return "URL maybe invalid";
			}
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {

			try{
				record = new JSONArray(result);
				university1= record.getJSONObject(0).getString("Name");
				link1 = record.getJSONObject(0).getString("Faculty");
				university2= record.getJSONObject(1).getString("Name");
				link2 = record.getJSONObject(1).getString("Faculty");
				university3= record.getJSONObject(2).getString("Name");
				link3 = record.getJSONObject(2).getString("Faculty");
				university4= record.getJSONObject(3).getString("Name");
				link4 = record.getJSONObject(3).getString("Faculty");
				university5= record.getJSONObject(4).getString("Name");
				link5 = record.getJSONObject(4).getString("Faculty");
				un1.setText(university1);
			    un2.setText(university2);
			    un3.setText(university3);
			    un4.setText(university4);
			    un5.setText(university5);
			    l1.setText(link1);
			    l2.setText(link2);
			    l3.setText(link3);
			    l4.setText(link4);
			    l5.setText(link5);
				
			
				
		} catch (JSONException e) {
			e.printStackTrace();
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
				/*
				 * content.put("GPA", GPA); content.put("TI", TI);
				 * content.put("GRE", GRE); content.put("country", country);
				 */
				content.put("MajorChoose", majorchoose);
				content.put("Success", "OK");
				
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
}
