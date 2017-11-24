package com.example.yshe.carcontrol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yshe on 2017/1/5.
 */

public class SendMessage {
    String apiKey = "";
    String site = "";
    String siteUrl = "";
    public void sendSensorValueToYeelink(int deviceId,int sensorId,int mt)
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
