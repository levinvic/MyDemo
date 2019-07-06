package com.example.administrator.mydemo.BlockCanvas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.administrator.mydemo.BuildConfig;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class MyCanvas extends View implements View.OnTouchListener {

    public static final String TAG = "ccc";
    private Path path;
    private List<Path> lst_path;
    private Iterator<Path> iterator;
    private Paint pen_green;
    private Paint pen_black;
    private int width;
    private int height;
    private static float pointX;
    private static float pointY;

    public MyCanvas(Context context) {
        super(context);
        path = new Path();
        lst_path = new LinkedList<>();
        pen_green = new Paint();
        pen_green.setColor(Color.GREEN);
        pen_green.setStrokeWidth(5);
        pen_green.setAntiAlias(true); // 反鋸齒
        pen_green.setStyle(Paint.Style.STROKE); // 空心

        pen_black = new Paint();
        pen_black.setColor(Color.BLACK);
        pen_black.setStrokeWidth(5);

        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        width = displayMetrics.widthPixels;
        height = displayMetrics.heightPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(50, 50, width - 50, height - 280, pen_black);

        iterator = lst_path.iterator();
        while (iterator.hasNext()) {
            canvas.drawPath(iterator.next(), pen_green);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        pointX = event.getX();
        pointY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (BuildConfig.DEBUG) Log.d("MyCanvas", "ActionDwon");
                path = new Path();
                lst_path.add(path);
                if (pointX > 50 & pointX < width - 50 & pointY > 50 & pointY < height - 280) {
                    path.moveTo(pointX, pointY);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (BuildConfig.DEBUG) Log.d("MyCanvas", "ActionMove");
                if (pointX > 50 & pointX < width - 50 & pointY > 50 & pointY < height - 280) {
                    path.lineTo(pointX, pointY);
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        invalidate(); // UI刷新
        return true;
    }



}
