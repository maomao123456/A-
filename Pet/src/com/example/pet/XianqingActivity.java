package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class XianqingActivity extends Activity {
	ImageView imageview;
	TextView tetview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiangqing);
		imageview=(ImageView)findViewById(R.id.imageview);
		imageview.setOnClickListener(onClickListener);
		tetview=(TextView) findViewById(R.id.dianping);
		tetview.setOnClickListener(onClickListener);
	}
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.dianping:
				Intent intent=new Intent(XianqingActivity.this, DianpingActivity.class);
				startActivity(intent);
				
				break;

			default:
				break;
			}
			
			
		}
	};
}
