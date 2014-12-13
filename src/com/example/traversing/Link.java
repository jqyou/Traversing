package com.example.traversing;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;


public class Link extends Activity{
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.link);
		MyApplication.getInstance().addActivity(this);
		
		Button button1 = (Button) findViewById(R.id.button_return);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent visitor_assess = new Intent(Link.this,
						SXX.class);
				Link.this.startActivity(visitor_assess);
			}

		});
	
	String[] major = { "Business", "Law", "Engineering", "Science", "Medical" };
	Spinner spinner;
	ArrayAdapter<String> adapter;

	spinner = (Spinner) findViewById(R.id.spinner_major);

	// ����ѡ������ArrayAdapter��������
	adapter = new ArrayAdapter<String>(this,
			android.R.layout.simple_spinner_item, major);

	// ���������б�ķ��
	adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

	// ��adapter ��ӵ�spinner��
	spinner.setAdapter(adapter);
	
	// ����Ĭ��ֵ
	spinner.setVisibility(View.VISIBLE);

}

}
