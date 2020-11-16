package com.testtask.events.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.testtask.events.R
import com.testtask.events.data.Event
import com.testtask.events.databinding.ActivityEventsItemBinding

class EventsAdapter(var items: List<Event>) :
    RecyclerView.Adapter<EventsAdapter.ViewHolder>() {

    private var onItemClickListener: ((item: Event) -> Unit)? = null

    fun setOnItemClickListener(listener: (item: Event) -> Unit) {
        onItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.activity_events_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind()
    }

    override fun getItemCount() = items.size

    inner class ViewHolder(private val binding: ActivityEventsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.model = items[adapterPosition]

            itemView.setOnClickListener {
                onItemClickListener?.invoke(items[adapterPosition])
            }
        }
    }
}