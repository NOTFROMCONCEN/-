package com.example.dklauncherdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @author Administrator
 * @year 2019
 * @Todo TODO 开机自启
 * @package_name com.example.dklauncherdemo
 * @project_name DKLauncherDemo
 * @file_name MyReceiver.java
 * @我的博客 https://naiyouhuameitang.club/
 */
public class MyReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			Intent i = new Intent(context, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(i);
		}
	}
}