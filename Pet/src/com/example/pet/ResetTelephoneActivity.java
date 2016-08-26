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

public class ResetTelephoneActivity extends Activity {

	ImageButton backAccountManager;
	Button saveNewTelephone;
	EditText newTelephone, confirmTelephone;
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
		setContentView(R.layout.activity_reset_telephone);
		initView();
	}
	
	//初始化视图
	private void initView(){
		backAccountManager = (ImageButton) findViewById(R.id.back_account_manager);
		saveNewTelephone = (Button) findViewById(R.id.ensure_new_telephone);
		newTelephone = (EditText) findViewById(R.id.new_telephone);
		confirmTelephone = (EditText) findViewById(R.id.confirm_telephone);
		//触发点击
		backAccountManager.setOnClickListener(clickListener);
		saveNewTelephone.setOnClickListener(clickListener);
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
		ResetTelephoneActivity.this.finish();
	}
}
