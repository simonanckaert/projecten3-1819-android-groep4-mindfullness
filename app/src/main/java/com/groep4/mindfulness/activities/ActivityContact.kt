package com.groep4.mindfulness.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.groep4.mindfulness.R



class ActivityContact : AppCompatActivity() {

    private var mail: TextView? = null
    private var subject: TextView? = null
    private var text: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)

        val sendButton = findViewById(R.id.sendMailButton) as Button
        val telnr = findViewById<View>(R.id.telNr) as TextView


        telnr.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:0477957485")
            startActivity(intent)
        }

        mail = findViewById(R.id.input_email)
        subject = findViewById(R.id.input_subject)
        text = findViewById(R.id.input_text)

        sendButton.setOnClickListener{
            val i = Intent(Intent.ACTION_SEND)
            i.setType("message/rfc822")
            i.putExtra(Intent.EXTRA_EMAIL, arrayOf(mail!!.text.toString()))
            i.putExtra(Intent.EXTRA_SUBJECT, subject!!.text.toString())
            i.putExtra(Intent.EXTRA_TEXT, text!!.text.toString())
            startActivity(Intent.createChooser(i, "Stuur mail"))
        }

    }

}
