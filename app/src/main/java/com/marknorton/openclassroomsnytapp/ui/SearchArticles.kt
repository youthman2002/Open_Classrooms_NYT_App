package com.marknorton.openclassroomsnytapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.ART
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.BUSINESS
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.CATEGORIES
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.EDATE
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.ENTREPRENEUR
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.POLITICS
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.SDATE
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.SEARCHSTRING
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.TRAVEL
import com.marknorton.openclassroomsnytapp.R
import kotlinx.android.synthetic.main.activity_search_articles.*
import java.text.SimpleDateFormat
import java.util.*


class SearchArticles : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var sdate: ArrayList<String> = ArrayList()
    private var edate: ArrayList<String> = ArrayList()

    @SuppressLint("SimpleDateFormat")
    var dateFormat = SimpleDateFormat("yyyy-MM-dd")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_articles)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        spStartDate!!.onItemSelectedListener = this
        spEndDate!!.onItemSelectedListener = this

        // Set up the Calendar to search specific dates
        var counter = 0
        while (counter < 30) {
            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -counter)
            val myDate = dateFormat.format(cal.time)
            sdate.add(myDate)
            edate.add(myDate)
            counter++
        }

        val startAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sdate)
        startAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spStartDate.adapter = startAdapter

        val endAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, edate)
        endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spEndDate.adapter = endAdapter

        val buttonClick= findViewById<Button>(R.id.articleSearchButton)
        buttonClick?.setOnClickListener {
            val searchString = etSearch.text.toString()
            val startDate = spStartDate.selectedItem.toString()
            val endDate = spEndDate.selectedItem.toString()
            var categories = ""

            if (cbArt.isChecked) {
                categories += ART
            }
            if (cbBusiness.isChecked) {
                categories += BUSINESS
            }
            if (cbEntrepreneurs.isChecked) {
                categories += ENTREPRENEUR
            }
            if (cbPolitics.isChecked) {
                categories += POLITICS
            }
            if (cbSports.isChecked) {
                categories += POLITICS
            }
            if (cbTravel.isChecked) {
                categories += TRAVEL
            }
            categories += " "
            if ((searchString == "") || ((!cbArt.isChecked) && (!cbBusiness.isChecked) && (!cbEntrepreneurs.isChecked) && (!cbPolitics.isChecked) && (!cbSports.isChecked) && (!cbTravel.isChecked))) {
                Toast.makeText(
                    this,
                    "You MUST choose a Keyword and at least 1 Category",
                    Toast.LENGTH_LONG
                ).show()
            } else {
                val intent = Intent(applicationContext, SearchResults::class.java)
                intent.putExtra(CATEGORIES, categories)
                intent.putExtra(SDATE, startDate)
                intent.putExtra(EDATE, endDate)
                intent.putExtra(SEARCHSTRING, searchString)
                startActivity(intent)
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }
    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}