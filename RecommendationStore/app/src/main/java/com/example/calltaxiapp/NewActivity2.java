package com.example.calltaxiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class NewActivity2 extends AppCompatActivity {

    ConstraintLayout cleanLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new2);

        //상태바 색 변경
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(NewActivity2.this, R.color.app_color));

        cleanLayout = findViewById(R.id.cleanLayout);

        cleanLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        cleanLayout.setBackgroundColor(getResources().getColor(R.color.app_color_clicked));
                        return true;
                    case MotionEvent.ACTION_UP:
                        Intent intent = new Intent(NewActivity2.this, NewActivity3.class);
                        startActivity(intent);
                        cleanLayout.setBackgroundColor(getResources().getColor(R.color.app_color));
                        break;
                }

                return false;
            }
        });
    }
}