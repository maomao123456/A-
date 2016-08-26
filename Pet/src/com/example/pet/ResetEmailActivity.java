package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ResetEmailActivity extends Activity {

	ImageButton backAccountManager;
	Button saveNewEmail;
	EditText newEmail, confirmEmail;
	
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
		setContentView(R.layout.activity_reset_email);
		initView();
	}
	
	//初始化视图
	private void initView(){
		backAccountManager = (ImageButton) findViewById(R.id.back_account_manager);
		saveNewEmail = (Button) findViewById(R.id.ensure_new_mail);
		newEmail = (EditText) findViewById(R.id.new_mail);
		confirmEmail = (EditText) findViewById(R.id.confirm_mail);
		//触发点击
		backAccountManager.setOnClickListener(clickListener);
		saveNewEmail.setOnClickListener(clickListener);
	}
	
	//按钮点击方法
	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back_account_manager:
				backAccountManager();
				break;

			default:
				break;
			}
		}
	};
	
	//返回管理设置界面
	private void backAccountManager(){
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), AccountManagerActivity.class);
		startActivity(intent);
		ResetEmailActivity.this.finish();
	}
}
