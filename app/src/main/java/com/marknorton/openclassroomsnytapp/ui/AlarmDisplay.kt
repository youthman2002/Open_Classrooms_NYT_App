package com.marknorton.openclassroomsnytapp.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.marknorton.openclassroomsnytapp.R

class AlarmDisplay : AppCompatActivity() {
    lateinit var notificationManager: NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var builder: NotificationCompat.Builder
    private val channelId = "NYT Alarm"
    private val description = "NYT Article Description"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_alarm_display)
        Log.d("Log", "Log ---------------------------------ALARM DISPLAY ----- ALARM RECEIVED!!!!!")

        val urls = intent.getStringExtra("data")

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, SearchResults::class.java)
        intent.putExtra("data", urls)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, intent, PendingIntent.FLAG_UPDATE_CURRENT
        )

        // RemoteViews are used to use the content of
        // some different layout apart from the current activity layout
//    val contentView = RemoteViews(packageName, R.layout.activity_alarm_display)

        // checking if android version is greater than oreo(API 26) or not
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(
                channelId, description, NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.GREEN
            notificationChannel.enableVibration(false)
            notificationManager.createNotificationChannel(notificationChannel)

            builder = NotificationCompat.Builder(this, channelId)
//             .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.drawable.ic_launcher_background
                    )
                )
                .setContentIntent(pendingIntent)
        } else {
            builder = NotificationCompat.Builder(this)
//            .setContent(contentView)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setLargeIcon(
                    BitmapFactory.decodeResource(
                        this.resources,
                        R.drawable.ic_launcher_background
                    )
                )
                .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234, builder.build())
    }

}
