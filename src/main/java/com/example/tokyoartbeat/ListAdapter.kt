package com.example.tokyoartbeat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import android.graphics.BitmapFactory
import android.os.Build
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import kotlin.collections.ArrayList


class ListAdapter(context: Context, private val values: ArrayList<News>) :
    ArrayAdapter<News>(context, R.layout.news_activity, values) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val inflater = context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val newsRow: News = this.values[position]

        val rowView = inflater.inflate(R.layout.news_activity, parent, false)
        var imgView: ImageView = rowView.findViewById(R.id.imgView)
        val outImage = newsRow.thumb_img
        val imageStream = ByteArrayInputStream(outImage)
        val theImage = BitmapFactory.decodeStream(imageStream)
        imgView.setImageBitmap(theImage)
        var title : TextView = rowView.findViewById(R.id.txtView1)
        title.text = newsRow.title
        var details = rowView.findViewById<TextView>(R.id.details)
        lateinit var date : String
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val currentDate = LocalDateTime.now()
            date = when (val count = ChronoUnit.DAYS.between(newsRow.publicDate, currentDate).toInt()){
                0 -> "Today"
                1 -> "Yesterday"
                else -> "$count days ago"
            }
        }
        var author = newsRow.author
        var feed = newsRow.feed
        details.text = "$date by $author on $feed"
        return rowView
    }
}