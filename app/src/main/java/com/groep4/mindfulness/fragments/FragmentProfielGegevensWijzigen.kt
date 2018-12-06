package com.groep4.mindfulness.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.groep4.mindfulness.R

class FragmentProfielGegevensWijzigen : Fragment() {

    private var btnAnnuleren : Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view:View=inflater!!.inflate(R.layout.fragment_profiel_gegevens_wijzigen,container,false)

        btnAnnuleren = view.findViewById(R.id.btnAnnuleren)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btnAnnuleren!!.setOnClickListener({
            activity!!.onBackPressed()
        })
    }
}