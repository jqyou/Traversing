package com.example.traversing;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class VisitorRank extends Activity {

	private SimpleAdapter adapter;
	private JSONArray rank;
	//定义Menu菜单选项的ItemId  
    final static int ONE = Menu.FIRST;  
    final static int TWO = Menu.FIRST+1;  
    final static int THREE = Menu.FIRST+2;  

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rankvisitor);
		MyApplication.getInstance().addActivity(this);

		Intent intent = getIntent();
		String result = intent.getStringExtra(Visitorassess.EXTRA_MESSAGE);//������string

		try {
			rank = new JSONArray(result);

			adapter = new SimpleAdapter(VisitorRank.this, getData(),//���������getdata����
					R.layout.listitem_name, new String[] { "name" },
					new int[] { R.id.names });
			ListView result_list = (ListView) findViewById(R.id.list_name);
			result_list.setAdapter(adapter);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		// set OnClick for return
		Button button_return = (Button) findViewById(R.id.button_return);
		button_return.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				VisitorRank.this.finish();
			}
		});
	}

	private List<Hashtable<String, Object>> getData() {
		List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
		for (int i = 0; i < rank.length(); i++) {
			Hashtable<String, Object> table = new Hashtable<String, Object>();

			try {
				table.put("name", rank.getJSONObject(i).getString("Name"));//��һ��name��listview�е�key��value�ṹ �ڶ���name��jsonobject�е�name
			} catch (JSONException e) {
				e.printStackTrace();
			}
			list.add(table);
		}
		return list;
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
	        	 Intent homepage = new Intent(VisitorRank.this,
							Homepage.class);
					VisitorRank.this.startActivity(homepage); 
	             break;  
	         case 2:  
	        	 Intent aboutus = new Intent(VisitorRank.this,
							AboutUs.class);
	        	 VisitorRank.this.startActivity(aboutus); 
	             break;  
	         case 3:  
	        	 VisitorRank.this.exitDialog(); 
	             break;
	         }  
	        return super.onOptionsItemSelected(item);
	    }
	    
	public void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(VisitorRank.this)
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
