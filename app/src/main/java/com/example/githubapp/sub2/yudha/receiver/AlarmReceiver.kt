package com.example.githubapp.sub2.yudha.receiver

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.githubapp.sub2.yudha.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "channel_01"
        private const val CHANNEL_NAME = "Github Yudha Reminder"
        private const val TIME_FORMAT = "HH:mm"
        private const val EXTRA_MESSAGE = "extra_message"
        private const val EXTRA_TYPE = "extra_type"
        private const val ID_REPEATING = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        sendNotif(context)
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun sendNotif(context: Context) {
        val intent = context.packageManager.getLaunchIntentForPackage("com.example.githubapp.sub2.yudha")
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_notifications)
            .setContentTitle(context.resources.getString(R.string.app_name))
            .setContentText(context.resources.getString(R.string.find_notif))
            .setAutoCancel(true)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)

            builder.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)

            val notification = builder.build()
            notificationManager.notify(NOTIFICATION_ID, notification)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun setRepeatAlarm(context: Context, type: String, time: String, message: String){
        if (TIME_FORMAT.isDateInvalid(time)) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_TYPE, type)

        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_REPEATING, intent, PendingIntent.FLAG_IMMUTABLE)
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent)
        Toast.makeText(context, context.getString(R.string.set_alarm), Toast.LENGTH_SHORT).show()
    }

    private fun String.isDateInvalid(time: String): Boolean {
        return try {
            val tm = SimpleDateFormat(this, Locale.getDefault())
            tm.isLenient = false
            tm.parse(time)
            false
        } catch (e: ParseException){
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun cancelAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiver::class.java)
        val reqCode = ID_REPEATING
        val pendingIntent = PendingIntent.getBroadcast(context, reqCode, intent, PendingIntent.FLAG_IMMUTABLE)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, context.getString(R.string.cancel_alarm), Toast.LENGTH_SHORT).show()
    }
}