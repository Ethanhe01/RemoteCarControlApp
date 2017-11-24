package com.example.yshe.carcontrol;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.icu.util.Calendar;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Joystick extends AppCompatActivity {

    private Button mStopButton;
    private Button mAccelerateButton;
    private Button mDecelerateButton;
    Calendar mCalendar;
    Data data=new Data();
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_joystick);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);

        //setContentView(R.layout.activity_joystick);
       // this.getWindow().setFlags(1024, 1024);
       // this.requestWindowFeature(1);
        this.setContentView(new MySurfaceView(this));

        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads().detectDiskWrites().detectNetwork()
                .penaltyLog().build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects().detectLeakedClosableObjects()
                .penaltyLog().penaltyDeath().build());

        mCalendar = Calendar.getInstance();
        long stamp = mCalendar.getTimeInMillis() / 1000l;// 1393844912
        long laststamp=0;
        if(stamp-laststamp>5) {                         // 每十秒发送一次
            laststamp=stamp;
            //System.out.println(data.getType());
            sendSensorValueToYeelink(353802, 399169, data.getType());
        }
    }
    int tagertSiteId = 0;//0:yeelink,1:lewei50
    String apiKey = "";
    String site = "";
    String siteUrl = "";

    private void sendSensorValueToYeelink(int deviceId,int sensorId,int mt)
    {
        System.out.println("try to send sensor value"+mt);
        site = "http://api.yeelink.net/";
//            site = "http://192.168.1.254/";
        siteUrl = site + "v1.0/device/"+deviceId+"/sensor/"+sensorId+"/datapoints";

        try {
            URL url = new URL(siteUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            System.out.println("POST");
            conn.setRequestProperty("Connection", "close");
            System.out.println("Connection");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            System.out.println("content-Type");
            conn.setRequestProperty("U-ApiKey", "98e0812c9969d84d4cd38483a6c9f5f3");  //api key in yeelink.net
            System.out.println("U-ApiKey");
            conn.setRequestProperty("Accept", "*/*");
            System.out.println("Accept");
            conn.connect();
            System.out.println("connect");
            OutputStream out = conn.getOutputStream();
            System.out.println("getOutputStream");

            String value = "{\"value\":"+mt+"}";
            out.write(value.getBytes());
            out.flush();
            InputStream stream = conn.getInputStream();
            byte[] data=new byte[102400];
            int length=stream.read(data);
            conn.disconnect();
            stream.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}

