package com.example.pet;

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

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class NewAlbumActivity extends Activity{
	TextView quxiao,finish;
	EditText name,describe;
	String id = "1234";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_album_dongtai);
		init();
	}
	public void init(){
		quxiao=(TextView)findViewById(R.id.quxiao_new_albums);
		finish=(TextView)findViewById(R.id.finish_new_albums);
		quxiao.setOnClickListener(clickListener);
		finish.setOnClickListener(clickListener);
		name=(EditText)findViewById(R.id.album_name_newalbum);
		describe=(EditText)findViewById(R.id.album_describe_newalbum);
	}
	OnClickListener clickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch(v.getId()){
			case R.id.quxiao_new_albums:
				startActivity(new Intent(NewAlbumActivity.this,MainActivity.class));
				NewAlbumActivity.this.finish();
				break;
			case R.id.finish_new_albums:
				if(name.getText().toString().isEmpty()){
					toast("相册名字不能为空");
				}else{
					saveData();
				}
				break;
			}
		}
	};
	
	@SuppressLint("SimpleDateFormat")
	SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
	Date date=new Date();
	String time=simpleDateFormat.format(date);
	private void saveData(){
		new Thread(new Runnable(){
			public void run(){
				String httpUrl = "http://192.168.1.197/index.php/Home/Api/album";//php接口地址
				HttpPost httpRequest = new HttpPost(httpUrl);//http用post方法请求数据
				List<NameValuePair> params = new ArrayList<NameValuePair>();//建立一个列表用于添加数据
				params.add(new BasicNameValuePair("id", id));//添加获得的用户的账号
				params.add(new BasicNameValuePair("time", time));//时间
				params.add(new BasicNameValuePair("describe", describe.getText().toString()));//相册描述
				params.add(new BasicNameValuePair("albumname", name.getText().toString()));//相册名字
				try {
					HttpEntity httpEntity = new UrlEncodedFormEntity(params, "utf-8");//设置用户的字符集格式
					httpRequest.setEntity(httpEntity);//请求字符集数据
					HttpClient httpClient = new DefaultHttpClient();//http客户端
					HttpResponse httpResponse = httpClient.execute(httpRequest);//http客户端请求响应
					if(httpResponse.getStatusLine().getStatusCode() == 200){//http请求响应成功
						String strResult = null;
						strResult = EntityUtils.toString(httpResponse
								.getEntity());
						Looper.prepare();
						Toast.makeText(NewAlbumActivity.this, strResult + "保存成功", Toast.LENGTH_SHORT).show();
						startActivity(new Intent(NewAlbumActivity.this,MainActivity.class));
						NewAlbumActivity.this.finish();
						Looper.loop();
					} else {
						Looper.prepare();
						Toast.makeText(getApplicationContext(), "保存失败", Toast.LENGTH_SHORT).show();
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
	 * @param string
	 */
	public void toast(String string) {
		Toast toast=Toast.makeText(NewAlbumActivity.this, string, Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
}
