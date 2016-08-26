package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DianpingActivity extends Activity {
	TextView textview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dianping);
		ImageView imageView=(ImageView)findViewById(R.id.imageviewTitle);
		textview=(TextView)findViewById(R.id.textviewTitle);
		textview.setOnClickListener(onClickListener);
		imageView.setOnClickListener(onClickListener);
		
	}
	
	OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.textviewTitle:
			Toast.makeText(DianpingActivity.this, "提交", Toast.LENGTH_SHORT).show();
				break;
			case R.id.imageviewTitle:
				Intent intent=new Intent(DianpingActivity.this, XianqingActivity.class);
				startActivity(intent);
					break;
				
			
					
				

			default:
				break;
			}
			
		}
	};

}
