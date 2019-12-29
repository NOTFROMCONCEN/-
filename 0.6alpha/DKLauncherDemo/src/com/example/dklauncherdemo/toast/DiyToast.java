package com.example.dklauncherdemo.toast;

import android.content.Context;
import android.widget.Toast;

/**
 * @author Administrator
 * @year 2019
 * @Todo TODO 自定义Toast
 * @package_name com.example.dklauncherdemo.toast
 * @project_name DKLauncherDemo
 * @file_name DiyToast.java
 * @我的博客 https://naiyouhuameitang.club/
 */
public class DiyToast {
	private static Toast toast;// 创建Toast

	public static void showToast(Context context, String string) {
		if (toast == null) {
			toast = Toast.makeText(context, string, Toast.LENGTH_SHORT);
		} else {
			toast.setText(string);
		}
		toast.show();
	}
}
