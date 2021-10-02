package com.example.tree.Main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.tree.*
import com.example.tree.neighbor.CheckGetModel
import com.example.tree.neighbor.nlist
import kotlinx.android.synthetic.main.neighborhood_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainTreeActivity : AppCompatActivity() {
    //드로우바
    var drawerLayout: DrawerLayout? = null
    var retrofit: Retrofit? = null
    private val BASE_URL = "http://180.230.121.23/"
    private var mMyAPI: RetrofitAPI? = null

    //레벨업바
    var progressBar: ProgressBar? = null
    var textView: TextView? = null
    var textView1: TextView? = null //단계
    var textView2: TextView? = null //몇그루
    var level_img: ImageView? = null
    var level = 1
    var maxExp = 0
    var myExp = 0f
    var account = 0 //몇그루 심을지 그 개수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintree)
        drawerLayout = findViewById(R.id.drawer_layout)
        textView1 = findViewById<View>(R.id.main_text2) as TextView
        textView2 = findViewById<View>(R.id.textView2) as TextView
        progressBar = findViewById<View>(R.id.progressBar) as ProgressBar
        textView = findViewById<View>(R.id.level_num) as TextView
        level_img = findViewById<View>(R.id.tree_1) as ImageView

        (application as MasterApplication).service_tree.get_plant()
            .enqueue(object :
                Callback<TreeDTO> {
                override fun onResponse(
                    call: Call<TreeDTO>,
                    response: Response<TreeDTO>,
                ) {
                    Log.d("log", response.toString())

                    // 받은 유저정보
                    if (response.isSuccessful) {
                        val treeCount = response.body()!!.treeCount
                        val treeLevel = response.body()!!.treeLevel
                        val carbon = response.body()!!.carbon
                        val levelReduction = response.body()!!.levelReduction
                        Log.d("log", treeLevel.toString())
                        Log.d("log", carbon.toString())
                        Log.d("log", treeCount.toString())
                        Log.d("log", levelReduction.toString())
                    }
                }

                override fun onFailure(call: Call<TreeDTO>, t: Throwable) {
                    // 실패
                    Log.d("log", t.message.toString())
                    Log.d("log", "fail")
                }
            })
        /*mMyAPI.getPlant().enqueue(new Callback<DataModels_Main>() {
            @Override
            public void onResponse(Call<DataModels_Main> call, Response<DataModels_Main> response) {
                if (response.isSuccessful()) {  // 조회성공
                    DataModels_Main res = response.body();
                    Intent intent=getIntent();
                    double myExp=intent.getExtras().getDouble("totalReduction");
                    //Log.d("총 누적 배출량 ", String.valueOf(res.getCarbonMap()));
                    level=res.getTreeLevel();
                    textView2.setText(res.getTreeCount());
                    textView.setText(level+ " 단계 - 필요경험치 : " + " 현재경험치 : " + myExp);
                    Log.d(TAG, "등록 완료");
                } else {
                    Log.d(TAG, "Status Code : " + response.code());
                }
            }

            @Override
            public void onFailure(Call<DataModels_Main> call, Throwable t) {
                t.printStackTrace();
            }
        });*/
    }

    //배출량 누적으로 레벨업
    //Intent intent=getIntent();
    //emission=intent.getFloatExtra("emission",0);
    //calEmission(emission)


    fun ClickMenu(view: View?) {
        openDrawer(drawerLayout)
    }

    fun ClickLogo(view: View?) {
        closeDrawer(drawerLayout)
    }

    fun ClickHome(view: View?) {
        recreate()
    }

    fun ClickDashboard(view: View?) {
        redirectActivity(this, Co2CalActivity::class.java)
    }

    fun ClickAboutUs(view: View?) {
        redirectActivity(this, allRankingActivity::class.java)
    }

    fun Neighborhood(view: View?) {
        redirectActivity(this, NeighborhoodActivity::class.java)
    }

    fun Setting(view: View?) {
        redirectActivity(this, SettingActivity::class.java)
    }

    override fun onPause() {
        super.onPause()
        closeDrawer(drawerLayout)
    }

    fun calEmission() {
        textView!!.text = "$level 단계 - 필요경험치 : $maxExp 현재경험치 : $myExp"
        /*myExp=emission;
        maxExp  = 1000 + 30 * (level - 1) * (level + 5);

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
        }*/
    }

    //------------------권한 설정 시작------------------------
    private fun checkDangerousPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_WIFI_STATE
        )
        var permissionCheck = PackageManager.PERMISSION_GRANTED
        for (i in permissions.indices) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i])
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            for (i in permissions.indices) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    companion object {
        //로그캣 사용 설정
        private const val TAG = "MainTreeActivity"
        @JvmStatic
        fun openDrawer(drawerLayout: DrawerLayout?) {
            drawerLayout!!.openDrawer(GravityCompat.START)
        }

        @JvmStatic
        fun closeDrawer(drawerLayout: DrawerLayout?) {
            if (drawerLayout!!.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)
            }
        }

        @JvmStatic
        fun redirectActivity(activity: Activity, aClass: Class<*>?) {
            val intent = Intent(activity, aClass)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            activity.startActivity(intent)
        }
    }
}