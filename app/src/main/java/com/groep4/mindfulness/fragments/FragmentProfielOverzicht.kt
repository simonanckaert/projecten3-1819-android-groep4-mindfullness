package com.groep4.mindfulness.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groep4.mindfulness.R

class FragmentProfielOverzicht: Fragment() {

    companion object {
        fun newInstance(): FragmentProfielOverzicht {
            return FragmentProfielOverzicht()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profiel_overzicht, container, false)
        return view
    }
}