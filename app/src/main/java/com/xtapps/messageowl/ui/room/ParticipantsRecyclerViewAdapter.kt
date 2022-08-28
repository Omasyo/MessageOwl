package com.xtapps.messageowl.ui.room

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.google.android.material.card.MaterialCardView
import com.xtapps.messageowl.R
import com.xtapps.messageowl.models.UserModel

class ParticipantsRecyclerViewAdapter(
    private val onClick: (particpantId: String) -> Unit
) : RecyclerView.Adapter<ParticipantsRecyclerViewAdapter.ViewHolder>() {
    private var test: List<UserModel> = listOf()

    class ViewHolder(val view: MaterialCardView) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.image_view)
    }

    fun submitList(list: List<UserModel>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return test.size
            }

            override fun getNewListSize(): Int {
                return list.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return test[oldItemPosition] == list[newItemPosition]
            }

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {
                return test[oldItemPosition] == list[newItemPosition]
            }
        })
        test = list
        result.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.participant_view, parent, false)

        return ViewHolder(view as MaterialCardView)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.view.setOnClickListener {
            onClick(test[position].id)
        }

        holder.image.load(test[position].profilePic)
        holder.name.text = test[position].name
    }

    override fun getItemCount(): Int {
        return test.size
    }
}