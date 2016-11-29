package com.dongxi.customerview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Shader;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * 闪烁的TextView
 * 实现原理：利用Paint对象的Shader渲染器，通过蛇医一个不断变化的LinearGradient
 *          并使用带有该属性的Paint对象来绘制要显示的文本
 */

public class BlinkTextView extends TextView {

    private TextPaint mPaint;
    private LinearGradient mLinearGradient;
    private Matrix mMatrix;
    private int mMeasuredWidth = 0;
    private int mTranslate = 0;

    public BlinkTextView(Context context) {
        super(context);
    }

    public BlinkTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BlinkTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 对象的初始化操作，并根据View的宽带设置一个LinearGradient渐变渲染器
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (mMeasuredWidth == 0){
            mMeasuredWidth = getMeasuredWidth();
            if (mMeasuredWidth > 0){
                mPaint = getPaint();    //获取当前绘制的TextView的Paint对象
                mLinearGradient = new LinearGradient(0, 0, mMeasuredWidth, 0,
                        new int[]{Color.BLUE, 0xffffffff, Color.BLUE},
                        null, Shader.TileMode.CLAMP);
                mPaint.setShader(mLinearGradient);  //设置原生TextView没有的LinearGradent属性
                mMatrix = new Matrix();
            }
        }
    }

    /**
     * 通过矩阵方式来不断平移渐变效果
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mMatrix != null){
            mTranslate += mMeasuredWidth / 5;
            if (mTranslate >2*mMeasuredWidth){
                mTranslate = -mMeasuredWidth ;
            }
            mMatrix.setTranslate( mTranslate, 0 );
            mLinearGradient.setLocalMatrix(mMatrix);
            postInvalidateDelayed(100); //无效后延时100毫秒后出现
        }
    }
}
