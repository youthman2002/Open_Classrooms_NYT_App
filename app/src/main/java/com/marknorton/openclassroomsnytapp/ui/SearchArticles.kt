package com.marknorton.openclassroomsnytapp.ui

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.marknorton.openclassroomsnytapp.R
import kotlinx.android.synthetic.main.activity_search_articles.*
import java.text.SimpleDateFormat
import java.util.*

class SearchArticles : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    internal lateinit var dpDate: DatePicker
    var sdate:ArrayList<String> = ArrayList()
    var edate:ArrayList<String> = ArrayList()
    var dateFormat = SimpleDateFormat("MM-dd-yyyy")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_articles)

        spStartDate!!.setOnItemSelectedListener(this)
        spEndDate!!.setOnItemSelectedListener(this)
        var counter =0
        while(counter < 30) {

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

        spStartDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Toast.makeText(this@SearchArticles, getString(R.string.sDate) + " " + sdate[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }

        val endAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, edate)
        endAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spEndDate.setAdapter(endAdapter)

        spEndDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                Toast.makeText(this@SearchArticles, getString(R.string.eDate) + " " + edate[position], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Code to perform some action when nothing is selected
            }
        }


    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }
}
