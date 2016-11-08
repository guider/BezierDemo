package com.guider.bezierdemo.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.BounceInterpolator;

/**
 * Created by apple on 16/11/8.
 */

public class 动画曲线BezierView extends View implements View.OnClickListener {
    private Paint textPaint, linePaint, bezierPaint;
    private Path path;
    private Point startPoint, endPoint, cPoint1, cPoint2;

    public 动画曲线BezierView(Context context) {
        this(context, null);
    }

    public 动画曲线BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public 动画曲线BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(20);
        textPaint.setStyle(Paint.Style.STROKE);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(5);
        linePaint.setStyle(Paint.Style.STROKE);

        bezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bezierPaint.setStyle(Paint.Style.STROKE);
        bezierPaint.setStrokeWidth(6);

        this.setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startPoint = new Point(w / 6, h / 2);
        endPoint = new Point(w / 6 * 5, h / 2);
        cPoint1 = new Point(w / 6, h / 2);
        cPoint2 = new Point(w / 6 * 5, h / 2);

        path = new Path();
        path.moveTo(startPoint.x, startPoint.y);
        path.cubicTo(cPoint1.x, cPoint1.y, cPoint2.x, cPoint2.y, endPoint.x, endPoint.y);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawBezier(canvas);
        drawOther(canvas);
        canvas.restore();

    }

    private void drawOther(Canvas canvas) {
        drawLine(startPoint, cPoint1, canvas);
        drawLine(cPoint1, cPoint2, canvas);
        drawLine(cPoint2, endPoint, canvas);
    }

    private void drawLine(Point startPoint, Point cPoint1, Canvas canvas) {
        canvas.drawLine(startPoint.x, startPoint.y, cPoint1.x, cPoint1.y, linePaint);
    }

    private void drawBezier(Canvas canvas) {
        canvas.drawPath(path, bezierPaint);
    }


    @Override
    public void onClick(View v) {
        final ValueAnimator anim = ValueAnimator.ofFloat(startPoint.y, this.getY());
        anim.setDuration(1000);
        anim.setInterpolator(new BounceInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                cPoint1.y = (int) (float) animation.getAnimatedValue();
                cPoint2.y = (int) (float) animation.getAnimatedValue();
                path.reset();
                path.moveTo(startPoint.x, startPoint.y);
                path.cubicTo(cPoint1.x, cPoint1.y, cPoint2.x, cPoint2.y, endPoint.x, endPoint.y);

                invalidate();
            }
        });
        anim.start();
    }
}
