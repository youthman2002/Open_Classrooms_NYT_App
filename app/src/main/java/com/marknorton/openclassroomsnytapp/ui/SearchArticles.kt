package com.marknorton.openclassroomsnytapp.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.R
import kotlinx.android.synthetic.main.activity_search_articles.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.onComplete
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class SearchArticles : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var sdate: ArrayList<String> = ArrayList()
    private var edate: ArrayList<String> = ArrayList()
@SuppressLint("SimpleDateFormat")
var dateFormat = SimpleDateFormat("yyyy-MM-dd")
    private var urls=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_articles)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        spStartDate!!.onItemSelectedListener = this
        spEndDate!!.onItemSelectedListener = this
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
            var categories=""

            if (cbArt.isChecked) {
                categories += "\"Art\" "
            }
            if (cbBusiness.isChecked) {
                categories += "\"Business\" "
            }
            if (cbEntrepreneurs.isChecked) {
                categories += "\"Entrepreneurs\" "
            }
            if (cbPolitics.isChecked) {
                categories += "\"Politics\" "
            }
            if (cbSports.isChecked) {
                categories += "\"Sports\" "
            }
            if (cbTravel.isChecked) {
                categories += "\"Travel\" "
            }

            if((searchString == "")||((!cbArt.isChecked) && (!cbBusiness.isChecked) && (!cbEntrepreneurs.isChecked)&& (!cbPolitics.isChecked) && (!cbSports.isChecked) && (!cbTravel.isChecked))){
                Toast.makeText(this, "You MUST choose a Keyword and at least 1 Category", Toast.LENGTH_LONG).show()
            }else {
                doAsync {
                    urls =
                        (URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:($categories)&q=$searchString&facet_field=day_of_week&facet=true&begin_date=$startDate&end_date=$endDate&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd").readText())
                    onComplete {
                        val intent = Intent(applicationContext, SearchResults::class.java)
                        intent.putExtra("data", urls)
                        startActivity(intent)
                    }
                }
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