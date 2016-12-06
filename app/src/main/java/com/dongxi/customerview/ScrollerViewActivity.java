package com.dongxi.customerview;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.dongxi.customerview.base.BaseActivity;
import com.dongxi.customerview.present.ActivityCollector;

public class ScrollerViewActivity extends BaseActivity {

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_view);

        mImageView = (ImageView) findViewById(R.id.imageView);

        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //屏蔽父控件拦截onTouch事件
                //view.getParent().requestDisallowInterceptTouchEvent(true);
                ActivityCollector.finishAll();
            }
        });

    }
}
