package com.groep4.mindfulness.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Toast
import com.groep4.mindfulness.R
import com.groep4.mindfulness.adapters.SessiePagerAdapter
import com.groep4.mindfulness.model.Sessie
import kotlinx.android.synthetic.main.activity_page.*
import kotlinx.android.synthetic.main.fragment_sessie.view.*

class FragmentSessie : Fragment() {

    lateinit var sessie: Sessie

    companion object {
        fun newInstance(): FragmentSessie {
            return FragmentSessie()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_sessie, container, false)

        val bundle = this.arguments

        if (bundle != null) {
            sessie = bundle.getParcelable("key_sessie")
            activity!!.tv_page.text = sessie.naam
        }

        val fragmentAdapter = SessiePagerAdapter(activity!!.supportFragmentManager, sessie)
        view.sessie_viewpager.adapter = fragmentAdapter
        view.sessie_tabs.setupWithViewPager(view.sessie_viewpager)


        return view
    }
}