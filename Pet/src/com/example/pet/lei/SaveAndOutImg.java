package com.example.pet.lei;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;


/**
 * 存储图片和读取图片的类
 * @author Administrator
 *毛毛
 */
public class SaveAndOutImg {
	/**
	 * 存储网络图片到本地
	 * @param urlPath
	 * 图片的网址
	 * @param id
	 *用户的id 用于绑定图片位于本地的地址 
	 */
	public static void saveImg(final String urlPath,final String id){
		 new Thread(){
             public void run() {
                 try {
                     //String urlPath = "http://pic7.nipic.com/20100607/4791134_172835008083_2.jpg";
                     URL url = new URL(urlPath);
                     HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                     conn.setConnectTimeout(6*1000);  // 注意要设置超时，设置时间不要超过10秒，避免被android系统回收
                     if (conn.getResponseCode() != 200) throw new RuntimeException("请求url失败");
                     InputStream inSream = conn.getInputStream();
                     //把图片保存到项目的根目录
                   //新建文件夹 
                     if(hasSDK()){
                    	 File nfile = new File(Environment.getExternalStorageDirectory() + "/pet_touxiang");
                    	 if(!nfile.exists()){
                    		 nfile.mkdir();
                    	 }
              			//在根目录下面的pet文件夹下，创建image.jpg文件
              			File file = new File(Environment.getExternalStorageDirectory() + 
              					"/pet_touxiang", "pet"+id+".jpg");
                          readAsFile(inSream, file); 
                     }
                 } catch (Exception e) {
                     e.printStackTrace();
                 }
             };
         }.start();
	}
	/**
	 * 写入图片到本地的方法
	 * @param inSream
	 * 获取输出流
	 * @param file
	 * 文件保存的地址
	 * @throws Exception
	 * 抛异常
	 */
	 public static void readAsFile(InputStream inSream, File file) throws Exception{
	        FileOutputStream outStream = new FileOutputStream(file);
	        byte[] buffer = new byte[1024];
	        int len = -1;
	        while( (len = inSream.read(buffer)) != -1 ){
	            outStream.write(buffer, 0, len);
	        }
	        outStream.close();
	        inSream.close();
	    }
	/**
	 * 读取本地图片的方法 
	 * @param id
	 * 用户的id
	 * @return
	 * 返回一个bitmap对象
	 */
	public static Bitmap outImg(String id) {
		if (!hasSDK()) {
			return null;
		} else {
			try {
				File file = new File(Environment.getExternalStorageDirectory()
						+ "/pet_touxiang", "pet" + id + ".jpg");
				FileInputStream fis = new FileInputStream(
						file.getAbsoluteFile());
				return BitmapFactory.decodeStream(fis);

			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
	}
	/**
	 * 检查设备是否存在SDCardz的工具方法
	 * @return
	 * 是否存在
	 */
	public static boolean hasSDK() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}

	}	 
}
