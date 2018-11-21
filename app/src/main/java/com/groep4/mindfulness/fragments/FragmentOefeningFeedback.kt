package com.groep4.mindfulness.fragments

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.groep4.mindfulness.R
import com.groep4.mindfulness.activities.ActivityPage
import com.groep4.mindfulness.model.Oefening

class FragmentOefeningFeedback : Fragment() {
    private var txtOefeningNaam : TextView? = null
    private var sliderFeedback : SeekBar? = null
    private var buttonOpslaan : Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {

        val view = inflater.inflate(R.layout.fragment_oefening_feedback, container, false)

        txtOefeningNaam = view.findViewById(R.id.textViewNaam) as TextView
        sliderFeedback = view.findViewById(R.id.sliderFeedback)
        buttonOpslaan = view.findViewById(R.id.buttonBevestigen)

        buttonOpslaan!!.setOnClickListener {
            val fragment = FragmentSessiePageOefeningen()
            (activity as ActivityPage).setFragment(fragment,false)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var oefening = arguments!!.getParcelable<Oefening>("oefening")

        txtOefeningNaam!!.text = oefening.naam
    }
}