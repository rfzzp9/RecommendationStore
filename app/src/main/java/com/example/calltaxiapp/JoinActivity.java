package com.example.calltaxiapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class JoinActivity extends AppCompatActivity {

    private FirebaseAuth mFirebaseAuth;     //파이어베이스 인증
    private DatabaseReference mDatabaseRef; //실시간 데이터베이스
    private EditText email, pwd;
    private ImageView joinBtn, checkBox_all, check1, check2, check3, check4, check5, check6, check7, exitBtn;
    ConstraintLayout background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        //상태바 투명하게
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        mFirebaseAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("CallTaxiApp");

        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pwd);
        joinBtn = findViewById(R.id.joinBtn);
        checkBox_all = findViewById(R.id.checkbox);
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        check4 = findViewById(R.id.check4);
        check5 = findViewById(R.id.check5);
        check6 = findViewById(R.id.check6);
        check7 = findViewById(R.id.check7);
        exitBtn = findViewById(R.id.exit);

        checkBox_all.setTag(R.drawable.checkbox);

        checkBox_all.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if((Integer) checkBox_all.getTag() == R.drawable.checkbox) {
                    checkBox_all.setTag(R.drawable.checkboxclicked);
                    checkBox_all.setImageResource(R.drawable.checkboxclicked);
                    check1.setImageResource(R.drawable.checkclicked);
                    check2.setImageResource(R.drawable.checkclicked);
                    check3.setImageResource(R.drawable.checkclicked);
                    check4.setImageResource(R.drawable.checkclicked);
                    check5.setImageResource(R.drawable.checkclicked);
                    check6.setImageResource(R.drawable.checkclicked);
                    check7.setImageResource(R.drawable.checkclicked);
                }
                else {
                    checkBox_all.setTag(R.drawable.checkbox);
                    checkBox_all.setImageResource(R.drawable.checkbox);
                    check1.setImageResource(R.drawable.check);
                    check2.setImageResource(R.drawable.check);
                    check3.setImageResource(R.drawable.check);
                    check4.setImageResource(R.drawable.check);
                    check5.setImageResource(R.drawable.check);
                    check6.setImageResource(R.drawable.check);
                    check7.setImageResource(R.drawable.check);
                }

                return false;
            }
        });

        joinBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                String strEmail = email.getText().toString();
                String strPwd = pwd.getText().toString();

                joinBtn.setImageResource(R.drawable.joinbtn2clicked);

                mFirebaseAuth.createUserWithEmailAndPassword(strEmail, strPwd).addOnCompleteListener(JoinActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {


                            FirebaseUser firebaseUser = mFirebaseAuth.getCurrentUser();
                            UserAccount account = new UserAccount();
                            account.setIdToken(firebaseUser.getUid());
                            account.setEmailId(firebaseUser.getEmail());
                            account.setPassword(strPwd);

                            //setValue : database에 삽입하는 행위
                            mDatabaseRef.child("UserAccount").child(firebaseUser.getUid()).setValue(account);

                            Toast.makeText(JoinActivity.this, "회원가입에 성공",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(JoinActivity.this, LoginActivity.class);
                            startActivity(intent);
                            joinBtn.setImageResource(R.drawable.joinbtn2);
                            finish(); //현재 액티비티 파괴
                        }
                        else {
                            joinBtn.setImageResource(R.drawable.joinbtn2);
                            Toast.makeText(JoinActivity.this, "회원가입에 실패",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                return false;
            }
        });

        exitBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                exitBtn.setImageResource(R.drawable.exitclicked);
                Intent intent = new Intent(JoinActivity.this, MainActivity2.class);
                startActivity(intent);
                finish();
                return false;
            }
        });

    }
}