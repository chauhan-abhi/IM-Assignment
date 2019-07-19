package com.example.demo_day1.ui.contactList

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.demo_day1.R
import com.example.demo_day1.base.BaseRecyclerViewHolder
import com.example.demo_day1.data.remote.model.Contact
import com.example.demo_day1.utils.loadImg

class ContactItemViewHolder(val view: View) : BaseRecyclerViewHolder<Contact>(view) {
    private var personName: TextView
    private var email: TextView
    private var personImageView: ImageView
    private var callButton: ImageView

    init {
        personName = view.findViewById(R.id.personName)
        email = view.findViewById(R.id.personEmail)
        personImageView = view.findViewById(R.id.personImageView)
        callButton = view.findViewById(R.id.call)
    }

    override fun bindData(contact: Contact) {
        personName.text = contact.personName
        email.text = contact.personEmail
        personImageView.loadImg(contact.profileImageUrl)
        callButton.setOnClickListener {

            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.contactNumber))
            view.context.startActivity(intent)
        }
    }

}