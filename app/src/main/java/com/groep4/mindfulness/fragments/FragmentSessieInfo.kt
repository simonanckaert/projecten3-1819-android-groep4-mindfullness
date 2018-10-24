package com.groep4.mindfulness.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.groep4.mindfulness.R
import com.groep4.mindfulness.model.Sessie
import kotlinx.android.synthetic.main.fragment_sessie_info.*
import kotlinx.android.synthetic.main.fragment_sessie_info.view.*

class FragmentSessieInfo : Fragment() {

    lateinit var sessie: Sessie

    companion object {
        fun newInstance(): FragmentSessieInfo {
            return FragmentSessieInfo()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_sessie_info, container, false)

        val bundle = this.arguments

        if (bundle != null) {
            sessie = bundle.getParcelable("key_sessie")
            view.tv_sessie_beschrijving.text = sessie.beschrijving
            view.tv_sessie_info.text = sessie.info
            view.tv_sessie_info.movementMethod = ScrollingMovementMethod()
        }
        return view
    }
}