package com.xtapps.messageowl.ui.contacts

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
import com.xtapps.messageowl.R
import com.xtapps.messageowl.models.ContactCard

class ContactsRecyclerViewAdapter(
    private val onImageClick: (image: String?) -> Unit = {},
    private val onClick: (userId: String) -> Unit
) :
    RecyclerView.Adapter<ContactsRecyclerViewAdapter.ViewHolder>() {
    private var contacts: List<ContactCard> = listOf()

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val titleText: TextView = view.findViewById(R.id.title_text)
        val subtitleText: TextView = view.findViewById(R.id.subtitle_text)
        val imageCardView: CardView = view.findViewById(R.id.image_card_view)
        val imageView: ImageView = view.findViewById(R.id.image_view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.contact_card_view, parent, false)
        val viewHolder = ViewHolder(view)
        viewHolder.bindingAdapterPosition

        return viewHolder
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        with(contacts[position]) {
            holder.apply {
                imageView.load(image) {
                    allowHardware(false)
                    crossfade(true)
                }
                titleText.text = contactName
                subtitleText.text = username
                view.setOnClickListener {
                    onClick(contacts[position].id)
                }
                imageCardView.setOnClickListener {
                    onImageClick(image)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    fun submitList(list: List<ContactCard>) {
        val result = DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return contacts.size
            }

            override fun getNewListSize(): Int {
                return list.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                return contacts[oldItemPosition] == list[newItemPosition]
            }

            override fun areContentsTheSame(
                oldItemPosition: Int,
                newItemPosition: Int
            ): Boolean {
                return contacts[oldItemPosition] == list[newItemPosition]
            }
        })
        contacts = list
        result.dispatchUpdatesTo(this)
    }
}