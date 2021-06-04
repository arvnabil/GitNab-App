package com.example.consumerapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.consumerapp.R
import com.example.consumerapp.model.user.ResponseUser
import com.example.consumerapp.model.user.User
import com.example.consumerapp.retrofit.RetroConfig
import retrofit2.Call
import retrofit2.Response

class BaseViewModel : ViewModel() {

    private val listUser : MutableLiveData<List<User>> = MutableLiveData()
    fun loadUser(context: Context?, query:String){
        RetroConfig.getRetrofit().getSearchData(query).enqueue(object : retrofit2.Callback<ResponseUser>{
            override fun onFailure(call: Call<ResponseUser>, t: Throwable) {
                Toast.makeText(context, context?.getString(R.string.check_connection), Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<ResponseUser>, response: Response<ResponseUser>) {
                if(response.body() != null){
                    listUser.postValue(response.body()?.items)
                }
            }
        })
    }

    val getListUser : LiveData<List<User>> = listUser
}