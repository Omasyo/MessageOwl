package com.xtapps.messageowl.ui.chats

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xtapps.messageowl.R
import com.xtapps.messageowl.database.ChatRoomDatabase
import com.xtapps.messageowl.models.ChatRoom
import com.xtapps.messageowl.ui.home.HomeFragmentDirections

class ChatsRecyclerViewAdapter(private val onClick: (View, position: Int) -> Unit) : RecyclerView.Adapter<ChatsRecyclerViewAdapter.ViewHolder>() {
    private var test: List<ChatRoom> = listOf()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.text_home)
        val cardView: CardView = view.findViewById(R.id.card_view)
    ***REMOVED***

    fun submitList(list: List<ChatRoom>) {
        Log.d("TAG", "submitList: submitting")
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
        holder.cardView.setOnClickListener {
            onClick(it, position)
        ***REMOVED***
    ***REMOVED***

    override fun getItemCount(): Int {
        return test.size
    ***REMOVED***
***REMOVED***