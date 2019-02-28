package com.Store.www.utils;

import android.app.Activity;

import java.util.Stack;

/**
 * Activity的管理类
 */

public class ActivityCollector {


    public static Stack<Activity> activities;
    public static void addActivity(Activity activity) {
        if(activities==null){
            activities=new Stack<Activity>();
        }
        activities.add(activity);
    }
    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }
    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        activities.clear();
    }

    /**
     * 获取栈顶 Activity
     *
     * @return
     */
    public static Activity getTopActivity() {
        if (!activities.isEmpty()) {
            return activities.get(activities.size() - 1);
        } else {
            return null;
        }
    }

    /**
     * 结束指定的Activity
     */
    public static void finishActivity(Activity activity){
        if(activity!=null){
            activities.remove(activity);
            activity.finish();
            activity=null;
        }
    }


}
