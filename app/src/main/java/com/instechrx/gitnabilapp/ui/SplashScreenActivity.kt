package com.instechrx.gitnabilapp.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import com.instechrx.gitnabilapp.R
import com.instechrx.gitnabilapp.databinding.ActivitySplashScreenBinding

class SplashScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val myAnim = AnimationUtils.loadAnimation(this, R.anim.mytransition)
        binding.tv.startAnimation(myAnim)
        binding.iv.startAnimation(myAnim)
        val i = Intent(this, MainActivity::class.java)
        val timer: Thread = object : Thread() {
            override fun run() {
                try {
                    sleep(2000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } finally {
                    startActivity(i)
                }
            }
        }
        timer.start()
    }
}