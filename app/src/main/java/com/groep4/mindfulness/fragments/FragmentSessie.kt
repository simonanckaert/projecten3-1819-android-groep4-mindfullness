package com.groep4.mindfulness.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.groep4.mindfulness.R
import com.groep4.mindfulness.activities.ActivityPage
import com.groep4.mindfulness.model.Sessie
import kotlinx.android.synthetic.main.fragment_sessie.*
import java.util.*


class FragmentSessie : Fragment() {

    private var imgSessie: ImageView? = null
    private var txtSessieTitel: TextView? = null
    private var txtSessieBeschrijving: TextView? = null
    private var imgSessieRoad: ImageView? = null
    private var imgBusMonsters: ArrayList<ImageView>? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.fragment_sessie, container, false)
        imgSessie = view.findViewById(R.id.iv_sessie)
        txtSessieTitel = view.findViewById(R.id.tv_sessie)
        txtSessieBeschrijving = view.findViewById(R.id.tv_sessie_beschrijving)
        imgSessieRoad = view.findViewById(R.id.iv_sessie_road)

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

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val page = arguments!!.getInt("page", 0)
        val sessie = arguments!!.getParcelable<Sessie>("sessie")

        var imgRes = context!!.resources.getIdentifier("mnstr$page","mipmap", context!!.packageName)
        imgSessie!!.setImageResource(imgRes)

        var imgRoadRes = context!!.resources.getIdentifier("road0$page","mipmap", context!!.packageName)
        imgSessieRoad!!.setImageResource(imgRoadRes)

        // Naargelang hoe ver je zit in de sessies de bus opvullen met monsters.
        for (i in 0 until page){
            var imgResBus = context!!.resources.getIdentifier("mnstr" + (i+1),"mipmap", context!!.packageName)
            imgBusMonsters!![i].setImageResource(imgResBus)
            imgBusMonsters!![i].visibility = VISIBLE
        }

        txtSessieTitel!!.text = sessie.naam
        txtSessieBeschrijving!!.text = sessie.beschrijving

        cv_sessie.setOnClickListener{
            val sessiePageFragment = FragmentSessiePage()
            val bundle = Bundle()
            bundle.putParcelable("key_sessie", sessie)
            bundle.putInt("key_page", page)
            sessiePageFragment.arguments = bundle
            (activity as ActivityPage).setFragment(sessiePageFragment, true)
        }

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