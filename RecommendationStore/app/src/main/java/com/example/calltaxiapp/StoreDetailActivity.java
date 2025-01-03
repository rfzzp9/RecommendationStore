package com.example.calltaxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class StoreDetailActivity extends AppCompatActivity {

    ImageView starButton;
    ImageView backButton;
    TextView storeName, address, etc, amount, visitRanking;
    ImageView contractMethod;
    DatabaseReference mDatabase; //스크랩버튼 누르면 스크랩 항목 추가
    HashMap<String, Object> hashmap;
    Context context = this;
    TextView interior_name, interior_address, interiorAmount; //인테리어업체
    TextView clean_name, clean_address, cleanAmount;          //청소업체

//    DatabaseReference db = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference initialBalanceRef = db
//            .child("account_details")
//            .child("Gn2eRixQQiTpvhX870n6nZp66tO2")
//            .child("initialBalance");
//initialBalanceRef.setValue(ServerValue.increment(amount));


    SharedPreferences shared;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        shared = context.getSharedPreferences("ScrapList", Context.MODE_PRIVATE);
        editor = shared.edit();
        editor.putInt("scrap count", 5);
        editor.commit();
        //상태바 색 변경
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(StoreDetailActivity.this, R.color.app_color));

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        starButton = findViewById(R.id.starBtn);
        backButton = findViewById(R.id.backBtn);
        storeName = findViewById(R.id.storeName);
        address = findViewById(R.id.address);
        etc = findViewById(R.id.etc);
        amount = findViewById(R.id.amount);
        contractMethod = findViewById(R.id.contractMethod);
        visitRanking = findViewById(R.id.visitRanking);
        interior_name = findViewById(R.id.interior_name);
        interior_address = findViewById(R.id.interior_address);
        interiorAmount = findViewById(R.id.interiorAmount);
        clean_name = findViewById(R.id.clean_name);
        clean_address = findViewById(R.id.clean_address);
        cleanAmount = findViewById(R.id.cleanAmount);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        TouchListener touchListener = new TouchListener();
        starButton.setOnTouchListener(touchListener);

        //상세 점포 데이터 receive
        hashmap = (HashMap<String,Object>) getIntent().getExtras().get("hashMap");
        Log.e("@@@@@receive ", ""+hashmap.toString());

        if(hashmap.containsKey("단지명")) {
            storeName.setText(hashmap.get("단지명").toString());
        }
        if(hashmap.containsKey("건축물주용도")) {
            storeName.setText(hashmap.get("건축물주용도").toString());
        }
        if(hashmap.containsKey("주택유형")) {
            storeName.setText(hashmap.get("주택유형").toString());
        }
        if(hashmap.containsKey("건물명")) {
            storeName.setText(hashmap.get("건물명").toString());
        }
        if(hashmap.containsKey("용도지역")) {
            storeName.setText(hashmap.get("용도지역").toString());
        }

        address.setText(hashmap.get("주소").toString());
        if(hashmap.get("주소").toString().contains("구미")) {
            databaseReference.child("청소업체").child("구미").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        // TODO: handle the post
                        clean_name.setText(postSnapshot.child("업소명").getValue().toString());
                        clean_address.setText(postSnapshot.child("소재지(도로명)").getValue().toString());
                        cleanAmount.setText("150(만원)");
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });

            databaseReference.child("인테리어업체").child("구미").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    interior_name.setText(snapshot.child("0").child("업소명").getValue().toString());
                    interior_address.setText(snapshot.child("0").child("소재지(도로명)").getValue().toString());
                    interiorAmount.setText("173(만원)");
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
        if(hashmap.get("주소").toString().contains("영천")) {
            databaseReference.child("청소업체").child("영천").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        // TODO: handle the post
                        clean_name.setText(postSnapshot.child("업소명").getValue().toString());
                        clean_address.setText(postSnapshot.child("소재지(도로명)").getValue().toString());
                        cleanAmount.setText("122(만원)");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            databaseReference.child("인테리어업체").child("영천").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                        // TODO: handle the post
                        interior_name.setText(postSnapshot.child("업소명").getValue().toString());
                        interior_address.setText(postSnapshot.child("소재지(도로명)").getValue().toString());
                        interiorAmount.setText("115(만원)");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                }
            });
        }

        if(hashmap.containsKey("전용면적")) {
            etc.setText(hashmap.get("전용면적(㎡)").toString()+" / "+hashmap.get("건축년도").toString()+(2025-Integer.valueOf(hashmap.get("건축년도").toString())));
        }
        if(hashmap.containsKey("대지면적")) {
            etc.setText(hashmap.get("대지면적(㎡)").toString()+" / "+hashmap.get("건축년도").toString()+(2025-Integer.valueOf(hashmap.get("건축년도").toString())));
        }
        if(hashmap.containsKey("계약면적")) {
            try {
                etc.setText(hashmap.get("계약면적(㎡)").toString()+" / "+hashmap.get("건축년도").toString()+"("+(2025-Integer.valueOf(hashmap.get("건축년도").toString()))+"년차)");
            } catch(Exception e) {
//                etc.setText(hashmap.get("계약면적(㎡)").toString()+" / "+"2013"+"("+(2025-2013)+"년차)");
            }
        }

        if(hashmap.containsKey("전월세구분")) {
            contractMethod.setImageResource(R.drawable.monthlyrent);
            amount.setText(hashmap.get("보증금(만원)").toString()+"(만원)");
                if(hashmap.get("전월세구분").toString().equals("월세")) {
                    amount.setText(hashmap.get("보증금(만원)").toString()+"(만원) / "+hashmap.get("월세금(만원)").toString()+"(만원)");
                }
        }
        if(!hashmap.containsKey("전월세구분") && hashmap.containsKey("거래금액(만원)")) {
            contractMethod.setImageResource(R.drawable.contract_method);
            amount.setText(hashmap.get("거래금액(만원)").toString()+"(만원)");
        }

        Random rand = new Random();
        int randomNum = 1 + rand.nextInt((50 - 1) + 1);
        visitRanking.setText("주간 방문자 순위 "+randomNum+"위");
    }

    class TouchListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    switch (view.getId()) {
                        case R.id.starBtn:
                            starButton.setImageResource(R.drawable.image333);
                            String key = mDatabase.child("scrap").push().getKey();
                            Map<String, Object> childUpdates = new HashMap<>();
                            childUpdates.put("/scrap/" + key, hashmap);
                            mDatabase.updateChildren(childUpdates);
                            break;
                        case R.id.backbutton:
                            backButton.setImageResource(R.drawable.image327);
                            break;
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    switch (view.getId()) {
                        case R.id.backbutton:
                            Intent intent = new Intent(StoreDetailActivity.this, MapActivity.class);
                            startActivity(intent);
                            break;
                    }
                    return true;
            }
            return false;
        }

    }


}