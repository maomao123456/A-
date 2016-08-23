package com.example.pet;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MineActivity extends Activity {

	ImageView user_icon;//用户头像
	TextView user_nickname, user_id;//用户的呢称和ID
	TextView concern_num, fans_num, collection_num;//用户关注，粉丝，和收藏
	RelativeLayout data_infor, account_manager, about_pets, settings;//用户的信息，账号管理，用户的萌萌的宠物，以及设置
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
		setContentView(R.layout.activity_mine);
		initView();
	}
	
	//初始化视图
	private void initView(){
		user_icon = (ImageView) findViewById(R.id.mine_user_icon);
		user_nickname = (TextView) findViewById(R.id.mine_nickname);
		user_id = (TextView) findViewById(R.id.mine_id);
		concern_num = (TextView) findViewById(R.id.concern_num);
		fans_num = (TextView) findViewById(R.id.fans_num);
		collection_num = (TextView) findViewById(R.id.collection_num);
		data_infor = (RelativeLayout) findViewById(R.id.information_next);
		account_manager = (RelativeLayout) findViewById(R.id.account_manager_next);
		about_pets = (RelativeLayout) findViewById(R.id.about_pets_next);
		settings = (RelativeLayout) findViewById(R.id.settings_next);
		
		//触发点击
		data_infor.setOnClickListener(clickListener);
		account_manager.setOnClickListener(clickListener);
		about_pets.setOnClickListener(clickListener);
		settings.setOnClickListener(clickListener);
	}
	
	//点击按钮方法
	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			switch(v.getId()){
			case R.id.information_next:
				toDataInfor();
				break;
			case R.id.account_manager_next:
				toAccountManager();
				break;
			case R.id.about_pets_next:
				toAboutPets();
				break;
			case R.id.settings_next:
				toSetting();
				break;
			default:
				break;
			}
		}
	};

	//跳转到资料信息界面
	private void toDataInfor(){
		Intent intent = new Intent();
		intent.setClass(MineActivity.this, DataInformationActivity.class);
		startActivity(intent);
	}
	
	//跳转到账户管理界面
	private void toAccountManager(){
		Intent intent = new Intent();
		intent.setClass(MineActivity.this, AccountManagerActivity.class);
		startActivity(intent);
	}
	
	//跳转到关于萌宠界面
	private void toAboutPets(){
		Intent intent = new Intent();
		intent.setClass(MineActivity.this, AboutPetsActivity.class);
		startActivity(intent);
	}
	
	//跳转到设置界面
	private void toSetting(){
		Intent intent = new Intent();
		intent.setClass(MineActivity.this, SetActivity.class);
		startActivity(intent);
	}
}
