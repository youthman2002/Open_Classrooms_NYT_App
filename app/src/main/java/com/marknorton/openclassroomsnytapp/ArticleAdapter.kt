package com.marknorton.openclassroomsnytapp

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.marknorton.openclassroomsnytapp.MainActivity.Companion.URL
import com.marknorton.openclassroomsnytapp.ui.Cell
import com.marknorton.openclassroomsnytapp.ui.WebViewActivity

/**
 *  Created by Mark Norton on 10/16/2019.
 *
 */
class ArticleAdapter(private val articleList: ArrayList<Cell>, private val context: Context) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    private val db: Database = Database(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.story_row_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // If the headline has been viewed, change the color to black
        val viewed = articleList[position].viewed
        if (viewed == "1") {
            holder.tvHeadline.text = articleList[position].title
            holder.tvHeadline.setTextColor(Color.parseColor("#000000"))
        } else {
            holder.tvHeadline.text = articleList[position].title
            holder.tvHeadline.setTextColor(Color.parseColor("#D81B60"))
        }

        holder.tvSection.text = articleList[position].section
        holder.tvSubSection.text = articleList[position].subsection
        holder.tvDate.text = articleList[position].publishDate

        // Set the onClickListener to take the article to the webview
        holder.storyRow.setOnClickListener {
            // Add the headline to the SQLite database
            db.addViewed(articleList[position].title)
            // Change the color to show the article has been viewed
            holder.tvHeadline.setTextColor(Color.parseColor("#000000"))
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(URL, articleList[position].url)
            context.startActivity(intent)
        }

        //Process the Image for the article, or replace with the "nothumb" image
        Glide.with(holder.imageView)
            .load(articleList[position].multimedia)
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
        val tvSection: TextView = itemView.findViewById(R.id.tvSection)
        val tvSubSection: TextView = itemView.findViewById(R.id.tvSubSection)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvHeadline: TextView = itemView.findViewById(R.id.tvHeadline)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val storyRow: CardView = itemView.findViewById(R.id.storyRow)
    }
}
