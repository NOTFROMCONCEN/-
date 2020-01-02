package com.example.dklauncherdemo.mysql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author Administrator
 * @year 2019
 * @Todo TODO ���ݿ⣨Ҳ�ʹ���ǳƣ�
 * @package_name com.example.dklauncherdemo.mysql
 * @project_name DKLuancherDemo
 * @file_name MyDataBaseHelper.java
 * @�ҵĲ��� https://naiyouhuameitang.club/
 */
public class MyDataBaseHelper extends SQLiteOpenHelper {

	public MyDataBaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table name(_id integer primary key autoincrement,username text)");
		db.execSQL("insert into name(username)values(?)", new String[] { "" });
		db.execSQL("create table wather_city(_id integer primary key autoincrement,city text)");
		db.execSQL("insert into wather_city(city)values(?)",
				new String[] { "����" });
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}

}
