package com.marknorton.openclassroomsnytapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.marknorton.openclassroomsnytapp.ArticleModel
import com.marknorton.openclassroomsnytapp.R
import com.marknorton.openclassroomsnytapp.TopArticleAdapter


class MostPopularFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //     inflater.inflate(R.layout.fragment_top_stories, container, false)

        val list = ArrayList<ArticleModel>()
        list.add(ArticleModel("SectionB1","https://www.nytimes.com/images/2019/10/08/insider/08insider-barnardpapers/merlin_161922126_5f249167-3c97-40f1-b5e9-a47f42b115af-articleLarge.jpg","headlineB1","dateB1"))
        list.add(ArticleModel("SectionB2","https://www.nytimes.com/images/2019/10/08/insider/08insider-barnardpapers/merlin_161922126_5f249167-3c97-40f1-b5e9-a47f42b115af-articleLarge.jpg","headlineB2","dateB2"))
        list.add(ArticleModel("SectionB3","https://www.nytimes.com/images/2019/10/08/insider/08insider-barnardpapers/merlin_161922126_5f249167-3c97-40f1-b5e9-a47f42b115af-articleLarge.jpg","headlineB3","dateB3"))
        list.add(ArticleModel("SectionB4","https://www.nytimes.com/images/2019/10/08/insider/08insider-barnardpapers/merlin_161922126_5f249167-3c97-40f1-b5e9-a47f42b115af-articleLarge.jpg","headlineB4","dateB4"))

        val rootView = inflater.inflate(R.layout.fragment_most_popular, container, false)
        val recyclerview = rootView.findViewById(R.id.rvMostPopular) as RecyclerView
        recyclerview.layoutManager = LinearLayoutManager(activity)
        recyclerview.adapter = TopArticleAdapter(list)
        return rootView

    }

}