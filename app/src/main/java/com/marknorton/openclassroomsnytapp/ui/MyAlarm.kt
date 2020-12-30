package com.marknorton.openclassroomsnytapp.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import java.net.URL

class MyAlarm : BroadcastReceiver() {
    init {
        instance = this
    }

    val context: Context = applicationContext()
    override fun onReceive(context: Context, intent: Intent) {
        //Plays when alarm is fired  SET NOTIFICATION HERE
        //Runs Search when Time Notification is triggered

        val urls = intent.getStringExtra("data")

        Log.d("Log", "Log ---------------------------------MyAlarm ALARM RECEIVED!!!!!")
        Log.d("Log", "Log - urls=$urls")

        doAsync {
            val data = (URL(urls).readText())
            onComplete {
                val intents = Intent(context, AlarmDisplay::class.java)
                Log.d("Log", "Log - data=$data")
                intents.putExtra("data", data)
                startActivity(context, intents, null)
            }
        }
    }

    companion object {
        private var instance: MyAlarm? = null

        fun applicationContext(): Context {
            return instance!!.context
        }
    }


}