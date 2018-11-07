package com.groep4.mindfulness.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.TargetApi
import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.groep4.mindfulness.R
import com.groep4.mindfulness.utils.LoginValidation
import kotlinx.android.synthetic.main.activity_register.*
import com.google.android.gms.tasks.Task
import com.groep4.mindfulness.activities.MainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_login.view.*
import kotlinx.android.synthetic.main.fragment_register.view.*


class FragmentRegister : Fragment() {

    lateinit var mAuth: FirebaseAuth


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view:View=inflater!!.inflate(R.layout.fragment_register,container,false)

        view.email_register_in_button.setOnClickListener { attemptRegister() }
        activity!!.tv_register.text = resources.getString(R.string.wachtwoord_beleid)
        activity!!.tv_register.isClickable.not()
        return view

    }

    fun attemptRegister() {

        // Reset errors.

        register_email.error = null
        register_password.error = null

        // values
        val emailStr = register_email.text.toString()
        val passwordStr = register_password.text.toString()

        var cancel = false
        var focusView: View? = null


        // Controleer op een geldig email adres.
        if (!LoginValidation.isValidEmail(register_email)) {

            focusView = view!!.register_email
            cancel = true
        }

        if (!LoginValidation.isValidPassword(register_password)) {
            focusView = view!!.register_password
            cancel = true
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {
            showProgress(true)
            mAuth = FirebaseAuth.getInstance()
            mAuth.createUserWithEmailAndPassword(emailStr,passwordStr)
                    .addOnCompleteListener(activity!!){
                        task ->
                        activity!!.tv_register.visibility = View.INVISIBLE


                        if (task.isSuccessful()) {
                            val fm = fragmentManager
                            Toast.makeText(context,"Account aangemaakt", Toast.LENGTH_SHORT).show()
                            activity!!.tv_register.visibility = View.VISIBLE
                            activity!!.tv_register.text = resources.getString(R.string.registreer)
                            activity!!.tv_register.isClickable
                            fm!!.popBackStack()


                        } else {

                            Toast.makeText(context,"Account is al in gebruik", Toast.LENGTH_SHORT).show()
                            }
                        }

                    }
            showProgress(false)
        }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    fun showProgress(show: Boolean) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()

            activity!!.login_layout.visibility = if (show) View.GONE else View.VISIBLE
            activity!!.login_layout.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 0 else 1).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            activity!!.login_layout.visibility = if (show) View.GONE else View.VISIBLE
                        }
                    })

            activity!!.login_progress.visibility = if (show) View.VISIBLE else View.GONE
            activity!!.login_progress.animate()
                    .setDuration(shortAnimTime)
                    .alpha((if (show) 1 else 0).toFloat())
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            activity!!.login_progress.visibility = if (show) View.VISIBLE else View.GONE

                        }
                    })
        } else {

            activity!!.login_progress.visibility = if (show) View.VISIBLE else View.GONE
            activity!!.login_layout.visibility = if (show) View.GONE else View.VISIBLE
        }
    }



}