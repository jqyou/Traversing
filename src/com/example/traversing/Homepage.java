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
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
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
	
	private NameStore usernamestore;//设置全局变量来查看是否有用户登陆
	private String username;

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
		MyApplication.getInstance().addActivity(this);
		
		usernamestore = (NameStore) getApplication();
	    username=usernamestore.getText();
	    Button button1 = (Button) findViewById(R.id.button_register);
	    Button button2 = (Button) findViewById(R.id.button_login);
		Button assess = (Button) findViewById(R.id.button_assess);
		Button exit = (Button) findViewById(R.id.button_exit);
		TextView user_name = (TextView) findViewById(R.id.textView1);
	    if(username.equals("")){
	    	
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
		
		
		
	
			
	
			ListView result_list = (ListView) findViewById(R.id.list_news);
			data = getData();
			MyAdapter adapter = new MyAdapter(this);
			result_list.setAdapter(adapter);
			result_list.setOnItemClickListener(listener);
			
	
		
		
	
		


	}

	// 退出的方法
	public void exitDialog() {
		Dialog dialog = new AlertDialog.Builder(Homepage.this)
				.setTitle("Exit？").setMessage("Are You Sure to Exit?")
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
			Toast.makeText(Homepage.this,
					(String) data.get(position).get("news"), Toast.LENGTH_LONG)
					.show();
		}
	};

	// ���������ṹ
	private List<Hashtable<String, Object>> getData() {
		List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
		for (int i = 0; i < news.length; i++) {
			Hashtable<String, Object> table = new Hashtable<String, Object>();

			table.put("news", news[i]);
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
