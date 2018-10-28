package com.groep4.mindfulness.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.groep4.mindfulness.fragments.FragmentSessie
import com.groep4.mindfulness.model.Sessie


internal class SessieListPagerAdapter(fm: FragmentManager, val sessies: ArrayList<Sessie>) : FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
        return sessies.size
    }

    override fun getItem(position: Int): Fragment {
        return FragmentSessie.newInstance(position + 1, position == count - 1, sessies[position])
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "Page $position"
    }
}