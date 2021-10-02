package com.example.tree

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.tree.login.APIS_login
import com.example.tree.login.Login
import com.example.tree.login.user_token
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    //val api = APIS_login.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LoginListener(this)

    }

    private fun LoginListener(activity: Activity) {

        // 회원가입 창으로 이동
        signup.setOnClickListener { startActivity(Intent(activity, SignupActivity::class.java)) }

        // 로그인 버튼 클릭
        login_button.setOnClickListener {
            val email = email_input.text.toString()
            val password = password_input.text.toString()

            var login = Login(email = email, password = password)

            (application as MasterApplication).service_login.signin(login).enqueue(object : Callback<user_token> {
                override fun onResponse(call: Call<user_token>, response: Response<user_token>) { // 안으로 안들어감
                    if (response.isSuccessful) {
                        val user = response.body()
                        val token = user!!.token!!
                        saveUserToken(email, token, activity)
                        (application as MasterApplication).createRetrofit()

                        Toast.makeText(activity, "로그인 하셨습니다", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(activity, NeighborhoodActivity::class.java))  //메인이랑 연결해야함. 우선 이웃화면으로 가도록 설정함.
                        finish()

                    } else {
                        Toast.makeText(activity, "로그인에 실패했습니다", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<user_token>, t: Throwable) {
                    Toast.makeText(activity, "서버 오류", Toast.LENGTH_SHORT).show()

                    Log.d("log",t.message.toString())
                    Log.d("log","fail")
                }
            })
        }
    }

    fun saveUserToken(email: String, token: String, activity: Activity) {
        val sp = activity.getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val editor = sp.edit()
        editor.putString("email", email)
        editor.putString("token", token)
        editor.commit()
    }


}