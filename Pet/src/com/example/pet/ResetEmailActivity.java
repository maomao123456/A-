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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.pet.classes.SysApplication;
import com.example.pet.classes.Utils;

public class ResetEmailActivity extends Activity {

	ImageButton backAccountManager;
	Button saveNewEmail;
	EditText newEmail, confirmEmail;
	TextView promptNewEmail, promptEnsureEmail;
	
	/**
	 * 获得用户 登录后的id 获得当前用户id的方法
	 */
	String id;
	public void getId() {
		SharedPreferences pf = getSharedPreferences("pet_user", MODE_PRIVATE);
		id = pf.getString("id", "");
	}

	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.activity_reset_email);
		SysApplication.getInstance().addActivity(this);

		// Bundle bundle = this.getIntent().getExtras();
		// userId = bundle.getString("userid");
		initView();
	}

	// 初始化视图
	private void initView() {
		backAccountManager = (ImageButton) findViewById(R.id.back_account_manager);
		saveNewEmail = (Button) findViewById(R.id.ensure_new_mail);
		newEmail = (EditText) findViewById(R.id.new_mail);
		promptNewEmail = (TextView) findViewById(R.id.prompt_new_email);
		confirmEmail = (EditText) findViewById(R.id.confirm_mail);
		promptEnsureEmail = (TextView) findViewById(R.id.prompt_ensure_email);
		// 触发点击
		backAccountManager.setOnClickListener(clickListener);
		saveNewEmail.setOnClickListener(clickListener);
	}

	// 按钮点击方法
	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back_account_manager:
				backAccountManager();
				break;
			case R.id.ensure_new_mail:
				if (!checkEdit()) {
					return;
				}
				resetEmail();
				break;

			default:
				break;
			}
		}
	};

	//输入框
	private boolean checkEdit() {
		String email = newEmail.getText().toString();
		String ensureEmail = confirmEmail.getText().toString();
		boolean resultEmail = Utils.isRightEmail(email);
		if (email.isEmpty()) {
			promptNewEmail.setText("请输入新邮箱");
			return false;
		} else if (resultEmail == false) {
			promptNewEmail.setText("请输入正确邮箱");
			return false;
		} else if (ensureEmail.isEmpty()) {
			promptNewEmail.setText("");
			promptEnsureEmail.setText("请输入确认邮箱");
			return false;
		} else if (resultEmail == false) {
			promptNewEmail.setText("请输入正确邮箱");
			return false;
		} else if (!ensureEmail.equals(email)) {
			promptEnsureEmail.setText("两次邮箱地址不一致");
			return false;
		} else {
			promptEnsureEmail.setText("");
			return true;
		}
	}

	// 返回管理设置界面
	private void backAccountManager() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), AccountManagerActivity.class);
		startActivity(intent);
		ResetEmailActivity.this.finish();
	}

	//修改邮箱
	private void resetEmail() {
		new Thread(new Runnable() {
			public void run() {
				String httpUrl = "http://192.168.1.182/index.php/Home/Pet/resetemail";// php接口地址
				HttpPost httpRequest = new HttpPost(httpUrl);// http用post方法请求数据
				List<NameValuePair> params = new ArrayList<NameValuePair>();// 建立一个列表用于添加数据
				params.add(new BasicNameValuePair("userid", id));// 添加获得的用户的账号
				params.add(new BasicNameValuePair("email", newEmail.getText()
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
						Toast.makeText(ResetEmailActivity.this,
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
