package com.marknorton.openclassroomsnytapp

// import com.bumptech.glide.Glide
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


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
        holder.tvSection.text = articleList[position].section
//        holder.imageView.image = articleList[position].imageURL
        holder.tvHeadline.text = articleList[position].headline
        holder.tvDate.text = articleList[position].date

        Log.d("Log","Log-ADAPTERIMAGE:            "+(articleList[position].imageURL))
        Glide.with(holder.imageView)
            .load(articleList[position].imageURL)
            .apply(
                RequestOptions()
                .placeholder(R.drawable.nothumb)
            )
            .into(holder.imageView)

    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvSection: TextView = itemView.findViewById<TextView>(R.id.tvSection)
        val imageView: ImageView = itemView.findViewById<ImageView>(R.id.imageView)
        val tvHeadline: TextView = itemView.findViewById<TextView>(R.id.tvHeadline)
        val tvDate: TextView = itemView.findViewById<TextView>(R.id.tvDate)
    }
}