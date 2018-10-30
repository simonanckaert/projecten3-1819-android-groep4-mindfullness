package com.groep4.mindfulness.activities


import android.content.pm.PackageManager
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.app.LoaderManager.LoaderCallbacks
import android.content.CursorLoader
import android.content.Loader
import android.database.Cursor
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.TextView
import java.util.ArrayList
import android.Manifest.permission.READ_CONTACTS
import android.app.Activity

import android.content.Intent
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.groep4.mindfulness.R
import com.groep4.mindfulness.R.id.password
import com.groep4.mindfulness.utils.LoginValidation
import kotlinx.android.synthetic.main.activity_login.*
import java.lang.ref.WeakReference


class ActivityLogin : AppCompatActivity(), LoaderCallbacks<Cursor> {
    /**
     * Houd de inlogtaak bij om ervoor te zorgen dat we deze kunnen annuleren als hierom wordt gevraagd.
     */

    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // Stel het login formulier in.
        populateAutoComplete()
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                attemptLogin()
                return@OnEditorActionListener true
            }
            false
        })
        email
        email_sign_in_button.setOnClickListener { attemptLogin() }

        register_account.setOnClickListener { attemptRegister() }
    }

    private fun populateAutoComplete() {
        if (!mayRequestContacts()) {
            return
        }

        loaderManager.initLoader(0, null, this)
    }

    private fun mayRequestContacts(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(email, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok,
                            { requestPermissions(arrayOf(READ_CONTACTS), REQUEST_READ_CONTACTS) })
        } else {
            requestPermissions(arrayOf(READ_CONTACTS), REQUEST_READ_CONTACTS)
        }
        return false
    }

    /**
     * Callback ontvangen wanneer een request is voltooid.
     */
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.size == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete()
            }
        }
    }

    private fun attemptRegister(){
        val intent = Intent(this, ActivityRegister::class.java)
        this.finish()
        this.startActivity(intent)
    }

    /**
     *Validatie en aanmelden van account
     */
    private fun attemptLogin() {

        // Reset errors.
        email.error = null
        password.error = null

        // values
        val emailStr = email.text.toString()
        val passwordStr = password.text.toString()

        var cancel = false
        var focusView: View? = null


        // Controleer op een geldig email adres.
        if (!LoginValidation.isValidEmail(email)) {

            focusView = email
            cancel = true
        }

        if (!LoginValidation.isValidPassword(password)) {
            focusView = password
            cancel = true
        }

        if (cancel) {

            focusView?.requestFocus()

        } else {

            //Aanmelden
            mAuth = FirebaseAuth.getInstance()

            mAuth.signInWithEmailAndPassword(emailStr,passwordStr).addOnCompleteListener(this@ActivityLogin){
                task ->

                if (task.isSuccessful()) {
                    //mainactivity tonen
                    val intent = Intent(this, MainActivity::class.java)
                    this.finish()
                    this.startActivity(intent)

                } else {
                    this.password.error = this.getString(R.string.error_incorrect_password)
                    this.password.requestFocus()
                }
            }

        }
    }


    /**
     * Autologin : data ophalen uit contacten
     */
    override fun onCreateLoader(i: Int, bundle: Bundle?): Loader<Cursor> {
        return CursorLoader(this,

                // Data ophalen van gebruikers profiel uit Contact pagina

                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Alleen email adressen selecteren
                ContactsContract.Contacts.Data.MIMETYPE + " = ?", arrayOf(ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE),

                // Hoofd email adres selecteren. Als er geen is word dit null
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC")
    }

    override fun onLoadFinished(cursorLoader: Loader<Cursor>, cursor: Cursor) {
        val emails = ArrayList<String>()
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS))
            cursor.moveToNext()
        }

        addEmailsToAutoComplete(emails)
    }

    override fun onLoaderReset(cursorLoader: Loader<Cursor>) {

    }

    private fun addEmailsToAutoComplete(emailAddressCollection: List<String>) {

        //Maak een adapter om eerder gebruikte email adressen weer te geven.
        val adapter = ArrayAdapter(this@ActivityLogin,
                android.R.layout.simple_dropdown_item_1line, emailAddressCollection)

        email.setAdapter(adapter)
    }

    object ProfileQuery {
        val PROJECTION = arrayOf(
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY)
        val ADDRESS = 0
        val IS_PRIMARY = 1
    }


    companion object {

        /**
         * Id voor READ_CONTACTS toestemming.
         */
        private val REQUEST_READ_CONTACTS = 0

        /**
         * Dummy data
         * TODO: Echte data gebruiken
         */
        private val DUMMY_CREDENTIALS = arrayOf("test@hotmail.com","123C1234c" )
    }
}
