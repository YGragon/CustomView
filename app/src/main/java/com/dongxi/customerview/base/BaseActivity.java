package com.dongxi.customerview.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.dongxi.customerview.present.ActivityCollector;

/**
 * Created by Administrator on 2016/12/5.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
