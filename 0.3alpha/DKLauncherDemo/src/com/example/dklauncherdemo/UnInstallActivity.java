package com.example.dklauncherdemo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * @author Administrator
 * @year 2019
 * @Todo TODO 卸载APP
 * @package_name com.example.dklauncherdemo.activitys
 * @project_name DKLauncherDemo
 * @file_name UnInstallActivity.java
 * @我的博客 https://naiyouhuameitang.club/
 */
public class UnInstallActivity extends Activity {

	private static final int REQUEST_CODE = 1;
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_un_install);
		Button uninstallButton = (Button) findViewById(R.id.btn_uninstall_con);
		Button clsButton = (Button) findViewById(R.id.btn_uninstall_cls);
		// 卸载按钮
		uninstallButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UninstallApk();// 卸载
			}
		});
		// 关闭按钮
		clsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();// 结束当前活动
			}
		});
	}

	/**
	 * 唤起系统的卸载apk功能
	 */
	private void UninstallApk() {
		String packageName = MainActivity.string_app_info;
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		startActivityForResult(uninstallIntent, REQUEST_CODE);
	}

	/**
	 * 这里有个问题，android 4.2.2模拟器环境下，卸载软件resultCode返回的并不是RESULT_OK，保险起见两处都加上刷新
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {// 检查返回Code
				Log.d(TAG, "onActivityResult: executed"); // 点击确定，为什么打印此行？？
				MainActivity.initAppList(getApplicationContext());// 刷新应用列表
				finish();
			} else {
				Log.d(TAG, "onActivityResult: executed in here**");// 点击取消，打印此行。
				MainActivity.initAppList(getApplicationContext());// 刷新应用列表
				finish();// 结束当前活动
			}
			break;
		default:
			break;
		}
	}
}