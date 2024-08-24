package com.example.calltaxiapp;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.net.ParseException;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.installations.Utils;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.label.Label;
import com.kakao.vectormap.label.LabelLayer;
import com.kakao.vectormap.label.LabelOptions;
import com.kakao.vectormap.label.LabelStyle;
import com.kakao.vectormap.label.LabelStyles;
import com.kakao.vectormap.shape.MapPoints;

import org.checkerframework.checker.units.qual.A;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ReadAndWriteSnippets {

    private static final String TAG = "ReadAndWriteSnippets";

    private Geocoder coder;
    private ArrayList<String> coordinate_arr;
    private ExecutorService executorService;
    private Handler mainHandler;

    private DatabaseReference databaseReference;
    String myUserId;
    Context context;
    KakaoMap kakaoMap;
    String sector, store, paymentMethod, area, price, parkingLot;
    public ReadAndWriteSnippets(Context _context, KakaoMap _kakaoMap) {
        //고유 아이디
        myUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        context = _context;
        kakaoMap = _kakaoMap;
        // Get a reference to the Firebase Database
        databaseReference = FirebaseDatabase.getInstance().getReference();


        String[] area = {"경기도", "경상남도", "경상북도", "광주광역시", "대구광역시", "대전광역시", "부산광역시", "서울특별시",
        "세종특별자치시", "울산광역시", "인천광역시", "전라남도", "전북특별자치도", "제주특별자치도", "충청남도", "충청북도"};

         //   databaseReference.child("경기도").child("아파트(전월세)");

        //basicQueryValueListener_SingleFamilyHome_Trading(); //단독다가구(매매)

    }


    //TODO 아래 함수 onDataChange에서 데이터 다가져온후 콜백함수 에서 데이터 반환하기
    public void basicQueryValueListener_v1(String element) {
     //   Query myTopPostsQuery = databaseReference.child(element);
        ArrayList<String> storeList = new ArrayList<String>();
        Query myTopPostsQuery = databaseReference.child("경상북도").child(element);

        myTopPostsQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                storeList.clear();
                for(DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    storeList.add(postSnapshot.toString());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
            }
        });

//        mDatabase.child("users").child(userId).setValue(user)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        // Write was successful!
//                        // ...
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        // Write failed
//                        // ...
//                    }
//                });

    }




}
