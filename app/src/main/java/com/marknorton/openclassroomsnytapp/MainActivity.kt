package com.marknorton.openclassroomsnytapp

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.ui.*
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        // Set up bottom navigation bar
        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.addFragment(TopStoriesFragment(), "Top Stories")
        adapter.addFragment(MostPopularFragment(), "Most Popular")
        adapter.addFragment(TechnologyFragment(), "Technology")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        val notificationPreferences = getSharedPreferences("notification", 0)
        val notifyChoice = notificationPreferences.getBoolean("notification", false)
        if (notifyChoice) {
            startAlertAtParticularTime()
        }
    }


    private fun startAlertAtParticularTime() {
        var categories = ""
        val myIntent = Intent(this, NotificationAlarm::class.java)
        val artPreferences = getSharedPreferences("art", 0)
        val businessPreferences = getSharedPreferences("business", 0)
        val entrepreneurPreferences = getSharedPreferences("entrepreneur", 0)
        val politicsPreferences = getSharedPreferences("politics", 0)
        val sportsPreferences = getSharedPreferences("sports", 0)
        val travelPreferences = getSharedPreferences("travel", 0)
        val searchStrings = getSharedPreferences("search", 0)

        val artPreference = artPreferences.getBoolean("art", false)
        val businessPreference = businessPreferences.getBoolean("business", false)
        val entrepreneurPreference = entrepreneurPreferences.getBoolean("entrepreneur", false)
        val politicsPreference = politicsPreferences.getBoolean("politics", false)
        val sportsPreference = sportsPreferences.getBoolean("sports", false)
        val travelPreference = travelPreferences.getBoolean("travel", false)
        val searchString = searchStrings.getString("search", "")

        if (artPreference) {
            categories = "\"Art\" $categories"
        }
        if (businessPreference) {
            categories = "\"Business\" $categories"
        }
        if (entrepreneurPreference) {
            categories = "\"Entrepreneurs\" $categories"
        }
        if (politicsPreference) {
            categories = "\"Politics\" $categories"
        }
        if (sportsPreference) {
            categories = "\"Sports\" $categories"
        }
        if (travelPreference) {
            categories = "\"Travel\" $categories"
        }

        val sdf: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        val startDate = sdf.format(cal.time)
        val endDate = sdf.format(Date())

        val urls =
            "https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:($categories)&q=$searchString&facet_field=day_of_week&facet=true&begin_date=$startDate&end_date=$endDate&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd"
        myIntent.putExtra("data", urls)
        pendingIntent = PendingIntent.getBroadcast(
            this.applicationContext, 280192, myIntent, PendingIntent.FLAG_CANCEL_CURRENT
        )
        cal.timeInMillis = System.currentTimeMillis()
        cal[Calendar.HOUR_OF_DAY] = 12
        cal[Calendar.MINUTE] = 0
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, cal.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )

    }

    override fun onDestroy() {
        //       unregisterReceiver(mReceiver)
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
        when (selectedOption) {
            "about" -> {
                val intent = Intent(this, About::class.java)
                startActivity(intent)
            }
            "help" -> {
                val intent = Intent(this, Help::class.java)
                startActivity(intent)
            }
            "notification" -> {
                val intent = Intent(this, NotificationOptions::class.java)
                startActivity(intent)
            }
            "search" -> {
                val intent = Intent(this, SearchArticles::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
