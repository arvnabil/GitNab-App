package com.instechrx.gitnabilapp.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.instechrx.gitnabilapp.R
import com.instechrx.gitnabilapp.databinding.ItemListBinding
import com.instechrx.gitnabilapp.model.user.User
import com.instechrx.gitnabilapp.ui.DetailActivity

class UserAdapter(
        private val context:Context?,
        private val userList: List<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_list, parent, false))
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER, userList[position])
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(intent)
        }
    }

    class ViewHolder(view : View)  : RecyclerView.ViewHolder(view) {
        private val binding = ItemListBinding.bind(itemView)

        fun bind(user: User) {
            Glide.with(itemView.context)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_baseline_account_circle_24)
                .into(binding.ivAvatar)

            binding.tvName.text = user.login
            binding.tvLink.text = user.htmlUrl
            binding.tvLink.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(user.htmlUrl))
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                itemView.context.startActivity(intent)
            }

        }
    }

}