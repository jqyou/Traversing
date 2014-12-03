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
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class Visitorassess extends Activity {
	
	public final static String EXTRA_MESSAGE = "com.example.traversing.MESSAGE";
	private String URL_PATH = "http://1.traversingoceans.sinaapp.com/index.php/api/visitor";
	private String GPA;
	private String TOEFL = "0";
	private String IELTS = "0";
	private String GRE = "0";
	private String country;
	private String type;
	private String majors;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.visitorassess);

		String[] target = { "USA", "UK", "HK&Macau", "Australia", "Singapore" };
		String[] master = { "Master", "PHD" };
		String[] major = { "Business", "Engineering", "Law", "Science",
				"Medical" };

		final Spinner spinner1, spinner2, spinner3;
		ArrayAdapter<String> adapter1, adapter2, adapter3;

		spinner1 = (Spinner) findViewById(R.id.content_country);
		spinner2 = (Spinner) findViewById(R.id.content_type);
		spinner3 = (Spinner) findViewById(R.id.content_major);

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
		spinner2.setAdapter(adapter2);
		spinner3.setAdapter(adapter3);

		// ����Ĭ��ֵ
		spinner1.setVisibility(View.VISIBLE);
		spinner2.setVisibility(View.VISIBLE);
		spinner3.setVisibility(View.VISIBLE);

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
				EditText toefl = (EditText) findViewById(R.id.content_toefl);
				TOEFL = toefl.getText().toString();
				EditText ielts = (EditText) findViewById(R.id.content_ielts);
				IELTS = ielts.getText().toString();
			
				EditText gre = (EditText) findViewById(R.id.content_gre);
				GRE = gre.getText().toString();
			
			

				country = spinner1.getSelectedItem().toString();
				type = spinner2.getSelectedItem().toString();
				majors = spinner3.getSelectedItem().toString();

				new UploadWebpageTask().execute(URL_PATH);
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
				content.put("type", type);
				content.put("majors", majors);
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
