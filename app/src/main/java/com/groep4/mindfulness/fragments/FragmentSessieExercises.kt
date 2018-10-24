package com.groep4.mindfulness.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groep4.mindfulness.R
import com.groep4.mindfulness.activities.ActivityPage
import com.groep4.mindfulness.adapters.ExercisesAdapter
import com.groep4.mindfulness.model.Oefening


class FragmentSessieExercises : Fragment() {

    var oefeningen: ArrayList<Oefening> = ArrayList()

    companion object {
        fun newInstance(): FragmentSessieExercises {
            return FragmentSessieExercises()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view: View = inflater.inflate(R.layout.fragment_sessie_exercises, container, false)

        // (Statische) oefeningen toevoegen, in afwachting van DB
        addOefeningen()

        // Oefening adapter aanmaken en oefeningen linken
        val oefeningenAdapter = ExercisesAdapter(oefeningen) {
            val exercisesFragment = FragmentSessieExercises()
            val bundle = Bundle()
            bundle.putParcelable("key_oefening", it)
            exercisesFragment.arguments = bundle
            (activity as ActivityPage).setFragment(exercisesFragment, true)
        }

        // Adapter voor recyclerview (listview) instellen
        val listView = view.findViewById<RecyclerView>(R.id.rv_exercises_list)
        listView.layoutManager = LinearLayoutManager(context)
        listView.adapter = oefeningenAdapter

        // Inflate
        return view
    }

    //
    private fun addOefeningen() {
        oefeningen.clear()

        for(i in 1..3) {
            val oef = Oefening("Oefening 0$i", "Beschrijving 0$i")
            oefeningen.add(oef)
        }
     }
}