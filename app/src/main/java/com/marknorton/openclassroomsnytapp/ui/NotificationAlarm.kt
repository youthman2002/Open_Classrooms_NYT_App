package com.marknorton.openclassroomsnytapp.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.marknorton.openclassroomsnytapp.R
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import java.net.URL

class NotificationAlarm : BroadcastReceiver() {
    init {
        instance = this
    }

    val context: Context = applicationContext()
    override fun onReceive(context: Context, intent: Intent) {
        //Plays when alarm is fired  SET NOTIFICATION HERE
        //Runs Search when Time Notification is triggered

        val urls = intent.getStringExtra("data")

        doAsync {
            val data = (URL(urls).readText())
            onComplete {

                lateinit var notificationChannel: NotificationChannel
                lateinit var builder: NotificationCompat.Builder
                val channelId = context.resources.getString(R.string.channelId)
                val description = context.resources.getString(R.string.channelDescription)

                val notificationManager: NotificationManager =
                    context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                val intents = Intent(context, SearchResults::class.java)
                intents.putExtra("data", data)
                val pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    intents,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )

                // checking if android version is greater than oreo(API 26) or not
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    notificationChannel = NotificationChannel(
                        channelId, description, NotificationManager.IMPORTANCE_HIGH
                    )
                    notificationChannel.enableLights(true)
                    notificationChannel.lightColor = Color.GREEN
                    notificationChannel.enableVibration(false)
                    notificationManager.createNotificationChannel(notificationChannel)

                    builder = NotificationCompat.Builder(context, channelId)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentIntent(pendingIntent)
                } else {
                    builder = NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentIntent(pendingIntent)
                }
                notificationManager.notify(1234, builder.build())

            }
        }
    }

    companion object {
        private var instance: NotificationAlarm? = null
        fun applicationContext(): Context {
            return instance!!.context
        }
    }


}