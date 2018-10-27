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
import com.groep4.mindfulness.activities.ActivityPage
import com.groep4.mindfulness.adapters.SessieListAdapter
import com.groep4.mindfulness.model.Oefening
import com.groep4.mindfulness.model.Sessie
import kotlinx.android.synthetic.main.activity_page.*
import android.R.attr.key



class FragmentSessieList : Fragment() {

    var sessies: ArrayList<Sessie> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_sessie_list, container, false)

        // Top bar info instellen
        activity!!.tr_page.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        //activity!!.iv_page.setImageResource(R.mipmap.sessies)
        activity!!.tv_page.setText(R.string.sessies)

        // (Statische) sessies toevoegen, in afwachting van DB
        addSessies()

        // Sessie adapter aanmaken en sessies linken

        val sessiesAdapter = SessieListAdapter(sessies) {
            val sessieFragment = FragmentSessie()
            val bundle = Bundle()
            bundle.putParcelable("key_sessie", it)

            sessieFragment.arguments = bundle
            (activity as ActivityPage).setFragment(sessieFragment, true)
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
        sessies.clear()

        for(i in 1..3) {
            val oef = Oefening("Oefening 0$i", "Beschrijving 0$i")
            oefeningen.add(oef)
        }

        for(i in 1..8) {
            val sessie: Sessie = if (i < 10)
                Sessie("Sessie 0$i", "Beschrijving Sessie 0$i", "Info", oefeningen, false)
            else
                Sessie("Sessie $i", "Beschrijving Sessie $i", "Info", oefeningen, false)
            sessies.add(sessie)
        }

        sessies.get(0).beschrijving = "Schets ACT - mindfulness - Ademfocus"
        sessies.get(0).info =
                "Mindfulness is de toestand die we willen bereiken, meditaties zijn de oefeningen die we doen om tot die toestand te komen (zoals een voetballer die moet trainen: lopen, krachttraining en behendigheidsoefeningen). Meditatie oefeningen versterken onze mentale kracht. We gebruiken onze zintuigen om onze aandacht te richten op om het eender wel object. Focussen op waar onze aandacht spontaan naartoe wordt getrokken (actiefilm, angst-woede…) vraagt geen mentale kracht. Wanneer we mentale kracht missen gaat onze aandacht automatisch naar die gedachten of zaken die ons het meest meeslepen of boeien, zelfs als we dat niet willen. Je betrapt jezelf erop dat je aan je lunch zit te denken terwijl je moet werken? Of dat je aan je werk denkt terwijl je aan het lunchen bent?"

    }
}