package com.example.adam.phoneutilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class FlashlightTestActivity extends Activity implements View.OnClickListener{
    private CameraManager cameraManager;
    private String cameraId;
    private Boolean isTorchOn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight_test);

        // Bind the on and off buttons.
        Button flashlightTestOnButton = (Button) findViewById(R.id.flashlightTestOnButton);
        flashlightTestOnButton.setOnClickListener(this);

        Button flashlightTestOffButton = (Button) findViewById(R.id.flashlightTestOffButton);
        flashlightTestOffButton.setOnClickListener(this);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
        isTorchOn = false;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            // Turn on the flashlight.
            case R.id.flashlightTestOnButton:
                if (!isTorchOn) {
                    try {
                        cameraManager.setTorchMode(cameraId, true);
                        Toast.makeText(this, "Flashlight On.", Toast.LENGTH_LONG).show();
                        isTorchOn = true;
                    }
                    catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

                else {
                    try {
                        cameraManager.setTorchMode(cameraId, false);
                        Toast.makeText(this, "Flashlight Off", Toast.LENGTH_LONG).show();
                        isTorchOn = false;
                    }
                    catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }
            // Turn off.
            case R.id.flashlightTestOffButton:
                if (isTorchOn) {
                    try {
                        cameraManager.setTorchMode(cameraId, false);
                        Toast.makeText(this, "Flashlight Off", Toast.LENGTH_LONG).show();
                        isTorchOn = false;
                    }
                    catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

            default:
                break;
        }

    }
}
