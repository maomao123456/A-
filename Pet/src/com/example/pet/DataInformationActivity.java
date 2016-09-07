package com.example.pet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

import com.example.pet.classes.SysApplication;
import com.example.pet.classes.Utils;

@SuppressLint("HandlerLeak")
public class DataInformationActivity extends Activity {

	ImageButton backMine;
	Button saveData;
	EditText nickname, birthday, star, occupation, company, address, hometown,
			email, remark;
	RadioGroup gender;
	RadioButton boy, girl;
	String temp = "男";

	/**
	 * 获得用户 登录后的id 获得当前用户id的方法
	 */
	String id;

	public void getId() {
		SharedPreferences pf = getSharedPreferences("pet_user", MODE_PRIVATE);
		id = pf.getString("id", "");
	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 取消标题栏
		setContentView(R.layout.activity_data_information);
		SysApplication.getInstance().addActivity(this);
		initView();
		getId();
		getUserData(id);
	}

	/**
	 * 获取用户信息
	 */
	private void getUserData(final String userid) {
		new Thread(new Runnable() {
			public void run() {
				StringBuilder builder = new StringBuilder();
				try {
					String httpPost = "http://192.168.1.192/index.php/Home/Pet/getuserinfo";
					String name = "id=" + userid;
					String urlName = httpPost + "?" + name;
					URL url = new URL(urlName);
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					conn.setConnectTimeout(5000);
					conn.setRequestProperty("accept", "*/*");
					conn.setRequestProperty("connection", "Keep-Alive");
					conn.setRequestProperty("user-agent",
							"Mozilla/4.0(compatible;MSIE 6.0;Windows NT5.1;SV1)");
					conn.setRequestProperty("accept-charset", "utf-8;GBK");
					conn.connect();// 建立连接
					InputStream inputStream = conn.getInputStream();
					Map<String, List<String>> headers = conn.getHeaderFields();
					for (String key : headers.keySet()) {
						System.out.println(key + "----" + headers.get(key));
					}
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(inputStream));
					String line = bufferedReader.readLine();
					while (line != null && line.length() > 0) {
						builder.append(line);
						line = bufferedReader.readLine();
					}
					bufferedReader.close();
					inputStream.close();
					Log.i("builder", builder.toString());
					str = builder.toString();
					Message msg = new Message();
					handler.sendMessage(msg);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	String str;
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			try {
				JSONObject jsonObject = new JSONObject(str);
				String nickName = jsonObject.getString("nickname");
				nickname.setText(nickName);
				String sex = jsonObject.getString("sex");
				if (sex.equals("男")) {
					boy.setChecked(true);
				} else {
					girl.setChecked(true);
				}
				String birth = jsonObject.getString("birthday");
				birthday.setText(birth);
				String constellation = jsonObject.getString("star");
				star.setText(constellation);
				String career = jsonObject.getString("occupation");
				occupation.setText(career);
				String firm = jsonObject.getString("company");
				company.setText(firm);
				String location = jsonObject.getString("address");
				address.setText(location);
				String birthland = jsonObject.getString("hometown");
				hometown.setText(birthland);
				String e_mail = jsonObject.getString("email");
				email.setText(e_mail);
				String personal = jsonObject.getString("remark");
				remark.setText(personal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	// 初始化视图
	private void initView() {
		backMine = (ImageButton) findViewById(R.id.back_mine);
		saveData = (Button) findViewById(R.id.save_user);
		nickname = (EditText) findViewById(R.id.input_nickname);
		birthday = (EditText) findViewById(R.id.input_birthday);
		star = (EditText) findViewById(R.id.input_star_sign);
		occupation = (EditText) findViewById(R.id.input_occupation);
		company = (EditText) findViewById(R.id.input_company);
		address = (EditText) findViewById(R.id.input_address);
		hometown = (EditText) findViewById(R.id.input_hometown);
		email = (EditText) findViewById(R.id.input_mail);
		remark = (EditText) findViewById(R.id.input_personality_desdcript);
		gender = (RadioGroup) findViewById(R.id.gender);
		boy = (RadioButton) findViewById(R.id.boy);
		girl = (RadioButton) findViewById(R.id.girl);
		// 触发点击
		backMine.setOnClickListener(clickListener);
		gender.setOnCheckedChangeListener(changeListener);
		saveData.setOnClickListener(clickListener);
	}

	// 点击按钮方法
	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.back_mine:
				backMine();
				break;
			case R.id.save_user:
				if (!checkEdit()) {
					Toast.makeText(getApplicationContext(), "未保存",
							Toast.LENGTH_SHORT).show();
					return;
				}
				Toast.makeText(getApplicationContext(), "正在保存.....",
						Toast.LENGTH_SHORT).show();
				saveData();
				break;

			default:
				break;
			}
		}
	};

	// 返回我的界面
	private void backMine() {
		Intent intent = new Intent();
		intent.setClass(DataInformationActivity.this, MainActivity.class);
		startActivity(intent);
	}

	// 保存数据时昵称不能为空
	public boolean checkEdit() {
		String e_mail = email.getText().toString();
		boolean rightEmail = Utils.isRightEmail(e_mail);
		if(!e_mail.isEmpty()&&rightEmail == false){
				Toast.makeText(getApplicationContext(), "请输入正确的邮箱地址",
						Toast.LENGTH_SHORT).show();
				return false;
		}else{
			return true;
		}
	}

	// 性别的选择
	OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			switch (checkedId) {
			case R.id.boy:
				if (boy.isChecked()) {
					temp = "男";
				} else {
					temp = "女";
				}

				break;
			case R.id.girl:
				if (girl.isChecked()) {
					temp = "女";
				} else {
					temp = "男";
				}
				break;

			default:
				break;
			}
		}
	};

	// 添加我的信息
	private void saveData() {
		new Thread(new Runnable() {
			public void run() {
				String httpUrl = "http://192.168.1.192/index.php/Home/Pet/update";// php接口地址
				HttpPost httpRequest = new HttpPost(httpUrl);// http用post方法请求数据
				List<NameValuePair> params = new ArrayList<NameValuePair>();// 建立一个列表用于添加数据
				params.add(new BasicNameValuePair("userid", id));// 添加获得的用户的账号
				params.add(new BasicNameValuePair("nickname", nickname
						.getText().toString()));// 添加用户的昵称
				params.add(new BasicNameValuePair("gender", temp));// 添加用户的性别
				params.add(new BasicNameValuePair("birthday", birthday
						.getText().toString()));// 添加用户的生日
				params.add(new BasicNameValuePair("star", star.getText()
						.toString()));// 添加用户的星座
				params.add(new BasicNameValuePair("occupation", occupation
						.getText().toString()));// 添加用户的职业
				params.add(new BasicNameValuePair("company", company.getText()
						.toString()));// 添加用户所在的公司
				params.add(new BasicNameValuePair("address", address.getText()
						.toString()));// 添加用户的所在地址
				params.add(new BasicNameValuePair("hometown", hometown
						.getText().toString()));// 添加用户故乡的所在地址
				params.add(new BasicNameValuePair("email", email.getText()
						.toString()));// 添加用户的邮箱地址
				params.add(new BasicNameValuePair("remark", remark.getText()
						.toString()));// 添加用户的个性说明
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
						Toast.makeText(DataInformationActivity.this,
								strResult + "保存成功", Toast.LENGTH_SHORT).show();
						Looper.loop();
					} else {
						Looper.prepare();
						Toast.makeText(getApplicationContext(), "保存失败",
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
