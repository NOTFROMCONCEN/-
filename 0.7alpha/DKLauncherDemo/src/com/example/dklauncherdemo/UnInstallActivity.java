package com.example.dklauncherdemo;

import com.example.dklauncherdemo.adapter.Code;
import com.example.dklauncherdemo.adapter.DeskTopGridViewBaseAdapter;
import com.example.dklauncherdemo.toast.DiyToast;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.ImageView;
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
				showDialogView();
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
		tv_pack_name.setText(DeskTopGridViewBaseAdapter.string_app_info);
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_uninstall_con = (Button) findViewById(R.id.btn_uninstall_con);
		btn_uninstall_cls = (Button) findViewById(R.id.btn_uninstall_cls);
		tv_pack_name = (TextView) findViewById(R.id.tv_pack_name);
	}

	private void showDialogView() {
		View view = LayoutInflater.from(UnInstallActivity.this).inflate(
				R.layout.dialog_code_show, null, false);
		final EditText et_code_set = (EditText) view
				.findViewById(R.id.et_code_set);
		final ImageView iv_code_show = (ImageView) view
				.findViewById(R.id.iv_code_show);
		iv_code_show.setImageBitmap(Code.createBitmap());
		DiyToast.showToast(getApplicationContext(), "��֤�룺" + Code.code);
		iv_code_show.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				iv_code_show.setImageBitmap(Code.createBitmap());
				DiyToast.showToast(getApplicationContext(), "��֤�룺" + Code.code);
			}
		});
		new AlertDialog.Builder(UnInstallActivity.this).setTitle("��֤")
				.setPositiveButton("����", null).setView(view)
				.setNegativeButton("ȷ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						if (et_code_set.getText().toString().equals(Code.code)) {
							UninstallApk();
						} else {
							DiyToast.showToast(getApplicationContext(),
									"������֤ʧ��");
							showDialogView();
						}
					}
				}).setMessage("��������������֤����ȷ���������˲���").show();
	}

	/**
	 * ����ϵͳ��ж��apk����
	 */
	private void UninstallApk() {
		String packageName = DeskTopGridViewBaseAdapter.string_app_info;
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