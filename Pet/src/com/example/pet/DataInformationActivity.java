package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;

public class DataInformationActivity extends Activity {
	
	ImageButton backMine;
	EditText nickname, birthday, star, occupation, company, address, hometown, email, remark;
	RadioButton boy, girl;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
		setContentView(R.layout.activity_data_information);
		initView();
	}
	
	//初始化视图
	private void initView(){
		backMine = (ImageButton) findViewById(R.id.back_mine);
		nickname = (EditText) findViewById(R.id.input_nickname);
		birthday = (EditText) findViewById(R.id.input_birthday);
		star = (EditText) findViewById(R.id.input_star_sign);
		occupation = (EditText) findViewById(R.id.input_occupation);
		company = (EditText) findViewById(R.id.input_company);
		address = (EditText) findViewById(R.id.input_address);
		hometown = (EditText) findViewById(R.id.input_hometown);
		email = (EditText) findViewById(R.id.input_mail);
		remark = (EditText) findViewById(R.id.input_personality_desdcript);
		boy = (RadioButton) findViewById(R.id.boy);
		girl = (RadioButton) findViewById(R.id.girl);
		//触发点击
		backMine.setOnClickListener(clickListener);
	}
	
	//点击按钮方法
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
		intent.setClass(DataInformationActivity.this, MineActivity.class);
		startActivity(intent);
	}
}
