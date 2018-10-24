package com.groep4.mindfulness.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.groep4.mindfulness.R
import com.groep4.mindfulness.model.Sessie
import kotlinx.android.synthetic.main.single_sessie.view.*

class SessieListAdapter (val items: ArrayList<Sessie>, val listener: (Sessie) -> Unit) : RecyclerView.Adapter<SessieListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.single_sessie, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(items[position], position, listener)

    override fun getItemCount() = items.size

    // Custom ViewHolder
    class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val frameBtnSessie = view.ll_sessieframe!!

        fun bind(item: Sessie, position: Int, listener: (Sessie) -> Unit) = with(frameBtnSessie) {
            frameBtnSessie.btn_single_sessie.text = item.naam
            frameBtnSessie.tv_btn_sessie.text = item.beschrijving
            frameBtnSessie.setOnClickListener { listener(item) }
            when (position) {
                0 -> frameBtnSessie.iv_sessie.setImageResource(R.mipmap.m0)
                1 -> frameBtnSessie.iv_sessie.setImageResource(R.mipmap.m1)
                2 -> frameBtnSessie.iv_sessie.setImageResource(R.mipmap.m2)
                3 -> frameBtnSessie.iv_sessie.setImageResource(R.mipmap.m3)
                4 -> frameBtnSessie.iv_sessie.setImageResource(R.mipmap.m4)
                5 -> frameBtnSessie.iv_sessie.setImageResource(R.mipmap.m5)
                6 -> frameBtnSessie.iv_sessie.setImageResource(R.mipmap.m6)
                7 -> frameBtnSessie.iv_sessie.setImageResource(R.mipmap.m7)
                else -> {
                    frameBtnSessie.iv_sessie.setImageResource(R.mipmap.m0)
                }
            }

        }
    }
}

