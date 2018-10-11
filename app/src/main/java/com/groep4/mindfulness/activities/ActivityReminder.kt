package com.groep4.mindfulness.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity;
import com.groep4.mindfulness.R

import kotlinx.android.synthetic.main.activity_reminder.*

class ActivityReminder : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        setSupportActionBar(toolbar)
    }

    fun show(){

    }

}
