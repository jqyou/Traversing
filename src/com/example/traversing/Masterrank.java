package com.example.traversing;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Masterrank extends Activity {

	private SimpleAdapter adapter;
	private String[] options = { "Expense: ", "Safety: ", "Employment: ",
			"Terrain: ", "District: ", "Tourism: ", "Airport: ", "Train: ",
			"Bus: ", "Annual Rainfall: ", "Low Temperature: ",
			"High Temperature: ", };

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.masterrank);

		// set content for listView getdata是下面的结构
		adapter = new SimpleAdapter(Masterrank.this, getData(),
				R.layout.listitem_assess, new String[] { "options" },
				new int[] { R.id.options });// 一行中有一个textview news
		ListView result_list = (ListView) findViewById(R.id.list_news);
		result_list.setAdapter(adapter);

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
	}
	//产生《》结构
		private List<Hashtable<String, Object>> getData() {
			List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
			for (int i = 0; i < options.length; i++) {
				Hashtable<String, Object> table = new Hashtable<String, Object>();

				table.put("options", options[i]);
				list.add(table);
			}
			return list;
		}
}
