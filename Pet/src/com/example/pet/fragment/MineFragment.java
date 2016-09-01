package com.example.pet.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.pet.AboutPetsActivity;
import com.example.pet.AccountManagerActivity;
import com.example.pet.DataInformationActivity;
import com.example.pet.R;
import com.example.pet.SetActivity;
import com.example.pet.lei.CircularImage;
import com.example.pet.qq.Util;

public class MineFragment extends Fragment {

	CircularImage user_icon;// 用户头像
	TextView user_nickname, user_id;// 用户的呢称和ID
	TextView concern_num, fans_num, collection_num;// 用户关注，粉丝，和收藏
	RelativeLayout data_infor, account_manager, about_pets, settings;// 用户的信息，账号管理，用户的萌萌的宠物，以及设置
	LayoutInflater inflater;
	View view;
	String userName, userId;
	int concern, fans, collection;

	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.activity_mine, null);
		initView();
		getUserIfon();
		return view;
	}
	/**
	 * 用户登录后所返回的城市信息
	 */
	String city;
	/**
	 * 用户登录后所返回的昵称
	 */
	String name;
	/**
	 * 用户登录后所返回的ID
	 */
	String id;
	/**
	 * 用户登录后所返回的头像
	 */
	String tongxiang;
	/**
	 * 头像地址转图片
	 */
	Bitmap bitmap=null;
	/**
	 * 判断是否为第三方登录
	 * numb=3为第三方
	 * numb=2自己注册
	 * numb=1为自己登录
	 */
	int numb;
	/**
	 * 获取用户登录后的个人信息
	 * (并判断是否为第三方登录)
	 */
	@SuppressWarnings("static-access")
	public void getUserIfon(){
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
		SharedPreferences pf=getActivity().
				getSharedPreferences("pet_user", getActivity().MODE_PRIVATE);
		numb = pf.getInt("disanfang", 0);
		city = pf.getString("city", "无");
		name = pf.getString("nicheng", "无");
		id = pf.getString("id", "");
		tongxiang = pf.getString("tongxiang", "无地址");
		user_nickname.setText(name);
		user_id.setText(id);
		new Thread(){
            @Override			
            public void run() {
                bitmap =Util.getbitmap(tongxiang);
                Message msg = new Message();
                msg.obj = bitmap;
                msg.what = 1;
                if(numb==3){
                	handler.sendMessage(msg);
                }
            }                       
        }.start();
	}
	/**
	 * 根据用户登录的状况来刷新UI
	 */
	@SuppressLint("HandlerLeak")
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				Bitmap bitmap = (Bitmap)msg.obj;
				user_icon.setImageBitmap(bitmap);
				break;

			default:
				break;
			}
		};
	};
	// 初始化视图
	private void initView() {
		user_icon = (CircularImage) view.findViewById(R.id.mine_user_icon);
		user_nickname = (TextView) view.findViewById(R.id.mine_user_nickname);
		user_id = (TextView) view.findViewById(R.id.mine_user_id);
		concern_num = (TextView) view.findViewById(R.id.concern_num);
		fans_num = (TextView) view.findViewById(R.id.fans_num);
		collection_num = (TextView) view.findViewById(R.id.collection_num);
		data_infor = (RelativeLayout) view.findViewById(R.id.information_next);
		account_manager = (RelativeLayout) view
				.findViewById(R.id.account_manager_next);
		about_pets = (RelativeLayout) view.findViewById(R.id.about_pets_next);
		settings = (RelativeLayout) view.findViewById(R.id.settings_next);
		user_icon.setImageResource(R.drawable.logo);
		// 触发点击
		concern_num.setText(concern + "");
		fans_num.setText(fans + "");
		collection_num.setText(collection + "");
		data_infor.setOnClickListener(clickListener);
		account_manager.setOnClickListener(clickListener);
		about_pets.setOnClickListener(clickListener);
		settings.setOnClickListener(clickListener);
	}

	// 点击按钮方法
	OnClickListener clickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.information_next:
				toDataInfor();
				break;
			case R.id.account_manager_next:
				toAccountManager();
				break;
			case R.id.about_pets_next:
				toAboutPets();
				break;
			case R.id.settings_next:
				toSetting();
				break;
			default:
				break;
			}
		}
	};
	
	//保存用户的关注、粉丝、收藏等信息
	@SuppressWarnings("static-access")
	public void toSave(){
		concern = 0;
		fans = 0;
		collection = 0;
		SharedPreferences share = getActivity().getSharedPreferences("count", getActivity().MODE_PRIVATE);
		for(int numb = 1;numb < 5;numb++){
			String str = numb + "关注";
			String str2 = share.getString(str, "1");
			if(str2.equals("已关注")){
				concern++;
			}
		}
		for(int numb = 1; numb < 5; numb++){
			String str = numb + "收藏";
			String str2 = share.getString(str, "1");
			if(str2.equals("已收藏")){
				collection++;
			}
		}
	}

	// 跳转到资料信息界面
	private void toDataInfor() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), DataInformationActivity.class);
		startActivity(intent);
	}

	// 跳转到账户管理界面
	private void toAccountManager() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), AccountManagerActivity.class);
		startActivity(intent);
	}

	// 跳转到关于萌宠界面
	private void toAboutPets() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), AboutPetsActivity.class);
		startActivity(intent);
	}

	// 跳转到设置界面
	private void toSetting() {
		Intent intent = new Intent();
		intent.setClass(getActivity(), SetActivity.class);
		startActivity(intent);
	}
}
