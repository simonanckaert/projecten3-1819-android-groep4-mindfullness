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


class ChatActivity : AppCompatActivity() {


    private var listView: ListView? = null
    private var btnSend: View? = null
    private var editText: EditText? = null
    private var messages: MutableList<Message>? = null
    private var adapter: FirebaseListAdapter<Message>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        listView = findViewById<View>(R.id.list_msg) as ListView
        btnSend = findViewById(R.id.btn_chat_send)
        btnSend!!.setOnClickListener {
            editText = findViewById<EditText>(R.id.msg_type)
            FirebaseDatabase.getInstance().getReference().push().setValue(Message(editText.toString(), FirebaseAuth.getInstance().currentUser!!.displayName))
            editText!!.setText("")

            displayChatMessages()
        }
    }

    private fun displayChatMessages() {

        val options: FirebaseListOptions<Message> = FirebaseListOptions.Builder<Message>()
                .setLayout(R.layout.left_chat_bubble)
                .setQuery(FirebaseDatabase.getInstance().reference, Message::class.java)
                .build()

        adapter = object : FirebaseListAdapter<Message>(options) {
            override fun populateView(v: View, model: Message, position: Int) {
                val messageText = v.findViewById(R.id.txt_msg) as TextView
                messageText.text = model.content
            }}
        listView!!.adapter = adapter
    }
}