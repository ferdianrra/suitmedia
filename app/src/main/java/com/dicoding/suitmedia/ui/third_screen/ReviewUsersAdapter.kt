package com.dicoding.suitmedia.ui.third_screen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.suitmedia.databinding.ItemUserBinding
import com.dicoding.suitmedia.network.response.UserItem

class ReviewUsersAdapter: PagingDataAdapter<UserItem, ReviewUsersAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback =onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: UserItem)
    }

    class MyViewHolder(private val binding: ItemUserBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(review: UserItem) {
            val fullName = "${review.firstName} ${review.lastName}"
            binding.tvUserName.text =  fullName
            binding.email.text = review.email
            Glide.with(binding.root)
                .load(review.avatar)
                .circleCrop()
                .into(binding.ivUser)
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review =getItem(position)
        if (review != null) {
            holder.bind(review)
        }
        holder.itemView.setOnClickListener { review?.let { it1 ->
            onItemClickCallback.onItemClicked(
                it1
            )
        }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserItem>() {
            override fun areContentsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: UserItem, newItem: UserItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}