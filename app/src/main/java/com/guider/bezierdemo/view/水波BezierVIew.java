package com.guider.bezierdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by apple on 16/11/8.
 */

public class 水波BezierVIew extends View implements View.OnClickListener {
    private Paint paint;
    private Path path;
    private int waveWidth=1000;
    private int waveCount =3;
    private float centerY;
    private int OFFSET;
    public 水波BezierVIew(Context context) {
        this(context,null);
    }

    public 水波BezierVIew(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public 水波BezierVIew(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(6);
        path = new Path();
        setOnClickListener(this);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerY = h/2;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(-waveWidth + OFFSET, centerY);
        for (int i = 0; i < waveCount; i++) {
            // + (i * mWaveLength)
            // + mOffset
            path.quadTo((-waveWidth * 3 / 4) + (i * waveWidth) + OFFSET, centerY + 60, (-waveWidth / 2) + (i * waveWidth) + OFFSET, centerY);
            path.quadTo((-waveWidth / 4) + (i * waveWidth) + OFFSET, centerY - 60, i * waveWidth + OFFSET, centerY);
        }
        path.lineTo(getWidth(), getHeight());
        path.lineTo(0, getHeight());
        path.close();
        canvas.drawPath(path, paint);


    }

    @Override
    public void onClick(View v) {
        ValueAnimator an = ValueAnimator.ofInt(0, 1000);
        an.setRepeatCount(ValueAnimator.INFINITE);
        an.setDuration(1000);
        an.setInterpolator(new LinearInterpolator());
        an.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                OFFSET = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        an.start();
    }
}
