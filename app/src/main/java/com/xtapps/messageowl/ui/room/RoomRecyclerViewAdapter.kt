package com.xtapps.messageowl.ui.room

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.R
import com.xtapps.messageowl.models.MessageWithSender
import com.xtapps.messageowl.utils.formatTime
import java.text.SimpleDateFormat

class RoomRecyclerViewAdapter(
    private val isGroup: Boolean = false,
    private val onImageClick: (image: Drawable?) -> Unit = {***REMOVED***
) : RecyclerView.Adapter<RoomRecyclerViewAdapter.ViewHolder>() {

    private val authUser = FirebaseAuth.getInstance().currentUser!!
    private var messages: List<MessageWithSender> = listOf()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val contentView: TextView = view.findViewById(R.id.content_textview)
        val senderView: TextView = view.findViewById(R.id.sender_textview)
        val timeView: TextView = view.findViewById(R.id.time_text)
        val imageCardView: CardView? = view.findViewById(R.id.image_card_view)
        val imageView: ImageView? = view.findViewById(R.id.image_view)
    ***REMOVED***

    fun submitList(list: List<MessageWithSender>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return messages.size
            ***REMOVED***

            override fun getNewListSize(): Int {
                return list.size
            ***REMOVED***

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return messages[oldItemPosition].message.id == list[newItemPosition].message.id
            ***REMOVED***

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
***REMOVED***: Boolean {
                return false//test[oldItemPosition] == list[newItemPosition]
            ***REMOVED***
        ***REMOVED***)
        messages = list
        result.dispatchUpdatesTo(this)
    ***REMOVED***

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].message.senderId == authUser.uid) 0
        else 1
    ***REMOVED***

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = with(
            LayoutInflater
                .from(parent.context)
        ) {
            when (viewType) {
                0 -> inflate(R.layout.user_message_bubble, parent, false)
                else -> inflate(R.layout.message_bubble, parent, false)
            ***REMOVED***
        ***REMOVED***
        return ViewHolder(view)
    ***REMOVED***


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        fun sameSender(m1: MessageWithSender, m2: MessageWithSender) = m1.user?.id == m2.user?.id

        with(messages[position]) {
            holder.apply {
                if (!isGroup) senderView.visibility = View.GONE
                imageView?.load(user?.profilePic)
                contentView.text = message.content
                senderView.text = user?.name
                val timestamp = message.timestamp

//                val date = SimpleDateFormat("MM/dd/yyyy").format(timestamp)
                val time = SimpleDateFormat("HH:mm").format(timestamp)
                timeView.text = formatTime(timestamp)

                if (position < messages.lastIndex) {
                    val nextTime =
                        SimpleDateFormat("HH:mm").format(messages[position + 1].message.timestamp)
                    if (sameSender(
                            messages[position],
                            messages[position + 1]
            ***REMOVED*** && time == nextTime
        ***REMOVED*** {
                        timeView.visibility = View.GONE
                    ***REMOVED*** else {
                        timeView.visibility = View.VISIBLE
                    ***REMOVED***
                ***REMOVED***

                if (position > 0) {
                    val prevTime =
                        SimpleDateFormat("HH:mm").format(messages[position - 1].message.timestamp)
                    if (sameSender(
                            messages[position],
                            messages[position - 1]
            ***REMOVED*** && time == prevTime
        ***REMOVED*** {
                        imageCardView?.visibility = View.INVISIBLE
                        senderView.visibility = View.GONE
                    ***REMOVED*** else {
                        imageCardView?.visibility = View.VISIBLE
                        if (user?.id != authUser.uid) holder.senderView.visibility =
                            View.VISIBLE
                    ***REMOVED***
                ***REMOVED***
                imageCardView?.setOnClickListener { onImageClick(imageView?.drawable) ***REMOVED***

                //for private groups
                if (!isGroup) senderView.visibility = View.GONE
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    override fun getItemCount(): Int {
        return messages.size
    ***REMOVED***
***REMOVED***