package com.groep4.mindfulness.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.groep4.mindfulness.R
import com.groep4.mindfulness.fragments.FragmentProfiel
import com.groep4.mindfulness.fragments.FragmentReminder
import com.groep4.mindfulness.fragments.FragmentSessieLijst
import com.groep4.mindfulness.model.Sessie
import com.groep4.mindfulness.utils.ExtendedDataHolder
import okhttp3.*


class ActivityPage : AppCompatActivity() {

    private val BACK_STACK_ROOT_TAG = "root_fragment"
    var sessies: ArrayList<Sessie> = ArrayList()
    private lateinit var fragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_page)

        val myIntent = intent // parent intent ophalen
        val keyPage = myIntent.getStringExtra("key_page") // key_page value ophalen
        sessies = ExtendedDataHolder.getInstance().getExtra("sessielist") as ArrayList<Sessie>

        // naargelang 'key_page value' (meegegeven via de MainActivity) kiezen welke Fragment er geladen moet worden
        if (savedInstanceState == null) {
            val fragment: Fragment = when(keyPage) {
                "sessie" -> FragmentSessieLijst()
                "reminder" -> FragmentReminder()
                else -> FragmentSessieLijst()
            }
            setFragment(fragment, false)
        }

        // Find the toolbar view inside the activity layout
        val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        // Sets the Toolbar to act as the ActionBar for this Activity window.
        // Make sure the toolbar exists in the activity and is not null
        setSupportActionBar(toolbar)
    }

    // Menu icons are inflated just as they were with actionbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_logout -> {
                val builder = AlertDialog.Builder(this@ActivityPage)
                builder.setMessage("Wil je uitloggen ?")

                builder.setPositiveButton("Ja"){dialog, which ->
                    FirebaseAuth.getInstance().signOut()
                    Toast.makeText(this,"Account uitgelogd", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, ActivityLogin::class.java)
                    this.startActivity(intent)
                    finish()
                }

                builder.setNegativeButton("Nee"){dialog,which ->
                }

                val dialog: AlertDialog = builder.create()
                dialog.show()
                return true
            }

            R.id.action_profiel -> {
                setFragment(FragmentProfiel(), true)
            }

        }
        return super.onOptionsItemSelected(item)
    }


    fun setFragment(fragment: Fragment, addToBackstack: Boolean) {
        if (addToBackstack)
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frag_content, fragment, "pageContent")
                    .addToBackStack(BACK_STACK_ROOT_TAG)
                    .commit()
        else
            supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.frag_content, fragment, "pageContent")
                    .commit()
    }

    fun postFeedback(url: String, body:FormBody): String {
        var response2 : String? = null
        val thread = Thread(Runnable {
            val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
            val client: OkHttpClient = OkHttpClient()
            //val body: RequestBody = RequestBody.create(mediaType, json)
            val request: Request = Request.Builder().url(url).post(body).build()
            val response = client.newCall(request).execute()
            response2 = response.body().toString()
        })
        thread.start()
        return response2.orEmpty()

    }
}
