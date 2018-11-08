package com.groep4.mindfulness.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import com.groep4.mindfulness.R
import com.groep4.mindfulness.fragments.FragmentChat
import com.groep4.mindfulness.fragments.FragmentLogin

class ChatActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        showContactFragment(FragmentChat())
    }

    private fun showContactFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.left_in,R.anim.left_out)
                .replace(R.id.frag_content, fragment, FragmentLogin::class.java.name)
                .commit()
    }


}