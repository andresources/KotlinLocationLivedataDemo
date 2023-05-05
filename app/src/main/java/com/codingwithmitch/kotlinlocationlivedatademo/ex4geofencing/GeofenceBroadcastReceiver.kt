package com.codingwithmitch.kotlinlocationlivedatademo.ex4geofencing

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingEvent

private const val TAG = "GeofenceBroadcastReceiv"

class GeofenceBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        if (geofencingEvent.hasError()) {
            val errorMessage = GeofenceStatusCodes.getStatusCodeString(geofencingEvent.errorCode)
            Log.e(TAG, errorMessage)
            return
        }

        val geofenceTransition = geofencingEvent.geofenceTransition
        Toast.makeText(context,"onReceive-"+geofenceTransition,Toast.LENGTH_LONG).show()
        if (geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER||geofenceTransition == Geofence.GEOFENCE_TRANSITION_DWELL||geofenceTransition == Geofence.GEOFENCE_TRANSITION_EXIT) {
            val triggeringGeofences = geofencingEvent.triggeringGeofences

            // Obtaining transition details as a String.
            /*val geofenceTransitionDetails = getGeofenceTransitionDetails(
                context!!,
                geofenceTransition,
                triggeringGeofences
            )*/
            Toast.makeText(context,"Entered....",Toast.LENGTH_LONG).show()

            // Creating and sending Notification
            val notificationManager = ContextCompat.getSystemService(
                context!!,
                NotificationManager::class.java
            ) as NotificationManager

            notificationManager.sendGeofenceEnteredNotification(context)
        } else {
            Log.e(TAG, "Invalid type transition $geofenceTransition")
        }
    }
}