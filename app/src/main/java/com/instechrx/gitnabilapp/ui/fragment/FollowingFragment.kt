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
import com.instechrx.gitnabilapp.databinding.FragmentFollowingBinding
import com.instechrx.gitnabilapp.model.user.User
import com.instechrx.gitnabilapp.ui.DetailActivity

class FollowingFragment : Fragment() {

    private lateinit var followViewModel: FollowViewModel
    private lateinit var binding:FragmentFollowingBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentFollowingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setFollowing()
        binding.shimmerFrameLayout.startShimmer()
        binding.rvFollowing.visibility = View.GONE
        binding.ivEmpty.visibility = View.GONE
    }


    private fun setFollowing() {
        val user = activity!!.intent.getParcelableExtra<User>(DetailActivity.EXTRA_USER)
        if (user != null) {
            followViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                FollowViewModel::class.java)
            followViewModel.loadFollowing(context, user.login, "1")
            followViewModel.getFollowing.observe(viewLifecycleOwner, {
                binding.rvFollowing.adapter = UserAdapter(context, it)
                binding.shimmerFrameLayout.stopShimmer()
                binding.shimmerFrameLayout.visibility = View.GONE
                val item = UserAdapter(context,it).itemCount
                if (item == 0 || item < -1) {
                    binding.rvFollowing.visibility = View.INVISIBLE
                    binding.ivEmpty.visibility = View.VISIBLE
                } else{
                    binding.rvFollowing.visibility = View.VISIBLE
                    binding.ivEmpty.visibility = View.GONE
                }
            })
        }

        binding.rvFollowing.layoutManager = LinearLayoutManager(context)
    }

}
