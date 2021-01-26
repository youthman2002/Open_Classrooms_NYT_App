package com.marknorton.openclassroomsnytapp.ui

import android.database.Cursor
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marknorton.openclassroomsnytapp.*
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.BASEURL
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.CATEGORIES
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.EDATE
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.IMAGEBASEURL
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.SDATE
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.SEARCHSTRING
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class SearchResults : AppCompatActivity() {
    private lateinit var headline: String
    private val returnList = ArrayList<Cell>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)
        val actionbar = supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        val categories = intent.getStringExtra(CATEGORIES)
        val sdate = intent.getStringExtra(SDATE)
        val edate = intent.getStringExtra(EDATE)
        val searchString = intent.getStringExtra(SEARCHSTRING)

        // Set up URL for download
        val retrofit = Retrofit.Builder()
            .baseUrl(BASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service & Download URL Data
        val service = retrofit.create(APIService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            // Do the GET request and get response
            val response = service.getSearch(categories!!, sdate!!, edate!!, searchString!!)
            withContext(Dispatchers.Main) {

                val myList = getList(response)

                val recyclerView = findViewById<RecyclerView>(R.id.rvSearchResults2)
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                recyclerView.adapter = ArticleAdapter(myList, applicationContext)
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun getList(response: Response<TechnologyModel>): ArrayList<Cell> {
        if (response.isSuccessful) {
            // Process the response from API
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
                    headline = headlineData?.headline!!
                    val db = Database(applicationContext!!)
                    db.addHeadline(headline)
                    val mediaData = results[h].mediaData
                    var mediaDataUrl = ""
                    // Process Image
                    if (mediaData != null) {
                        for (j in 0 until mediaData.count()) {
                            val mediaUrl = mediaData[j].mediaUrl
                            val mediaFormat = mediaData[j].mediaFormat
                            if (mediaFormat == "Standard Thumbnail") {
                                mediaDataUrl = "$IMAGEBASEURL/$mediaUrl"
                            }
                            if ((mediaDataUrl == "") && (mediaFormat == "thumbLarge")) {
                                mediaDataUrl = "$IMAGEBASEURL/$mediaUrl"
                            }
                        }
                    }
                    // Check Database to see if article has been read
                    val dbResult: Cursor = db.getHeadline(headline)
                    dbResult.moveToFirst()
                    var viewed = "0"
                    if (dbResult.count > 0) {
                        viewed = dbResult.getString(2)
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
        } else {
            Log.e("RETROFIT_ERROR", response.code().toString())
        }
        return returnList
    }

}