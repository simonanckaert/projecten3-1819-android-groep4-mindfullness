package com.groep4.mindfulness.fragments

import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.badoualy.stepperindicator.StepperIndicator
import com.diegodobelo.expandingview.ExpandingItem
import com.diegodobelo.expandingview.ExpandingList
import com.groep4.mindfulness.R
import com.groep4.mindfulness.adapters.OefeningListPagerAdapter
import com.groep4.mindfulness.adapters.SessieListPagerAdapter
import com.groep4.mindfulness.model.Oefening
import com.groep4.mindfulness.model.Sessie
import es.dmoral.toasty.Toasty
import kotlinx.android.synthetic.main.fragment_sessie_page_info.view.*
import java.util.*


class FragmentSessiePageExercises : Fragment() {

    lateinit var sessie: Sessie
    private lateinit var oefeningen: ArrayList<Oefening>
    lateinit var mp: MediaPlayer

    companion object {
        fun newInstance(): FragmentSessiePageExercises {
            return FragmentSessiePageExercises()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_sessie_page_exercises, container, false)
        //expandingList = view.findViewById(R.id.expanding_list_main)

        oefeningen = arrayListOf()

        val bundle = this.arguments
        if (bundle != null) {
            sessie = bundle.getParcelable("key_sessie")
        }

        // Oefening toevoegen
        addOefeningen()



        val pager = view.findViewById<ViewPager>(R.id.pager_oefeningen)!!
        // offscreenpagelimit nodig zodat de pages niet telkens herladen worden bij het scrollen
        pager.offscreenPageLimit = oefeningen.size
        val pagerAdapter = OefeningListPagerAdapter(childFragmentManager, oefeningen)
        pager.adapter = pagerAdapter

        val stepper = view.findViewById<StepperIndicator>(R.id.si_oefeningen)
        stepper.setViewPager(pager, (pager.adapter as OefeningListPagerAdapter).count)

        // Audioplayer
        mp = MediaPlayer.create(context, R.raw.ademmeditatie)
        mp.isLooping = false


        // Inflate
        return view
    }

    private fun addOefeningen() {
        oefeningen.clear()

        // icons toevoegen
        val icons: ArrayList<Int> = ArrayList()
        icons.add(R.mipmap.lotusposition1)
        icons.add(R.mipmap.lotusposition2)
        icons.add(R.mipmap.lotusposition3)
        icons.add(R.mipmap.lotusposition4)
        icons.add(R.mipmap.lotusposition5)
        icons.add(R.mipmap.lotusposition6)
        icons.add(R.mipmap.lotusposition7)
        icons.add(R.mipmap.lotusposition8)

        if (sessie.oefeningen != null){
            oefeningen.addAll(sessie.oefeningen!!)
        }


    }

    /*private fun setCloseOtherExercises(item: ExpandingItem){
        item.setStateChangedListener{
            if (item.isExpanded) {
                for (i in 0 until expandingList.itemsCount){
                    if (item != expandingList.getItemByIndex(i) && expandingList.getItemByIndex(i).isExpanded)
                        expandingList.getItemByIndex(i).toggleExpanded()
                }
            } else {
                if (mp.isPlaying){
                    mp.stop()
                    (item.findViewById<View>(R.id.ib_playAudio) as ImageButton).setImageResource(R.drawable.ic_play_arrow_white_24dp)
                    Toasty.info(view!!.context, "Audio Stopped").show()
                }
            }
        }
    }*/
}