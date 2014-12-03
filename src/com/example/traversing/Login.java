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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends Activity {
	private String URL_PATH = "http://1.traversingoceans.sinaapp.com/index.php/api/login/lr";
	private String URL_PATH_NEW;
	private String username;
	private String userpwd;
	private JSONArray record;
//	private Storage storage;



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.login);

		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// Intent intent = new Intent();
				EditText UserName = (EditText) findViewById(R.id.editText2);
				username = UserName.getText().toString();
				EditText UserPwd = (EditText) findViewById(R.id.editText1);
				userpwd = UserPwd.getText().toString();
				URL_PATH_NEW = URL_PATH + "/" + username + "/" + userpwd;
				new UploadWebpageTask().execute(URL_PATH_NEW);
			}

		});

		Button button2 = (Button) findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Login.this, Homepage.class);
				Login.this.startActivity(intent);
			}

		});
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

			try {
				record = new JSONArray(result);
				username = record.getJSONObject(0).getString("UserName");
				userpwd = record.getJSONObject(0).getString("UserPwd");
				if (username.equals("empty")) {
					Toast.makeText(getApplicationContext(),
							"UserName or Password is not Right!",
							Toast.LENGTH_SHORT).show();

				} else {
					Intent intent = new Intent();
					intent.setClass(Login.this, SXX.class);
					Login.this.startActivity(intent);
				//	storage = (Storage) getApplication();
				//	storage.set_user_name(username);
					Toast.makeText(getApplicationContext(), "Login success!",
							Toast.LENGTH_SHORT).show();

				}
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
				/*
				 * content.put("GPA", GPA); content.put("TI", TI);
				 * content.put("GRE", GRE); content.put("country", country);
				 */
				content.put("UserName", username);
				content.put("UserPwd", userpwd);
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
