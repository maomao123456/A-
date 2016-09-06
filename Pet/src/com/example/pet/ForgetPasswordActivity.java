package com.example.pet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import cn.smssdk.utils.SMSLog;

import com.example.pet.classes.SysApplication;
import com.example.pet.lei.PhoneTy;

public class ForgetPasswordActivity extends Activity implements OnClickListener{
	LinearLayout back;
	TextView getYzm,queding;
	EditText shoujihao,yanzhengma,mima,mima2;
	ScrollView sc;
	Thread thread=null;
	private int time=60;
	private boolean tag=true;
	private static final String APPKEY = "168b024d49944";    
	private static final String APPSECRETE = "3554d93a1086b52572d38877fa651526"; 
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
	 * 获得用户再次输入的密码
	 */
	String password2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_forgetpassword);
		initView ();
	}
	/**
	 * 通过id找到控件
	 */
	public void initView (){
		back=(LinearLayout)findViewById(R.id.forget_backquyu);
		getYzm=(TextView)findViewById(R.id.forget_getyanzhengma);
		queding=(TextView)findViewById(R.id.forget_queding);
		shoujihao=(EditText)findViewById(R.id.forget_shoujihao);
		yanzhengma=(EditText)findViewById(R.id.forget_yanzhengma);
		mima=(EditText)findViewById(R.id.forget_mima);
		mima2=(EditText)findViewById(R.id.forget_mima_next);
		sc=(ScrollView)findViewById(R.id.forget_scroll);
		back.setOnClickListener(this);
		getYzm.setOnClickListener(this);
		queding.setOnClickListener(this);
		shoujihao.setOnClickListener(this);
		shoujihao.setOnFocusChangeListener(foucusChange);
		yanzhengma.setOnFocusChangeListener(foucusChange);
		mima.setOnFocusChangeListener(foucusChange);
		mima2.setOnFocusChangeListener(foucusChange);
		 // 启动短信验证sdk  
		SMSSDK.initSDK(ForgetPasswordActivity.this, APPKEY, APPSECRETE); 
        EventHandler eventHandler = new EventHandler(){    
            @Override    
            public void afterEvent(int event, int result, Object data) {    
                Message msg = new Message();    
                msg.arg1 = event;    
                msg.arg2 = result;    
                msg.obj = data;
                msg.what=1;
                handler.sendMessage(msg);   
            }    
        };
    	//注册回调监听接口    
        SMSSDK.registerEventHandler(eventHandler);
	}
	public final String format2 = "^([A-Za-z0-9])+$";//密码格式
	OnFocusChangeListener foucusChange=new OnFocusChangeListener() {
		public void onFocusChange(View v, boolean hasFocus) {
			if (!hasFocus) {
				phoneNumb = shoujihao.getText().toString();
				boolean phone = PhoneTy.isPhoneNb(phoneNumb);
				yanzhengCd = yanzhengma.getText().toString();
				password = mima.getText().toString();
				password2 = mima2.getText().toString();
				switch (v.getId()) {
				case R.id.forget_shoujihao:
					if(!phone){
						toast("请输入正确的手机号！");
					}
					break;
				case R.id.forget_yanzhengma:
					if(yanzhengCd.length()!=4){
						toast("验证码长度为4位数");
					} 
					break;
				case R.id.forget_mima:
					if(password.length()<6||password.length()>20){
						toast("密码为6-20位数字或字母!");
					}else if(!password.matches(format2)){
						toast("密码为6-20位数字或字母!");
					}
					break;
				case R.id.forget_mima_next:
					if(!password2.equals(password)){
						toast("密码不一致!");
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
		password=mima.getText().toString();
		password2=mima2.getText().toString();
		if(!phone){
			toast("请输入正确的手机号！");
			return false;
		}else if(yanzhengCd.length()!=4){
			toast("格式错误：验证码长度为4位");
			return false;
		}else if(password.length()<6||password.length()>20){
			toast("密码只能是6-20位数字或字母!");
			return false;
		}else if(!password.matches(format2)){
			toast("密码只能是数字或字母!");
			return false;
		}else if(!password2.equals(password)){
			toast("密码不一致！请重新输入");
			return false;
		}else{
			return true;
		}
	}
	@Override
	public void onClick(View v) {
		phoneNumb=shoujihao.getText().toString();
		boolean phone=PhoneTy.isPhoneNb(phoneNumb);
		yanzhengCd=yanzhengma.getText().toString();
		password=mima.getText().toString();
		password2=mima2.getText().toString();
		switch (v.getId()) {
		case R.id.forget_shoujihao:
			changeSize();
			break;
		case R.id.forget_backquyu:
			Intent intent=new Intent(ForgetPasswordActivity.this, LoginActivity.class);
			startActivity(intent);
			ForgetPasswordActivity.this.finish();
			break;
		case R.id.forget_getyanzhengma://获取验证码
			if(!checkNetwork()){
				toast("网络不可用！无法获取验证码");
			}else if(!phone){
				toast("手机格式不正确！无法获取验证码");
			}else{
				getYzm.setText("获取验证码");
				getYzm.setClickable(true);
				new AlertDialog.Builder(ForgetPasswordActivity.this)
						.setTitle("提示")
						.setMessage("验证码将作为修改此账号密码的凭证，请注意查收！")
						.setPositiveButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								})
						.setNegativeButton("确定",
								new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// 2. 通过sdk发送短信验证
								SMSSDK.getVerificationCode("86", phoneNumb+"");
								changeTime();
							}
						}).create().show();
			}
			break;
		case R.id.forget_queding://确定修改
			if(!checkNetwork()){
				toast("网络不可用！请检查网络设置");
			}else if(!checked()){
				
			}else{
				SMSSDK.submitVerificationCode("86", phoneNumb+"", yanzhengCd+""); 
			}
			break;
			
		default:
			break;
		}
	}
	Handler handler = new Handler() {    
        public void handleMessage(Message msg) {    
            if(msg.what==4){//判断修改密码是否成功 
            	try {
					JSONObject jsonObject =new JSONObject(str);
					int status = jsonObject.getInt("status");
					String message = jsonObject.getString("message");
					if(status==1){//账号不存在 修改失败！
						toast(message);
					}else if(status==2){//修改成功！
						toast(message);
						threeTz(1, "", phoneNumb, "", "", "");
					}
            	} catch (JSONException e) {
					e.printStackTrace();
				}	
            }else if(msg.what==1){ 
            		int event = msg.arg1;    
                    int result = msg.arg2;    
                    Object data = msg.obj;    
                    Log.e("event", "event=" + event);    
                    if (result == SMSSDK.RESULT_COMPLETE) {    
                        // 短信注册成功后，返回MainActivity,然后提示    
                        if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功    
                            toast("验证码校验成功！");
                           resetPassword(phoneNumb, password);
                        } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {    
                            toast("正在发送验证码....");
                        } else {
                             ((Throwable) data).printStackTrace();
                        }    
                    }else if (result == SMSSDK.RESULT_ERROR) {
					try {
						int status = 0;
						Throwable throwable = (Throwable) data;
						throwable.printStackTrace();
						JSONObject object = new JSONObject(
								throwable.getMessage());
						String des = object.optString("detail");// 错误描述
						status = object.optInt("status");// 错误代码
						if (status > 0 && !TextUtils.isEmpty(des)) {
							toast(""+des);
							return;
						}
					} catch (Exception e) {
						 SMSLog.getInstance().w(e);
						toast("验证码校验失败！ 请重试");
					}
				}else{
					Throwable throwable = (Throwable) data;
					throwable.printStackTrace();
					toast("验证码校验失败！ 请重试");
				}
            }     
        }    
    };
    /**
	 * 转换数据方便解析
	 */
	String str="";
	/**
	 * 数据库中根据用户的账号来修改密码
	 */
	public void resetPassword(final String userName, final String password) {
		new Thread(new Runnable() {
			public void run() {
				StringBuilder builder = new StringBuilder();
				try {
					String httpHost = "http://192.168.1.192/index.php/Home/Pet/forgetpassword";// php接口
					String name = "phonenumber=" + userName + "&password="
							+ password;
					String urlName = httpHost + "?" + name;
					URL url = new URL(urlName);
					HttpURLConnection connection = (HttpURLConnection) url
							.openConnection();
					connection.setConnectTimeout(5000);
					connection.setRequestProperty("accept", "*/*");// 设置客户端接受那些类型的信息，通配符代表接收所有类型的数据
					connection.setRequestProperty("connection", "Keep-Alive");// 保持长链接
					connection
							.setRequestProperty("user-agent",
									"Mozilla/4.0(compatible;MSIE 6.0;Windows NT5.1;SV1)");// 设置浏览器代理
					connection
							.setRequestProperty("accept-charset", "utf-8;GBK");// 客户端接受的字符集
					connection.connect();// 建立连接
					InputStream inputStream = connection.getInputStream();
					Map<String, List<String>> headers = connection
							.getHeaderFields();
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
					str = builder.toString();
					Message msg = new Message();
					// 发送登录验证消息
					msg.what = 4;
					handler.sendMessage(msg);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}
    
    
    
    
    public void changeTime(){
		 thread = new Thread() { 
		      @SuppressWarnings("unused")
			public void run() { 
		        if (tag) { 
		          while (time > 0) { 
		            time--; 
		            if (ForgetPasswordActivity.this == null) { 
		              break; 
		            }   
		            ForgetPasswordActivity.this .runOnUiThread(new Runnable() { 
		                  public void run() { 
		                	  getYzm.setText("重新发送("
		                        + time + ")"); 
		                	  getYzm .setClickable(false); } }); 
		            try { 
		              Thread.sleep(1000); 
		            } catch (InterruptedException e) { 
		              throw new RuntimeException(e); 
		            } 
		          } 
		          tag = false; 
		        } 
		        time = 60; 
		        tag = true; 
		        if (ForgetPasswordActivity.this != null) { 
		        	ForgetPasswordActivity.this.runOnUiThread(new Runnable() { 
		            @Override
		            public void run() { 
		            	getYzm.setText("获取验证码"); 
		            	getYzm.setClickable(true); 
		              handler.sendEmptyMessage(3);
		            } 
		          }); 
		        } 
		      }; 
		    }; 
		    thread.start(); 
	}
	/**
	 * 验证网络状态
	 * (WiFi只能验证是否连接上 不能验证是否能上网)
	 */
	private boolean checkNetwork() {
		ConnectivityManager connManager = (ConnectivityManager)
				getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}
	/**
	 *  提示全局通用
	 * @param string
	 * 提示语
	 */
	public void toast(String string) {
		Toast toast=Toast.makeText(ForgetPasswordActivity.this, string, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	/**
	 * 保存用户登录方式
	 * 第三方登录成功后 进行跳转并保存第三方用户信息
	 * @param numb
	 * numb=1:用自己的账号登录的
	 * numb=2刚注册的用户
	 * numb=3为第三方登录的
	 * @param nickName
	 * 用户的昵称
	 * @param ID
	 * 用户的id
	 * @param six
	 * 用户的性别
	 * @param touxiang
	 * 用户的头像 为一个网址
	 * @param city
	 * 用户所在地
	 */
	public void threeTz(int numb,String nickName,String ID,String six,String touxiang,String city){
		Editor editor=getSharedPreferences("pet_user", MODE_PRIVATE).edit();
		editor.putInt("disanfang", numb);
		editor.putString("nicheng", nickName);
    	editor.putString("id", ID);
    	editor.putString("six", six);
    	editor.putString("tongxiang", touxiang);
    	editor.putString("city", city);
		editor.commit();
		Intent intent =new Intent(ForgetPasswordActivity.this, MainActivity.class);
		startActivity(intent);
		ForgetPasswordActivity.this.finish();
	} 
	/**
	 * 储存用户上次是否已登录
	 */
	public void saveLogin(){
		Editor editor2=getSharedPreferences("pet", MODE_PRIVATE).edit();
    	editor2.putInt("login", 1);
    	editor2.commit();
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
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		 SMSSDK.unregisterAllEventHandler();
	}
	
}
