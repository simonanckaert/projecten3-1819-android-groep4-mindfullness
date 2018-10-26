package com.groep4.mindfulness.adapters

import android.media.MediaPlayer
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.groep4.mindfulness.R
import com.groep4.mindfulness.model.Oefening
import kotlinx.android.synthetic.main.single_exercise.view.*
import kotlinx.android.synthetic.main.single_exercise_content.view.*
import java.util.*
import android.support.v4.view.ViewCompat.setActivated




class ExercisesAdapter (private val items: ArrayList<Oefening>, private val listener: (Oefening) -> Unit) : RecyclerView.Adapter<ExercisesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_exercise, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        return holder.bind(items[position], position, listener)
    }

    override fun getItemCount() = items.size

    fun onClick(v: View, oef: Oefening) {
        Toast.makeText(v.context, "000000", Toast.LENGTH_LONG).show()
    }

    // Custom ViewHolder
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        val frameBtnOefening = view.cl_oefeningenframe!!

        fun bind(oefening: Oefening, position: Int, listener: (Oefening) -> Unit) = with(frameBtnOefening) {

            // Oefening has audio
            var mp: MediaPlayer = MediaPlayer.create(context, R.raw.ademmeditatie)
            mp.isLooping = false

            frameBtnOefening.cv_exercise.setTitle(oefening.naam)
            frameBtnOefening.cv_exercise.setOnExpandedListener {
                view, isExpanded ->
                view.tv_contents.text = oefening.beschrijving
                if (!isExpanded) {
                    mp.stop()
                    frameBtnOefening.cv_exercise.ib_playAudio.setImageResource(R.drawable.ic_play_arrow_white_24dp)
                }
            }

            frameBtnOefening.cv_exercise.ib_playAudio.setOnClickListener{
                if(mp.isPlaying){
                    mp.stop()
                    frameBtnOefening.cv_exercise.ib_playAudio.setImageResource(R.drawable.ic_play_arrow_white_24dp)
                    //Toast.makeText(context, "STOPPED", Toast.LENGTH_SHORT).show()
                } else {
                    mp.reset()
                    mp = MediaPlayer.create(context, R.raw.ademmeditatie)
                    mp.start()
                    frameBtnOefening.cv_exercise.ib_playAudio.setImageResource(R.drawable.ic_pause_white_24dp)
                    //Toast.makeText(context, "PLAYING", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}