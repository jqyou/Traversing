package com.example.traversing;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class Homepage extends Activity {

	private SimpleAdapter adapter;
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

		// set content for listView  getdata是下面的结构
		adapter = new SimpleAdapter(Homepage.this, getData(),
				R.layout.listitem, new String[] { "news" },
				new int[] { R.id.news });//一行中有一个textview news
		ListView result_list = (ListView) findViewById(R.id.list_news);
		result_list.setAdapter(adapter);
		
        /*image button
		ImageButton imagebutton = (ImageButton)findViewById(R.id.like);
		imagebutton.setOnTouchListener(new View.OnTouchListener() {
			
			@SuppressLint("ClickableViewAccessibility") @Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN){
					//重新设置按下时的背景图片  
		               ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.icon));
				}else if(event.getAction() == MotionEvent.ACTION_UP){       
	                //再修改为抬起时的正常图片  
	                ((ImageButton)v).setImageDrawable(getResources().getDrawable(R.drawable.beijingtu));             
				}
				return false;
			}
				
			
		});*/
		
		//set OnClick for VisitorAssess
		Button assess = (Button)findViewById(R.id.button_assess);
		assess.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Intent visitor_assess = new Intent(Homepage.this, Visitorassess.class);
				Homepage.this.startActivity(visitor_assess);
			}
			
		});
	}
	
    //产生《》结构
	private List<Hashtable<String, Object>> getData() {
		List<Hashtable<String, Object>> list = new ArrayList<Hashtable<String, Object>>();
		for (int i = 0; i < news.length; i++) {
			Hashtable<String, Object> table = new Hashtable<String, Object>();

			table.put("news", news[i]);
			list.add(table);
		}
		return list;
	}
}
