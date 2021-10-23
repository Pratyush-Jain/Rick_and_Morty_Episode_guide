package com.example.rickandmortyepisodeguide.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmortyepisodeguide.data.pojo.CharacterInfo
import com.example.rickandmortyepisodeguide.databinding.CharacterRowItemBinding
import com.example.rickandmortyepisodeguide.databinding.EpisodeListItemBinding


class RVCharacterAdapter(val data: List<CharacterInfo>): RecyclerView.Adapter<RVCharacterAdapter.viewHolder>() {
    class viewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = CharacterRowItemBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = CharacterRowItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        holder.binding.CharacterName.setText(data[position].name)
        holder.binding.CharacterOrigin.setText(data[position].origin?.name)
        holder.binding.CharacterSpecies.setText(data[position].species)
        holder.binding.CharacterStatus.apply{
            setText(data[position].status)
            setTextColor(when(data[position].status?.lowercase()){
                "alive"-> Color.GREEN
                "dead" -> Color.RED
                "unknown"-> Color.YELLOW
                else->Color.WHITE
            })
        }
        Glide.with(holder.itemView.context)
            .load(data[position].image)
            .centerCrop()
            .into(holder.binding.CharacterImage)
    }

    override fun getItemCount(): Int {
        return if(data.isNullOrEmpty()) 0 else data.size
    }
}