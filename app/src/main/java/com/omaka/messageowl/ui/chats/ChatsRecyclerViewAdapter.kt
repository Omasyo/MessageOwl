package com.omaka.messageowl.ui.chats

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import com.omaka.messageowl.R
import com.omaka.messageowl.models.ChatCardModel
import com.omaka.messageowl.utils.formatTime
import java.util.*

class ChatsRecyclerViewAdapter(
    private val onImageClick: (image: String?) -> Unit = {},
    private val onClick: (roomId: String) -> Unit
) :
    RecyclerView.Adapter<ChatsRecyclerViewAdapter.ViewHolder>() {
    private var chats: List<ChatCardModel> = listOf()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_view)
        val imageCardView: CardView = view.findViewById(R.id.image_card_view)
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
                imageView.load(image) {
                    allowHardware(false)
                    crossfade(true)
                }
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

                timeView.text = formatTime(timestamp)
                imageCardView.setOnClickListener { onImageClick(image) }
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
                return chats[oldItemPosition].room_id == list[newItemPosition].room_id
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