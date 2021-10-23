package com.example.rickandmortyepisodeguide.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyepisodeguide.EpisodeActivity
import com.example.rickandmortyepisodeguide.MainActivity
import com.example.rickandmortyepisodeguide.RVEpisodeClickListener
import com.example.rickandmortyepisodeguide.data.pojo.EpisodeInfo
import com.example.rickandmortyepisodeguide.databinding.EpisodeListItemBinding

class RVEpisodesAdapter(val data: List<EpisodeInfo>, private val clickListener: RVEpisodeClickListener): RecyclerView.Adapter<RVEpisodesAdapter.viewHolder>() {
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = EpisodeListItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = EpisodeListItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding.episodeTitle.setText(data.get(position).name)
        holder.binding.episodeNumber.setText(data.get(position).episode)
        holder.itemView.setOnClickListener {
            clickListener.onEpisodeCLickListener(data[position].id)
        }
    }

    override fun getItemCount(): Int {
      return if(data.isNullOrEmpty()) 0 else data.size
    }
}