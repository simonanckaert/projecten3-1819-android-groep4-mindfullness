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
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_chat.*
import java.text.DateFormat

/**
 * TODO: zet om naar fragments
 *
 * Class zorgt voor het chat scherm met de psycholoog
 * */
class ChatActivity : AppCompatActivity() {

/**
 * Variabelen voor layout en data
 * */
    private var listView: ListView? = null
    private var btnSend: View? = null
    private var editText: EditText? = null

    private var adapter: FirebaseListAdapter<Message>? = null

    private var dbInstance : FirebaseDatabase? = FirebaseDatabase.getInstance()
    private var currentUserId: FirebaseUser? = FirebaseAuth.getInstance().currentUser!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        contactUser.text = currentUserId!!.email

        btnSend = findViewById(R.id.btn_chat_send)
        /** Bericht sturen naar de database met als sleutel het userid van de huidig ingelogde gebruiker. */
        btnSend!!.setOnClickListener {
            editText = findViewById(R.id.msg_type)
            dbInstance!!.reference.child(currentUserId!!.uid).push().setValue(
                    Message(editText!!.text.toString(),
                            FirebaseAuth.getInstance().currentUser!!.email))
            editText!!.setText("")
        }
      displayChatMessages()
    }

    override fun onStart() {
        super.onStart()
        adapter!!.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter!!.stopListening()
    }


    /**
     * De listAdapter zorgt ervoor dat de List opgevuld raakt met chatberichten opgehaald uit de database met de huidige gebruiker's userid als argument.
     */
    private fun displayChatMessages() {

        listView = findViewById<View>(R.id.list_msg) as ListView

        val options: FirebaseListOptions<Message> = FirebaseListOptions.Builder<Message>()
                .setLayout(R.layout.message)
                .setQuery(dbInstance!!.reference.child(currentUserId!!.uid), Message::class.java)
                .setLifecycleOwner(this)
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