package com.dongxi.customerview;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.dongxi.customerview.base.BaseActivity;
import com.dongxi.customerview.present.ActivityCollector;

public class ScrollerViewActivity extends BaseActivity {

    private ImageView mImageView;

    private static final String TAG = "ScrollerViewActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroller_view);

        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                view.getParent().requestDisallowInterceptTouchEvent(true);
                Log.w(TAG,"我是ScrollerViewActivity") ;
                ActivityCollector.finishAll();
                return false;
            }
//            @Override
//            public void onClick(View view) {
//                Log.w(TAG,"我是ScrollerViewActivity") ;
//                ActivityCollector.finishAll();
//            }
        });
    }


}
