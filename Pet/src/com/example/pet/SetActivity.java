package com.example.pet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class SetActivity extends Activity {

	ImageButton backMine;
	RelativeLayout share, feedback, help, annoouncement, clear, exitLogin;
	PopupWindow popupWindow;//自定义对话框

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.activity_set);
		initView();
	}

	// 初始化视图
	private void initView() {
		backMine = (ImageButton) findViewById(R.id.back_mine);
		share = (RelativeLayout) findViewById(R.id.share_next);
		feedback = (RelativeLayout) findViewById(R.id.feedback_next);
		help = (RelativeLayout) findViewById(R.id.user_help_next);
		annoouncement = (RelativeLayout) findViewById(R.id.new_announcement);
		clear = (RelativeLayout) findViewById(R.id.clear_cache_next);
		exitLogin = (RelativeLayout) findViewById(R.id.exit_login);
		// 触发点击
		share.setOnClickListener(clickListener);
		feedback.setOnClickListener(clickListener);
		help.setOnClickListener(clickListener);
		backMine.setOnClickListener(clickListener);
		clear.setOnClickListener(clickListener);
		exitLogin.setOnClickListener(clickListener);
	}

	// 点击按钮方法
	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back_mine:
				backMine();
				break;

			case R.id.feedback_next:
				toFeedback();
				break;
				
			case R.id.clear_cache_next:
				new AlertDialog.Builder(SetActivity.this)
						.setMessage("确定清除缓存吗？").setPositiveButton("确定", null)
						.setNegativeButton("取消", null).show();
				break;

			case R.id.exit_login:
				exitLogin();
				break;

			default:
				break;
			}
		}
	};
	
	//跳转到意见反馈界面
	private void toFeedback(){
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), FeedbackActivity.class);
		startActivity(intent);
	}

	// 返回我的界面
	private void backMine() {
		Intent intent = new Intent();
		intent.setClass(SetActivity.this, MineActivity.class);
		startActivity(intent);
	}


	// 退出登录
	private void exitLogin() {
		new AlertDialog.Builder(this)
				.setTitle("退出当前账号")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int whitch) {
						// 点击"确定"后操作
						// SetActivity.this.finish();
						// System.exit(0);
						// SetActivity.this.closeContextMenu();
						/*
						 * ActivityManager mgr = (ActivityManager)
						 * SetActivity.this.getSystemService(ACTIVITY_SERVICE);
						 * mgr.restartPackage(getPackageName());
						 */

						Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_HOME);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(intent);
						android.os.Process.killProcess(android.os.Process
								.myPid());
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int witch) {
						// 点击"取消"后操作，在这里不做任何操作

					}
				}).show();

	}

	// 判断退出登录对话框的点击事件
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			exitLogin();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
}
