package com.groep4.mindfulness.fragments

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groep4.mindfulness.R
import android.support.v7.app.AppCompatActivity
import com.groep4.mindfulness.interfaces.CallbackInterface
import kotlinx.android.synthetic.main.fragment_main.view.*


class FragmentMain : Fragment() {

    private var callback: CallbackInterface? = null

    companion object {
        fun newInstance(): FragmentMain {
            return FragmentMain()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = context as? CallbackInterface
        if (callback == null) {
            throw ClassCastException("$context must implement OnArticleSelectedListener")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_main, container, false)

        // Toolbar
        val toolbar = view.findViewById<View>(R.id.toolbar) as Toolbar
        (activity as AppCompatActivity).setSupportActionBar(toolbar)

        view.ll_sessies.setOnClickListener {
            view.ll_sessies.isEnabled = false
            callback!!.setFragment(FragmentSessieLijst(), true)
        }

        view.ll_reminder.setOnClickListener {
            view.ll_reminder.isEnabled = false
            callback!!.setFragment(FragmentReminder(), true)
        }

        view.ll_contact.setOnClickListener{
            view.ll_contact.isEnabled = false
            callback!!.setFragment(FragmentChat(), true)
        }

        view.ll_kalender.setOnClickListener{
            view.ll_kalender.isEnabled = false
            callback!!.setFragment(FragmentKalender(), true)
        }

        return view
    }

    override fun onResume()
    {
        super.onResume()
        view!!.ll_sessies.isEnabled = true
        view!!.ll_reminder.isEnabled = true
        view!!.ll_contact.isEnabled = true
        view!!.ll_kalender.isEnabled = true
    }
}