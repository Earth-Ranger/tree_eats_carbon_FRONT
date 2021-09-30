package com.example.tree

import android.app.Application
import android.content.Context
import com.example.tree.login.APIS_login
import com.example.tree.signup.APIS_signup
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

import retrofit2.converter.gson.GsonConverterFactory

class MasterApplication : Application() {

    lateinit var service: APIS_signup
    lateinit var service_login: APIS_login
    //이곳에 추가적인 APIS들 작성해야 함.

    override fun onCreate() {
        super.onCreate()

        Stetho.initializeWithDefaults(this)
        createRetrofit()
        //chrome://inspect/#devices
    }


    fun createRetrofit() {
        val header = Interceptor {
            val original = it.request()
            if (checkIsLogin()) {
                getUserToken()?.let { token ->
                    val requeset = original.newBuilder()
                        .header("X-AUTH-TOKEN", token)
                        .build()
                    it.proceed(requeset)
                }
            } else {
                it.proceed(original)
            }
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(header)
            .addNetworkInterceptor(StethoInterceptor())
            .build()


        val retrofit = Retrofit.Builder()
            .baseUrl("http://180.230.121.23/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()



        service = retrofit.create(APIS_signup::class.java)
        service_login = retrofit.create(APIS_login::class.java)
        //이곳에 다른 APIS들도 작성해야 함.
    }

    fun checkIsLogin(): Boolean {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        var token = sp.getString("token", "null")
        if (token != "null") return true
        else return false
    }

    fun getUserToken(): String? {
        val sp = getSharedPreferences("login_sp", Context.MODE_PRIVATE)
        val token = sp.getString("token", "null")
        if (token == "null") return null
        else return token
    }
}