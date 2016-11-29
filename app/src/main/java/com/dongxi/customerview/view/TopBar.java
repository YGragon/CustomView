package com.dongxi.customerview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dongxi.customerview.R;

/**
 * 标题栏的自定义
 */

public class TopBar extends RelativeLayout{

    private int mLeftTextColor;
    private Drawable mLeftBackground;
    private String mLeftText;
    private int mRightTextColor;
    private Drawable mRighBackground;
    private String mRighText;
    private float mTitleTextSize;
    private int mTitleTextColor;
    private String mTitle;
    private Button mLeftButtom;
    private Button mRightButtom;
    private TextView mTitleView;
    private LayoutParams mLeftParams;
    private LayoutParams mRightParams;
    private LayoutParams mTitleParams;
    // 映射传入的接口对象
    private topBarClickListener mListener;

    public TopBar(Context context) {
        super(context);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        // 设置topbar的背景
        setBackgroundColor(0xFFF59563);
        // 通过这个方法，将你在atts.xml中定义的declare-styleable的所有属性的值存储到TypedArray中
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        //Left属性
        mLeftTextColor = typedArray.getColor(R.styleable.TopBar_leftTextColor, 0);
        mLeftBackground = typedArray.getDrawable(R.styleable.TopBar_leftBackground);
        mLeftText = typedArray.getString(R.styleable.TopBar_leftText);
        //Right属性
        mRightTextColor = typedArray.getColor(R.styleable.TopBar_rightTextColor, 0);
        mRighBackground = typedArray.getDrawable(R.styleable.TopBar_rightBackground);
        mRighText = typedArray.getString(R.styleable.TopBar_rightText);
        //center属性
        mTitleTextSize = typedArray.getDimension(R.styleable.TopBar_titleTextSize, 10);
        mTitleTextColor = typedArray.getColor(R.styleable.TopBar_titleTextColor, 0);
        mTitle = typedArray.getString(R.styleable.TopBar_title);

        // 获取完TypedArray的值后，一般要调用
        // recyle方法来避免重新创建的时候的错误
        typedArray.recycle();

        //创建3个控件
        mLeftButtom = new Button(context);
        mRightButtom = new Button(context);
        mTitleView = new TextView(context);

        // 为创建的组件元素赋值,值就来源于我们在引用的xml文件中给对应属性的赋值
        // Left
        mLeftButtom.setTextColor(mLeftTextColor);
        mLeftButtom.setText(mLeftText);
        mLeftButtom.setBackground(mLeftBackground);
        //Right
        mRightButtom.setTextColor(mRightTextColor);
        mRightButtom.setText(mRighText);
        mRightButtom.setBackground(mRighBackground);
        //Title
        mTitleView.setText(mTitle);
        mTitleView.setTextColor(mTitleTextColor);
        mTitleView.setTextSize(mTitleTextSize);
        mTitleView.setGravity(Gravity.CENTER);

        // 为组件元素设置相应的布局元素
        //Left
        mLeftParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        mLeftParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT,TRUE);
        addView(mLeftButtom,mLeftParams);

        //Right
        mRightParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        mRightParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,TRUE);
        addView(mRightButtom,mRightParams);

        //Title
        mTitleParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.MATCH_PARENT);
        mTitleParams.addRule(RelativeLayout.CENTER_IN_PARENT,TRUE);
        addView(mTitleView,mTitleParams);

        // 按钮的点击事件，不需要具体的实现，
        // 只需调用接口的方法，回调的时候，会有具体的实现
        mLeftButtom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.leftClick();
            }
        });
        mRightButtom.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.rightClick();
            }
        });
    }

    /**
     * 设置按钮的显示与否
     * @param id    0代表Left，1代表Right
     * @param flag  true显示，false不显示
     */
    public void setButtomVisable(int id,boolean flag){
        if (flag){
            if (id == 0){
                mLeftButtom.setVisibility(VISIBLE);
            }else {
                mRightButtom.setVisibility(VISIBLE);
            }
        }else {
            if (id == 0){
                mLeftButtom.setVisibility(GONE);
            }else {
                mRightButtom.setVisibility(GONE);
            }
        }
    }

    /**
     * 接口对象，实现回调机制，在回调方法中
     * 通过映射的接口对象调用接口中的方法
     * 而不用去考虑如何实现，具体的实现由调用者去创建
     */
    public interface topBarClickListener{
        // 左按钮点击事件
        void leftClick();
        // 右按钮点击事件
        void rightClick();
    }
    // 暴露一个方法给调用者来注册接口回调
    // 通过接口来获得回调者对接口方法的实现
    public void setOnTopbarClickListener(topBarClickListener mListener) {
        this.mListener = mListener;
    }
}
