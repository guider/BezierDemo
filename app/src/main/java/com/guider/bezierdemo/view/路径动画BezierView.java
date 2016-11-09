package com.guider.bezierdemo.view;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.guider.bezierdemo.util.BezierUtil;

/**
 * Created by apple on 16/11/9.
 */

public class 路径动画BezierView extends View implements View.OnClickListener {
    private Paint potPaint, linePaint;
    private PointF startPoint, endPoint, currentPoint, cPoint;
    private Path path;

    public 路径动画BezierView(Context context) {
        this(context, null);
    }

    public 路径动画BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public 路径动画BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path = new Path();


        potPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        potPaint.setStrokeWidth(6);
        potPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(4);
        linePaint.setStyle(Paint.Style.STROKE);
        setOnClickListener(this);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawOther(canvas);
        drawBezier(canvas);
        canvas.restore();


    }

    private void drawOther(Canvas canvas) {
        canvas.drawPath(path, linePaint);
        canvas.drawCircle(startPoint.x, startPoint.y, 20, potPaint);
        canvas.drawCircle(endPoint.x, endPoint.y, 20, potPaint);
    }

    private void drawBezier(Canvas canvas) {
        canvas.drawCircle(currentPoint.x, currentPoint.y, 20, potPaint);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startPoint = new PointF(w / 4, h / 4);
        endPoint = new PointF(w * 3 / 4, h * 3 / 4);
        cPoint = new PointF(w * 3 / 4 - 100, h / 4 + 100);
        currentPoint = startPoint;
        path.moveTo(startPoint.x, startPoint.y);
        path.quadTo(cPoint.x, cPoint.y, endPoint.x, endPoint.y);
    }

    @Override
    public void onClick(View v) {
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierEvaluator(), startPoint, endPoint);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(0);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint = (PointF) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    class BezierEvaluator implements TypeEvaluator<PointF> {
        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            return BezierUtil.CalculateBezierPointForQuadratic(fraction, startPoint, cPoint, endPoint);
        }
    }
}
