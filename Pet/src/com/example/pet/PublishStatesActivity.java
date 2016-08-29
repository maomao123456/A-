package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

public class PublishStatesActivity extends Activity{
	TextView quxiao,fabiao;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_states);
		init();
	}
	public void init(){
		quxiao=(TextView)findViewById(R.id.quxiao_publish_states);
		fabiao=(TextView)findViewById(R.id.fabiao_publish_states);
		quxiao.setOnClickListener(clickListener);
		fabiao.setOnClickListener(clickListener);
	}
	OnClickListener clickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.quxiao_publish_states:
				startActivity(new Intent(PublishStatesActivity.this,SquareDetailsActivity.class));
				PublishStatesActivity.this.finish();
				break;
			case R.id.fabiao_publish_states:
				Toast.makeText(PublishStatesActivity.this, "发表", Toast.LENGTH_SHORT).show();
				break;
			}
		}
	};
}
