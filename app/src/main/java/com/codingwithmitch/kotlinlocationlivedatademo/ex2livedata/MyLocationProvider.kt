package com.codingwithmitch.kotlinlocationlivedatademo.ex2livedata

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.os.Looper
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

class MyLocationProvider(mContext: Context) : LiveData<MyLocationProvider.MyLocation>() {
    private var mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mContext)

    private val mLocationCallback = object : LocationCallback(){
        override fun onLocationResult(mLocationResult: LocationResult?) {
             var loc = mLocationResult?.locations?.get(0)
             value = loc?.let { MyLocation(it.latitude,loc.longitude) }
        }
    }
    private lateinit var mLocationRequest: LocationRequest

    override fun onActive() {
        super.onActive()
        prepareLocationRequest()
        startLocationUpdates()
    }
    private fun prepareLocationRequest(){
        mLocationRequest = LocationRequest().apply {
            interval = 5000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates(){
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest,mLocationCallback, null)
    }
    override fun onInactive() {
        super.onInactive()
        mFusedLocationProviderClient.removeLocationUpdates(mLocationCallback)
    }
    data class MyLocation(val lat:Double,val lng:Double)
}