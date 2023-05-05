package com.codingwithmitch.kotlinlocationlivedatademo.ex3FlowForeground

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import com.codingwithmitch.kotlinlocationlivedatademo.R

class Ex3MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ex3_main)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ),
            0
        )
    }

    fun funStart(view: View) {
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }
    }
    fun funStop(view: View) {
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_STOP
            // Just send intent
            startService(this)
        }
    }
}