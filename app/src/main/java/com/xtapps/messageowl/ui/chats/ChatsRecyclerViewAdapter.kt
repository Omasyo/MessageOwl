package com.xtapps.messageowl.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.xtapps.messageowl.R
import com.xtapps.messageowl.database.ChatRoomDatabase
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.ui.home.HomeFragmentDirections

class ChatsRecyclerViewAdapter(private val test: List<ChatRoom>) : RecyclerView.Adapter<ChatsRecyclerViewAdapter.ViewHolder>() {

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text_home)
        val cardView: CardView = view.findViewById(R.id.card_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.chat_card_view, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = test[position].name
        holder.cardView.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDirectMessageFragment("Chats Screen $position")
            it.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return test.size
    }
}