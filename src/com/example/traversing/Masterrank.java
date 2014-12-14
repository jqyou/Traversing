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

import org.json.JSONException;
import org.json.JSONObject;

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
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Masterrank extends Activity {

	private SimpleAdapter adapter;
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
    

	public final static String EXTRA_MESSAGE = "com.example.traversing.MESSAGE";
	private String URL_PATH = "http://1.traversingoceans.sinaapp.com/index.php/api/user";
	private String GPA;
	private String TOEFL = "0";
	private String IELTS = "0";
	private String GRE = "0";
	private String EXPENSE;
	private String SAFETY;
	private String EMPLOYMENT;
	private String TERRAIN;
	private String DISTRICT;
	private String TOURISM;
	private String AIRPORT;
	private String TRAIN;
	private String BUS;
	private String RAINFALL;
	private String LOWTEM;
	private String HIGHTEM;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.masterrank);
		MyApplication.getInstance().addActivity(this);

		// set OnClick for VisitorAssess
		Button button1 = (Button) findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {

			/*
			 * @Override public void onClick(View v) { Intent visitor_assess =
			 * new Intent(Masterrank.this, RankUser.class);
			 * Masterrank.this.startActivity(visitor_assess); }
			 */

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
				

				new UploadWebpageTask().execute(URL_PATH);
			}

		});

		// Listview
		dataOfOptions = getData();
		ListView result_list = (ListView) findViewById(R.id.listView1);
		MyAdapterOfOptions adapterOfOptions = new MyAdapterOfOptions(Masterrank.this);
		result_list.setAdapter(adapterOfOptions);
		result_list.setOnItemClickListener(listener);

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

	//
	private List<Hashtable<String, Object>> getData() {
		List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
		for (int i = 0; i < options.length; i++) {
			Hashtable<String, Object> table = new Hashtable<String, Object>();

			table.put("options", options[i]);
			list.add(table);
		}
		return list;
	}
	
	ViewHolder hello=new ViewHolder();
	
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
					android.R.layout.simple_spinner_item,
					getlistOfSpinner(position));
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
			
			/*EXPENSE = holder.spinner.getSelectedItem().toString();
			SAFETY = spinner.getSelectedItem().toString();
			EMPLOYMENT = spinner.getSelectedItem().toString();
			TERRAIN = spinner.getSelectedItem().toString();
			DISTRICT = spinner.listOfSpinner().getSelectedItem().toString();
			TOURISM = spinner.listOfSpinner().getSelectedItem().toString();
			AIRPORT = spinner.listOfSpinner().getSelectedItem().toString();
			TRAIN = spinner.listOfSpinner().getSelectedItem().toString();
			BUS = spinner.listOfSpinner.getSelectedItem().toString();
			RAINFALL = spinner.listOfSpinner.getSelectedItem().toString();
			LOWTEM = spinner.listOfSpinner.getSelectedItem().toString();
			HIGHTEM = spinner.getSelectedItem().toString();*/
			// Toast.makeText(Masterrank.this,"You select "+listOfSpinner.get(position),
			// Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {
			// Toast.makeText(Masterrank.this,"You don't select anything!",
			// Toast.LENGTH_SHORT).show();
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

	// the button options
	public List<String> getlistOfSpinner(int position) {
		listOfSpinner = new ArrayList<String>();
		switch (position) {
		case 0:
			listOfSpinner.add("<20000");
			listOfSpinner.add("20000-40000");
			listOfSpinner.add(">40000");
			return listOfSpinner;

		case 1:
			listOfSpinner.add("<15");
			listOfSpinner.add("15-30");
			listOfSpinner.add("31-51");
			return listOfSpinner;

		case 2:
			listOfSpinner.add(">85%");
			listOfSpinner.add(">80%");
			listOfSpinner.add(">70%");
			listOfSpinner.add(">60%");
			return listOfSpinner;

		case 3:
			listOfSpinner.add("Plain");
			listOfSpinner.add("Mountain");
			return listOfSpinner;

		case 4:
			listOfSpinner.add("East");
			listOfSpinner.add("West");
			listOfSpinner.add("North");
			listOfSpinner.add("South");
			return listOfSpinner;

		case 5:
			listOfSpinner.add("Yes");
			listOfSpinner.add("No");
			return listOfSpinner;

		case 6:
			listOfSpinner.add("Yes");
			listOfSpinner.add("No");
			return listOfSpinner;

		case 7:
			listOfSpinner.add("Yes");
			listOfSpinner.add("No");
			return listOfSpinner;

		case 8:
			listOfSpinner.add("Middle");
			listOfSpinner.add("Low");
			listOfSpinner.add("High");
			listOfSpinner.add("VHigh");
			return listOfSpinner;

		case 9:
			listOfSpinner.add(">5");
			listOfSpinner.add("Minus5-5");
			listOfSpinner.add("<Minus5");
			return listOfSpinner;

		case 10:
			listOfSpinner.add("<25");
			listOfSpinner.add("25-30");
			listOfSpinner.add(">30");
			return listOfSpinner;

		default:
			return null;
		}
	}

	private class UploadWebpageTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {
			try {
				return downloadUrl(URL_PATH);// downloadurl string
			} catch (IOException e) {
				e.printStackTrace();
				return "URL maybe invalid";
			}
		}

		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {// string
														// json
			Intent rank_name = new Intent(Masterrank.this, RankUser.class);
			rank_name.putExtra(EXTRA_MESSAGE, result);// ʵ��activity
														// char����
			Masterrank.this.startActivity(rank_name);
		}
	}

	private String downloadUrl(String myurl) throws IOException {// ����һ��downurl
		InputStream is = null;// input

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
					"application/x-www-form-urlencoded");// 

			conn.connect();

			DataOutputStream out = new DataOutputStream(conn.getOutputStream());// out
																				
																				// data input

			JSONObject content = new JSONObject();

			try {
				content.put("GPA", GPA);
				content.put("TOEFL", TOEFL);
				content.put("IELTS", IELTS);
				content.put("GRE", GRE);
				content.put("EXPENSE", EXPENSE);
				content.put("SAFETY", SAFETY);
				content.put("EMPLOYMENT", EMPLOYMENT);
				content.put("TERRAIN", TERRAIN);
				content.put("DISTRICT", DISTRICT);
				content.put("TOURISM", TOURISM);
				content.put("AIRPORT", AIRPORT);
				content.put("TRAIN", TRAIN);
				content.put("BUS", BUS);
				content.put("RAINFALL", RAINFALL);
				content.put("HIGHTEM", HIGHTEM);
				content.put("LOWTEM", LOWTEM);
			} catch (JSONException e) {
				e.printStackTrace();
			}

			out.write(content.toString().getBytes());// string
														
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

	public String readIt(InputStream stream, int len) throws IOException,// 
			UnsupportedEncodingException {
		Reader reader = null;
		reader = new InputStreamReader(stream, "UTF-8");
		char[] buffer = new char[len];//string
		reader.read(buffer);
		return new String(buffer);
	}

}
