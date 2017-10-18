package com.example.adam.phoneutilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        // Bind the speedometer's button.
        Button speedometerButton = (Button) findViewById(R.id.speedometerButton);
        speedometerButton.setOnClickListener(this);

        // Bind the flashlight's button.
        Button flashlightButton = (Button) findViewById(R.id.flashlightButton);
        flashlightButton.setOnClickListener(this);

        // Bind the flashlightTest's button.
        Button flashlightTestButton = (Button) findViewById(R.id.flashlightTestButton);
        flashlightTestButton.setOnClickListener(this);

        // Bind the compass's button.
        Button compassButton = (Button) findViewById(R.id.compassButton);
        compassButton.setOnClickListener(this);

        // Bind the level gauge's button.
        Button levelGaugeButton = (Button) findViewById(R.id.levelGaugeButton);
        levelGaugeButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {

            case R.id.speedometerButton:
                intent = new Intent(this, SpeedometerActivity.class);
                startActivity(intent);
                break;

            case R.id.flashlightButton:
                intent = new Intent(this, FlashlightActivity.class);
                startActivity(intent);
                break;

            case R.id.flashlightTestButton:
                intent = new Intent(this, FlashlightTestActivity.class);
                startActivity(intent);
                break;

            case R.id.compassButton:
                break;

            case R.id.levelGaugeButton:
                intent = new Intent(this, LevelGaugeActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }
}
