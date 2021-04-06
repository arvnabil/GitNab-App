package com.instechrx.gitnabilapp.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.instechrx.gitnabilapp.R
import com.instechrx.gitnabilapp.databinding.ActivityDetailBinding
import com.instechrx.gitnabilapp.model.UserModel

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }

    lateinit var title: String
    private lateinit var binding:ActivityDetailBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val dataUser: UserModel? =  intent.getParcelableExtra(EXTRA_USER)

        val txtProfile = resources.getString(R.string.txtProfile)
        title = "$txtProfile ${dataUser?.name}"
        binding.toolbar.title = title
        setActionBarTitle(title)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.navigationIcon = resources.getDrawable(R.drawable.ic_baseline_arrow_back_ios_24, null)
        binding.toolbar.setNavigationOnClickListener { finish() }

        Glide.with(this).load(dataUser?.avatar).into(binding.ivAvatar)
        binding.tvUsername.text = dataUser?.username
        binding.tvName.text = dataUser?.name
        binding.tvCompany.text = dataUser?.company
        binding.tvLocation.text = dataUser?.location
        binding.tvRepo.text = dataUser?.repository
        binding.tvFollowers.text = dataUser?.followers
        binding.tvFollowing.text = dataUser?.following

    }
}