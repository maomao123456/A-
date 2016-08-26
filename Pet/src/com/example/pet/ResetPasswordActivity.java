package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class ResetPasswordActivity extends Activity {

	ImageButton backAccountManager;
	Button saveNewPassword;
	EditText newPassword, confirmPassword;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
		setContentView(R.layout.activity_reset_password);
		initView();
	}
	
	//初始化视图
	private void initView(){
		backAccountManager = (ImageButton) findViewById(R.id.back_account_manager);
		saveNewPassword = (Button) findViewById(R.id.ensure_new_password);
		newPassword = (EditText) findViewById(R.id.new_password);
		confirmPassword = (EditText) findViewById(R.id.confirm_password);
		//触发点击
		backAccountManager.setOnClickListener(clickListener);
		saveNewPassword.setOnClickListener(clickListener);
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
		ResetPasswordActivity.this.finish();
	}
}
