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
// Making a change in order to import to Travis 3

        // Set up bottom navigation bar and Main Screen View
        val adapter = MyPagerAdapter(supportFragmentManager)

        adapter.addFragment(TopStoriesFragment(), getString(R.string.topStories))
        adapter.addFragment(MostPopularFragment(), getString(R.string.mostPopular))
        adapter.addFragment(TechnologyFragment(), getString(R.string.technology))
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
        val notificationPreferences = getSharedPreferences(NOTIFICATION, 0)
        val notifyChoice = notificationPreferences.getBoolean(NOTIFICATION, false)
        if (notifyChoice) {
            startAlertAtParticularTime()
        }
    }


    private fun startAlertAtParticularTime() {
        //Set up the Alarm Notification at noon to send New Article Notifications
        var categories = ""
        val myIntent = Intent(this, NotificationAlarm::class.java)
        //Get Selected Shared Preferences
        val artPreferences = getSharedPreferences(ART, 0)
        val businessPreferences = getSharedPreferences(BUSINESS, 0)
        val entrepreneurPreferences = getSharedPreferences(ENTREPRENEUR, 0)
        val politicsPreferences = getSharedPreferences(POLITICS, 0)
        val sportsPreferences = getSharedPreferences(SPORTS, 0)
        val travelPreferences = getSharedPreferences(TRAVEL, 0)
        val searchStrings = getSharedPreferences(SEARCH, 0)
        val artPreference = artPreferences.getBoolean(ART, false)
        val businessPreference = businessPreferences.getBoolean(BUSINESS, false)
        val entrepreneurPreference = entrepreneurPreferences.getBoolean(ENTREPRENEUR, false)
        val politicsPreference = politicsPreferences.getBoolean(POLITICS, false)
        val sportsPreference = sportsPreferences.getBoolean(SPORTS, false)
        val travelPreference = travelPreferences.getBoolean(TRAVEL, false)
        val searchString = searchStrings.getString(SEARCH, "")

        // Create the Categories variable for the URL
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

        // Set up the dates to be searched in the URL
        val sdf: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        val cal = Calendar.getInstance()
        cal.add(Calendar.DATE, -1)
        val sdate = sdf.format(cal.time)
        val edate = sdf.format(Date())

        myIntent.putExtra(SDATE, sdate)
        myIntent.putExtra(EDATE, edate)
        myIntent.putExtra(CATEGORIES, categories)
        myIntent.putExtra(SEARCHSTRING, searchString)
        // Create Pending Intent
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
        // Set up Menu
        when (item.itemId) {
            R.id.menuAbout -> selectedOption = ABOUT
            R.id.menuHelp -> selectedOption = HELP
            R.id.menuNotification -> selectedOption = NOTIFICATION
            R.id.menuSearch -> selectedOption = SEARCH
        }
        when (selectedOption) {
            ABOUT -> {
                val intent = Intent(this, About::class.java)
                startActivity(intent)
            }
            HELP -> {
                val intent = Intent(this, Help::class.java)
                startActivity(intent)
            }
            NOTIFICATION -> {
                val intent = Intent(this, NotificationOptions::class.java)
                startActivity(intent)
            }
            SEARCH -> {
                val intent = Intent(this, SearchArticles::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        const val NOTIFICATION = "notification"
        const val ART = "art"
        const val BUSINESS = "business"
        const val ENTREPRENEUR = "entrepreneur"
        const val POLITICS = "politics"
        const val SPORTS = "sports"
        const val TRAVEL = "travel"
        const val SEARCH = "search"
        const val SDATE = "sdate"
        const val EDATE = "edate"
        const val CATEGORIES = "categories"
        const val SEARCHSTRING = "searchstring"
        const val ABOUT = "about"
        const val HELP = "help"
        const val BACK = "back"
        const val DB_NAME = "NYTDatabase"
        const val TABLE_NAME = "headlines"
        const val COLUMN_ID = "id"
        const val COLUMN_HEADLINE = "headline"
        const val COLUMN_VIEWED = "viewed"
        const val DB_VERSION = 1
        const val URL = "url"
        const val BASEURL = "https://api.nytimes.com"
        const val IMAGEBASEURL = "https://www.nytimes.com/"
        const val APIKEY = "MI5HXzccCCRrvJBlbUJghlzbb2281VRd"
        const val NEWSDESK = "newsdesk:"
        const val BEGINDATE = "begin_date"
        const val ENDDATE = "end_date"
        const val Q = "q"

    }
}
