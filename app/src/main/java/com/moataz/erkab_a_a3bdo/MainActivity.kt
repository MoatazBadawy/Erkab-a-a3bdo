package com.moataz.erkab_a_a3bdo // ktlint-disable package-name

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.moataz.erkab_a_a3bdo.databinding.ActivityMainBinding
import java.time.LocalTime
import java.time.temporal.ChronoUnit
import java.util.Calendar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        initNotification()
        displayCurrentTime()
        isTimeWithinRange(LocalTime.now())
    }

    private fun initNotification() {
        val notificationHelper = NotificationHelper(this)
        notificationHelper.showNotificationIfTimeWithinRange()
    }

    private fun displayCurrentTime() {
        val currentTime = getCurrentTimeUsingCalendar()
        binding.timeTextView.text = currentTime
    }

    private fun getCurrentTimeUsingCalendar(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR)
        val minute = calendar.get(Calendar.MINUTE)

        return String.format("%02d:%02d", hour, minute)
    }

    private fun isTimeWithinRange(currentTime: LocalTime) {
        val clockHead = currentTime.truncatedTo(ChronoUnit.HOURS)
        val startTime = clockHead.minusMinutes(11)
        val endTime = clockHead.plusMinutes(10)

        if (currentTime.isAfter(startTime)) {
            binding.saveOrNotTextView.text = "مش أمان، أوعى تركب"
            binding.saveOrNotTextView.setTextColor(binding.saveOrNotTextView.context.getColor(R.color.red))
        } else if (currentTime.isAfter(endTime)) {
            binding.saveOrNotTextView.text = "أمان، إركب"
            binding.saveOrNotTextView.setTextColor(binding.saveOrNotTextView.context.getColor(R.color.green))
        }
    }
}
