package com.groep4.mindfulness.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.groep4.mindfulness.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // belangrijk key_page mee te geven om juiste fragment te kunnen laden vanuit eenzelfde activity! (Zie AcitivityPage)
        ll_sessies.setOnClickListener {
            val intent = Intent(this, ActivityPage::class.java)
            intent.putExtra("key_page", "sessies")
            startActivity(intent)
        }

        ll_reminder.setOnClickListener {
            val intent = Intent(this, ActivityPage::class.java)
            intent.putExtra("key_page", "reminder")
            startActivity(intent)
        }
    }

    /** Button Handler voor Contact Activity*/
    fun openContact(view: View) {
        val intent = Intent(this, ActivityContact::class.java)
        startActivity(intent)
    }
    /** Button Handler voor Contact Activity*/
    fun openContact(view: View) {
        val intent = Intent(this, ActivityContact::class.java)
        startActivity(intent)
    }
}