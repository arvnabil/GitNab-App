package com.instechrx.gitnabilapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.instechrx.gitnabilapp.ui.fragment.FollowersFragment
import com.instechrx.gitnabilapp.ui.fragment.FollowingFragment

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