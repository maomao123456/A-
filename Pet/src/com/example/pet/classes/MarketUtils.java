package com.example.pet.classes;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;

//应用商店
public class MarketUtils {

	//获取已安装应用商店的包名列表
	public static ArrayList<String> queryInstalledMarketPkgs(Context context){
		ArrayList<String> pkgs = new ArrayList<String>();
		if(context == null){
			return pkgs;
		}
		Intent intent = new Intent();
		intent.setAction("android.intent.action.MAIN");
		intent.addCategory("android.intent.category.APP_MARKET");
		PackageManager pm = context.getPackageManager();
		List<ResolveInfo> infos = pm.queryIntentActivities(intent, 0);
		if(infos == null || infos.size() == 0){
			return pkgs;
		}
		int size = infos.size();
		for(int i = 0; i < size; i++){
			String pkgName = "";
			try{
				ActivityInfo activityInfo = infos.get(i).activityInfo;
				pkgName = activityInfo.packageName;
			}catch(Exception e){
				e.printStackTrace();
			}
			if(!TextUtils.isEmpty(pkgName)){
				pkgs.add(pkgName);
			}
		}
		return pkgs;
 	}
	
	//过滤出已经安装的包名集合
	//params pkgs 待过滤包名集合
	//return 已安装的包名集合
	public static ArrayList<String> filterInstalledPkgs(Context context, ArrayList<String> pkgs){
		ArrayList<String> empty = new ArrayList<String>();
		if(context == null || pkgs == null || pkgs.size() == 0){
			return empty;
		}
		PackageManager pm = context.getPackageManager();
		List<PackageInfo> installedPkgs = pm.getInstalledPackages(0);
		int li = installedPkgs.size();
		int lj = pkgs.size();
		for(int j = 0; j < lj; j++){
			for(int i = 0; i < li; i++){
				String installedPkg = "";
				String checkPkg = pkgs.get(j);
				try{
					installedPkg = installedPkgs.get(i).applicationInfo.packageName;
				}catch(Exception e){
					e.printStackTrace();
				}
				if(TextUtils.isEmpty(installedPkg)){
					continue;
				}
				if(installedPkg.equals(checkPkg)){
					empty.add(installedPkg);
					break;
				}
				
			}
		}
		return empty;
	}
	
	//启动到APP详情页面
	//params appPkg APP的包名
	//params marketPkg 应用商店包名，如果为""，则由系统弹出应用商店列表供用户选择，否则跳转到目标市场的应用详情界面，某些应用商店可能会失败
	public static void launchAppDetail(String appPkg, String marketPkg){
		try{
			if(TextUtils.isDigitsOnly(appPkg)){
				return;
			}
			Uri uri = Uri.parse("market://details?id=" + appPkg);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			if(!TextUtils.isEmpty(marketPkg)){
				intent.setPackage(marketPkg);
			}
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			//APPUtils.getAppContext().startActivity(intent);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}
