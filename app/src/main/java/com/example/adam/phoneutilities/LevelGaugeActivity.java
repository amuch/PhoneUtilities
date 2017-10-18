package com.example.adam.phoneutilities;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Display;

public class LevelGaugeActivity extends Activity {
    LevelGaugeView levelGaugeView;
    Display display;
    Point point;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SensorManager sensorManager;
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        // Get the screen dimensions to set the view.
        display = getWindowManager().getDefaultDisplay();
        point = new Point();
        display.getSize(point);

        // Set the view.
        levelGaugeView = new LevelGaugeView(this, point, sensorManager);

        setContentView(levelGaugeView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        levelGaugeView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        levelGaugeView.pause();
    }
}
