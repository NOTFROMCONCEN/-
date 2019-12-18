package com.example.dklauncherdemo;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.dklauncherdemo.toast.DiyToast;

/**
 * @author Administrator
 * @year 2019
 * @Todo TODO 桌面主页
 * @package_name com.example.dklauncherdemo
 * @project_name DKLauncherDemo
 * @file_name MainActivity.java
 * @我的博客 https://naiyouhuameitang.club/
 */
public class MainActivity extends Activity {
	private PopupWindow mPopupWindow;
	private TextView batterLevel;
	private BroadcastReceiver batteryLevelRcvr;
	private IntentFilter batteryLevelFilter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		monitorBatteryState();
	}

	private void initView() {
		// TODO Auto-generated method stub
		batterLevel = (TextView) findViewById(R.id.tv_batterLevel);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			DiyToast.showToast(getApplicationContext(), "你已经在桌面了");
			return false;
		}
		if (keyCode == KeyEvent.KEYCODE_HOME) {
			DiyToast.showToast(getApplicationContext(), "你已经在桌面了");
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(batteryLevelRcvr);
	}

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
					switch (status) {
					case BatteryManager.BATTERY_STATUS_UNKNOWN:
						sb.append("未找到电池.");
						break;
					case BatteryManager.BATTERY_STATUS_CHARGING:
						if (level <= 33)
							sb.append("充电中" + "\n" + "电池电量低" + "\n" + "["
									+ level + "]");
						else if (level <= 84)
							sb.append(" 充电中." + "\n" + "[" + level + "]");
						else
							sb.append("即将充满." + "\n" + "[" + level + "]");
						break;
					case BatteryManager.BATTERY_STATUS_DISCHARGING:
					case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
						if (level == 0)
							sb.append("急需充电.");
						else if (level > 0 && level <= 33)
							sb.append("低电量." + "\n" + "[" + level + "]");
						else
							sb.append("当前电量." + "\n" + "[" + level + "]");
						break;
					case BatteryManager.BATTERY_STATUS_FULL:
						sb.append(" is fully charged.");
						break;
					default:
						sb.append("'s battery is indescribable!");
						break;
					}
				}
				sb.append(' ');
				batterLevel.setText(sb.toString());
			}
		};
		batteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		registerReceiver(batteryLevelRcvr, batteryLevelFilter);
	}
}