package com.groep4.mindfulness.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.groep4.mindfulness.R
import java.util.*

class ActivityReminder : AppCompatActivity() {


    private var mTimeSetListener: TimePickerDialog.OnTimeSetListener? = null
     private var mDisplayTime: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)


        mDisplayTime = findViewById(R.id.PickReminder) as TextView

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
        mTimeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute -> mDisplayTime!!.text = hour.toString() + " " + minute.toString()  }

    }

}


