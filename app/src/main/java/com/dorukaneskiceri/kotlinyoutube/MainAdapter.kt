package com.dorukaneskiceri.kotlinyoutube

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_row.view.*

class MainAdapter(private val homeFeed: HomeFeed): RecyclerView.Adapter<CustomViewHolder>(){

    //val videoTitles = listOf("First Comment", "Second Video", "Third Video")

    //number of items
    override fun getItemCount(): Int {
        return homeFeed.videos.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row,parent,false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        //holder.view.textView_video_title.text = videoTitles[position]
        val video = homeFeed.videos.get(position)
        holder.view.textView_video_title.text = video.name
        holder.view.textView_channel_name.text = video.channel.name

        var numOfViews = video.numberOfViews / 1000
        holder.view.textView_numberOf_views.text = numOfViews.toString() + "K views"
        holder.view.textView_upload_date.text = "1 year ago"

        val videoThumbnail = holder.view.imageView_video_thumbnail
        Picasso.get().load(video.imageUrl).into(videoThumbnail)

        val channelThumbnail = holder.view.imageView_channel_profile
        Picasso.get().load(video.channel.profileImageUrl).into(channelThumbnail)

        holder.video = video
    }
}

class CustomViewHolder(val view: View, var video: Video? = null): RecyclerView.ViewHolder(view){

    companion object{
        const val navBarTitle = "VIDEO_TITLE"
        const val videoId = "VIDEO_ID"
    }

    init {
        view.setOnClickListener{
            val intent = Intent(view.context, CourseDetailActivity::class.java)

            intent.putExtra(navBarTitle,video?.name)
            intent.putExtra(videoId,video?.id)
            view.context.startActivity(intent)
        }
    }
}