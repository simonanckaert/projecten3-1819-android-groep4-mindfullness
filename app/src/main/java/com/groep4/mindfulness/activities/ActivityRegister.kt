package com.groep4.mindfulness.activities

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.groep4.mindfulness.R
import com.groep4.mindfulness.utils.LoginValidation
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import com.google.android.gms.tasks.Task


class ActivityRegister : AppCompatActivity() {

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        email_register_in_button.setOnClickListener { attemptRegister() }

    }

    fun attemptRegister() {


        // Reset errors.
        mAuth = FirebaseAuth.getInstance()
        register_email.error = null
        register_password.error = null

        // values
        val emailStr = register_email.text.toString()
        val passwordStr = register_password.text.toString()

        var cancel = false
        var focusView: View? = null


        // Controleer op een geldig email adres.
        if (!LoginValidation.isValidEmail(register_email)) {

            focusView = email
            cancel = true
        }

        if (!LoginValidation.isValidPassword(register_password)) {
            focusView = password
            cancel = true
        }

        if (cancel) {
            focusView?.requestFocus()
        } else {
            mAuth.createUserWithEmailAndPassword(emailStr, passwordStr).addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    System.out.println("Succes Register")
                    val intent = Intent(this, ActivityLogin::class.java)
                    this.finish()
                    this.startActivity(intent)

                } else {

                this.password.error = this.getString(R.string.error_incorrect_password)
                this.password.requestFocus()

                }
            }

        }

    }
}