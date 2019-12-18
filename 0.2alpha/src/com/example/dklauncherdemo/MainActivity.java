package com.example.dklauncherdemo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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

import com.example.dklauncherdemo.activitys.UnInstallActivity;
import com.example.dklauncherdemo.adapter.DeskTopGridViewBaseAdapter;
import com.example.dklauncherdemo.adapter.GetApps;
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
	private TextView batterLevel;
	private BroadcastReceiver batteryLevelRcvr;
	private IntentFilter batteryLevelFilter;
	private Handler handler;
	private Runnable runnable;
	private TextView tv_user_id;
	private TextView tv_now_time;
	private SharedPreferences sharedPreferences;
	private TextView tv_wifi_state;
	static GridView mGridView;
	static List<AppInfo> appInfos = new ArrayList<AppInfo>();
	public static String string_app_info;

	/**
	 * <!-- android:numColumns="auto_fit" --------列数设置为自动 --> <!--
	 * android:columnWidth="90dp"，----------每列的宽度，也就是Item的宽度 --> <!--
	 * android:stretchMode="columnWidth"------缩放与列宽大小同步 --> <!--
	 * android:verticalSpacing="10dp"----------垂直边距 --> <!--
	 * android:horizontalSpacing="10dp"-------水平边距 -->
	 * 
	 * @param savedInstanceState
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 无Title
		setContentView(R.layout.activity_main);
		initView();// 绑定控件
		new_time_Thread();// 启用更新时间进程
		monitorBatteryState();// 监听电池信息
		rember_name();// 记住昵称
		get_wifi_server();// 获取WIFI状态
		mGridView = (GridView) findViewById(R.id.gridView1);
		initAppList(this);
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

	public static void initAppList(Context context) {
		mGridView.setAdapter(null);
		appInfos = GetApps.GetAppList1(context);
		DeskTopGridViewBaseAdapter deskTopGridViewBaseAdapter = new DeskTopGridViewBaseAdapter(
				appInfos, context);
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
			System.out.println(wifiState);
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
		System.out.println("wifiInfo：" + wifiInfo.toString());
		System.out.println("SSID：" + wifiInfo.getSSID().toString());
		return wifiInfo.getSSID();
	}

	/**
	 * 读取昵称
	 */
	private void rember_name() {
		if (sharedPreferences != null) {
			tv_user_id.setText(sharedPreferences.getString("name", null)
					+ "的多看电子书");
		} else {
			tv_user_id.setText("我的多看电子书");
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
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
				tv_now_time.setText(simpleDateFormat
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
		batterLevel = (TextView) findViewById(R.id.tv_batterLevel);
		tv_now_time = (TextView) findViewById(R.id.tv_now_time);
		tv_user_id = (TextView) findViewById(R.id.tv_user_id);
		tv_wifi_state = (TextView) findViewById(R.id.tv_wifi_state);
		tv_user_id.setOnClickListener(this);
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
	 * 充电状态显示（可能比较鸡肋？）
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
						sb.append(String.valueOf(level) + "✚");
					} else {
						sb.append(String.valueOf(level));
					}
					System.out.println(level);
				}
				sb.append(' ');
				batterLevel.setText(sb.toString());
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
							sharedPreferences = getSharedPreferences("rember",
									MODE_WORLD_WRITEABLE);
							if (et_name_get.getText().toString().isEmpty()) {
								sharedPreferences.edit()
										.putString("name", "我的").commit();
							} else {
								sharedPreferences
										.edit()
										.putString(
												"name",
												et_name_get.getText()
														.toString()).commit();
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
}