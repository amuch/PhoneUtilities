package com.example.adam.phoneutilities;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

/**
 * Created by adam on 8/8/17.
 */

public class FlashlightService extends Service {
    private CameraManager cameraManager;
    private String cameraId;
    private Boolean isTorchOn;

    @Override
    public void onCreate() {
        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = cameraManager.getCameraIdList()[0];
        }
        catch (CameraAccessException e) {
            e.printStackTrace();
        }
        isTorchOn = false;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        /*Boolean isFlashAvailable = getApplicationContext()
                .getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if (!isFlashAvailable) {
            Toast.makeText(this, "Flashlight unavailable.", Toast.LENGTH_LONG).show();
            return 0;
        }*/
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
        // Continue running until stopped.
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

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
    }
}
