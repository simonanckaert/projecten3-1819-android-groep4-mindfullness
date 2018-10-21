package com.groep4.mindfulness.adapters

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.groep4.mindfulness.fragments.FragmentSessieExercises
import com.groep4.mindfulness.fragments.FragmentSessieInfo
import com.groep4.mindfulness.model.Sessie

class SessiePagerAdapter(fm: FragmentManager, sessie: Sessie) : FragmentStatePagerAdapter(fm) {

    var currentSessie: Sessie? = null
    val bundle = Bundle()

    init {
        currentSessie = sessie
        bundle.putParcelable("key_sessie", currentSessie)
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                val fragmentInfo = FragmentSessieInfo.newInstance()
                fragmentInfo.arguments = bundle
                fragmentInfo
            }
            1 -> {
                val fragmentExercises = FragmentSessieExercises.newInstance()
                fragmentExercises.arguments = bundle
                fragmentExercises
            }
            else -> {
                val fragmentInfo = FragmentSessieInfo.newInstance()
                fragmentInfo.arguments = bundle
                return fragmentInfo
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "Info"
            1 -> "Oefeningen"
            else -> {
                return "Info"
            }
        }
    }
}