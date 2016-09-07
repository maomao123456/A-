package com.example.pet.lei;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.pet.R;

/**
 * 一个可以调用系统相册和照相机的类
 * @author Administrator
 *
 */
public class CameraAndAlbum extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
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
	// 剪裁后图片的宽和高
	private int output_X;
	private int output_Y;
	/**
	 * 判断应该调用拍照还是相册，1为相册，2为拍照
	 */
	/**
	 * 头像地址转图片
	 */
	static Bitmap bitmap = null;
		public void which(int numb,int output_X ,int output_Y){
			this.output_X=output_X;
			this.output_Y=output_Y;
			if(numb==1){
				fromGallery();
			}else if(numb==2){
				takePhoto();
			}
		}

		/**
		 * 拍照
		 */
		public void takePhoto() {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			//指定调用相机拍照后的照片储存的路径
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(
					new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
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
				cropRawPhoto(intent.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (hasSdcard()) {
					File tempFile = new File(
							Environment.getExternalStorageDirectory(),
							IMAGE_FILE_NAME);
					cropRawPhoto(Uri.fromFile(tempFile));
				} else {
					Toast.makeText(CameraAndAlbum.this, "没有SDcard", Toast.LENGTH_SHORT)
							.show();
				}
				break;
			case RESULT_REQUEST_CODE:
				if (intent != null) {
					setIconView(intent);
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

	// File file2 = new File(Environment.getExternalStorageDirectory() + "/ask",
	// "icon.jpg");
	File file;

	/**
	 * 提取保存剪裁之后的图片数据，并设置头像部分的View
	 */
	private void setIconView(Intent intent) {
		Bundle extras = intent.getExtras();
		if (extras != null) {
			bitmap = extras.getParcelable("data");
			// image.setImageBitmap(photo);
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
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
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
	 * 返回本地图片的路径
	 * 
	 * @return 图片路径
	 */
	public static String getLujing() {
		File file2 = new File(Environment.getExternalStorageDirectory()
				+ "/ask", "icon.jpg");

		return file2.getAbsolutePath();
	}

	public Bitmap getPath() {
		return bitmap;
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
}
