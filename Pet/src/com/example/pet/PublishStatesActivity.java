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
import java.util.HashMap;
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

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.SimpleAdapter.ViewBinder;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pet.classes.SysApplication;
import com.example.pet.lei.CameraAndAlbum;

public class PublishStatesActivity extends Activity {
	TextView quxiao, fabiao, address;
	/**
	 * 用户id
	 */
	String id = "1234";
	/**
	 * 头像地址转图片
	 */
	Bitmap bitmap;
	EditText content;
	View popview;
	LayoutInflater inflater;
	PopupWindow popWindow;
	GridView gridview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_publish_states);
		SysApplication.getInstance().addActivity(this);
		init();
		addPics();
	}

	public void init() {
		inflater = this.getLayoutInflater();
		quxiao = (TextView) findViewById(R.id.quxiao_publish_states);
		fabiao = (TextView) findViewById(R.id.fabiao_publish_states);
		quxiao.setOnClickListener(clickListener);
		fabiao.setOnClickListener(clickListener);
		address = (TextView) findViewById(R.id.address_publish_content);
		content = (EditText) findViewById(R.id.content_publish_content);
		address.setOnClickListener(clickListener);
		popview = (View) inflater.inflate(R.layout.popupwindow_add_head, null);
		gridview = (GridView) findViewById(R.id.gridView_publish_state);
	}

	int numb;
	private static final String IMAGE_FILE_NAME = "temp_head_image.jpg";
	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESULT_REQUEST_CODE = 2;
	String pathImage;
	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.quxiao_publish_states:
				startActivity(new Intent(PublishStatesActivity.this,
						SquareDetailsActivity.class));
				PublishStatesActivity.this.finish();
				break;
			case R.id.fabiao_publish_states:
				saveContent();
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

	List<HashMap<String, Object>> list;
	SimpleAdapter adapter;

	/**
	 * 填充gridView
	 */
	public void addPics() {
		/**
		 * 载入默认图片添加图片加号 通过适配器实现 SimpleAdapter参数list为数据源
		 * R.layout.grid_item_publish_state为布局
		 */
		bitmap = BitmapFactory.decodeResource(getResources(),
				R.drawable.add_photo);
		list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("itemImage", bitmap);
		list.add(map);
		adapter = new SimpleAdapter(this, list,
				R.layout.gridview_item_publish_state,
				new String[] { "itemImage" },
				new int[] { R.id.imageView_gridview_publish_state });
		adapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// TODO Auto-generated method stub
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView i = (ImageView) view;
					i.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (list.size() == 9) { // 第一张为默认图片
					toast("图片数8张已满");
				} else if (position == 0) { // 点击图片位置为+ 0对应0张图片
					creatPopupWindow();
					// 通过onResume()刷新数据
				} else {
					toast("hahaha");
				}
			}
		});
	};

	/**
	 * 调用照相机
	 */
	public void takePhoto() {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		// 指定调用相机拍照后的照片储存的路径
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(
				Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
		startActivityForResult(intent, CAMERA_REQUEST_CODE);
		// 判断储存卡是否可用，储存照片文件
	}

	/**
	 * 调用相册
	 */
	public void fromGallery() {
		Intent intent = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(intent, IMAGE_REQUEST_CODE);
		onResume();
	}

	/**
	 * 获取图片路径 响应startActivityForResult
	 */
	Intent intent;
	File file = new File(Environment.getExternalStorageDirectory() + "/ask", "icon.jpg");
	Bitmap bitmap2=getLoacalBitmap(file.getAbsolutePath());
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 打开图片
		if (resultCode == RESULT_OK && requestCode == IMAGE_REQUEST_CODE) {
			Uri uri = data.getData();
			if (!TextUtils.isEmpty(uri.getAuthority())) {
				// 查询选择图片
				Cursor cursor = getContentResolver().query(uri,
						new String[] { MediaStore.Images.Media.DATA }, null,
						null, null);
				// 返回 没找到选择图片
				if (null == cursor) {
					return;
				}
				// 光标移动至开头 获取图片路径
				cursor.moveToFirst();
				pathImage = cursor.getString(cursor
						.getColumnIndex(MediaStore.Images.Media.DATA));
			}
		} // end if 打开图片
		else if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST_CODE) {
			if (hasSdcard() && data != null) {
				intent =data ;
			}
		}
	}

	// 刷新图片
	HashMap<String, Object> map;
	@Override
	protected void onResume() {
		super.onResume();
		map = new HashMap<String, Object>();
		if (!TextUtils.isEmpty(pathImage)) {
			Bitmap addbmp = BitmapFactory.decodeFile(pathImage);
			map.put("itemImage", addbmp);
			list.add(map);

		} else if (intent != null) {
			Bundle extras = intent.getExtras();
			bitmap2 = extras.getParcelable("data");
			map.put("itemImage", bitmap2);
			list.add(map);

		}
		adapter = new SimpleAdapter(this, list,
				R.layout.gridview_item_publish_state,
				new String[] { "itemImage" },
				new int[] { R.id.imageView_gridview_publish_state });
		adapter.setViewBinder(new ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data,
					String textRepresentation) {
				// TODO Auto-generated method stub
				if (view instanceof ImageView && data instanceof Bitmap) {
					ImageView i = (ImageView) view;
					i.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
		gridview.setAdapter(adapter);
		adapter.notifyDataSetChanged();
		// 刷新后释放防止手机休眠后自动添加
		pathImage = null;
	}
	
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	Date date=new Date();
	String time=simpleDateFormat.format(date);
	/**
	 * 保存发表的内容
	 */
	public void saveContent() {
		new Thread(new Runnable(){
			public void run(){
				String httpUrl = "http://192.168.1.197/index.php/Home/Api/publishContent";//php接口地址
				HttpPost httpRequest = new HttpPost(httpUrl);//http用post方法请求数据
				List<NameValuePair> params = new ArrayList<NameValuePair>();//建立一个列表用于添加数据
				params.add(new BasicNameValuePair("id", id));//添加获得的用户的账号
				if(list!=null){
					params.add(new BasicNameValuePair("image",list.toString()));//用户说说图片
					saveImage();
				}
				params.add(new BasicNameValuePair("content", content.getText().toString()));//说说内容
				params.add(new BasicNameValuePair("address", address.getText().toString()));//地址
				params.add(new BasicNameValuePair("time", time));//时间
				try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");//设置用户的字符集格式
					httpRequest.setEntity(httpEntity);//请求字符集数据
					HttpClient httpClient = new DefaultHttpClient();//http客户端
					HttpResponse httpResponse = httpClient.execute(httpRequest);//http客户端请求响应
					if(httpResponse.getStatusLine().getStatusCode() == 200){//http请求响应成功
						Looper.prepare();
						toast("发表成功");
						startActivity(new Intent(PublishStatesActivity.this,SquareDetailsActivity.class));
						PublishStatesActivity.this.finish();
						Looper.loop();
					} else {
						Looper.prepare();
						toast("发表失败");
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
	 * toast方法
	 * 
	 * @param string
	 */
	public void toast(String string) {
		Toast toast = Toast.makeText(PublishStatesActivity.this, string,
				Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
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
	 * 存储图片到sd卡中
	 */
	public void saveImage(){
		File nfile = new File(Environment.getExternalStorageDirectory() + "/pet_publish_state");
      	 if(!nfile.exists()){
      		 nfile.mkdir();
      	 }
			//在根目录下面的pet文件夹下，创建image.jpg文件
			file = new File(Environment.getExternalStorageDirectory() + 
					"/pet_publish_state", id+" "+time+"pet"+".jpg");
			
			FileOutputStream fos = null;
			try{
				//打开输出流，将图片数据填入文件中
				fos = new FileOutputStream(file);
				bitmap.compress(Bitmap.CompressFormat.PNG, 99, fos);
				try{
					fos.flush();
					fos.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}catch(FileNotFoundException e){
				e.printStackTrace();
			}
	}
}
