package com.example.sgspecial.myapplication;

import java.util.List;
import java.util.Random;

import android.os.Handler;
import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.wearable.view.WatchViewStub;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.hardware.SensorManager;
import android.hardware.Sensor;    // 1.5
import android.hardware.SensorEvent;    // 1.5
import android.hardware.SensorEventListener;    // 1.5

import com.google.android.gms.internal.e;
//import android.hardware.SensorListener; // 1.1

public class MyActivity extends Activity {

    private TextView mTextView;
    private Random mRandom = new Random(); // ランダム
    private SensorManager _sensorManager;

    private SensorEventListener _sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
//                changeColor((int) (event.values[SensorManager.DATA_X] + event.values[SensorManager.DATA_Y] + event.values[SensorManager.DATA_Z]));

                if (mTextView == null) {
                    return;
                }
                MyActivity.this.startFlashDisplay();
                String str = SensorModel.getString(event);
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
        _sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        final WatchViewStub stub = (WatchViewStub) findViewById(R.id.watch_view_stub);
        stub.setOnLayoutInflatedListener(new WatchViewStub.OnLayoutInflatedListener() {
            @Override
            public void onLayoutInflated(WatchViewStub stub) {
                mTextView = (TextView) stub.findViewById(R.id.text);
                mTextView.setText("テスト!");
            }
        });

//        SensorModel.printSensors(this);
    }

    //TODO_DONE:色きりかえ
    private void startFlashDisplay() {
        int colors[] = {Color.BLUE, Color.RED, Color.GREEN, Color.WHITE, Color.YELLOW, Color.BLACK, Color.CYAN, Color.DKGRAY, Color.MAGENTA, Color.BLUE, Color.RED};
        int color = colors[Util.get0to9()];
        changeColor(color);
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
