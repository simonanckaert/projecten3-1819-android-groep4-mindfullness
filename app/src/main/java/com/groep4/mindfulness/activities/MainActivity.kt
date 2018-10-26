package com.groep4.mindfulness.activities

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessagingService
import com.groep4.mindfulness.R
import com.groep4.mindfulness.services.DbService
import com.groep4.mindfulness.utils.RetrofitUtils
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import android.os.StrictMode
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.groep4.mindfulness.model.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {


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
            stuurBackend();

        }
    }

    /** Button Handler voor Contact Activity*/
    fun openContact(view: View) {
        val intent = Intent(this, ActivityContact::class.java)
        startActivity(intent)



    }

    fun stuurBackend(){

        val dbservice = RetrofitUtils.getDbService()

            val user = User("", "")
                user.email = "emailtest"
                user.token = "tokentest"
        /*
        dbservice.users.enqueue(object : Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>?, t: Throwable?) {
                Log.e("stuurBackend", "Sturen gefaald")

            }

            override fun onResponse(call: Call<List<User>>?, response: Response<List<User>>?) {
                Log.e("stuurBackend", "Sturen gelukt.")

            }
        })*/

        /*  dbservice.saveUser("emailtest","tokentest").enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Log.e("stuurBackend", "Sturen gefaald")

            }

            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                Log.e("stuurBackend", "Sturen gelukt.")

            }
        })*/

        /*
        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }
            val token = task.result!!.token


            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(ContentValues.TAG, msg)
            Toast.makeText(this@MainActivity, msg, Toast.LENGTH_SHORT).show()
        })*/

        dbservice.createUser(user).enqueue(object : Callback<User>{
            override fun onFailure(call: Call<User>?, t: Throwable?) {
                Log.e("stuurBackend", "Sturen gefaald")

            }

            override fun onResponse(call: Call<User>?, response: Response<User>?) {
                Log.e("stuurBackend", "Sturen gelukt.")

            }
        })
    }

}