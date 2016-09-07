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
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
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
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import com.example.pet.classes.SysApplication;
import com.example.pet.lei.PhoneTy;

public class RegisterActivity extends Activity implements OnClickListener {
	TextView denglu, getYangzhengma, xieyiNeirong, comeing;
	LinearLayout backQuyu, xieyiQuyu;
	EditText shoujihao, yanzhengma, shezhimima, yaoqingma;
	CheckBox xieyiCb, showMima;
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
	Thread thread = null;
	private int time = 60;
	private boolean tag = true;
	private static final String APPKEY = "168b024d49944";
	private static final String APPSECRETE = "3554d93a1086b52572d38877fa651526";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN
						| WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		setContentView(R.layout.activity_register);
		SysApplication.getInstance().addActivity(this);
		initView();
	}

	/**
	 * 通过id找到控件
	 */
	public void initView() {
		sc = (ScrollView) findViewById(R.id.register_scroll);
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
		showMima = (CheckBox) findViewById(R.id.register_show_mima);
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
		// 启动短信验证sdk
		SMSSDK.initSDK(RegisterActivity.this, APPKEY, APPSECRETE);
		EventHandler eventHandler = new EventHandler() {
			@Override
			public void afterEvent(int event, int result, Object data) {
				Message msg = new Message();
				msg.arg1 = event;
				msg.arg2 = result;
				msg.obj = data;
				msg.what = 1;
				handler.sendMessage(msg);
			}
		};
		// 注册回调监听接口
		SMSSDK.registerEventHandler(eventHandler);
	}

	/**
	 * 布局尺寸的调整
	 */
	public void changeSize() {
		Handler mHandler = new Handler();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				sc.fullScroll(ScrollView.FOCUS_DOWN);
			}
		}, 300);
	}

	public final String format = "^([A-Za-z]|[0-9])+$";// 密码格式
	public final String format2 = "^([A-Za-z0-9])+$";// 密码格式
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
					if (!phone) {
						toast("请输入正确的手机号！");
					}
					break;
				case R.id.register_yanzhengma:
					if (yanzhengCd.length() != 4) {
						toast("验证码长度为4位数");
					}
					break;
				case R.id.register_shezhimima:
					if (password.length() < 6 || password.length() > 20) {
						toast("密码为6-20位数字或字母!");
					} else if (!password.matches(format2)) {
						toast("密码为6-20位数字或字母!");
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
	public boolean checked() {
		phoneNumb = shoujihao.getText().toString();
		boolean phone = PhoneTy.isPhoneNb(phoneNumb);
		yanzhengCd = yanzhengma.getText().toString();
		password = shezhimima.getText().toString();
		inviteCd = yaoqingma.getText().toString();
		if (!phone) {
			toast("请输入正确的手机号！");
			return false;
		} else if (yanzhengCd.length() != 4) {
			toast("格式错误：验证码长度为4位");
			return false;
		} else if (password.length() < 6 || password.length() > 20) {
			toast("密码只能是6-20位数字或字母!");
			return false;
		} else if (!password.matches(format2)) {
			toast("密码只能是数字或字母!");
			return false;
		} else if (yaoqingma.getText() == null
				|| yaoqingma.getText().toString().trim().length() != 0) {
			toast("邀请码不正确！");
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 普通控件的点击事件
	 */
	public void onClick(View v) {
		final String phoneNums = shoujihao.getText().toString();
		String yzm = yanzhengma.getText().toString();
		switch (v.getId()) {
		case R.id.register_shoujihao:
			changeSize();
			break;
		case R.id.register_show_mima:// 是否显示密码图标
			if (showMima.isChecked()) {
				shezhimima.setInputType(EditorInfo.TYPE_CLASS_TEXT
						| EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
			} else {
				shezhimima.setInputType(EditorInfo.TYPE_CLASS_TEXT
						| EditorInfo.TYPE_TEXT_VARIATION_WEB_PASSWORD);
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
		case R.id.register_huoquyanzhengma:// 获取验证码(按钮)
			boolean phone = PhoneTy.isPhoneNb(phoneNums);
			if (!phone) {
				toast("请输入正确的手机号！");
				break;
			}
			if (!checkNetwork()) {
				toast("网络未连接，无法获取验证码！");
				break;
			}
			getYangzhengma.setText("获取验证码");
			getYangzhengma.setClickable(true);
			new AlertDialog.Builder(RegisterActivity.this)
					.setTitle("确认手机号码")
					.setMessage("我们将发送验证码到这个号码:\n" + "+86 " + phoneNums)
					.setPositiveButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
								}
							})
					.setNegativeButton("好",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									// 2. 通过sdk发送短信验证
									SMSSDK.getVerificationCode("86", phoneNums);
									changeTime();
								}
							}).create().show();
			break;
		case R.id.register_tongyixieyi_quyu:// 同意协议区域
			if (xieyiCb.isChecked()) {
				xieyiCb.setChecked(false);
			} else {
				xieyiCb.setChecked(true);
			}
			break;
		case R.id.register_xieyi_neirong:// 协议内容 展示；
			intent = new Intent(getApplicationContext(), XieYiActivity.class);
			startActivity(intent);
			break;
		case R.id.register_lijijiaru:// 立即加入
			if (!checkNetwork()) {
				toast("网络未连接，请检查网络设置！");
			} else {
				if (!checked()) {
					// 自动检测 错误 不用再调用方法
				} else {
					if (xieyiCb.isChecked()) {
						SMSSDK.submitVerificationCode("86", phoneNums, yzm);
					} else {
						Builder builder = new AlertDialog.Builder(
								RegisterActivity.this);
						builder.setTitle("你还未同意《隐私与服务协议》！");
						builder.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int which) {
									}
								});
						builder.show();
					}
				}
			}
			break;
		default:
			break;
		}
	}

	int tiaozhuan = 0;

	/**
	 * 用户信息存到数据库中
	 */
	public void complete() {
		toast("正在为你注册...");
		new Thread(new Runnable() {
			@SuppressWarnings("unused")
			public void run() {
				String httpUrl = "http://192.168.1.192/index.php/Home/Pet/add";// PHP接口地址
				HttpPost httpRequest = new HttpPost(httpUrl);// http用post方法请求数据
				List<NameValuePair> params = new ArrayList<NameValuePair>();// 建立一个列表用于添加数据
				params.add(new BasicNameValuePair("phonenumber", shoujihao
						.getText().toString().trim()));// 添加用户的用户名
				params.add(new BasicNameValuePair("id", shoujihao
						.getText().toString().trim()));//根据手机号创建用户的ID
				params.add(new BasicNameValuePair("password", shezhimima
						.getText().toString().trim()));// 添加用户的密码
				HttpEntity httpentity = null;
				try {
					httpentity = new UrlEncodedFormEntity(params, "utf8");// 设置用户字符集的格式
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
					threeTz(2, "", shoujihao
							.getText().toString(), "无", "无", "无");
					Looper.prepare();
					toast("注册成功！启宠资源大门已为你敞开。");
					Looper.loop();
				} else {
					Looper.prepare();
					toast("注册失败或用户已存在！");
					Looper.loop();
				}
			}
		}).start();
	}

	/**
	 * 验证网络状态
	 */
	private boolean checkNetwork() {
		ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		if (connManager.getActiveNetworkInfo() != null) {
			return connManager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}

	/**
	 * 提示全局通用
	 */
	public void toast(String string) {
		Toast toast = Toast.makeText(RegisterActivity.this, string,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	public void changeTime() {
		thread = new Thread() {
			@SuppressWarnings("unused")
			public void run() {
				if (tag) {
					while (time > 0) {
						time--;
						if (RegisterActivity.this == null) {
							break;
						}
						RegisterActivity.this.runOnUiThread(new Runnable() {
							public void run() {
								getYangzhengma.setText("重新发送(" + time + ")");
								getYangzhengma.setClickable(false);
							}
						});
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
				if (RegisterActivity.this != null) {
					RegisterActivity.this.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							getYangzhengma.setText("获取验证码");
							getYangzhengma.setClickable(true);
							handler.sendEmptyMessage(3);
						}
					});
				}
			};
		};
		thread.start();
	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 3) {

			} else if (msg.what == 1) {
				int event = msg.arg1;
				int result = msg.arg2;
				Object data = msg.obj;
				Log.e("event", "event=" + event);
				if (result == SMSSDK.RESULT_COMPLETE) {
					// 短信注册成功后，返回MainActivity,然后提示
					if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {// 提交验证码成功
						toast("验证码校验成功！");
						complete();
					} else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
						toast("正在发送验证码....");
					} else {
						Throwable throwable = (Throwable) data;
						throwable.printStackTrace();
					}
				} else if (result == SMSSDK.RESULT_ERROR) {
					try {
						Throwable throwable = (Throwable) data;
						throwable.printStackTrace();
						JSONObject object = new JSONObject(
								throwable.getMessage());
						String des = object.optString("detail");// 错误描述
						int status = object.optInt("status");// 错误代码
						if (status > 0 && !TextUtils.isEmpty(des)) {
							toast("" + des);
							return;
						}
					} catch (Exception e) {
						toast("验证码校验失败！ 请重试");
					}
				} else {

				}
			}
		}
	};

	protected void onDestroy() {
		super.onDestroy();
		SMSSDK.unregisterAllEventHandler();
	};

	/**
	 * 保存用户登录方式 第三方登录成功后 进行跳转并保存第三方用户信息
	 * 
	 * @param numb
	 *            numb=1:用自己的账号登录的 numb=2刚注册的用户 numb=3为第三方登录的
	 * @param nickName
	 *            用户的昵称
	 * @param ID
	 *            用户的id
	 * @param six
	 *            用户的性别
	 * @param touxiang
	 *            用户的头像 为一个网址
	 * @param city
	 *            用户所在地
	 */
	public void threeTz(int numb, String nickName, String ID, String six,
			String touxiang, String city) {
		Editor editor = getSharedPreferences("pet_user", MODE_PRIVATE).edit();
		editor.putInt("disanfang", numb);
		editor.putString("nicheng", nickName);
		editor.putString("id", ID);
		editor.putString("six", six);
		editor.putString("tongxiang", touxiang);
		editor.putString("city", city);
		editor.commit();
		Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
		startActivity(intent);
		RegisterActivity.this.finish();
	}

}
