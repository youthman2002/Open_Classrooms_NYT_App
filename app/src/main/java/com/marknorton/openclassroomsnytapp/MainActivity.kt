package com.marknorton.openclassroomsnytapp

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.marknorton.openclassroomsnytapp.ui.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.addFragment(TopStoriesFragment(), "Top Stories")
        adapter.addFragment(MostPopularFragment(), "Most Popular")
        adapter.addFragment(TechnologyFragment(), "Technology")
//        adapter.addFragment(SearchResultsFragment(), "Search Results")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)


//        }
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
                android.R.id.home -> {
                    mDrawerLayout.openDrawer(GravityCompat.START)
                    true
                }
                else -> super.onOptionsItemSelected(item)

//            R.id.exit -> selectedOption = "exit"
//            R.id.menuBack -> selectedOption = "back"
            }
            when (selectedOption) {
                "about" -> {
                    val intent = Intent(this, About::class.java)
                    //              intent.putExtra("uid", userUID)
                    startActivity(intent)
                }
                "help" -> {
                    val intent = Intent(this, Help::class.java)
                    startActivity(intent)
                }
                "notification" -> {
                    val intent = Intent(this, Notification::class.java)
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


