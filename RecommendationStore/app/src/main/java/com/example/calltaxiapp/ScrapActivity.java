package com.example.calltaxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class ScrapActivity extends AppCompatActivity {

    ImageView backbutton;
    RecyclerView recyclerView;
    ScrapRecyclerviewAdapter scrapRecyclerviewAdapter;
    DatabaseReference mDatabase; //스크랩 데이터 가져오기
    ArrayList<FactoryInfo> scraplist;
    SharedPreferences sharedPreferences;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrap);

        sharedPreferences = getSharedPreferences("ScrapList", MODE_PRIVATE);

        backbutton = findViewById(R.id.backbutton);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL)); ///구분선 넣어주는 옵션
        mDatabase = FirebaseDatabase.getInstance().getReference();
        scraplist = new ArrayList<>();
        scrapRecyclerviewAdapter = new ScrapRecyclerviewAdapter(this, scraplist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        backBtn = findViewById(R.id.backbutton);

        mDatabase.child("scrap").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { //TODO scrapitem -> 데이터 받아오는 값마다 view랑 제대로 mapping 시켜주기
                if(snapshot.exists()) {
                    for(DataSnapshot childSnapshot : snapshot.getChildren()) {
                        String key = childSnapshot.getKey();
                        Map<String, Object> value = (Map<String, Object>) childSnapshot.getValue();
                        String name = "", address = "", area = "", year = "", price = "", contract_method = "";

                        if(value.containsKey("단지명")) {
                            name = value.get("단지명").toString();
                        } else if(value.containsKey("용도지역")) {
                            name = value.get("용도지역").toString();
                        } else if(value.containsKey("주택유형")) {
                            name = value.get("주택유형").toString();
                        } else if(value.containsKey("건물명")) {
                            name = value.get("건물명").toString();
                        }

                        address = value.get("주소").toString();

                        if(value.containsKey("대지면적(㎡)")) {
                            area = value.get("대지면적(㎡)").toString()+"㎡ ";
                        } else if(value.containsKey("계약면적(㎡)")) {
                            area = value.get("계약면적(㎡)").toString()+"㎡ ";
                        } else if(value.containsKey("전용면적(㎡)")) {
                            area = value.get("전용면적(㎡)").toString()+"㎡ ";
                        } else if(value.containsKey("토지")) {
                            area = value.get("토지").toString()+"㎡ ";
                        }

                        if(value.containsKey("건축년도")) {
                            year = value.get("건축년도").toString();
                        } else {
                            year = "2005";
                        }

                        if(value.containsKey("거래금액(만원)")) {
                            price = value.get("거래금액(만원)").toString();
                            contract_method = "매매";
                        } else if(value.containsKey("보증금(만원)") && value.containsKey("전월세구분")) {
                            price = value.get("보증금(만원)") + " / "+ value.get("월세금(만원)");
                            contract_method = value.get("전월세구분").toString();
                        }

                        scraplist.add(new FactoryInfo(name, address, price, area, year, contract_method));
                        recyclerView.setAdapter(scrapRecyclerviewAdapter);
                        scrapRecyclerviewAdapter.setList(scraplist);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });



        //상태바 색 변경
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(ScrapActivity.this, R.color.app_color));
    }
}