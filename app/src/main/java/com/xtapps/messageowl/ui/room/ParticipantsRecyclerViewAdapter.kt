package com.xtapps.messageowl.ui.room

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.xtapps.messageowl.R
import com.xtapps.messageowl.models.UserModel

class ParticipantsRecyclerViewAdapter(
) : RecyclerView.Adapter<ParticipantsRecyclerViewAdapter.ViewHolder>() {
    private var test: List<UserModel> = listOf()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.name)
        val image: ImageView = view.findViewById(R.id.imageView)
    ***REMOVED***

    fun submitList(list: List<UserModel>) {
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
            .from(parent.context).inflate(R.layout.participant_view, parent, false)

        return ViewHolder(view)
    ***REMOVED***


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = test[position].name
    ***REMOVED***

    override fun getItemCount(): Int {
        return test.size
    ***REMOVED***
***REMOVED***