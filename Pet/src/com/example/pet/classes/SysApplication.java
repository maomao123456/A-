package com.example.pet.classes;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.app.Application;

//添加Activity页面方法，便于退出整个程序时关闭所有的Activity
public class SysApplication extends Application {

	private List<Activity> mList = new LinkedList<Activity>();
	private static SysApplication instance;
	
	private SysApplication(){}
	public synchronized static SysApplication getInstance(){
		if(null == instance){
			instance = new SysApplication();
		}
		return instance;
	}
	
	//add Activity
	public void addActivity(Activity activity){
		mList.add(activity);
	}
	
	public void exit(){
		try{
			for(Activity activity : mList){
				if(activity != null){
					activity.finish();
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			System.exit(0);
		}
	}
	
	public void onLowMemory(){
		super.onLowMemory();
		System.gc();
	}
}
