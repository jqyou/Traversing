package com.example.traversing;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register extends Activity {
	private String URL_PATH = "http://1.traversingoceans.sinaapp.com/index.php/api/register/rg";
	// private String URL_PATH_NEW;
	private String username, usernamestr;
	private String userpwd, userpwdstr;
	private String useremail;
	private JSONArray record;
	private String success;

	// 定义Menu菜单选项的ItemId
	final static int ONE = Menu.FIRST;
	final static int TWO = Menu.FIRST + 1;
	final static int THREE = Menu.FIRST + 2;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		MyApplication.getInstance().addActivity(this);

		setContentView(R.layout.register);

		Button button1 = (Button) findViewById(R.id.button1);

		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*
				 * Intent intent = new Intent(); intent.setClass(Register.this,
				 * Masterrank.class); Register.this.startActivity(intent);
				 */
				EditText UserName = (EditText) findViewById(R.id.editText4);
				username = UserName.getText().toString();
				usernamestr = UserName.getText().toString().trim();

				EditText UserEmail = (EditText) findViewById(R.id.editTextemail);
				useremail = UserEmail.getText().toString();

				EditText UserPwd = (EditText) findViewById(R.id.editTextpassword);
				userpwd = UserPwd.getText().toString();
				userpwdstr = UserPwd.getText().toString().trim();

				boolean isEmail = isEmail(useremail);
				if (usernamestr == null || usernamestr == ""
						|| usernamestr.length() == 0) {

					Toast.makeText(getApplicationContext(),
							"Must input username!", Toast.LENGTH_SHORT).show();

				} else if (username.equals("empty")) {

					Toast.makeText(getApplicationContext(),
							"Do not use the username 'empty'",
							Toast.LENGTH_SHORT).show();

				} else if (!isEmail) {
					Toast.makeText(getApplicationContext(),
							"The format of Email is not right!",
							Toast.LENGTH_SHORT).show();
				} else if (userpwdstr == null || userpwdstr == ""
						|| userpwdstr.length() == 0) {
					Toast.makeText(getApplicationContext(),
							"Must input password!", Toast.LENGTH_SHORT).show();
				}

				// URL_PATH_NEW = URL_PATH + "/" + username + "/" +
				// userpwd+"/"+useremail;
				else {

					new UploadWebpageTask().execute(URL_PATH);
				}
			}

		});
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
				success = record.getJSONObject(0).getString("Success");

				if (success.equals("false")) {
					Toast.makeText(getApplicationContext(),
							"UserName is exsited!", Toast.LENGTH_SHORT).show();

				} else {

					// storage = (Storage) getApplication();
					// storage.set_user_name(username);
					Toast.makeText(getApplicationContext(),
							"Register success!", Toast.LENGTH_SHORT).show();
					Intent intent = new Intent();
					intent.setClass(Register.this, Login.class);
					Register.this.startActivity(intent);

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
				content.put("Name", username);
				content.put("Password", userpwd);
				content.put("Email", useremail);
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

	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		menu.add(0, ONE, 0, "Home Page");
		menu.add(0, TWO, 1, "About Us");
		menu.add(0, THREE, 2, "Exit");
		return super.onCreateOptionsMenu(menu);

	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		switch (item.getItemId()) {
		case 1:
			Intent homepage = new Intent(Register.this, Homepage.class);
			Register.this.startActivity(homepage);
			break;
		case 2:
			Intent aboutus = new Intent(Register.this, AboutUs.class);
			Register.this.startActivity(aboutus);
			break;
		case 3:
			Register.this.exitDialog();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(Register.this)
				.setTitle("Leave?")
				.setMessage("Are You Sure to Exit?")
				.setPositiveButton("Sure",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// 确定的话就表示退出，此时我们结束我们程序
								// 使用我们Activity提供的finish方法
								MyApplication.getInstance().exit();
								// Homepage.this.finish();// 操作结束
							}
						})
				.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
							}
						}).create();
		dialog.show();
	}

	// 判断email格式是否正确
	public boolean isEmail(String email) {
		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
		Pattern p = Pattern.compile(str);
		Matcher m = p.matcher(email);

		return m.matches();
	}

}
