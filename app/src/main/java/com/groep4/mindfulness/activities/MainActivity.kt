package com.groep4.mindfulness.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.groep4.mindfulness.R
import com.groep4.mindfulness.model.Oefening
import com.groep4.mindfulness.model.Sessie
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.Logger.addLogAdapter


import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private val client = OkHttpClient()



    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Logger.addLogAdapter(AndroidLogAdapter())

        // Toolbar
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // Sessies
        val sessies: ArrayList<Sessie> = getSessies()

        // belangrijk key_page mee te geven om juiste fragment te kunnen laden vanuit eenzelfde activity!
        ll_sessies.setOnClickListener {
            val intent = Intent(this, ActivityPage::class.java)
            intent.putExtra("key_page", "sessies")
            intent.putExtra("sessielist", sessies)
            startActivity(intent)
        }

        ll_reminder.setOnClickListener {
            val intent = Intent(this, ActivityPage::class.java)
            intent.putExtra("key_page", "reminder")
            intent.putExtra("sessielist", sessies)
            startActivity(intent)
        }
    }

        ll_contact.setOnClickListener{
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("key_page", "contact")
            startActivity(intent)

    }}

    // Menu icons are inflated just as they were with actionbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Sessies ophalen
    fun getSessies(): ArrayList<Sessie> {
        val sessies: ArrayList<Sessie> = ArrayList()

        // HTTP Request sessies
        val request = Request.Builder()
                /*.header("Authorization", "token abcd")*/
                .url("http://141.134.155.219:3000/sessies")
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERROR", "HTTP request failed: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonarray = JSONArray(response.body()!!.string())
                for (i in 0 until jsonarray.length()) {
                    val jsonobject = jsonarray.getJSONObject(i)
                    val sessieId = jsonobject.getInt("sessieId")
                    val naam = jsonobject.getString("naam")
                    val beschrijving = jsonobject.getString("beschrijving")
                    val oefeningen = getOefeningen(sessieId)
                    val sessie: Sessie = Sessie(sessieId, naam, beschrijving, "Info", oefeningen, false)
                    sessies.add(sessie)
                }
            }
        })
        return sessies
    }

    // Oefeningen van sessie ophalen
    fun getOefeningen(sessieId: Int): ArrayList<Oefening>{
        val oefeningen: ArrayList<Oefening> = ArrayList()

        // HTTP Request oefeningen
        val request = Request.Builder()
                /*.header("Authorization", "token abcd")*/
                .url("http://141.134.155.219:3000/oefeningen/$sessieId")
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERROR", "HTTP request failed: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonarray = JSONArray(response.body()!!.string())
                for (i in 0 until jsonarray.length()) {
                    val jsonobject = jsonarray.getJSONObject(i)
                    val oefeningenId = jsonobject.getInt("oefeningId")
                    val naam = jsonobject.getString("naam")
                    val beschrijving = jsonobject.getString("beschrijving")
                    val sessieid = jsonobject.getInt("sessieId")

                    val oefening: Oefening = Oefening(oefeningenId, naam, beschrijving, sessieid)
                    oefeningen.add(oefening)
                }
            }
        })

        return oefeningen
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Toast.makeText(this,"Account uitgelogd", Toast.LENGTH_SHORT).show()
        FirebaseAuth.getInstance().signOut()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                Toast.makeText(this,"Account uitgelogd", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, ActivityLogin::class.java)
                this.startActivity(intent)
                finish()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

}
