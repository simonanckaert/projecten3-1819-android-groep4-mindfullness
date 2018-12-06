package com.groep4.mindfulness.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.FragmentStatePagerAdapter
import android.util.SparseArray
import com.groep4.mindfulness.fragments.FragmentProfielInfo
import com.groep4.mindfulness.fragments.FragmentProfielOverzicht

class ViewPagerAdapter(fm: FragmentManager): FragmentStatePagerAdapter(fm) {

    private val fragmentList: MutableList<Fragment> = ArrayList()
    private val fragmentListTitles: MutableList<String> = ArrayList()


    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentListTitles.count()
    }

    override fun getPageTitle(position: Int): CharSequence {
        return fragmentListTitles[position]
    }

    fun addFragment(fragment: Fragment, title: String){
        fragmentList.add(fragment)
        fragmentListTitles.add(title)
    }

}