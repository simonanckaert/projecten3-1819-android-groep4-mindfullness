package com.groep4.mindfulness.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.groep4.mindfulness.R
import com.groep4.mindfulness.model.Oefening


class FragmentOefening : Fragment() {

    private var txtOefeningNaam: TextView? = null
    private var txtOefeningBeschrijving: TextView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_oefening, container, false)

        txtOefeningNaam = view.findViewById(R.id.tv_oefening_naam)
        txtOefeningBeschrijving = view.findViewById(R.id.tv_oefening_beschrijving)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val page = arguments!!.getInt("page", 0)
        val oefening = arguments!!.getParcelable<Oefening>("oefening")

        txtOefeningNaam!!.text = oefening.naam
        txtOefeningBeschrijving!!.text = oefening.beschrijving
        txtOefeningBeschrijving!!.movementMethod = ScrollingMovementMethod()
    }

    companion object {
        fun newInstance(page: Int, isLast: Boolean, oefening: Oefening): FragmentOefening {
            val args = Bundle()
            args.putInt("page", page)
            if (isLast)
                args.putBoolean("isLast", true)
            args.putParcelable("oefening", oefening)
            val fragment = FragmentOefening()
            fragment.arguments = args
            return fragment
        }
    }
}