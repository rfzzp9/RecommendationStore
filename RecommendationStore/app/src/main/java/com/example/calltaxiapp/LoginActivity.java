package com.example.calltaxiapp;

import android.content.Intent;
import android.os.Build;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;     //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText email, pwd;
    private ImageView loginBtn, exitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //상태바 투명하게
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        loginBtn = findViewById(R.id.loginBtn);
        exitBtn = findViewById(R.id.exit);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("CallTaxiApp");

        //로그인 버튼
        loginBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                //로그인 요청
                String strEmail = email.getText().toString();
                String strPwd = pwd.getText().toString();

                loginBtn.setImageResource(R.drawable.loginbtnclicked2);

                mFirebaseAuth.signInWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            //로그인 성공
                            Intent intent = new Intent(LoginActivity.this, MapActivity.class);
                            startActivity(intent);
                            loginBtn.setImageResource(R.drawable.loginbtn);
                            finish(); //현재 액티비티 파괴
                        } else {
                            loginBtn.setImageResource(R.drawable.loginbtn);
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                return false;
            }
        });

        //x 버튼
        exitBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
                startActivity(intent);
                exitBtn.setImageResource(R.drawable.exitclicked);
                finish(); //현재 액티비티 파괴

                return false;
            }
        });
    }
}