package com.example.consumerapp.ui

import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.consumerapp.R
import com.example.consumerapp.adapter.UserAdapter
import com.example.consumerapp.database.UserContract
import com.example.consumerapp.databinding.ActivityMainBinding
import com.example.consumerapp.helper.MappingHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var time: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

        contentResolver.registerContentObserver(UserContract.UserColumns.CONTENT_URI_USER, true, myObserver)

        if (savedInstanceState == null){
            loadUserAsync()
        }

        binding.rvFav.layoutManager = LinearLayoutManager(this)
    }
    private fun loadUserAsync(){
        GlobalScope.launch(Dispatchers.Main){
            val deferredUser = async(Dispatchers.IO){
                val cursor = contentResolver.query(UserContract.UserColumns.CONTENT_URI_USER,null,null,null,null)
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
    override fun onBackPressed() {
        val timePlus = 2000
        if (time.plus(timePlus) > System.currentTimeMillis()) {
            super.onBackPressed()
            moveTaskToBack(true)
            finish()
            exitProcess(0)
        } else {
            val txtMessage = getString(R.string.txtMessageExit)
            Toast.makeText(this, txtMessage, Toast.LENGTH_SHORT)
                .show()
        }
        time = System.currentTimeMillis()
    }

}