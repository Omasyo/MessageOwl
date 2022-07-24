package com.xtapps.messageowl.ui.room

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xtapps.messageowl.R
import com.xtapps.messageowl.models.MessageModel
import com.xtapps.messageowl.models.MessageWithSender
import java.text.SimpleDateFormat

class RoomRecyclerViewAdapter(
    private val isGroup: Boolean = false
) : RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder>() {
    private var test: List<MessageWithSender> = listOf()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val content: TextView = view.findViewById(R.id.content_textview)
        val sender: TextView = view.findViewById(R.id.sender_textview)
        val time: TextView = view.findViewById(R.id.time_text)
        val image:View = view.findViewById(R.id.imageView)
    ***REMOVED***

    fun submitList(list: List<MessageWithSender>) {
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
        return test[position].message.senderId.toInt()
    ***REMOVED***

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = with(
            LayoutInflater
                .from(parent.context)
        ) {
            when (viewType) {
                2 -> inflate(R.layout.user_message_bubble, parent, false)
                else -> inflate(R.layout.message_bubble, parent, false)
            ***REMOVED***
        ***REMOVED***
        return ViewHolder(view)
    ***REMOVED***


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!isGroup) holder.sender.visibility = View.GONE
        holder.content.text = test[position].message.content
        holder.sender.text = test[position].user.name
        val timestamp = test[position].message.timestamp
        val date = SimpleDateFormat("MM/dd/yyyy").format(timestamp)
        val time = SimpleDateFormat("HH:mm").format(timestamp)
        holder.time.text = time

        if (position < test.lastIndex) {
            val sender = test[position].user.id
            val nextSender = test[position+1].user.id
            val nextTime = SimpleDateFormat("HH:mm").format(test[position + 1].message.timestamp)
            if (sender == nextSender && time == nextTime) {
                holder.time.visibility = View.GONE
                (holder.view.layoutParams as ViewGroup.MarginLayoutParams).bottomMargin =
                    holder.view.resources.getDimensionPixelSize(R.dimen.consecutive_bubble_margin)
            ***REMOVED***
        ***REMOVED***
        if(position > 0) {
            val sender = test[position].user.id
            val prevSender = test[position-1].user.id
            val prevTime = SimpleDateFormat("HH:mm").format(test[position - 1].message.timestamp)
            if (sender == prevSender && time == prevTime) {
                holder.image.visibility = View.INVISIBLE
                holder.sender.visibility = View.GONE
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    override fun getItemCount(): Int {
        return test.size
    ***REMOVED***
***REMOVED***