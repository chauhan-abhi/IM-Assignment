package com.example.demo_day1.ui.contactList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class ContactsViewModelFactory @Inject constructor(
    private val contactsViewModel: ContactListViewModel
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(ContactListViewModel::class.java)) {
            return contactsViewModel as T
        }
        throw IllegalArgumentException("Unknow class name")
    }
}