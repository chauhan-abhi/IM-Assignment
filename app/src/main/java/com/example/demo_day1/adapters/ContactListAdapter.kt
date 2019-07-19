package com.example.demo_day1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.demo_day1.data.remote.model.Contact
import com.example.demo_day1.base.GenericRecyclerViewAdapter
import com.example.demo_day1.ui.contactList.ContactItemViewHolder


class ContactListAdapter(
    itemLayout: Int,
    contactList: List<Contact>
) : GenericRecyclerViewAdapter<Contact, ContactItemViewHolder>(itemLayout, contactList) {

    override fun onCreateViewHolder(parent: ViewGroup, itemType: Int): ContactItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(itemLayout, parent, false)
        return ContactItemViewHolder(itemView)
    }

}