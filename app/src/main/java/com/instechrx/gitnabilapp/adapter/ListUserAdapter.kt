package com.instechrx.gitnabilapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.instechrx.gitnabilapp.R
import com.instechrx.gitnabilapp.databinding.ItemListBinding
import com.instechrx.gitnabilapp.model.UserModel
import com.instechrx.gitnabilapp.ui.DetailActivity

open class ListUserAdapter(
    private val context: Context?,
    private val listData: List<UserModel>
) : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_list, viewGroup, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listData[position])
        holder.itemView.setOnClickListener {
            val txtProfile = it.resources.getString(R.string.txtProfile)
            val name = listData[position].name
            Toast.makeText(it.context, "$txtProfile $name", Toast.LENGTH_SHORT)
                .show()

            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_USER, listData[position])
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context?.startActivity(intent)
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemListBinding.bind(itemView)
        fun bind(data: UserModel) {
            itemView.apply {
                binding.tvUsername.text = data.username
                binding.tvName.text = data.name
                binding.tvCompany.text = data.company
                binding.tvLocation.text = data.location
                binding.tvrepo.text = data.repository
                Glide.with(itemView.context)
                    .load(data.avatar)
                    .apply(RequestOptions().override(120, 120))
                    .into(binding.ivAvatar)
            }
        }
    }
}