package com.example.demo_day1.ui.contactList.contactitem

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo_day1.data.remote.model.Contact

class ContactViewModel : ViewModel() {
    private val personName = MutableLiveData<String>()
    private val personEmail = MutableLiveData<String>()
    private val personImageView = MutableLiveData<String>()
    private val personContact = MutableLiveData<String>()

    fun bind(contact: Contact) {
        personName.value = contact.personName
        personEmail.value = contact.personEmail
        personImageView.value = contact.profileImageUrl
        personContact.value = contact.contactNumber
    }

    fun makeCall(view: View, contactNum: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + this.personContact.value))
        view.context.startActivity(intent)
    }


}