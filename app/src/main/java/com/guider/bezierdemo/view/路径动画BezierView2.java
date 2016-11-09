package com.guider.bezierdemo.view;

import android.animation.AnimatorSet;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Loader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;

import com.guider.bezierdemo.util.BezierUtil;

/**
 * Created by apple on 16/11/9.
 */

public class 路径动画BezierView2 extends View implements View.OnClickListener {

    Paint paint;
    Path path1, path2;
    PointF startPoint, endPonit, cPoint1, cPoint2, currentPoint1, currentPoint2;

    public 路径动画BezierView2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public 路径动画BezierView2(Context context) {
        this(context, null);
    }

    public 路径动画BezierView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(Color.BLUE);
        setOnClickListener(this);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        startPoint = new PointF(100, h / 2);
        endPonit = new PointF(w - 100, h / 2);
        cPoint1 = new PointF(w / 2, 100);
        cPoint2 = new PointF(w / 2, h - 100);

        path1 = new Path();
        path1.moveTo(startPoint.x, startPoint.y);
        path1.quadTo(cPoint1.x, cPoint1.y, endPonit.x, endPonit.y);

        path2 = new Path();
        path2.moveTo(startPoint.x, startPoint.y);
        path2.quadTo(cPoint2.x, cPoint2.y, endPonit.x, endPonit.y);

        currentPoint1 = startPoint;
        currentPoint2 = startPoint;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawBezier(canvas);
        canvas.restore();
    }

    private void drawBezier(Canvas canvas) {
        canvas.drawCircle(currentPoint1.x, currentPoint1.y, 20, paint);
        canvas.drawCircle(currentPoint2.x, currentPoint2.y, 20, paint);

    }

    @Override
    public void onClick(View v) {
        Log.e("zjw", "Click  ");
        ValueAnimator valueAnimator1 = ValueAnimator.ofObject(new BezierEvaluator(cPoint1), startPoint, endPonit);
        valueAnimator1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint1 = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator1.setInterpolator(new BounceInterpolator());
        ValueAnimator valueAnimator2 = ValueAnimator.ofObject(new BezierEvaluator(cPoint2), startPoint, endPonit);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentPoint2 = (PointF) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator2.setInterpolator(new DecelerateInterpolator());
        AnimatorSet animationSet = new AnimatorSet();
        animationSet.playTogether(valueAnimator1, valueAnimator2);
        animationSet.setDuration(2000);
        animationSet.start();
    }

    class BezierEvaluator implements TypeEvaluator<PointF> {
        private PointF cPoint;

        public BezierEvaluator(PointF cPoint) {
            this.cPoint = cPoint;
        }

        @Override
        public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
            return BezierUtil.CalculateBezierPointForQuadratic(fraction, startValue, cPoint, endValue);
        }
    }

}
