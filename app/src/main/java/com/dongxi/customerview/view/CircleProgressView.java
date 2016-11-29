package com.dongxi.customerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * 圆形进度条
 */

public class CircleProgressView extends View {

    private int mMeasureWidth;
    private int mMeasureHight;
    private float mCircleXY;
    private float mRadius;
    private RectF mArcRectF;
    private Paint mCirclePaint;

    private float mSweepValue = 66;
    private float mSweepAngle;
    private Paint mArcPaint;
    private String mShowText;
    private float mShowTextSize;
    private Paint mTextPaint;

    public CircleProgressView(Context context) {
        super(context);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //将View的绘制长度设置为屏幕的宽度
        mMeasureWidth = MeasureSpec.getSize(widthMeasureSpec);
        mMeasureHight = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(mMeasureWidth,mMeasureHight);
        initView();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制中心圆
        canvas.drawCircle(mCircleXY,mCircleXY,mRadius,mCirclePaint);
        //绘制弧线
        canvas.drawArc(mArcRectF,270,mSweepAngle,false,mArcPaint);
        //绘制文本
        canvas.drawText(mShowText,0,mShowText.length(),mCircleXY,mCircleXY + (mShowTextSize / 4),mTextPaint);
    }

    private void initView() {
        float lenght = 0 ;
        if (mMeasureHight >= mMeasureWidth){
            lenght = mMeasureWidth ;
        }else{
            lenght = mMeasureHight ;
        }

        //中心圆的参数
        mCircleXY = lenght / 2;
        mRadius = (float) (lenght * 0.5 / 2);

        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);//设置画笔的锯齿效果
        mCirclePaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));

        //绘制椭圆的外接矩形
        mArcRectF = new RectF((float) (lenght * 0.1), (float) (lenght * 0.1),
                (float) (lenght * 0.9), (float) (lenght * 0.9));

        mSweepAngle = (mSweepValue / 100f) * 360f ;
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);//设置画笔的锯齿效果
        mArcPaint.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        mArcPaint.setStrokeWidth((float)(lenght * 0.1));//设置空心边框的宽度
        mArcPaint.setStyle(Paint.Style.STROKE);//画笔为实心

        //绘制中心文本
        mShowText = setShowText();
        mShowTextSize = setShowTextSize();
        mTextPaint = new Paint();
        mTextPaint.setTextSize(mShowTextSize);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    private float setShowTextSize() {
        this.invalidate();
        return 40;
    }

    private String setShowText() {
        this.invalidate();
        return "Android";
    }
    public void forceInvalidate() {
        this.invalidate();
    }

    public  void setSweepValue(float sweepValue){
        if (sweepValue != 0){
            mSweepValue = sweepValue ;
        }else {
            mSweepValue = 25 ;
        }
        this.invalidate();
    }
}
