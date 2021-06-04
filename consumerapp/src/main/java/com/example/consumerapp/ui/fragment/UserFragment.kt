package com.example.consumerapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.facebook.shimmer.ShimmerFrameLayout
import com.example.consumerapp.viewmodel.BaseViewModel
import com.example.consumerapp.adapter.UserAdapter
import com.example.consumerapp.databinding.FragmentUserBinding

class UserFragment : Fragment(){

    private lateinit var baseViewModel: BaseViewModel
    private lateinit var shimmerContainer: ShimmerFrameLayout
    private lateinit var binding: FragmentUserBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setUser()
        shimmerContainer = binding.shimmerFrameLayout
        shimmerContainer.startShimmer()
        binding.rvUser.visibility = View.GONE
        binding.ivEmpty.visibility = View.GONE
    }

    private fun setUser(){
        val user = this.arguments!!.getString("QUERY")
        if (user != null){
            baseViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
                BaseViewModel::class.java)
            baseViewModel.loadUser(context, user)
            baseViewModel.getListUser.observe(viewLifecycleOwner, {
                binding.rvUser.adapter = UserAdapter(context, it)

                shimmerContainer.stopShimmer()
                shimmerContainer.visibility = View.GONE

                val item = UserAdapter(context,it).itemCount
                if (item == 0 || item < -1) {
                    binding.rvUser.visibility = View.INVISIBLE
                    binding.ivEmpty.visibility = View.VISIBLE
                } else{
                    binding.rvUser.visibility = View.VISIBLE
                    binding.ivEmpty.visibility = View.GONE
                }
            })
            binding.rvUser.layoutManager = LinearLayoutManager(context)
        }
    }
}
