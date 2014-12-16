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

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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
	private List<String> listOfSpinner;
	private NameStore usernamestore;
	private ArrayAdapter<String> adapterOfSpinner;
	private String[] options = { "Choose Expense: ", "Choose Safety: ",
			"Choose Employment: ", "Choose Terrain: ", "Choose District: ",
			"Choose Tourism: ", "Choose Airport: ", "Choose Train: ",
			"Choose Bus: ", "Choose Annual Rainfall: ",
			"Choose Low Temperature: ", "Choose High Temperature: ", };
	private List<Hashtable<String, Object>> dataOfOptions;

	final static int ONE = Menu.FIRST;
	final static int TWO = Menu.FIRST + 1;
	final static int THREE = Menu.FIRST + 2;

	public final static String EXTRA_MESSAGE = "com.example.traversing.MESSAGE";
	private String URL_PATH = "http://1.traversingoceans.sinaapp.com/index.php/api/master/index";

	private String GPA,GPAstr;
	private String TOEFL = "0",TOEFLstr;
	private String IELTS = "0",IELTSstr;
	private String GRE = "0",GREstr;
	private String Expense = "50000";
	private String Safety = "51";
	private String Employment = "60.0";
	private String Terrain = "plain";
	private String District = "east";
	private String Tourism = "y";
	private String Airport = "y";
	private String Train = "y";
	private String Bus = "y";
	private String AnnualRainfall = "middle";
	private String LowTemperature = "15";
	private String HighTemperature = "20";
	private String username;
	
	private JSONArray record;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.masterrank);
		MyApplication.getInstance().addActivity(this);
		

		// set OnClick for VisitorAssess
		Button assess = (Button) findViewById(R.id.button1);
		assess.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				usernamestore = (NameStore) getApplication();
			    username=usernamestore.getText();
			    
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
				if(GPAstr == null || GPAstr == ""
						|| GPAstr.length() == 0 || TOEFLstr == null
						|| TOEFLstr == "" || TOEFLstr.length() == 0
						||IELTSstr == null || IELTSstr == ""
						|| IELTSstr.length() == 0 || GREstr == null
						|| GREstr == "" || GREstr.length() == 0)
				{
					Toast.makeText(Masterrank.this,
							"Please insert all scores of GPA,TOEFL,IELTS,GRE",
							Toast.LENGTH_LONG).show();
				}
				else{

				new UploadWebpageTask().execute(URL_PATH);}
			}

		});

		// Listview
		dataOfOptions = getData();
		ListView result_list = (ListView) findViewById(R.id.listView1);
		MyAdapterOfOptions adapterOfOptions = new MyAdapterOfOptions(
				Masterrank.this);
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

	// ≤˙…˙°∂°∑Ω·ππ
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
					android.R.layout.simple_spinner_item,
					getlistOfSpinner(position));
			adapterOfSpinner
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			holder.spinner.setAdapter(adapterOfSpinner);
			holder.spinner.setTag(position);
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
			AssignValue(parent.getItemAtPosition(0).toString(), parent
					.getItemAtPosition(position).toString());
		}

		@Override
		public void onNothingSelected(AdapterView<?> parent) {

			AssignValue(parent.getItemAtPosition(0).toString(), parent
					.getItemAtPosition(0).toString());
		}
	};

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
			Intent homepage = new Intent(Masterrank.this, Homepage.class);
			Masterrank.this.startActivity(homepage);
			break;
		case 2:
			Intent aboutus = new Intent(Masterrank.this, AboutUs.class);
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
				.setTitle("Leave?")
				.setMessage("Are You Sure to Exit?")
				.setPositiveButton("Sure",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								MyApplication.getInstance().exit();
								// Homepage.this.finish();
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

	// spinner
	public List<String> getlistOfSpinner(int position) {
		listOfSpinner = new ArrayList<String>();
		switch (position) {
		case 0:
			listOfSpinner.add("Expense:");
			listOfSpinner.add("<25000");
			listOfSpinner.add("<35000");
			listOfSpinner.add("<50000");
			return listOfSpinner;

		case 1:
			listOfSpinner.add("Safety:");
			listOfSpinner.add("<15");
			listOfSpinner.add("<35");
			listOfSpinner.add("<51");
			return listOfSpinner;

		case 2:
			listOfSpinner.add("Employment:");
			listOfSpinner.add(">85");
			listOfSpinner.add(">80");
			listOfSpinner.add(">70");
			listOfSpinner.add(">60");
			return listOfSpinner;

		case 3:
			listOfSpinner.add("Terrain:");
			listOfSpinner.add("Plain");
			listOfSpinner.add("Mountain");
			return listOfSpinner;

		case 4:
			listOfSpinner.add("District:");
			listOfSpinner.add("East");
			listOfSpinner.add("West");
			listOfSpinner.add("North");
			listOfSpinner.add("South");
			listOfSpinner.add("Middle");
			return listOfSpinner;

		case 5:
			listOfSpinner.add("Tourism:");
			listOfSpinner.add("Yes");
			listOfSpinner.add("No");
			return listOfSpinner;

		case 6:
			listOfSpinner.add("Airport:");
			listOfSpinner.add("Yes");
			listOfSpinner.add("No");
			return listOfSpinner;

		case 7:
			listOfSpinner.add("Train:");
			listOfSpinner.add("Yes");
			listOfSpinner.add("No");
			return listOfSpinner;

		case 8:
			listOfSpinner.add("Bus:");
			listOfSpinner.add("Yes");
			listOfSpinner.add("No");
			return listOfSpinner;

		case 9:
			listOfSpinner.add("Annual Rainfall:");
			listOfSpinner.add("Middle");
			listOfSpinner.add("Low");
			listOfSpinner.add("High");
			listOfSpinner.add("VHigh");
			return listOfSpinner;

		case 10:
			listOfSpinner.add("Low Temperature:");
			listOfSpinner.add("<15");
			listOfSpinner.add("<5");
			listOfSpinner.add("<0");
			return listOfSpinner;

		case 11:
			listOfSpinner.add("High Temperature:");
			listOfSpinner.add(">20");
			listOfSpinner.add(">25");
			listOfSpinner.add(">30");
			return listOfSpinner;

		default:
			return null;
		}
	}

	public void AssignValue(String option, String selection) {
		if (option.equals("Expense:")) {
			if (option.equals(selection)) {
				Expense = "50000";
			} else if(selection.equals("<25000")){
				Expense = "25000";
			} else if(selection.equals("<35000")){
				Expense = "35000";
			} else if(selection.equals("<50000")){
				Expense = "50000";
			} 
			
		}
		else if (option.equals("Safety:")) {
			if (option.equals(selection)) {
				Safety = "51";
			} else if(selection.equals("<15")){
				Safety = "15";
			} else if(selection.equals("<35")){
				Safety = "35";
			} else if(selection.equals("<51")){
				Safety = "51";
			}
		} else if (option.equals("Employment:")) {
			if (option.equals(selection)) {
				Employment = "60.0";
			} else if(selection.equals(">85")){
				Employment = "85.0";
			} else if(selection.equals(">80")){
				Employment = "80.0";
			} else if(selection.equals(">70")){
				Employment = "70.0";
			} else if(selection.equals(">60")){
				Employment = "60.0";
			}
		} else if (option.equals("Terrain:")) {
			if (option.equals(selection)) {
				Terrain = "plain";
			} else if(selection.equals("Plain")){
				Terrain = "plain";
			} else if(selection.equals("Mountain")){
				Terrain = "mountain";
			}
			
		} else if (option.equals("District:")) {
			if (option.equals(selection)) {
				District = "east";
			} else if (selection.equals("East")){
					District = "east";
			} else if (selection.equals("West")){
				District = "west";
			} else if (selection.equals("North")){
				District = "north";
			} else if (selection.equals("South")){
				District = "south";
			} else if (selection.equals("Middle")){
				District = "middle";
			} else {
				District = "east";
			}
			
		} else if (option.equals("Tourism:")) {
			if (option.equals(selection)) {
				Tourism = "y";
			} else if(selection.equals("Yes")){
				Tourism = "y";
			} else if(selection.equals("No")){
				Tourism = "n";
			} 
			
		} else if (option.equals("Airport:")) {
			if (option.equals(selection)) {
				Airport = "y";
			} else if(selection.equals("Yes")){
				Airport = "y";
			} else if(selection.equals("No")){
				Airport = "n";
			} 
		} else if (option.equals("Train:")) {
			if (option.equals(selection)) {
				Train = "y";
			} else if(selection.equals("Yes")){
				Train = "y";
			} else if(selection.equals("No")){
				Train = "n";
			} 
			
		} else if (option.equals("Bus:")) {
			if (option.equals(selection)) {
				Bus = "y";
			} else if(selection.equals("Yes")){
				Bus = "y";
			} else if(selection.equals("No")){
				Bus = "n";
			} 
		} else if (option.equals("Annual Rainfall:")) {
			if (option.equals(selection)) {
				AnnualRainfall = "middle";
			} else if (selection.equals("Middle")){
				AnnualRainfall = "middle";
			} else if (selection.equals("High")){
				AnnualRainfall = "high";
			} else if (selection.equals("VHigh")){
				AnnualRainfall = "vhigh";
			} else if (selection.equals("Low")){
				AnnualRainfall = "low";
			} 
		} else if (option.equals("Low Temperature:")) {
			if (option.equals(selection)) {
				LowTemperature = "15";
			} else if (selection.equals("<15")){
				LowTemperature = "15";
			} else if (selection.equals("<5")){
				LowTemperature = "5";
			} else if (selection.equals("<0")){
				LowTemperature = "0";
			} 
		} else if (option.equals("High Temperature:")) {
			if (option.equals(selection)) {
				HighTemperature = "20";
			} else if (selection.equals(">20")){
				HighTemperature = "20";
			} else if (selection.equals(">25")){
				HighTemperature = "25";
			} else if (selection.equals(">30")){
				HighTemperature = "30";
			} 
			Log.i("TAG", HighTemperature);
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
				content.put("EXPENSE", Expense); 
				content.put("SAFETY",Safety); 
				content.put("EMPLOYMENT", Employment);
				content.put("TERRAIN", Terrain); 
				content.put("DISTRICT",District); 
				content.put("TOURISM", Tourism);
				content.put("AIRPORT", Airport); 
				content.put("TRAIN", Train);
				content.put("BUS", Bus); 
				content.put("RAINFALL",AnnualRainfall);
				content.put("HIGHTEM", HighTemperature);
				content.put("LOWTEM", LowTemperature);
				content.put("UserName", username );
	
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
		char[] buffer = new char[len];// string
		reader.read(buffer);
		return new String(buffer);
	}

}
