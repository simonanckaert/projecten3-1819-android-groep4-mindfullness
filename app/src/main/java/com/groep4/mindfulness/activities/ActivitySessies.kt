package com.groep4.mindfulness.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.groep4.mindfulness.R

class ActivitySessies : AppCompatActivity() {
    val TAG = "ActivityReminder"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sessies)
    }
}
