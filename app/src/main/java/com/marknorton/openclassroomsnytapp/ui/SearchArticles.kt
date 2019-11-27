package com.marknorton.openclassroomsnytapp.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.R
import kotlinx.android.synthetic.main.activity_search_articles.*
import org.jetbrains.anko.doAsync
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*


class SearchArticles : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    internal lateinit var dpDate: DatePicker
    var sdate: ArrayList<String> = ArrayList()
    var edate: ArrayList<String> = ArrayList()
//    var dateFormat = SimpleDateFormat("MM-dd-yyyy")
    var dateFormat = SimpleDateFormat("yyyy-MM-dd")
    var urls=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_articles)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        spStartDate!!.setOnItemSelectedListener(this)
        spEndDate!!.setOnItemSelectedListener(this)
        var counter = 0
        while (counter < 30) {

            val cal = Calendar.getInstance()
            cal.add(Calendar.DATE, -counter)
            var myDate = dateFormat.format(cal.time)
            sdate.add(myDate)
            edate.add(myDate)
            counter++
        }


        val startAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, sdate)
        startAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spStartDate.setAdapter(startAdapter)


        val endAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, edate)
        endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spEndDate.setAdapter(endAdapter)

        val buttonClick= findViewById<Button>(R.id.articleSearchButton)
        buttonClick?.setOnClickListener {
            val searchString = etSearch.getText().toString()
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


            doAsync {
               urls =
                    (URL("https://api.nytimes.com/svc/search/v2/articlesearch.json?fq=news_desk:($categories)&q=$searchString&facet_field=day_of_week&facet=true&begin_date=$startDate&end_date=$endDate&api-key=MI5HXzccCCRrvJBlbUJghlzbb2281VRd").readText())
            }
            Thread.sleep(2000)
            Log.d(
                "Log", "Log-URLS-INSEARCH: $urls"
            )
            val intent = Intent(this, SearchResults::class.java)
            intent.putExtra("data", urls)
            startActivity(intent)



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