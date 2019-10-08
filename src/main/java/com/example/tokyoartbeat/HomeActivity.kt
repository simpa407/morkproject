package com.example.tokyoartbeat

import android.app.TabActivity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TabHost

class HomeActivity : TabActivity() {
    private lateinit var mDataAccess: DatabaseAccess
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val tabHost = findViewById<View>(android.R.id.tabhost) as TabHost
        var newsListView : ListView = tabHost.findViewById(R.id.newsList)
        mDataAccess = DatabaseAccess(this)
        var newsList = mDataAccess.getAllNews()
        var adapter = ListAdapter(this, newsList)
        newsListView.adapter = adapter

        tabHost.addTab(tabHost.newTabSpec("News").setIndicator("NEWS").setContent(R.id.newsList))
        newsListView.onItemClickListener = object : AdapterView.OnItemClickListener {

            override fun onItemClick(parent: AdapterView<*>, view: View,
                                     position: Int, id: Long) {

                val itemValue = newsListView.getItemAtPosition(position) as News
                val eventDetails = mDataAccess.getEventDetails()
                val intent = Intent(this@HomeActivity, NewsActivity::class.java)
                intent.putExtra("photo", itemValue.thumb_img)
                intent.putExtra("nameVenue", eventDetails[position].nameVenue)
                intent.putExtra("nameEvent", eventDetails[position].nameEvent)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    intent.putExtra("startDate", eventDetails[position].startDate.toString())
                    intent.putExtra("endDate", eventDetails[position].endDate.toString())
                }
                intent.putExtra("going_count", eventDetails[position].going_count)
                intent.putExtra("description", eventDetails[position].descriptpion)
                intent.putExtra("category", eventDetails[position].category)
                intent.putExtra("address", eventDetails[position].address)
                intent.putExtra("artist", eventDetails[position].artist)
                startActivity(intent)

            }
        }
        tabHost.addTab(tabHost.newTabSpec("Popular").setIndicator("POPULAR").setContent(R.id.popularList))
    }
}