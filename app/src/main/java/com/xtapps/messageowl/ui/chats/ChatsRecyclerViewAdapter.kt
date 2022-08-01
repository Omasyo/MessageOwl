package com.xtapps.messageowl.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.xtapps.messageowl.R
import com.xtapps.messageowl.models.ChatCardModel
import java.text.SimpleDateFormat

class ChatsRecyclerViewAdapter(private val onClick: (roomId: String) -> Unit) :
    RecyclerView.Adapter<ChatsRecyclerViewAdapter.ViewHolder>() {
    private var chats: List<ChatCardModel> = listOf()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.title_text)
        val subtitleTextView: TextView = view.findViewById(R.id.subtitle_text)
        val cardView: CardView = view.findViewById(R.id.card_view)
        val timeView: TextView = view.findViewById(R.id.time)
        val unreadView: TextView = view.findViewById(R.id.unread)
        val unreadContainer: MaterialCardView = view.findViewById(R.id.unread_container)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.chat_card_view, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.bindingAdapterPosition

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(chats[position]) {
            holder.apply {
                textView.text = roomName
                val prefix = if (isGroup) {
                    if (sender_id == FirebaseAuth.getInstance().uid) "You: "
                    else senderName + ": "
                } else ""
                subtitleTextView.text = prefix + recentMessage
                cardView.setOnClickListener {
                    onClick(room_id)
                }
                if (unread == 0) {
                    unreadContainer.visibility = View.GONE
                } else {
                    unreadContainer.visibility = View.VISIBLE
                    unreadView.text = unread.toString()
                }
                val formattedTime = SimpleDateFormat("HH:mm").format(timestamp)
                timeView.text = formattedTime
            }
        }
    }

    override fun getItemCount(): Int {
        return chats.size
    }

    fun submitList(list: List<ChatCardModel>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return chats.size
            }

            override fun getNewListSize(): Int {
                return list.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return chats[oldItemPosition] == list[newItemPosition]
            }

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {
                return chats[oldItemPosition] == list[newItemPosition]
            }
        })
        chats = list
        result.dispatchUpdatesTo(this)
    }
}