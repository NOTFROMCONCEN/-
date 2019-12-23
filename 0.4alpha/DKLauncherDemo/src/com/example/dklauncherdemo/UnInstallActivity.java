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
import android.widget.TextView;

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

	private TextView tv_pack_name;
	private Button btn_uninstall_con;
	private Button btn_uninstall_cls;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_un_install);
		initView();
		// ж�ذ�ť
		btn_uninstall_con.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UninstallApk();// ж��
			}
		});
		// �رհ�ť
		btn_uninstall_cls.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();// ������ǰ�
			}
		});
		// ������ʾ����
		tv_pack_name.setText(MainActivity.string_app_info);
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_uninstall_con = (Button) findViewById(R.id.btn_uninstall_con);
		btn_uninstall_cls = (Button) findViewById(R.id.btn_uninstall_cls);
		tv_pack_name = (TextView) findViewById(R.id.tv_pack_name);
	}

	/**
	 * ����ϵͳ��ж��apk����
	 */
	private void UninstallApk() {
		String packageName = MainActivity.string_app_info;
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		startActivityForResult(uninstallIntent, 1);
	}

	/**
	 * �����и����⣬android 4.2.2ģ���������£�ж�����resultCode���صĲ�����RESULT_OK�������������������ˢ��
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {// ��鷵��Code
				MainActivity.initAppList(getApplicationContext());// ˢ��Ӧ���б�
				finish();
			} else {
				MainActivity.initAppList(getApplicationContext());// ˢ��Ӧ���б�
				finish();// ������ǰ�
			}
			break;
		default:
			break;
		}
	}
}