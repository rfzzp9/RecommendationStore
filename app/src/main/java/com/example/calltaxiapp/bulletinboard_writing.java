package com.example.calltaxiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class bulletinboard_writing extends AppCompatActivity {
    private boolean checkbox_flag = true;
    ImageView complete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletinboard_writing);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(bulletinboard_writing.this, R.color.app_color));

        complete = findViewById(R.id.Imageview_Completionicon);

        //  TitleBar_Remove();
       // Change_Color(R.color.PaprikaOrange);

        findViewById(R.id.Imageview_Closeicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                complete.setImageResource(R.drawable.group507);

                String Title = ((EditText)findViewById(R.id.Title_EditText)).getText().toString();
                String Content = ((EditText)findViewById(R.id.Content_EditText)).getText().toString();

                Calendar calendar = Calendar.getInstance();
                Date now = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String Time =sdf.format(now);

                String Nickname = "";
                if(!checkbox_flag) {
                    Nickname = "익명";
                }

                bulletinboard_scroll_data mainData = new bulletinboard_scroll_data(Title, Content, Time.split(" ")[1], Nickname, "0", "0");

                Intent intent = new Intent();
                intent.putExtra("newData", mainData);
                setResult(RESULT_OK, intent);

                Intent intent1 = new Intent(bulletinboard_writing.this, publishing_details_Activity.class);
                intent1.putExtra("title", Title);
                intent1.putExtra("content", Content);
                intent1.putExtra("time", Time);
                intent1.putExtra("nickname", Nickname);
                startActivity(intent1);

                finish();

            }
        });

        ImageView imageView_checkbox = findViewById(R.id.Imageview_Checkbox);
        imageView_checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkbox_flag) {
                    imageView_checkbox.setImageResource(R.drawable.checkbox_chack);
                    checkbox_flag = false;
                } else {
                    imageView_checkbox.setImageResource(R.drawable.checkbox);
                    checkbox_flag = true;
                }
            }
        });

    }
    void TitleBar_Remove() {
        getSupportActionBar().hide();
    }

    private void Change_Color(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, colorResId));
        }
    }

}