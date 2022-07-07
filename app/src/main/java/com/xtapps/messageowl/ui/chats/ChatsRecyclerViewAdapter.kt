package com.xtapps.messageowl.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xtapps.messageowl.R
import com.xtapps.messageowl.models.ChatRoom

class ChatsRecyclerViewAdapter(private val onClick: (View, roomId: String, Boolean) -> Unit) : RecyclerView.Adapter<ChatsRecyclerViewAdapter.ViewHolder>() {
    private var test: List<ChatRoom> = listOf()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text_home)
        val subtitleTextView: TextView = view.findViewById(R.id.text_home2)
        val cardView: CardView = view.findViewById(R.id.card_view)
    ***REMOVED***

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.chat_card_view, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.bindingAdapterPosition

        return viewHolder
    ***REMOVED***

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.textView.text = test[position].name
        holder.subtitleTextView.text = if (test[position].isGroup) "This is a group chat" else "This is not a group chat"
        holder.cardView.setOnClickListener {
            onClick(it, test[position].id, test[position].isGroup)
        ***REMOVED***
    ***REMOVED***

    override fun getItemCount(): Int {
        return test.size
    ***REMOVED***

    fun submitList(list: List<ChatRoom>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return test.size
            ***REMOVED***

            override fun getNewListSize(): Int {
                return list.size
            ***REMOVED***

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return test[oldItemPosition] == list[newItemPosition]
            ***REMOVED***

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
***REMOVED***: Boolean {
                return test[oldItemPosition] == list[newItemPosition]
            ***REMOVED***
        ***REMOVED***)
        test = list
        result.dispatchUpdatesTo(this)
    ***REMOVED***
***REMOVED***