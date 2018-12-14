package com.groep4.mindfulness.fragments

import android.os.Bundle
import android.os.DropBoxManager
import android.renderscript.Sampler
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.groep4.mindfulness.R
import com.groep4.mindfulness.activities.ActivityPage
import com.groep4.mindfulness.activities.MainActivity
import com.groep4.mindfulness.model.Gebruiker
import java.security.KeyStore

class FragmentProfielInfo: Fragment() {

    /*lateinit var mAuth: FirebaseAuth
    var ref: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")*/
    private var txtEmail: TextView? = null
    private var txtNaam: TextView? = null
    private var gebruiker : Gebruiker? = null
    private var txtRegio: TextView? = null
    private var txtTelnr : TextView? = null

    private var btnGegevensWijzigen : Button? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profiel_info, container, false)

        gebruiker = (activity as MainActivity).gebruiker

        txtEmail = view.findViewById(R.id.txtEmail)
        txtEmail!!.text = gebruiker!!.email

        txtNaam = view.findViewById(R.id.txtNaam)
        txtNaam!!.text = gebruiker!!.name

        txtTelnr = view.findViewById(R.id.txtTelnr)
        txtTelnr!!.text = gebruiker!!.telnr

        txtRegio = view.findViewById(R.id.txtRegio)
        txtRegio!!.text = gebruiker!!.regio

        //Toon de gegevenswijzigenfragment indien op gegevenswijzigen geklikt is
        btnGegevensWijzigen = view.findViewById(R.id.btnGegevensWijzigen)
        btnGegevensWijzigen!!.setOnClickListener {
            activity?.supportFragmentManager!!
                    .beginTransaction()
                    .replace(R.id.fragment_holder_main, FragmentProfielGegevensWijzigen(), "pageContent")
                    .addToBackStack("root_fragment")
                    .commit()
        }

        return view
    }

    /*
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mAuth = FirebaseAuth.getInstance()
        val user = FirebaseAuth.getInstance().currentUser

        val name = user!!.displayName
        txtEmail!!.text = user!!.email
        txtNaam!!.text = user!!.displayName

    }

    fun getUserData() {

        val userListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val children = dataSnapshot.children
                Log.i("firebase", children.count().toString())

                children.forEach {

                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("firebase", error!!.message)
            }
        }
    }
*/

}