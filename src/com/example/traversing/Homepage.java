package com.example.traversing;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Homepage extends Activity {

	// private SimpleAdapter adapter;
	private List<Hashtable<String, Object>> data;
	private String[] news = {
			"1. USNews global university rankings.",
			"2. USNews national university rankings.",
			"3. QS university rankings in Asia.",
			"4. Updated deadline of the application.",
			"5. New entry requirements of HKUST.",
			"6. Propaganda of USC and NYU in Peking University.",
			"7. Professor in CMU talks about employment situation in CS field.",
			"8. The safe city rankings in America.",
			"9. Professor Chen Lei recruits exchange student major in Datamining between HKUST and UWaterloo.",
			"10. Students in Beijing find friends to co-fly to Chicago on Sep,12th.",
			"11. Successfully applying CV and PS shared by students in HKU.",
			"12. The difference between Msc and Mphil of universities in British commonwealth countries.",
			"13. The university rankings of highest employment rate in Law field.",
			"14. Convene volunteers of the coming China Higher Education Exhibition in Shanghai." };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage);

		Button button1 = (Button) findViewById(R.id.button_register);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Homepage.this, Register.class);
				Homepage.this.startActivity(intent);
			}

		});

		Button button2 = (Button) findViewById(R.id.button_login);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(Homepage.this, Login.class);
				Homepage.this.startActivity(intent);
			}

		});

		// set OnClick for VisitorAssess
		Button assess = (Button) findViewById(R.id.button_assess);
		assess.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent visitor_assess = new Intent(Homepage.this,
						Visitorassess.class);
				Homepage.this.startActivity(visitor_assess);
			}

		});

		ListView result_list = (ListView) findViewById(R.id.list_news);
		data = getData();
		MyAdapter adapter = new MyAdapter(this);
		result_list.setAdapter(adapter);
		result_list.setOnItemClickListener(listener);
	}

	// 实现item的点击
	public OnItemClickListener listener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			Toast.makeText(Homepage.this,
					(String) data.get(position).get("news"), Toast.LENGTH_LONG)
					.show();
		}
	};

	// 产生《》结构
	private List<Hashtable<String, Object>> getData() {
		List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
		for (int i = 0; i < news.length; i++) {
			Hashtable<String, Object> table = new Hashtable<String, Object>();

			table.put("news", news[i]);
			list.add(table);
		}
		return list;
	}

	// 实现button的点击
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
