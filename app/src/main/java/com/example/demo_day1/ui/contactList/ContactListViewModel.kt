package com.example.demo_day1.ui.contactList

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.demo_day1.R
import com.example.demo_day1.data.remote.model.Contact
import com.example.demo_day1.ui.contactList.repository.ContactsRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ContactListViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    private lateinit var networkSubscription: Disposable

    private val contactsResult: MutableLiveData<List<Contact>> = MutableLiveData()
    private val contactsError: MutableLiveData<String> = MutableLiveData()
    private val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    fun contactsResult(): LiveData<List<Contact>> = contactsResult
    fun contactsError(): LiveData<String> = contactsError
    fun loadingVisibility(): MutableLiveData<Int> = loadingVisibility

    fun fetchContacts() {
        networkSubscription = contactsRepository.getContactList()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread(), true)
            .doOnSubscribe { showLoading() }
            .doOnTerminate { hideLoading() }
            .debounce(400, TimeUnit.MILLISECONDS)
            .subscribe(
                { contactsList -> setContactsLiveData(contactsList) },
                { onContactsError() }
            )
    }


    private fun setContactsLiveData(contactsList: List<Contact>) {
        contactsResult.postValue(contactsList)
    }

    private fun onContactsError() {
        contactsError.postValue(R.string.contacts_error.toString())
        hideLoading()
    }


    private fun hideLoading() {
        loadingVisibility.postValue(View.GONE)
    }

    private fun showLoading() {
        loadingVisibility.postValue(View.VISIBLE)
        contactsError.postValue(null)
    }

    override fun onCleared() {
        super.onCleared()
        networkSubscription.dispose()
    }

}