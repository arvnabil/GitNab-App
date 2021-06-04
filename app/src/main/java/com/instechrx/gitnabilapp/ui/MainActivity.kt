package com.instechrx.gitnabilapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import com.instechrx.gitnabilapp.ui.fragment.UserFragment
import com.instechrx.gitnabilapp.R
import com.instechrx.gitnabilapp.databinding.ActivityMainBinding
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

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

        binding.searchUser.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                val bundle = Bundle()
                bundle.putString("QUERY", query)

                binding.ivEmpty.visibility = View.INVISIBLE
                val myFragment = UserFragment()
                myFragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.container,myFragment).commit()
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean = false
        })
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_lang -> {
                val mIntent = Intent(this, SettingsActivity::class.java)
                startActivity(mIntent)
            }
            R.id.action_about -> {
                val mIntent = Intent(this, AboutActivity::class.java)
                startActivity(mIntent)
            }
        }
        return super.onOptionsItemSelected(item)
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