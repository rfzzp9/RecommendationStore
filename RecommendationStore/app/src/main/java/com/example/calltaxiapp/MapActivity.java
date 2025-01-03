package com.example.calltaxiapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kakao.vectormap.GestureType;
import com.kakao.vectormap.KakaoMap;
import com.kakao.vectormap.KakaoMapReadyCallback;
import com.kakao.vectormap.KakaoMapSdk;
import com.kakao.vectormap.LatLng;
import com.kakao.vectormap.MapLifeCycleCallback;
import com.kakao.vectormap.MapView;
import com.kakao.vectormap.camera.CameraAnimation;
import com.kakao.vectormap.camera.CameraPosition;
import com.kakao.vectormap.camera.CameraUpdate;
import com.kakao.vectormap.camera.CameraUpdateFactory;
import com.kakao.vectormap.label.Label;
import com.kakao.vectormap.label.LabelLayer;
import com.kakao.vectormap.label.LabelManager;
import com.kakao.vectormap.label.LabelOptions;
import com.kakao.vectormap.label.LabelStyle;
import com.kakao.vectormap.label.LabelStyles;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapActivity extends AppCompatActivity {
    MapView mapView;
    ImageView scrapIcon;
    ConstraintLayout category_detail_sectors, category_detail_stores, category_detail_purchase, category_detail_area;
    ConstraintLayout category_detail_price, category_detail_parkingLot;
    TextView category1, category2, category3, category4, category5, category6, category7, category8, category9, category10;
    TextView category11, category12, category13, category14, category15, category16, category17;
    TextView category18, category19;
    TextView category20, category21, category22, category23, category24;
    TextView sectors, stores, trading, area, price, parkinglot;
    AutoCompleteTextView editText_category;
    EditText price_minimum, price_maximum;
    Animation animSlide_RightToLeft, animSlide_LeftToRight;
    Animation animSlide_RightToLeft_2, animSlide_LeftToRight_2;
    ImageView confirmBtn, confirmBtn_2, confirmBtn_3, confirmBtn_4, confirmBtn_5, confirmBtn_6;
    ImageView checkBox_parkingLot;
    boolean flag_checkbox_parkingLot = true;
    KakaoMap kakaomap;
    ReadAndWriteSnippets readAndWriteSnippets;
    Context context = this;
    double currentLongitude, currentLatitude;
    String currentCoordinates;
    ImageView progressbar;
    private Handler handler;
    private Runnable runnable;
    int imageIndex = 0;
    FrameLayout progressbarFrame;
    ImageView logoText;
    LocationListener gpsLocationListener;
    int F_Call_Data_Count = 1;
    ImageView boardIcon;
    String User_Choice_Purchase_Data = "-1", User_Choice_Stores_Data = "-1";
    long[] Maney_Data = {-1};
    ArrayList<ArrayList<HashMap<String, Object>>> Category_Choice_Change_List_Data = new ArrayList<>();
    ArrayList<ArrayList<HashMap<String, Object>>> Final_All_Data = new ArrayList<>();
    double L_Top_Lat_Coordinates = 0, R_Top_Lon_Coordinates = 0, L_Bottom_Lat_Coordinates = 0, R_Bottom_Lat_Coordinates = 0;
    int[] Area_Size_User_Choice = {-1}; // 점포 사이즈 유저 선택
    String Store_Name = "";
    AutoCompleteTextView addressEdit;
    ArrayList<HashMap<String, Object>> list_storeDetails = new ArrayList<>();
    HashMap<String, String> Category_Area_Map = new HashMap<String, String>() {{
        put("공장창고등(매매)", "대지면적(㎡)");
        put("단독다가구(매매)", "연면적(㎡)");
        put("단독다가구(전월세)", "계약면적(㎡)");
        put("분양입주권(매매)", "전용면적(㎡)");
        put("상업업무용(매매)", "연면적(㎡)");

        put("아파트(매매)", "전용면적(㎡)");
        put("아파트(전월세)", "전용면적(㎡)");
        put("연립다세대(매매)", "전용면적(㎡)");
        put("연립다세대(전월세)", "전용면적(㎡)");
        put("오피스텔(매매)", "전용면적(㎡)");

        put("오피스텔(전월세)", "전용면적(㎡)");
        put("토지(매매)", "계약면적(㎡)");
    }};
    HashMap<String, String> Category_Amount_Map = new HashMap<String, String>() {{
        put("공장창고등(매매)", "거래금액(만원)");
        put("단독다가구(매매)", "거래금액(만원)");
        put("단독다가구(전월세)", "보증금(만원),월세금(만원)");
        put("분양입주권(매매)", "거래금액(만원)");
        put("상업업무용(매매)", "거래금액(만원)");

        put("아파트(매매)", "거래금액(만원)");
        put("아파트(전월세)", "보증금(만원),월세금(만원)");
        put("연립다세대(매매)", "거래금액(만원)");
        put("연립다세대(전월세)", "보증금(만원),월세금(만원)");
        put("오피스텔(매매)", "거래금액(만원)");

        put("오피스텔(전월세)", "보증금(만원),월세금(만원)");
        put("토지(매매)", "거래금액(만원)");
    }};
    TouchListener_Stores touchListener_stores = new TouchListener_Stores();
    TouchListener_Purchase touchListener_purchase = new TouchListener_Purchase();
    TouchListener_Area touchListener_area = new TouchListener_Area();
    TouchListener_Price touchListener_price = new TouchListener_Price();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        progressbarFrame = findViewById(R.id.progressbarBackground);
        logoText = findViewById(R.id.logotext);
        boardIcon = findViewById(R.id.boardIcon);
        scrapIcon = findViewById(R.id.scrapIcon);
        addressEdit = findViewById(R.id.addressEdit);

        //상태바 색 변경
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        getWindow().setStatusBarColor(ContextCompat.getColor(MapActivity.this, R.color.app_color));

        boardIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        boardIcon.setImageResource(R.drawable.clickedboard);
                        return true;
                    case MotionEvent.ACTION_UP:
                        Intent intent = new Intent(MapActivity.this, bulletin_board_Activity.class);
                        startActivity(intent);
                        boardIcon.setImageResource(R.drawable.boardicon);
                        break;
                }
                return false;
            }
        });

        //repaired 시작지점
        scrapIcon.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        scrapIcon.setImageResource(R.drawable.clickedscrap);
                        return true;
                    case MotionEvent.ACTION_UP:
                        Intent intent = new Intent(MapActivity.this, ScrapActivity.class);
                        startActivity(intent);
                        scrapIcon.setImageResource(R.drawable.star);
                        break;
                }
                return false;
            }
        });
        //repaired 끝지점

        // 위치 관리자 객체 참조하기
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{
                    android.Manifest.permission.ACCESS_FINE_LOCATION}, 0);
        } else {
            // 가장최근 위치정보 가져오기
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(location == null) {
                location = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            }
            if (location != null) {
                currentLongitude = location.getLongitude(); // 경도
                currentLatitude = location.getLatitude();   // 위도

                currentCoordinates = getAddressFromLocation(context, currentLatitude, currentLongitude);
                setupLocationListener();

//                if(lm != null) {  // 맵뷰에 버튼 추가 후 버튼 클릭 리스너 안에 들어갈 코드
//                    lm.requestSingleUpdate(LocationManager.GPS_PROVIDER, gpsLocationListener, null);
//                }

                fetchDataFromFirebase(new FirebaseCallback() {
                    @Override
                    public void onCallback(ArrayList<ArrayList<HashMap<String, Object>>> list) {

                        Final_All_Data = list;
                        //mapView 실행 메소드
                        setMapView(Final_All_Data);
                        //업종 선택 시 실행 메소드
                        setSectors();
                        //점포 선택 시 실행 메소드
                        setStores();
                        //구매방식 선택 시 실행 메소드
                        setPurchase();
                        //평형 선택 시 실행 메소드
                        setArea();
                        //가격 선택 시 실행 메소드 (거래금액(만원))
                        setPrice();
                        //주차장 선택 시 실행 메소드
                        setParkinglot();

                        // 로딩화면 패널

                        // UI를 업데이트하거나 다른 클래스의 함수 호출

                    }
                });
            }
        }
    }
    private void setupLocationListener() {
        gpsLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                String provider = location.getProvider();
                double longitude = location.getLongitude();
                double latitude = location.getLatitude();
                double altitude = location.getAltitude();
            }
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {}
            @Override
            public void onProviderDisabled(String provider) {}
        };
    }

    private void fetchDataFromFirebase(FirebaseCallback firebaseCallback) {

        if (F_Call_Data_Count == 1) {
            progressbarFrame.setBackgroundColor(getResources().getColor(R.color.app_color));
            logoText.setVisibility(View.VISIBLE);
        } else {
            progressbarFrame.setBackgroundColor(getResources().getColor(R.color.progressbarBackcolor));
            logoText.setVisibility(View.GONE);
        }
        progressbar = findViewById(R.id.progressbar);

        int[] imageResources = {
                R.drawable.progress_1,
                R.drawable.progress_2,
                R.drawable.progress_3,
                R.drawable.progress_4
        };

        handler = new Handler();
        runnable = new Runnable() { //프로그레스바 이미지 연속적으로 바뀌도록
            @Override
            public void run() {

                // Change the image in the ImageView
                progressbar.setImageResource(imageResources[imageIndex]);

                // Update the index for the next image
                imageIndex = (imageIndex + 1) % imageResources.length;

                // Schedule the next execution
                handler.postDelayed(this, 500); // 1 second delay
            }
        };
        handler.post(runnable);

        String[] Stores = {"공장창고등(매매)", "단독다가구(매매)", "단독다가구(전월세)", "분양입주권(매매)", "상업업무용(매매)",
                "아파트(매매)", "아파트(전월세)", "연립다세대(매매)", "연립다세대(전월세)", "오피스텔(매매)",
                "오피스텔(전월세)", "토지(매매)"};

        ArrayList<ArrayList<HashMap<String, Object>>> AllData = new ArrayList<>();

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

        String[] currentCoordinates_split = currentCoordinates.split("/"); //현재 지역
        String areaAndCity = currentCoordinates_split[1] + " "+ currentCoordinates_split[2]; //현재 ~도 ~시인지 가져옴

        for (String store : Stores) {
            databaseReference.child(currentCoordinates_split[1]).child(store).addValueEventListener(new ValueEventListener() {
                ArrayList<HashMap<String, Object>> storeList = new ArrayList<>();
                HashMap<String, Object> itemValue = new HashMap<>();
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    storeList.clear();
                    itemValue.put("Store Name", store);
                    storeList.add(itemValue);
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        if (postSnapshot.child("주소").getValue().toString().startsWith(areaAndCity) && !postSnapshot.child("위도경도").getValue().toString().equals("")) {
                            itemValue = (HashMap<String, Object>) postSnapshot.getValue();
                            storeList.add(itemValue);
                        }
                    }
                    AllData.add(storeList);
                    // 모든 데이터가 로드되었는지 확인 후 콜백 호출
                    if (AllData.size() == Stores.length) {
                        firebaseCallback.onCallback(AllData);
                        Final_All_Data = AllData;
                        ++F_Call_Data_Count;
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }
    private interface FirebaseCallback {
        void onCallback(ArrayList<ArrayList<HashMap<String, Object>>> list);
    }
    // 위도와 경도를 받아서 주소로 변환하는 메서드
    public static String getAddressFromLocation(Context context, double latitude, double longitude) {
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        String addressText = "";
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses != null && addresses.size() > 0) {
                Address address = addresses.get(0);

                String countryName = address.getCountryName();  // 국가 (ex : 대한민국)
                String adminArea = address.getAdminArea();  // 도 (ex : 경상북도)
                String cityName = address.getLocality(); //시 (ex : 구미시)
                /* 동 (ex : 형곡동)
                address.getSubLocality();
                address.getThoroughfare();
                 */
                addressText = countryName + "/" + adminArea + "/" + cityName;
            } else {
                addressText = "주소를 찾을 수 없습니다.";
            }
        } catch (IOException e) {
            addressText = "주소를 가져오는 중 오류가 발생했습니다.";
        }

        return addressText;
    }
    @Override
    public void onResume() {
        super.onResume();
        if (mapView != null) {
            mapView.resume(); // MapView 의 resume 호출
        }
    }
    @Override
    public void onPause() {
        super.onPause();
        if (mapView != null) {
            mapView.resume(); // MapView 의 pause 호출
        }
    }
    public void setMapView(ArrayList<ArrayList<HashMap<String, Object>>> _list) {  //mapView 실행 메소드, 지도 최초실행시 and 지도 움직임 있을 때마다 _list 반환
        //앱 키 등록
        KakaoMapSdk.init(this, "ab1d9c3ca6248ea506b26a16c17ad75b");

        mapView = findViewById(R.id.map_view);

        //mapview 생명주기
        mapView.start(new MapLifeCycleCallback() {
            @Override
            public void onMapDestroy() {
                // 지도 API 가 정상적으로 종료될 때 호출됨
                Log.w("when?", "when");
            }

            @Override
            public void onMapError(Exception error) {
                // 인증 실패 및 지도 사용 중 에러가 발생할 때 호출됨
                Log.w("when?", "fail");
            }

        }, new KakaoMapReadyCallback() {
            @Override
            public void onMapReady(KakaoMap kakaoMap) {

                findViewById(R.id.glass).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                        ((AutoCompleteTextView) findViewById(R.id.addressEdit)).getText().toString()

                        currentLatitude = 35.9230365;
                        currentLongitude = 128.9449911;

                        if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                        }

                        CameraUpdate cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(currentLatitude, currentLongitude));

                        kakaoMap.moveCamera(cameraUpdate, CameraAnimation.from(500, true, true));

                        fetchDataFromFirebase(new FirebaseCallback() {
                            @Override
                            public void onCallback(ArrayList<ArrayList<HashMap<String, Object>>> list) {

                                Final_All_Data = list;
                                L_Top_Lat_Coordinates = kakaoMap.fromScreenPoint(0, 0).getLatitude();
                                R_Top_Lon_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), 0).getLongitude();
                                L_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(0, mapView.getHeight()).getLongitude();
                                R_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), mapView.getHeight()).getLatitude();

                                touchListener_stores.GetData(Final_All_Data, kakaoMap);
                                touchListener_purchase.GetData(Final_All_Data, kakaoMap);
                                touchListener_area.GetData(Final_All_Data, kakaoMap);
                                touchListener_price.GetData(Final_All_Data, kakaoMap);

                                addMarker(Final_All_Data);

                                // 모든 ui세팅 끝나 위치
                                handler.removeCallbacks(runnable);
                                progressbarFrame.setVisibility(View.GONE);
                            }
                        });
                    }
                });

                // 인증 후 API 가 정상적으로 실행될 때 호출됨
                kakaomap = kakaoMap;
                L_Top_Lat_Coordinates = kakaoMap.fromScreenPoint(0, 0).getLatitude();
                R_Top_Lon_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), 0).getLongitude();
                L_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(0, mapView.getHeight()).getLongitude();
                R_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), mapView.getHeight()).getLatitude();

                touchListener_stores.GetData(_list, kakaoMap);
                touchListener_purchase.GetData(_list, kakaoMap);
                touchListener_area.GetData(_list, kakaoMap);
                touchListener_price.GetData(_list, kakaoMap);

                readAndWriteSnippets = new ReadAndWriteSnippets(context, kakaomap);

                addMarker(_list);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    window.setStatusBarColor(ContextCompat.getColor(MapActivity.this, R.color.app_color));
                }

                //repaired
                kakaomap.setOnLabelClickListener(new KakaoMap.OnLabelClickListener() {
                    @Override
                    public void onLabelClicked(KakaoMap kakaoMap, LabelLayer labelLayer, Label label) {

                        Log.e("@@@@@@Label.getPosition    ", ""+label.getPosition());
                        //repaired 시작지점
                        for(HashMap<String, Object> list : list_storeDetails) {
                            String[] coordinates_split = list.get("위도경도").toString().split("/");
                            if(String.valueOf(label.getPosition().latitude).equals(coordinates_split[0].toString()) &&
                                    String.valueOf(label.getPosition().longitude).equals(coordinates_split[1].toString())) {
                                //Activity to Activity, 클릭한 점포의 상세 데이터 pass
                                Log.e("@@@@@pass ", ""+list.toString());
                                startActivity(new Intent(MapActivity.this,StoreDetailActivity.class).putExtra("hashMap", list));
                            }
                        }
                        //repaired 끝지점

                        // 마커 클릭시 점포 상세화면 띄워주는 곳

                        //TODO 업종 선택화면에서 상세업종 입력하고 뒤로가기 할 수 있도록 해야 함, 마커 클릭하면 해당 마커가 있는 지역의 청소, 시공업체 뜨도록 마커의 상세 정보가 동적으로 뜨도록 해야함
                        addressEdit.setText("");
                        addressEdit.setFocusable(false);
                        sectors.setBackground(getDrawable(R.drawable.category_border));
                        stores.setBackground(getDrawable(R.drawable.category_border));
                        trading.setBackground(getDrawable(R.drawable.category_border));
                        area.setBackground(getDrawable(R.drawable.category_border));
                        price.setBackground(getDrawable(R.drawable.category_border));
                        parkinglot.setBackground(getDrawable(R.drawable.category_border));

                        editText_category.clearAnimation();
                        confirmBtn.clearAnimation();
                        editText_category.setVisibility(EditText.GONE);
                        confirmBtn.setVisibility(View.GONE);
                        confirmBtn.setImageResource(R.drawable.confirm);
                        editText_category.setText("");
                        price_minimum.setText("");
                        price_maximum.setText("");
                        checkBox_parkingLot.setImageResource(R.drawable.emptybox);

                        confirmBtn_2.setImageResource(R.drawable.confirm);
                        confirmBtn_3.setImageResource(R.drawable.confirm);
                        confirmBtn_4.setImageResource(R.drawable.confirm);
                        confirmBtn_5.setImageResource(R.drawable.confirm);
                        confirmBtn_6.setImageResource(R.drawable.confirm);

                        TextView[] categorys = {category1, category2, category3, category4, category5, category6, category7, category8, category9, category10, category11, category12, category13, category14, category15, category16,
                                category17, category18, category19, category20, category21, category22, category23};

                        for(int i= 0; i < 23; ++i) {
                            categorys[i].setTextColor(getResources().getColor(R.color.black));
                            categorys[i].setBackground(getDrawable(R.drawable.category_detail_border));
                            if(i < 10) {
                                categorys[i].setEnabled(true);
                                categorys[i].clearAnimation();
                            }
                        }
                    }
                });
                // 로딩화면 패널
                handler.removeCallbacks(runnable);
                progressbarFrame.setVisibility(View.GONE);

                kakaomap.setOnCameraMoveEndListener(new KakaoMap.OnCameraMoveEndListener() {
                    @Override
                    public void onCameraMoveEnd(@NonNull KakaoMap kakaoMap, @NonNull CameraPosition position, @NonNull GestureType gestureType) {
                        //지도 중심 좌표
                        currentLatitude = position.getPosition().latitude; //위도
                        currentLongitude = position.getPosition().longitude; //경도

                        kakaomap = kakaoMap;
                        i = 0; //repaired

                        kakaoMap.getZoomLevel();

                        L_Top_Lat_Coordinates = kakaoMap.fromScreenPoint(0, 0).getLatitude();
                        R_Top_Lon_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), 0).getLongitude();
                        L_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(0, mapView.getHeight()).getLongitude();
                        R_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), mapView.getHeight()).getLatitude();

                        if(!(getAddressFromLocation(context, currentLatitude, currentLongitude).equals(currentCoordinates))) {
                            currentCoordinates = getAddressFromLocation(context, currentLatitude, currentLongitude);
                            progressbarFrame.setVisibility(View.VISIBLE);
                            fetchDataFromFirebase(new FirebaseCallback() {
                                @Override
                                public void onCallback(ArrayList<ArrayList<HashMap<String, Object>>> list) {
                                    Final_All_Data = list;

                                    touchListener_stores.GetData(Final_All_Data, kakaoMap);
                                    touchListener_purchase.GetData(Final_All_Data, kakaoMap);
                                    touchListener_area.GetData(Final_All_Data, kakaoMap);
                                    touchListener_price.GetData(Final_All_Data, kakaoMap);

                                    if(!Category_Choice_Change_List_Data.isEmpty()) {
                                        addMarker(Category_Choice_Change_List_Data);
                                    } else {
                                        addMarker(Final_All_Data);
                                    }
                                    handler.removeCallbacks(runnable);
                                    progressbarFrame.setVisibility(View.GONE);
                                }
                            });
                        } else {

                            if(!Category_Choice_Change_List_Data.isEmpty()) {
                                addMarker(Category_Choice_Change_List_Data);
                            } else {
                                addMarker(Final_All_Data);
                            }
                            handler.removeCallbacks(runnable);
                            progressbarFrame.setVisibility(View.GONE);
                        }
                    }
                });
            }
            @Override
            public LatLng getPosition() {
                // 지도 시작 시 위치 좌표를 설정
                return LatLng.from(currentLatitude, currentLongitude);
            }
            @Override
            public int getZoomLevel() {
                // 지도 시작 시 확대/축소 줌 레벨 설정
                return 15;
            }
