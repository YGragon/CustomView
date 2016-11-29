package com.dongxi.customerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SeekBar;

import com.dongxi.customerview.view.CircleProgressView;
import com.dongxi.customerview.view.TopBar;

public class MainActivity extends AppCompatActivity {

    private TopBar mTopbar;
    private CircleProgressView mCircleProgressView;
    private SeekBar mSeekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 获得我们创建的topbar
        mTopbar = (TopBar) findViewById(R.id.topBar);
        // 为topbar注册监听事件，传入定义的接口
        // 并以匿名类的方式实现接口内的方法
        mTopbar.setOnTopbarClickListener(
                new TopBar.topBarClickListener() {

                    @Override
                    public void rightClick() {
                        startActivity(new Intent(MainActivity.this, ScrollerViewActivity.class));
                    }

                    @Override
                    public void leftClick() {
                        ToastUtils.showShort(MainActivity.this,"Toast..left..");
                    }
                });
        // 控制topbar上组件的状态
        mTopbar.setButtomVisable(0, true);
        mTopbar.setButtomVisable(1, true);
//        mTopbar.setButtomVisable(1, false);

        //圆形进度条
        mCircleProgressView = (CircleProgressView) findViewById(R.id.circleProgressView);
        mCircleProgressView.setSweepValue(50);//设置不同的弧度，注意不是角度.

    }
}
