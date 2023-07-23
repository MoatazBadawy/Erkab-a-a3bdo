package com.moataz.erkab_a_a3bdo // ktlint-disable package-name

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.time.LocalTime
import java.time.temporal.ChronoUnit

class NotificationHelper(private val context: Context) {

    fun showNotificationIfTimeWithinRange() {
        val currentTime = LocalTime.now()
        val clockHead = currentTime.truncatedTo(ChronoUnit.HOURS)
        val startTime = clockHead.minusMinutes(11)
        val endTime = clockHead.plusMinutes(10)

        val isInRange = currentTime.isAfter(startTime) && currentTime.isBefore(endTime)

        if (isInRange) {
            showNotification("تمام ياغالي", "أمان إركب")
        } else {
            showNotification("خلي بالك", "مش أمان، أوعى تركب")
        }
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "my_notification_channel"
        val notificationId = 1

        // Create a NotificationChannel for Android Oreo and above
        val notificationChannel = NotificationChannel(
            channelId,
            "My Notifications",
            NotificationManager.IMPORTANCE_DEFAULT,
        )

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)

        // Create the notification
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // Show the notification
        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            notify(notificationId, builder.build())
        }
    }
}
