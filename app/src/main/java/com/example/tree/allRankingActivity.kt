package com.example.tree

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tree.All_List.CheckGetAllModel
import com.example.tree.All_List.GetModel_all_list
import com.example.tree.MainTreeActivity
import com.example.tree.Main.getAllList
import com.example.tree.neighbor.*
import kotlinx.android.synthetic.main.all_ranking_main.*
import kotlinx.android.synthetic.main.neighborhood_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class allRankingActivity : AppCompatActivity() {
    var mDrawerLayout: DrawerLayout? = null
    var memberId: Long = 401
    var name: String = ""
    var treeCount: Int = 0

    val allList: allList = allList()
    //var mAdapter = ViewAdapter(allList)
    //var mAdapter2 = ViewAdapter(allList)

    lateinit var profileAdapter: ProfileAdapter
    val datas = mutableListOf<GetModel_all_list>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_ranking_main)

        initRecycler()
    }


    private fun initRecycler() {
        profileAdapter = ProfileAdapter(this)
        all_list.adapter = profileAdapter


        datas.apply {
            add(GetModel_all_list(rank = 1, name = "tes", treeCount = 28))
            add(GetModel_all_list(rank = 2, name = "ca", treeCount = 27))
            add(GetModel_all_list(rank = 3, name = "dds", treeCount = 27))
            add(GetModel_all_list(rank = 4, name = "fff", treeCount = 23))
            add(GetModel_all_list(rank = 5, name = "vfs", treeCount = 21))
            add(GetModel_all_list(rank = 6, name = "dsa", treeCount = 20))
            add(GetModel_all_list(rank = 7, name = "ww", treeCount = 19))
            add(GetModel_all_list(rank = 8, name = "gfg", treeCount = 17))

            profileAdapter.datas = datas
            profileAdapter.notifyDataSetChanged()

        }
    }

    fun ClickMenu(view: View?) {
        MainTreeActivity.openDrawer(mDrawerLayout)
    }

    fun ClickLogo(view: View?) {
        MainTreeActivity.closeDrawer(mDrawerLayout)
    }

    fun ClickHome(view: View?) {
        MainTreeActivity.redirectActivity(this, MainTreeActivity::class.java)
    }

    fun ClickDashboard(view: View?) {
        MainTreeActivity.redirectActivity(this, Co2CalActivity::class.java)
    }

    fun ClickAboutUs(view: View?) {
        recreate()
    }

    fun Neighborhood(view: View?) {
        MainTreeActivity.redirectActivity(this, NeighborhoodActivity::class.java)
    }

    fun Setting(view: View?) {
        MainTreeActivity.redirectActivity(this, SettingActivity::class.java)
    }

    override fun onPause() {
        super.onPause()
        MainTreeActivity.closeDrawer(mDrawerLayout)
    }

    /*fun ViewListener() {

        // 이웃 목록 데이터 받기
        (application as MasterApplication).service_rank.get_All()
            .enqueue(object :
                Callback<getAllList> {
                override fun onResponse(
                    call: Call<getAllList>,
                    response: Response<getAllList>,
                ) {
                    Log.d("log", response.toString())

                    // 받은 유저정보
                    if (response.isSuccessful) {
                        for (i in response.body()!!.getallList.indices) {
                            memberId = response.body()!!.getallList[i].memberId
                            name = response.body()!!.getallList[i].name
                            treeCount = response.body()!!.getallList[i].treeCount

                            Log.d("log", name)
                            Log.d("log", memberId.toString())
                            Log.d("log", treeCount.toString())

                        }
                    }

                    // 리사이클러뷰 설정
                    all_list.adapter = mAdapter
                }

                override fun onFailure(call: Call<getAllList>, t: Throwable) {
                    // 실패
                    Log.d("log", t.message.toString())
                    Log.d("log", "fail")
                }
            })
    }*/

}