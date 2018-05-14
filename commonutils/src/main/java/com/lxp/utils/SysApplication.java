package com.lxp.utils;

import android.app.Activity;

import java.util.LinkedList;
import java.util.List;

/**
 * 一次退出多个页面
 */
public class SysApplication {
	private List<Activity> mList = new LinkedList<Activity>();

	private static SysApplication instance;

	public synchronized static SysApplication getInstance() {
		if (null == instance) {
			instance = new SysApplication();
		}
		return instance;
	}

	// add Activity
	public void addActivity(Activity activity) {
		mList.add(activity);
	}

	public void exit() {
		try {
			for (Activity activity : mList) {
				if (activity != null)
					activity.finish();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
//			System.exit(0);
		}
	}

}
