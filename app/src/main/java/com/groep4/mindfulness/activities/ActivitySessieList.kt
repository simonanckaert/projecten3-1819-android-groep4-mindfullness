package com.groep4.mindfulness.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.groep4.mindfulness.R
import com.groep4.mindfulness.adapters.SessiesAdapter
import com.groep4.mindfulness.model.Oefening
import com.groep4.mindfulness.model.Sessie
import kotlinx.android.synthetic.main.activity_sessielist.*

class ActivitySessieList : AppCompatActivity() {

    val sessies: ArrayList<Sessie> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sessielist)

        // (Statische) sessies toevoegen
        addSessies()

        // Sessie adapter aanmaken en sessies linken
        val sessiesAdapter: SessiesAdapter = SessiesAdapter(sessies) {
            Toast.makeText(this, it.beschrijving, Toast.LENGTH_LONG).show()
        }

        // Adapter voor recyclerview (listview) instellen
        rv_sessies_list.layoutManager = LinearLayoutManager(this)
        rv_sessies_list.adapter = sessiesAdapter
    }

    // Sessies toevoegen (static placeholder)
    fun addSessies() {
        val oefeningen: ArrayList<Oefening> = ArrayList()

        for(i in 1..3) {
            val oef = Oefening("Oefening " + "0" + i, "Beschrijving " + "0" + i)
            oefeningen.add(oef)
        }

        for(i in 1..8) {
            val sessie = Sessie("Sessie " + "0" + i, "Beschrijving Sessie " + "0" + i, oefeningen, false)
            sessies.add(sessie)
        }
    }
}
