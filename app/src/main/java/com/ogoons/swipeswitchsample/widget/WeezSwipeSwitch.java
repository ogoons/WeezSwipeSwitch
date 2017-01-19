package com.ogoons.swipeswitchsample.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.v4.view.GestureDetectorCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import com.ogoons.swipeswitchsample.R;

/**
 * Created by ogoons on 2015-12-18.
 */
public class WeezSwipeSwitch extends LinearLayout implements GestureDetector.OnGestureListener {
    final String TAG = getClass().getSimpleName();
    private GestureDetectorCompat mGestureDetectorCompat;
    private OnSwipeSwitchListener mListener;

    private int mScrollX = 0;
    private int mAlpha = 100; // default
    private int mColor = 150; // default
    private int mStartWidth = 0;
    private int mRoundCorner = 0;
    private int mBackColor = Color.TRANSPARENT; // default

    public WeezSwipeSwitch(Context context) {
        super(context);
        initialize(context);
    }

    public WeezSwipeSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_swipe, this, true);

        mGestureDetectorCompat = new GestureDetectorCompat(context, this);
        super.setBackgroundColor(Color.TRANSPARENT);
        setStartSwipePosition(25); // default left to 10% area
    }

    /**
     * swipe overay color
     * @param alpha
     * @param color
     */
    public void setSwipeOverlayColor(int alpha, int color) {
        mAlpha = alpha;
        mColor = color;
    }

    /**
     * 사용자가 스와이프를 시작을 허용하는 위치의 비율 설정
     * @param ratio
     */
    public void setStartSwipePosition(float ratio) {
        if (100 < ratio)
            ratio = 100;
        float f = ratio / 100;
        measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED);
        mStartWidth = (int) (getMeasuredWidth() * f);
    }

    /**
     * 사용자가 스와이프를 끝까지 행한 경우(On) 발생되는 callback 설정
     * @param listener
     */
    public void setOnSwipeSwitchListener(OnSwipeSwitchListener listener) {
        mListener = listener;
    }

    /**
     * set background color
     * @param color
     */
    @Override
    public void setBackgroundColor(int color) {
        mBackColor = color;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 배경 표현
        if (0 == (mBackColor & Color.TRANSPARENT)) {
            Paint backPaint = new Paint();
            backPaint.setColor(mBackColor);

            int left = 0;
            int top = 0;
            int right = canvas.getWidth();
            int bottom = canvas.getHeight();
            int radius = mRoundCorner;

            Path backPath = new Path();
            backPath.moveTo(left, top);
            backPath.lineTo(right, top);
            backPath.lineTo(right, bottom - radius);
            backPath.quadTo(right, bottom, right - radius, bottom);
            backPath.lineTo(left + radius, bottom);
            backPath.quadTo(left, bottom, left, bottom - radius);
            backPath.lineTo(left, top);
            canvas.drawPath(backPath, backPaint);
        }


        // 드래그 이미지 표현
        if (0 < mScrollX) {
            Paint paint = new Paint();
            paint.setColor(mColor);
            paint.setAlpha(mAlpha);
            paint.setAntiAlias(true);

            int left = 0;
            int top = 0;
            int right = mScrollX;
            int bottom = canvas.getHeight();
            int radius = mRoundCorner;

            Path path = new Path();
            path.moveTo(left, top);
            path.lineTo(right, top);
            path.lineTo(right, bottom - radius);
            path.quadTo(right, bottom, right - radius, bottom);
            path.lineTo(left + radius, bottom);
            path.quadTo(left, bottom, left, bottom - radius);
            path.lineTo(left, top);
            canvas.drawPath(path, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (MotionEvent.ACTION_UP == event.getAction()) {
            int minWidth = (getWidth() * 95) / 100;

            if (mScrollX > minWidth && null != mListener)
                mListener.onSwitchOn(); // 구현은 리스너를 설치한 클래스에서 한다.

            mScrollX = 0;
            invalidate();
        }

        return mGestureDetectorCompat.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        mScrollX = 0;
        invalidate();
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (mStartWidth < (int) (e1.getX()))
            return false;

        int right = (int) e2.getX();
        if (getWidth() >= right)
            mScrollX = right;

        invalidate();
        return true;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    public interface OnSwipeSwitchListener {
        void onSwitchOn(); // on switch callback
    }
}

