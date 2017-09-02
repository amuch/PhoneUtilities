package com.example.adam.phoneutilities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.text.DecimalFormat;

/**
 * Created by adam on 8/7/17.
 */

public class SpeedometerView extends SurfaceView implements Runnable {
    // Convert meters per second to miles per hour.
    final float SPEED_CONVERSION_FACTOR = 2.23694f;

    DecimalFormat speedFormat, odometerFormat;
    String pattern = "##0.00";


    Thread speedometerThread = null;
    SurfaceHolder speedometerHolder;
    volatile boolean speedometerInView;

    private int screenWidth, screenHeight;
    Canvas canvas;

    double latitude, longitude;

    // Float and string for speed.
    float speed;
    String speedString;

    // Float and string for maximum speed.
    float maxSpeed;
    String maxSpeedString;

    // Custom location listener.
    SpeedometerLocationListener speedometerLocationListener;

    // Default constructor required.
    public SpeedometerView(Context context) {
        super(context);
        speedometerHolder = getHolder();

        speedString = "NULL";

        maxSpeedString = "NULL";
        maxSpeed = 0.0f;
    }

    // More useful constructor.
    public SpeedometerView(Context context, Point size) {
        super(context);
        speedometerHolder = getHolder();

        // Set the screen width and height.
        screenWidth = size.x;
        screenHeight = size.y;

        // Set-up the location listener.
        speedometerLocationListener = new SpeedometerLocationListener(context);

        speedFormat = new DecimalFormat(pattern);
        speedString = "NULL";

        maxSpeedString = "NULL";
        maxSpeed = 0.0f;
    }
    @Override
    public void run() {
        while (speedometerInView) {
            updateSpeedometer();
            drawSpeedometer();
            controlFrameRate();
        }
    }

    private void updateSpeedometer() {
        latitude = speedometerLocationListener.getLatitude();
        longitude = speedometerLocationListener.getLongitude();
        speed = speedometerLocationListener.getSpeed() * SPEED_CONVERSION_FACTOR;

        speedString = speedFormat.format(speed);

        if (speed > maxSpeed) {
            maxSpeed = speed;
            maxSpeedString = speedString;
        }
    }

    private void drawSpeedometer() {
        if (speedometerHolder.getSurface().isValid()) {

            canvas = speedometerHolder.lockCanvas();
            Paint paint = new Paint();
            canvas.drawColor(Color.BLACK);

            paint.setColor(Color.WHITE);
            paint.setTextSize(screenHeight / 13);
            canvas.drawText("Latitude: " + latitude, screenWidth / 100, screenHeight / 12, paint);
            canvas.drawText("Longitude: " + longitude, screenWidth / 2 + screenWidth / 100, screenHeight / 12, paint);

            paint.setColor(Color.GREEN);
            paint.setTextSize(screenHeight / 5);
            canvas.drawText(speedString + " MPH", screenWidth / 3, screenHeight / 2, paint);

            paint.setColor(Color.WHITE);
            paint.setTextSize(screenHeight / 13);
            canvas.drawText("Max speed: " + maxSpeedString + " MPH", screenWidth / 100, screenHeight - screenHeight / 12, paint);

            speedometerHolder.unlockCanvasAndPost(canvas);
        }

    }

    private void controlFrameRate() {

    }
    public void pause() {
        speedometerInView = false;
        try {
            speedometerThread.join();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        speedometerInView = true;
        speedometerThread = new Thread(this);
        speedometerThread.start();
    }
}
