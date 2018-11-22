package com.groep4.mindfulness.fragments

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.groep4.mindfulness.R
import com.groep4.mindfulness.activities.ActivityPage
import com.groep4.mindfulness.activities.MainActivity
import com.groep4.mindfulness.model.Oefening
import com.groep4.mindfulness.model.Sessie

class FragmentOefeningFeedback : Fragment() {
    private var txtOefeningNaam : TextView? = null
    private var sliderFeedback : SeekBar? = null
    private var buttonOpslaan : Button? = null
    private var sessie : Sessie? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {

        val view = inflater.inflate(R.layout.fragment_oefening_feedback, container, false)

        txtOefeningNaam = view.findViewById(R.id.textViewNaam) as TextView
        sliderFeedback = view.findViewById(R.id.sliderFeedback)
        buttonOpslaan = view.findViewById(R.id.buttonBevestigen)



        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var oefening = arguments!!.getParcelable<Oefening>("oefening")
        val pageActivity = activity as ActivityPage

        buttonOpslaan!!.setOnClickListener {
            /*val sessiePageFragment = FragmentSessiePage()
            val page = arguments!!.getInt("page", 0)
            val sessie = FragmentSessieLijst().sessies.find { sessie1 : Sessie -> sessie1.sessieId.equals(oefening.sessieId) }

            Log.d(tag, sessie.toString())
            val bundle = Bundle()
            bundle.putParcelable("key_sessie", sessie)
            bundle.putInt("key_page", page)
            sessiePageFragment.arguments = bundle
            (activity as ActivityPage).setFragment(sessiePageFragment, false)*/
            (activity as ActivityPage).onBackPressed()
        }

        txtOefeningNaam!!.text = oefening.naam
    }
}