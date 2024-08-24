package com.example.calltaxiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class publishing_details_Activity extends AppCompatActivity {

    private boolean checkbox_flag = true;

    private ArrayList<post_comments_scroll_data> arrayList;
    private post_comments_scroll_adapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.publishing_details);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(publishing_details_Activity.this, R.color.app_color));


        Intent intent = getIntent();

        String title = intent.getStringExtra("title");
        String content = intent.getStringExtra("content");
        String time = intent.getStringExtra("time");
        String nickname = intent.getStringExtra("nickname");

        ((TextView)findViewById(R.id.publishing_details_Textview_Time)).setText(time);
        ((TextView)findViewById(R.id.publishing_details_Textview_Posttitle)).setText(title);
        ((TextView)findViewById(R.id.publishing_details_Textview_Contents_the_post)).setText(content);
        ((TextView)findViewById(R.id.publishing_details_Textview_Nickname)).setText(nickname);


        recyclerView = (RecyclerView)findViewById(R.id.publishing_details_recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();

        mainAdapter = new post_comments_scroll_adapter(arrayList);
        recyclerView.setAdapter(mainAdapter);

        findViewById(R.id.publishing_details_Imageview_Backicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        ImageView imageView_checkbox = findViewById(R.id.publishing_details_Imageview_Checkbox);
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

        findViewById(R.id.publishing_details_Imageview_enter_comments_arrow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String Content = ((EditText)findViewById(R.id.publishing_details_Content_EditText)).getText().toString();

                Calendar calendar = Calendar.getInstance();
                Date now = calendar.getTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String Time =sdf.format(now);

                String Nickname = "";
                if(!checkbox_flag) {
                    Nickname = "익명";
                }

                ((EditText)findViewById(R.id.publishing_details_Content_EditText)).setText("");

                post_comments_scroll_data mainData = new post_comments_scroll_data(R.drawable.anonymous_image, Nickname, Content, Time.split(" ")[1]); // 초기 값으로 설정
                arrayList.add(mainData);
                mainAdapter.notifyDataSetChanged();


            }
        });

    }




}