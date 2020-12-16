package com.marknorton.openclassroomsnytapp

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.ui.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

// private var mNotifyManager: NotificationManager? = null
//  private val mReceiver = MyAlarm.NotificationReceiver()

class MainActivity : AppCompatActivity() {
    private val requestCode = 100
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
    private var mNotifyManager: NotificationManager? = null
    private val mReceiver = NotificationReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.addFragment(TopStoriesFragment(), "Top Stories")
        adapter.addFragment(MostPopularFragment(), "Most Popular")
        adapter.addFragment(TechnologyFragment(), "Technology")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)

        getSharedPreferences("notification", 0)
            // Creating the pending intent to send to the BroadcastReceiver
            alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(this, MyAlarm::class.java)
            pendingIntent = PendingIntent.getBroadcast(this, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT)

            // Setting the specific time for the alarm manager to trigger the intent, in this example, the alarm is set to go off at 23:30, update the time according to your need
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()
            calendar.set(Calendar.HOUR_OF_DAY, 2)
            calendar.set(Calendar.MINUTE, 51)
            calendar.set(Calendar.SECOND, 0)

            // Starts the alarm manager
            alarmManager.setRepeating(
                AlarmManager.RTC,
                calendar.timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
            )

        // Create the notification channel.
     createNotificationChannel()
        // Register the broadcast receiver to receive the update action from the notification.
        registerReceiver(
            mReceiver,
            IntentFilter(ACTION_UPDATE_NOTIFICATION)
        )
    }
    override fun onDestroy() {
        unregisterReceiver(mReceiver)
        super.onDestroy()
        // Cancels the pendingIntent if it is no longer needed after this activity is destroyed.
        alarmManager.cancel(pendingIntent)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var selectedOption = ""
        when (item.itemId) {
            R.id.menuAbout -> selectedOption = "about"
            R.id.menuHelp -> selectedOption = "help"
            R.id.menuNotification -> selectedOption = "notification"
            R.id.menuSearch -> selectedOption = "search"
        }
        when(selectedOption){
            "about" ->{  val intent = Intent(this, About::class.java)
                startActivity(intent)}
            "help"->{  val intent = Intent(this, Help::class.java)
                startActivity(intent)}
            "notification" ->{val intent = Intent(this, Notification::class.java)
                startActivity(intent)}
            "search" ->{
                val intent = Intent(this, SearchArticles::class.java)
                startActivity(intent)}
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        // Constants for the notification actions buttons.
        const val ACTION_UPDATE_NOTIFICATION =
            "com.marknorton.openclassroomsnytapp.ACTION_UPDATE_NOTIFICATION"
        // Notification channel ID.
        const val PRIMARY_CHANNEL_ID = "primary_notification_channel"
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    private fun createNotificationChannel() { // Create a notification manager object.
        mNotifyManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (Build.VERSION.SDK_INT >=
            Build.VERSION_CODES.O
        ) { // Create the NotificationChannel with all the parameters.
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.RED
            notificationChannel.enableVibration(true)
            notificationChannel.description = getString(R.string.notification_channel_description)
            mNotifyManager!!.createNotificationChannel(notificationChannel)
        }
    }
    /**
     * The broadcast receiver class for notifications.
     * Responds to the update notification pending intent action.
     */
    inner class NotificationReceiver : BroadcastReceiver() {
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
                "Log", "Log-Notification Clicked"
            )
        }
    }
}
