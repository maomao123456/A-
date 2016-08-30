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
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.classes.SysApplication;
import com.example.pet.classes.Utils;

public class FeedbackActivity extends Activity {

	String userId = "1235833";
	ImageButton backSet;// 返回设置页面
	Button closeFeedback, submitFeedback;// 关闭意见反馈页面，提交反馈的意见
	EditText inputFeedback, contantPhone;// 输入反馈的意见和电话联系方式
	ImageView addImage;// 添加图片
	TextView textPrompt, maxNum;// 提示用户输入正确的电话号码，输入文字显示的最大数字

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.activity_feedback);
		SysApplication.getInstance().addActivity(this);
		initView();
	}

	// 初始化视图
	private void initView() {
		backSet = (ImageButton) findViewById(R.id.back_set);
		closeFeedback = (Button) findViewById(R.id.close_feedback);
		submitFeedback = (Button) findViewById(R.id.submit_feedback);
		inputFeedback = (EditText) findViewById(R.id.input_question);
		contantPhone = (EditText) findViewById(R.id.input_contant_phone);
		addImage = (ImageView) findViewById(R.id.add_pictrue);
		textPrompt = (TextView) findViewById(R.id.text_prompt);
		maxNum = (TextView) findViewById(R.id.max_num);
		// 触发点击
		backSet.setOnClickListener(clickListener);
		closeFeedback.setOnClickListener(clickListener);
		submitFeedback.setOnClickListener(clickListener);
	}

	// 按钮点击方法
	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back_set:
				backSet();
				break;
			case R.id.close_feedback:
				closeFeedback();
				break;
			case R.id.submit_feedback:
				if (!checkEdit()) {
					return;
				}
				addFeedback();
				break;

			default:
				break;
			}
		}
	};

	// 返回设置界面
	private void backSet() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), SetActivity.class);
		startActivity(intent);
		FeedbackActivity.this.finish();
	}

	// 关闭当前意见反馈界面
	private void closeFeedback() {
		Intent intent = new Intent();
		intent.setClass(getApplicationContext(), SetActivity.class);
		startActivity(intent);
		FeedbackActivity.this.finish();
	}

	public int wordLength() {
		int i, j = 400;
		String feedback = inputFeedback.getText().toString();
		for (i = 0; i < feedback.length(); i++) {
			j = j - feedback.length();
		}
		return j;
	}

	public boolean checkEdit() {
		String conPhone = contantPhone.getText().toString();
		String feedback = inputFeedback.getText().toString();
		boolean rightPhone = Utils.isRightPhoneNumber(conPhone);
		if (feedback.isEmpty()) {
			textPrompt.setText("提交失败，请填写问题或建议");
		} else if (!conPhone.isEmpty()) {
			if (rightPhone == false) {
				textPrompt.setText("请输入正确的电话号码");
			}
		} else {
			textPrompt.setText("");
			return true;
		}
		return false;
	}

	private void addFeedback() {
		new Thread(new Runnable() {
			public void run() {
				/*
				 * StringBuilder builder = new StringBuilder(); try{ String
				 * httpPost =
				 * "http://192.168.1.145/index.php/Home/Pet/addFeedback";
				 * }catch(Exception e){e.printStackTrace();}
				 */
				String httpUrl = "http://192.168.1.182/index.php/Home/Pet/userfeedback";// PHP接口地址
				HttpPost httpRequest = new HttpPost(httpUrl);// http用post方法请求数据
				List<NameValuePair> params = new ArrayList<NameValuePair>();// 建立一个列表用于添加数据
				// params.add(new BasicNameValuePair("id", ))
				params.add(new BasicNameValuePair("userid", userId));// 添加用户ID
				params.add(new BasicNameValuePair("feedback", inputFeedback
						.getText().toString().trim()));// 添加用户反馈的意见或遇到的问题
				params.add(new BasicNameValuePair("conphone", contantPhone
						.getText().toString().trim()));// 天机用户的联系电话
				try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(params,
							"utf-8");// 设置用户字符集的格式
					httpRequest.setEntity(httpEntity);
					HttpClient httpClient = new DefaultHttpClient();// http客户端
					HttpResponse httpResponse = httpClient.execute(httpRequest);// http客户端请求响应
					if (httpResponse.getStatusLine().getStatusCode() == 200) {// http客户端响应成功
						String strResult = null;
						strResult = EntityUtils.toString(httpResponse
								.getEntity());
						Looper.prepare();
						Toast.makeText(getApplicationContext(),
								strResult + "提交成功", Toast.LENGTH_SHORT).show();
						Looper.loop();
					} else {
						Looper.prepare();
						Toast.makeText(getApplicationContext(), "提交失败",
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
