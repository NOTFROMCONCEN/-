package com.example.dklauncherdemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.example.dklauncherdemo.adapter.DeskTopGridViewBaseAdapter;
import com.example.dklauncherdemo.adapter.GetApps;
import com.example.dklauncherdemo.mysql.MyDataBaseHelper;
import com.example.dklauncherdemo.util.AppInfo;

/**
 * @author Administrator
 * @year 2019
 * @Todo TODO 桌面主页
 * @package_name com.example.dklauncherdemo
 * @project_name DKLauncherDemo
 * @file_name MainActivity.java
 * @我的博客 https://naiyouhuameitang.club/
 */
public class MainActivity extends Activity implements OnClickListener {
	private BroadcastReceiver batteryLevelRcvr;
	private Handler handler;
	private Runnable runnable;
	private IntentFilter batteryLevelFilter;
	private TextView tv_wifi_state, tv_user_id, tv_time_hour, tv_time_min,
			tv_desk_top_time;
	static GridView mGridView;
	static List<AppInfo> appInfos = new ArrayList<AppInfo>();
	public static String string_app_info;
	private MyDataBaseHelper dbHelper;
	private SQLiteDatabase db;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
		setContentView(R.layout.activity_main);
		System.out.println("你好，世界");
		initView();// 绑定控件
		new_time_Thread();// 启用更新时间进程
		rember_name();// 记住昵称
		get_wifi_server();// 获取WIFI状态
		initAppList(this);// 获取应用列表
		monitorBatteryState();// 监听电池信息
		getScreenBrightness(MainActivity.this);
		// 当点击GridView时，获取ID和应用包名并启动
		mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// Intent intent=appInfos.get(position).getIntent();
				// startActivity(intent);
				Intent intent = getPackageManager().getLaunchIntentForPackage(
						appInfos.get(position).getPackageName());
				if (intent != null) {
					intent.putExtra("type", "110");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					startActivity(intent);
				}
			}
		});
		// 当长按GridView时，获取ID和应用包名并弹出对话框
		mGridView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				// TODO Auto-generated method stub
				string_app_info = appInfos.get(position).getPackageName();
				startActivity(new Intent(getApplicationContext(),
						UnInstallActivity.class));
				return true;
			}
		});
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("被启动");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		System.out.println("被重启");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("被重调");
		get_wifi_server();// 获取WIFI状态
		initAppList(this);// 获取应用列表
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("被暂停");
	}

	/**
	 * +获取应用列表
	 * 
	 * @param context
	 */
	public static void initAppList(Context context) {
		DeskTopGridViewBaseAdapter deskTopGridViewBaseAdapter = null;
		appInfos = GetApps.GetAppList1(context);
		deskTopGridViewBaseAdapter = new DeskTopGridViewBaseAdapter(appInfos,
				context);
		mGridView.setAdapter(deskTopGridViewBaseAdapter);
	}

	/**
	 * 获取WIFI状态
	 * 
	 * 原作者：https://blog.csdn.net/F_hawk189/article/details/89277570
	 * 
	 * WIFI有四种状态，getWifiState()会返回四种状态码，分别是：
	 * 
	 * 1. wifiManager.WIFI_STATE_DISABLED (1)
	 * 
	 * 2. wifiManager..WIFI_STATE_ENABLED (3)
	 * 
	 * 3. wifiManager..WIFI_STATE_DISABLING (0)
	 * 
	 * 4 wifiManager..WIFI_STATE_ENABLING  (2)
	 */
	private void get_wifi_server() {
		WifiManager wifiManager = (WifiManager) this
				.getSystemService(Context.WIFI_SERVICE);
		if (wifiManager != null) {
			int wifiState = wifiManager.getWifiState();
			if (wifiState == 3) {
				tv_wifi_state.setText(getConnectWifiSsid());
			} else {
				tv_wifi_state.setText("未连接");
			}
		}
	}

	/**
	 * 获取连接的wifi名称
	 * 
	 * @return
	 */
	private String getConnectWifiSsid() {
		WifiManager wifiManager = (WifiManager) getSystemService(WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		return wifiInfo.getSSID();
	}

	/**
	 * 读取昵称
	 * 
	 * SQLite
	 */
	private void rember_name() {
		Cursor cursor = db.rawQuery("select * from name", null);
		if (cursor.getCount() != 0) {
			cursor.moveToFirst();
			tv_user_id.setText(cursor.getString(cursor
					.getColumnIndex("username")) + "多看电纸书");
		}
	}

	/**
	 * 更新时间
	 */
	private void new_time_Thread() {
		handler = new Handler() {
			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				get_wifi_server();
				SimpleDateFormat simpleDateFormat_hour = new SimpleDateFormat(
						"HH");
				SimpleDateFormat simpleDateFormat_min = new SimpleDateFormat(
						"mm");
				SimpleDateFormat simpleDateFormat_year = new SimpleDateFormat(
						"yyyy年MM月dd日");
				tv_desk_top_time.setText(simpleDateFormat_year
						.format(new java.util.Date()));
				tv_time_hour.setText(simpleDateFormat_hour
						.format(new java.util.Date()));
				tv_time_min.setText(simpleDateFormat_min
						.format(new java.util.Date()));
				handler.postDelayed(runnable, 500);
			}
		};
		runnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Message msg = handler.obtainMessage();
				handler.sendMessage(msg);
			}
		};
		handler.post(runnable);
	}

	/**
	 * 绑定控件
	 */
	private void initView() {
		// TODO Auto-generated method stub
		mGridView = (GridView) findViewById(R.id.gridView1);
		tv_time_hour = (TextView) findViewById(R.id.tv_time_hour);
		tv_time_min = (TextView) findViewById(R.id.tv_time_min);
		tv_user_id = (TextView) findViewById(R.id.tv_user_id);
		tv_wifi_state = (TextView) findViewById(R.id.tv_wifi_state);
		tv_desk_top_time = (TextView) findViewById(R.id.tv_desk_top_time);
		tv_user_id.setOnClickListener(this);
		dbHelper = new MyDataBaseHelper(getApplicationContext(), "info.db",
				null, 2);
		db = dbHelper.getWritableDatabase();
	}

	/**
	 * 拦截返回键、Home键
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			return false;
		}
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * Activity被销毁的同时销毁广播
	 */
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(batteryLevelRcvr);
	}

	/**
	 * 充电状态显示
	 * 
	 * Code Copy from http://blog.sina.com.cn/s/blog_c79c5e3c0102uyun.html
	 */
	private void monitorBatteryState() {
		batteryLevelRcvr = new BroadcastReceiver() {

			public void onReceive(Context context, Intent intent) {
				StringBuilder sb = new StringBuilder();
				int rawlevel = intent.getIntExtra("level", -1);
				int scale = intent.getIntExtra("scale", -1);
				int status = intent.getIntExtra("status", -1);
				int health = intent.getIntExtra("health", -1);
				int level = -1; // percentage, or -1 for unknown
				if (rawlevel >= 0 && scale > 0) {
					level = (rawlevel * 100) / scale;
				}
				if (BatteryManager.BATTERY_HEALTH_OVERHEAT == health) {
					sb.append("'s battery feels very hot!");
				} else {
					if (status == 5) {
						sb.append(String.valueOf(level) + "%  " + "+");
					} else {
						sb.append(String.valueOf(level) + "%  ");
					}
				}
				sb.append(' ');
			}
		};
		batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(batteryLevelRcvr, batteryLevelFilter);
	}

	/**
	 * 点击事件监听
	 */
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_user_id:
			AlertDialog.Builder builder = new AlertDialog.Builder(
					MainActivity.this);
			View view = LayoutInflater.from(getApplicationContext()).inflate(
					R.layout.dialog_name_show, null, false);
			builder.setView(view);
			final EditText et_name_get = (EditText) view
					.findViewById(R.id.et_title_name);
			builder.setTitle("请输入你的昵称");
			builder.setPositiveButton("确定",
					new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							if (et_name_get.getText().toString().isEmpty()) {
								db.execSQL("update name set username = ?",
										new String[] { "我" });
							} else {
								db.execSQL("update name set username = ?",
										new String[] { et_name_get.getText()
												.toString() });
							}
							rember_name();
						}
					});
			builder.setNegativeButton("取消", null);
			builder.show();
			break;
		default:
			break;
		}
	}

	public static int getScreenBrightness(Context context) {
		int value = 0;
		ContentResolver cr = context.getContentResolver();
		try {
			value = Settings.System.getInt(cr,
					Settings.System.SCREEN_BRIGHTNESS);
		} catch (SettingNotFoundException e) {
			Log.e("TAG", e.toString());
		}
		System.out.println(value);
		return value;
	}

}