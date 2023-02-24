package com.example.pruebadeingreso.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pruebadeingreso.R
import com.example.pruebadeingreso.databinding.PublishItemBinding
import com.example.pruebadeingreso.data.model.PublishModel

open class PublishAdapter(private val mPubs: List<PublishModel>) : RecyclerView.Adapter<PublishAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.publish_item, parent, false))
    }

    override fun getItemCount(): Int {
        return mPubs.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mPubs[position]
        holder.bind(item)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = PublishItemBinding.bind(itemView)
        fun bind(pub: PublishModel){
            binding.pubTitle.text = pub.title
            binding.pubBody.text = pub.body
        }
    }

}