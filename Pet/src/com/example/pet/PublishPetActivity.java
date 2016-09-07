package com.example.pet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class PublishPetActivity extends Activity {
	RelativeLayout back;
	TextView fabu;
	ImageView image;
	EditText name, type, describe;
	View popview;
	LayoutInflater inflater;
	PopupWindow popWindow;
	/**
	 * 头像地址转图片
	 */
	Bitmap bitmap = null;
	String id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_pet);
		SysApplication.getInstance().addActivity(this);
		getId();
		init();
	}

	@SuppressLint("InflateParams")
	public void init() {
		inflater = this.getLayoutInflater();
		back = (RelativeLayout) findViewById(R.id.back_publish_petdetails_square);
		fabu = (TextView) findViewById(R.id.publish_publish_pet);
		image = (ImageView) findViewById(R.id.pet_image_publish);
		name = (EditText) findViewById(R.id.editname_publish);
		type = (EditText) findViewById(R.id.edittype_publish);
		describe = (EditText) findViewById(R.id.editdescribe_publish);
		back.setOnClickListener(clickListener);
		fabu.setOnClickListener(clickListener);
		image.setOnClickListener(clickListener);
		popview = (View) inflater.inflate(R.layout.popupwindow_add_head, null);
		bitmap = getLoacalBitmap(file2.getAbsolutePath());
	}

	/**
	 * 获得用户 登录后的id
	 */
	public void getId() {
		SharedPreferences pf = getSharedPreferences("pet_user", MODE_PRIVATE);
		id = pf.getString("id", "");
	}

	private static int output_X = 720;
	private static int output_Y = 526;

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.back_publish_petdetails_square:
				startActivity(new Intent(PublishPetActivity.this,
						MainActivity.class));
				PublishPetActivity.this.finish();
				break;
			case R.id.publish_publish_pet:
				saveData();
				startActivity(new Intent(PublishPetActivity.this,
						PublishPetActivity.class));
				toast("发布成功!");
				break;
			case R.id.pet_image_publish:
				creatPopupWindow();
				break;
			case R.id.popup_window_bg:
				popWindow.dismiss();
				break;
			case R.id.album_head_pop:
				fromGallery();
				popWindow.dismiss();
				break;
			case R.id.camera_head_pop:
				takePhoto();
				popWindow.dismiss();
				break;
			case R.id.cancel_head_pop:
				popWindow.dismiss();
				break;

			default:
				break;
			}
		}
	};

	@SuppressLint("ClickableViewAccessibility")
	@SuppressWarnings("deprecation")
	/**
	 * 创建一个popupWindow
	 */
	public void creatPopupWindow() {
		popWindow = new PopupWindow(popview, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, true);
		popWindow.setBackgroundDrawable(new BitmapDrawable());
		popWindow.setTouchable(true);
		popWindow.setOutsideTouchable(true);
		popWindow.update();
		popWindow.setFocusable(true);
		popWindow.setTouchInterceptor(new OnTouchListener() {
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		popWindow.showAtLocation(popview, Gravity.BOTTOM, 0, 0);
		TextView camera = (TextView) popview.findViewById(R.id.camera_head_pop);
		TextView album = (TextView) popview.findViewById(R.id.album_head_pop);
		TextView cancel = (TextView) popview.findViewById(R.id.cancel_head_pop);
		View popbg = (View) popview.findViewById(R.id.popup_window_bg);
		camera.setOnClickListener(clickListener);
		album.setOnClickListener(clickListener);
		cancel.setOnClickListener(clickListener);
		popbg.setOnClickListener(clickListener);
	}

	/**
	 * toast方法
	 * 
	 * @param string
	 */
	public void toast(String string) {
		Toast toast = Toast.makeText(PublishPetActivity.this, string,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	Date date = new Date();
	String time = simpleDateFormat.format(date);

	/**
	 * 存入数据库中
	 */
	private void saveData() {
		new Thread(new Runnable() {
			public void run() {
				String httpUrl = "http://192.168.1.197/index.php/Home/Api/uploadPetMessage";// php接口地址
				HttpPost httpRequest = new HttpPost(httpUrl);// http用post方法请求数据
				List<NameValuePair> params = new ArrayList<NameValuePair>();// 建立一个列表用于添加数据
				params.add(new BasicNameValuePair("id", id));// 添加获得的用户的账号
				if (bitmap != null) {
					params.add(new BasicNameValuePair("image", bitmap
							.toString()));// 宠物图片
					saveImage();
				}
				params.add(new BasicNameValuePair("describe", describe
						.getText().toString()));// 宠物描述
				params.add(new BasicNameValuePair("name", name.getText()
						.toString()));// 宠物名字
				params.add(new BasicNameValuePair("type", type.getText()
						.toString()));// 宠物类型
				params.add(new BasicNameValuePair("time", time));// 时间
				try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(params,
							"utf-8");// 设置用户的字符集格式
					httpRequest.setEntity(httpEntity);// 请求字符集数据
					HttpClient httpClient = new DefaultHttpClient();// http客户端
					HttpResponse httpResponse = httpClient.execute(httpRequest);// http客户端请求响应
					if (httpResponse.getStatusLine().getStatusCode() == 200) {// http请求响应成功
						String strResult = null;
						strResult = EntityUtils.toString(httpResponse
								.getEntity());
						Looper.prepare();
						toast("发布成功");
						startActivity(new Intent(PublishPetActivity.this,
								MainActivity.class));
						PublishPetActivity.this.finish();
						Looper.loop();
					} else {
						Looper.prepare();
						toast("发布失败");
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

	/**
	 * 相册相机的方法
	 */
	// 头像文件
	private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
	// 请求识别码
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;

	// 剪裁后图片的宽(X)和高(Y),

	/**
	 * 拍照
	 */
	private void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后的照片储存的路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
		startActivityForResult(intent, CAMERA_REQUEST_CODE);
		// 判断储存卡是否可用，储存照片文件
		if (hasSdcard()) {
			intent.putExtra(MediaStore.EXTRA_OUTPUT,
					Uri.fromFile(new File(Environment
							.getExternalStorageDirectory(), IMAGE_FILE_NAME)));

		}
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

	public void onActivityResult(int requestCode, int resultCode, Intent intent) {

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
				Toast.makeText(PublishPetActivity.this, "没有SDcard",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case RESULT_REQUEST_CODE:
			if (intent != null) {
				setIconView(intent);
				bitmap = getLoacalBitmap(file.getAbsolutePath());
				image.setImageBitmap(bitmap);
			}
			break;

		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, intent);
	};

	/**
	 * 剪裁原始图片
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
	 */
	private void setIconView(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
			image.setImageBitmap(photo);
			// 新建文件夹
			File nfile = new File(Environment.getExternalStorageDirectory()
					+ "/ask");
			nfile.mkdir();
			// 在根目录下面的ask文件夹下，创建temp_head_image.jpg文件
			file = new File(Environment.getExternalStorageDirectory() + "/ask",
					"icon.jpg");
			FileOutputStream fos = null;
			try {
				// 打开输出流，将图片数据填入文件中
				fos = new FileOutputStream(file);
				photo.compress(Bitmap.CompressFormat.PNG, 100, fos);
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

	/**
	 * 点击发布后储存图片
	 */
	public void saveImage() {
		File nfile = new File(Environment.getExternalStorageDirectory()
				+ "/pet_publish");
		if (!nfile.exists()) {
			nfile.mkdir();
		}
		// 在根目录下面的pet文件夹下，创建image.jpg文件
		file = new File(Environment.getExternalStorageDirectory()
				+ "/pet_publish", id + " " + time + "pet" + ".jpg");

		FileOutputStream fos = null;
		try {
			// 打开输出流，将图片数据填入文件中
			fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 99, fos);
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
