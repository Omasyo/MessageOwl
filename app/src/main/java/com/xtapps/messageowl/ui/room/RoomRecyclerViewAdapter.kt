package com.xtapps.messageowl.ui.room

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xtapps.messageowl.R
import com.xtapps.messageowl.models.MessageModel

class RoomRecyclerViewAdapter(
    private val isGroup: Boolean = false
) : RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder>() {
    private var test: List<MessageModel> = listOf()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val content: TextView = view.findViewById(R.id.content_textview)
        val sender: TextView = view.findViewById(R.id.sender_textview)
    ***REMOVED***

    fun submitList(list: List<MessageModel>) {
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

    override fun getItemViewType(position: Int): Int {
        return test[position].senderId.toInt() % 2
    ***REMOVED***

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = with(
            LayoutInflater
                .from(parent.context)
        ) {
            when (viewType) {
                0 -> inflate(R.layout.message_bubble, parent, false) as LinearLayout
                else -> inflate(R.layout.user_message_bubble, parent, false) as LinearLayout
            ***REMOVED***
        ***REMOVED***
        return ViewHolder(view)
    ***REMOVED***


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!isGroup) holder.sender.visibility = View.GONE
        holder.content.text = test[position].content
        holder.sender.text = "User ${test[position].senderId***REMOVED***"
    ***REMOVED***

    override fun getItemCount(): Int {
        return test.size
    ***REMOVED***
***REMOVED***