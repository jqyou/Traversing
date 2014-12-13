package com.example.traversing;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class VisitorRank extends Activity {

	private SimpleAdapter adapter;
	private JSONArray rank;

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
}
