package com.dorukaneskiceri.kotlinyoutube

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshMainActivity()

        recyclerView_main.setBackgroundColor(Color.rgb(32,32,32))

        recyclerView_main.layoutManager = LinearLayoutManager(this)
        //recyclerView_main.adapter = MainAdapter()
        fetchJSON()
    }

    private fun refreshMainActivity(){
        swipeToRefresh.setProgressBackgroundColorSchemeColor(Color.rgb(0, 0, 0))
        swipeToRefresh.setColorSchemeColors(Color.rgb(255, 255, 255))
        swipeToRefresh.setOnRefreshListener{
            fetchJSON()
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun fetchJSON(){
        println("Attempting to fetch JSON")

        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{

            override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                println("Failed to send to request")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val body = response.body?.string()

                val gson = GsonBuilder()
                val homeFeed = gson.create().fromJson(body,HomeFeed::class.java)
                //async process
                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(homeFeed)
                }
            }

        })
    }
}
