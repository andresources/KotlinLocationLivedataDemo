package com.codingwithmitch.kotlinlocationlivedatademo.ex2livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.codingwithmitch.kotlinlocationlivedatademo.R

class MainActivity1 : AppCompatActivity() {
    private lateinit var latLong : TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        latLong = findViewById(R.id.latLong)
        val myLocationViewModel = ViewModelProvider(this).get(MyLocationViewModel::class.java)
        myLocationViewModel.getMyLocationProvider().observe(this,{
            latLong.text = getString(R.string.latLong, it.lat, it.lng)
        })
    }
}