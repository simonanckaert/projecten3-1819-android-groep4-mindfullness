package com.groep4.mindfulness.activities

import android.os.Bundle

import android.support.v7.app.AppCompatActivity

import android.widget.TextView

import com.groep4.mindfulness.R
import com.groep4.mindfulness.fragments.FragmentLogin
import com.groep4.mindfulness.fragments.FragmentRegister
import kotlinx.android.synthetic.main.activity_login.*


class ActivityLogin : AppCompatActivity(){

    lateinit var mLoginFragment: FragmentLogin
    lateinit var mRegisterFragment: FragmentRegister
    var tvRegister: TextView?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        initFragment()
        showLoginFragment()

        tvRegister = findViewById(R.id.tv_register) as TextView


        tvRegister?.setOnClickListener {

            System.out.println("click register")
            showRegisterFragment()


        }

    }

    private fun initFragment() {
        mLoginFragment= FragmentLogin()
        mRegisterFragment= FragmentRegister()
    }

    private fun showLoginFragment() {

        supportFragmentManager.beginTransaction()
                .setCustomAnimations(R.anim.left_in,R.anim.left_out)
                .replace(R.id.login_layout,mLoginFragment,FragmentLogin::class.java.name)
                .commit()

    }
    private fun showRegisterFragment(){

        supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .setCustomAnimations(R.anim.right_in,R.anim.right_out,R.anim.left_in,R.anim.left_out)
                .replace(R.id.login_layout,mRegisterFragment,FragmentRegister::class.java.name)
                .commit()

    }



    override fun onBackPressed() {
        val count = fragmentManager.backStackEntryCount

        if (count == 0) {
            super.onBackPressed()
        } else {
            fragmentManager.popBackStack()
        }
    }




}