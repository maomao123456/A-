package com.example.pet;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.Toast;

import com.example.pet.classes.DataClearManager;
import com.example.pet.classes.SysApplication;

public class SetActivity extends Activity {

	ImageButton backMine;
	RelativeLayout share, feedback, help, annoouncement, clear, exitLogin;
	ImageView lockScreen;
	boolean state = false;// 锁屏密码是否打开，默认不打开
	PopupWindow popupWindowExitLogin, popupWindowShare;// 自定义对话框
	View exitLoginWindView, shareWindView;
	LayoutInflater exitLoginInflater, shareInflater;

	@SuppressLint("InflateParams")
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.activity_set);
		SysApplication.getInstance().addActivity(this);

		//退出登录
		exitLoginInflater = this.getLayoutInflater();
		exitLoginWindView = exitLoginInflater.inflate(R.layout.activity_exit, null);
		//分享
		shareInflater = this.getLayoutInflater();
		shareWindView = shareInflater.inflate(R.layout.activity_share, null);
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
					Toast.makeText(getApplicationContext(), "因权限问题，锁屏功能暂无法实现",
							Toast.LENGTH_SHORT).show();
				} else if (state == true) {
					lockScreen.setImageDrawable(getResources().getDrawable(
							R.drawable.off));
					state = false;
				}
				break;
			case R.id.share_next:
				//createSharePopupWindow();
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
						.setMessage("确定清除缓存吗？")
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int whitch) {
										DataClearManager
												.cleanInternalCache(getApplicationContext());
										toastClearCache();
									}
								}).setNegativeButton("取消", null).show();
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

	// 锁屏功能的实现
	// private DevicePolicyManager policyManager;
	// private ComponentName componentName;
	/*
	 * private void lock(){ policyManager = (DevicePolicyManager)
	 * getSystemService(Context.DEVICE_POLICY_SERVICE); componentName = new
	 * ComponentName(SetActivity.this, LockReceiver.class);
	 * 
	 * // 判断是否有权限激活设备管理器 if(policyManager.isAdminActive(componentName)){
	 * policyManager.lockNow();//直接锁屏
	 * android.os.Process.killProcess(android.os.Process.myPid());
	 * 
	 * } else { activeManager();//激活设备管理器获取权限 } }
	 * 
	 * // 重写此方法用来在第一次激活设备管理器之后锁定屏幕 protected void onResume(){
	 * if(policyManager.isAdminActive(componentName)){ policyManager.lockNow();
	 * android.os.Process.killProcess(android.os.Process.myPid()); }
	 * super.onResume(); }
	 * 
	 * // 激活设备管理器获取锁屏的权限 private void activeManager(){ //使用隐式意图调用系统方法来激活指定的设备管理器
	 * Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
	 * intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
	 * intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "一键锁屏");
	 * startActivity(intent); }
	 */

	// 清除缓存提示
	public void toastClearCache() {
		final ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("正在清除缓存....");
		progressDialog.show();
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			public void run() {
				progressDialog.dismiss();
				Toast.makeText(getApplicationContext(), "缓存清除完成！",
						Toast.LENGTH_SHORT).show();
			}
		}, 3000);

	}
	
	@SuppressWarnings("deprecation")
	public void createSharePopupWindow(){
		popupWindowShare = new PopupWindow(shareWindView, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
		popupWindowShare.setBackgroundDrawable(new BitmapDrawable());
		popupWindowShare.setOutsideTouchable(true);
		popupWindowShare.setTouchable(true);
		popupWindowShare.setTouchInterceptor(new OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		popupWindowShare.showAtLocation(shareWindView, Gravity.FILL, 0, 0);
		TextView shareCancel = (TextView) shareWindView.findViewById(R.id.cancel_share);
		TextView shareSend = (TextView) shareWindView.findViewById(R.id.send_share);
		TextView linkPet = (TextView) shareWindView.findViewById(R.id.link_pet);
		TextView youFriends = (TextView) shareWindView.findViewById(R.id.you_friends);
		
		/**
		 * 取消分享
		 */
		shareCancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				popupWindowShare.dismiss();
			}
		});
		
		/**
		 * 发送分享
		 */
		shareSend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				
			}
		});
	}

	/**
	 * 安装包地址
	 */
	String linkPath = "https://github.com/maomao123456/Pet.git";
	// 分享
	public void share() {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		intent.setType("text/plain");
		intent.putExtra(Intent.EXTRA_SUBJECT, linkPath);
		intent.putExtra(Intent.EXTRA_TEXT, linkPath);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(Intent.createChooser(intent, getTitle()));
	}
	
	/**   
	  * 分享功能   
	  * @param context 上下文   
	  * @param activityTitle Activity的名字   
	  * @param msgTitle 消息标题   
	  * @param msgText 消息内容   
	  * @param imgPath 图片路径，不分享图片则传null   
	  */  
	public void shareMsg(Context context, Bundle activityTitle, String msgTitle, String msgText, String imgPath){
		Intent intent = new Intent(Intent.ACTION_SEND);
		if(imgPath == null || imgPath.equals("")){
			intent.setType("text/plain");//chun wen ben
		} else {
			File file = new File(imgPath);
			if(file != null && file.exists() && file.isFile()){
				intent.setType("image/*");
				Uri uri = Uri.fromFile(file);
				intent.putExtra(Intent.EXTRA_STREAM, uri);
			}
			intent.putExtra(Intent.EXTRA_SUBJECT, "Share");
			intent.putExtra(Intent.EXTRA_TEXT, msgText);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(Intent.createChooser(intent, getTitle()));
		}
	}

	// 跳转到意见反馈界面
	private void toFeedback() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), FeedbackActivity.class);
		startActivity(intent);
	}

	// 跳转到用户帮助界面
	private void toUserHelp() {
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

	// popupwindowExitLogin
	@SuppressLint("ClickableViewAccessibility")
	@SuppressWarnings("deprecation")
	public void createExitLoginPopupWindow() {
		// 初始化一个popupWindow的对象并给以长和宽
		popupWindowExitLogin = new PopupWindow(exitLoginWindView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		// 设置popupWindow背景，不设置则无法监听(背景设置为全透明)
		popupWindowExitLogin.setBackgroundDrawable(new BitmapDrawable());
		// 设置popupWindow窗口外布局是否可以点击
		popupWindowExitLogin.setOutsideTouchable(true);
		popupWindowExitLogin.setTouchable(true);
		// 设置是否可以点击
		popupWindowExitLogin.setTouchInterceptor(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		popupWindowExitLogin.showAtLocation(exitLoginWindView, Gravity.FILL, 0, 0);
		TextView exitAccount = (TextView) exitLoginWindView
				.findViewById(R.id.exit_login_account);
		TextView closePet = (TextView) exitLoginWindView.findViewById(R.id.close_pet);
		View textBg = (View) exitLoginWindView.findViewById(R.id.exit_bg);
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
				popupWindowExitLogin.dismiss();
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
						popupWindowExitLogin.dismiss();
						saveLoginOut();
						Intent intent = new Intent();
						intent.setClass(getApplicationContext(),
								LoginActivity.class);
						startActivity(intent);
						MainActivity.instance.finish();
						SetActivity.this.finish();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int whitch) {
						// 点击"取消"后操作，在这里不做任何操作
						popupWindowExitLogin.dismiss();
					}
				}).show();
	}

	/**
	 * 退出时缓存用户已是退出状态 下次登录时需要进入登录界面
	 */
	public void saveLoginOut() {
		Editor editor = getSharedPreferences("pet", MODE_PRIVATE).edit();
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
						popupWindowExitLogin.dismiss();
						saveLoginOut();// 请勿屏蔽此方法 下次进入时需要登录
						SysApplication.getInstance().exit();
					}
				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int witch) {
						// 点击"取消"后操作，在这里不做任何操作
						popupWindowExitLogin.dismiss();
					}
				}).show();

	}
}
