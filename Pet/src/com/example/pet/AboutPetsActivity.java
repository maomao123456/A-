package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class AboutPetsActivity extends Activity {

	ImageButton backMine;
	RelativeLayout rating, function, system, report, version;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
		setContentView(R.layout.activity_about_lovely_pets);
		initView();
	}
	
	//初始化视图
	private void initView(){
		backMine = (ImageButton) findViewById(R.id.back_mine);
		rating = (RelativeLayout) findViewById(R.id.rating_next);
		function = (RelativeLayout) findViewById(R.id.function_next);
		system = (RelativeLayout) findViewById(R.id.system_notification_next);
		report = (RelativeLayout) findViewById(R.id.report_complaint_next);
		version = (RelativeLayout) findViewById(R.id.check_new_version_next);
		//触发点击
		backMine.setOnClickListener(clickListener);
	}
	
	//按钮点击方法
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back_mine:
				backMine();
				break;

			default:
				break;
			}
		}
	};
	
	//返回我的界面
	private void backMine(){
		Intent intent = new Intent();
		intent.setClass(AboutPetsActivity.this, MineActivity.class);
		startActivity(intent);
	}
}
