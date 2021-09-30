package com.example.tree

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.neighborhood_main.*

class NeighborhoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.neighborhood_main)

        // 이웃 추가 화면으로 이동
        neighborhood_add.setOnClickListener {
            startActivity(Intent(this, NeighborhoodAddActivity::class.java))
            finish()
        }









    }
}