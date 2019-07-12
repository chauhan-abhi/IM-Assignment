package com.example.demo_day1.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.demo_day1.R
import com.example.demo_day1.base.BaseRecyclerViewAdapter
import com.example.demo_day1.model.Contact
import com.example.demo_day1.utils.loadImg
import android.Manifest.permission.CALL_PHONE
import com.example.demo_day1.activities.MainActivity
import androidx.core.app.ActivityCompat
import android.content.pm.PackageManager


class ContactListAdapter(
    contactList: List<Contact>
) : BaseRecyclerViewAdapter<Contact, ContactListAdapter.ViewHolder>(contactList) {

    private lateinit var inflater: LayoutInflater


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.contact_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mList?.let {
            //Log.d("LIST_CONTACT", it.toString())
            holder.bind(it[position])
        }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
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

        fun bind(contact: Contact) {
            personName.text = contact.personName
            email.text = contact.personEmail
            personImageView.loadImg(contact.profileImageUrl)
            callButton.setOnClickListener {

                val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + contact.contactNumber))
                view.context.startActivity(intent)
            }
        }

    }

}