package com.marknorton.openclassroomsnytapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.ui.MostPopularFragment
import com.marknorton.openclassroomsnytapp.ui.MyPagerAdapter
import com.marknorton.openclassroomsnytapp.ui.SearchResultsFragment
import com.marknorton.openclassroomsnytapp.ui.TopStoriesFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val adapter = MyPagerAdapter(supportFragmentManager)
        adapter.addFragment(TopStoriesFragment(), "Top Stories")
        adapter.addFragment(MostPopularFragment(), "Most Popular")
        adapter.addFragment(SearchResultsFragment(), "Search Results")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }
}