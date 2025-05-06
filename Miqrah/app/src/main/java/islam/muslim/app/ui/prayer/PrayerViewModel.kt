package islam.muslim.app.ui.prayer

import android.app.Application
import islam.muslim.app.Constants.TIME_12
import islam.muslim.app.R
import islam.muslim.app.base.BaseViewModel
import islam.muslim.app.utils.PrefsUtils
import islam.muslim.app.scheduler.PrayAlarmReceiver
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.media.AudioAttributes
import android.net.Uri
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

@HiltViewModel
class PrayerViewModel @Inject constructor(val appContext: Application) : BaseViewModel() {

    var NAMAZ_NOTIFICATION_CHANNEL = 0
    private var utils: PrefsUtils = PrefsUtils(appContext)

    fun checktimingsbefore(time: String, endtime: String): Boolean {
        val sdf = SimpleDateFormat(TIME_12)
        try {
            val date1 = sdf.parse(time)
            val date2 = sdf.parse(endtime)
            return date1.before(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    fun checktimingsafter(time: String, endtime: String): Boolean {
        val sdf = SimpleDateFormat(TIME_12)
        try {
            val date1 = sdf.parse(time)
            val date2 = sdf.parse(endtime)
            return date1.after(date2)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    fun getCurrentTime() : String {
        var simpleDateFormat = SimpleDateFormat(TIME_12)
        return simpleDateFormat.format(Calendar.getInstance().time).lowercase(Locale.getDefault())
    }

    fun updateAlarmStatus() {
        val prayerAlarmReceiver = PrayAlarmReceiver()
        prayerAlarmReceiver.cancelAlarm(appContext)
        prayerAlarmReceiver.setAlarm(appContext)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun makeNotificationChannel(
        path: Uri?,
        context: Context,
        notificationManager: NotificationManager,
        isVibrationEnabled: Boolean,
        patternVibrate: LongArray?
    ) {
        notificationManager.deleteNotificationChannel(NAMAZ_NOTIFICATION_CHANNEL.toString())
        NAMAZ_NOTIFICATION_CHANNEL++
        val channel = NotificationChannel(
            NAMAZ_NOTIFICATION_CHANNEL.toString(),
            "Namaz Adhan Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        channel.lightColor = Color.GRAY
        channel.enableLights(true)
        channel.enableVibration(true)
        channel.vibrationPattern = patternVibrate
        channel.enableVibration(isVibrationEnabled)
        channel.description = context.resources.getString(R.string.app_name)
        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION)
            .build()
        channel.setSound(path, audioAttributes)
        if (channel.canBypassDnd()) channel.setBypassDnd(true)
        notificationManager.createNotificationChannel(channel)

    }

}