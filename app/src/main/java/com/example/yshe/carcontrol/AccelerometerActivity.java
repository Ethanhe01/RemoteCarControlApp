package com.example.yshe.carcontrol;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.icu.util.Calendar;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v4.os.AsyncTaskCompat;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AccelerometerActivity extends AppCompatActivity implements SensorEventListener {
    //分别显示X、Y、Z轴方向上的加速度
    private TextView xView,yView,zView,oView;
    // SensorManager对象
    private SensorManager manager;
    // 传感器对象
    private Sensor sensor;
    private Button mStopButton;
    public int movetype;
    int sen=1;
    int tagertSiteId = 0;//0:yeelink,1:lewei50
    String apiKey = "";
    String site = "";
    String siteUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accelerometer);
        android.icu.util.Calendar mCalendar;

        xView = (TextView)findViewById(R.id.textView1);
        yView = (TextView)findViewById(R.id.textView2);
        zView = (TextView)findViewById(R.id.textView3);
        oView = (TextView)findViewById(R.id.textView4);

        mStopButton = (Button) findViewById(R.id.stop_button);
        mStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AccelerometerActivity.this, MainActivity.class);
                startActivity(i);
            }
        });
        //获取加速度传感器管理服务
        manager = (SensorManager)getSystemService(SENSOR_SERVICE);
        //获取加速度传感器
        sensor = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //后台子进程创建
        new UpdateMessage().execute();

    }

    @Override
    //覆盖onResume()方法
    protected void onResume() {
        // TODO Auto-generated method stub
        // 注册监听对象
        manager.registerListener(
                //监听对象
                this,
                    //加速度传感器对象
                    sensor,
                    //延迟60ms
                    manager.SENSOR_DELAY_UI);
        super.onResume();
    }

    private class UpdateMessage extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params){
            new SendMessage().sendSensorValueToYeelink(353802, 399169, movetype);
            return null;
        }
    }

    public void onAccuracyChanged(Sensor sensor,int accuracy){
        // TODO Auto-generated method stub
    }
    public void onSensorChanged(SensorEvent event){
        // TODO Auto-generated method stub
        //显示加速度值
        float [] values = event.values;
        xView.setText("X："+String.valueOf((int) values[0]));
        yView.setText("Y："+String.valueOf((int) values[1]));
        zView.setText("Z："+String.valueOf((int) values[2]));
        movetype = GetType(values[0],values[1],values[2]);
        if(movetype==1)
            oView.setText("Move forward!");
        else if(movetype==2)
            oView.setText("Move backward!");
        else if(movetype==3)
            oView.setText("Turn left!");
        else if(movetype==4)
            oView.setText("Turn right!");
        else
            oView.setText("Stop!");
    }

    public int GetType(float px, float py,float pz) {
        int change=0;
        float x,y,z;
        x=px;y=py;z=pz;
        if(Math.abs(x)-Math.abs(y)>0)
        {
            y=x;change=1;
        }
        if(y<-3.0)
        {
            if(change==1) return 4;
            return 1;
        }
        else if(y>3.0) {
            if(change==1) return 3;
            return 2;
        }
        return 0;
    }

}
