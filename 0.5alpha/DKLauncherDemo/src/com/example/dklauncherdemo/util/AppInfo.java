package com.example.dklauncherdemo.util;

import android.content.Intent;
import android.graphics.drawable.Drawable;

public class AppInfo {
	private String packageName; // ����
	private Drawable ico; // ͼ��
	private String Name; // Ӧ�ñ�ǩ
	private Intent intent; // ����Ӧ�ó����Intent
							// ��һ����ActionΪMain��CategoryΪLancher��Activity

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
