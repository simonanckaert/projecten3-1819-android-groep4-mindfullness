package com.groep4.mindfulness.fragments

import android.app.Activity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.groep4.mindfulness.R
import com.groep4.mindfulness.activities.ActivityPage
import com.groep4.mindfulness.activities.MainActivity
import com.groep4.mindfulness.model.Oefening
import com.groep4.mindfulness.model.Sessie
import kotlinx.android.synthetic.main.fragment_oefening_feedback.*
import java.io.DataOutputStream
import java.net.HttpURLConnection
import java.net.URL
import com.koushikdutta.async.http.callback.RequestCallback
import android.R.attr.keySet
import com.koushikdutta.ion.builder.RequestBuilder
import okhttp3.*


class FragmentOefeningFeedback : Fragment() {
    private var txtOefeningNaam : TextView? = null
    private var buttonOpslaan : Button? = null
    private var sessie : Sessie? = null
    private var ratingBar : RatingBar? = null
    private var ratingFeedback : Int = 5
    private var txtFeedback : TextInputEditText? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) : View {

        val view = inflater.inflate(R.layout.fragment_oefening_feedback, container, false)

        txtOefeningNaam = view.findViewById(R.id.textViewNaam) as TextView
        buttonOpslaan = view.findViewById(R.id.buttonBevestigen)
        ratingBar = view.findViewById(R.id.ratingBar) as RatingBar
        txtFeedback = view.findViewById(R.id.txtFeedback)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var oefening = arguments!!.getParcelable<Oefening>("oefening")
        val pageActivity = activity as ActivityPage

        buttonOpslaan!!.setOnClickListener {
            /*val sessiePageFragment = FragmentSessiePage()
            val page = arguments!!.getInt("page", 0)
            val sessie = FragmentSessieLijst().sessies.find { sessie1 : Sessie -> sessie1.sessieId.equals(oefening.sessieId) }

            Log.d(tag, sessie.toString())
            val bundle = Bundle()
            bundle.putParcelable("key_sessie", sessie)
            bundle.putInt("key_page", page)
            sessiePageFragment.arguments = bundle
            (activity as ActivityPage).setFragment(sessiePageFragment, false)*/
            val serverURL: String = "http://141.134.155.219:3000/oefeningen/oef/" + oefening.oefenigenId + "/feedback"
            val url = URL(serverURL)
            /*val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.connectTimeout = 300000
            connection.doOutput = true


            connection.setRequestProperty("charset", "utf-8")
            connection.setRequestProperty("Content-Type", "application/json")

            //try {
            val thread = Thread(Runnable {
                try {
                    val outputStream = DataOutputStream(connection.outputStream)
                    outputStream.write(oefening.oefenigenId)
                    outputStream.writeChars(txtFeedback!!.text.toString())
                    outputStream.write(ratingFeedback)
                    outputStream.flush()
                    outputStream.close()
                    (activity as ActivityPage).onBackPressed()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            })

            thread.start()

            /*} catch (exception: Exception) {


            }*/*/

            //val thread = Thread(Runnable {
                //try {

                val builder = Request.Builder().url(url)

                val sb = StringBuilder()
                sb.append("oefeningId=" + oefening.oefenigenId)
                        .append("&&")
                        .append("beschrijving="+txtFeedback!!.text)
                        .append("&&")
                        .append("score=" + ratingFeedback)

                val fromBodyBuilder = FormBody.Builder()
                fromBodyBuilder.add("oefeningId", oefening.oefenigenId.toString())
                fromBodyBuilder.add("beschrijving", txtFeedback!!.text.toString())
                fromBodyBuilder.add("score", ratingFeedback.toString())

                /*for (entry in postDataParams) {
                    Log.d("param--->", entry.key + ":" + entry.value)
                    fromBodyBuilder.add(entry.key,entry.value)
                }*/


                // val response = builder.post(RequestBody.create(sb.toString(),sb.toString()))
                var response = (activity as ActivityPage)
                        .postFeedback("http://141.134.155.219:3000/oefeningen/oef/" + oefening.oefenigenId + "/feedback", fromBodyBuilder.build())


                txtOefeningNaam!!.text = response
            (activity as ActivityPage).onBackPressed()
                /*} catch (e: Exception) {
                    e.printStackTrace()
                }*/
            /*})

                thread.start()*/

            }


            ratingBar!!.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
                ratingFeedback = (rating * 2).toInt()
                //txtOefeningNaam!!.text = ratingFeedback.toString()
            }
        txtOefeningNaam!!.text = oefening.naam
    }


}