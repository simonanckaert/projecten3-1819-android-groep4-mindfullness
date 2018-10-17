package com.groep4.mindfulness.activities

import android.app.TimePickerDialog
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import com.groep4.mindfulness.R
import com.groep4.mindfulness.utils.NotificationUtils
import java.text.DateFormat
import java.text.DecimalFormat
import java.util.*
import java.util.concurrent.TimeUnit

class ActivityReminder : AppCompatActivity() {
    private var mNotified = false

    private var mTimeSetListener: TimePickerDialog.OnTimeSetListener? = null
    private var mDisplayTime: TextView? = null

    val PREFS_REMINDER = "com.groep4.mindfulness.prefs"
    val REMINDER_TIME = "reminder_time"
    var prefs: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)

        val prefs = this.getSharedPreferences(PREFS_REMINDER, 0)
        val storedTime = prefs!!.getLong(REMINDER_TIME, System.currentTimeMillis())

        mDisplayTime = findViewById(R.id.PickReminder)

        mDisplayTime!!.text = DateFormat.getTimeInstance(DateFormat.SHORT).format(storedTime)


        mDisplayTime!!.setOnClickListener {
            val cal = Calendar.getInstance()
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minute = cal.get(Calendar.MINUTE)

            val dialog = TimePickerDialog(
                    this@ActivityReminder,
                    android.R.style.ThemeOverlay_Material,
                    mTimeSetListener,
                    hour,
                    minute,
                    true
            )
            dialog.show()
        }

        mTimeSetListener = TimePickerDialog.OnTimeSetListener { _, hour, minute -> mDisplayTime!!.text = String.format("%02d", hour) + " : " + String.format("%02d", minute)
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.HOUR_OF_DAY, hour)
            calendar.set(Calendar.MINUTE, minute)
            calendar.set(Calendar.SECOND, 0)
            calendar.set(Calendar.MILLISECOND, 0)

            val millis = calendar.timeInMillis
            setReminderNotification(millis)

            val editor = prefs.edit()
            editor.putLong(REMINDER_TIME, millis)
            editor.apply()
        }
    }

    private fun setReminderNotification(time: Long) {
        // Aanmaken van notificatie
        if (!mNotified) {
            NotificationUtils().setNotification(time, this@ActivityReminder)
        } else {
            Toast.makeText(this, "Already notified", Toast.LENGTH_SHORT).show()
        }
    }

}


