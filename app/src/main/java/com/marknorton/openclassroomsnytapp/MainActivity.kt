package com.marknorton.openclassroomsnytapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.ui.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.addFragment(TopStoriesFragment(), "Top Stories")
        adapter.addFragment(MostPopularFragment(), "Most Popular")
        adapter.addFragment(SearchFragment(), "Search Results")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        var selectedOption = ""
        when (item?.itemId) {
            R.id.menuAbout -> selectedOption = "about"
            R.id.menuHelp -> selectedOption = "help"
            R.id.menuNotification -> selectedOption = "notification"
            R.id.menuSearch -> selectedOption = "search"
//            R.id.exit -> selectedOption = "exit"
        }
        when(selectedOption){
            "about" ->{  val intent = Intent(this, About::class.java)
  //              intent.putExtra("uid", userUID)
                startActivity(intent)}
            "help"->{  val intent = Intent(this, Help::class.java)
                startActivity(intent)}
            "notification" ->{val intent = Intent(this, Notification::class.java)
                startActivity(intent)}
            "search" ->{
                val intent = Intent(this, SearchFragment::class.java)
                startActivity(intent)}
//            "exit" ->{finish()}
        }

        return super.onOptionsItemSelected(item)
    }

}