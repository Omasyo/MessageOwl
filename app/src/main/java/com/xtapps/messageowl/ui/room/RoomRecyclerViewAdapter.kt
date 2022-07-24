package com.xtapps.messageowl.ui.room

import android.icu.text.Transliterator
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
                return test[oldItemPosition].message.id == list[newItemPosition].message.id
            ***REMOVED***

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
***REMOVED***: Boolean {
                return false//test[oldItemPosition] == list[newItemPosition]
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
                1 -> inflate(R.layout.user_message_bubble, parent, false)
                else -> inflate(R.layout.message_bubble, parent, false)
            ***REMOVED***
        ***REMOVED***
        return ViewHolder(view)
    ***REMOVED***


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun sameSender(m1: MessageWithSender, m2: MessageWithSender)
        = m1.user.id == m2.user.id

        if (!isGroup) holder.sender.visibility = View.GONE
        holder.content.text = test[position].message.content
        holder.sender.text = test[position].user.name
        val timestamp = test[position].message.timestamp
        val date = SimpleDateFormat("MM/dd/yyyy").format(timestamp)
        val time = SimpleDateFormat("HH:mm").format(timestamp)
        holder.time.text = time

        if (position < test.lastIndex) {
            val nextTime = SimpleDateFormat("HH:mm").format(test[position + 1].message.timestamp)
            if (sameSender(test[position] , test[position+1]) && time == nextTime) {
                holder.time.visibility = View.GONE
            ***REMOVED*** else{
                holder.time.visibility = View.VISIBLE
            ***REMOVED***
        ***REMOVED***

        if(position > 0) {
            val prevTime = SimpleDateFormat("HH:mm").format(test[position - 1].message.timestamp)
            if(test[position].message.content == "Hey") Log.d("Messago", "onBindViewHolder: Normal $position ${test[position]***REMOVED*** ${test[position-1]***REMOVED***")
            if (sameSender(test[position] , test[position-1]) && time == prevTime) {
                Log.d("Messago", "onBindViewHolder: Bullcrap $position ${test[position]***REMOVED*** ${test[position-1]***REMOVED***")
                holder.image.visibility = View.INVISIBLE
                holder.sender.visibility = View.GONE
            ***REMOVED*** else {
                holder.image.visibility = View.VISIBLE
                holder.sender.visibility = View.VISIBLE
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    override fun getItemCount(): Int {
        return test.size
    ***REMOVED***
***REMOVED***