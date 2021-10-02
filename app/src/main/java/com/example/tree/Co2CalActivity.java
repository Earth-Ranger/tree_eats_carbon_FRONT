package com.example.tree;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tree.Main.DataModels_Main;
import com.example.tree.Main.MainData_response;
import com.google.android.gms.maps.model.LatLng;
import com.example.tree.Main.MainTreeActivity;
import com.example.tree.Main.RetrofitAPI;

import org.mozilla.javascript.tools.jsc.Main;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Co2CalActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Context context = this;

    private final String BASE_URL = "http://180.230.121.23/";
    private RetrofitAPI mMyAPI;

    //로그캣 사용 설정
    private static final String TAG = "Co2CalActivity";


    Button btnKor3Loc, btnKor2Loc, d_btn,give_water_btn;
    EditText editText, editText2;
    TextView text1 = null;
    double distance;
    int check;
    String meter;
    Location location1 = new Location("point 1");
    Location location2 = new Location("point 2");
    LatLng curPoint1;
    LatLng curPoint2;
    DrawerLayout drawerLayout;

    //Co2배출량
    float emission=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_co2_cal);

        drawerLayout=findViewById(R.id.drawer_layout);
        //권한 설정
        //checkDangerousPermissions();
        initMyAPI(BASE_URL);
        //객체 초기화
        editText = findViewById(R.id.editText);
        btnKor2Loc = findViewById(R.id.button2);
        editText2 = findViewById(R.id.editText2);
        btnKor3Loc = findViewById(R.id.button3);
        text1 = (TextView) findViewById(R.id.distance);
        d_btn = findViewById(R.id.d_btn);
        give_water_btn=findViewById(R.id.give_water_btn);


        btnKor2Loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().length() > 0) {
                    location1 = getLocationFromAddress(getApplicationContext(), editText.getText().toString());
                    curPoint1 = new LatLng(location1.getLatitude(), location1.getLongitude());
                    location1.setLatitude(curPoint1.latitude);
                    location1.setLongitude(curPoint1.longitude);
                }
            }
        });

        btnKor3Loc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText2.getText().toString().length() > 0) {
                    location2 = getLocationFromAddress(getApplicationContext(), editText2.getText().toString());
                    curPoint2 = new LatLng(location2.getLatitude(), location2.getLongitude());
                    location2.setLatitude(curPoint2.latitude);
                    location2.setLongitude(curPoint2.longitude);
                }
            }
        });

        d_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distance = location1.distanceTo(location2);
                distance = (float) Math.round(distance * 0.001);
                meter = Double.toString(distance);
                Log.d(TAG, "DistanceBetweenPointAandB: " + distance);
                text1.setText("이동 거리(km) : " + meter + "km");
            }
        });
        //체크박스
        CheckBox checkBox1 = (CheckBox) findViewById(R.id.check1) ;
        CheckBox checkBox2 = (CheckBox) findViewById(R.id.check2) ;
        CheckBox checkBox3 = (CheckBox) findViewById(R.id.check3) ;
        checkBox1.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                    check=0;
                }
            }
        });
        checkBox2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                    check=1;
                }
            }
        });
        checkBox3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                    check=2;
                }
            }
        });

        give_water_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                    Log.d(TAG, "POST");
                    //RetrofitAPI postAPI = retrofit.create(RetrofitAPI.class);
                    DataModels_Main data=new DataModels_Main(distance,check);
               /* RetrofitAPI result=mMyAPI;
                result.responsePost(distance, check).enqueue(new Callback<DataModels_Main>() {
                    @Override
                    public void onResponse(@NonNull Call<DataModels_Main> call, @NonNull Response<DataModels_Main> response) {
                        if(response.isSuccessful()) {  // 조회성공
                            DataModels_Main res = response.body();
                            Log.d("총 누적 배출량 ", String.valueOf(res.getTotalReduction()));
                            Log.d("트리 레벨", String.valueOf(res.getTreeLevel()));
                            Log.d("트리 개수", String.valueOf(res.getTreeCount()));
                            Log.d("레벨 별 배출량", String.valueOf(res.getLevelReduction()));
                        }
                    }
                    @Override
                    public void onFailure(Call<DataModels_Main> call, Throwable t) {

                        t.printStackTrace();
                    }
                });*/

                Call<MainData_response> postCall = mMyAPI.createPost(data);
                postCall.enqueue(new Callback<MainData_response>() {
                    @Override
                    public void onResponse(Call<MainData_response> call, Response<MainData_response> response) {
                        Log.d("retrofit", "Data fetch success");
                        if (response.isSuccessful()) {
                            MainData_response result=response.body();
                            Log.d(TAG, "등록 완료");
                            Intent intent =new Intent(getBaseContext(), MainTreeActivity.class);
                            intent.putExtra("totalReduction", result.getTotalReduction());
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "Status Code : " + response.code());
                            Log.d(TAG, response.errorBody().toString());
                            Log.d(TAG, call.request().body().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<MainData_response> call, Throwable t) {
                        Log.d(TAG, "Fail msg : " + t.getMessage());
                        Toast.makeText(getApplicationContext(), "FAIL", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });
    }

    private void initMyAPI(String base_url) {
        Log.d(TAG,"initMyAPI : " + base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMyAPI = retrofit.create(RetrofitAPI.class);

    }




    public void ClickMenu(View view){
        MainTreeActivity.openDrawer(drawerLayout);
    }
    public void ClickLogo(View view){
        MainTreeActivity.closeDrawer(drawerLayout);
    }

    public void ClickHome(View view){
        MainTreeActivity.redirectActivity(this, MainTreeActivity.class);
    }

    public void ClickDashboard(View view){
        recreate();
    }

    public void ClickAboutUs(View view){
        MainTreeActivity.redirectActivity(this,Co2CalActivity.class);
    }
    public void Neighborhood(View view){
        MainTreeActivity.redirectActivity(this,NeighborhoodActivity.class);
    }
    public void Setting(View view){
        MainTreeActivity.redirectActivity(this,SettingActivity.class);
    }

    protected void onPause(){
        super.onPause();
        MainTreeActivity.closeDrawer(drawerLayout);
    }


    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");
        try {
            addresses = geocoder.getFromLocationName(address, 5);
            if ((addresses == null) || (addresses.size() == 0)) {
                return null;
            }
            Address addressLoc = addresses.get(0);

            resLocation.setLatitude(addressLoc.getLatitude());
            resLocation.setLongitude(addressLoc.getLongitude());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return resLocation;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

//    private void showCurrentLocation(Location location) {
//        LatLng curPoint = new LatLng(location.getLatitude(), location.getLongitude());
//        String msg = "Latitutde : " + curPoint.latitude
//                + "\nLongitude : " + curPoint.longitude;
//        //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
//    }

}



