package com.example.pet.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.AboutPetsActivity;
import com.example.pet.AccountManagerActivity;
import com.example.pet.DataInformationActivity;
import com.example.pet.R;
import com.example.pet.SetActivity;
import com.example.pet.lei.CircularImage;
import com.example.pet.lei.JieXiShuJu;
import com.example.pet.lei.SaveAndOutImg;

public class MineFragment extends Fragment {

	CircularImage user_icon;// 用户头像
	TextView user_nickname, user_id;// 用户的呢称和ID
	TextView concern_num, fans_num, collection_num;// 用户关注，粉丝，和收藏
	RelativeLayout data_infor, account_manager, about_pets, settings;// 用户的信息，账号管理，用户的萌萌的宠物，以及设置
	LayoutInflater inflater;
	View view;
	String userName, userId;
	int concern, fans, collection;
	// 修改头像
	PopupWindow popupWindow;// 自定义对话框
	View windView;
	LayoutInflater windInflater;

	@SuppressLint("InflateParams")
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle bundle) {
		view = inflater.inflate(R.layout.activity_mine, null);
		// 修改头像
		windInflater = this.getLayoutInflater(bundle);
		windView = windInflater.inflate(R.layout.activity_update_icon, null);
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
	Bitmap bitmap = null;
	/**
	 * 判断是否为第三方登录 numb=3为第三方 numb=2自己注册 numb=1为自己登录
	 */
	int numb;

	/**
	 * 获取用户登录后的个人信息 (并判断是否为第三方登录)
	 */
	@SuppressWarnings("static-access")
	public void getUserIfon() {
		/**
		 * 第三方登录返回的头像地址 (需要开启一个支线程来把头像地址转换为Bitmap) 示列 new Thread(){
		 * 
		 * @Override public void run() { bitmap =Util.getbitmap(tongxiang);
		 *           Message msg = new Message(); msg.obj = bitmap; msg.what =
		 *           2; handler.sendMessage(msg); } }.start(); 然后在handler中接收消息
		 *           更新UI Bitmap bitmap = (Bitmap)msg.obj;
		 *           XXX.setsetImageBitmap(bitmap);
		 */
		SharedPreferences pf = getActivity().getSharedPreferences("pet_user",
				getActivity().MODE_PRIVATE);
		numb = pf.getInt("disanfang", 0);
		name = pf.getString("nicheng", "无");
		id = pf.getString("id", "");
		tongxiang = pf.getString("tongxiang", "无地址");
		getInfo(id);
		user_id.setText(id);
	}
	/**
	 * 获得用户的Json数据
	 */
	String str2;
	/**
	 * 获得用户信息
	 */
	public void getInfo(final String id){
		new Thread(new Runnable() {
			@Override
			public void run() {
				str2=JieXiShuJu.doGet("http://192.168.1.192/index.php/Home/Pet/getuserinfo"
						, new String[]{"id"}, new String[]{id});
				Message msg=new Message();
				msg.what=1;
				handler.sendMessage(msg);
			}
		}).start();
	}

	/**
	 * 根据用户登录的状况来刷新UI
	 */
	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				try {
					JSONObject object=new JSONObject(str2);
					String name1=object.getString("nickname");
					//判断用户是否为第三方登录
					if(numb!=3){//不是第三方登录
						user_nickname.setText(name1);
					}else{
						user_nickname.setText(name);
					}
					//根据用户id获取头像
					if(SaveAndOutImg.outImg(id)==null){//判断用户是否已存在头像
						user_icon.setImageResource(R.drawable.logo);
					}else{
						user_icon.setImageBitmap(SaveAndOutImg.outImg(id));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
		bitmap = getLoacalBitmap(file2.getAbsolutePath());
		user_icon.setImageBitmap(bitmap);
		// 触发点击
		user_icon.setOnClickListener(clickListener);
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
			case R.id.mine_user_icon:
				createUpdateIconPopupWindow();
				break;
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

	/**
	 * 更改头像
	 */
	@SuppressWarnings("deprecation")
	public void createUpdateIconPopupWindow() {
		// 初始化一个popupWindow的对象并给以长宽
		popupWindow = new PopupWindow(windView, LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT, true);
		// 设置popupWindow背景，不设置无法监听(背景设置为全透明)
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		// 设置popupWindow窗口外布局是否可以点击
		popupWindow.setOutsideTouchable(true);
		popupWindow.setTouchable(true);
		// 设置是否可以点击
		popupWindow.setTouchInterceptor(new OnTouchListener() {
			@SuppressLint("ClickableViewAccessibility")
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		popupWindow.showAtLocation(windView, Gravity.FILL, 0, 0);
		TextView photo = (TextView) windView.findViewById(R.id.take_photo);
		TextView album = (TextView) windView.findViewById(R.id.from_album);
		TextView cancel = (TextView) windView
				.findViewById(R.id.cancel_update_icon);
		View iconBg = (View) windView.findViewById(R.id.update_icon_bg);
		photo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				takePhotot();
				popupWindow.dismiss();
			}
		});
		album.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				fromGallery();
				popupWindow.dismiss();
			}
		});

		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});

		iconBg.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				popupWindow.dismiss();
			}
		});
	}

	// 头像文件
	private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
	// 请求识别码
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	// 剪裁后图片的宽(X)和高(Y),138 X 138的正方形
	private static int output_X = 138;
	private static int output_Y = 138;

	/**
	 * 拍照
	 */
	private void takePhotot() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后的照片储存的路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
		startActivityForResult(intent, CAMERA_REQUEST_CODE);
	}

	/**
	 * 从本地相册选取图片作为头像
	 */
	private void fromGallery() {
		Intent intent = new Intent(Intent.ACTION_PICK, null);
		// 调用相机拍照后的照片储存
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(intent, IMAGE_REQUEST_CODE);
	}

	@SuppressWarnings("static-access")
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// 用户没有进行有效的设置操作，返回
		if (resultCode ==getActivity().RESULT_CANCELED) {
			return;
		}
		switch (requestCode) {
		case IMAGE_REQUEST_CODE:
			if (intent != null) {
				cropRawPhoto(intent.getData());
			}
			break;
		case CAMERA_REQUEST_CODE:
			if (hasSdcard()) {
				File tempFile = new File(
						Environment.getExternalStorageDirectory(),
						IMAGE_FILE_NAME);
				cropRawPhoto(Uri.fromFile(tempFile));
			} else {
				Toast.makeText(getActivity(), "没有SDcard", Toast.LENGTH_SHORT)
						.show();
			}
			break;
		case RESULT_REQUEST_CODE:
			if (intent != null) {
				setIconView(intent);
				bitmap = getLoacalBitmap(file.getAbsolutePath());
				user_icon.setImageBitmap(bitmap);
			} else {
				user_icon.setImageResource(R.drawable.logo);
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, intent);
	};

	/**
	 * 剪裁原始图片
	 * @param uri图片来源
	 */
	public void cropRawPhoto(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// 设置剪裁
		intent.putExtra("crop", "true");
		// aspectX, aspectY:宽高的比例
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		// outputX, outputY:剪裁图片的宽高
		intent.putExtra("outputX", output_X);
		intent.putExtra("outputY", output_Y);
		intent.putExtra("return-data", true);
		startActivityForResult(intent, RESULT_REQUEST_CODE);
	}

	File file2 = new File(Environment.getExternalStorageDirectory() + "/ask",
			"icon.jpg");
	File file;

	/**
	 * 提取保存剪裁之后的图片数据，并设置头像部分的View 
	 * @param intent保存到的路径
	 */
	private void setIconView(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			user_icon.setImageBitmap(photo);
			// 新建文件夹
			File nfile = new File(Environment.getExternalStorageDirectory()
					+ "/pet_touxiang");
			if (!nfile.exists()) {
				nfile.mkdir();
			}
			// 在根目录下面的pet文件夹下，创建image.jpg文件
			file = new File(Environment.getExternalStorageDirectory()
					+ "/pet_touxiang", "pet" + id + ".jpg");

			FileOutputStream fos = null;
			try {
				// 打开输出流，将图片数据填入文件中
				fos = new FileOutputStream(file);
				photo.compress(Bitmap.CompressFormat.PNG, 99, fos);
				try {
					fos.flush();
					fos.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * 检查设备是否存在SDCardz的工具方法
	 */
	public static boolean hasSdcard() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}

	}

	// 保存用户的关注、粉丝、收藏等信息
	public void toSave() {
		/*concern = 0;
		fans = 0;
		collection = 0;
		SharedPreferences shareCollection = getActivity().getSharedPreferences("pet_shoucang",
				getActivity().MODE_PRIVATE);
		SharedPreferences share = getActivity().getSharedPreferences("pet_shoucang",
				getActivity().MODE_PRIVATE);
		for (int numb = 1; numb < 5; numb++) {
			String str = numb + "关注";
			String str2 = share.getString(str, "1");
			if (str2.equals("已关注")) {
				concern++;
			}
		}
		for (int numb = 1; numb < 5; numb++) {
			String str = numb + "收藏";
			String str2 = shareCollection.getString(str, "1");
			if (str2.equals("已收藏")) {
				collection++;
			}
		}*/
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

	/**
	 * 加载本地图片
	 */
	public static Bitmap getLoacalBitmap(String url) {
		try {
			FileInputStream fis = new FileInputStream(url);
			return BitmapFactory.decodeStream(fis);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}
