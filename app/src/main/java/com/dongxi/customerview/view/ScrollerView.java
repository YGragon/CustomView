package com.dongxi.customerview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * 具有黏性的ScrollerView
 */

public class ScrollerView extends ViewGroup {

    private int mScreenHeight;
    private Scroller mScroller;
    private int mLastY;
    private float mStart;
    private boolean isMove = false ;

    public ScrollerView(Context context) {
        super(context);
        initView(context);
    }



    public ScrollerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public ScrollerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        WindowManager mWindowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics mDisplayMetrics = new DisplayMetrics();

        //获取到Activity的实际屏幕信息
        mWindowManager.getDefaultDisplay().getMetrics(mDisplayMetrics);

        //实际屏幕的高度
        mScreenHeight = mDisplayMetrics.heightPixels;
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();

        MarginLayoutParams mMarginLayoutParams = (MarginLayoutParams) getLayoutParams();

        //ViewGroup的宽度 = 屏幕的高度 * 子View的个数
        mMarginLayoutParams.height = mScreenHeight * childCount ;

        // 设置ViewGroup的高度
        setLayoutParams(mMarginLayoutParams);
        for (int j = 0 ; j < childCount; j++){
            View childAt = getChildAt(j);
            if (childAt.getVisibility() != GONE){
                //设置每个子View需要放置的位置,z主要通过修改top ,bottom两个属性的值，让子View依次排列下来
                childAt.layout(l, j * mScreenHeight,r,(j + 1) * mScreenHeight );
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i =0; i < childCount ;i++){
            View childAt = getChildAt(i);
            measureChild(childAt,widthMeasureSpec,heightMeasureSpec);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int y = (int) event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN://手指摁下
                mLastY = y;
                mStart = getScaleY();
                break;
            case MotionEvent.ACTION_MOVE://手指滑动,实现滚动
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                if (getScrollY() < 0){
                    dy = 0 ;
                }
                if (getScrollY() > getHeight() - mScreenHeight){
                    dy = 0 ;
                }
                scrollBy(0,dy);//手指滑动到手指摁下到dy长度的位置
                mLastY = y ;
                break;
            case MotionEvent.ACTION_UP://手指抬起，实现黏性
                int dScrollY = checkAlignment();
                if (dScrollY > 0){//向下滑动
                    if (dScrollY < mScreenHeight / 3){
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    }else{
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);
                    }
                }else {//向上滑动
                    if (-dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -dScrollY);
                    } else {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -mScreenHeight - dScrollY);
                    }
                }
                break;
        }
        postInvalidate();
        return true;

    }

    /**
     * 判断方向
     * @return
     */
    private int checkAlignment() {
        int mEnd = getScrollY();
        boolean isUp = ((mEnd - mStart) > 0 ) ? true:false ;
        int lastPrev = mEnd % mScreenHeight;
        int lastNext = mScreenHeight - lastPrev;
        if (isUp){
            //向上
            return lastPrev ;
        }else {
            return -lastNext;
        }
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()){
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }

    /**
     * 事件拦截的核心方法
     * false不拦截子View的点击事件，则子View可以响应相应的事件
     * true拦截，不让子View相应相应的事件
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN://手指摁下
                mLastY = y;
                mStart = getScaleY();
                isMove = false ;
                break;
            case MotionEvent.ACTION_MOVE://手指滑动,实现滚动
                if (!mScroller.isFinished()){
                    mScroller.abortAnimation();
                }
                int dy = mLastY - y;
                if (getScrollY() < 0){
                    dy = 0 ;
                }
                if (getScrollY() > getHeight() - mScreenHeight){
                    dy = 0 ;
                }
                scrollBy(0,dy);//手指滑动到手指摁下到dy长度的位置
                mLastY = y ;
                isMove = true ;//则点击图片没用
                break;
            case MotionEvent.ACTION_UP://手指抬起，实现黏性
                int dScrollY = checkAlignment();
                if (dScrollY > 0){//向下滑动
                    if (dScrollY < mScreenHeight / 3){
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    }else{
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);
                    }
                }else {//向上滑动
                    if (-dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -dScrollY);
                    } else {
                        mScroller.startScroll(
                                0, getScrollY(),
                                0, -mScreenHeight - dScrollY);
                    }
                }
                isMove = false ;
                break;
        }
        postInvalidate();

        return isMove;//则点击图片退出

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
