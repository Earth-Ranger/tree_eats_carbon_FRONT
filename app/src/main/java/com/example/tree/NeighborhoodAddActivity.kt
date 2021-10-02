package com.example.tree

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tree.neighbor.GetModel
import com.example.tree.neighbor.PostModel
import kotlinx.android.synthetic.main.neighborhood_add.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

var userId: Long = 401

class NeighborhoodAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.neighborhood_add)


        pre_button.setOnClickListener {
            finish()
        }



        SearchListener(this)
    }

    private fun SearchListener(activity: Activity) {

        // 이웃 검색 기능 구현
        search_button.setOnClickListener {



            (application as MasterApplication).service_neighbor.neighborSearch(neighborhood_search_input.text.toString())
                .enqueue(object :Callback<GetModel> {
                override fun onResponse(call: Call<GetModel>, response: Response<GetModel>) {
                    Log.d("log", response.toString())

                    // 받은 유저정보
                    if (response.isSuccessful) {

                        val id = response.body()!!.id
                        val name = response.body()!!.name
                        val followerCount = response.body()!!.followerCount
                        val followingCount = response.body()!!.followingCount
                        val treeCount = response.body()!!.treeCount
                        val treeLevel = response.body()!!.treeLevel

                        Log.d("log", id.toString())
                        Log.d("log", name)
                        Log.d("log", treeLevel.toString())

                        // 전달받은 유저 정보들
                        userId = id
                        neighborhood_nikname.setText(name)
                        neighborhood_tree_count.setText(treeCount.toString())
                        neighborhood_tree_Level.setText(treeLevel.toString())
                        neighborhood_followerCount.setText(followerCount.toString())


                    } else {
                        Toast.makeText(this@NeighborhoodAddActivity,
                            "존재하지 않는 이웃계정입니다",
                            Toast.LENGTH_LONG).show()
                    }

                }

                override fun onFailure(call: Call<GetModel>, t: Throwable) {
                    // 실패
                    Log.d("log", t.message.toString())
                    Log.d("log", "fail")
                }
            })
        }


        // 이웃 추가 기능 구현
        Add_button.setOnClickListener {
            (application as MasterApplication).service_neighbor.neighborAdd(userId).enqueue(object : Callback<PostModel> {
                    override fun onResponse(call: Call<PostModel>, response: Response<PostModel>) {
                        Log.d("log", response.toString())

                        Log.d("log", userId.toString())

                        if (response.body()!!.code == 201) {
                            Toast.makeText(this@NeighborhoodAddActivity,
                                response.body()!!.message,
                                Toast.LENGTH_LONG).show()

                            finish()
                        } else {
                            Toast.makeText(this@NeighborhoodAddActivity,
                                response.body()!!.message,
                                Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<PostModel>, t: Throwable) {
                        // 실패
                        Log.d("log", t.message.toString())
                        Log.d("log", "fail")
                    }
                })



            finish()
        }


    }
}