package com.example.pet;

import com.example.pet.classes.SysApplication;

import android.app.Activity;
import android.os.Bundle;

public class ReportComplaintActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_report_complaint);
		SysApplication.getInstance().addActivity(this);// 添加Activity页面到SysApplication
	}
}
