package com.codingwithmitch.kotlinlocationlivedatademo.ex3FlowForeground

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.BackgroundColorSpan
import android.text.style.ForegroundColorSpan
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.codingwithmitch.kotlinlocationlivedatademo.R
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationService : Service() {

    private val serviceScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private lateinit var locationClient: LocationClient

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {

        val spannable = SpannableString("Delete")
        spannable.setSpan(
            ForegroundColorSpan(Color.RED),
            0, // start
            spannable.length, // end
            Spannable.SPAN_EXCLUSIVE_INCLUSIVE
        )

        val intent = Intent(this,LocationService::class.java)
        intent.action = ACTION_STOP
        val pIntent = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        val deleteAction = NotificationCompat.Action.Builder(R.mipmap.ic_launcher,spannable,pIntent).build()
        val notification = NotificationCompat.Builder(this, "location")
            .setContentTitle("Tracking location ...")
            .setContentText("Location: --")
            .setSmallIcon(R.mipmap.ic_launcher)
            .addAction(deleteAction)
            .setOngoing(true)
        val notificationManager = getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager

        locationClient.getLocationUpdates(1000L)
            .catch { e -> e.printStackTrace() }
            .onEach { location ->
                val latitude = location.latitude.toString()
                val longitude = location.longitude.toString()
                val updatedNotification = notification.setContentText(
                    "Location: ($latitude, $longitude)"
                )
                notificationManager.notify(1, updatedNotification.build())
            }
            .launchIn(serviceScope)

        startForeground(1, notification.build())
    }

    private fun stop() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceScope.cancel()
    }

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_STOP = "ACTION_STOP"
    }
}