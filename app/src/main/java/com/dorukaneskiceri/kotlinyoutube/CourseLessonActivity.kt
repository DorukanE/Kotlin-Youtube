package com.dorukaneskiceri.kotlinyoutube

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_course_lesson.*

class CourseLessonActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_lesson)

        val courseLessonLink = intent.getStringExtra(CourseDetailActivity.CourseLessonViewHolder.courseLink)

        webViewCourseLesson.settings.javaScriptEnabled = true
        webViewCourseLesson.settings.loadWithOverviewMode = true
        webViewCourseLesson.settings.useWideViewPort = true

        webViewCourseLesson.loadUrl(courseLessonLink)

    }
}
