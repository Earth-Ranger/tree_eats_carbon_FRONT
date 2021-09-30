package com.example.tree.Main;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.google.gson.Gson;
import com.kansun.earth_ranger.Co2CalActivity;
import com.kansun.earth_ranger.R;
import com.kansun.earth_ranger.allRankingActivity;

import retrofit2.Call;
import retrofit2.Retrofit;

public class MainTreeActivity extends AppCompatActivity {
    //드로우바
    DrawerLayout drawerLayout;
    //통신
    private Retrofit mRetrofit;
    private RetrofitAPI mRetrofitAPI;
    private Call<Double> mDistance;
    private Call<Integer> mTransport;
    private Call<DataModels_Main> mData;
    private Gson mGson;

    private float emission=0;

    //레벨업바
    ProgressBar progressBar = null;
    TextView textView = null;
    TextView textView1 = null; //단계
    TextView textView2 = null; //몇그루
    Handler handler = null;
    ImageView level_img=null;

    int level = 1;
    int maxExp= 0;
    float myExp = 0;
    int account =0;//몇그루 심을지 그 개수

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintree);

        checkDangerousPermissions();

        drawerLayout=findViewById(R.id.drawer_layout);
        textView1=(TextView) findViewById(R.id.main_text2);
        textView2=(TextView) findViewById(R.id.textView2);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        textView = (TextView) findViewById(R.id.level_num);
        level_img=(ImageView)findViewById(R.id.tree_1);

        //배출량 누적으로 레벨업
        Intent intent=getIntent();
        emission=intent.getFloatExtra("emission",0);
        calEmission(emission);


    }

    public void ClickMenu(View view){
        openDrawer(drawerLayout);
    }

    public static void openDrawer(DrawerLayout drawerLayout){
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view){
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(DrawerLayout drawerLayout){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){


            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void ClickHome(View view){
        recreate();
    }

    public void ClickDashboard(View view){
        redirectActivity(this, Co2CalActivity.class);
    }

    public void ClickAboutUs(View view){
        redirectActivity(this, allRankingActivity.class);
    }

    public static void redirectActivity(Activity activity, Class aClass){
        Intent intent=new Intent(activity,aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public void calEmission(float emission) {
        myExp=emission;
        maxExp  = 1000 + 30 * (level - 1) * (level + 5);
        textView.setText(level+ " 단계 - 필요경험치 : " + maxExp + " 현재경험치 : " + myExp);

        progressBar.setMax(maxExp);
        progressBar.setProgress((int) myExp);

        switch(maxExp){
            case 1000:
                level_img.setImageResource(R.drawable.img_1);
                textView1.setText(level+" 단계");
                break;
            case 3100:
                level_img.setImageResource(R.drawable.img_2);
                textView1.setText(level+" 단계");
                break;
            case 5800:
                level_img.setImageResource(R.drawable.img_3);
                textView1.setText(level+" 단계");
                break;
            case 9100:
                level_img.setImageResource(R.drawable.img_4);
                textView1.setText(level+" 단계");
                break;
            case 13000:
                level_img.setImageResource(R.drawable.img_5);
                textView1.setText(level+" 단계");
                break;
        }

        if(myExp >= maxExp) {
            level++;
            myExp = myExp- maxExp;
        }
        if(level==6){
            level=1;
            myExp=0;
            maxExp=0;
            account++;
            textView2.setText(account+"그루");
        }
    }

    //------------------권한 설정 시작------------------------
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}