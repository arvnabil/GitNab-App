package com.example.consumerapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.consumerapp.R
import com.example.consumerapp.model.detailUser.DetailUser
import com.example.consumerapp.retrofit.RetroConfig
import retrofit2.Call
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val userDetail : MutableLiveData<DetailUser> = MutableLiveData()

    fun loadUser(context: Context, username:String){
        RetroConfig.getRetrofit().getDetailUser(username).enqueue(object :retrofit2.Callback<DetailUser>{
            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                if (response.body() != null){
                    userDetail.value = response.body()
                }
            }
        })
    }

    val getDetailUser : LiveData<DetailUser> = userDetail
}