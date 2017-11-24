package com.example.yshe.carcontrol;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button mGravityButton;
    private Button mAccelerometerButton;
    private Button mJoystickButton;
    private Button mGuestureButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAccelerometerButton = (Button) findViewById(R.id.Accelerometer_button);
        mAccelerometerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,R.string.Gravity_toast,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, AccelerometerActivity.class);
                startActivity(i);
            }
        });

        mJoystickButton = (Button) findViewById(R.id.joystick_button);
        mJoystickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,R.string.Gravity_toast,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, Joystick.class);
                startActivity(i);
            }
        });

        mGuestureButton = (Button) findViewById(R.id.Guesture_button);
        mGuestureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,R.string.Gravity_toast,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, Guesture.class);
                startActivity(i);
            }
        });

        mGravityButton = (Button) findViewById(R.id.About_button);
        mGravityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(MainActivity.this,R.string.Gravity_toast,Toast.LENGTH_SHORT).show();
                Intent i = new Intent(MainActivity.this, About.class);
                startActivity(i);
            }
        });

    }
}
