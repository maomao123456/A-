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

public class ResetTelephoneActivity extends Activity {

	ImageButton backAccountManager;
	Button saveNewTelephone;
	EditText newTelephone, confirmTelephone;
	TextView promptNewTelephone, promptEnsureTelephone;
	String userId = "123";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.activity_reset_telephone);
		SysApplication.getInstance().addActivity(this);
		initView();
	}

	// 初始化视图
	private void initView() {
		backAccountManager = (ImageButton) findViewById(R.id.back_account_manager);
		saveNewTelephone = (Button) findViewById(R.id.ensure_new_telephone);
		newTelephone = (EditText) findViewById(R.id.new_telephone);
		promptNewTelephone = (TextView) findViewById(R.id.prompt_new_telephone);
		confirmTelephone = (EditText) findViewById(R.id.confirm_telephone);
		promptEnsureTelephone = (TextView) findViewById(R.id.prompt_ensure_telephone);
		// 触发点击
		backAccountManager.setOnClickListener(clickListener);
		saveNewTelephone.setOnClickListener(clickListener);
	}

	// 按钮点击方法
	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back_account_manager:
				backAccountManager();
				break;
			case R.id.ensure_new_telephone:
				if (!checkEdit()) {
					return;
				}
				resetTelephone();
				break;

			default:
				break;
			}
		}
	};

	private boolean checkEdit() {
		String telephone = newTelephone.getText().toString();
		String ensureTelephone = confirmTelephone.getText().toString();
		boolean resultTelephone = Utils.isRightPhoneNumber(telephone);
		if (telephone.isEmpty()) {
			promptNewTelephone.setText("请输入新手机号");
			return false;
		} else if (resultTelephone == false) {
			promptNewTelephone.setText("请输入正确手机号码");
			return false;
		} else if (ensureTelephone.isEmpty()) {
			promptNewTelephone.setText("");
			promptEnsureTelephone.setText("请输入确认手机号");
			return false;
		} else if (resultTelephone == false) {
			promptEnsureTelephone.setText("请输入正确手机号码");
			return false;
		} else if (!ensureTelephone.equals(telephone)) {
			promptEnsureTelephone.setText("两次手机号不一致");
			return false;
		} else {
			promptEnsureTelephone.setText("");
			return true;
		}
	}

	// 返回管理设置界面
	private void backAccountManager() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), AccountManagerActivity.class);
		startActivity(intent);
		ResetTelephoneActivity.this.finish();
	}
	
	//修改手机号
		private void resetTelephone() {
			new Thread(new Runnable() {
				public void run() {
					String httpUrl = "http://192.168.1.182/index.php/Home/Pet/resettelephone";// php接口地址
					HttpPost httpRequest = new HttpPost(httpUrl);// http用post方法请求数据
					List<NameValuePair> params = new ArrayList<NameValuePair>();// 建立一个列表用于添加数据
					params.add(new BasicNameValuePair("userid", userId));// 添加获得的用户的账号
					params.add(new BasicNameValuePair("telephone", newTelephone.getText()
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
							Toast.makeText(ResetTelephoneActivity.this,
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
