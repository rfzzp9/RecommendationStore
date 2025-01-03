package com.example.calltaxiapp;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity2 extends AppCompatActivity {

    ImageView joinButton; //가입하기 버튼
    ImageView loginButton; //로그인하기 버튼

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //상태바 투명하게
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        joinButton = findViewById(R.id.joinBtn);
        loginButton = findViewById(R.id.loginBtn);

        joinButton.setImageResource(R.drawable.joinbtn);
        loginButton.setImageResource(R.drawable.text3);

        joinButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                joinButton.setImageResource(R.drawable.joinbtnclicked);

                Intent intent = new Intent(MainActivity2.this, JoinActivity.class);
                MainActivity2.this.startActivity(intent);
                finish();
                return false;

            }
        });

        loginButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                loginButton.setImageResource(R.drawable.loginbtnclicked);

                Intent intent = new Intent(MainActivity2.this, LoginActivity.class);
                MainActivity2.this.startActivity(intent);
                finish();
                return false;
            }
        });

    }

}

