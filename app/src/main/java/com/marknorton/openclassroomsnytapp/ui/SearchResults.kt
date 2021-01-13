package com.marknorton.openclassroomsnytapp.ui

import android.database.Cursor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marknorton.openclassroomsnytapp.APIService
import com.marknorton.openclassroomsnytapp.ArticleAdapter
import com.marknorton.openclassroomsnytapp.Database
import com.marknorton.openclassroomsnytapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class SearchResults : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        val categories = intent.getStringExtra("categories")
        val sdate = intent.getStringExtra("sdate")
        val edate = intent.getStringExtra("edate")
        val searchString = intent.getStringExtra("searchString")

        var headline: String
        val returnList = ArrayList<Cell>()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nytimes.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            // Do the GET request and get response
            val response = service.getSearch(categories!!, sdate!!, edate!!, searchString!!)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val body = response.body()?.results
                    val results = body?.docs
                    if (results != null) {
                        for (h in 0 until results.count()) {
                            val section = results[h].section
                            var subSection = results[h].subsection
                            if (subSection != null) {
                                subSection = " > $subSection"
                            }
                            val url = results[h].url
                            var publishDate = results[h].publishDate
                            val dateData = publishDate?.split("T")
                            publishDate = dateData!![0]
                            val headlineData = results[h].headlineData
                            headline = headlineData?.headline ?: "N/A headline"
                            val mediaData = results[h].mediaData
                            var mediaDataUrl = ""
                            if (mediaData != null) {
                                for (j in 0 until mediaData.count()) {
                                    val mediaUrl = mediaData[j].mediaUrl ?: "N/A multimedia_url"
                                    val mediaFormat = mediaData[j].mediaFormat ?: "N/A media_format"
                                    if (mediaFormat == "Standard Thumbnail") {
                                        mediaDataUrl = "https://www.nytimes.com/$mediaUrl"
                                    }
                                    if ((mediaDataUrl == "") && (mediaFormat == "thumbLarge")) {
                                        mediaDataUrl = "https://www.nytimes.com/$mediaUrl"
                                    }
                                }
                            }
                            val db = applicationContext?.let { Database(it) }
                            val dbResult: Cursor = db!!.getHeadline(headline)
                            dbResult.moveToFirst()
                            val viewed = if (dbResult.count > 0) {
                                "1"
                            } else {
                                "0"
                            }
                            val model = Cell(
                                section!!,
                                subSection!!,
                                headline,
                                url!!,
                                publishDate,
                                mediaDataUrl,
                                viewed
                            )
                            returnList.add(model)

                        }
                    }
                }
                val recyclerView = findViewById<RecyclerView>(R.id.rvSearchResults2)
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerView.adapter = ArticleAdapter(returnList, applicationContext)
            }
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}