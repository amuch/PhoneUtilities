package com.example.adam.phoneutilities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class FlashlightActivity extends Activity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);

        // Bind the on and off buttons.
        Button flashlightOnButton = (Button) findViewById(R.id.flashlightOnButton);
        flashlightOnButton.setOnClickListener(this);

        Button flashlightOffButton = (Button) findViewById(R.id.flashlightOffButton);
        flashlightOffButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;

        switch (view.getId()) {
            // Start the service (turn on the flashlight).
            case R.id.flashlightOnButton:
                intent = new Intent(FlashlightActivity.this, FlashlightService.class);
                this.startService(intent);
                break;

            // Stop the service (turn off the flashlight).
            case R.id.flashlightOffButton:
                intent = new Intent(FlashlightActivity.this, FlashlightService.class);
                this.stopService(intent);
                break;

            default:
                break;
        }

    }
}
