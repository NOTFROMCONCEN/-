package com.example.dklauncherdemo.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dklauncherdemo.R;
import com.example.dklauncherdemo.UnInstallActivity;
import com.example.dklauncherdemo.util.AppInfo;

/**
 * @author Administrator
 * @year 2019
 * @Todo TODO APP信息的BaseAdapter
 * @package_name com.example.dklauncherdemo.adapter
 * @project_name DKLauncherDemo
 * @file_name DeskTopGridViewBaseAdapter.java
 * @我的博客 https://naiyouhuameitang.club/
 */
public class DeskTopGridViewBaseAdapter extends BaseAdapter {

	Context context;
	List<AppInfo> appInfos = new ArrayList<AppInfo>();
	public static String string_app_info = "";

	public DeskTopGridViewBaseAdapter(List<AppInfo> appInfos, Context context) {
		this.appInfos = appInfos;
		this.context = context;
	}

	@Override
	public int getCount() {
		return appInfos.size();
	}

	@Override
	public Object getItem(int position) {
		return appInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.desktop_gridview_item, null);
			holder = new Holder();
			holder.del = (ImageView) convertView.findViewById(R.id.del);
			holder.ico = (ImageView) convertView.findViewById(R.id.iv);
			holder.Name = (TextView) convertView.findViewById(R.id.tv);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.ico.setImageDrawable(appInfos.get(position).getIco());
		holder.Name.setText(appInfos.get(position).getName());
		holder.del.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				string_app_info = appInfos.get(position).getPackageName();
				Intent intent = new Intent(context, UnInstallActivity.class);
				context.startActivity(intent);
			}
		});
		return convertView;
	}

	static class Holder {
		ImageView ico;
		ImageView del;
		TextView Name;
	}
}
