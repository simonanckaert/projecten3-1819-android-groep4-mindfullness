package com.groep4.mindfulness.activities

import android.app.Fragment
import android.app.FragmentTransaction
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.groep4.mindfulness.R
import com.groep4.mindfulness.utils.NotificationUtils
import java.lang.Exception
import java.util.*

class ActivityReminders : AppCompatActivity() {

    private val mNotificationTime = Calendar.getInstance().timeInMillis + 5000 //Notificatie wordt gezet ~5 seconden na starten van activiteit. (Voor te testen)
    private var mNotified = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminders)

        // Aanmaken van notificatie
        // BUG: Wanneer je de app killed, en de service probeert notificatiecode uit te voeren (background task) crasht de app voor nog onduidelijke reden...
        if (!mNotified) {
            NotificationUtils().setNotification(mNotificationTime, this@ActivityReminders)
        } else {
            Toast.makeText(this, "Already notified", Toast.LENGTH_SHORT).show()
        }
    }
}