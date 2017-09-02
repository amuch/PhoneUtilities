package com.example.adam.phoneutilities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SpeedometerActivity extends Activity implements View.OnClickListener{
    TextView latitude, longitude, speedometer, odometer, maxSpeed;
    Button resetOdometer, resetMaxSpeed, stopService;
    Intent intent;
    /*SpeedometerView speedometerView;
    Display display;
    Point size;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Code to use the predefined layout.
        setContentView(R.layout.activity_speedometer);

        //Bind the TextViews.
        latitude = (TextView) findViewById(R.id.latitudeText);
        longitude = (TextView) findViewById(R.id.longitudeText);
        speedometer = (TextView) findViewById(R.id.speedText);
        odometer = (TextView) findViewById(R.id.odometerText);
        maxSpeed = (TextView) findViewById(R.id.maxSpeedText);

        // Bind the buttons.
        resetOdometer = (Button) findViewById(R.id.resetOdometerButton);
        resetMaxSpeed = (Button) findViewById(R.id.resetMaxSpeedButton);
        stopService = (Button) findViewById(R.id.stopSpeedometerServiceButton);

        intent = new Intent(SpeedometerActivity.this, SpeedometerService.class);
        this.startService(intent);

        // Code to use a SpeedometerView
        /*display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);

        speedometerView = new SpeedometerView(this, size);

        setContentView(speedometerView);*/
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onResume() {
        super.onResume();


        //speedometerView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();


        //speedometerView.pause();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.resetOdometerButton:
                break;

            case R.id.resetMaxSpeedButton:
                break;

            case R.id.stopSpeedometerServiceButton:
                this.stopService(intent);
                break;

            default:
                break;
        }
    }

    private void runSpeedometer() {
        //SpeedometerService.getRequestInterval();
    }
}
