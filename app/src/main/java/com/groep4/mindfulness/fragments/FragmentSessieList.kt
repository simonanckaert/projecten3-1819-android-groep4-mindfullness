package com.groep4.mindfulness.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.groep4.mindfulness.R
import com.groep4.mindfulness.adapters.SessiesAdapter
import com.groep4.mindfulness.model.Oefening
import com.groep4.mindfulness.model.Sessie
import kotlinx.android.synthetic.main.activity_page.*

class FragmentSessieList : Fragment() {

    val sessies: ArrayList<Sessie> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_sessie_list, container, false)

        // Top bar info instellen
        activity!!.tr_page.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        activity!!.iv_page.setImageResource(R.mipmap.sessies)
        activity!!.tv_page.setText(R.string.sessies)

        // (Statische) sessies toevoegen, in afwachting van DB
        addSessies()

        // Sessie adapter aanmaken en sessies linken
        val sessiesAdapter = SessiesAdapter(sessies) {
            Toast.makeText(context, it.beschrijving, Toast.LENGTH_LONG).show()
        }

        // Adapter voor recyclerview (listview) instellen
        val listView = view.findViewById<RecyclerView>(R.id.rv_sessies_list)
        listView.layoutManager = LinearLayoutManager(context)
        listView.adapter = sessiesAdapter

        // Inflate
        return view
    }

    // Sessies toevoegen (static placeholders voor te testen)
    private fun addSessies() {
        val oefeningen: ArrayList<Oefening> = ArrayList()

        for(i in 1..3) {
            val oef = Oefening("Oefening 0$i", "Beschrijving 0$i")
            oefeningen.add(oef)
        }

        for(i in 1..12) {
            val sessie: Sessie = if (i < 10)
                Sessie("Sessie 0$i", "Beschrijving Sessie 0$i", oefeningen, false)
            else
                Sessie("Sessie $i", "Beschrijving Sessie $i", oefeningen, false)
            sessies.add(sessie)
        }
    }
}