package com.marknorton.openclassroomsnytapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.R
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        val url = intent.getStringExtra("url")
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
            R.id.menuAbout -> selectedOption = "about"
            R.id.menuHelp -> selectedOption = "help"
            R.id.menuNotification -> selectedOption = "notification"
            R.id.menuSearch -> selectedOption = "search"
//            R.id.menuBack -> selectedOption = "back"
        }
        when(selectedOption){
            "about" ->{  val intent = Intent(this, About::class.java)
                //              intent.putExtra("uid", userUID)
                startActivity(intent)}
            "help" -> {
                val intent = Intent(this, Help::class.java)
                startActivity(intent)
            }
            "notification" -> {
                val intent = Intent(this, NotificationOptions::class.java)
                startActivity(intent)
            }
            "search" -> {
                val intent = Intent(this, TechnologyFragment::class.java)
                startActivity(intent)
            }
            "back" -> {
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