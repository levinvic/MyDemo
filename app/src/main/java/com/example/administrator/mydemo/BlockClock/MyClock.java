package com.example.administrator.mydemo.BlockClock;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.View;

import java.util.Calendar;

public class MyClock extends View {
    public static final String TAG = "ccc";
    private Paint pen_green;
    private Paint pen_black;
    private Paint pen_red;
    private Paint pen_blue;
    private Paint pen_gray;
    private Path path_hour;
    private Path path_minute;


    public MyClock(Context context) {
        super(context);
        setPaint();
    }

    private void setPaint(){
        // 設定屬性
        pen_green = new Paint();
        pen_black = new Paint();
        pen_red = new Paint();
        pen_blue = new Paint();
        pen_gray = new Paint();
        pen_green.setColor(Color.GREEN);
        pen_black.setColor(Color.BLACK);
        pen_red.setColor(Color.RED);
        pen_blue.setColor(Color.BLUE);
        pen_gray.setColor(Color.GRAY);
        pen_green.setStrokeWidth(10);
        pen_black.setStrokeWidth(5);
        pen_red.setStrokeWidth(5);
        pen_blue.setStrokeWidth(8);
        pen_gray.setStrokeWidth(2);

        pen_green.setStyle(Paint.Style.STROKE);
        pen_blue.setStyle(Paint.Style.STROKE);
        pen_red.setStyle(Paint.Style.STROKE);

        // 設定路徑
        path_hour = new Path();
        path_minute = new Path();

        path_hour.moveTo(500, 500);
        path_hour.lineTo(500, 300);

        path_minute.moveTo(500, 500);
        path_minute.lineTo(500, 150);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 背景
        canvas.drawRect(50, 50, getWidth() - 50, getHeight() - 50, pen_black);
        // 畫圓
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, getWidth() / 2 - 100, pen_green);
        canvas.save();

        //將圖畫在中間
        canvas.translate((getWidth() - 1000) / 2, (getHeight() - 1000) / 2);
        canvas.save();

        Calendar calendar = Calendar.getInstance();
        canvas.save();
        // 通過獲取系統的時間，然後繪製到對應的時針
        canvas.rotate(calendar.get(Calendar.HOUR) * 30, 500, 500);
        canvas.drawPath(path_hour, pen_blue);


        // 重繪上一次的時鐘
        canvas.restore();
        canvas.rotate(calendar.get(Calendar.MINUTE) * 6, 500, 500);
        canvas.drawPath(path_minute, pen_red);
        canvas.save();

        canvas.rotate(calendar.get(Calendar.SECOND) * 6, 500, 500);
        canvas.drawLine(500, 500, 500, 100, pen_gray);

        canvas.restore();
        canvas.save();

        invalidate();

//        if ((i += 1) > 360) {
//            i = 0;
//        }
//
//        if (i % 60 == 0) {
//            int str_second = Calendar.getInstance().getTime().getSeconds();
//            Log.d(TAG, "str_second: " + str_second);
//            path.moveTo(width / 2, height / 2 - 200);
//            path.lineTo(height / 2 - 150, width / 2 - 150);
//        }
//        invalidate();
    }


}
