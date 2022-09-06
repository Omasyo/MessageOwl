package com.xtapps.messageowl.ui.room

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.card.MaterialCardView
import com.xtapps.messageowl.R
import com.xtapps.messageowl.models.UserModel

class ParticipantsRecyclerViewAdapter(
    private val onImageClick: (image: Drawable?) -> Unit = {},
    private val onClick: (particpantId: String) -> Unit
) : RecyclerView.Adapter<ParticipantsRecyclerViewAdapter.ViewHolder>() {
    private var participants: List<UserModel> = listOf()

    class ViewHolder(val view: MaterialCardView) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.name)
        val imageView: ImageView = view.findViewById(R.id.image_view)
        val imageCardView: CardView = view.findViewById(R.id.image_card_view)
    }

    fun submitList(list: List<UserModel>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return participants.size
            }

            override fun getNewListSize(): Int {
                return list.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return participants[oldItemPosition] == list[newItemPosition]
            }

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {
                return participants[oldItemPosition] == list[newItemPosition]
            }
        })
        participants = list
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.participant_view, parent, false)

        return ViewHolder(view as MaterialCardView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(participants[position]) {
            holder.apply {
              view.setOnClickListener {
                  onClick(participants[position].id)
              }
                imageView.load(profilePic)
                nameView.text = name
                imageCardView.setOnClickListener { onImageClick(imageView.drawable) }
            }
        }
    }

    override fun getItemCount(): Int {
        return participants.size
    }
}