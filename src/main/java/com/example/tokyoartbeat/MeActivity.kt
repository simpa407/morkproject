package com.example.tokyoartbeat

import android.app.TabActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TabHost
class MeActivity : TabActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.me_activity)
        val tabHost = findViewById<View>(android.R.id.tabhost) as TabHost
        var spec: TabHost.TabSpec
        var intent: Intent?

        spec = tabHost.newTabSpec("Home")
        spec.setIndicator("HOME")
        intent = Intent(this, HomeActivity::class.java)
        spec.setContent(intent)
        tabHost.addTab(spec)

        spec = tabHost.newTabSpec("Near")
        spec.setIndicator("NEAR")
        intent = Intent(this, NearActivity::class.java)
        spec.setContent(intent)
        tabHost.addTab(spec)

        spec = tabHost.newTabSpec("Search")
        spec.setIndicator("SEARCH")
        intent = Intent(this, SearchActivity::class.java)
        spec.setContent(intent)
        tabHost.addTab(spec)

        /*spec = tabHost.newTabSpec("Me")
        spec.setIndicator("ME")
        intent = Intent(this, MeActivity::class.java)
        spec.setContent(intent)
        tabHost.addTab(spec)
        tabHost.currentTab = 3
        tabHost.setOnTabChangedListener { tabId ->
            Toast.makeText(applicationContext, tabId, Toast.LENGTH_SHORT).show()
        }*/
    }
}