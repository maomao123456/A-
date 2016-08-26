package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class DianpuActivity  extends Activity{
	TextView textview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dianpu);
		textview=(TextView) findViewById(R.id.textview2);
		textview.setOnClickListener(onClickListener);
		
	}
	
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.textview2:
				Intent intent=new Intent(DianpuActivity.this, FabuActivity.class);
				startActivity(intent);
				
				break;

			default:
				break;
			}
			
			
		}
	};
	
}
