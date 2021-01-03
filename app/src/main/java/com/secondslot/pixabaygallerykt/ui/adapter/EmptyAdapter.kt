package com.secondslot.pixabaygallerykt.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.secondslot.pixabaygallerykt.R

class EmptyAdapter : RecyclerView.Adapter<EmptyAdapter.EmptyHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_gallery_empty, parent, false)
        return EmptyHolder(view)
    }

    override fun onBindViewHolder(holder: EmptyHolder, position: Int) {
    }

    override fun getItemCount(): Int {
        return 1
    }

    /*
    Класс EmptyHolder
    */
    class EmptyHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}