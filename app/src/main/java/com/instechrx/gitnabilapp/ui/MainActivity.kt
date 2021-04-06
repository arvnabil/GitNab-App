package com.instechrx.gitnabilapp.ui

import android.content.res.TypedArray
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.instechrx.gitnabilapp.R
import com.instechrx.gitnabilapp.adapter.ListUserAdapter
import com.instechrx.gitnabilapp.databinding.ActivityMainBinding
import com.instechrx.gitnabilapp.model.UserModel
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var userData: MutableList<UserModel> = mutableListOf()
    private fun setActionBarTitle(title: String) {
        supportActionBar?.title = title
    }
    private lateinit var title: String
    private var time: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = getString(R.string.app_name)
        binding.toolbar.title = title
        setActionBarTitle(title)
        setSupportActionBar(binding.toolbar)

        binding.rvListUsers.setHasFixedSize(true)

        initData()
        showRecyclerList()

    }

    private fun initData(){
        val username = resources.getStringArray(R.array.username)
        val name = resources.getStringArray(R.array.name)
        val avatar: TypedArray = resources.obtainTypedArray(R.array.avatar)
        val company = resources.getStringArray(R.array.company)
        val location = resources.getStringArray(R.array.location)
        val repository = resources.getStringArray(R.array.repository)
        val followers = resources.getStringArray(R.array.followers)
        val following = resources.getStringArray(R.array.following)
        userData.clear()
        for (i in name.indices) {
            userData.add(
                UserModel(
                username[i],
                name[i],
                avatar.getResourceId(i, 0),
                company[i],
                location[i],
                repository[i],
                followers[i],
                following[i]
            )
            )
        }

        avatar.recycle()
    }

    private fun showRecyclerList() {
        binding.rvListUsers.layoutManager = LinearLayoutManager(this)
        val listUserAdapter = ListUserAdapter(this,userData)
        binding.rvListUsers.adapter = listUserAdapter
    }

    override fun onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            moveTaskToBack(true)
            finish()
            exitProcess(0)
        } else {
            val txtMessage = getString(R.string.txtMessageExit)
            Toast.makeText(this@MainActivity, txtMessage, Toast.LENGTH_SHORT)
                .show()
        }
        time = System.currentTimeMillis()
    }

}