//            @Override
//            public MapViewInfo getMapViewInfo() {
//                // 지도 시작 시 App 및 MapType 설정
//                return MapViewInfo.from(String.valueOf(MapType.NORMAL));
//            }
            @Override
            public String getViewName() {
                // KakaoMap 의 고유한 이름을 설정
                return "MyFirstMap";
            }
            @Override
            public boolean isVisible() {
                // 지도 시작 시 visible 여부를 설정
                return true;
            }
            @Override
            public String getTag() {
                // KakaoMap 의 tag 을 설정
                return "FirstMapTag";
            }
        });
    }
    void Marker_Remove() {
        // 맵 위에 모든 마커 제거
        LabelManager labelManager = kakaomap.getLabelManager();
        labelManager.removeAllLabelLayer();
    }
    public boolean Stores_or_Purchase_Return_Category_FilteringData() {
        // 카테고리 (점포/매매) 선택된 값에 따라 필터링 된 값 전달
        if(User_Choice_Stores_Data != "-1") {
            return (Store_Name.contains(User_Choice_Stores_Data) && (User_Choice_Purchase_Data == "-1" || Store_Name.contains(User_Choice_Purchase_Data)));
        }
        if(User_Choice_Purchase_Data != "-1") {
            return (Store_Name.contains(User_Choice_Purchase_Data) && (User_Choice_Stores_Data == "-1" || Store_Name.contains(User_Choice_Stores_Data)));
        }
        return true;
    }
    public boolean Area_or_Price_Return_Category_FilteringData(double _Area_Size_Data, double _Price_Data_Money) {
        /*
         * 카테고리 (면적/가격) 선택된 값에 따라 필터링 된 값 전달
         * _Area_Size_Data : 데이터 베이스 안에 있는 면적 값
         * _Price_Data_Money : 데이터 베이스 안에 있는 가격 값
         * WithinAreaSize : 면적에 의해 필터링 된 값
         * WithinPriceRange : 가격에 의해 필터링 된 값
         *
         * 전/월세 일 경우 보증금만 가져오는중 (월세는 가져오지 않음)
         */
        boolean WithinAreaSize = (Area_Size_User_Choice.length == 1) ? (_Area_Size_Data <= Area_Size_User_Choice[0]) :
                (Area_Size_User_Choice[0] < _Area_Size_Data && Area_Size_User_Choice[1] >= _Area_Size_Data);
        boolean WithinPriceRange = (Maney_Data.length != 1) ? (Maney_Data[0] <= _Price_Data_Money && Maney_Data[1] >= _Price_Data_Money) : false;
        if(Area_Size_User_Choice[0] != -1) {
            return (Maney_Data[0] == -1) ? WithinAreaSize : WithinAreaSize && WithinPriceRange;  // 면적
        }
        if(Maney_Data[0] != -1) {
            return (Area_Size_User_Choice[0] == -1) ? WithinPriceRange : WithinAreaSize && WithinPriceRange;  // 가격
        }
        return true;
    }

    private void addMarker1(ArrayList<ArrayList<HashMap<String, Object>>> _list) {
        i = 0; //repaired
        if(!Category_Choice_Change_List_Data.isEmpty()) {
            Category_Choice_Change_List_Data.clear();
        }
        Marker_Remove();
        //repairing
        list_storeDetails.clear();
        for(ArrayList<HashMap<String, Object>> arrayList : _list) {
            ArrayList<HashMap<String, Object>> stores_add_marker_storeList = new ArrayList<>();
            stores_add_marker_storeList.add(arrayList.get(0));
            Store_Name = arrayList.get(0).get("Store Name").toString();
            String Area_Map_Name = Category_Area_Map.get(Store_Name);
            if(Stores_or_Purchase_Return_Category_FilteringData()) {
                for(HashMap<String, Object> stores_arrayList : arrayList) {
                    String S = arrayList.get(0).toString().contains("매매") ? "거래금액(만원)" : "보증금(만원)";
                    if(stores_arrayList.get(Area_Map_Name) != null && stores_arrayList.get(S) != null && stores_arrayList.get("위도경도") != null) {
                        double Price_Data_Money = Double.valueOf(stores_arrayList.get(S).toString().replaceAll("[^\\d]", ""));
                        double Area_Size_Data = Double.valueOf(stores_arrayList.get(Area_Map_Name).toString());
                        String coordinates = stores_arrayList.get("위도경도").toString();
                        String[] coordinates_split = coordinates.split("/");
                        if(Area_or_Price_Return_Category_FilteringData(Area_Size_Data, Price_Data_Money)) {

                            Classification_Marker(coordinates_split[0], coordinates_split[1], R.drawable.marker);

                            //repairing
                            list_storeDetails.add(stores_arrayList);
                        }
                        stores_add_marker_storeList.add(stores_arrayList);
                    }
                }
                Category_Choice_Change_List_Data.add(stores_add_marker_storeList);
            }
        }
        Log.e("@@@addMarer data1", ""+list_storeDetails);
    }
    private void addMarker(ArrayList<ArrayList<HashMap<String, Object>>> _list) {
        i = 0; //repaired
        Marker_Remove();
        //repairing
        list_storeDetails.clear();
        for(ArrayList<HashMap<String, Object>> arrayList : _list) {
            for(HashMap<String, Object> stores_arrayList : arrayList) {
                if(stores_arrayList.get("위도경도") != null) {
                    String coordinates = stores_arrayList.get("위도경도").toString();
                    String[] coordinates_split = coordinates.split("/");

                    Classification_Marker(coordinates_split[0], coordinates_split[1], R.drawable.marker);
                    //repairing
                    list_storeDetails.add(stores_arrayList);
                }
            }
        }
        Log.e("@@@addMarer data", ""+list_storeDetails);
    }
    int i = 0; //repaired
    void Classification_Marker(String Coordinate_Split_Lat, String Coordinate_Split_Lon, int Marker_Image) {
        // 맵뷰 안에 있는 좌표값만 필터링 해서 찍어주는 함수
        //repaired 시작지점
        if(isWithinBounds(Double.valueOf(Coordinate_Split_Lat), Double.valueOf(Coordinate_Split_Lon), L_Top_Lat_Coordinates, R_Top_Lon_Coordinates, L_Bottom_Lat_Coordinates, R_Bottom_Lat_Coordinates)) {
            if(i != 0 ) {
                LabelStyles styles = kakaomap.getLabelManager()
                        .addLabelStyles(LabelStyles.from(LabelStyle.from(Marker_Image)));
                LabelOptions options = LabelOptions.from(LatLng.from(Double.valueOf(Coordinate_Split_Lat),Double.valueOf(Coordinate_Split_Lon)))
                        .setStyles(styles);
                options.setTag(Double.valueOf(Coordinate_Split_Lat) + "," + Double.valueOf(Coordinate_Split_Lon));
                LabelLayer layer = kakaomap.getLabelManager().getLayer();
                layer.addLabel(options);

            }
            else if(i == 0 ){
                LabelStyles styles = kakaomap.getLabelManager()
                        .addLabelStyles(LabelStyles.from(LabelStyle.from(R.drawable.marker_2)));
                LabelOptions options = LabelOptions.from(LatLng.from(Double.valueOf(Coordinate_Split_Lat),Double.valueOf(Coordinate_Split_Lon)))
                        .setStyles(styles);
                options.setTag(Double.valueOf(Coordinate_Split_Lat) + "," + Double.valueOf(Coordinate_Split_Lon));
                LabelLayer layer = kakaomap.getLabelManager().getLayer();
                layer.addLabel(options);
                ++i;
            }
        }
        //repaired 끝지점
    }

    private boolean isWithinBounds(double latitude, double longitude, double L_Top_lat, double R_Top_lon, double L_Bottom_lon, double R_Bottom_lat) {
        return L_Top_lat >= latitude && R_Bottom_lat <= latitude && R_Top_lon >= longitude && L_Bottom_lon <= longitude;
    }
    public void setSectors() {  //업종 선택 시 실행 메소드
        category_detail_sectors = findViewById(R.id.category_detail_sectors); //업종 선택 화면 레이아웃
        category1 = findViewById(R.id.category1);    //과학·기술
        category2 = findViewById(R.id.category2);    //교육
        category3 = findViewById(R.id.category3);    //보건의료
        category4 = findViewById(R.id.category4);    //부동산
        category5 = findViewById(R.id.category5);    //소매
        category6 = findViewById(R.id.category6);    //수리·개인
        category7 = findViewById(R.id.category7);    //숙박
        category8 = findViewById(R.id.category8);    //시설관리·임대
        category9 = findViewById(R.id.category9);    //예술·스포츠
        category10 = findViewById(R.id.category10);  //음식
        editText_category = findViewById(R.id.editText_category);  //상세 업종 입력 필드
        confirmBtn = findViewById(R.id.confirmBtn);
        sectors = findViewById(R.id.sectors); //업종

        TouchListener_Sectors touchListener_Sectors = new TouchListener_Sectors();

        category1.setOnTouchListener(touchListener_Sectors);
        category2.setOnTouchListener(touchListener_Sectors);
        category3.setOnTouchListener(touchListener_Sectors);
        category4.setOnTouchListener(touchListener_Sectors);
        category5.setOnTouchListener(touchListener_Sectors);
        category6.setOnTouchListener(touchListener_Sectors);
        category7.setOnTouchListener(touchListener_Sectors);
        category8.setOnTouchListener(touchListener_Sectors);
        category9.setOnTouchListener(touchListener_Sectors);
        category10.setOnTouchListener(touchListener_Sectors);
        confirmBtn.setOnTouchListener(touchListener_Sectors);
        sectors.setOnTouchListener(touchListener_Sectors);

        //업종 선택 카테고리 레이아웃 visible false
        category_detail_sectors.setVisibility(ConstraintLayout.GONE);

        //입력 필드 초기에 안보이게 (왼쪽으로 슬라이드하면 보여야 함)
        editText_category.setVisibility(EditText.GONE);
        confirmBtn.setVisibility(EditText.GONE);

        //오른쪽 -> 왼쪽 슬라이딩 변수 (카테고리)
        animSlide_RightToLeft = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right_to_left);
        //왼쪽 -> 오른쪽 슬라이딩 변수 (카테고리)
        animSlide_LeftToRight = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_left_to_right);
        //오른쪽 -> 왼쪽 슬라이딩 함수 (상세 업종 입력 필드)
        animSlide_RightToLeft_2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_right_to_left_2);
    }
    class TouchListener_Sectors implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    switch (view.getId()) {
                        case R.id.category1:  //과학·기술
                            category1.setTextColor(Color.WHITE);
                            category1.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            String[] List1 = { "건물 및 토목 엔지니어링 서비스업", "건축 설계 및 관련 서비스업", "경영 컨설팅업", "공인노무사", "공인회계사",
                                    "광고 대행업", "광고 매체 판매업", "광고물 설계/제작업", "기타 광고 관련 서비스업", "기타 법무관련 서비스업",
                                    "기타 엔지니어링 서비스업", "기타 회계 관련 서비스업", "도시 계획 및 조경 설계 서비스업", "동물병원", "명함/간판/광고물 제작",
                                    "번역/통역 서비스업", "법무사", "변리사", "변호사", "사업/무형 재산권 중개업",
                                    "사진촬영업", "세무사", "시각 디자인업", "시장 조사 및 여론 조사업", "옥외/전시 광고 대행업",
                                    "인테리어 디자인업", "제품 디자인업", "패션/섬유/기타 전문 디자인업", "행정사", "환경 관련 엔지니어링 서비스업" };
                            editText_category.setAdapter(new ArrayAdapter<String>(MapActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, List1));
                            if(!Category_Choice_Change_List_Data.isEmpty()) {
                                addMarker(Category_Choice_Change_List_Data);
                            } else {
                                addMarker(Final_All_Data);
                            }
                            break;
                        case R.id.category2:  //교육
                            category2.setTextColor(Color.WHITE);
                            category2.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            String[] List2 = { "교육컨설팅업", "그 외 기타 교육기관", "기타 교육지원 서비스업", "기타 기술/직업 훈련학원", "기타 예술/스포츠 교육기관",
                                    "레크리에이션 교육기관", "미술학원", "사회교육시설", "외국어학원", "요가/필라테스 학원",
                                    "운전학원", "음악학원", "입시·교과학원", "전문자격/고시학원", "직원 훈련기관",
                                    "청소년 수련시설", "컴퓨터 학원", "태권도/무술학원" };
                            editText_category.setAdapter(new ArrayAdapter<String>(MapActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, List2));

                            if(!Category_Choice_Change_List_Data.isEmpty()) {
                                addMarker(Category_Choice_Change_List_Data);
                            } else {
                                addMarker(Final_All_Data);
                            }
                            break;
                        case R.id.category3:  //보건의료
                            category3.setTextColor(Color.WHITE);
                            category3.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            String[] List3 = { "기타 의원", "내과/소아과 의원", "방사선 진단/병리 검사 의원", "산부인과 의원", "성형외과 의원",
                                    "신경/정신과 의원", "안과 의원", "외과 의원", "요양병원", "유사 의료업",
                                    "이비인후과 의원", "일반병원", "종합병원" };
                            editText_category.setAdapter(new ArrayAdapter<String>(MapActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, List3));
                            if(!Category_Choice_Change_List_Data.isEmpty()) {
                                addMarker(Category_Choice_Change_List_Data);
                            } else {
                                addMarker(Final_All_Data);
                            }
                            break;
                        case R.id.category4:  //부동산
                            category4.setTextColor(Color.WHITE);
                            category4.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            String[] List4 = { "부동산 중개/대리업" };
                            editText_category.setAdapter(new ArrayAdapter<String>(MapActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, List4));
                            if(!Category_Choice_Change_List_Data.isEmpty()) {
                                addMarker(Category_Choice_Change_List_Data);
                            } else {
                                addMarker(Final_All_Data);
                            }
                            break;
                        case R.id.category5:  //소매
                            category5.setTextColor(Color.WHITE);
                            category5.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            String[] List5 = {"가구 소매업", "가발 소매업", "가방 소매업", "가스 충전소", "가전제품 소매업",
                                    "가정용 연료 소매업", "가축 사료 소매업", "건강보조식품 소매업", "건설/건축자재 소매업", "건어물/젓갈 소매업",
                                    "곡물/곡분 소매업", "그 외 기타 상품 전문 소매업", "그 외 기타 종합 소매업", "기념품점", "기타 건설/건축자재 소매업",
                                    "기타 의류 소매업", "꽃집", "남성 의류 소매업", "담배/전자담배 소매업", "모터사이클 및 부품 소매업",
                                    "문구/회화용품 소매업", "반찬/식료품 소매업", "벽지/장판/마루 소매업", "사무기기 소매업", "사진기/기타 광학기기 소매업",
                                    "생수/음료 소매업", "서점", "수산물 소매업", "슈퍼마켓", "시계/귀금속 소매업",
                                    "신발 소매업", "실/섬유제품 소매업", "아이스크림 할인점", "악기 소매업", "안경렌즈 소매업",
                                    "애완동물/애완용품 소매업", "액세서리/잡화 소매업", "약국", "얼음 소매업", "여성 의류 소매업",
                                    "예술품 소매업", "우유 소매업", "운동용품 소매업", "유아용 의류 소매업", "음반/비디오물 소매업",
                                    "의료기기 소매업", "자동차 부품 소매업", "자전거 소매업", "장난감 소매업", "전기용품/조명장치 소매업",
                                    "정육점", "주류 소매업", "주방/가정용품 소매업", "주유소", "중고 상품 소매업",
                                    "채소/과일 소매업", "철물/공구 소매업", "침구류/커튼 소매업", "컴퓨터/소프트웨어 소매업", "타이어 소매업",
                                    "편의점", "한복 소매업", "핸드폰 소매업", "화장품 소매업"};
                            editText_category.setAdapter(new ArrayAdapter<String>(MapActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, List5));
                            if(!Category_Choice_Change_List_Data.isEmpty()) {
                                addMarker(Category_Choice_Change_List_Data);
                            } else {
                                addMarker(Final_All_Data);
                            }
                            break;
                        case R.id.category6:  //수리·개인
                            category6.setTextColor(Color.WHITE);
                            category6.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            String[] List6 = { "가전제품 수리점", "가죽/가방/신발 수선업", "결혼 상담 서비스업", "그 외 기타 개인/가정용품 수리업", "네일숍",
                                    "마사지/안마", "모터사이클 수리업", "목욕탕/사우나", "미용실", "세탁소",
                                    "셀프 빨래방", "시계/귀금속/악기 수리업", "예식장업", "의류/이불 수선업", "자동차 세차장",
                                    "자동차 정비소", "장례식장", "체형/비만 관리", "컴퓨터/노트북/프린터 수리업", "피부 관리실",
                                    "핸드폰/통신장비 수리업", "화장터/묘지/납골당" };
                            editText_category.setAdapter(new ArrayAdapter<String>(MapActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, List6));
                            if(!Category_Choice_Change_List_Data.isEmpty()) {
                                addMarker(Category_Choice_Change_List_Data);
                            } else {
                                addMarker(Final_All_Data);
                            }
                            break;
                        case R.id.category7:  //숙박
                            category7.setTextColor(Color.WHITE);
                            category7.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            String[] List7 = { "그 외 기타 숙박업", "기숙사/고시원", "여관/모텔", "캠핑/글램핑", "펜션",
                                    "호텔/리조트" };
                            editText_category.setAdapter(new ArrayAdapter<String>(MapActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, List7));
                            if(!Category_Choice_Change_List_Data.isEmpty()) {
                                addMarker(Category_Choice_Change_List_Data);
                            } else {
                                addMarker(Final_All_Data);
                            }
                            break;
                        case R.id.category8:  //시설관리·임대
                            category8.setTextColor(Color.WHITE);
                            category8.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            String[] List8 = { "건설기계/장비 대여업", "건축물 일반 청소업", "고용 알선업", "기타 개인/가정용품 대여업", "기타 사무 지원 서비스업",
                                    "기타 산업용 기계/장비 대여업", "기타 여행 보조/예약 서비스업", "기타 운송장비 대여업", "만화방", "복사업",
                                    "사업시설 유지·관리 서비스업", "산업설비, 운송장비 및 공공장소 청소업", "상용 인력 공급 및 인사관리 서비스업", "소독, 구충 및 방제 서비스업", "스포츠/레크리에이션 용품 대여업",
                                    "여행사", "음반/비디오물 대여업", "의류 대여업", "임시/일용 인력 공급업", "자동차 대여업",
                                    "전시/컨벤션/행사 대행 서비스업", "조경 유지·관리 서비스업", "컴퓨터/사무기기 대여업", "포장/충전업" };
                            editText_category.setAdapter(new ArrayAdapter<String>(MapActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, List8));
                            if(!Category_Choice_Change_List_Data.isEmpty()) {
                                addMarker(Category_Choice_Change_List_Data);
                            } else {
                                addMarker(Final_All_Data);
                            }
                            break;
                        case R.id.category9:  //예술·스포츠
                            category9.setTextColor(Color.WHITE);
                            category9.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            String[] List9 = { "PC방", "골프 연습장", "기타 스포츠시설 운영업", "기타 오락관련 서비스업", "기타 오락장",
                                    "낚시터 운영업", "노래방", "당구장", "독서실/스터디 카페", "바둑/장기/체스 경기 운영업",
                                    "복권 발행/판매업", "볼링장", "비디오방", "수상/해양 레저업", "수영장",
                                    "스쿼시/라켓볼장", "전자 게임장", "종합 스포츠시설", "탁구장", "테니스장",
                                    "헬스장" };
                            editText_category.setAdapter(new ArrayAdapter<String>(MapActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, List9));
                            if(!Category_Choice_Change_List_Data.isEmpty()) {
                                addMarker(Category_Choice_Change_List_Data);
                            } else {
                                addMarker(Final_All_Data);
                            }
                            break;
                        case R.id.category10:  //음식
                            category10.setTextColor(Color.WHITE);
                            category10.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            String[] List10 = { "경양식", "곱창 전골/구이", "구내식당", "국/탕/찌개류", "국수/칼국수",
                                    "그 외 기타 간이 음식점", "기타 동남아식 전문", "기타 서양식 음식점", "기타 일식 음식점", "기타 한식 음식점",
                                    "김밥/만두/분식", "냉면/밀면", "닭/오리고기 구이/찜", "돼지고기 구이/찜", "떡/한과",
                                    "마라탕/훠궈", "무도 유흥 주점", "백반/한정식", "버거", "베트남식 전문",
                                    "복 요리 전문", "분류 안된 외국식 음식점", "뷔페", "빵/도넛", "생맥주 전문",
                                    "소고기 구이/찜", "아이스크림/빙수", "요리 주점", "일반 유흥 주점", "일식 면 요리",
                                    "일식 카레/돈가스/덮밥", "일식 회/초밥", "전/부침개", "족발/보쌈", "중국집",
                                    "치킨", "카페", "토스트/샌드위치/샐러드", "파스타/스테이크", "패밀리레스토랑",
                                    "피자", "해산물 구이/찜", "횟집" };
                            editText_category.setAdapter(new ArrayAdapter<String>(MapActivity.this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, List10));
                            if(!Category_Choice_Change_List_Data.isEmpty()) {
                                addMarker(Category_Choice_Change_List_Data);
                            } else {
                                addMarker(Final_All_Data);
                            }
                            break;
                        case R.id.confirmBtn:
                            confirmBtn.setImageResource(R.drawable.confirmclicked);
                            if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                            }
                            break;
                        case R.id.sectors:
                            sectors.setBackground(getDrawable(R.drawable.category_border_clicked));
                            break;
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    switch (view.getId()) {
                        case R.id.confirmBtn:
                            confirmBtn.setBackground(getDrawable(R.drawable.confirm));
                            category_detail_sectors.setVisibility(ConstraintLayout.GONE);
                            confirmBtn.setImageResource(R.drawable.confirmclicked);
                            sectors.setBackground(getDrawable(R.drawable.category_border_complete));
                            break;
                        case R.id.sectors:
                            category_detail_sectors.setVisibility(ConstraintLayout.VISIBLE);
                            //repaired
                            addressEdit.setText("");
                            addressEdit.setFocusable(false);
                            editText_category.clearAnimation();
                            confirmBtn.clearAnimation();
                            editText_category.setVisibility(EditText.GONE);
                            confirmBtn.setVisibility(View.GONE);
                            confirmBtn.setImageResource(R.drawable.confirm);
                            editText_category.setText("");
                            TextView[] categorys = {category1, category2, category3, category4, category5, category6, category7, category8, category9, category10};
                            for(int i= 0; i < 10; ++i) {
                                categorys[i].setTextColor(getResources().getColor(R.color.black));
                                categorys[i].setBackground(getDrawable(R.drawable.category_detail_border));
                                categorys[i].clearAnimation();
                                categorys[i].setEnabled(true);
                            }
                            break;
                        default:
                            //카테고리 모두 왼쪽으로 슬라이드
                            category1.startAnimation(animSlide_RightToLeft);
                            category2.startAnimation(animSlide_RightToLeft);
                            category3.startAnimation(animSlide_RightToLeft);
                            category4.startAnimation(animSlide_RightToLeft);
                            category5.startAnimation(animSlide_RightToLeft);
                            category6.startAnimation(animSlide_RightToLeft);
                            category7.startAnimation(animSlide_RightToLeft);
                            category8.startAnimation(animSlide_RightToLeft);
                            category9.startAnimation(animSlide_RightToLeft);
                            category10.startAnimation(animSlide_RightToLeft);

                            //상세 업종 입력 필드, 저장 버튼 왼쪽으로 슬라이드 (보이게)
                            editText_category.startAnimation(animSlide_RightToLeft_2);
                            confirmBtn.startAnimation(animSlide_RightToLeft_2);
                            editText_category.setVisibility(EditText.VISIBLE);
                            confirmBtn.setVisibility(EditText.VISIBLE);

                            //카테고리 모두 터치 비활성화
                            category1.setEnabled(false);
                            category2.setEnabled(false);
                            category3.setEnabled(false);
                            category4.setEnabled(false);
                            category5.setEnabled(false);
                            category6.setEnabled(false);
                            category7.setEnabled(false);
                            category8.setEnabled(false);
                            category9.setEnabled(false);
                            category10.setEnabled(false);
                            break;
                    }
                    return true;
            }
            return false;
        }
    }
    public void setStores() {   //점포 선택 시 실행 메소드
        category_detail_stores = findViewById(R.id.category_detail_stores); //점포 선택 화면 레이아웃
        category11 = findViewById(R.id.category11);
        category12 = findViewById(R.id.category12);
        category13 = findViewById(R.id.category13);
        category14 = findViewById(R.id.category14);
        category15 = findViewById(R.id.category15);
        category16 = findViewById(R.id.category16);
        category17 = findViewById(R.id.category17);
        confirmBtn_2 = findViewById(R.id.confirmBtn_2);
        stores = findViewById(R.id.stores);

        category11.setOnTouchListener(touchListener_stores);
        category12.setOnTouchListener(touchListener_stores);
        category13.setOnTouchListener(touchListener_stores);
        category14.setOnTouchListener(touchListener_stores);
        category15.setOnTouchListener(touchListener_stores);
        category16.setOnTouchListener(touchListener_stores);
        category17.setOnTouchListener(touchListener_stores);
        stores.setOnTouchListener(touchListener_stores);
        confirmBtn_2.setOnTouchListener(touchListener_stores);

        //점포 선택 카테고리 레이아웃 visible false
        category_detail_stores.setVisibility(ConstraintLayout.GONE);
    }
    class TouchListener_Stores implements View.OnTouchListener {
        ArrayList<ArrayList<HashMap<String, Object>>> list;
        KakaoMap kakaoMap;
        public void GetData(ArrayList<ArrayList<HashMap<String, Object>>> _list, KakaoMap _kaKakaoMap) {
            kakaoMap = _kaKakaoMap;
        }
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            list = Final_All_Data;

            L_Top_Lat_Coordinates = kakaoMap.fromScreenPoint(0, 0).getLatitude();
            R_Top_Lon_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), 0).getLongitude();
            L_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(0, mapView.getHeight()).getLongitude();
            R_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), mapView.getHeight()).getLatitude();

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    switch (view.getId()) {
                        case R.id.category11: //아파트
                            category11.setTextColor(Color.WHITE);
                            category11.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            User_Choice_Stores_Data = "아파트";
                            addMarker1(list);
                            break;
                        case R.id.category12:  //연립/다세대
                            category12.setTextColor(Color.WHITE);
                            category12.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            User_Choice_Stores_Data = "연립다세대";
                            addMarker1(list);
                            break;
                        case R.id.category13:  //단독/다가구
                            category13.setTextColor(Color.WHITE);
                            category13.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            User_Choice_Stores_Data = "단독다가구";
                            addMarker1(list);
                            break;
                        case R.id.category14:  //오피스텔
                            category14.setTextColor(Color.WHITE);
                            category14.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            User_Choice_Stores_Data = "오피스텔";
                            addMarker1(list);
                            break;
                        case R.id.category15:  //분양/입주권
                            category15.setTextColor(Color.WHITE);
                            category15.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            User_Choice_Stores_Data = "분양입주권";
                            addMarker1(list);
                            break;
                        case R.id.category16:  //상권/업무용
                            category16.setTextColor(Color.WHITE);
                            category16.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            User_Choice_Stores_Data = "상업업무용";
                            addMarker1(list);
                            break;
                        case R.id.category17:  //토지
                            category17.setTextColor(Color.WHITE);
                            category17.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            User_Choice_Stores_Data = "토지";
                            addMarker1(list);
                            break;
                        case R.id.confirmBtn_2:
                            confirmBtn_2.setImageResource(R.drawable.confirmclicked);
                            break;
                        case R.id.stores:
                            stores.setBackground(getDrawable(R.drawable.category_border_clicked));
                            break;
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    switch (view.getId()) {
                        case R.id.confirmBtn_2:
                            confirmBtn_2.setBackground(getDrawable(R.drawable.confirm));
                            category_detail_stores.setVisibility(ConstraintLayout.GONE);
                            confirmBtn_2.setImageResource(R.drawable.confirmclicked);
                            stores.setBackground(getDrawable(R.drawable.category_border_complete));
                            break;
                        case R.id.stores:
                            category_detail_stores.setVisibility(ConstraintLayout.VISIBLE);
                            //repaired
                            confirmBtn_2.setImageResource(R.drawable.confirm);
                            TextView[] categorys = {category11, category12, category13, category14, category15, category16, category17};
                            for(int i= 0; i < 7; ++i) {
                                categorys[i].setTextColor(getResources().getColor(R.color.black));
                                categorys[i].setBackground(getDrawable(R.drawable.category_detail_border));
                            }
                            break;
                    }
                    return true;
            }
            return false;
        }
    }

    public void setPurchase() {   //구매방식 선택 시 실행 메소드
        category_detail_purchase = findViewById(R.id.category_detail_purchase); //점포 선택 화면 레이아웃
        category18 = findViewById(R.id.category18);
        category19 = findViewById(R.id.category19);

        confirmBtn_3 = findViewById(R.id.confirmBtn_3);
        trading = findViewById(R.id.trading);

        category18.setOnTouchListener(touchListener_purchase);
        category19.setOnTouchListener(touchListener_purchase);

        trading.setOnTouchListener(touchListener_purchase);
        confirmBtn_3.setOnTouchListener(touchListener_purchase);

        //매매 선택 카테고리 레이아웃 visible false
        category_detail_purchase.setVisibility(ConstraintLayout.GONE);

    }

    class TouchListener_Purchase implements View.OnTouchListener {
        ArrayList<ArrayList<HashMap<String, Object>>> list;
        KakaoMap kakaoMap;
        public void GetData(ArrayList<ArrayList<HashMap<String, Object>>> _list, KakaoMap _kaKakaoMap) {
            kakaoMap = _kaKakaoMap;
        }
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            list = Final_All_Data;

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    switch (view.getId()) {
                        case R.id.category18: //매매
                            category18.setTextColor(Color.WHITE);
                            category18.setBackground(getDrawable(R.drawable.category_detail_border_clicked));

                            L_Top_Lat_Coordinates = kakaoMap.fromScreenPoint(0, 0).getLatitude();
                            R_Top_Lon_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), 0).getLongitude();
                            L_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(0, mapView.getHeight()).getLongitude();
                            R_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), mapView.getHeight()).getLatitude();

                            User_Choice_Purchase_Data = "매매";
                            addMarker1(list);
                            break;
                        case R.id.category19:  //전월세
                            category19.setTextColor(Color.WHITE);
                            category19.setBackground(getDrawable(R.drawable.category_detail_border_clicked));

                            L_Top_Lat_Coordinates = kakaoMap.fromScreenPoint(0, 0).getLatitude();
                            R_Top_Lon_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), 0).getLongitude();
                            L_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(0, mapView.getHeight()).getLongitude();
                            R_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), mapView.getHeight()).getLatitude();

                            User_Choice_Purchase_Data = "전월세";
                            addMarker1(list);
                            break;
                        case R.id.confirmBtn_3:
                            confirmBtn_3.setImageResource(R.drawable.confirmclicked);

                            break;
                        case R.id.trading:
                            trading.setBackground(getDrawable(R.drawable.category_border_clicked));
                            break;
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    switch (view.getId()) {
                        case R.id.confirmBtn_3:
                            confirmBtn_3.setBackground(getDrawable(R.drawable.confirm));
                            category_detail_purchase.setVisibility(ConstraintLayout.GONE);
                            confirmBtn_3.setImageResource(R.drawable.confirmclicked);
                            trading.setBackground(getDrawable(R.drawable.category_border_complete));
                            break;
                        case R.id.trading:
                            category_detail_purchase.setVisibility(ConstraintLayout.VISIBLE);
                            //repaired
                            confirmBtn_3.setImageResource(R.drawable.confirm);
                            TextView[] categorys = {category18, category19};
                            for(int i= 0; i < 2; ++i) {
                                categorys[i].setTextColor(getResources().getColor(R.color.black));
                                categorys[i].setBackground(getDrawable(R.drawable.category_detail_border));
                            }
                            break;
                    }
                    return true;
            }
            return false;
        }
    }

    public void setArea() {   //구매방식 선택 시 실행 메소드
        category_detail_area = findViewById(R.id.category_detail_area); //점포 선택 화면 레이아웃
        category20 = findViewById(R.id.category20);
        category21 = findViewById(R.id.category21);
        category22 = findViewById(R.id.category22);
        category23 = findViewById(R.id.category23);


        confirmBtn_4 = findViewById(R.id.confirmBtn_4);
        area = findViewById(R.id.area);

        category20.setOnTouchListener(touchListener_area);
        category21.setOnTouchListener(touchListener_area);
        category22.setOnTouchListener(touchListener_area);
        category23.setOnTouchListener(touchListener_area);

        area.setOnTouchListener(touchListener_area);
        confirmBtn_4.setOnTouchListener(touchListener_area);

        //평형 선택 카테고리 레이아웃 visible false
        category_detail_area.setVisibility(ConstraintLayout.GONE);
    }

    class TouchListener_Area implements View.OnTouchListener {
        ArrayList<ArrayList<HashMap<String, Object>>> list;
        KakaoMap kakaoMap;
        public void GetData(ArrayList<ArrayList<HashMap<String, Object>>> _list, KakaoMap _kaKakaoMap) {
            kakaoMap = _kaKakaoMap;
        }

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            list =  Final_All_Data;

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    switch (view.getId()) {
                        case R.id.category20:
                            category20.setTextColor(Color.WHITE);
                            category20.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            Area_Size_User_Choice = new int[] {60};

                            L_Top_Lat_Coordinates = kakaoMap.fromScreenPoint(0, 0).getLatitude();
                            R_Top_Lon_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), 0).getLongitude();
                            L_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(0, mapView.getHeight()).getLongitude();
                            R_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), mapView.getHeight()).getLatitude();

                            addMarker1(list);
                            break;
                        case R.id.category21:
                            category21.setTextColor(Color.WHITE);
                            category21.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            Area_Size_User_Choice = new int[] {60, 85};

                            L_Top_Lat_Coordinates = kakaoMap.fromScreenPoint(0, 0).getLatitude();
                            R_Top_Lon_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), 0).getLongitude();
                            L_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(0, mapView.getHeight()).getLongitude();
                            R_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), mapView.getHeight()).getLatitude();

                            addMarker1(list);
                            break;
                        case R.id.category22:
                            category22.setTextColor(Color.WHITE);
                            category22.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            Area_Size_User_Choice = new int[] {85, 102};

                            L_Top_Lat_Coordinates = kakaoMap.fromScreenPoint(0, 0).getLatitude();
                            R_Top_Lon_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), 0).getLongitude();
                            L_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(0, mapView.getHeight()).getLongitude();
                            R_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), mapView.getHeight()).getLatitude();

                            addMarker1(list);
                            break;
                        case R.id.category23:
                            category23.setTextColor(Color.WHITE);
                            category23.setBackground(getDrawable(R.drawable.category_detail_border_clicked));
                            Area_Size_User_Choice = new int[] {102, 135};

                            L_Top_Lat_Coordinates = kakaoMap.fromScreenPoint(0, 0).getLatitude();
                            R_Top_Lon_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), 0).getLongitude();
                            L_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(0, mapView.getHeight()).getLongitude();
                            R_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), mapView.getHeight()).getLatitude();

                            addMarker1(list);
                            break;
                        case R.id.confirmBtn_4:
                            confirmBtn_4.setImageResource(R.drawable.confirmclicked);
                            if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                            }
                            break;
                        case R.id.area:
                            area.setBackground(getDrawable(R.drawable.category_border_clicked));
                            break;
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    switch (view.getId()) {
                        case R.id.confirmBtn_4:
                            confirmBtn_4.setBackground(getDrawable(R.drawable.confirm));
                            category_detail_area.setVisibility(ConstraintLayout.GONE);
                            confirmBtn_4.setImageResource(R.drawable.confirmclicked);
                            area.setBackground(getDrawable(R.drawable.category_border_complete));

                            break;
                        case R.id.area:
                            category_detail_area.setVisibility(ConstraintLayout.VISIBLE);
                            //repaired
                            confirmBtn_4.setImageResource(R.drawable.confirm);
                            TextView[] categorys = {category20, category21, category22, category23};
                            for(int i= 0; i < 4; ++i) {
                                categorys[i].setTextColor(getResources().getColor(R.color.black));
                                categorys[i].setBackground(getDrawable(R.drawable.category_detail_border));
                            }
                            break;
                    }
                    return true;
            }
            return false;
        }

    }

    //여기
    public void setPrice() {   //구매방식 선택 시 실행 메소드
        category_detail_price = findViewById(R.id.category_detail_price); //점포 선택 화면 레이아웃
        price_minimum = findViewById(R.id.price_minimum);
        price_maximum = findViewById(R.id.price_maximum);

        confirmBtn_5 = findViewById(R.id.confirmBtn_5);
        price = findViewById(R.id.price);

        price.setOnTouchListener(touchListener_price);
        confirmBtn_5.setOnTouchListener(touchListener_price);

        //평형 선택 카테고리 레이아웃 visible false
        category_detail_price.setVisibility(ConstraintLayout.GONE);
    }
    class TouchListener_Price implements View.OnTouchListener {
        int Count = 0;
        ArrayList<ArrayList<HashMap<String, Object>>> list;
        KakaoMap kakaoMap;
        public void GetData(ArrayList<ArrayList<HashMap<String, Object>>> _list, KakaoMap _kaKakaoMap) {
            kakaoMap = _kaKakaoMap;
        }
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {

            list =  Final_All_Data;

            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    switch (view.getId()) {
                        case R.id.confirmBtn_5:
                            ++Count;
                            confirmBtn_5.setImageResource(R.drawable.confirmclicked);
                            if (getCurrentFocus() != null && getCurrentFocus() instanceof EditText) {
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
                            }
                            Maney_Data = new long[] {Integer.parseInt(price_minimum.getText().toString()), Integer.parseInt(price_maximum.getText().toString())};

                            L_Top_Lat_Coordinates = kakaoMap.fromScreenPoint(0, 0).getLatitude();
                            R_Top_Lon_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), 0).getLongitude();
                            L_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(0, mapView.getHeight()).getLongitude();
                            R_Bottom_Lat_Coordinates = kakaoMap.fromScreenPoint(mapView.getWidth(), mapView.getHeight()).getLatitude();

                            addMarker1(list);

                            break;
                        case R.id.price:
                            price.setBackground(getDrawable(R.drawable.category_border_clicked));
                            break;
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    switch (view.getId()) {
                        case R.id.confirmBtn_5:
                            confirmBtn_5.setBackground(getDrawable(R.drawable.confirm));
                            category_detail_price.setVisibility(ConstraintLayout.GONE);
                            confirmBtn_5.setImageResource(R.drawable.confirmclicked);
                            price.setBackground(getDrawable(R.drawable.category_border_complete));
                            break;
                        case R.id.price:
                            category_detail_price.setVisibility(ConstraintLayout.VISIBLE);
                            //repaired
                            confirmBtn_5.setImageResource(R.drawable.confirm);
                            price_minimum.setText("");
                            price_maximum.setText("");
                            break;
                    }
                    return true;
            }
            return false;
        }
    }
    public void setParkinglot() {   //주차장 선택 시 실행 메소드
        category_detail_parkingLot = findViewById(R.id.category_detail_parkingLot); //점포 선택 화면 레이아웃
        checkBox_parkingLot = findViewById(R.id.emptyBox);
        confirmBtn_6 = findViewById(R.id.confirmBtn_6);
        parkinglot = findViewById(R.id.parkinglot);

        TouchListener_ParkingLot touchListener_parkingLot = new TouchListener_ParkingLot();

        parkinglot.setOnTouchListener(touchListener_parkingLot);
        checkBox_parkingLot.setOnTouchListener(touchListener_parkingLot);
        confirmBtn_6.setOnTouchListener(touchListener_parkingLot);

        //평형 선택 카테고리 레이아웃 visible false
        category_detail_parkingLot.setVisibility(ConstraintLayout.GONE);
    }

    class TouchListener_ParkingLot implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    switch (view.getId()) {
                        case R.id.confirmBtn_6:
                            confirmBtn_6.setImageResource(R.drawable.confirmclicked);
                            break;
                        case R.id.parkinglot:
                            parkinglot.setBackground(getDrawable(R.drawable.category_border_clicked));
                            break;
                        case R.id.emptyBox:
                            if (flag_checkbox_parkingLot == true) {
                                checkBox_parkingLot.setImageResource(R.drawable.fullbox);
                                flag_checkbox_parkingLot = false;
                            } else {
                                checkBox_parkingLot.setImageResource(R.drawable.emptybox);
                                flag_checkbox_parkingLot = true;
                            }
                    }
                    return true;
                case MotionEvent.ACTION_UP:
                    switch (view.getId()) {
                        case R.id.confirmBtn_6:
                            confirmBtn_6.setBackground(getDrawable(R.drawable.confirm));
                            category_detail_parkingLot.setVisibility(ConstraintLayout.GONE);
                            confirmBtn_6.setImageResource(R.drawable.confirmclicked);
                            parkinglot.setBackground(getDrawable(R.drawable.category_border_complete));
                            break;
                        case R.id.parkinglot:
                            category_detail_parkingLot.setVisibility(ConstraintLayout.VISIBLE);
                            //repaired
                            confirmBtn_6.setImageResource(R.drawable.confirm);
                            checkBox_parkingLot.setImageResource(R.drawable.emptybox);
                            break;
                    }
                    return true;
            }
            return false;
        }
    }
}