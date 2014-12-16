package com.example.traversing;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import com.baidu.frontia.FrontiaApplication;

public class MyApp extends FrontiaApplication {

	public List<Activity> activitiesList;

	@Override
	public void onCreate() {

		// TODO Auto-generated method stub

		activitiesList = new ArrayList<Activity>();

		super.onCreate();

	}

}
