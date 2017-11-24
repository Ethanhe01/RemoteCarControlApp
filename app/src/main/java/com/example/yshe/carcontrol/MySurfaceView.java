package com.example.yshe.carcontrol;

/**
 * Created by yshe on 2017/1/4.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.widget.Toast;

public class MySurfaceView extends SurfaceView implements Callback, Runnable {
    private Thread th;
    private SurfaceHolder sfh;
    private Canvas canvas;
    private Paint paint;
    private boolean flag;
    private int RockerCircleX = 550;
    private int RockerCircleY = 1100;
    private int RockerCircleR = 150;
    private float SmallRockerCircleX = 550.0F;
    private float SmallRockerCircleY = 1100.0F;
    private float SmallRockerCircleR = 75.0F;

    public MySurfaceView(Context context) {
        super(context);
        Log.v("Hi", "MySurfaceView");
        this.setKeepScreenOn(true);
        this.sfh = this.getHolder();
        this.sfh.addCallback(this);
        this.paint = new Paint();
        this.paint.setAntiAlias(true);
        this.setFocusable(true);
        this.setFocusableInTouchMode(true);
    }

    public void surfaceCreated(SurfaceHolder holder) {
        this.th = new Thread(this);
        this.flag = true;
        this.th.start();
    }

    public double getRad(float px1, float py1, float px2, float py2) {
        float x = px2 - px1;
        float y = py1 - py2;
        float xie = (float)Math.sqrt(Math.pow((double)x, 2.0D) + Math.pow((double)y, 2.0D));
        float cosAngle = x / xie;
        float rad = (float)Math.acos((double)cosAngle);
        if(py2 < py1) {
            rad = -rad;
        }

        return (double)rad;
    }

    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() != 0 && event.getAction() != 2) {
            if(event.getAction() == 1) {
                this.SmallRockerCircleX = 550.0F;
                this.SmallRockerCircleY = 1100.0F;
            }
        } else if(Math.sqrt(Math.pow((double)(this.RockerCircleX - (int)event.getX()), 2.0D) + Math.pow((double)(this.RockerCircleY - (int)event.getY()), 2.0D)) >= (double)this.RockerCircleR) {
            double tempRad = this.getRad((float)this.RockerCircleX, (float)this.RockerCircleY, event.getX(), event.getY());
            this.getXY((float)this.RockerCircleX, (float)this.RockerCircleY, (float)this.RockerCircleR, tempRad);
        } else {
            this.SmallRockerCircleX = (float)((int)event.getX());
            this.SmallRockerCircleY = (float)((int)event.getY());
        }

        return true;
    }

    public void getXY(float centerX, float centerY, float R, double rad) {
        this.SmallRockerCircleX = (float)((double)R * Math.cos(rad)) + centerX;
        this.SmallRockerCircleY = (float)((double)R * Math.sin(rad)) + centerY;
    }

    public void draw() {
        try {
            this.canvas = this.sfh.lockCanvas();
            this.canvas.drawColor(-1);
            this.paint.setColor(1879048192);
            this.canvas.drawCircle((float)this.RockerCircleX, (float)this.RockerCircleY, (float)this.RockerCircleR, this.paint);
            this.paint.setColor(1895759872);
            this.canvas.drawCircle(this.SmallRockerCircleX, this.SmallRockerCircleY, this.SmallRockerCircleR, this.paint);
        } catch (Exception var10) {
            ;
        } finally {
            try {
                if(this.canvas != null) {
                    this.sfh.unlockCanvasAndPost(this.canvas);
                }
            } catch (Exception var9) {
                ;
            }

        }

    }

    public void run() {
        while(this.flag) {
            this.draw();

            try {
                Thread.sleep(50L);
            } catch (Exception var2) {
                ;
            }
        }

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.v("Hi", "surfaceChanged");
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        this.flag = false;
        Log.v("Hi", "surfaceDestroyed");
    }
}
