package com.example.tree

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tree.MainTreeActivity
import com.example.tree.neighbor.*
import kotlinx.android.synthetic.main.neighborhood_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NeighborhoodActivity : AppCompatActivity() {
    var mDrawerLayout: DrawerLayout? = null
    var userId: Long = 401
    var name: String = ""
    var followerCount: Int = 0
    var followingCount: Int = 0
    var treeCount: Int = 0
    var treeLevel: Int = 0


    val neighborList: neighborList = neighborList()
    var mAdapter = ViewAdapter(neighborList)
    var mAdapter2 = ViewAdapter(neighborList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.neighborhood_main)
        mDrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        // 이웃 추가 화면으로 이동
        neighborhood_add.setOnClickListener {
            startActivity(Intent(this, NeighborhoodAddActivity::class.java))
        }

        ViewListener()


        // 리싸이클러뷰 어댑터 설정
        mAdapter = ViewAdapter(neighborList)
        mAdapter2 = ViewAdapter(neighborList)
        val manager = LinearLayoutManager(this)
        manager.reverseLayout = false
        manager.stackFromEnd = false
        neighborhood_list.layoutManager = manager


        // 휴지통 버튼 클릭시
        mAdapter.setItemClickListener(object : ViewAdapter.ItemClickListener {

            override fun onClick(view: View, position: Int) {

                userId = neighborList.NeighborList[position].id

                // 선택한 친구 목록 삭제
                (application as MasterApplication).service_neighbor_list.neighborDelete(userId)
                    .enqueue(object : Callback<DeleteModel> {
                        override fun onResponse(
                            call: Call<DeleteModel>,
                            response: Response<DeleteModel>,
                        ) {
                            Log.d("log", response.toString())

                            if (response.body()!!.code == 204) {
                                Toast.makeText(this@NeighborhoodActivity,
                                    "삭제되었습니다",
                                    Toast.LENGTH_LONG).show()

                                neighborList.NeighborList.clear()
                                ViewListener()
                            } else {
                                Toast.makeText(this@NeighborhoodActivity,
                                    "삭제할 수 없습니다",
                                    Toast.LENGTH_LONG).show() //나중에 삭제할것
                            }


                        }

                        override fun onFailure(call: Call<DeleteModel>, t: Throwable) {
                            // 실패
                            Log.d("log", t.message.toString())
                            Log.d("log", "fail")
                        }
                    })

            }
        })

        // 각 아이템 리스터 (다이얼로그)
        mAdapter.setItemClickListener2(object : ViewAdapter.ItemClickListener2 {
            override fun onClick2(view: View, position: Int) {

                userId = neighborList.NeighborList[position].id

                (application as MasterApplication).service_neighbor_list.neighbortreeView(userId)
                    .enqueue(object : Callback<GetModel2> {
                        override fun onResponse(
                            call: Call<GetModel2>,
                            response: Response<GetModel2>,
                        ) {
                            Log.d("log", response.toString())


                            var builder = AlertDialog.Builder(this@NeighborhoodActivity)

                            // 화면 가져오기 (다이얼로그, 나무)
                            var v1 = layoutInflater.inflate(R.layout.neighborhood_tree_dialog, null)

                            var user_nickname: TextView? = v1.findViewById(R.id.user_nickname)
                            var user_tree_count_content: TextView? = v1.findViewById(R.id.user_tree_count_content)
                            var user_tree_level_content: TextView? =  v1.findViewById(R.id.user_tree_level_content)
                            var user_tree_image: ImageView? =  v1.findViewById(R.id.user_tree_image)
                            var followerCount_content: TextView? = v1.findViewById(R.id.followerCount_content)
                            var followingCount_content: TextView? =  v1.findViewById(R.id.followingCount_content)



                            val name = response.body()!!.name
                            val followerCount = response.body()!!.followerCount
                            val followingCount = response.body()!!.followingCount
                            val treeLevel = response.body()!!.treeLevel
                            val treeCount = response.body()!!.treeCount

                            // 세부정보 표시
                            user_nickname?.setText(name)
                            user_tree_count_content?.setText(treeCount.toString())
                            user_tree_level_content?.setText(treeLevel.toString())
                            followerCount_content?.setText(followerCount.toString())
                            followingCount_content?.setText(followingCount.toString())


                            if(treeLevel==1){
                                user_tree_image!!.setImageResource(R.drawable.img_1)
                            }
                            else if(treeLevel==2){
                                user_tree_image!!.setImageResource(R.drawable.img_2)
                            }
                            else if(treeLevel==3){
                                user_tree_image!!.setImageResource(R.drawable.img_3)
                            }
                            else if(treeLevel==4){
                                user_tree_image!!.setImageResource(R.drawable.img_4)
                            }
                            else{
                                user_tree_image!!.setImageResource(R.drawable.img_5)
                            }

                            builder.setView(v1)
                            builder.show()


                        }

                        override fun onFailure(call: Call<GetModel2>, t: Throwable) {

                        }
                    })
            }
        })


        neighborhood_list.adapter = mAdapter

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
        MainTreeActivity.redirectActivity(this, allRankingActivity::class.java)
    }

    fun Neighborhood(view: View?) {
        recreate()
    }
    fun Setting(view: View?) {
        MainTreeActivity.redirectActivity(this, SettingActivity::class.java)
    }

    override fun onPause() {
        super.onPause()
        MainTreeActivity.closeDrawer(mDrawerLayout)
    }

    override fun onRestart() {
        super.onRestart()
        setContentView(R.layout.neighborhood_main)

        neighborList.NeighborList.clear()

        // 이웃 추가 화면으로 이동
        neighborhood_add.setOnClickListener {
            startActivity(Intent(this, NeighborhoodAddActivity::class.java))
        }

        // 리싸이클러뷰 어댑터 설정
        mAdapter = ViewAdapter(neighborList)
        val manager = LinearLayoutManager(this)
        manager.reverseLayout = false
        manager.stackFromEnd = false
        neighborhood_list.layoutManager = manager

        neighborhood_list.adapter = mAdapter


        // 휴지통 버튼 클릭시
        mAdapter.setItemClickListener(object : ViewAdapter.ItemClickListener {

            override fun onClick(view: View, position: Int) {

                userId = neighborList.NeighborList[position].id

                // 선택한 친구 목록 삭제
                (application as MasterApplication).service_neighbor_list.neighborDelete(userId)
                    .enqueue(object : Callback<DeleteModel> {
                        override fun onResponse(
                            call: Call<DeleteModel>,
                            response: Response<DeleteModel>,
                        ) {
                            Log.d("log", response.toString())

                            if (response.body()!!.code == 204) {
                                Toast.makeText(this@NeighborhoodActivity,
                                    "삭제되었습니다",
                                    Toast.LENGTH_LONG).show()

                                neighborList.NeighborList.clear()
                                ViewListener()
                            } else {
                                Toast.makeText(this@NeighborhoodActivity,
                                    "삭제할 수 없습니다",
                                    Toast.LENGTH_LONG).show() //나중에 삭제할것
                            }


                        }

                        override fun onFailure(call: Call<DeleteModel>, t: Throwable) {
                            // 실패
                            Log.d("log", t.message.toString())
                            Log.d("log", "fail")
                        }
                    })

            }
        })

        // 각 아이템 리스터 (다이얼로그)
        mAdapter.setItemClickListener2(object : ViewAdapter.ItemClickListener2 {
            override fun onClick2(view: View, position: Int) {

                userId = neighborList.NeighborList[position].id

                (application as MasterApplication).service_neighbor_list.neighbortreeView(userId)
                    .enqueue(object : Callback<GetModel2> {
                        override fun onResponse(
                            call: Call<GetModel2>,
                            response: Response<GetModel2>,
                        ) {
                            Log.d("log", response.toString())


                            var builder = AlertDialog.Builder(this@NeighborhoodActivity)

                            // 화면 가져오기 (다이얼로그, 나무)
                            var v1 = layoutInflater.inflate(R.layout.neighborhood_tree_dialog, null)

                            var user_nickname: TextView? = v1.findViewById(R.id.user_nickname)
                            var user_tree_count_content: TextView? = v1.findViewById(R.id.user_tree_count_content)
                            var user_tree_level_content: TextView? =  v1.findViewById(R.id.user_tree_level_content)
                            var user_tree_image: ImageView? =  v1.findViewById(R.id.user_tree_image)
                            var followerCount_content: TextView? = v1.findViewById(R.id.followerCount_content)
                            var followingCount_content: TextView? =  v1.findViewById(R.id.followingCount_content)



                            val name = response.body()!!.name
                            val followerCount = response.body()!!.followerCount
                            val followingCount = response.body()!!.followingCount
                            val treeLevel = response.body()!!.treeLevel
                            val treeCount = response.body()!!.treeCount

                            // 세부정보 표시
                            user_nickname?.setText(name)
                            user_tree_count_content?.setText(treeCount.toString())
                            user_tree_level_content?.setText(treeLevel.toString())
                            followerCount_content?.setText(followerCount.toString())
                            followingCount_content?.setText(followingCount.toString())


                            if(treeLevel==1){
                                user_tree_image!!.setImageResource(R.drawable.img_1)
                            }
                            else if(treeLevel==2){
                                user_tree_image!!.setImageResource(R.drawable.img_2)
                            }
                            else if(treeLevel==3){
                                user_tree_image!!.setImageResource(R.drawable.img_3)
                            }
                            else if(treeLevel==4){
                                user_tree_image!!.setImageResource(R.drawable.img_4)
                            }
                            else{
                                user_tree_image!!.setImageResource(R.drawable.img_5)
                            }

                            builder.setView(v1)
                            builder.show()


                        }

                        override fun onFailure(call: Call<GetModel2>, t: Throwable) {

                        }
                    })
            }
        })




        // 리사이클러뷰 설정
        neighborhood_list.adapter = mAdapter

        ViewListener()
    }


    private fun ViewListener() {

        // 이웃 목록 데이터 받기
        (application as MasterApplication).service_neighbor_list.AllneighborSearch()
            .enqueue(object :
                Callback<CheckGetModel> {
                override fun onResponse(
                    call: Call<CheckGetModel>,
                    response: Response<CheckGetModel>,
                ) {
                    Log.d("log", response.toString())

                    // 받은 유저정보
                    if (response.isSuccessful) {
                        for (i in response.body()!!.checkRoomList.indices) {
                            userId = response.body()!!.checkRoomList[i].id
                            name = response.body()!!.checkRoomList[i].name
                            followerCount = response.body()!!.checkRoomList[i].followerCount
                            treeCount = response.body()!!.checkRoomList[i].treeCount

                            Log.d("log", name)
                            Log.d("log", followerCount.toString())
                            Log.d("log", treeCount.toString())


                            // 리스트에 추가
                            neighborList.AddFriend(
                                nlist(
                                    userId,
                                    name,
                                    followerCount,
                                    treeCount
                                )
                            )


                        }
                    }

                    // 리사이클러뷰 설정
                    neighborhood_list.adapter = mAdapter
                }

                override fun onFailure(call: Call<CheckGetModel>, t: Throwable) {
                    // 실패
                    Log.d("log", t.message.toString())
                    Log.d("log", "fail")
                }
            })

    }


    class ViewAdapter(
        val Neighbor_List: neighborList,
    ) : RecyclerView.Adapter<ViewAdapter.ViewHolder>() {

        private lateinit var itemClickListner: ItemClickListener
        private lateinit var itemClickListner2: ItemClickListener2

        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val neighborhood_nikname: TextView
            val neighborhood_tree_count: TextView
            var neighborhood_followerCount: TextView
            var deletebutton: ImageView
            var user_page: LinearLayout

            init {
                neighborhood_nikname = itemView.findViewById(R.id.neighborhood_nikname_l)
                neighborhood_tree_count = itemView.findViewById(R.id.neighborhood_tree_count_l)
                neighborhood_followerCount =
                    itemView.findViewById(R.id.neighborhood_followerCount_l)
                deletebutton = itemView.findViewById(R.id.deletebutton)
                user_page = itemView.findViewById(R.id.user_page)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.neighborhood_list, parent, false)
            return ViewHolder(view)
        }

        override fun getItemCount(): Int {
            return Neighbor_List.NeighborList.size
        }

        // 리사이클러뷰 아이템클릭 리스너
        interface ItemClickListener {
            fun onClick(view: View, position: Int)
        }

        fun setItemClickListener(itemClickListener: ItemClickListener) {
            this.itemClickListner = itemClickListener
        }

        // 리사이클러뷰 아이템클릭 리스너
        interface ItemClickListener2 {
            fun onClick2(view: View, position: Int)
        }

        fun setItemClickListener2(itemClickListener2: ItemClickListener2) {
            this.itemClickListner2 = itemClickListener2
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val Neighbor = Neighbor_List.NeighborList[position]

            holder.neighborhood_nikname.text = Neighbor.name
            holder.neighborhood_tree_count.text = Neighbor.treeCount.toString()
            holder.neighborhood_followerCount.text = Neighbor.followerCount.toString()


            // 리사이클러뷰 아이템클릭 리스너 (이웃삭제)
            holder.deletebutton.setOnClickListener {
                itemClickListner.onClick(it, position)
            }

            // 리사이클러뷰 아이템클릭 리스너 (이웃 리스트 세부정보 확인)
            holder.user_page.setOnClickListener {
                itemClickListner2.onClick2(it, position)
            }

        }
    }



}