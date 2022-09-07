package com.xtapps.messageowl.ui.room

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.Coil
import coil.load
import coil.util.CoilUtils
import com.google.android.material.card.MaterialCardView
import com.xtapps.messageowl.R
import com.xtapps.messageowl.models.UserModel

class ParticipantsRecyclerViewAdapter(
    private val onImageClick: (image: String?) -> Unit = {***REMOVED***,
    private val onClick: (particpantId: String) -> Unit
) : RecyclerView.Adapter<ParticipantsRecyclerViewAdapter.ViewHolder>() {
    private var participants: List<UserModel> = listOf()

    class ViewHolder(val view: MaterialCardView) : RecyclerView.ViewHolder(view) {
        val nameView: TextView = view.findViewById(R.id.name)
        val imageView: ImageView = view.findViewById(R.id.image_view)
        val imageCardView: CardView = view.findViewById(R.id.image_card_view)
    ***REMOVED***

    fun submitList(list: List<UserModel>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return participants.size
            ***REMOVED***

            override fun getNewListSize(): Int {
                return list.size
            ***REMOVED***

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return participants[oldItemPosition] == list[newItemPosition]
            ***REMOVED***

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
***REMOVED***: Boolean {
                return participants[oldItemPosition] == list[newItemPosition]
            ***REMOVED***
        ***REMOVED***)
        participants = list
        result.dispatchUpdatesTo(this)
    ***REMOVED***

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context).inflate(R.layout.participant_view, parent, false)

        return ViewHolder(view as MaterialCardView)
    ***REMOVED***


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(participants[position]) {
            holder.apply {
              view.setOnClickListener {
                  onClick(participants[position].id)
              ***REMOVED***
                imageView.load(profilePic)

                nameView.text = name
                imageCardView.setOnClickListener { onImageClick(profilePic) ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    override fun getItemCount(): Int {
        return participants.size
    ***REMOVED***
***REMOVED***