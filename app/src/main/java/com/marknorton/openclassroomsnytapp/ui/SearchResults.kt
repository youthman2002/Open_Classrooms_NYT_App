package com.marknorton.openclassroomsnytapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.marknorton.openclassroomsnytapp.Database
import com.marknorton.openclassroomsnytapp.R

class SearchResults : AppCompatActivity() {
    private var db = Database(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        val urls = intent.getStringExtra("data")

        //getting recyclerview from xml
        val recyclerView = findViewById<RecyclerView>(R.id.rvSearchResults2)

    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}