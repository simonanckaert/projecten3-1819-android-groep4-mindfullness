package com.groep4.mindfulness.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.groep4.mindfulness.R
import com.groep4.mindfulness.activities.ActivityPage
import com.groep4.mindfulness.model.Sessie
import com.orhanobut.logger.Logger
import kotlinx.android.synthetic.main.fragment_sessie.*
import kotlinx.android.synthetic.main.fragment_sessie.view.*
import java.util.*
import kotlin.collections.ArrayList


class FragmentSessie : Fragment() {

    private var imgSessie: ImageView? = null
    private var txtSessieTitel: TextView? = null
    private var txtSessieBeschrijving: TextView? = null
    private var imgBusMonsters: ArrayList<ImageView>? = null

    private var imgViewsBuildings: ArrayList<ImageView>? = null
    private var imgViewsMisc: ArrayList<ImageView>? = null

    private var objectAnimator: ObjectAnimator? = null

    private var random: Random? = null

    private var screenWidth: Int? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        random = Random()
        val view = inflater.inflate(R.layout.fragment_sessie, container, false)
        imgSessie = view.findViewById(R.id.iv_sessie)
        txtSessieTitel = view.findViewById(R.id.tv_sessie)
        txtSessieBeschrijving = view.findViewById(R.id.tv_sessie_beschrijving)

        view.iv_bus.translationX = resources.displayMetrics.widthPixels.toFloat()
        view.iv_bus_gloss.translationX = resources.displayMetrics.widthPixels.toFloat()
        view.gl_bus_passengers.translationX = resources.displayMetrics.widthPixels.toFloat()

        // Bus-seats imageviews in array stoppen
        imgBusMonsters = ArrayList()
        imgBusMonsters!!.add(view.findViewById(R.id.iv_bus_seat1))
        imgBusMonsters!!.add(view.findViewById(R.id.iv_bus_seat2))
        imgBusMonsters!!.add(view.findViewById(R.id.iv_bus_seat3))
        imgBusMonsters!!.add(view.findViewById(R.id.iv_bus_seat4))
        imgBusMonsters!!.add(view.findViewById(R.id.iv_bus_seat5))
        imgBusMonsters!!.add(view.findViewById(R.id.iv_bus_seat6))
        imgBusMonsters!!.add(view.findViewById(R.id.iv_bus_seat7))
        imgBusMonsters!!.add(view.findViewById(R.id.iv_bus_seat8))

        // Background buildings & misc imageviews in array stoppen & random weergeven
        imgViewsBuildings = ArrayList()
        imgViewsBuildings!!.add(view.findViewById(R.id.iv_sessie_building00))
        imgViewsBuildings!!.add(view.findViewById(R.id.iv_sessie_building01))
        imgViewsBuildings!!.add(view.findViewById(R.id.iv_sessie_building02))

        imgViewsMisc = ArrayList()
        imgViewsMisc!!.add(view.findViewById(R.id.iv_sessie_miscbg00))
        imgViewsMisc!!.add(view.findViewById(R.id.iv_sessie_miscbg01))
        imgViewsMisc!!.add(view.findViewById(R.id.iv_sessie_miscbg02))
        imgViewsMisc!!.add(view.findViewById(R.id.iv_sessie_miscbg03))


        // Dynamisch background images verdelen per sessie
        for (v in imgViewsBuildings!!) {
            v.setImageResource((parentFragment as FragmentSessieList).imgBuildings!![random!!.nextInt((parentFragment as FragmentSessieList).imgBuildings!!.size)])
            when (random!!.nextBoolean()) {
                true -> v.visibility = VISIBLE
                false -> v.visibility = INVISIBLE
            }
        }

        for (v in imgViewsMisc!!) {
            v.setImageResource((parentFragment as FragmentSessieList).imgMisc!![random!!.nextInt((parentFragment as FragmentSessieList).imgMisc!!.size)])
        }

        screenWidth = resources.displayMetrics.widthPixels

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val page = arguments!!.getInt("page", 0)
        val sessie = arguments!!.getParcelable<Sessie>("sessie")

        val imgRes = context!!.resources.getIdentifier("mnstr$page","mipmap", context!!.packageName)
        imgSessie!!.setImageResource(imgRes)

        // Naargelang hoe ver je zit in de sessies de bus opvullen met monsters.
        for (i in 0 until page){
            val imgResBus = context!!.resources.getIdentifier("mnstr" + (i+1),"mipmap", context!!.packageName)
            imgBusMonsters!![i].setImageResource(imgResBus)
            imgBusMonsters!![i].visibility = VISIBLE
        }

        txtSessieTitel!!.text = sessie.naam
        Logger.e(sessie.naam)

        cv_sessie.setOnClickListener{
            val sessiePageFragment = FragmentSessiePage()
            val bundle = Bundle()
            bundle.putParcelable("key_sessie", sessie)
            bundle.putInt("key_page", page)
            sessiePageFragment.arguments = bundle
            (activity as ActivityPage).setFragment(sessiePageFragment, true)
        }
    }

    fun setBusVisible(isVisible: Boolean){
        val root = view!!.findViewById(R.id.rl_sessie) as RelativeLayout
        if(isVisible){
            root.iv_bus.visibility = VISIBLE
            root.iv_bus_gloss.visibility = VISIBLE
            root.gl_bus_passengers.visibility = VISIBLE
        }else{
            root.iv_bus.visibility = INVISIBLE
            root.iv_bus_gloss.visibility = INVISIBLE
            root.gl_bus_passengers.visibility = INVISIBLE
        }
    }

    fun drive(forward: Boolean){
        val root = view!!.findViewById(R.id.rl_sessie) as RelativeLayout

        if(forward){
            handleDriveForward(root.iv_bus)
            handleDriveForward(root.iv_bus_gloss)
            handleDriveForward(root.gl_bus_passengers)
        }else{
            handleDriveBackward(root.iv_bus)
            handleDriveBackward(root.iv_bus_gloss)
            handleDriveBackward(root.gl_bus_passengers)
        }
        setBusVisible(true)
    }

    private fun handleDriveForward(v: View){
        objectAnimator = ObjectAnimator.ofFloat(v, "translationX", -screenWidth!!.toFloat(), 0f)
        objectAnimator!!.duration = 1100
        objectAnimator!!.start()
    }

    private fun handleDriveBackward(v: View){
        objectAnimator = ObjectAnimator.ofFloat(v, "translationX", screenWidth!!.toFloat(), 0f)
        objectAnimator!!.duration = 1100
        objectAnimator!!.start()
    }

    companion object {
        fun newInstance(page: Int, isLast: Boolean, sessie: Sessie): FragmentSessie {
            val args = Bundle()
            args.putInt("page", page)
            if (isLast)
                args.putBoolean("isLast", true)
            args.putParcelable("sessie", sessie)
            val fragment = FragmentSessie()
            fragment.arguments = args
            return fragment
        }
    }
}