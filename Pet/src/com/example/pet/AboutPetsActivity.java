package com.example.pet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import update.UpdateInfo;
import update.UpdateInfoService;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.classes.SysApplication;

public class AboutPetsActivity extends Activity {

	ImageButton backMine;// 返回按钮
	RelativeLayout rating, function, system, report, version;// 功能布局
	TextView versionID;// 版本号

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.activity_about_lovely_pets);
		SysApplication.getInstance().addActivity(this);// 添加Activity页面到SysApplication
		initView();
	}

	// 初始化视图
	private void initView() {
		backMine = (ImageButton) findViewById(R.id.back_mine);
		rating = (RelativeLayout) findViewById(R.id.rating_next);
		function = (RelativeLayout) findViewById(R.id.function_next);
		system = (RelativeLayout) findViewById(R.id.system_notification_next);
		report = (RelativeLayout) findViewById(R.id.report_complaint_next);
		version = (RelativeLayout) findViewById(R.id.check_new_version_next);
		versionID = (TextView) findViewById(R.id.about_lovely_pets_bb_num);
		// 触发点击
		backMine.setOnClickListener(clickListener);
		rating.setOnClickListener(clickListener);
		function.setOnClickListener(clickListener);
		version.setOnClickListener(clickListener);
	}

	// 按钮点击方法
	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			// 返回我的页面
			case R.id.back_mine:
				backMine();
				break;
			// 评分
			case R.id.rating_next:
				goToRating();
				break;
			// 功能介绍
			case R.id.function_next:
				goToFunction();
				break;
			// 检查新版本
			case R.id.check_new_version_next:
				checkVersion();
				break;

			default:
				break;
			}
		}
	};

	// 返回我的界面
	private void backMine() {
		Intent intent = new Intent();
		intent.setClass(AboutPetsActivity.this, MainActivity.class);
		startActivity(intent);
	}

	// 启动应用市场去评分
	public void goToRating() {
		// 只能跳转到手机上安装的应用商店，若没有，则无法跳转
		Uri uri = Uri.parse("market://details?id=" + getPackageName());
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	// 跳转到功能介绍页面
	public void goToFunction() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), FunctionGuideActivity.class);
		startActivity(intent);
	}

	// 检查新版本
	// 更新版本需要的信息
	private UpdateInfo info;
	private ProgressDialog pBar;

	private void checkVersion() {
		Toast.makeText(getApplicationContext(), "正在检查版本更新...",
				Toast.LENGTH_SHORT).show();
		/**
		 * 自动检查是否需要版本更新
		 */
		new Thread(new Runnable() {
			public void run() {
				try {
					UpdateInfoService updateInfoService = new UpdateInfoService(
							AboutPetsActivity.this);
					info = updateInfoService.getUpdateInfo();
					handler1.sendEmptyMessage(0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	@SuppressLint("HandlerLeak")
	private Handler handler1 = new Handler() {
		public void handleMessage(Message msg) {
			// 如果有更新就提示
			if (isNeedUpdate()) {
				showUpdateDialog();
			}
		}
	};

	/**
	 * 判断最新版本
	 */
	private void showUpdateDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setIcon(android.R.drawable.ic_dialog_info);
		builder.setTitle("请升级APP至版本" + info.getVersion());
		builder.setMessage(info.getDescription());
		builder.setCancelable(false);// 默认设置不可取消
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					downFile(info.getUrl());
				} else {
					Toast.makeText(getApplicationContext(), "SD卡不可用，请插入SD卡",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
			}
		});

		builder.create().show();
	}

	/**
	 * 判断是否需要更新
	 * @return
	 */
	private boolean isNeedUpdate() {
		String version = info.getVersion();// 最新版本的版本号
		Toast.makeText(getApplicationContext(), version, Toast.LENGTH_SHORT)
				.show();
		if (version.equals(getVersion())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 获得当前版本的版本号
	 * @return
	 */
	private String getVersion() {
		try {
			PackageManager packageManager = getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					getPackageName(), 0);
			return packageInfo.versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			return "版本号未知";
		}
	}

	private void downFile(final String url) {
		pBar = new ProgressDialog(AboutPetsActivity.this);// 进度条，在下载的时候实时更新进度，提高用户友好度
		pBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//进度条的样式
		pBar.setTitle("正在下载");
		pBar.setMessage("请稍后...");
		pBar.setProgress(0);
		pBar.show();
		new Thread(new Runnable() {
			public void run() {
				HttpClient client = new DefaultHttpClient();
				HttpGet get = new HttpGet(url);
				HttpResponse response;
				try {
					response = client.execute(get);// 客户端请求方式
					HttpEntity entity = response.getEntity();// 客户端请求字符集
					int length = (int) entity.getContentLength();// 获取文件大小
					pBar.setMax(length);// 设置进度条的总长度
					InputStream is = entity.getContent();
					FileOutputStream fileOutputStream = null;
					if (is != null) {
						File file = new File(
								Environment.getExternalStorageDirectory(),
								"Pet.apk");
						fileOutputStream = new FileOutputStream(file);
						byte[] buffer = new byte[10];
						int ch = -1;
						int process = 0;
						while ((ch = is.read(buffer)) != -1) {
							fileOutputStream.write(buffer, 0, ch);
							process += ch;
							pBar.setProgress(process);// 实时更新
						}
					}
					fileOutputStream.flush();
					if (fileOutputStream != null) {
						fileOutputStream.close();
					}
					down();
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void down() {
		handler1.post(new Runnable() {
			public void run() {
				pBar.cancel();
				update();
			}
		});
	}

	/**
	 * 安装文件，一般学法固定
	 */
	private void update() {
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(new File(Environment
				.getExternalStorageDirectory(), "Pet.apk")),
				"application/vnd.android.package-archive");
		startActivity(intent);
	}
}
