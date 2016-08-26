package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

public class AccountManagerActivity extends Activity {

	ImageButton backMine;
	RelativeLayout password, telephone, email, set;//修改密码，修改电话号码，修改邮箱，密保设置

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
		setContentView(R.layout.activity_account_manager);
		initView();
	}
	
	//初始化视图
	private void initView(){
		backMine = (ImageButton) findViewById(R.id.back_mine);
		password = (RelativeLayout) findViewById(R.id.reset_password_next);
		telephone = (RelativeLayout) findViewById(R.id.reset_phone_next);
		email = (RelativeLayout) findViewById(R.id.reset_mail_next);
		set = (RelativeLayout) findViewById(R.id.security_set_next);
		
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
		intent.setClass(AccountManagerActivity.this, MainActivity.class);
		startActivity(intent);
	}
}
