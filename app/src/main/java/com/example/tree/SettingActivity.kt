package com.example.tree

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.tree.mypage.GetModel2
import com.example.tree.mypage.PUTModel
import kotlinx.android.synthetic.main.setting.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var name:String=""

class SettingActivity  : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)


        SettingListener()

        // 닉네임 편집 버튼
        change_nickname_button.setOnClickListener {

            var builder = AlertDialog.Builder(this@SettingActivity)

            // 화면 가져오기 (다이얼로그, 나무)
            var v1 = layoutInflater.inflate(R.layout.nickname_edit_dialog, null)

            var change_nickname_input: EditText? = v1.findViewById(R.id.change_nickname_input)
            var yesbutton: TextView? = v1.findViewById(R.id.yesbutton)


            // 기존닉네임 표시
            change_nickname_input?.setText(name)

            builder.setView(v1)
            builder.show()

            yesbutton!!.setOnClickListener {
                (application as MasterApplication).service_mypage.mydataChange(name).enqueue(object :Callback<PUTModel> {
                    override fun onResponse(call: Call<PUTModel>, response: Response<PUTModel>) {
                        Log.d("log", response.toString())

                        // 받은 유저정보
                        if (response.isSuccessful) {

                        }
                    }

                    override fun onFailure(call: Call<PUTModel>, t: Throwable) {
                        // 실패
                        Log.d("log", t.message.toString())
                        Log.d("log", "fail")
                    }
                })
            }

        }


    }

    private fun SettingListener() {

        (application as MasterApplication).service_mypage.mydataSearch().enqueue(object :Callback<GetModel2> {
                override fun onResponse(call: Call<GetModel2>,response: Response<GetModel2>) {
                    Log.d("log", response.toString())

                    // 받은 유저정보
                    if (response.isSuccessful) {
                        var user=response.body()
                        name=user!!.name
                        var email=user!!.email
                        var follower=user!!.follower
                        var following=user!!.following

                        change_nickname_input.setText(name)
                        change_email_input.setText(email)
                        followerCount_content.setText(follower)
                        followingCount_content.setText(following)
                    }
                }

            override fun onFailure(call: Call<GetModel2>, t: Throwable) {
                // 실패
                Log.d("log", t.message.toString())
                Log.d("log", "fail")
            }
        })

    }
}