package com.dongxi.customerview.present;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动管理器，可以随时随地退出活动
 */

public class ActivityCollector {
    public static List<Activity> activities = new ArrayList<>() ;

    public static void addActivity(Activity activity){
        activities.add(activity)  ;
    }
    public static void removeActivity(Activity activity){
        activities.remove(activity) ;
    }

    public static void finishAll(){
        for (Activity activity: activities) {
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
