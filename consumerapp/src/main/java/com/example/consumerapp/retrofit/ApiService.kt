package com.example.consumerapp.retrofit


import com.example.consumerapp.BuildConfig
import com.example.consumerapp.model.detailUser.DetailUser
import com.example.consumerapp.model.user.ResponseUser
import com.example.consumerapp.model.user.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers(BuildConfig.API_KEY)
    fun getSearchData(
        @Query("q") query: String
    ) : Call<ResponseUser>


    @GET("users/{username}")
    @Headers(BuildConfig.API_KEY)
    fun getDetailUser(
        @Path("username") username : String
    ) : Call<DetailUser>


    @GET("users/{username}/followers")
    @Headers(BuildConfig.API_KEY)
    fun getFollowersData(
        @Path("username") username : String,
        @Query("page") page : String
    ) : Call<List<User>>


    @GET("users/{username}/following")
    @Headers(BuildConfig.API_KEY)
    fun getFollowingData(
        @Path("username") username : String,
        @Query("page") page : String
    ) : Call<List<User>>
}