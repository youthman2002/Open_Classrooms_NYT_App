package com.marknorton.openclassroomsnytapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.ABOUT
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.BACK
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.HELP
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.NOTIFICATION
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.SEARCH
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.URL
import com.marknorton.openclassroomsnytapp.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        // Get the URL passed from Clicked Article and display in WebView
        val url = intent.getStringExtra(URL)
        if (url != null) {
            // Load the Article in a Webview
            webView.loadUrl(url)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var selectedOption = ""
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
                val intent = Intent(this, TechnologyFragment::class.java)
                startActivity(intent)
            }
            BACK -> {
                this.onBackPressed()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}