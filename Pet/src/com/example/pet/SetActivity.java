package com.example.pet;

import com.example.pet.classes.SysApplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SetActivity extends Activity {

	ImageButton backMine;
	RelativeLayout share, feedback, help, annoouncement, clear, exitLogin;
	ImageView lockScreen;
	boolean state = false;// 锁屏密码是否打开，默认不打开
	PopupWindow popupWindow;// 自定义对话框
	View windView;
	LayoutInflater inflater;

	@SuppressLint("InflateParams")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.activity_set);
		SysApplication.getInstance().addActivity(this);

		inflater = this.getLayoutInflater();
		windView = inflater.inflate(R.layout.activity_exit, null);
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
		lockScreen = (ImageView) findViewById(R.id.lock_screen_off);
		// 触发点击
		share.setOnClickListener(clickListener);
		feedback.setOnClickListener(clickListener);
		help.setOnClickListener(clickListener);
		backMine.setOnClickListener(clickListener);
		clear.setOnClickListener(clickListener);
		exitLogin.setOnClickListener(clickListener);
		lockScreen.setOnClickListener(clickListener);
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
			case R.id.lock_screen_off:
				if (state == false) {
					lockScreen.setImageDrawable(getResources().getDrawable(
							R.drawable.on));
					state = true;
				} else if (state == true) {
					lockScreen.setImageDrawable(getResources().getDrawable(
							R.drawable.off));
					state = false;
				}
				break;
			case R.id.share_next:
				share();
				break;

			case R.id.feedback_next:
				toFeedback();
				break;
			case R.id.user_help_next:
				toUserHelp();
				break;

			case R.id.clear_cache_next:
				new AlertDialog.Builder(SetActivity.this)
						.setMessage("确定清除缓存吗？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int whitch) {
								
							}
						})
						.setNegativeButton("取消", null).show();
				break;

			case R.id.exit_login:
				createExitLoginPopupWindow();
				// exitLogin();
				break;

			default:
				break;
			}
		}
	};

	//分享
    public void share() {
    	Intent intent=new Intent(Intent.ACTION_SEND); 
    	intent.setType("image/*"); 
    	intent.putExtra(Intent.EXTRA_SUBJECT, "Share"); 
    	intent.putExtra(Intent.EXTRA_TEXT, "New Message");  
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
    	startActivity(Intent.createChooser(intent, getTitle()));
    }

	// 跳转到意见反馈界面
	private void toFeedback() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), FeedbackActivity.class);
		startActivity(intent);
	}
	
	//跳转到用户帮助界面 
	private void toUserHelp(){
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), UserHelpActivity.class);
		startActivity(intent);
	}

	// 返回我的界面
	private void backMine() {
		Intent intent = new Intent();
		intent.setClass(SetActivity.this, MainActivity.class);
		startActivity(intent);
	}

	// popupwindow
	@SuppressLint("ClickableViewAccessibility")
	@SuppressWarnings("deprecation")
	public void createExitLoginPopupWindow() {
		// 初始化一个popupWindow的对象并给以长和宽
		popupWindow = new PopupWindow(windView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		// 设置popupWindow背景，不设置则无法监听(背景设置为全透明)
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置popupWindow窗口外布局是否可以点击
		popupWindow.setOutsideTouchable(true);
		popupWindow.setTouchable(true);
		// 设置是否可以点击
		popupWindow.setTouchInterceptor(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		popupWindow.showAtLocation(windView, Gravity.FILL, 0, 0);
		TextView exitAccount = (TextView) windView
				.findViewById(R.id.exit_login_account);
		TextView closePet = (TextView) windView.findViewById(R.id.close_pet);
		View textBg = (View) windView.findViewById(R.id.exit_bg);
		exitAccount.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				exitAccount();
			}
		});
		closePet.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				exitLogin();
			}
		});
		textBg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
	}

	// 退出账号到登录界面
	public void exitAccount() {
		new AlertDialog.Builder(this)
				.setTitle("退出当前账号")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whitch) {
						// 点击"确定"后操作
						popupWindow.dismiss();
						saveLoginOut();
						Intent intent = new Intent();
						intent.setClass(getApplicationContext(),
								LoginActivity.class);
						startActivity(intent);
						SetActivity.this.finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whitch) {
						// 点击"取消"后操作，在这里不做任何操作
						popupWindow.dismiss();
					}
				}).show();
	}
	/**
	 * 退出时缓存用户已是退出状态 下次登录时需要进入登录界面
	 */
	public void saveLoginOut(){
		Editor editor=getSharedPreferences("pet", MODE_PRIVATE).edit();
    	editor.putInt("kills", 1);
    	editor.putInt("login", 0);
    	editor.commit();
	}

	// 退出登录
	private void exitLogin() {
		new AlertDialog.Builder(this).setTitle("关闭宠物")
				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int whitch) {
						// 点击"确定"后操作
						popupWindow.dismiss();
						//saveLoginOut();
						SysApplication.getInstance().exit();
						/*Intent intent = new Intent(Intent.ACTION_MAIN);
						intent.addCategory(Intent.CATEGORY_HOME);
						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						//finish();
						android.os.Process.killProcess(android.os.Process.myPid());*/
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int witch) {
						// 点击"取消"后操作，在这里不做任何操作
						popupWindow.dismiss();
					}
				}).show();

	}

	// 判断退出登录对话框的点击事件
	/*public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
			backMine();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
*/
}
