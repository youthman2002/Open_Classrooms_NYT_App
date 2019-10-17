package com.marknorton.openclassroomsnytapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marknorton.openclassroomsnytapp.ArticleAdapter
import com.marknorton.openclassroomsnytapp.ArticleModel
import com.marknorton.openclassroomsnytapp.R


class TopStoriesFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //     inflater.inflate(R.layout.fragment_top_stories, container, false)

       val list = ArrayList<ArticleModel>()
        list.add(ArticleModel("Section1","Image1","headline1","date1"))
        list.add(ArticleModel("Section2","Image2","headline2","date2"))
        list.add(ArticleModel("Section3","Image3","headline3","date3"))
        list.add(ArticleModel("Section4","Image4","headline4","date4"))

        val rootView = inflater.inflate(R.layout.fragment_top_stories, container, false)
        val recyclerview = rootView.findViewById(R.id.rvTopStories) as RecyclerView
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.adapter = ArticleAdapter(list)
        return rootView

    }

}

