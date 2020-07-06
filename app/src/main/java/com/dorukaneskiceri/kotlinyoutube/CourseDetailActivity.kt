package com.dorukaneskiceri.kotlinyoutube

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.course_lesson_row.view.*
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.URL

class CourseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        refreshDetailActivity()

        recyclerView_main.setBackgroundColor(Color.rgb(32,32,32))
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        //recyclerView_main.adapter = CourseDetailAdapter()

        //changing navigation bar title
        val title = intent.getStringExtra(CustomViewHolder.navBarTitle)
        supportActionBar?.title = title

        fetchJSON()
    }

    private fun refreshDetailActivity(){
        swipeToRefresh.setOnRefreshListener {
            fetchJSON()
            swipeToRefresh.isRefreshing = false
        }
    }

    private fun fetchJSON(){
        println("Attempting to fetch JSON from Course Detail")

        //getting video id
        val videoId = intent.getIntExtra(CustomViewHolder.videoId, -1)
        val courseDetailUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=${videoId}"

        val request = Request.Builder().url(courseDetailUrl).build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {

            override fun onFailure(call: okhttp3.Call, e: java.io.IOException) {
                println("Failed to send to request")
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val body = response.body?.string()

                val gson = GsonBuilder()
                val courseLessons = gson.create().fromJson(body,Array<CourseLesson>::class.java)
                //async process
                runOnUiThread {
                    recyclerView_main.adapter = CourseDetailAdapter(courseLessons)
                }
            }

        })
    }

    private class CourseDetailAdapter(private val courseLessons: Array<CourseLesson>): RecyclerView.Adapter<CourseLessonViewHolder>(){

        override fun getItemCount(): Int {
            return courseLessons.size
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseLessonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val mainView = layoutInflater.inflate(R.layout.course_lesson_row, parent,false)
            return CourseLessonViewHolder(mainView)
        }

        override fun onBindViewHolder(holder: CourseLessonViewHolder, position: Int) {
            val courseLesson = courseLessons.get(position)

            holder.customView.textViewCourseLessonTitle.text = courseLesson.name
            holder.customView.textViewEpisode.text = "Episode # ${courseLesson.number}"
            holder.customView.textViewDuration.text = courseLesson.duration

            val courseThumbnail = holder.customView.imageViewCourseLesson
            Picasso.get().load(courseLesson.imageUrl).into(courseThumbnail)

            //sending link to CourseLessonActivity
            holder.courseLesson = courseLesson

        }

    }

    class CourseLessonViewHolder(val customView: View, var courseLesson: CourseLesson? = null): RecyclerView.ViewHolder(customView){
        companion object{
            const val courseLink = "LINK"
        }
        init {
            customView.setOnClickListener{
                val intent = Intent(customView.context, CourseLessonActivity::class.java)
                intent.putExtra(courseLink, courseLesson?.link)
                customView.context.startActivity(intent)

            }
        }
    }

}
