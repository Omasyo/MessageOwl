package com.xtapps.messageowl

import android.graphics.Color
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.marginEnd
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.google.android.material.imageview.ShapeableImageView

class MessageBubbleRecyclerAdapter : RecyclerView.Adapter<MessageBubbleRecyclerAdapter.ViewHolder>() {
    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val content = view.findViewById<TextView>(R.id.content_textview)
        val bubble = view.findViewById<MaterialCardView>(R.id.bubble_view)
        val image = view.findViewById<ShapeableImageView>(R.id.imageView)
    ***REMOVED***

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.message_bubble_view, parent, false) as LinearLayout
        return ViewHolder(view)
    ***REMOVED***

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.content.text = dummyMessage[position]
        val condition = position % (3..6).random() == 0
        if(condition) {

            holder.image.visibility = View.GONE
            holder.bubble.apply {
                setCardBackgroundColor(Color.TRANSPARENT)
                strokeWidth = resources.getDimensionPixelOffset(R.dimen.stroke_width)
                val typedValue = TypedValue()
                context.theme.resolveAttribute(com.hbb20.R.attr.colorPrimary, typedValue, true)
                strokeColor = typedValue.data

                (layoutParams as LinearLayout.LayoutParams).apply {
                    (holder.view as LinearLayout).gravity = Gravity.END
                    marginStart = resources.getDimensionPixelOffset(R.dimen.bubble_far_margin)
                    marginEnd = 0
                ***REMOVED***
            ***REMOVED***
        ***REMOVED***
    ***REMOVED***

    override fun getItemCount(): Int {
        return 100
    ***REMOVED***
***REMOVED***