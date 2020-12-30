package com.marknorton.openclassroomsnytapp


import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.ui.*
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {
    private lateinit var alarmManager: AlarmManager
    private lateinit var pendingIntent: PendingIntent
//    private val mReceiver = MyAlarm()

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

        // Set up notifications
//        getSharedPreferences("notification", 0)

        startAlertAtParticularTime()

    }

    private fun startAlertAtParticularTime() {
        val myIntent = Intent(this, MyAlarm::class.java)
        val categories = "Health"
        val startDate = "2020-01-01"
        val endDate = "2020-12-12"
        val searchString = "Covid"
        val urls =
            "https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:($categories)&q=$searchString&facet_field=day_of_week&facet=true&begin_date=$startDate&end_date=$endDate&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd"
        myIntent.putExtra("data", urls)
        pendingIntent = PendingIntent.getBroadcast(
            this.applicationContext, 280192, myIntent, PendingIntent.FLAG_CANCEL_CURRENT
        )
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar[Calendar.HOUR_OF_DAY] = 8
        calendar[Calendar.MINUTE] = 40
        alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent
        )
        Log.d("Log", "Log ------------------- Main - Alarm Set 08:40")

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
}
