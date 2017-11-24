package com.example.yshe.carcontrol;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Gsensor extends Activity implements SensorEventListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView textviewX;
    private TextView textviewY;
    private TextView textviewZ;
    private TextView textviewF;
    private Button mStopButton;

    private int mX, mY, mZ;
    private long lasttimestamp = 0;
    Calendar mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsensor);
        textviewX = (TextView) findViewById(R.id.textView1);
        textviewY = (TextView) findViewById(R.id.textView3);
        textviewZ = (TextView) findViewById(R.id.textView4);
        textviewF = (TextView) findViewById(R.id.textView2);
        mStopButton = (Button) findViewById(R.id.stop_button);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Gsensor.this,R.string.stop_toast,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Gsensor.this,MainActivity.class);
                startActivity(i);
            }
        });

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);// TYPE_GRAVITY
        if (null == mSensorManager) {
            Log.d(TAG, "deveice not support SensorManager");
        }
        // 参数三，检测的精准度
        mSensorManager.registerListener(this, mSensor,
                SensorManager.SENSOR_DELAY_NORMAL);// SENSOR_DELAY_GAME

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor == null) {
            return;
        }

        if (event.sensor.getType() == Sensor.TYPE_GRAVITY) {
            int x = (int) event.values[0];
            int y = (int) event.values[1];
            int z = (int) event.values[2];
            mCalendar = Calendar.getInstance();
            long stamp = mCalendar.getTimeInMillis() / 1000l;// 1393844912

            textviewX.setText("X: "+String.valueOf(x));
            textviewY.setText("Y: "+String.valueOf(y));
            textviewZ.setText("Z: "+String.valueOf(z));

            int second = mCalendar.get(Calendar.SECOND);// 53

            int px = Math.abs(mX - x);
            int py = Math.abs(mY - y);
            int pz = Math.abs(mZ - z);
            Log.d(TAG, "pX:" + px + "  pY:" + py + "  pZ:" + pz + "    stamp:"
                    + stamp + "  second:" + second);
            int movetype = getMaxValue(px, py, pz);

            if (movetype > 0 && (stamp - lasttimestamp) > 30) {
                lasttimestamp = stamp;
                Log.d(TAG, " sensor isMoveorchanged....");
                if(movetype==1)
                    textviewF.setText("Move forward");
                else if(movetype==2)
                    textviewF.setText("Move backward");
                else if(movetype==3)
                    textviewF.setText("Turn left");
                else
                    textviewF.setText("Ture right");
            }

            mX = x;
            mY = y;
            mZ = z;

        }
    }
    /**
     * 获取一个最大值
     * @param px
     * @param py
     * @param pz
     * @return
     */
    public int getMaxValue(int px, int py, int pz) {
        int max = 0;
        if (px > py && px > pz) {
            max = px;
            if(max>2) return 1;
        } else if (py > px && py > pz) {
            max = py;
            if(max>2) return 2;
        } else if (pz > px && pz > py) {
            max = pz;
            if(max>2) return 3;
        }
        return max;
    }


}

