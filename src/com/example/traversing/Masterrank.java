package com.example.traversing;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Masterrank extends Activity {

	private List<String> listOfSpinner = new ArrayList<String>();
	private ArrayAdapter<String> adapterOfSpinner;
	private String[] options = { "Expense: ", "Safety: ", "Employment: ",
			"Terrain: ", "District: ", "Tourism: ", "Airport: ", "Train: ",
			"Bus: ", "Annual Rainfall: ", "Low Temperature: ",
			"High Temperature: ", };
	private List<Hashtable<String, Object>> dataOfOptions;
	
	//定义Menu菜单选项的ItemId  
    final static int ONE = Menu.FIRST;  
    final static int TWO = Menu.FIRST+1;  
    final static int THREE = Menu.FIRST+2;  
    

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.masterrank);
		MyApplication.getInstance().addActivity(this);

		// // set content for listView getdata������Ľṹ
		// adapter = new SimpleAdapter(Masterrank.this, getData(),
		// R.layout.listitem_assess, new String[] { "options" },
		// new int[] { R.id.options });// һ������һ��textview news
		// ListView result_list = (ListView) findViewById(R.id.list_news);
		// result_list.setAdapter(adapter);

		// set OnClick for VisitorAssess
		Button assess = (Button) findViewById(R.id.button1);
		assess.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent visitor_assess = new Intent(Masterrank.this,
						RankUser.class);
				Masterrank.this.startActivity(visitor_assess);
			}

		});

		// Listview
		dataOfOptions = getData();
		ListView result_list = (ListView) findViewById(R.id.listView1);
		MyAdapterOfOptions adapterOfOptions = new MyAdapterOfOptions(
				Masterrank.this);
		result_list.setAdapter(adapterOfOptions);
		result_list.setOnItemClickListener(listener);

		// data of spinner
		listOfSpinner.add("A");
		listOfSpinner.add("B");
		listOfSpinner.add("C");
		listOfSpinner.add("D");
	}

	// implement item's click
	public OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Toast.makeText(Masterrank.this,
					(String) dataOfOptions.get(position).get("options"),
					Toast.LENGTH_LONG).show();
		}
	};

	// ���������ṹ
	private List<Hashtable<String, Object>> getData() {
		List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
		for (int i = 0; i < options.length; i++) {
			Hashtable<String, Object> table = new Hashtable<String, Object>();

			table.put("options", options[i]);
			list.add(table);
		}
		return list;
	}

	// implement button's click event
	public final class ViewHolder {
		public TextView options;
		public Spinner spinner;
	}

	public class MyAdapterOfOptions extends BaseAdapter {
		private LayoutInflater inflater;

		public MyAdapterOfOptions(Context context) {
			this.inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return dataOfOptions.size();
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
				convertView = inflater.inflate(R.layout.listitem_assess, null);
				holder.options = (TextView) convertView
						.findViewById(R.id.options);
				holder.spinner = (Spinner) convertView
						.findViewById(R.id.spinner);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.options.setText((String) dataOfOptions.get(position).get(
					"options"));

			// Set spinner property
			adapterOfSpinner = new ArrayAdapter<String>(Masterrank.this,
					android.R.layout.simple_spinner_item, listOfSpinner);
			adapterOfSpinner
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			holder.spinner.setAdapter(adapterOfSpinner);
			holder.spinner.setSelection(0, true);
			holder.spinner.setOnItemSelectedListener(listenerOfSpinner);

			return convertView;
		}
	}

	// spinner click listener
	public OnItemSelectedListener listenerOfSpinner = new OnItemSelectedListener() {

		@Override
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			Toast.makeText(Masterrank.this,
					"You select " + listOfSpinner.get(position),
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			Toast.makeText(Masterrank.this, "You don't select anything!",
					Toast.LENGTH_SHORT).show();
		}
	};
	
	
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
	        	 Intent homepage = new Intent(Masterrank.this,
							Homepage.class);
					Masterrank.this.startActivity(homepage); 
	             break;  
	         case 2:  
	        	 Intent aboutus = new Intent(Masterrank.this,
							AboutUs.class);
					Masterrank.this.startActivity(aboutus); 
	             break;  
	         case 3:  
	        	 Masterrank.this.exitDialog(); 
	             break;
	         }  
	        return super.onOptionsItemSelected(item);
	    }
	    
	public void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(Masterrank.this)
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
