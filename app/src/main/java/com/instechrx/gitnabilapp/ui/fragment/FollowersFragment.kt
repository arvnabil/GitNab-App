package com.instechrx.gitnabilapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.instechrx.gitnabilapp.viewmodel.FollowViewModel
import com.instechrx.gitnabilapp.adapter.UserAdapter
import com.instechrx.gitnabilapp.databinding.FragmentFollowersBinding
import com.instechrx.gitnabilapp.model.user.User
import com.instechrx.gitnabilapp.ui.DetailActivity

class FollowersFragment : Fragment() {

    private lateinit var binding:FragmentFollowersBinding
    private lateinit var followViewModel: FollowViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowersBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setFollowers()
        binding.shimmerFrameLayout.startShimmer()
        binding.rvFollowers.visibility = View.GONE
        binding.ivEmpty.visibility = View.GONE
    }


    private fun setFollowers() {
        val user = activity?.intent?.getParcelableExtra<User>(DetailActivity.EXTRA_USER)

        if (user != null) {
            followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                FollowViewModel::class.java)
            followViewModel.loadFollowers(context, user.login, "1")
            followViewModel.getFollowers.observe(viewLifecycleOwner, {
                binding.rvFollowers.adapter = UserAdapter(context, it)
                binding.shimmerFrameLayout.stopShimmer()
                binding.shimmerFrameLayout.visibility = View.GONE
                val item = UserAdapter(context,it).itemCount
                if (item == 0 || item < -1) {
                    binding.rvFollowers.visibility = View.GONE
                    binding.ivEmpty.visibility = View.VISIBLE
                } else{
                    binding.rvFollowers.visibility = View.VISIBLE
                    binding.ivEmpty.visibility = View.GONE
                }
            })
        }

       binding.rvFollowers.layoutManager = LinearLayoutManager(context)
    }

}
