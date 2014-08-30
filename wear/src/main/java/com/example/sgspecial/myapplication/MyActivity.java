package com.example.sgspecial.myapplication;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.Sensor;    // 1.5
import android.hardware.SensorEvent;    // 1.5
import android.hardware.SensorEventListener;    // 1.5
//import android.hardware.SensorListener; // 1.1

public class MyActivity extends Activity {

    private TextView mTextView;
    private Random mRandom = new Random(); // ランダム
    private boolean _isFlashingDisplay;
    private SensorManager _sensorManager;
    private SensorEventListener _sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                String str = "加速度センサー値:"
                        + "\nX軸:" + event.values[SensorManager.DATA_X]
                        + "\nY軸:" + event.values[SensorManager.DATA_Y]
                        + "\nZ軸:" + event.values[SensorManager.DATA_Z];
                mTextView.setText(str);
            }

        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                mTextView.setText("テスト!");
                startFlashDisplay();
            }
        });

        _sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);


        printSensors(this);
    }

    private static void printSensors(Context context) {
        SensorManager mSensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);

        List<Sensor> deviceSensors = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : deviceSensors) {
            Log.d("タグ", sensor.getName() + ", " + sensor.getStringType() + ", " + sensor.getVendor());
        }
    }

    private void startFlashDisplay() {
        //TODO:色きりかえ
//        int colors[] = [Color.blue, Color.red, Color.green, Color.WHITE, Color.YELLOW];
//        ArrayList<> colors = new ArrayList();
//        colors.add(Color.BLUE);
        changeColor(Color.BLUE);
    }

    private void stopFlashDisplay() {
        _isFlashingDisplay = false;
    }

    private void changeColor(int color) {
        LinearLayout ll = (LinearLayout) findViewById(R.id.LLayout);
        ll.setBackgroundColor(color);
    }


    @Override
    protected void onResume() {
        super.onResume();
        List<Sensor> sensors = _sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            Sensor s = sensors.get(0);
            _sensorManager.registerListener(_sensorEventListener, s, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        _sensorManager.unregisterListener(_sensorEventListener);
    }
}
