package com.example.traversing;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.traversing.R;



import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class Homepage extends Activity {
	
	private NameStore usernamestore;//设置全局变量来查看是否有用户登陆
	private String username;
	private String URL_PATH = "http://1.traversingoceans.sinaapp.com/index.php/api/homepage/new";
	private JSONArray record;
	//private SimpleAdapter adapter;
	public final static String EXTRA_MESSAGE = "com.example.traversing.MESSAGE";

	//定义Menu菜单选项的ItemId  
    final static int ONE = Menu.FIRST;  
    final static int TWO = Menu.FIRST+1;  
    final static int THREE = Menu.FIRST+2;  


	// private SimpleAdapter adapter;
	private List<Hashtable<String, Object>> data;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);
		MyApplication.getInstance().addActivity(this);
		
	
		
		usernamestore = (NameStore) getApplication();
	    username=usernamestore.getText();
	    Button button1 = (Button) findViewById(R.id.button_register);
	    Button button2 = (Button) findViewById(R.id.button_login);
		Button assess = (Button) findViewById(R.id.button_assess);
		Button exit = (Button) findViewById(R.id.button_exit);
		TextView user_name = (TextView) findViewById(R.id.textView1);
	    if(username.equals("")||username.equals("empty")){
	    	
			button1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(Homepage.this, Register.class);
					Homepage.this.startActivity(intent);
				}

			});

			
			button2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent();
					intent.setClass(Homepage.this, Login.class);
					Homepage.this.startActivity(intent);
				}

			});

			// set OnClick for VisitorAssess
		
			assess.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent visitor_assess = new Intent(Homepage.this,
							Visitorassess.class);
					Homepage.this.startActivity(visitor_assess);
				}

			});
			user_name.setVisibility(View.INVISIBLE);
			exit.setVisibility(View.INVISIBLE);
			
	    }
	    else
	    {
	    	button1.setVisibility(View.INVISIBLE);
	    	button2.setVisibility(View.INVISIBLE);
	    	//assess.setVisibility(View.INVISIBLE);
	    	// set OnClick for VisitorAssess
			
			assess.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Intent visitor_assess = new Intent(Homepage.this,
								SXX.class);
						Homepage.this.startActivity(visitor_assess);
					}

				});
	    
			user_name.setText(username);
	    }
		
	    exit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
			
				Homepage.this.exitDialog();
			
			}

		});
		
		
		
	
			
	
			/*ListView result_list = (ListView) findViewById(R.id.list_news);
			data = getData();
			MyAdapter adapter = new MyAdapter(this);
			result_list.setAdapter(adapter);
			result_list.setOnItemClickListener(listener);*/
		new UploadWebpageTask().execute(URL_PATH);
			
		
			
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
				MyAdapter adapter = new MyAdapter(Homepage.this);
				/*adapter = new SimpleAdapter(Homepage.this,getData(),
						R.layout.listitem_name, new String[] { "news" },
						new int[] { R.id.names });*/
				data=getData();
				ListView result_list = (ListView) findViewById(R.id.list_news);
				result_list.setAdapter(adapter);
				result_list.setOnItemClickListener(listener);
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
			conn.setRequestMethod("GET");
			//conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(10000);
			conn.setReadTimeout(10000);
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("charset", "utf-8");
			conn.setRequestProperty("Content-Type",
					"application/x-www-form-urlencoded");

			conn.connect();

		/*	DataOutputStream out = new DataOutputStream(conn.getOutputStream());

			JSONObject content = new JSONObject();


			out.write(content.toString().getBytes());
			out.flush();
			out.close();*/

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
	        	 Intent homepage = new Intent(Homepage.this,
							Homepage.class);
					Homepage.this.startActivity(homepage); 
	             break;   
	         case 2:  
	        	 Intent aboutus = new Intent(Homepage.this,
							AboutUs.class);
					Homepage.this.startActivity(aboutus); 
	             break;  
	         case 3:  
	        	 Homepage.this.exitDialog(); 
	             break;
	         }  
	        return super.onOptionsItemSelected(item);
	    }
	    
	public void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(Homepage.this)
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
	

	// ʵ��item�ĵ��
	public OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			/*Toast.makeText(Homepage.this,
					(String) data.get(position).get("news"), Toast.LENGTH_LONG)
					.show();*/
			Intent news_name = new Intent(Homepage.this, NewContent.class);
			news_name.putExtra(EXTRA_MESSAGE, (String) data.get(position).get("news"));//ʵ��activity֮�����Ϣ���� char����
			Homepage.this.startActivity(news_name);
		}
	};

	// ���������ṹ
/*	private List<Hashtable<String, Object>> getData() {
		List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
		for (int i = 0; i < news.length; i++) {
			Hashtable<String, Object> table = new Hashtable<String, Object>();

			table.put("news", news[i]);
			list.add(table);
		}
		return list;
	}*/
	private List<Hashtable<String, Object>> getData() {
		List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
		for (int i = 0; i < record.length(); i++) {
			Hashtable<String, Object> table = new Hashtable<String, Object>();

			try {
				table.put("news", record.getJSONObject(i).getString("Title"));//��һ��name��listview�е�key��value�ṹ �ڶ���name��jsonobject�е�name
			} catch (JSONException e) {
				e.printStackTrace();
			}
			list.add(table);
		}
		return list;
	}

	// ʵ��button�ĵ��
	public final class ViewHolder {
		public TextView news;
		public ImageButton button;
	}

	public class MyAdapter extends BaseAdapter {
		private LayoutInflater inflater;

		public MyAdapter(Context context) {
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.listitem, null);
				holder.news = (TextView) convertView.findViewById(R.id.news);
				holder.button = (ImageButton) convertView
						.findViewById(R.id.like);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.news.setText((String) data.get(position).get("news"));
			holder.button.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					((ImageButton) v).setImageResource(R.drawable.icon2);
				}
			});

			return convertView;
		}
	}
}
