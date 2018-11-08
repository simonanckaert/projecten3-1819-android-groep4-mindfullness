package com.groep4.mindfulness.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.groep4.mindfulness.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var mAuth:FirebaseAuth
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

        ll_contact.setOnClickListener{
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("key_page", "contact")
            startActivity(intent)

        }
    }


    /** Button Handler voor Kalender Activity*/


    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this,"Account uitgelogd", Toast.LENGTH_SHORT).show()
        mAuth = FirebaseAuth.getInstance()
        mAuth.signOut()
    }
}