package com.example.tree

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.neighborhood_add.*

class NeighborhoodAddActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.neighborhood_add)


        pre_button.setOnClickListener {
            finish()
        }






    }
}