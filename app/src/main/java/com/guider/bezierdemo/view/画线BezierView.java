package com.guider.bezierdemo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

/**
 * Created by apple on 16/11/8.
 */

public class 画线BezierView extends View {
    private float currentX;
    private float currentY;

    private Paint paint;
    private Path path;
    private float TOUCH_SOLP = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    public 画线BezierView(Context context) {
        this(context, null);
    }

    public 画线BezierView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public 画线BezierView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        path = new Path();
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4);
        paint.setColor(Color.BLUE);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.reset();
                currentX = event.getX();
                currentY = event.getY();
                path.moveTo(currentX, currentY);
                break;
            case MotionEvent.ACTION_MOVE:
                if (TOUCH_SOLP < Math.sqrt(Math.pow(event.getX() - currentX, 2) + Math.pow(event.getY() - currentY, 2))) {
                    float centerX = (currentX + event.getX()) / 2;
                    float centerY = (currentY + event.getY()) / 2;
                    path.quadTo(currentX, currentY, centerX, centerY);
                    currentX = event.getX();
                    currentY = event.getY();
                }
                break;
        }
        invalidate();
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path,paint);
    }
}
