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
 * @Todo TODO ж��APP
 * @package_name com.example.dklauncherdemo.activitys
 * @project_name DKLauncherDemo
 * @file_name UnInstallActivity.java
 * @�ҵĲ��� https://naiyouhuameitang.club/
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
		// ж�ذ�ť
		uninstallButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UninstallApk();// ж��
			}
		});
		// �رհ�ť
		clsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();// ������ǰ�
			}
		});
	}

	/**
	 * ����ϵͳ��ж��apk����
	 */
	private void UninstallApk() {
		String packageName = MainActivity.string_app_info;
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		startActivityForResult(uninstallIntent, REQUEST_CODE);
	}

	/**
	 * �����и����⣬android 4.2.2ģ���������£�ж�����resultCode���صĲ�����RESULT_OK�������������������ˢ��
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {// ��鷵��Code
				Log.d(TAG, "onActivityResult: executed"); // ���ȷ����Ϊʲô��ӡ���У���
				MainActivity.initAppList(getApplicationContext());// ˢ��Ӧ���б�
				finish();
			} else {
				Log.d(TAG, "onActivityResult: executed in here**");// ���ȡ������ӡ���С�
				MainActivity.initAppList(getApplicationContext());// ˢ��Ӧ���б�
				finish();// ������ǰ�
			}
			break;
		default:
			break;
		}
	}
}