package com.example.pet;


import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.example.pet.qq.AppConstant;
import com.example.pet.qq.Util;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class LoginActivity extends Activity  implements OnClickListener {
	EditText userName, mima;
	Button zhuce, denglu;
	ImageView qq, wb, wx,lg;
	ScrollView sc;
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
		
	}
	/**
	 * 微博登录
	 */
	public void loginWb(){
		
	}
	/**
	 * QQ登录
	 */
	public void loginQQ(){
		//这里的APP_ID请换成你应用申请的APP_ID，我这里使用的是DEMO中官方提供的测试APP_ID 222222
        mAppid = AppConstant.APP_ID;
        //第一个参数就是上面所说的申请的APPID，第二个是全局的Context上下文，这句话实现了调用QQ登录
        mTencent = Tencent.createInstance(mAppid,getApplicationContext());
		mTencent = Tencent.createInstance(mAppid, this.getApplicationContext());
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
	public static QQAuth mQQAuth;
	public static String mAppid;
	public static String openidString;
	public static String nicknameString;
	public static String TAG = "LoginActivity";
	Bitmap bitmap = null;
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
	                Log.e(TAG, "-------------"+arg0.toString());
	                openidString = ((JSONObject) arg0).getString("openid");
	                Log.e(TAG, "-------------"+openidString);
	                //access_token= ((JSONObject) response).getString("access_token");              //expires_in = ((JSONObject) response).getString("expires_in");
	            } catch (JSONException e) {
	                e.printStackTrace();
	            }
	            //得到userIfon类
	            QQToken qqToken = mTencent.getQQToken();
	            UserInfo info = new UserInfo(getApplicationContext(), qqToken);
	            //这样我们就拿到这个类了，之后的操作就跟上面的一样了，同样是解析JSON           
	            info.getUserInfo(new IUiListener() {
	                public void onComplete(final Object response) {
	                    Log.e(TAG, "---------------111111");
	                    Message msg = new Message();
	                    msg.obj = response;
	                    msg.what = 0;
	                    mHandler.sendMessage(msg);
	                    Log.e(TAG, "-----111---"+response.toString());
	                    /**
	                     * 由于图片需要下载所以这里使用了线程，如果是想获得其他文字信息直接
	                     * 在mHandler里进行操作
	                     */
	                    new Thread(){
	                        @Override
	                        public void run() {
	                            // TODO Auto-generated method stub
	                            JSONObject json = (JSONObject)response;
	                            try {
	                                bitmap =Util.getbitmap(json.getString("figureurl"));
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
	 * 处理第三方的回调消息
	 */
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		LoginListener myListener = new LoginListener();   
		Tencent.onActivityResultData(requestCode,resultCode,data,myListener);
	}
	/**
	 * httpUrlConnection实列应用 get方式
	 *//*
	public void getHttpUrlConnection(){
		new Thread(new Runnable(){
			public void run() {
				StringBuilder builder=new StringBuilder();
				String httpHost="http://openapi.tencentyun.com/v3/user/get_info?";
				try {
					String param="cityname="+URLEncoder.encode("重庆","utf-8");
					String urlName=httpHost+"?"+param;
					URL url =new URL(urlName);
					//强转为HttpUrlConnection
					HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
					httpURLConnection.setConnectTimeout(5000);//设置连接超时的时间
					//设置请求报头参数
					httpURLConnection.setRequestProperty("apikey", "50657b78f0f72e081b5baf70a2dd3648");
					//设置http请求方式
					httpURLConnection.setRequestMethod("GET");
					httpURLConnection.connect();
					if(httpURLConnection.getResponseCode()==200){//判断是否加载成功
						//通过连接对象获取输入流
						InputStream inputStream=httpURLConnection.getInputStream();
						//获取到输入流也就是响应内容
						BufferedReader bufferedReader=new BufferedReader(
								new InputStreamReader(inputStream));//通过bufferedReader读取输入流
						String line=bufferedReader.readLine();
						while(line!=null&&line.length()>0){//循环获取字节
							builder.append(line);//加到builder中
							line=bufferedReader.readLine();
						}
							bufferedReader.close();
							inputStream.close();
							System.out.println(builder.toString());
							//解析QQ返回的信息
							jiexishuju(builder.toString());
							//return builder.toString();//此为包装后的方法(返回一个值)直接调用
					}else{
						toast("warn 请求异常");//如果返回码不为200(加载不成功)
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}}).start();
	}
	*//**
	 * 解析QQ数据的方法
	 *//*
	public void jiexishuju(String data){
		//创建一个JsonObject的对象，
		//方括号代表的是数组使用JsonArray,{}代表的是对象使用JsonObject()
		try {
			JSONObject jsonObject=new JSONObject(data);
			String nickname=jsonObject.getString("nickname");
			String six=jsonObject.getString("gender");
			String country=jsonObject.getString("country");
			String city=jsonObject.getString("city");
			//头像解析出来的是URI地址
			String touxiang=jsonObject.getString("figureurl");
			System.out.println(nickname+"QQ昵称");
			System.out.println(six+"性别");
			System.out.println(country+"国际");
			System.out.println(city+"家乡");
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}*/

}
