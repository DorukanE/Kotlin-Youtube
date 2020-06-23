package com.dorukaneskiceri.kotlinyoutube

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.video_row.view.*

class MainAdapter(val homeFeed: MainActivity.HomeFeed): RecyclerView.Adapter<CustomViewHolder>(){

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
        holder.view.textView_video_title.text = homeFeed.videos.get(position).name
    }
}

class CustomViewHolder(val view: View): RecyclerView.ViewHolder(view){

}