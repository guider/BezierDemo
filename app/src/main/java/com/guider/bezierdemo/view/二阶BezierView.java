package com.guider.bezierdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by apple on 16/11/7.
 */

public class 二阶BezierView extends View {
    private Paint textPaint;
    private Paint linePaint;
    private Paint bezierPaint;
    private Point auxiliaryPoint;
    private Point startPoint;
    private Point endPoint;
    private Path path = new Path();


    public 二阶BezierView(Context context) {
        this(context, null);
    }

    public 二阶BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public 二阶BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setStyle(Paint.Style.STROKE);
        textPaint.setTextSize(20);

        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        linePaint.setStrokeWidth(2);
        linePaint.setStyle(Paint.Style.STROKE);

        bezierPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bezierPaint.setStyle(Paint.Style.STROKE);
        bezierPaint.setStrokeWidth(5);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        startPoint = new Point(getWidth() / 4, getHeight() / 2 - 200);
        auxiliaryPoint = new Point(getWidth() / 2, getHeight() / 2);
        endPoint = new Point(getWidth() / 4 * 3, getHeight() / 2 - 200);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public 二阶BezierView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        path.reset();
        path.moveTo(startPoint.x, startPoint.y);
        path.quadTo(auxiliaryPoint.x, auxiliaryPoint.y, endPoint.x, endPoint.y);
        canvas.drawPath(path, bezierPaint);
        canvas.drawText("起点", startPoint.x, startPoint.y, textPaint);
        canvas.drawText("终点", endPoint.x, endPoint.y, textPaint);

        canvas.drawLine(startPoint.x, startPoint.y, auxiliaryPoint.x, auxiliaryPoint.y, linePaint);
        canvas.drawLine(auxiliaryPoint.x, auxiliaryPoint.y,endPoint.x,endPoint.y,linePaint);


    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                auxiliaryPoint.x = (int) event.getX();
                auxiliaryPoint.y = (int) event.getY();
                invalidate();
                break;
        }

        return true;
    }
}
