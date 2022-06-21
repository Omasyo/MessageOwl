package com.xtapps.messageowl.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.xtapps.messageowl.R

class ChatsRecycleViewAdapter : RecyclerView.Adapter<ChatsRecycleViewAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.text_home)
    ***REMOVED***

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.chat_card_view, parent, false)
        return ViewHolder(view);
    ***REMOVED***

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = "Chats $position"
    ***REMOVED***

    override fun getItemCount(): Int {
        return 100
    ***REMOVED***
***REMOVED***