package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class XianqingActivity extends Activity {
	ImageView imageview;
	TextView tetview,textview1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_xiangqing);
		imageview=(ImageView)findViewById(R.id.imageview);
		textview1=(TextView)findViewById(R.id.sc);
		imageview.setOnClickListener(onClickListener);
		tetview=(TextView) findViewById(R.id.dianping111);
		tetview.setOnClickListener(onClickListener);
		textview1.setOnClickListener(onClickListener);
	}
	OnClickListener onClickListener=new OnClickListener() {
	
		
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.dianping111:
				Intent intent=new Intent(XianqingActivity.this, DianpingActivity.class);
				startActivity(intent);
			
				break;
			case R.id.imageview:
				Intent intent1=new Intent(XianqingActivity.this, MainActivity.class);
				startActivity(intent1);
				break;
				
			case R.id.sc:
				Toast.makeText(XianqingActivity.this, "收藏+1", Toast.LENGTH_SHORT).show();
				break;
				

			default:
				break;
				
			}
			
			
		}
	};
}
