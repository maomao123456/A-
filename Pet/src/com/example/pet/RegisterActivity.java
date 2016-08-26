package com.example.pet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.lei.PhoneTy;

public class RegisterActivity extends Activity implements OnClickListener {
	TextView denglu, getYangzhengma, xieyiNeirong, comeing;
	LinearLayout backQuyu, xieyiQuyu;
	EditText shoujihao, yanzhengma, shezhimima, yaoqingma;
	CheckBox xieyiCb,showMima;
	ScrollView sc;
	/**
	 * 获得用户输入的手机号
	 */
	String phoneNumb;
	/**
	 * 获得用户输入的验证码
	 */
	String yanzhengCd;
	/**
	 * 获得用户输入的密码
	 */
	String password;
	/**
	 * 获得用户输的邀请码
	 */
	String inviteCd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN|
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setContentView(R.layout.activity_register);
		initView();

	}

	/**
	 * 通过id找到控件
	 */
	public void initView() {
		sc=(ScrollView)findViewById(R.id.register_scroll);
		denglu = (TextView) findViewById(R.id.register_denglu);
		getYangzhengma = (TextView) findViewById(R.id.register_huoquyanzhengma);
		xieyiNeirong = (TextView) findViewById(R.id.register_xieyi_neirong);
		comeing = (TextView) findViewById(R.id.register_lijijiaru);
		backQuyu = (LinearLayout) findViewById(R.id.register_back_quyu);
		xieyiQuyu = (LinearLayout) findViewById(R.id.register_tongyixieyi_quyu);
		shoujihao = (EditText) findViewById(R.id.register_shoujihao);
		yanzhengma = (EditText) findViewById(R.id.register_yanzhengma);
		shezhimima = (EditText) findViewById(R.id.register_shezhimima);
		yaoqingma = (EditText) findViewById(R.id.register_yaoqingma);
		xieyiCb = (CheckBox) findViewById(R.id.register_xieyi_cb);
		showMima=(CheckBox)findViewById(R.id.register_show_mima);
		showMima.setOnClickListener(this);
		denglu.setOnClickListener(this);
		getYangzhengma.setOnClickListener(this);
		xieyiNeirong.setOnClickListener(this);
		comeing.setOnClickListener(this);
		backQuyu.setOnClickListener(this);
		xieyiQuyu.setOnClickListener(this);
		shoujihao.setOnClickListener(this);
		shoujihao.setOnFocusChangeListener(focusChangeListener);
		yanzhengma.setOnFocusChangeListener(focusChangeListener);
		shezhimima.setOnFocusChangeListener(focusChangeListener);
	}
	/**
	 * 布局尺寸的调整
	 */
	public void changeSize(){
		Handler mHandler = new Handler(); 
		mHandler.postDelayed(new Runnable() {
			 @Override public void run() { 
			sc.fullScroll(ScrollView.FOCUS_DOWN); } },300); 
	}
	public final String format = "^([A-Za-z]|[0-9])+$";//密码格式
	public final String format2 = "^([A-Za-z0-9])+$";//密码格式
	/**
	 * editText的聚焦监听
	 */
	OnFocusChangeListener focusChangeListener = new OnFocusChangeListener() {
		public void onFocusChange(View v, boolean hasFocus) {
			if (!hasFocus) {
				phoneNumb = shoujihao.getText().toString();
				boolean phone = PhoneTy.isPhoneNb(phoneNumb);
				yanzhengCd = yanzhengma.getText().toString();
				password = shezhimima.getText().toString();
				inviteCd = yaoqingma.getText().toString();
				switch (v.getId()) {
				case R.id.register_shoujihao:
					if(!phone){
						Toast.makeText(getApplicationContext(), "请输入正确的手机号！",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case R.id.register_yanzhengma:
					if(yanzhengCd.length()<4||yanzhengCd.length()>6){
						Toast.makeText(getApplicationContext(), "验证码长度为4~6位",
								Toast.LENGTH_SHORT).show();
					} 
					break;
				case R.id.register_shezhimima:
					if(password.length()<6||password.length()>20){
						Toast.makeText(getApplicationContext(), "密码为6-20位数字或字母!",
								Toast.LENGTH_SHORT).show();
					}else if(!password.matches(format2)){
						Toast.makeText(getApplicationContext(), "密码为6-20位数字或字母!",
								Toast.LENGTH_SHORT).show();
					}
					break;

				default:
					break;
				}
			}
		}
	};
	/**
	 * 检查用户输入
	 */
	public boolean checked(){
		phoneNumb=shoujihao.getText().toString();
		boolean phone=PhoneTy.isPhoneNb(phoneNumb);
		yanzhengCd=yanzhengma.getText().toString();
		password=shezhimima.getText().toString();
		inviteCd=yaoqingma.getText().toString();
		if(!phone){
			Toast.makeText(getApplicationContext(), "请输入正确的手机号！",
					Toast.LENGTH_SHORT).show();
			return false;
		}else if(yanzhengCd.length()<4||yanzhengCd.length()>6){
			Toast.makeText(getApplicationContext(), "格式错误：验证码长度4~6位",
					Toast.LENGTH_SHORT).show();
			return false;
		}else if(password.length()<6||password.length()>20){
			Toast.makeText(getApplicationContext(), "密码只能是6-20位数字或字母!",
					Toast.LENGTH_SHORT).show();
			return false;
		}else if(!password.matches(format2)){
			Toast.makeText(getApplicationContext(), "密码只能是数字或字母!",
					Toast.LENGTH_SHORT).show();
			return false;
		}else if(yaoqingma.getText()==null||yaoqingma.getText().toString().trim().length()!=0){
			Toast.makeText(getApplicationContext(), "邀请码不正确！"+yaoqingma.getText().toString(),
					Toast.LENGTH_SHORT).show();
			return false;
		}else{
			return true;
		}
	}

	/**
	 * 普通控件的点击事件
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.register_shoujihao:
			changeSize();
			break;
		case R.id.register_show_mima://是否显示密码图标
			if(showMima.isChecked()){
				shezhimima.setInputType(EditorInfo.TYPE_CLASS_TEXT |EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			}else{
				shezhimima.setInputType(EditorInfo.TYPE_CLASS_TEXT |EditorInfo.TYPE_TEXT_VARIATION_WEB_PASSWORD);
			}
			break;
		case R.id.register_back_quyu:// 返回区域
			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(intent);
			RegisterActivity.this.finish();
			break;
		case R.id.register_denglu:// 登录区域
			intent = new Intent(getApplicationContext(), LoginActivity.class);
			startActivity(intent);
			RegisterActivity.this.finish();
			break;
		case R.id.register_huoquyanzhengma:// 获取验证码

			break;
		case R.id.register_tongyixieyi_quyu:// 同意协议区域
			if (xieyiCb.isChecked()) {
				xieyiCb.setChecked(false);
			} else {
				xieyiCb.setChecked(true);
			}
			break;
		case R.id.register_xieyi_neirong:// 协议内容 展示；
			intent=new Intent(getApplicationContext(), XieYiActivity.class);
			startActivity(intent);
			break;
		case R.id.register_lijijiaru://立即加入
			if(!checked()){
				//自动检测 错误  不用再调用方法
			}else{
				if(xieyiCb.isChecked()){
					complete();
				}else{
					Builder builder=new AlertDialog.Builder(RegisterActivity.this);
					builder.setTitle("你还未同意《隐私与服务协议》！");
					builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							
						}
					});
					builder.show();
				}
			}
			break;
		default:
			break;
		}
	}
	int tiaozhuan=0;
	/**
	 * 用户信息存到数据库中
	 */
	public void complete() {
		Toast.makeText(RegisterActivity.this, "正在为你注册...", Toast.LENGTH_LONG).show();
		new Thread(new Runnable() {
			public void run() {
				String httpUrl = "http://192.168.1.192/index.php/Home/Api/add";//PHP接口地址
				HttpPost httpRequest = new HttpPost(httpUrl);//http用post方法请求数据
				List<NameValuePair> params = new ArrayList<NameValuePair>();//建立一个列表用于添加数据
				params.add(new BasicNameValuePair("useraccount", shoujihao
						.getText().toString().trim()));//添加用户的用户名
				params.add(new BasicNameValuePair("userpassword", shezhimima
						.getText().toString().trim()));//添加用户的密码
				HttpEntity httpentity = null;
				try {
					httpentity = new UrlEncodedFormEntity(params, "utf8");//设置用户字符集的格式
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				httpRequest.setEntity(httpentity);
				HttpClient httpClient = new DefaultHttpClient();
				HttpResponse httpResponse = null;
				try {
					httpResponse = httpClient.execute(httpRequest);
				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {

					e.printStackTrace();
				}
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					String strResult = null;
					try {
						strResult = EntityUtils.toString(httpResponse
								.getEntity());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					Intent intent=new Intent(RegisterActivity.this, MainActivity.class);
					startActivity(intent);
					RegisterActivity.this.finish();
					Looper.prepare();
					Toast.makeText(RegisterActivity.this, strResult + "注册成功！启宠资源大门以为你敞开。",
							Toast.LENGTH_SHORT).show();
					Looper.loop();
				} else {
					Looper.prepare();
					Toast.makeText(RegisterActivity.this, "注册失败或用户已存在！",
							Toast.LENGTH_SHORT).show();
					Looper.loop();
				}
			}
		}).start();
	}
}
