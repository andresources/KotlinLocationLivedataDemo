package com.codingwithmitch.kotlinlocationlivedatademo.ex2livedata

import android.app.Application
import androidx.lifecycle.AndroidViewModel

class MyLocationViewModel(application: Application) : AndroidViewModel(application) {
    private val myLocationProvider = MyLocationProvider(application)
    fun getMyLocationProvider() =myLocationProvider

}