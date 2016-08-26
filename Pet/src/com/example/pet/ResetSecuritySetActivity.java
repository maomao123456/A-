package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;

public class ResetSecuritySetActivity extends Activity {

	ImageButton backAccountManager;
	Button saveSecuritySet;

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reset_security_set);
		initView();
	}
	
	//初始化视图
	private void initView(){
		backAccountManager = (ImageButton) findViewById(R.id.back_account_manager);
		saveSecuritySet = (Button) findViewById(R.id.save_security_set);
		//触发点击
		backAccountManager.setOnClickListener(clickListener);
	}
	
	//按钮点击方法
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
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
			ResetSecuritySetActivity.this.finish();
		}
}
