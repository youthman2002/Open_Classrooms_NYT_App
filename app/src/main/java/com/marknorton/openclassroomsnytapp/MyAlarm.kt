package com.marknorton.openclassroomsnytapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat


/**
 *  Created by Mark Norton on 12/17/2019.
 *
 */
class MyAlarm (val applicationContext: Context) : BroadcastReceiver() {
    init {
        instance = this
    }

    //    val context: Context = applicationContext()
    val context: Context = applicationContext()
    private var mNotifyManager: NotificationManager? = null
    override fun onReceive(context: Context, intent: Intent) {
        //Plays when alarm is fired  SET NOTIFICATION HERE
        Log.d("Log", "ALARM Triggered")
//        val mediaPlayer =
//            MediaPlayer.create(context, Settings.System.DEFAULT_RINGTONE_URI)
//        mediaPlayer.start()

        //Runs Search when Time Notification is triggered

        createNotificationChannel()

        sendNotification()
    }


    private fun createNotificationChannel() { // Create a notification manager object.
        mNotifyManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Notification channels are only available in OREO and higher.
// So, add a check on SDK version.
        if (Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) { // Create the NotificationChannel with all the parameters.
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                context.getString(R.string.notification_channel_name),
//                Resources.getSystem().getString(android.R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = context.getString(R.string.notification_channel_description)
            mNotifyManager!!.createNotificationChannel(notificationChannel)
        }
    }
    /**
     * Creates and delivers a simple notification.
     */
    private fun sendNotification() { // Sets up the pending intent to update the notification.
// Corresponds to a press of the Update Me! button.
        val updateIntent = Intent(ACTION_UPDATE_NOTIFICATION)
        val updatePendingIntent = PendingIntent.getBroadcast(context, NOTIFICATION_ID, updateIntent, PendingIntent.FLAG_ONE_SHOT
        )
        // Build the notification with all of the parameters using helper
// method.
        val notifyBuilder = notificationBuilder
        // Add the action button using the pending intent.
        notifyBuilder.addAction(
            R.drawable.ic_update,
            context.getString(R.string.update), updatePendingIntent
        )
        // Deliver the notification.
        mNotifyManager!!.notify(NOTIFICATION_ID, notifyBuilder.build())
    }// Set up the pending intent that is delivered when the notification
// is clicked.
    // Build the notification with all of the parameters.

    /**
     * Helper method that builds the notification.
     *
     * @return NotificationCompat.Builder: notification build with all the
     * parameters.
     */
    private val notificationBuilder: NotificationCompat.Builder
        get() { // Set up the pending intent that is delivered when the notification
            // is clicked.
            val notificationIntent = Intent(context, Notification::class.java)
            val notificationPendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            // Build the notification with all of the parameters.
            return NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setContentTitle(context.getString(R.string.notification_title))
                .setContentText(context.getString(R.string.notification_text))
                .setSmallIcon(R.drawable.ic_android)
                .setAutoCancel(true).setContentIntent(notificationPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
        }





    /**
     * The broadcast receiver class for notifications.
     * Responds to the update notification pending intent action.
     */
    class NotificationReceiver : BroadcastReceiver() {
        /**
         * Receives the incoming broadcasts and responds accordingly.
         *
         * @param context Context of the app when the broadcast is received.
         * @param intent The broadcast intent containing the action.
         */
        override fun onReceive(
            context: Context,
            intent: Intent
        ) { // Update the notification.
            Log.d(
                "Log", "Log-Notification Clicked - Set Up Search Here"
            )

            // TODO go to article


        }
    }


    companion object {
        // Constants for the notification actions buttons.
        private const val ACTION_UPDATE_NOTIFICATION =
            "com.marknorton.openclassroomsnytapp.notifyme.ACTION_UPDATE_NOTIFICATION"
        // Notification channel ID.
        private const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
        // Notification ID.
        private const val NOTIFICATION_ID = 0

        private var instance: MyAlarm? = null
        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}