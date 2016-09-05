package com.example.pet.updateversion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

/**
 * 获取更新的信息，即update.txt文件
 * @author Administrator
 *
 */

public class UpdateInfoService {

	public UpdateInfoService(Context context){
		
	}
	
	public UpdateInfo getUpdateInfo() throws Exception{
		String path = GetServerUrl.getUrl() + "/update.txt";
		StringBuffer sBuffer = new StringBuffer();
		String line = null;
		BufferedReader bReader = null;
		try {
			//创建一个URL对象
			URL url = new URL(path);
			//通过URL对象，创建一个httpURLconnection对象(连接)
			HttpURLConnection httpURLConn = (HttpURLConnection) url.openConnection();
			//通过httpURLConnection对象，得到InputStream
			bReader = new BufferedReader(new InputStreamReader(httpURLConn.getInputStream()));
			//使用io流读取文件
			while((line = bReader.readLine()) != null){
				sBuffer.append(line);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try{
				if(bReader != null){
					bReader.close();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		String info = sBuffer.toString();
		UpdateInfo updateInfo = new UpdateInfo();
		updateInfo.setVersion(info.split("&")[1]);
		updateInfo.setDescription(info.split("&")[2]);
		updateInfo.setUrl(info.split("&")[3]);
		return updateInfo;
	}
}
