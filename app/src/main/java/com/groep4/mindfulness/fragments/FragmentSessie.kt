package com.groep4.mindfulness.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import com.groep4.mindfulness.R
import com.groep4.mindfulness.activities.ActivityPage
import com.groep4.mindfulness.model.Sessie
import es.dmoral.toasty.Toasty
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
    private var imgViewsMiscfg: ArrayList<ImageView>? = null
    private var imgFinish: ImageView? = null

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

        imgViewsMiscfg = ArrayList()
        imgViewsMiscfg!!.add(view.findViewById(R.id.iv_sessie_miscfg00))
        imgViewsMiscfg!!.add(view.findViewById(R.id.iv_sessie_miscfg01))
        imgViewsMiscfg!!.add(view.findViewById(R.id.iv_sessie_miscfg02))
        imgViewsMiscfg!!.add(view.findViewById(R.id.iv_sessie_miscfg03))
        imgViewsMiscfg!!.add(view.findViewById(R.id.iv_sessie_miscfg04))

        imgFinish = view.findViewById(R.id.iv_sessie_finish)

        // Dynamisch background images verdelen per sessie
        if (arguments!!.containsKey("isLast")){
            achtergrondFinish()
        } else {
            achtergrondRandomizen()
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
            if (i < 8){
                val imgResBus = context!!.resources.getIdentifier("mnstr" + (i+1),"mipmap", context!!.packageName)
                imgBusMonsters!![i].setImageResource(imgResBus)
                imgBusMonsters!![i].visibility = VISIBLE}
            }

        txtSessieTitel!!.text = sessie.naam

        cv_sessie.setOnClickListener{
            if (sessie!!.naam != "Geen sessie gevonden."){
                if (sessie!!.sessieId == 1){
                    val sessiePageFragment = FragmentSessiePage()
                    val bundle = Bundle()
                    bundle.putParcelable("key_sessie", sessie)
                    bundle.putInt("key_page", page)
                    sessiePageFragment.arguments = bundle
                    (activity as ActivityPage).setFragment(sessiePageFragment, true)
                } else {

                    val builder = AlertDialog.Builder(context!!)
                    var editTextCode: EditText

                    builder.setTitle("Sessie gelocked!")
                    builder.setMessage("Gelieve de sessiecode in te geven")
                    builder.setCancelable(true)

                    editTextCode = EditText(context)
                    editTextCode.hint = "sessiecode"
                    editTextCode.inputType = InputType.TYPE_CLASS_TEXT

                    builder.setPositiveButton("Stuur") { dialog, which ->
                        var code = editTextCode.text.toString()
                        if ("goed" == code) {
                            val sessiePageFragment = FragmentSessiePage()
                            val bundle = Bundle()
                            bundle.putParcelable("key_sessie", sessie)
                            bundle.putInt("key_page", page)
                            sessiePageFragment.arguments = bundle
                            (activity as ActivityPage).setFragment(sessiePageFragment, true)
                        } else {
                            Toast.makeText(context, "Helaas, het antwoord is fout", Toast.LENGTH_SHORT).show()

                        }
                    }

                    builder.setNeutralButton("Annuleer") { dialog, which ->
                        dialog.cancel()
                    }

                    val dialog: AlertDialog = builder.create()

                    dialog.setView(editTextCode)
                    dialog.show()
                }
            }else{
                Toasty.info(view!!.context, "Geen sessie gevonden, controleer uw internetverbinding.").show()
            }
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

    private fun achtergrondRandomizen(){
        for (v in imgViewsBuildings!!) {
            v.setImageResource((parentFragment as FragmentSessieLijst).imgBuildings!![random!!.nextInt((parentFragment as FragmentSessieLijst).imgBuildings!!.size)])
            when (random!!.nextBoolean()) {
                true -> v.visibility = VISIBLE
                false -> v.visibility = INVISIBLE
            }
        }

        for (v in imgViewsMisc!!) {
            v.setImageResource((parentFragment as FragmentSessieLijst).imgMisc!![random!!.nextInt((parentFragment as FragmentSessieLijst).imgMisc!!.size)])
        }

        imgFinish!!.visibility = INVISIBLE
    }

    private fun achtergrondFinish(){
        imgFinish!!.visibility = VISIBLE


        imgViewsBuildings!![0].visibility = INVISIBLE
        imgViewsBuildings!![1].visibility = INVISIBLE
        imgViewsBuildings!![2].visibility = VISIBLE

        imgViewsMisc!![0].visibility = INVISIBLE
        imgViewsMisc!![1].visibility = INVISIBLE
        imgViewsMisc!![2].visibility = INVISIBLE
        imgViewsMisc!![3].visibility = INVISIBLE

        imgViewsMiscfg!![0].visibility = INVISIBLE
        imgViewsMiscfg!![1].visibility = INVISIBLE
        imgViewsMiscfg!![2].visibility = INVISIBLE
        imgViewsMiscfg!![3].visibility = INVISIBLE
        imgViewsMiscfg!![4].visibility = INVISIBLE
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