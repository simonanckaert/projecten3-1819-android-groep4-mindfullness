package com.groep4.mindfulness.adapters

import android.content.res.Resources
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.groep4.mindfulness.R
import com.groep4.mindfulness.model.Oefening
import kotlinx.android.synthetic.main.single_exercise.view.*
import kotlinx.android.synthetic.main.single_exercise_content.view.*
import java.io.FileInputStream


class ExercisesAdapter (val items: ArrayList<Oefening>, val listener: (Oefening) -> Unit) : RecyclerView.Adapter<ExercisesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_exercise, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(items[position], position, listener)
    }

    override fun getItemCount() = items.size

    // Custom ViewHolder
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val frameBtnOefening = view.cl_oefeningenframe!!

        fun bind(item: Oefening, position: Int, listener: (Oefening) -> Unit) = with(frameBtnOefening) {
            var mp: MediaPlayer = MediaPlayer.create(context, R.raw.ademmeditatie)
            mp.isLooping = false
            //frameBtnOefening.btn_single_oef.text = item.naam
            //frameBtnOefening.setOnClickListener { listener(item) }

            var prevPosition = 0

            frameBtnOefening.cv_exercise.setTitle(item.naam)
            frameBtnOefening.cv_exercise.setOnExpandedListener {
                _, isExpanded ->
                if (!isExpanded)
                    mp.stop()
            }

            frameBtnOefening.cv_exercise.ib_playAudio.setOnClickListener{
                if(mp.isPlaying){
                    mp.stop()
                    Toast.makeText(context, "STOPPED", Toast.LENGTH_SHORT).show()
                } else {
                    mp.reset()
                    mp = MediaPlayer.create(context, R.raw.ademmeditatie)
                    mp.start()
                    Toast.makeText(context, "PLAYING", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
