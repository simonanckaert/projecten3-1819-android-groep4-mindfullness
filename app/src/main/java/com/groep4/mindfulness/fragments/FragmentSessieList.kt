package com.groep4.mindfulness.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.badoualy.stepperindicator.StepperIndicator
import com.groep4.mindfulness.R
import com.groep4.mindfulness.adapters.SessieListPagerAdapter
import com.groep4.mindfulness.model.Oefening
import com.groep4.mindfulness.model.Sessie
import kotlinx.android.synthetic.main.activity_page.*


class FragmentSessieList : Fragment() {

    var sessies: ArrayList<Sessie> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_sessie_list, container, false)

        // Top bar info instellen
        activity!!.tr_page.setBackgroundColor(ContextCompat.getColor(context!!, R.color.colorPrimary))
        activity!!.tv_page.setText(R.string.sessies)

        // (Statische) sessies toevoegen, in afwachting van DB
        addSessies()

        var pager = view.findViewById<ViewPager>(R.id.pager)!!
        pager.adapter = SessieListPagerAdapter(childFragmentManager, sessies)

        val indicator = view.findViewById(R.id.stepper_indicator) as StepperIndicator
        // We keep last page for a "finishing" page
        indicator.setViewPager(pager, false)
        indicator.addOnStepClickListener {
            step -> pager.setCurrentItem(step, true)
        }

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

        sessies[0].beschrijving = "Schets ACT - mindfulness - Ademfocus"
        sessies[0].info =
                "Mindfulness is de toestand die we willen bereiken, meditaties zijn de oefeningen die we doen om tot die toestand te komen (zoals een voetballer die moet trainen: lopen, krachttraining en behendigheidsoefeningen). Meditatie oefeningen versterken onze mentale kracht. We gebruiken onze zintuigen om onze aandacht te richten op om het eender wel object. Focussen op waar onze aandacht spontaan naartoe wordt getrokken (actiefilm, angst-woede…) vraagt geen mentale kracht. Wanneer we mentale kracht missen gaat onze aandacht automatisch naar die gedachten of zaken die ons het meest meeslepen of boeien, zelfs als we dat niet willen. Je betrapt jezelf erop dat je aan je lunch zit te denken terwijl je moet werken? Of dat je aan je werk denkt terwijl je aan het lunchen bent?"

    }
}