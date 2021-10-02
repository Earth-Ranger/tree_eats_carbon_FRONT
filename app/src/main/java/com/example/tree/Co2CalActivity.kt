package com.example.tree

import android.content.Context
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.tree.Main.*
import com.example.tree.Main.MainTreeActivity.Companion.closeDrawer
import com.example.tree.Main.MainTreeActivity.Companion.openDrawer
import com.example.tree.Main.MainTreeActivity.Companion.redirectActivity
import com.example.tree.neighbor.DeleteModel
import com.google.android.gms.maps.model.LatLng
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Co2CalActivity : AppCompatActivity() {
    private val mDrawerLayout: DrawerLayout? = null
    private val context: Context = this

    var distance : Double=0.0
    var check : Int = 0
    var meter: String? = null
    var location1: Location? = Location("point 1")
    var location2: Location? = Location("point 2")
    var curPoint1: LatLng? = null
    var curPoint2: LatLng? = null
    var drawerLayout: DrawerLayout? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_co2_cal)
        drawerLayout = findViewById(R.id.drawer_layout)
        //객체 초기화
        var editText : EditText = findViewById(R.id.editText)
        var btnKor2Loc : Button= findViewById(R.id.button2)
        var editText2 : EditText= findViewById(R.id.editText2)
        var btnKor3Loc : Button= findViewById(R.id.button3)
        var text1 :TextView= findViewById<View>(R.id.distance) as TextView
        var d_btn :Button= findViewById(R.id.d_btn)
        var give_water_btn :Button= findViewById(R.id.give_water_btn)


        btnKor2Loc.setOnClickListener(View.OnClickListener {
            if (editText.getText().toString().length > 0) {
                location1 =
                    getLocationFromAddress(applicationContext, editText.getText().toString())
                curPoint1 = LatLng(location1!!.latitude, location1!!.longitude)
                location1!!.latitude = curPoint1!!.latitude
                location1!!.longitude = curPoint1!!.longitude
            }
        })
        btnKor3Loc.setOnClickListener(View.OnClickListener {
            if (editText2.getText().toString().length > 0) {
                location2 =
                    getLocationFromAddress(applicationContext, editText2.getText().toString())
                curPoint2 = LatLng(location2!!.latitude, location2!!.longitude)
                location2!!.latitude = curPoint2!!.latitude
                location2!!.longitude = curPoint2!!.longitude
            }
        })

        d_btn.setOnClickListener(View.OnClickListener {
            distance = location1!!.distanceTo(location2).toDouble()
            distance = Math.round(distance * 0.001).toDouble()
            meter = java.lang.Double.toString(distance)
            Log.d(TAG, "DistanceBetweenPointAandB: $distance")
            text1!!.text = "이동 거리(km) : " + meter + "km"
        })
        //체크박스
        val checkBox1 = findViewById<View>(R.id.check1) as CheckBox
        val checkBox2 = findViewById<View>(R.id.check2) as CheckBox
        val checkBox3 = findViewById<View>(R.id.check3) as CheckBox
        checkBox1.setOnClickListener { v ->
            if ((v as CheckBox).isChecked) {
                // TODO : CheckBox is checked.
                check = 0
            }
        }
        checkBox2.setOnClickListener { v ->
            if ((v as CheckBox).isChecked) {
                // TODO : CheckBox is checked.
                check = 1
            }
        }
        checkBox3.setOnClickListener { v ->
            if ((v as CheckBox).isChecked) {
                // TODO : CheckBox is checked.
                check = 2
            }
        }

        give_water_btn.setOnClickListener(View.OnClickListener {
            Log.d(TAG, "POST")
            //RetrofitAPI postAPI = retrofit.create(RetrofitAPI.class);
            val data = Data_Request(distance, check)
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
            (application as MasterApplication).service_tree.createPost(data)
                .enqueue(object : Callback<Data_Response> {
                override fun onResponse(
                    call: Call<Data_Response>,
                    response: Response<Data_Response>
                ) {
                    Log.d("retrofit", "Data fetch success")
                    if (response.isSuccessful) {
                        val result = response.body()
                        Log.d(TAG, "등록 완료")
                        val intent = Intent(baseContext, MainTreeActivity::class.java)
                        intent.putExtra("totalReduction", result!!.totalReduction)
                        startActivity(intent)
                    } else {
                        Log.d(TAG, "Status Code : " + response.code())
                        Log.d(TAG, response.errorBody().toString())
                        Log.d(TAG, call.request().body().toString())
                    }
                }

                override fun onFailure(call: Call<Data_Response>, t: Throwable) {
                    Log.d(TAG, "Fail msg : " + t.message)
                    Toast.makeText(applicationContext, "FAIL", Toast.LENGTH_SHORT).show()
                }
            })
        })
    }

    fun ClickMenu(view: View?) {
        openDrawer(drawerLayout)
    }

    fun ClickLogo(view: View?) {
        closeDrawer(drawerLayout)
    }

    fun ClickHome(view: View?) {
        redirectActivity(this, MainTreeActivity::class.java)
    }

    fun ClickDashboard(view: View?) {
        recreate()
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

    private fun getLocationFromAddress(context: Context, address: String): Location? {
        val geocoder = Geocoder(context)
        val addresses: List<Address>?
        val resLocation = Location("")
        try {
            addresses = geocoder.getFromLocationName(address, 5)
            if (addresses == null || addresses.size == 0) {
                return null
            }
            val addressLoc = addresses[0]
            resLocation.latitude = addressLoc.latitude
            resLocation.longitude = addressLoc.longitude
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return resLocation
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout!!.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        //로그캣 사용 설정
        private const val TAG = "Co2CalActivity"
    }
}