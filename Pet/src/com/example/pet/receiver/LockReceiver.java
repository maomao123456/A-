package com.example.pet.receiver;

import android.app.admin.DeviceAdminReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class LockReceiver extends DeviceAdminReceiver {

	public void onReceiver(Context context, Intent intent) {
		super.onReceive(context, intent);
		Log.i("onReceiver", "onReceiver");
	}

	public void onEnabled(Context context, Intent intent) {
		super.onEnabled(context, intent);
		Log.i("onEnabled", "激活使用");
	}

	public void pnDisabled(Context context, Intent intent) {
		super.onDisabled(context, intent);
		Log.i("onDisabled", "取消激活");
	}
}
