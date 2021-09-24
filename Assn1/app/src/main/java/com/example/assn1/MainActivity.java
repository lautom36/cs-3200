package com.example.assn1;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.example.assn1.databinding.ActivityMainBinding;

public class MainActivity extends Activity {

    private int steps;
    private long lastStepTimeStamp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        TextView counter = findViewById(R.id.counter);
        steps = 0;
        lastStepTimeStamp = 0;

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent sensorEvent) {
                float x = sensorEvent.values[0];
                float y = sensorEvent.values[1];
                float z = sensorEvent.values[2];

                float gForceX = x / SensorManager.GRAVITY_EARTH;
                float gForceY = y / SensorManager.GRAVITY_EARTH;
                float gForceZ = z / SensorManager.GRAVITY_EARTH;

                float normalizedGForce = (float)Math.sqrt(gForceX * gForceX + gForceY * gForceY + gForceZ * gForceZ);

                long now = System.currentTimeMillis();
                if (normalizedGForce > 1.5f){
                    if (lastStepTimeStamp + 500 > now) { return; }
                    steps += 1;
                    counter.setText(String.valueOf(steps));

                    lastStepTimeStamp = now;
                }

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int i) {

            }
        }, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);


        findViewById(R.id.container).setOnTouchListener((view, motionEvent) -> {
            if (motionEvent.getAction()== MotionEvent.ACTION_DOWN){
                steps = 0;
                counter.setText(0 + "");
            }
            return  false;
        });
    }
}