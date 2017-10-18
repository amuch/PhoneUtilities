package com.example.adam.phoneutilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by adam on 10/12/17.
 */

public class LevelGaugeView extends SurfaceView implements Runnable{

    Thread viewThread = null;
    SurfaceHolder surfaceHolder;
    Canvas canvas;
    Paint paint;
    volatile boolean isInView;
    long lastFrameTime;
    int fps;
    private int screenWidth, screenHeight;
    private RectF horizontalRect, verticalRect;
    private float topEdge, bottomEdge, leftEdge, rightEdge;
    private float xAcceleration, yAcceleration, zAcceleration;
    private float blueX, blueY, greenX, greenY, radius;
    private SensorManager sensorManager;
    private Sensor sensor;




    public LevelGaugeView(Context context) {
        super(context);
        surfaceHolder = getHolder();
    }

    public LevelGaugeView(Context context, Point point, SensorManager sensorManager) {
        super(context);
        surfaceHolder = getHolder();

        setScreenWidth(point.x);
        setScreenHeight(point.y);

        setLeftEdge(getScreenWidth() / 20);
        setRightEdge(19 * getScreenWidth() / 20);

        horizontalRect = new RectF(getLeftEdge(),
                                   getScreenHeight() / 2 - getScreenWidth() / 12,
                                   getRightEdge(),
                                   getScreenHeight() / 2 + getScreenWidth() / 12);



        setTopEdge(getScreenWidth() / 20);
        setBottomEdge(getScreenHeight() - getScreenWidth() /20);

        verticalRect = new RectF(getScreenWidth() / 2 - getScreenWidth() / 12,
                                 getTopEdge(),
                                 getScreenWidth() / 2 + getScreenWidth() / 12,
                                 getBottomEdge());

        this.sensorManager = sensorManager;
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        setXAcceleration(0.0f);
        setYAcceleration(0.0f);
        setZAcceleration(0.0f);

        setBlueX(getScreenWidth() / 2);
        setBlueY(getScreenHeight() / 2);
        setGreenX(getScreenWidth() / 2);
        setGreenY(getScreenHeight() / 2);
        setRadius(getScreenWidth() / 12);
    }

    @Override
    public void run() {
        while (isInView) {
            updateGauge();
            drawGauge();
            //controlFPS();
        }
    }

    private void updateGauge() {

    }

    private void drawGauge() {
        if (surfaceHolder.getSurface().isValid()) {
            canvas = surfaceHolder.lockCanvas();

            paint = new Paint();

            // Draw the background color.
            canvas.drawColor(Color.argb(255, 95, 95, 95));

            paint.setColor(Color.WHITE);
            paint.setTextSize(60.0f);
            canvas.drawText("X: " + getXAcceleration(), 10, 60, paint);
            canvas.drawText("Y: " + getYAcceleration(), getScreenWidth() / 3 + 10, 60, paint);
            canvas.drawText("Z: " + getZAcceleration(), 2 * getScreenWidth() / 3 + 10, 60, paint);
            paint.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(horizontalRect, 30, 30, paint);
            canvas.drawRoundRect(verticalRect, 30, 30, paint);

            // Draw the circles.
            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.argb(80, 0, 127, 0));
            canvas.drawCircle(getGreenX(), getGreenY(), getRadius(), paint);

            paint.setColor(Color.argb(100, 0, 0, 127));
            canvas.drawCircle(getBlueX(), getBlueY(), getRadius(), paint);


            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    private void controlFPS() {
        long timeThisFrame = (System.currentTimeMillis() - lastFrameTime);
        long timeToSleep = 15 - timeThisFrame;
        if (timeThisFrame > 0) {
            fps = (int) (1000 / timeThisFrame);
        }
        if (timeToSleep > 0) {

            try {
                viewThread.sleep(timeToSleep);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        lastFrameTime = System.currentTimeMillis();
    }

    private SensorEventListener sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            setXAcceleration(event.values[0]);

            setBlueY((getScreenHeight() / 2) + (event.values[0] / 9.8f) * (getScreenHeight() / 2 - getScreenWidth() / 20 - radius));

            setYAcceleration(event.values[1]);

            setGreenX((getScreenWidth() / 2) + (event.values[1] / 9.8f) * (getScreenWidth() / 2 - getScreenWidth() / 20 - radius));



            setZAcceleration(event.values[2]);
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };


    public void pause() {
        isInView = false;
        try {
            viewThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        sensorManager.unregisterListener(sensorEventListener);
    }

    public void resume() {
        isInView = true;
        viewThread = new Thread(this);
        viewThread.start();
        sensorManager.registerListener(sensorEventListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public float getTopEdge() {
        return topEdge;
    }

    public void setTopEdge(float topEdge) {
        this.topEdge = topEdge;
    }

    public float getBottomEdge() {
        return bottomEdge;
    }

    public void setBottomEdge(float bottomEdge) {
        this.bottomEdge = bottomEdge;
    }

    public float getLeftEdge() {
        return leftEdge;
    }

    public void setLeftEdge(float leftEdge) {
        this.leftEdge = leftEdge;
    }

    public float getRightEdge() {
        return rightEdge;
    }

    public void setRightEdge(float rightEdge) {
        this.rightEdge = rightEdge;
    }

    public float getXAcceleration() {
        return xAcceleration;
    }

    public void setXAcceleration(float xAcceleration) {
        this.xAcceleration = xAcceleration;
    }

    public float getYAcceleration() {
        return yAcceleration;
    }

    public void setYAcceleration(float yAcceleration) {
        this.yAcceleration = yAcceleration;
    }

    public float getZAcceleration() {
        return zAcceleration;
    }

    public void setZAcceleration(float zAcceleration) {
        this.zAcceleration = zAcceleration;
    }

    public float getBlueX() {
        return blueX;
    }

    public void setBlueX(float blueX) {
        this.blueX = blueX;
    }

    public float getBlueY() {
        return blueY;
    }

    public void setBlueY(float blueY) {
        this.blueY = blueY;
    }

    public float getGreenX() {
        return greenX;
    }

    public void setGreenX(float greenX) {
        this.greenX = greenX;
    }

    public float getGreenY() {
        return greenY;
    }

    public void setGreenY(float greenY) {
        this.greenY = greenY;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
}
