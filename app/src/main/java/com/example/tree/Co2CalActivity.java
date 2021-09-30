package com.example.tree;


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

import com.google.android.gms.maps.model.LatLng;
import com.kansun.earth_ranger.Main.MainTreeActivity;
import com.kansun.earth_ranger.Main.RetrofitAPI;

import java.util.List;

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
    float distance;
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
                    emission=distance*145;
                }
            }
        });
        checkBox2.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                    emission=distance*145-distance*58;
                }
            }
        });
        checkBox3.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked()) {
                    // TODO : CheckBox is checked.
                    emission=distance*145-distance*60;
                }
            }
        });

        give_water_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                distance=0;
                text1.setText("이동 거리(km) : 0km");
                Intent intent = new Intent(getApplicationContext(), MainTreeActivity.class);
                intent.putExtra("emission", emission);
                startActivity(intent);
            }
        });

        initMyAPI(BASE_URL);

    }

    private void initMyAPI(String base_url) {
        Log.d(TAG,"initMyAPI : " + base_url);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(base_url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        mMyAPI = retrofit.create(RetrofitAPI.class);

    }

    public float getEmission() {
        return emission;
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



