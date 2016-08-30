package com.example.pet;

import com.example.pet.classes.SysApplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class UserHelpActivity extends Activity {

	ImageButton backSet;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_help);
		SysApplication.getInstance().addActivity(this);
		
		backSet = (ImageButton) findViewById(R.id.back_set);
		backSet.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(getApplicationContext(), SetActivity.class);
				startActivity(intent);
				UserHelpActivity.this.finish();
			}
		});
	}
}
