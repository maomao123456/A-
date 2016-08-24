package com.example.pet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.pet.qq.QqAppConstant;
import com.example.pet.qq.Util;
import com.example.pet.wb.WbConstant;
import com.example.pet.wx.WxAppConstant;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.SendAuth;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends Activity  implements OnClickListener {
	EditText userName, mima;
	Button zhuce, denglu;
	ImageView qq, wb, wx,lg;
	ScrollView sc;
	/**
	 * 解析数据时 提供的判断
	 */
	int numb=0;
	//UMShareAPI mShareAPI = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		setContentView(R.layout.activity_login);
		initView();
	}
	/*
	 * 通过ID找到控件
	 */
	public void initView() {
		lg=(ImageView)findViewById(R.id.login_logo);
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
	 * 普通控件的点击事件
	 */
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_zhuce:
			Intent intent=new Intent(getApplicationContext(), RegisterActivity.class);
			startActivity(intent);
			LoginActivity.this.finish();
			break;
		case R.id.login_userName:
			changeSize();
			break;
		case R.id.login_denglu:
			if(checkedEditText()){
				Toast.makeText(getApplicationContext(), "正在登录....",
						Toast.LENGTH_SHORT).show();
			}else{
				Toast.makeText(getApplicationContext(), "输入格式不正确，请重新输入",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.login_qq:
			Toast.makeText(getApplicationContext(), "稍等片刻，QQ接入中....",
					Toast.LENGTH_SHORT).show();
			loginQQ();
			break;
		case R.id.login_wb:
			Toast.makeText(getApplicationContext(),"稍等片刻，微博接入中....",
					Toast.LENGTH_SHORT).show();
			loginWb();
			break;
		case R.id.login_wx:
			Toast.makeText(getApplicationContext(), "稍等片刻，微信接入中....",
					Toast.LENGTH_SHORT).show();
			loginWx();
			break;
		default:
			break;
		}
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
	 * 提示全局通用
	 * @param string
	 */
	public void toast(String string) {
		Toast.makeText(LoginActivity.this, string, Toast.LENGTH_SHORT).show();
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
						Toast.makeText(getApplicationContext(), "用户名不能为空！",
								Toast.LENGTH_SHORT).show();
					}
					break;
				case R.id.login_password:
					if(mima.getText()==null||str2.trim().length()==0){
						Toast.makeText(getApplicationContext(), "密码不能为空！",
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
	SsoHandler mSsoHandler;
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
	WeiboAuthListener authListener=new WeiboAuthListener() {
			public void onWeiboException(WeiboException arg0) {
				toast("登录出错！"+arg0.getMessage());				
			}
			public void onComplete(Bundle arg0) {
				// 从 Bundle 中解析 Token
				Oauth2AccessToken	 mAccessToken = Oauth2AccessToken.parseAccessToken(arg0);
				 //从这里获取用户输入的 电话号码信息
				String  phoneNum =  mAccessToken.getPhoneNum();
		        if (mAccessToken.isSessionValid()) {
		            // 保存 Token 到 SharedPreferences
		        	String token=mAccessToken.getToken();
		        	String uid=mAccessToken.getUid();
		        	Editor editor=getSharedPreferences("pet", MODE_PRIVATE).edit();
		        	editor.putString("token", token);
		        	editor.putString("uid", uid);
		        	editor.commit();
		        	Log.i("token", ">>>>>>>>"+token.toString());
				Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT)
						.show();
				//调用第三方解析方法
				numb=1;
				String httpHost="https://api.weibo.com/2/users/show.json";
				String canshu="access_token,screen_name";
				jiexi(canshu, httpHost, numb,token);
				toast("登录成功！即将为你跳转到主页面");
		        } else {
		        // 当您注册的应用程序签名不正确时，就会收到 Code，请确保签名正确
		            String code = arg0.getString("code", "");
		            String message = "授权失败";
		            if (!TextUtils.isEmpty(code)) {
		            message = message + "\nObtained the code: " + code;
		            }
		            Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
		        }
			}
			public void onCancel() {
				toast("登录取消");
			}
		};
		
	/**
	 * QQ登录
	 */
	public void loginQQ(){
		//这里的APP_ID请换成你应用申请的APP_ID，我这里使用的是DEMO中官方提供的测试APP_ID 222222
        mAppid = QqAppConstant.APP_ID;
        //第一个参数就是上面所说的申请的APPID，第二个是全局的Context上下文，这句话实现了调用QQ登录
        mTencent = Tencent.createInstance(mAppid,getApplicationContext());
		if (!mTencent.isSessionValid()){
			/**
			 * 通过这句代码，SDK实现了QQ的登录，这个方法有三个参数， 第一个参数是context上下文，
			 *  第二个参数SCOPO 是一个String类型的字符串，表示一些权限 官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。
			 *  例如：SCOPE =“get_user_info,add_t”；所有权限用“all”
			 */
			mTencent.login(LoginActivity.this, "all", new LoginListener());
		}
	}
	private Tencent mTencent;
	public static String mAppid;
	public static String openidString;
	public static String nicknameString;
	public static String TAG = "LoginActivity";
	Bitmap bitmap = null;
	/**
	 * QQ的监听
	 *
	 */
	private class LoginListener implements IUiListener{
		public void onCancel() {
			toast("登录取消");
		}
		public void onComplete(Object arg0) {
			//登录成功后可以获取到用户openID和token等信息
			toast("登录成功！即将为你跳转到主页面");
			 try {
	                //获得的数据是JSON格式的，获得你想获得的内容
	                //如果你不知道你能获得什么，看一下下面的LOG
	                Log.e(TAG+"登录后获得的所用数据", "-------------"+arg0.toString());
	                openidString = ((JSONObject) arg0).getString("openid");
	                Log.e(TAG+"得到的openID", "-------------"+openidString);
	                String token= ((JSONObject) arg0).getString("access_token"); 
	                //expires_in = ((JSONObject) response).getString("expires_in");
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	            //得到userIfon类
	            QQToken qqToken = mTencent.getQQToken();
	            UserInfo info = new UserInfo(getApplicationContext(), qqToken);
	            //这样我们就拿到这个类了，之后的操作就跟上面的一样了，同样是解析JSON           
	            info.getUserInfo(new IUiListener() {
	                public void onComplete(final Object response) {
	                    Log.e(TAG, "进入info类 解析数据---------------111111");
	                    Message msg = new Message();
	                    msg.obj = response;
	                    msg.what = 0;
	                    mHandler.sendMessage(msg);
	                    Log.e(TAG, "返回的所有数据-----111---"+response.toString());
	                    /**
	                     * 由于图片需要下载所以这里使用了线程，如果是想获得其他文字信息直接
	                     * 在mHandler里进行操作
	                     */
	                    new Thread(){
	                        @Override
	                        public void run() {
	                            JSONObject json = (JSONObject)response;
	                            try {
	                                bitmap =Util.getbitmap(json.getString("figureurl_qq_2"));
	                            } catch (JSONException e) {
	                                // TODO Auto-generated catch block
	                                e.printStackTrace();
	                            }
	                            Message msg = new Message();
	                            msg.obj = bitmap;
	                            msg.what = 1;
	                            mHandler.sendMessage(msg);
	                        }                       
	                    }.start();
	                }
					@Override
					public void onCancel() {
					}
					@Override
					public void onError(UiError arg0) {
						
					}
	            });  
		}
		public void onError(UiError arg0) {
			toast("登录出错");
		}
	}
		Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	            if (msg.what == 0) {
	                JSONObject response = (JSONObject) msg.obj;
	                if (response.has("nickname")) {
	                    try {
	                        nicknameString=response.getString("nickname");
	                        toast(nicknameString);
	                        Log.e(TAG, "--"+nicknameString);
	                    } catch (JSONException e) {
	                        e.printStackTrace();
	                    }
	                }
	            }else if(msg.what == 1){
	                Bitmap bitmap = (Bitmap)msg.obj;
	                lg.setImageBitmap(bitmap);
	                 
	            }
	        }
	 
	    };
	    /**
		 * 解析第三方数据
		 */
		public void jiexi(final String canshu,final String httpHost,
				final int numb,final String valu){
			System.out.println("。？》》》》》》是否进入解析数据的方法");
			//SharedPreferences preferences=getSharedPreferences("pet", MODE_PRIVATE);
		    //final String token=preferences.getString("token", "没有");
		    new Thread(new Runnable() {
				public void run() {
					StringBuilder builder=new StringBuilder();
					try {
						String parm=canshu+"="+URLEncoder.encode(valu,"utf-8");
						String urlName=httpHost+"?"+parm;
						URL url =new URL(urlName);
						//强转为HttpUrlConnection
						HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
						httpURLConnection.setConnectTimeout(50000);//设置连接超时的时间
						//设置http请求方式
						httpURLConnection.setRequestMethod("GET");
						httpURLConnection.connect();
						if(httpURLConnection.getResponseCode()==200){//判断网页是否加载成功
							//通过连接对象获取输入流
							InputStream inputStream=httpURLConnection.getInputStream();
							BufferedReader bufferedReader=new BufferedReader(
									new InputStreamReader(inputStream));//通过bufferedReader读取输入流
							String line=bufferedReader.readLine();
							while(line!=null&&line.length()>0){
								builder.append(line);
								line=bufferedReader.readLine();
							}
							bufferedReader.close();
							inputStream.close();
							String str=builder.toString();
							System.out.println(builder.toString()+"+》》》builder张么子");
							//解析数据
							if(numb==1){
								jiexiQQ(str);
							}else if(numb==2){
								jiexiWb(str);
							}else if(numb==3){
								jiexiWx(str);
							}
							
						}else{
							System.out.println("请求异常！");
						}
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}).start();
		} 
	//一下是解析数据后的结果
	String nickname="";
	String city="";
	String touxiang="";
	String six="";
		
	/**
	 * 解析QQ数据的方法
	 */
	public void jiexiQQ(String data){
		//创建一个JsonObject的对象，
		//方括号代表的是数组使用JsonArray,{}代表的是对象使用JsonObject()
		try {
			JSONObject jsonObject=new JSONObject(data);
			nickname=jsonObject.getString("nickname");
			six=jsonObject.getString("gender");
			city=jsonObject.getString("city");
			//头像解析出来的是URI地址
			touxiang=jsonObject.getString("figureurl");
			System.out.println(nickname+"QQ昵称");
			System.out.println(six+"性别");
			System.out.println(city+"家乡");
			Message msg=new Message();
			msg.what=1;
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 解析微博数据
	 */
	public void jiexiWb(String data){
		try {
			JSONObject jsonObject=new JSONObject(data);
			nickname=jsonObject.getString("name");
			city=jsonObject.getString("location");
			touxiang=jsonObject.getString("profile_image_url");
			Message msg=new Message();
			msg.what=2;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 解析微信数据
	 */
	public void jiexiWx(String data){
		try {
			JSONObject jsonObject=new JSONObject(data);
			nickname=jsonObject.getString("name");
			city=jsonObject.getString("location");
			touxiang=jsonObject.getString("profile_image_url");
			Message msg=new Message();
			msg.what=3;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:

				break;
			case 2:
				toast("微博用户昵称为："+nickname+"  地址："+city);
				break;
			case 3:

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
		LoginListener myListener = new LoginListener(); 
		//QQ的返回码
		Tencent.onActivityResultData(requestCode,resultCode,data,myListener);
		if (resultCode ==Constants.ACTIVITY_OK){
			// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
			if (mSsoHandler != null) {
			 Log.i("新浪微博登陆返回","返回");
			 //不能少
			 mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
			 }
		}
			super.onActivityResult(requestCode, resultCode, data);
	}

}
