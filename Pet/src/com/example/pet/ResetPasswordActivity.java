package com.example.pet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.example.pet.classes.SysApplication;
import com.example.pet.classes.Utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPasswordActivity extends Activity {

	ImageButton backAccountManager;
	Button saveNewPassword;
	EditText newPassword, confirmPassword;
	TextView promptNewPassword, promptEnsurePassword;
	String userId = "123";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.activity_reset_password);
		SysApplication.getInstance().addActivity(this);
		initView();
	}

	// 初始化视图
	private void initView() {
		backAccountManager = (ImageButton) findViewById(R.id.back_account_manager);
		saveNewPassword = (Button) findViewById(R.id.ensure_new_password);
		newPassword = (EditText) findViewById(R.id.new_password);
		promptNewPassword = (TextView) findViewById(R.id.prompt_new_password);
		confirmPassword = (EditText) findViewById(R.id.confirm_password);
		promptEnsurePassword = (TextView) findViewById(R.id.prompt_ensure_password);
		// 触发点击
		backAccountManager.setOnClickListener(clickListener);
		saveNewPassword.setOnClickListener(clickListener);
	}

	// 按钮点击方法
	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back_account_manager:
				backAccountManager();
				break;
			case R.id.ensure_new_password:
				if (!checkEdit()) {
					return;
				}
				resetPassword();
				break;

			default:
				break;
			}
		}
	};

	//输入框
	private boolean checkEdit() {
		String password = newPassword.getText().toString();
		String ensurePassword = confirmPassword.getText().toString();
		boolean resultPassword = Utils.isRightPassword(password);
		if (password.isEmpty()) {
			promptNewPassword.setText("请输入新密码");
			return false;
		} else if (resultPassword == false) {
			promptNewPassword.setText("密码为大小写字母或数字");
			return false;
		} else if(password.length() < 6 || password.length() > 20){
			promptNewPassword.setText("密码长度为6~20");
			return false;
		} else if (ensurePassword.isEmpty()) {
			promptNewPassword.setText("");
			promptEnsurePassword.setText("请输入确认密码");
			return false;
		} else if (resultPassword == false) {
			promptNewPassword.setText("密码为大小写字母或数字");
			return false;
		} else if (!ensurePassword.equals(password)) {
			promptEnsurePassword.setText("两次密码不一致");
			return false;
		} else {
			promptEnsurePassword.setText("");
			return true;
		}
	}

	// 返回管理设置界面
	private void backAccountManager() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), AccountManagerActivity.class);
		startActivity(intent);
		ResetPasswordActivity.this.finish();
	}

	//修改密码
	private void resetPassword() {
		new Thread(new Runnable() {
			public void run() {
				String httpUrl = "http://192.168.1.182/index.php/Home/Pet/resetpassword";// php接口地址
				HttpPost httpRequest = new HttpPost(httpUrl);// http用post方法请求数据
				List<NameValuePair> params = new ArrayList<NameValuePair>();// 建立一个列表用于添加数据
				params.add(new BasicNameValuePair("userid", userId));// 添加获得的用户的账号
				params.add(new BasicNameValuePair("password", newPassword.getText()
						.toString()));// 添加用户的邮箱地址
				try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(params,
							"utf-8");// 设置用户的字符集格式
					httpRequest.setEntity(httpEntity);// 请求字符集数据
					HttpClient httpClient = new DefaultHttpClient();// http客户端
					HttpResponse httpResponse = httpClient.execute(httpRequest);// http客户端请求响应
					if (httpResponse.getStatusLine().getStatusCode() == 200) {// http请求响应成功
						String strResult = null;
						strResult = EntityUtils.toString(httpResponse
								.getEntity());
						Looper.prepare();
						Toast.makeText(ResetPasswordActivity.this,
								strResult + "修改成功", Toast.LENGTH_SHORT).show();
						Looper.loop();
					} else {
						Looper.prepare();
						Toast.makeText(getApplicationContext(), "修改失败",
								Toast.LENGTH_SHORT).show();
						Looper.loop();
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();
	}
}
