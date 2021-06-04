package com.example.consumerapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.consumerapp.R
import com.example.consumerapp.databinding.ActivityAboutBinding


class AboutActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutBinding

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
    private var title: String = ""
    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val txtAbout = resources.getString(R.string.txtAbout)
        title = txtAbout
        binding.toolbar.title = title
        setActionBarTitle(title)
        setSupportActionBar(binding.toolbar)
        binding.toolbar.navigationIcon = resources.getDrawable(
            R.drawable.ic_baseline_arrow_back_ios_24,
            null
        )
        binding.toolbar.setNavigationOnClickListener { finish() }

        binding.imgAboutPhoto.setImageDrawable(getDrawable(R.drawable.nabil))
        Glide.with(this)
            .load(R.drawable.nabil)
            .into(binding.imgAboutPhoto)
    }
}
