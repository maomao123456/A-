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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class DataInformationActivity extends Activity {
	
	String userid = "123223";
	String password = "123123";
	ImageButton backMine;
	Button saveData;
	EditText nickname, birthday, star, occupation, company, address, hometown, email, remark;
	RadioGroup gender;
	RadioButton boy, girl;
	String temp = "男";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);//取消标题栏
		setContentView(R.layout.activity_data_information);
		SysApplication.getInstance().addActivity(this);
		initView();
	}
	
	//初始化视图
	private void initView(){
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
		//触发点击
		backMine.setOnClickListener(clickListener);
		gender.setOnCheckedChangeListener(changeListener);
		saveData.setOnClickListener(clickListener);
	}
	
	//点击按钮方法
	OnClickListener clickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back_mine:
				backMine();
				break;
			case R.id.save_user:
				if(!checkEdit()){
					return;
				}
				saveData();
				break;

			default:
				break;
			}
		}
	};
	
	//返回我的界面
	private void backMine(){
		Intent intent = new Intent();
		intent.setClass(DataInformationActivity.this, MainActivity.class);
		startActivity(intent);
	}
	
	//保存数据时昵称不能为空
	public boolean checkEdit(){
		String nickName = nickname.getText().toString();
		String e_mail = email.getText().toString();
		boolean rightEmail = Utils.isRightEmail(e_mail);
		if(nickName.isEmpty()){
			Toast.makeText(getApplicationContext(), "保存失败，请输入昵称", Toast.LENGTH_SHORT).show();
		} else if(!e_mail.isEmpty()){
			if(rightEmail == false){
				Toast.makeText(getApplicationContext(), "请输入正确的邮箱地址", Toast.LENGTH_SHORT).show();
			}
		} else {
			return true;
		}
		return false;
	}
	
	//性别的选择
	OnCheckedChangeListener changeListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			// TODO Auto-generated method stub
			switch (checkedId) {
			case R.id.boy:
				if(boy.isChecked()){
					temp = "男";
				} else {
					temp = "女";
				}
				
				break;
			case R.id.girl:
				if(girl.isChecked()){
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
	
	//添加我的信息
	private void saveData(){
		new Thread(new Runnable(){
			public void run(){
				String httpUrl = "http://192.168.1.182/index.php/Home/Pet/userinfo";//php接口地址
				HttpPost httpRequest = new HttpPost(httpUrl);//http用post方法请求数据
				List<NameValuePair> params = new ArrayList<NameValuePair>();//建立一个列表用于添加数据
				params.add(new BasicNameValuePair("userid", userid));//添加获得的用户的账号
				params.add(new BasicNameValuePair("password", password));//添加获得的用户的密码
				params.add(new BasicNameValuePair("nickname", nickname.getText().toString()));//添加用户的昵称
				params.add(new BasicNameValuePair("gender", temp));//添加用户的性别
				params.add(new BasicNameValuePair("birthday", birthday.getText().toString()));//添加用户的生日
				params.add(new BasicNameValuePair("star", star.getText().toString()));//添加用户的星座
				params.add(new BasicNameValuePair("occupation", occupation.getText().toString()));//添加用户的职业
				params.add(new BasicNameValuePair("company", company.getText().toString()));//添加用户所在的公司
				params.add(new BasicNameValuePair("address", address.getText().toString()));//添加用户的所在地址
				params.add(new BasicNameValuePair("hometown", hometown.getText().toString()));//添加用户故乡的所在地址
				params.add(new BasicNameValuePair("email", email.getText().toString()));//添加用户的邮箱地址
				params.add(new BasicNameValuePair("remark", remark.getText().toString()));//添加用户的个性说明
				try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");//设置用户的字符集格式
					httpRequest.setEntity(httpEntity);//请求字符集数据
					HttpClient httpClient = new DefaultHttpClient();//http客户端
					HttpResponse httpResponse = httpClient.execute(httpRequest);//http客户端请求响应
					if(httpResponse.getStatusLine().getStatusCode() == 200){//http请求响应成功
						String strResult = null;
						strResult = EntityUtils.toString(httpResponse
								.getEntity());
						Looper.prepare();
						Toast.makeText(DataInformationActivity.this, strResult + "保存成功", Toast.LENGTH_SHORT).show();
						Looper.loop();
					} else {
						Looper.prepare();
						Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
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
