package com.xavi.testapirandom.ui.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.xavi.testapirandom.data.model.Result
import com.xavi.testapirandom.databinding.ItemUserBinding
import com.xavi.testapirandom.utils.CircleTransform

class ListAdapter(
    private val listSearch: List<Result>
    // private var listener: OnClickListFlickrListener
) :
    RecyclerView.Adapter<ListAdapter.ListUsersHolder>() {
    private lateinit var binding: ItemUserBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListUsersHolder {
        binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListUsersHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ListUsersHolder, position: Int) {
        val item = listSearch[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = listSearch.size

    inner class ListUsersHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(itemUser: Result) {
            val name = itemUser.name.first
            val last = itemUser.name.last
            binding.nameTextView.text = "$name $last"
            binding.mailTextView.text = itemUser.email
            Picasso.get().load(itemUser.picture.thumbnail).transform(CircleTransform())
                .into(binding.imageImageView)
        }
    }
}