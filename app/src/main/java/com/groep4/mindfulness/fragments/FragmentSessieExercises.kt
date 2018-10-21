package com.groep4.mindfulness.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groep4.mindfulness.R
import com.groep4.mindfulness.adapters.SessiePagerAdapter
import kotlinx.android.synthetic.main.activity_page.*
import kotlinx.android.synthetic.main.fragment_sessie.*
import kotlinx.android.synthetic.main.fragment_sessie.view.*

class FragmentSessieExercises : Fragment() {

    companion object {
        fun newInstance(): FragmentSessieExercises {
            return FragmentSessieExercises()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_sessie_exercises, container, false)
        return view
    }
}