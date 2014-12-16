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

import com.example.traversing.Homepage.ViewHolder;



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
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class LastRank extends Activity {
	private NameStore usernamestore;
	//定义Menu菜单选项的ItemId  
    final static int ONE = Menu.FIRST;  
    final static int TWO = Menu.FIRST+1;  
    final static int THREE = Menu.FIRST+2; 
    

	private SimpleAdapter adapter;
	private JSONArray rank;
	private String universityname;
	




	public final static String EXTRA_MESSAGE = "com.example.traversing.MESSAGE";



	// private SimpleAdapter adapter;
	private List<Hashtable<String, Object>> data;
	
	
   
    
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyApplication.getInstance().addActivity(this);
		setContentView(R.layout.lastrank);
		usernamestore = (NameStore) getApplication();
		TextView user_name = (TextView) findViewById(R.id.textView1);
		user_name.setText(usernamestore.getText());
		
		Intent intent = getIntent();
		String result = intent.getStringExtra(SXX.EXTRA_MESSAGE);//������string
		
		try {
			rank = new JSONArray(result);
			universityname = rank.getJSONObject(0).getString("Name");

			if (universityname.equals("empty")) {
				Toast.makeText(getApplicationContext(),
						"You have not made assess before,Or you do not have university to match",
						Toast.LENGTH_SHORT).show();

			}
			else{
			ListView result_list = (ListView) findViewById(R.id.list_name);
			data=getData();
			MyAdapter adapter = new MyAdapter(this);
			result_list.setAdapter(adapter);
			result_list.setOnItemClickListener(listener);
			/*adapter = new SimpleAdapter(LastRank.this,getData(),
					R.layout.listitem_name, new String[] { "universityname" },
					new int[] { R.id.names });
			
			result_list.setAdapter(adapter);*/
			//result_list.setOnItemClickListener(listener);
			}

		} catch (JSONException e) {
			e.printStackTrace();
		}
	
		Button button1 = (Button) findViewById(R.id.button_return);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(LastRank.this, SXX.class);
				LastRank.this.startActivity(intent);
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
	        	 Intent homepage = new Intent(LastRank.this,
							Homepage.class);
					LastRank.this.startActivity(homepage); 
	             break;  
	         case 2:  
	        	 Intent aboutus = new Intent(LastRank.this,
							AboutUs.class);
					LastRank.this.startActivity(aboutus); 
	             break;  
	         case 3:  
	        	 LastRank.this.exitDialog(); 
	             break;
	         }  
	        return super.onOptionsItemSelected(item);
	    }
	    
	public void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(LastRank.this)
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
	

	public OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Toast.makeText(LastRank.this,
					(String) data.get(position).get("news"), Toast.LENGTH_LONG)
					.show();
			Intent news_name = new Intent(LastRank.this, LastRankContent.class);
			news_name.putExtra(EXTRA_MESSAGE, (String) data.get(position).get("news"));//ʵ��activity֮�����Ϣ���� char����
			LastRank.this.startActivity(news_name);
		}
	};
	private List<Hashtable<String, Object>> getData() {
		List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
		for (int i = 0; i < rank.length(); i++) {
			Hashtable<String, Object> table = new Hashtable<String, Object>();
            
			try {
				table.put("news", rank.getJSONObject(i).getString("Name"));//��һ��name��listview�е�key��value�ṹ �ڶ���name��jsonobject�е�name
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
				convertView = inflater.inflate(R.layout.listitem2, null);
				holder.news = (TextView) convertView.findViewById(R.id.news);
			
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.news.setText((String) data.get(position).get("news"));
			

			return convertView;
		}
	}
	

}

