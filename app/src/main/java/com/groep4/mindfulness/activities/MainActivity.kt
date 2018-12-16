package com.groep4.mindfulness.activities

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.groep4.mindfulness.R
import com.groep4.mindfulness.fragments.*
import com.groep4.mindfulness.interfaces.CallbackInterface
import com.groep4.mindfulness.model.Gebruiker
import com.groep4.mindfulness.model.Oefening
import com.groep4.mindfulness.model.Sessie
import com.groep4.mindfulness.utils.ExtendedDataHolder
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.wdullaer.materialdatetimepicker.date.MonthAdapter
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.net.URL
import java.time.LocalDateTime
import java.util.*


class MainActivity : AppCompatActivity(), CallbackInterface {

    private val BACK_STACK_ROOT_TAG = "root_fragment"
    private val client = OkHttpClient()
    lateinit var mAuth: FirebaseAuth

  //  private var isFragmentProfielLoaded = false
    var gebruiker : Gebruiker? = null
    var cal: Calendar? = null

    var gebruiker : Gebruiker = Gebruiker()

    var sessies: ArrayList<Sessie> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mAuth = FirebaseAuth.getInstance()
        Logger.addLogAdapter(AndroidLogAdapter())
        Log.d("tag", "TIJD TIJD TIJD TIJD  " +  Calendar.DAY_OF_MONTH)

        // Toolbar
        //val toolbar = findViewById<View>(R.id.toolbar) as Toolbar
        //setSupportActionBar(toolbar)

        // Set gebruiker
        this.gebruiker = getAangemeldeGebruiker()

        // Sessies
        sessies = getSessiesFromDB()
        val extras = ExtendedDataHolder.getInstance()
        extras.putExtra("sessielist", sessies)

        setFragment(FragmentMain(), true)

        // Set fragment on btnclick
        /*ll_sessies.setOnClickListener {
            ll_sessies.isEnabled = false
            setFragment(FragmentSessieLijst(), false)
        }

        ll_reminder.setOnClickListener {
            ll_reminder.isEnabled = false
            setFragment(FragmentReminder(), false)
        }

        ll_contact.setOnClickListener{
            ll_contact.isEnabled = false
            setFragment(FragmentChat(), false)
        }

        ll_kalender.setOnClickListener{
            val intent = Intent(this, ActivityKalender::class.java)
            intent.putExtra("key_page", "kalender")
            startActivity(intent)

        } */


        val cal = Calendar.getInstance()
        val day = cal.get(Calendar.DAY_OF_MONTH)
        tv_quote.text = randomQuote(day)
        Log.d("tag", "TIJD TIJD TIJD TIJD  " +  day)

