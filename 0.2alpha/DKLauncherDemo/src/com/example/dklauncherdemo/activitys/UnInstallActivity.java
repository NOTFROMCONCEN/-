package com.example.dklauncherdemo.activitys;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.dklauncherdemo.MainActivity;
import com.example.dklauncherdemo.R;

public class UnInstallActivity extends Activity {

	private static final int REQUEST_CODE = 1;
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_un_install);
		Button uninstallButton = (Button) findViewById(R.id.uninstall_button);
		Button clsButton = (Button) findViewById(R.id.uninstall_cls_button);
		uninstallButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UninstallApk();
				finish();
				MainActivity.initAppList(getApplicationContext());
			}
		});
		clsButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
	}

	private void UninstallApk() {
		String packageName = MainActivity.string_app_info;
		Log.d(TAG, "UninstallApk: " + packageName);
		Uri packageURI = Uri.parse("package:" + packageName);
		Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageURI);
		startActivityForResult(uninstallIntent, REQUEST_CODE);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.d(TAG, "requestCode:" + String.valueOf(requestCode)
				+ ", resultCode:" + String.valueOf(resultCode));
		switch (requestCode) {
		case 1:
			if (resultCode == RESULT_OK) {
				Log.d(TAG, "onActivityResult: executed"); // 点击确定，为什么打印此行？？
			} else {
				Log.d(TAG, "onActivityResult: executed in here**");// 点击取消，打印此行。
			}
			break;
		default:
			break;
		}
	}
}