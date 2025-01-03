package com.example.calltaxiapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import java.util.ArrayList;

public class bulletin_board_Activity extends AppCompatActivity {

    private ArrayList<bulletinboard_scroll_data> arrayList;
    private bulletinboard_scroll_adapter mainAdapter;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    ImageView editBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bulletin_board);

//        TitleBar_Remove();
        //Change_Color(R.color.PaprikaOrange);

        recyclerView = (RecyclerView)findViewById(R.id.bulletin_board_RecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        arrayList = new ArrayList<>();
        editBtn = findViewById(R.id.bulletin_board_Imageview_Writingicon);
        Context context = this;
        mainAdapter = new bulletinboard_scroll_adapter(arrayList, context);
        recyclerView.setAdapter(mainAdapter);

        //상태바 색 변경
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(bulletin_board_Activity.this, R.color.app_color));

        findViewById(R.id.bulletin_board_Imageview_Backicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("!!!!", "뒤로가기 onClick");
                finish();
            }

        });

        findViewById(R.id.bulletin_board_Imageview_Searchicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("!!!", "검색 onClick");
            }
        });

        findViewById(R.id.bulletin_board_Imageview_Writingicon).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editBtn.setImageResource(R.drawable.image321);
                Intent intent = new Intent(getApplicationContext(), bulletinboard_writing.class);
                startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("newData")) {
                bulletinboard_scroll_data newData = (bulletinboard_scroll_data) data.getSerializableExtra("newData");
                arrayList.add(newData);
                mainAdapter.notifyDataSetChanged();
            }
        }
    }

    void TitleBar_Remove() {
        getSupportActionBar().hide();
    }
    void StatusBar_Remove() {
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    }
    private void Change_Color(int colorResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, colorResId));
        }
    }
}