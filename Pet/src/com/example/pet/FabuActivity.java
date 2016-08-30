package com.example.pet;

import com.example.pet.classes.SysApplication;

import android.app.Activity;
import android.os.Bundle;

public class FabuActivity  extends Activity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_fabu);
		SysApplication.getInstance().addActivity(this);
	}

}
