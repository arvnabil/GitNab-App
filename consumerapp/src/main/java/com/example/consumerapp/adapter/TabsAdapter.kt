package com.example.consumerapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.consumerapp.ui.fragment.FollowersFragment
import com.example.consumerapp.ui.fragment.FollowingFragment

class TabsAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int = 2
    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return FollowingFragment()
            1 -> return FollowersFragment()
        }
        return FollowingFragment()
    }

}