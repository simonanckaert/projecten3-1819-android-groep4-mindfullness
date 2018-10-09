package com.groep4.mindfulness.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.groep4.mindfulness.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /** Button handler voor Sessies Activity */
    fun openSessies(view: View) {
        val intent = Intent(this, ActivitySessies::class.java)
        startActivity(intent)
    }

}