@file:Suppress("DEPRECATION")

package com.instechrx.gitnabilapp.ui

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.PreferenceManager
import android.provider.Settings
import android.util.Log
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.instechrx.gitnabilapp.R
import com.instechrx.gitnabilapp.databinding.ActivitySettingsBinding
import com.instechrx.gitnabilapp.reminder.MyReceiver
import java.util.*

class SettingsActivity : AppCompatActivity() {
    companion object {
        private const val ID_DAILY_REMINDER = 1000
    }
    private lateinit var binding: ActivitySettingsBinding
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.btnLang.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(intent)
        }

        binding.btnFav.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        binding.tvAuthor.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/arvnabil")))
        }

        val daily: Switch = binding.switchDaily
        val sharedPref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val dailyy = sharedPref.getInt("daily_notification", 0)
        daily.isChecked = dailyy == 1
        switchHandling()
    }

    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private fun switchHandling() {
        val daily:Switch = binding.switchDaily
        val sharedPref:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        daily.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                setDaily(applicationContext)
                val editor: SharedPreferences.Editor = sharedPref.edit()
                editor.putInt("daily_notification", 1)
                editor.apply()
                Toast.makeText(this, resources.getString(R.string.msg_onswitch), Toast.LENGTH_SHORT)
                        .show()
            } else {
                dailyOff(this)
                val editor: SharedPreferences.Editor = sharedPref.edit()
                editor.putInt("daily_notification", 0)
                editor.apply()
                Toast.makeText(this, resources.getString(R.string.msg_offswitch), Toast.LENGTH_SHORT)
                        .show()

            }
        }
    }

    private fun setDaily(context: Context) {
        val intent = Intent(context, MyReceiver::class.java)
        val pendingIntent =
                PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, intent, 0)
        val alarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        Log.d("daily", "setDaily")
        alarmManager.setInexactRepeating(
                AlarmManager.RTC_WAKEUP,
                getReminderTime().timeInMillis,
                AlarmManager.INTERVAL_DAY,
                pendingIntent
        )
    }

    private fun getReminderTime(): Calendar {
        val calendar = Calendar.getInstance()
        calendar[Calendar.HOUR_OF_DAY] = 9
        calendar[Calendar.MINUTE] = 0
        calendar[Calendar.SECOND] = 0
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1)
        }
        return calendar
    }

    private fun dailyOff(context: Context) {
        Log.d("daily", "RemoveDaily")
        val intent = Intent(context, MyReceiver::class.java)
        val alarmManager =
                context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val pendingIntent =
                PendingIntent.getBroadcast(context, ID_DAILY_REMINDER, intent, 0)
        Objects.requireNonNull(alarmManager).cancel(pendingIntent)
    }

}