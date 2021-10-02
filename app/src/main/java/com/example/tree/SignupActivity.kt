package com.example.tree

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.example.tree.login.Login
import com.example.tree.login.user_token
import com.example.tree.signup.CheckGetModel2
import com.example.tree.signup.Signup
import kotlinx.android.synthetic.main.signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


var nickname_check: Boolean = false

class SignupActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)



        //로그인 버튼 클릭
        signup_button.setOnClickListener{
            register(this)
        }

        //비밀번호 일치 확인
        password_check_input.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                if(password_input.getText().toString().equals(password_check_input.getText().toString())){
                    password_check_txt.setText("비밀번호가 일치합니다")
                }
                else
                    password_check_txt.setText("비밀번호가 일치하지 않습니다")
            }
        })
    }


    private fun register(activity: Activity) {

        //입력 string값 선언
        val email = email_input.text.toString()
        val password = password_input.text.toString()
        val password_check = password_check_input.text.toString()
        val nickname = nickname.text.toString()


        //nickname 중복 여부 판단
        (application as MasterApplication).service.get_nickname(nickname).enqueue(object: Callback<CheckGetModel2> {
            override fun onResponse(call: Call<CheckGetModel2>, response: Response<CheckGetModel2>) {
                if (response.isSuccessful) {

                    val respon = response.body()
                    val message = respon!!.message!!
                    nickname_check=message

                    if(message==false){
                        Toast.makeText(activity, "중복된 닉네임입니다", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        Toast.makeText(activity, "사용가능한 닉네임입니다", Toast.LENGTH_SHORT).show()
                    }

                }
            }

            override fun onFailure(call: Call<CheckGetModel2>, t: Throwable) {
                Toast.makeText(activity, "서버 오류", Toast.LENGTH_SHORT).show()

                Log.d("log",t.message.toString())
                Log.d("log","fail")
            }
        })




        //DataModels 에 전달
        var signup = Signup(email=email, name=nickname, password=password)

        if (password == password_check) {
            (application as MasterApplication).service.register(signup).enqueue(object: Callback<Signup> {
                override fun onResponse(call: Call<Signup>, response: Response<Signup>) {
                    if (response.isSuccessful && nickname_check==true) {
                        Toast.makeText(activity, email+"님 회원가입 되었습니다", Toast.LENGTH_LONG).show()

                        // 회원가입 성공시 자동으로 로그인 되도록 설정
                        var login = Login(email = email, password = password)
                        (application as MasterApplication).service_login.signin(login).enqueue(object : Callback<user_token> {

                            override fun onResponse(call: Call<user_token>, response: Response<user_token>) {
                                if (response.isSuccessful) {
                                    val user = response.body()
                                    val token = user!!.token!!
                                    saveUserToken(email, token, activity)
                                    (application as MasterApplication).createRetrofit()

                                    Toast.makeText(activity, nickname+"님 로그인 하셨습니다", Toast.LENGTH_SHORT).show()
                                    startActivity(Intent(activity, NeighborhoodActivity::class.java))  //메인이랑 연결해야함. 우선 이웃화면으로 가도록 설정함.

                                }
                            }

                            override fun onFailure(call: Call<user_token>, t: Throwable) {
                                Toast.makeText(activity, "서버 오류", Toast.LENGTH_SHORT).show()

                                Log.d("log",t.message.toString())
                                Log.d("log","fail")
                            }
                        })

                    } else {
                        if(nickname_check==true){
                            Toast.makeText(activity, "사용할 수 없는 이메일입니다", Toast.LENGTH_LONG).show()
                        }
                    }
                }

                //통신실패
                override fun onFailure(call: Call<Signup>, t: Throwable) {
                    Toast.makeText(activity, "서버 오류", Toast.LENGTH_SHORT).show()

                    Log.d("log",t.message.toString())
                    Log.d("log","fail")
                }
            })
        } else {
            Toast.makeText(activity, "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show()

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