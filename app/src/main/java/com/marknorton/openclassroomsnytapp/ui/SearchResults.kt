package com.marknorton.openclassroomsnytapp.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marknorton.openclassroomsnytapp.ArticleAdapter
import com.marknorton.openclassroomsnytapp.ArticleModel
import com.marknorton.openclassroomsnytapp.Database
import com.marknorton.openclassroomsnytapp.R
import org.json.JSONObject
import java.util.*

class SearchResults : AppCompatActivity() {
    private var db = Database(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        val urls = intent.getStringExtra("data")

        Log.d("Log", "Log --------------- In Search Results!!!")
//        Log.d("Log", "Log --------------- urlsData=$urls")

        //getting recyclerview from xml
        val recyclerView = findViewById<RecyclerView>(R.id.rvSearchResults2)

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val returnList = ArrayList<ArticleModel>()
        var jsonObject: JSONObject? = JSONObject(urls!!)
        jsonObject = jsonObject?.getJSONObject("response")
        val jsonObjects = (jsonObject?.getJSONArray("docs"))

        var image=""
        var url: String
        var pubDate: String
        var section: String
        var theHeadline: String

        for (i in 0 until jsonObjects!!.length()) {
            val c = jsonObjects.getJSONObject(i)
            section = (c.getString("section_name"))
            val imageurl = c.getJSONArray("multimedia")
            for (j in 0 until imageurl.length()) {
                val d = imageurl.getJSONObject(j)
                val subtype = d.getString("subtype")

                if (subtype == "articleInline") {
                    image = (d.getString("url"))
                    image = "https://www.nytimes.com/$image"
                }
            }
            val headline = c.getJSONObject("headline")
            theHeadline = (headline.getString("main"))
            pubDate = (c.getString("pub_date"))
            url = (c.getString("web_url"))
            db.addHeadline(theHeadline)
            returnList.add(ArticleModel(section, image, theHeadline, pubDate, url))
            image = ""
        }

        //creating our adapter
        val adapter = ArticleAdapter(returnList, this)
        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}