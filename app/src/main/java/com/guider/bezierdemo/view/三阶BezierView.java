package com.guider.bezierdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by apple on 16/11/8.
 */

public class 三阶BezierView extends View {
    private Paint linePaint, textPaint, bezierPaint;
    private Point startPonit, endPonit, cPoint1, cPoint2;
    private Path path = new Path();

    public 三阶BezierView(Context context) {
        this(context, null);
    }

    public 三阶BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public 三阶BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(6);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(20);
        textPaint.setStyle(Paint.Style.STROKE);

        bezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bezierPaint.setStrokeWidth(8);
        bezierPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startPonit = new Point(w / 4, h / 2);
        endPonit = new Point((int) (w * 0.75), h / 2);
        cPoint1 = new Point(w / 3, 0);
        cPoint2 = new Point(w / 3 * 2, h);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawBezier(canvas);
        drawaOther(canvas);
        canvas.restore();

    }

    private void drawaOther(Canvas canvas) {
        canvas.drawText("起点", startPonit.x, startPonit.y, textPaint);
        canvas.drawText("终点", endPonit.x, endPonit.y, textPaint);
        drawLine(startPonit, cPoint1, linePaint, canvas);
        drawLine(cPoint1, cPoint2, linePaint, canvas);
        drawLine(cPoint2, endPonit, linePaint, canvas);

    }

    private void drawLine(Point startPonit, Point cPoint1, Paint linePaint, Canvas canvas) {
        canvas.drawLine(startPonit.x, startPonit.y, cPoint1.x, cPoint1.y, linePaint);
    }

    private void drawBezier(Canvas canvas) {
        path.reset();
        path.moveTo(startPonit.x, startPonit.y);
        path.cubicTo(cPoint1.x, cPoint1.y, cPoint2.x, cPoint2.y, endPonit.x, endPonit.y);
        canvas.drawPath(path, bezierPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:

                if (Math.sqrt(Math.pow(event.getX() - cPoint1.x, 2) + Math.pow(event.getY() - cPoint1.y, 2))
                        < Math.sqrt(Math.pow(event.getX() - cPoint2.x, 2) + Math.pow(event.getY() - cPoint2.y, 2))) {
                    //距离cPonit1 比较近
                    cPoint1.x = (int) event.getX();
                    cPoint1.y = (int) event.getY();

                } else {
                    cPoint2.x = (int) event.getX();
                    cPoint2.y = (int) event.getY();

                }
                invalidate();
                break;
        }
        // 不return 无法获得action_move事件
        return true;
    }
}
