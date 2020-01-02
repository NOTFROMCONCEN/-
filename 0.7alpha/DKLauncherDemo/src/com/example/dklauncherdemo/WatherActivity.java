package com.example.dklauncherdemo;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.example.dklauncherdemo.mysql.MyDataBaseHelper;
import com.example.dklauncherdemo.toast.DiyToast;

/**
 * @author Administrator
 * @year 2019
 * @Todo TODO 设置天气
 * @package_name com.example.dklauncherdemo
 * @project_name 桌面
 * @file_name WatherActivity.java
 * @我的博客 https://naiyouhuameitang.club/
 */
public class WatherActivity extends Activity {
	private Button btn_wather_con;
	private EditText et_city_get;
	private MyDataBaseHelper dbHelper;
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_wather);
		initView();

		btn_wather_con.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (et_city_get.getText().toString().isEmpty()) {
					DiyToast.showToast(getApplicationContext(), "请输入你居住的城市");
				} else {
					db.execSQL("update wather_city set city = ? ",
							new String[] { et_city_get.getText().toString() });// 更新数据库
					finish();
				}
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		btn_wather_con = (Button) findViewById(R.id.btn_wather_con);
		et_city_get = (EditText) findViewById(R.id.et_city_get);
		dbHelper = new MyDataBaseHelper(getApplicationContext(), "info.db",
				null, 2);
		db = dbHelper.getWritableDatabase();
	}
}
