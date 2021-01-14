package com.marknorton.openclassroomsnytapp.ui

import android.annotation.SuppressLint
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import java.util.*

class TopStoriesFragment : Fragment() {
    private var returnList: ArrayList<Cell> = ArrayList()

    @SuppressLint("DefaultLocale")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_top_stories, container, false)
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.nytimes.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Create Service
        val service = retrofit.create(APIService::class.java)
        CoroutineScope(Dispatchers.IO).launch {
            // Do the GET request and get response
            val response = service.getTopStories()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = response.body()?.results
                    // Process Response
                    if (items != null) {
                        for (i in 0 until items.count()) {
                            var section = items[i].section ?: "N/A section"
                            val newSection = section.capitalize()
                            section = newSection

                            var subSection = items[i].subsection ?: "N/A subSection"
                            val newSubSection = subSection.capitalize()
                            subSection = newSubSection
                            if (subSection != "") {
                                subSection = "  > $subSection"
                            }
                            val headline = items[i].title ?: "N/A title"
                            val db = Database(context!!)
                            db.addHeadline(headline)
                            // Check Database to see if article has been read
                            val dbResult: Cursor = db.getHeadline(headline)
                            dbResult.moveToFirst()
                            var viewed = "0"
                            if (dbResult.count > 0) {
                                viewed = dbResult.getString(2)
                            }
                            val url = items[i].url ?: "N/A url"
                            var publishDate = items[i].publishDate ?: "N/A publishDate"
                            val dateData = publishDate.split("T")
                            publishDate = dateData[0]
                            var mediaDataUrl = ""
                            val medias = items[i].multimedia
                            if (medias != null) {
                                for (j in 0 until medias.count()) {
                                    val mediaUrl = medias[j].mediaUrl ?: "N/A multimedia_url"
                                    val mediaFormat = medias[j].mediaFormat ?: "N/A media_format"
                                    if (mediaFormat == "Standard Thumbnail") {
                                        mediaDataUrl = mediaUrl
                                    }
                                    if ((mediaDataUrl == "") && (mediaFormat == "thumbLarge")) {
                                        mediaDataUrl = mediaUrl
                                    }
                                }
                            }
//                            Log.d("Log", "Log TOP - Viewed=$viewed - headline=$headline")
                            val model = Cell(
                                section,
                                subSection,
                                headline,
                                url,
                                publishDate,
                                mediaDataUrl,
                                viewed
                            )
                            returnList.add(model)
                        }
                    } else {
                        Log.d("Log", "Log - Items was empty")
                    }

                    val recyclerview = rootView.findViewById(R.id.rvTopStories) as RecyclerView
                    recyclerview.layoutManager = LinearLayoutManager(activity)
                    recyclerview.adapter = ArticleAdapter(returnList, context!!)
                } else {
                    Log.e("RETROFIT_ERROR", response.code().toString())
                }
            }
        }

        return rootView
    }
}