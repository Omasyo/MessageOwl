package com.xtapps.messageowl.ui.home.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.xtapps.messageowl.R
import com.xtapps.messageowl.ui.home.HomeFragmentDirections

class ChatsRecyclerViewAdapter : RecyclerView.Adapter<ChatsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.text_home)
        val cardView = view.findViewById<CardView>(R.id.card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.chat_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = "Chats $position"
        holder.cardView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDirectMessageFragment("Chats Screen $position")
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return 100
    }
}