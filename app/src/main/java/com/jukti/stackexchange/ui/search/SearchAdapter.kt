package com.jukti.stackexchange.ui.search


import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jukti.stackexchange.data.model.StackExchangeUser
import com.jukti.stackexchange.databinding.RowUserBinding

import javax.inject.Inject
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class SearchAdapter @Inject constructor() : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    internal var collection: MutableList<StackExchangeUser> by Delegates.observable(mutableListOf()) { _, _, _ ->
        notifyDataSetChanged()
    }


    internal var clickListener: (StackExchangeUser) -> Unit = { _ -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = RowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) =
        viewHolder.bind(collection[position], clickListener)

    override fun getItemCount() = collection.size

    class ViewHolder(private val binding: RowUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: StackExchangeUser, clickListener: (StackExchangeUser) -> Unit) {

            binding.reputationTv.text = user.reputation.toString()
            binding.usernameTv.text = user.displayName.trim()
            itemView.setOnClickListener {
                clickListener(user)
            }
        }
    }
}