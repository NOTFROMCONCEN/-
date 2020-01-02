package com.example.dklauncherdemo;

import com.example.dklauncherdemo.toast.DiyToast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.Toast;

/**
 * �����װ����
 * 
 * @author Administrator
 * 
 */
public class AppInstallReceiver extends BroadcastReceiver {
	boolean isInstall = false;

	@Override
	public void onReceive(Context context, Intent intent) {
		if (intent.getAction().equals(Intent.ACTION_PACKAGE_ADDED)) {
			isInstall = true;
			String packageName = intent.getData().getSchemeSpecificPart();
			DiyToast.showToast(context, "�����װ��ɣ�" + packageName);
			if (isInstall) {
				MainActivity.initAppList(context);
			}
		}
	}
}