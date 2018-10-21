package com.groep4.mindfulness.fragments

import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.groep4.mindfulness.R
import com.groep4.mindfulness.utils.NotificationUtils
import kotlinx.android.synthetic.main.activity_page.*
import java.text.DateFormat
import java.util.*
import android.text.style.UnderlineSpan
import android.text.SpannableString



class FragmentReminder : Fragment() {
    private var mNotified = false

    private var mTimeSetListener: TimePickerDialog.OnTimeSetListener? = null
    private var mDisplayTime: TextView? = null

    val PREFS_REMINDER = "com.groep4.mindfulness.prefs"
    val REMINDER_TIME = "reminder_time"

    companion object {
        fun newInstance(): FragmentReminder {
            return FragmentReminder()
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_reminder, container, false)

        activity!!.tr_page.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPink))
        //activity!!.iv_page.setImageResource(R.mipmap.reminders)
        activity!!.tv_page.setText(R.string.reminder)

        //
        // Time uit sharedpreferences halen en invoegen in timepicker textview
        val prefs = context?.getSharedPreferences(PREFS_REMINDER, 0)
        val storedTime = prefs!!.getLong(REMINDER_TIME, System.currentTimeMillis())
        mDisplayTime = view.findViewById(R.id.tv_reminder_timepicker)
        val content = SpannableString(DateFormat.getTimeInstance(DateFormat.SHORT).format(storedTime))
        content.setSpan(UnderlineSpan(), 0, content.length, 0)
        mDisplayTime!!.text = content

        mDisplayTime!!.setOnClickListener {
            val cal = Calendar.getInstance()
            val hour = cal.get(Calendar.HOUR_OF_DAY)
            val minute = cal.get(Calendar.MINUTE)
            val dialog = TimePickerDialog(
                    activity,
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
        //

        return view
    }

    private fun setReminderNotification(time: Long) {

        // Indien tijd in het verleden ligt, voeg er 1 dag aan toe.
        // (nodig anders wordt notificatie direct na het setten al uitgevoerd)
        var fixedTime = time
        val diff = Calendar.getInstance().timeInMillis - time
        if (diff > 0) {
            fixedTime += 86400000
        }

        // Aanmaken van notificatie
        if (!mNotified) {
            NotificationUtils().setNotification(fixedTime, activity!!)
        } else {
            Toast.makeText(activity, "Already notified", Toast.LENGTH_SHORT).show()
        }
    }

}