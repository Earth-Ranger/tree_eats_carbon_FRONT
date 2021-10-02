package com.example.tree

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import com.example.tree.Main.MainTreeActivity

class allRankingActivity : AppCompatActivity() {

    var drawerLayout: DrawerLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.all_ranking_main)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
    }

    fun ClickMenu(view: View?) {
        MainTreeActivity.openDrawer(drawerLayout)
    }

    fun ClickLogo(view: View?) {
        MainTreeActivity.closeDrawer(drawerLayout)
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
        MainTreeActivity.closeDrawer(drawerLayout)
    }
}