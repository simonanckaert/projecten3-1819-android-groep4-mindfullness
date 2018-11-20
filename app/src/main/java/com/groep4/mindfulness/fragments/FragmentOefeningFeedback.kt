package com.groep4.mindfulness.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.TextView
import com.groep4.mindfulness.R

class FragmentOefeningFeedback : Fragment() {
    private var txtOefeningNaam : TextView? = null
    private var txtOefeningBeschrijving : TextView? = null
    private var sliderFeedback : SeekBar? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {

        val view = inflater.inflate(R.layout.fragment_oefening_feedback, container, false)

        txtOefeningNaam = view.findViewById(R.id.textViewNaam)
        txtOefeningBeschrijving = view.findViewById(R.id.textViewBeschrijving)
        sliderFeedback = view.findViewById(R.id.sliderFeedback)

        return view
    }
}