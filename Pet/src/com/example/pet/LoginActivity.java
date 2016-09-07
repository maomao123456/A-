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
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.classes.SysApplication;
import com.example.pet.lei.CircularImage;
import com.example.pet.lei.JieXiShuJu;
import com.example.pet.lei.SaveAndOutImg;
import com.example.pet.qq.QqAppConstant;
import com.example.pet.qq.Util;
import com.example.pet.wb.AccessTokenKeeper;
import com.example.pet.wb.UsersAPI;
import com.example.pet.wb.WbConstant;
import com.example.pet.wx.WxAppConstant;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.tencent.connect.UserInfo;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends Activity  implements OnClickListener{
	EditText userName, mima;
	Button zhuce, denglu;
	ImageView qq, wb, wx;
	CircularImage lg;
	ScrollView sc;
	/**
	 * logo下面的名字  用户名
	 */
	TextView loName;
	/**
	 * get方式的解析方法
	 */
	JieXiShuJu jx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		panDuan();
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN|
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		SysApplication.getInstance().addActivity(this);
		setContentView(R.layout.activity_login);
		 mAppid = QqAppConstant.APP_ID;//初始化主操作对象
		 if(null==mTencent){
			 mTencent = Tencent.createInstance(mAppid,getApplicationContext()); 
		 }
		jx=new JieXiShuJu();
		initView();
	}
	/**
	 * 判断用户上次是否已登录
	 */
	public void panDuan(){
		SharedPreferences pf=getSharedPreferences("pet", Context.MODE_PRIVATE);
		int numb=pf.getInt("login", 0);
		if(numb!=0){
			Intent intent=new Intent(LoginActivity.this, KaiQiActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
		}
	}
	/**
	 * 通过ID找到控件
	 */
	public void initView() {
		lg=(CircularImage)findViewById(R.id.login_logo);
		lg.setImageResource(R.drawable.logo);
		loName=(TextView)findViewById(R.id.login_logo_name);
		userName = (EditText) findViewById(R.id.login_userName);
		mima = (EditText) findViewById(R.id.login_password);
		zhuce = (Button) findViewById(R.id.login_zhuce);
		denglu = (Button) findViewById(R.id.login_denglu);
		qq = (ImageView) findViewById(R.id.login_qq);
		wb = (ImageView) findViewById(R.id.login_wb);
		wx = (ImageView) findViewById(R.id.login_wx);
		sc=(ScrollView)findViewById(R.id.login_scroll);
		userName.setOnFocusChangeListener(focusChangeListener);
		userName.setOnClickListener(this);
		mima.setOnFocusChangeListener(focusChangeListener);
		zhuce.setOnClickListener(this);
		denglu.setOnClickListener(this);
		qq.setOnClickListener(this);
		wb.setOnClickListener(this);
		wx.setOnClickListener(this);
	}
	/**
	 * 登录时等待进度条(缓冲数据加载)
	 */
	ProgressDialog  progressDialog;
	/**
	 * 登录提示语 （转圈圈）
	 */
	public void zhuanquanquan(){
		progressDialog=new  ProgressDialog(LoginActivity.this);
        progressDialog.setMessage("正在登录......");
        progressDialog.show();
	}
	/**
	 * 普通控件的点击事件
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_zhuce:
			Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.login_userName:
			changeSize();
			break;
		case R.id.login_denglu:
			if (!checkNetwork()) {
				toast("网络未连接，请检查网络设置！");
			} else {
				if (checkedEditText()) {
					loging(userName.getText().toString(), mima.getText()
							.toString());
				} else {
					toast("输入格式不正确，请重新输入");
				}
			}
			break;
		case R.id.login_qq:
			if (!checkNetwork()) {
				toast("网络未连接，请检查网络设置！");
			} else {
				toast("稍等片刻，QQ接入中....");
				loginQQ();
			}
			break;
		case R.id.login_wb:
			if (!checkNetwork()) {
				toast("网络未连接，请检查网络设置！");
			} else {
				toast("稍等片刻，微博接入中....");
				loginWb();
			}
			break;
		case R.id.login_wx:
			if (!checkNetwork()) {
				toast("网络未连接，请检查网络设置！");
			} else {
				toast("稍等片刻，微信接入中....");
				loginWx();
			}
			break;
		default:
			break;
		}
	}
	/**
	 * 转换数据方便解析
	 */
	String str="";
	/**
	 * 远程判断用户账号 数据库账号比对
	 */
	public void loging(final String userName, final String password) {
		new Thread(new Runnable() {
			public void run() {
				StringBuilder builder = new StringBuilder();
				try {
					String httpHost = "http://192.168.1.192/index.php/Home/Pet/login";// php接口
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
	/**
	 * 布局尺寸的调整
	 */
	public void changeSize(){
		Handler mHandler = new Handler(); 
		mHandler.postDelayed(new Runnable() {
			 @Override public void run() { 
			sc.fullScroll(ScrollView.FOCUS_DOWN); } },300); 
	}
	/**
	 * 登录前判断用户输入是否正确
	 */
	public boolean checkedEditText(){
		String str=userName.getText().toString();
		String str2=mima.getText().toString();
		if(userName.getText()==null||str.trim().length()==0){
			return false;
		}else if(mima.getText()==null||str2.trim().length()==0){
			return false;
		}else{
			return true;
		}
	}
	/**
	 * 监听用户输入 及时给予提示
	 */
	OnFocusChangeListener focusChangeListener=new OnFocusChangeListener() {
		public void onFocusChange(View v, boolean hasFocus) {
			if (!hasFocus) {
				String str = userName.getText().toString();
				String str2 = mima.getText().toString();
				switch (v.getId()) {
				case R.id.login_userName:
					if(userName.getText()==null||str.trim().length()==0){
						toast("用户名不能为空！");
					}
					break;
				case R.id.login_password:
					if(mima.getText()==null||str2.trim().length()==0){
						toast("密码不能为空！");
					}
					break;
				default:
					break;
				}
			}
		}
	};
	/**
	 * 微信登录
	 */
	public void loginWx(){
		String wxID=WxAppConstant.APP_ID;
		IWXAPI api;
		api=WXAPIFactory.createWXAPI(LoginActivity.this, wxID,true);
		api.registerApp(wxID);
		SendAuth.Req req = new SendAuth.Req();
		//授权读取用户信息  
		req.scope = "snsapi_userinfo";
		//自定义信息 
		req.state = "wechat_sdk_demo_test";
		//向微信发送请求
		api.sendReq(req);
	}
	/**
	 * 微博的handler
	 */
	SsoHandler mSsoHandler;
	/**
	 * 微博的AccessToken
	 */
	Oauth2AccessToken mAccessToken;
	/**
	 * 微博登录
	 */
	public void loginWb(){
		// 创建授权认证信息
	AuthInfo mAuthInfo = new AuthInfo(LoginActivity.this,
			WbConstant.APP_KEY, WbConstant.REDIRECT_URL, null);
	mSsoHandler = new SsoHandler(LoginActivity.this, mAuthInfo);
	mSsoHandler.authorizeWeb(authListener);
	}
	/**
	 * 微博的监听
	 */
	WeiboAuthListener authListener = new WeiboAuthListener() {
		public void onWeiboException(WeiboException arg0) {
			toast("登录出错！" + arg0.getMessage());
		}
		public void onComplete(Bundle arg0) {
			zhuanquanquan();
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(arg0);
			// 从这里获取用户输入的 电话号码信息
			String phoneNum = mAccessToken.getPhoneNum();
			if (mAccessToken.isSessionValid()) {
				AccessTokenKeeper.writeAccessToken(LoginActivity.this,
						mAccessToken);
				// 保存 Token 到 SharedPreferences
				String token = mAccessToken.getToken();
				String uid = mAccessToken.getUid();
				Editor editor = getSharedPreferences("pet", MODE_PRIVATE)
						.edit();
				editor.putString("token", token);
				editor.putString("uid", uid);
				editor.commit();
				Log.i("token", ">>>>>>>>" + token.toString());
			} else {
				// 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
				String code = arg0.getString("code");
				String message = "授权失败";
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				toast(message);
			}// 封装好的用户信息解析方法
			new Thread(new Runnable() {
				public void run() {
					Oauth2AccessToken mAccessToken = AccessTokenKeeper
							.readAccessToken(LoginActivity.this);
					UsersAPI usersAPI = new UsersAPI(LoginActivity.this,
							WbConstant.APP_KEY, mAccessToken);
					usersAPI.show(Long.parseLong(mAccessToken.getUid()),
							new SinaRequestListener());
				}
			}).start();
		}
		public void onCancel() {
			toast("登录取消");
		}
	};
	/**
	 * 新浪微请求接口
	 */
	public class SinaRequestListener implements RequestListener { // 新浪微博请求接口
		public void onComplete(String response) {
			toast("登录成功！");
			saveLogin();
			try {
				JSONObject jsonObject = new JSONObject(response);
				System.out.println(jsonObject.toString());
				String ID = jsonObject.getString("idstr");// 唯一标识符(uid)
				String city = jsonObject.getString("location");// 地址
				String nickName = jsonObject.getString("name");// 姓名
				final String touxiang = jsonObject.getString("avatar_hd");// 头像
				// 调用用户信息类
				SharedPreferences pf=getSharedPreferences("pet_user", MODE_PRIVATE);
				Editor editor=pf.edit();
				saveDisanfangInfo(ID, nickName, touxiang, "重庆", "男");
				if(pf.getString(ID, "无").equals("无")){
					editor.putString(ID, ID);
					SaveAndOutImg.saveImg(touxiang, ID);
				}	
				loName.setText(nickName);
				threeTz(3, nickName, ID, "无", touxiang, city);
				// 用户头像转换
				new Thread() {
					public void run() {
						bitmap = Util.getbitmap(touxiang);
						Message msg = new Message();
						msg.obj = bitmap;
						msg.what = 2;
						handler.sendMessage(msg);
					}
				}.start();
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		public void onWeiboException(WeiboException e) {
			toast("授权成功获取信息异常 没有权限！");
			Log.i("授权成功但登录异常》》《《《《《", "Auth exception : " + e.getMessage());
		}
	}
	/**
	 * 我的APPid
	 */
	public static String mAppid;
	/**
	 * QQ信息类 已包装
	 */
	UserInfo qqInfo;
	/**
	 * qq主操作对象
	 */
	private Tencent mTencent;
	Bitmap bitmap = null;
	/**
	 * 登录QQ方法
	 */
	public void loginQQ() {
		if (!mTencent.isSessionValid()) {
			mTencent.login(this, "all", qqListener);
		}
	}
	/**
	 * 登录QQ时的监听
	 */
	private IUiListener qqListener = new BaseUiListener() {
		protected void doComplete(JSONObject values) {
			// super.doComplete(values);
			initLoginID(values);
		}
	};
	private class BaseUiListener implements IUiListener {
		protected void doComplete(JSONObject values) {
		}
		public void onComplete(Object response) {
			if (null == response) {
				toast("登录失败！");
				return;
			}
			JSONObject jsonResponse = (JSONObject) response;
			if (null != jsonResponse && jsonResponse.length() == 0) {
				toast("登录失败！");
				return;
			}
			doComplete((JSONObject) response);
			zhuanquanquan();
		}
		public void onError(UiError e) {
			toast("登录时出错！");
		}
		public void onCancel() {
			toast("登录取消！");
		}
	}
	/**
	 * 登录后获取qqID及token信息
	 * 
	 * @param jsonObject
	 *            json对象
	 */
	private void initLoginID(JSONObject jsonObject) {
		try {
			if (jsonObject.getInt("ret") == 0) {
				String token = jsonObject
						.getString(Constants.PARAM_ACCESS_TOKEN);
				String expires = jsonObject
						.getString(Constants.PARAM_EXPIRES_IN);
				String openID = jsonObject.getString(Constants.PARAM_OPEN_ID);
				// **下面这两步设置很重要,如果没有设置,返回为空**
				mTencent.setOpenId(openID);
				mTencent.setAccessToken(token, expires);
				//以下是根据openID来存储QQID的方法
				//并判断是否为同一QQ号
				SharedPreferences pf=getSharedPreferences("pet_user", MODE_PRIVATE);
				Editor editor=pf.edit();
				if(!pf.getString(openID, "无").equals("无")){
					qqID=pf.getString(openID, "无");
				}else{
					String	qd=pf.getString("openID", "20160000");
					int qqid= Integer.parseInt(qd)+1;
					editor.putString("openID", qqid+"");
					qqID=qqid+"";
					editor.putString(openID, qqID);
					editor.commit();
					hasDL=false;
				}
				getuserInfo();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 自定义生成QQid
	 */
	String qqID;
	/**
	 * 判断是否已登录过
	 */
	boolean hasDL=true;
	/**
	 * 获得qq用户的信息
	 */
	private void getuserInfo() {
		qqInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());
		qqInfo.getUserInfo(getQQinfoListener);
	}
	/**
	 * 获取用户信息的监听
	 */
	private IUiListener getQQinfoListener = new IUiListener() {
		public void onComplete(final Object response) {
			try {
				JSONObject jsonObject = (JSONObject) response;
				Log.i("qq用户信息", jsonObject.toString());
				// 处理自己需要的信息
				String name = jsonObject.getString("nickname");
				String sex = jsonObject.getString("gender");
				String city = jsonObject.getString("city");
				String touxiang = jsonObject.getString("figureurl_qq_2");
				loName.setText(name);
				saveDisanfangInfo(qqID, name, touxiang, "重庆", sex);
				if(!hasDL){
					SaveAndOutImg.saveImg(touxiang, qqID);
				}
				saveLogin();
				threeTz(3, name, qqID, sex, touxiang, city);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Message msg = new Message();
			msg.obj = response;
			msg.what = 0;
			handler.sendMessage(msg);
			new Thread() {
				public void run() {
					JSONObject json = (JSONObject) response;
					// 获取头像
					if (json.has("figureurl")) {// 判断字段是否为空
						bitmap = null;
						try {
							bitmap = Util.getbitmap(json
									.getString("figureurl_qq_2"));
						} catch (JSONException e) {
						}
						Message msg = new Message();
						msg.obj = bitmap;
						msg.what = 1;
						handler.sendMessage(msg);
					}
				}
			}.start();
		}
		public void onError(UiError uiError) {
			toast("登录成功但获取信息时异常！");
		}
		public void onCancel() {
			toast("登录成功但获取信息时被取消！");
		}
	};
	/**
	 * 接收解析数据并刷新UI
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bitmap bitmap = (Bitmap) msg.obj;
				lg.setImageBitmap(bitmap);
				break;
			case 2:
				/**
				 * 第三方返回的数据缓存在本地此文件下
				 */
				SharedPreferences pf=getSharedPreferences("pet_user", MODE_PRIVATE);
				/**
				 * 第三方登录后返回的所在地
				 */
				String city=pf.getString("city", "无");
				/**
				 * 第三方登录后返回的昵称
				 */
				String name=pf.getString("nicheng", "无");
				/**
				 * 第三方登录后所返回的id
				 */
				String id=pf.getString("id", "无");
				/**
				 * 第三方登录返回的头像地址
				 * (需要开启一个支线程来把头像地址转换为Bitmap)
				 *示列  new Thread(){
	                        @Override			
	                        public void run() {
	                            bitmap =Util.getbitmap(tongxiang);
	                            Message msg = new Message();
	                            msg.obj = bitmap;
	                            msg.what = 2;
	                            handler.sendMessage(msg);
	                        }                       
	                    }.start();
				 * 然后在handler中接收消息 更新UI
				 * Bitmap bitmap = (Bitmap)msg.obj;
				 * XXX.setsetImageBitmap(bitmap);
				 */
				String tongxiang=pf.getString("tongxiang", "无地址");
				
				 Bitmap bitmap2 = (Bitmap)msg.obj;
	                lg.setImageBitmap(bitmap2);
				break;
			case 3:

				break;
			case 4:
				try {
					JSONObject jsonObject =new JSONObject(str);
					int status = jsonObject.getInt("status");
					String message = jsonObject.getString("message");
					
					if (status == 2) {
						// 密码错误，提示用户名或密码错误
						//跳转到忘记密码界面
						new AlertDialog.Builder(LoginActivity.this)
						.setTitle("密码错误！")
						.setMessage("忘记密码？？？？？\n" + "是否立即找回密码！" )
						.setPositiveButton("我想再试试看",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								})
						.setNegativeButton("我要改密码",
								new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
								Intent intent=new Intent(LoginActivity.this,
										ForgetPasswordActivity.class);
								startActivity(intent);
							}
						}).create().show();	
					} else if (status == 3) {
						toast(message);// 当输入的用户名不存在时，提示用户不存在
					} else {
						toast("登录成功！");
						saveLogin();
						threeTz(1, "", 
								userName.getText().toString(),
								"无", "无", "无");;
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				break;
			default:
				break;
			}
		};
	};
	/**
	 * 处理第三方的回调消息
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		//微博返回码 发起 SSO 登陆的 Activity 必须重写 onActivityResults
		if (mSsoHandler != null) {
			Log.i("新浪微博登陆返回", "返回");
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
		//QQ的回调监听
		if (requestCode == Constants.REQUEST_LOGIN) {
			if (resultCode == -1) {
				Tencent.onActivityResultData(requestCode, resultCode, data,
						qqListener);
				Tencent.handleResultData(data, qqListener);
				UserInfo info = new UserInfo(this, mTencent.getQQToken());
				info.getUserInfo(getQQinfoListener);
			}
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
	@Override
	protected void onDestroy() {//QQ登出
		super.onDestroy();
		mTencent.logout(LoginActivity.this);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){//返回键的监听
			onBackPressed();
		}
		return true;
	}
	/**
	 * 实现Home键效果
	 */
	public void onBackPressed() {        
		Intent setIntent = new Intent(Intent.ACTION_MAIN);
		setIntent.addCategory(Intent.CATEGORY_HOME);
		setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);        
		startActivity(setIntent);    
	}
	/**
	 * 验证网络状态
	 * (WiFi只能验证是否连接上 不能验证是否能上网)
	 */
	private boolean checkNetwork() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
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
		Toast toast=Toast.makeText(LoginActivity.this, string, Toast.LENGTH_SHORT);
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
		if(progressDialog!=null){
			progressDialog.dismiss();
		}
		Intent intent =new Intent(LoginActivity.this, MainActivity.class);
		startActivity(intent);
		LoginActivity.this.finish();
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
	 * 存储第三方信息到数据库
	 */
	public void saveDisanfangInfo(final String id ,final String nickname,
			final String touxiang,final String address,final String sex){
		new Thread(new Runnable() {
			public void run() {
				JieXiShuJu.doGet("http://192.168.1.192/index.php/Home/Pet/adddisanfang",
						new String[]{"id","nickname","sex","address","touxiang"},
						new String[]{id,nickname,sex,address,touxiang});
			}
		}).start();
	}
}
