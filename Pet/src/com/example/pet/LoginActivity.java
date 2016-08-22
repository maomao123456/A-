package com.example.pet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class LoginActivity extends Activity implements OnClickListener {
	EditText userName, mima;
	Button zhuce, denglu;
	ImageView qq, wb, wx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_login);
		initView();
	}

	/**
	 * 通过ID找到控件
	 */
	public void initView() {
		userName = (EditText) findViewById(R.id.login_userName);
		mima = (EditText) findViewById(R.id.login_password);
		zhuce = (Button) findViewById(R.id.login_zhuce);
		denglu = (Button) findViewById(R.id.login_denglu);
		qq = (ImageView) findViewById(R.id.login_qq);
		wb = (ImageView) findViewById(R.id.login_wb);
		wx = (ImageView) findViewById(R.id.login_wx);
		userName.setOnFocusChangeListener(focusChangeListener);
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
			/*Intent intent=new Intent(getApplicationContext(), register.class);
			startActivity(intent);*/
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
			break;
		case R.id.login_wb:
			Toast.makeText(getApplicationContext(),"稍等片刻，微博接入中....",
					Toast.LENGTH_SHORT).show();
			break;
		case R.id.login_wx:
			Toast.makeText(getApplicationContext(), "稍等片刻，微信接入中....",
					Toast.LENGTH_SHORT).show();
			break;
		default:
			break;
		}
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
			if(!hasFocus){
				String str=userName.getText().toString();
				String str2=mima.getText().toString();
				if(userName.getText()==null||str.trim().length()==0){
					Toast.makeText(getApplicationContext(), "用户名不能为空！",
							Toast.LENGTH_SHORT).show();
				}else if(mima.getText()==null||str2.trim().length()==0){
					Toast.makeText(getApplicationContext(), "密码不能为空！",
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	};

}
