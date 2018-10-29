package com.groep4.mindfulness.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.groep4.mindfulness.R
import com.groep4.mindfulness.model.ChatBubble
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.groep4.mindfulness.adapters.MessageAdapter


class ChatActivity : AppCompatActivity() {


    private var listView: ListView? = null
    private var btnSend: View? = null
    private var editText: EditText? = null
    var myMessage = true
    private var ChatBubbles: MutableList<ChatBubble>? = null
    private var adapter: ArrayAdapter<ChatBubble>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        ChatBubbles = mutableListOf()
        listView = findViewById<View>(R.id.list_msg) as ListView
        btnSend = findViewById(R.id.btn_chat_send)
        editText = findViewById(R.id.msg_type) as EditText
        adapter = MessageAdapter(this, R.layout.left_chat_bubble, ChatBubbles)
        listView!!.setAdapter(adapter);


                btnSend!!.setOnClickListener {
                    if (editText!!.text.toString().trim { it <= ' ' } == "") {
                        Toast.makeText(this@ChatActivity, "Please input some text...", Toast.LENGTH_SHORT).show()
                    } else {
                        //add message to list
                        val ChatBubble = ChatBubble(editText!!.text.toString(), myMessage)
                        ChatBubbles!!.add(ChatBubble)
                        ChatBubbles
                        adapter!!.notifyDataSetChanged()
                        editText!!.setText("")
                        if (myMessage) {
                            myMessage = false
                        } else {
                            myMessage = true
                        }
                    }
                }
    }
}