            ll_kalender.isEnabled = false
            setFragment(FragmentKalender(), false)
        }

    }

    // Callback Function
    override fun setFragment(fragment: Fragment, addToBackstack: Boolean) {
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

    /*override fun onResume()
    {
        super.onResume()
        ll_sessies.isEnabled = true
        ll_reminder.isEnabled = true
        ll_contact.isEnabled = true
        ll_kalender.isEnabled = true
    }*/

    // Menu icons are inflated just as they were with actionbar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Sessies ophalen
    fun getSessiesFromDB(): ArrayList<Sessie> {
        val tempSessies: ArrayList<Sessie> = ArrayList()

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
                tempSessies.clear()
                val jsonarray = JSONArray(response.body()!!.string())
                for (i in 0 until jsonarray.length()) {
                    val jsonobject = jsonarray.getJSONObject(i)
                    val sessieId = jsonobject.getInt("sessieId")
                    val naam = jsonobject.getString("naam")
                    val beschrijving = jsonobject.getString("beschrijving")
                    val oefeningen = getOefeningen(sessieId)
                    //val oefeningen = ArrayList<Oefening>()
                    val sessieCode = jsonobject.getString("sessieCode")
                    val sessie: Sessie = Sessie(sessieId, naam, beschrijving, oefeningen, sessieCode)
                    tempSessies.add(sessie)
                }
            }
        })
        return tempSessies
    }

    fun getAangemeldeGebruiker() : Gebruiker{
        val gebruiker : Gebruiker = Gebruiker()
        val id = mAuth.currentUser!!.uid
        val string1 = ("http://141.134.155.219:3000/users/" + id)
        val string = "http://141.134.155.219:3000/users/yXQmL8IGSCbN15fzWw60t5udU2o2"

        // HTTP Request sessies
        val request = Request.Builder()
                /*.header("Authorization", "token abcd")*/
                .url(string1/*+ mAuth.currentUser!!.uid*/)
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("ERROR", "HTTP request failed: $e")
            }

            override fun onResponse(call: Call, response: Response) {
                //val jsonarray = JSONArray(response.body()!!.string())
                val jsonobject = JSONObject(response.body()!!.string())

                    gebruiker.uid = mAuth.currentUser!!.uid
                    gebruiker.regio = if (jsonobject.has("regio")) jsonobject.getString("regio") else ""
                    gebruiker.email = if (jsonobject.has("email")) jsonobject.getString("email") else ""
                    gebruiker.name = if (jsonobject.has("name")) jsonobject.getString("name") else ""
                    gebruiker.telnr = if (jsonobject.has("telnr")) jsonobject.getString("telnr") else ""
                    gebruiker.groepsnr = if (jsonobject.has("groepnr")) jsonobject.getInt("groepnr") else 0
                    gebruiker.sessieId = if (jsonobject.has("sessieid")) jsonobject.getInt("sessieid") else 1
            }
        })
        return gebruiker
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
                    val fileUrl = jsonobject.getString("fileName")
                    val fileMimeType = jsonobject.getString("fileMimetype")
                    val groepen = jsonobject.getString("groepen")

                    if(groepen.contains(gebruiker!!.groepsnr.toString())) {
                        val oefening: Oefening = Oefening(oefeningenId, naam, beschrijving, sessieid, fileUrl, fileMimeType, groepen)
                        oefeningen.add(oefening)
                    }
                }
            }
        })
        return oefeningen
    }

    override fun onBackPressed() {

        super.onBackPressed()
    }

    fun veranderGegevensGebruiker(gebruikersnaam : String, regio : String, telnr : String) {
        gebruiker!!.name = gebruikersnaam
        gebruiker!!.regio = regio
        gebruiker!!.telnr = telnr
    }

    fun gegevensGebruikerOpslaan(body : FormBody, url : String) : String {
        var response2 : String? = null
        val thread = Thread(Runnable {
            val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
            val client: OkHttpClient = OkHttpClient()
            //val body: RequestBody = RequestBody.create(mediaType, json)
            val request: Request = Request.Builder().url(url).put(body).build()
            val response = client.newCall(request).execute()
            response2 = response.body().toString()
        })
        thread.start()
        getAangemeldeGebruiker()
        //support FragmentManager.popBackStack()


        return response2.orEmpty()
    }

    /*fun gegevensGebruikerOpslaan(body : FormBody, url : String) : String {
        var response2 : String? = null
        val thread = Thread(Runnable {
            val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
            val client: OkHttpClient = OkHttpClient()
            //val body: RequestBody = RequestBody.create(mediaType, json)
            val request: Request = Request.Builder().url(url).put(body).build()
            val response = client.newCall(request).execute()
            response2 = response.body().toString()
        })
        thread.start()
        //supportFragmentManager.popBackStack()

        return response2.orEmpty()
    }*/

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.action_logout -> {

                val builder = AlertDialog.Builder(this@MainActivity)
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
                supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_holder_main, FragmentProfiel(), "pageContent")
                        .addToBackStack(BACK_STACK_ROOT_TAG)
                        .commit()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*fun postFeedback(url: String, json: String): String {
        val mediaType: MediaType? = MediaType.parse("application/json; charset=utf-8")
        val client: OkHttpClient = OkHttpClient()
        val body: RequestBody = RequestBody.create(mediaType, json)
        val request: Request = Request.Builder().url(url).post(body).build()
        val response: Response = client.newCall(request).execute()
        return response.body().toString()
    }*/

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


    fun randomQuote(i: Int): String{
        var quote = ""

        when (i){
            1 -> quote = "Elk moment is een plaats waar je nog nooit bent geweest."
            2 -> quote = "Met mindfulness leer je je grootste pestkoppen kennen."
            3 -> quote = "In het laten varen van onze perceptie ontstaat ruimte..."
            4 -> quote = "Laat achter wat je was, laat gaan wat moet en laat zijn wat is."
            5 -> quote = "Je hebt een afspraak met het leven, een afspraak met het hier en nu."
            6 -> quote = "Piekeren is net als schommelen, je bent wel bezig, maar je komt niet van je plaats."
            7 -> quote = "Laat gaan wat was, accepteer wat is, omarm wat komt."
            8 -> quote = "De kleine dingen, momenten ? Die zijn niet klein."
            9 -> quote = "Minder streng voor jezelf zijn, is een weg naar meer ontspanning en zelfcompassie."
            10 -> quote = "Omarm het onbekende."
            11 -> quote = "Hoe stiller je wordt, hoe meer je kan horen."
            12 -> quote = "Tijd nemen voor jezelf is een keuze."
            13 -> quote = "Lach, haal adem, neem de tijd."
            14 -> quote = "Het mooie van afstand nemen is dat het je dichterbij inzicht brengt."
            15 -> quote = "Je bent perfect, inclusief al je imperfecties!"
            16 -> quote = "Het leven is er om vandaag van te genieten."
            17 -> quote = "Verander je toekomst ingrijpend door jezelf te worden."
            18 -> quote = "Volg je hart, want dat klopt."
            19 -> quote = "Herinner je gisteren, droom van morgen, maar leef vandaag!"
            20 -> quote = "Waar je ook heen gaat, daar ben je."
            21 -> quote = "Ik wil niet weten wat ik later word, ik wil weten wat ik nu ben."
            22 -> quote = "Piekeren is de verkeerde kant op fantaseren."
            23 -> quote = "Niks moet & niks mag."
            24 -> quote = "Morgen is er weer een dag."
            25 -> quote = "Don't call it a dream, call it a plan."
            26 -> quote = "Zie niet wat je denkt te zien, maar zie wat er is."
            27 -> quote = "Hakuna matata"
            28 -> quote = "In de stilte van het denken, hoor je de antwoorden."
            29 -> quote = "Geluk kan je vermenigvuldigen door het te delen."
            30 -> quote = "Gun jezelf rust, uit rust komt de kracht."
            31 -> quote = "Oordeel niet, verbaas je slechts."
        }
        return quote
    }




    fun sessieUnlocked() {
        gebruiker!!.sessieId += 1
        val fromBodyBuilder = FormBody.Builder()
        fromBodyBuilder.add("name", gebruiker!!.name)
        fromBodyBuilder.add("regio", gebruiker!!.regio)
        fromBodyBuilder.add("telnr", gebruiker!!.telnr)
        fromBodyBuilder.add("uid", gebruiker!!.uid)
        fromBodyBuilder.add("email", gebruiker!!.email)
        fromBodyBuilder.add("groepnr", gebruiker!!.groepsnr.toString())
        fromBodyBuilder.add("sessieid", gebruiker!!.sessieId.toString())
        var url = "http://141.134.155.219:3000/users/" + gebruiker!!.uid
        gegevensGebruikerOpslaan(fromBodyBuilder.build(), url)
    }

}
