package com.marknorton.openclassroomsnytapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 *  Created by Mark Norton on 10/16/2019.
 *
 */

  class TopArticleAdapter(val articleList: ArrayList<ArticleModel>): RecyclerView.Adapter<TopArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.story_row_layout, parent, false)
        return ViewHolder(v)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder?.tvSection?.text = articleList[position].section
        holder?.imageView2?.text = articleList[position].imageURL
        holder?.tvHeadline?.text = articleList[position].headline
        holder?.tvDate?.text = articleList[position].date
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvSection: TextView = itemView.findViewById<TextView>(R.id.tvSection)
        val imageView2: TextView = itemView.findViewById<TextView>(R.id.imageView2)
        val tvHeadline: TextView = itemView.findViewById<TextView>(R.id.tvHeadline)
        val tvDate: TextView = itemView.findViewById<TextView>(R.id.tvDate)
    }
}