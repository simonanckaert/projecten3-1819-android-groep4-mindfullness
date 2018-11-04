package com.groep4.mindfulness.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.groep4.mindfulness.R
import com.groep4.mindfulness.model.Message
import android.widget.EditText
import android.widget.ListView
import com.firebase.ui.database.FirebaseListAdapter
import com.firebase.ui.database.FirebaseListOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_chat.*
import java.text.DateFormat


class ChatActivity : AppCompatActivity() {


    private var listView: ListView? = null
    private var btnSend: View? = null
    private var editText: EditText? = null
    private var messages: MutableList<Message>? = null
    private var adapter: FirebaseListAdapter<Message>? = null
    private var dbinstance : FirebaseDatabase? = FirebaseDatabase.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        btnSend = findViewById(R.id.btn_chat_send)

        contactUser.text = "Gebruiker"

        /** Bericht sturen naar de database */
        btnSend!!.setOnClickListener {
            editText = findViewById(R.id.msg_type)
            dbinstance!!.reference.push().setValue(
                    Message(editText!!.text.toString(),
                            FirebaseAuth.getInstance().currentUser!!.displayName.toString()))
            editText!!.setText("")
        }
        displayChatMessages()

    }

    private fun displayChatMessages() {
        listView = findViewById<View>(R.id.list_msg) as ListView

        val options: FirebaseListOptions<Message> = FirebaseListOptions.Builder<Message>()
                .setLayout(R.layout.message)
                .setQuery(dbinstance!!.reference, Message::class.java)
                .build()

        adapter = object : FirebaseListAdapter<Message>(options) {
            override fun populateView(v: View, model: Message, position: Int) {
                val messageText = v.findViewById(R.id.message_text) as TextView
                val messageUser = v.findViewById(R.id.message_user) as TextView
                val messageTime = v.findViewById(R.id.message_time) as TextView

                messageText.text = model.content
                messageTime.text = DateFormat.getDateInstance().format(model.messageTime)
                messageUser.text = model.messageUser
            }}
        listView!!.adapter = adapter
    }


}