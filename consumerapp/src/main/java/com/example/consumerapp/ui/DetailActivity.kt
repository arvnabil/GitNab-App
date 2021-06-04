package com.example.consumerapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.consumerapp.R
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.consumerapp.adapter.TabsAdapter
import com.example.consumerapp.databinding.ActivityDetailBinding
import com.example.consumerapp.model.user.User
import com.example.consumerapp.viewmodel.DetailViewModel


class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var shimmerContainer: ShimmerFrameLayout
    private var title: String = ""
    private lateinit var binding: ActivityDetailBinding

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.navigationIcon = resources.getDrawable(
            R.drawable.ic_baseline_arrow_back_ios_24,
            null
        )
        binding.toolbar.setNavigationOnClickListener { finish() }

        initTabs()
        initData()

        shimmerContainer = binding.shimmerFrameLayout
        shimmerContainer.startShimmer()
    }
    private fun initData() {
        val user = intent.getParcelableExtra<User>(EXTRA_USER)
        if (user != null) {

            detailViewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(DetailViewModel::class.java)
            detailViewModel.loadUser(applicationContext, user.login)
            detailViewModel.getDetailUser.observe(this, {

                val txtProfile = resources.getString(R.string.txtProfile)
                title = "$txtProfile ${it.login}"
                binding.toolbar.title = title
                setActionBarTitle(title)

                shimmerContainer.stopShimmer()
                shimmerContainer.visibility = View.GONE

                Glide.with(applicationContext)
                    .load(it.avatarUrl)
                    .placeholder(R.drawable.ic_baseline_account_circle_24)
                    .into(binding.ivAvatar)
                binding.tvName.text = it.name
                binding.tvRepo.text = it.publicRepos.toString()
                binding.tvFollowing.text = it.following.toString()
                binding.tvFollowers.text = it.followers.toString()
                binding.tvUsername.text = it.login
                binding.tvBio.text = it.bio ?: getString(R.string.nullBioData)
                binding.tvLocation.text = it.location ?: getString(R.string.nullLocationData)
                binding.tvCompany.text = it.company ?: getString(R.string.nullCompanyData)
                binding.tvEmail.text = it.email ?: getString(R.string.nullEmailData)
            })
        }
    }

    private fun initTabs() {
        binding.viewpager2.adapter = TabsAdapter(this)
        TabLayoutMediator(
            binding.tabs, binding.viewpager2
        ) { tab, position ->
            when (position) {
                0 -> tab.text = resources.getString(R.string.txtFollowing)
                1 -> tab.text = resources.getString(R.string.txtFollowers)
            }
        }.attach()
    }
}