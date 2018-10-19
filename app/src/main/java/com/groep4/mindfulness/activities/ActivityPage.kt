package com.groep4.mindfulness.activities

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.groep4.mindfulness.R
import com.groep4.mindfulness.fragments.FragmentReminder
import com.groep4.mindfulness.fragments.FragmentSessieList


class ActivityPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page)

        val myIntent = intent // parent intent ophalen
        val keyPage = myIntent.getStringExtra("key_page") // key_page value ophalen

        // naargelang 'key_page value' (meegegeven via de MainActivity) kiezen welke Fragment er geladen moet worden
        if (savedInstanceState == null) {
            var fragment: Fragment = when(keyPage) {
                "sessie" -> FragmentSessieList()
                "reminder" -> FragmentReminder()
                else -> FragmentSessieList()
            }

            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.ll_page, fragment, "pageContent")
                    .commit()
        }
    }
}
