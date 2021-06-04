package com.instechrx.gitnabilapp.ui

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.instechrx.gitnabilapp.adapter.UserAdapter
import com.instechrx.gitnabilapp.database.UserContract.UserColumns.Companion.CONTENT_URI_USER
import com.instechrx.gitnabilapp.databinding.ActivityFavoriteBinding
import com.instechrx.gitnabilapp.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btSettings.setOnClickListener {
            startActivity(Intent(applicationContext, SettingsActivity::class.java))
        }
        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        val myObserver = object : ContentObserver(handler){
            override fun onChange(selfChange: Boolean) {
                loadUserAsync()
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI_USER, true, myObserver)

        if (savedInstanceState == null){
            loadUserAsync()
        }

        binding.rvFav.layoutManager = LinearLayoutManager(this)

    }

    private fun loadUserAsync(){
        GlobalScope.launch(Dispatchers.Main){
            val deferredUser = async(Dispatchers.IO){
                val cursor = contentResolver.query(CONTENT_URI_USER,null,null,null,null)
                MappingHelper.mapCursorToList(cursor)
            }

            val user = deferredUser.await()
            if (user.isNotEmpty()){
                binding.rvFav.adapter = UserAdapter(applicationContext, user)
                binding.message.visibility = View.INVISIBLE
            }else{
                binding.message.visibility = View.VISIBLE
            }
        }
    }
}