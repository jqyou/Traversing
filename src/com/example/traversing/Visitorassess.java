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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class Visitorassess extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.example.traversing.MESSAGE";
	private String URL_PATH = "http://1.traversingoceans.sinaapp.com/index.php/api/visitor";
	private String GPA,GPAstr;
	private String TOEFL,TOEFLstr;
	private String IELTS,IELTSstr;
	private String GRE,GREstr;
	private String country;

	
	//定义Menu菜单选项的ItemId  
    final static int ONE = Menu.FIRST;  
    final static int TWO = Menu.FIRST+1;  
    final static int THREE = Menu.FIRST+2;  

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.visitorassess);
		MyApplication.getInstance().addActivity(this);

		String[] target = { "USA", "UK", "HK&Macau", "Australia", "Singapore" };
		String[] master = { "Master", "PHD" };
		String[] major = { "Business", "Engineering", "Law", "Science",
				"Medical" };

		final Spinner spinner1, spinner2, spinner3;
		ArrayAdapter<String> adapter1, adapter2, adapter3;

		spinner1 = (Spinner) findViewById(R.id.content_country);
	
		// ����ѡ������ArrayAdapter��������
		adapter1 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, target);
		adapter2 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, master);
		adapter3 = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, major);

		// ���������б�ķ��
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		// ��adapter ��ӵ�spinner��
		spinner1.setAdapter(adapter1);
	

		// ����Ĭ��ֵ
		spinner1.setVisibility(View.VISIBLE);
	

		// set OnClick for return
		Button button_return = (Button) findViewById(R.id.button_return);
		button_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Visitorassess.this.finish();
			}

		});

		// set OnClick for search
		Button button_search = (Button) findViewById(R.id.button_search);
		button_search.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				EditText gpa = (EditText) findViewById(R.id.content_gpa);
				GPA = gpa.getText().toString();
				GPAstr=gpa.getText().toString().trim();
				EditText toefl = (EditText) findViewById(R.id.content_toefl);
				TOEFL = toefl.getText().toString();
				TOEFLstr=toefl.getText().toString().trim();
				EditText ielts = (EditText) findViewById(R.id.content_ielts);
				IELTS = ielts.getText().toString();
				IELTSstr=ielts.getText().toString();
				EditText gre = (EditText) findViewById(R.id.content_gre);
				GRE = gre.getText().toString();
				GREstr=gre.getText().toString().trim();
				
				country = spinner1.getSelectedItem().toString();
				
				if(GPAstr == null || GPAstr == ""
						|| GPAstr.length() == 0 || TOEFLstr == null
						|| TOEFLstr == "" || TOEFLstr.length() == 0
						||IELTSstr == null || IELTSstr == ""
						|| IELTSstr.length() == 0 || GREstr == null
						|| GREstr == "" || GREstr.length() == 0)
				{
					Toast.makeText(Visitorassess.this,
							"Please insert all scores of GPA,TOEFL,IELTS,GRE",
							Toast.LENGTH_LONG).show();
				}
				else{

				   new UploadWebpageTask().execute(URL_PATH);}
			}
			
				
			

			
		});
	}
    //�¿���̨�߳�
	private class UploadWebpageTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			try {
				return downloadUrl(URL_PATH);//ʹ�������downloadurl�������ص�string
			} catch (IOException e) {
				e.printStackTrace();
				return "URL maybe invalid";
			}
		}

		// onPostExecute displays the results of the AsyncTask. ���ص����߳�
		@Override
		protected void onPostExecute(String result) {//��һ���������ص�string  json
			Intent rank_name = new Intent(Visitorassess.this, VisitorRank.class);
			rank_name.putExtra(EXTRA_MESSAGE, result);//ʵ��activity֮�����Ϣ���� char����
			Visitorassess.this.startActivity(rank_name);
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
				content.put("GPA", GPA);
				content.put("TOEFL", TOEFL);
				content.put("IELTS", IELTS);
				content.put("GRE", GRE);
				content.put("country", country);
			
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
	        	 Intent homepage = new Intent(Visitorassess.this,
							Homepage.class);
	        	 Visitorassess.this.startActivity(homepage); 
	             break;  
	         case 2:  
	        	 Intent aboutus = new Intent(Visitorassess.this,
							AboutUs.class);
	        	 Visitorassess.this.startActivity(aboutus); 
	             break;  
	         case 3:  
	        	 Visitorassess.this.exitDialog(); 
	             break;
	         }  
	        return super.onOptionsItemSelected(item);
	    }
	    
	public void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(Visitorassess.this)
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
}
