package com.example.dklauncherdemo.util;

import android.content.Intent;
import android.graphics.drawable.Drawable;

/**
 * APP信息
 * 
 * @author Administrator
 * 
 */
public class AppInfo {
	private String packageName;// 包名
	private Drawable ico;// 图标
	private String Name;// 名称
	private Intent intent;// 连接

	public Intent getIntent() {
		return intent;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	public Drawable getIco() {
		return ico;
	}

	public void setIco(Drawable ico) {
		this.ico = ico;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
}
