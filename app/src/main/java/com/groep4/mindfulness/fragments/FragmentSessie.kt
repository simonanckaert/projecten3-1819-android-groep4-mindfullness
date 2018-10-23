package com.groep4.mindfulness.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groep4.mindfulness.R
import kotlinx.android.synthetic.main.activity_page.*
import kotlinx.android.synthetic.main.fragment_sessie.view.*


class FragmentSessie : Fragment() {

    companion object {
        fun newInstance(): FragmentSessie {
            return FragmentSessie()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_sessie, container, false)



        val bundle = this.arguments
        if (bundle != null) {
            val i = bundle.getString("key_sessie", "test")
            activity!!.tv_page.text = bundle!!.getString("key_sessie", "test")
        }

        view.tv_sessie_naam.text = bundle!!.getString("key_sessie", "test")

        return view
    }
}