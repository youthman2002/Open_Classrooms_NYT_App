package com.marknorton.openclassroomsnytapp

// import com.bumptech.glide.Glide
import android.content.Context
import android.content.Intent
import android.database.Cursor
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
import com.marknorton.openclassroomsnytapp.ui.WebViewActivity


/**
 *  Created by Mark Norton on 10/16/2019.
 *
 */

  class ArticleAdapter(private val articleList: ArrayList<ArticleModel>, private val context: Context): RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.story_row_layout, parent, false)
        return ViewHolder(v)

    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val db = Database(context)
        val result: Cursor = db.getHeadline(articleList[position].headline)
        result.moveToFirst()
        // Populate the data into the List to send to History Info
        val viewed=result.getString(2)

        if (viewed == "1") {
            holder.tvHeadline.text = articleList[position].headline
                holder.tvHeadline.setTextColor(Color.parseColor("#000000"))
        } else {
            holder.tvHeadline.text = articleList[position].headline
            holder.tvHeadline.setTextColor(Color.parseColor("#D81B60"))
        }

        holder.tvSection.text = articleList[position].section
        holder.tvDate.text = articleList[position].date

        holder.storyRow.setOnClickListener{
            db.addViewed(articleList[position].headline )
            holder.tvHeadline.setTextColor(Color.parseColor("#000000"))
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra("url", articleList[position].url)
            context.startActivity(intent)
        }

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
        val tvSection: TextView = itemView.findViewById(R.id.tvSection)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val tvHeadline: TextView = itemView.findViewById(R.id.tvHeadline)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        val storyRow: CardView = itemView.findViewById(R.id.storyRow)
    }
}
