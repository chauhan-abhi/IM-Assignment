package com.example.demo_day1.ui.contactList.repository

import android.annotation.SuppressLint
import android.util.Log
import com.example.demo_day1.data.db.room_contactsdb.ContactsDao
import com.example.demo_day1.data.remote.ApiInterface
import com.example.demo_day1.data.remote.model.Contact
import com.example.demo_day1.utils.AppUtils
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ContactsRepository @Inject constructor(
    private val api: ApiInterface,
    private val contactsDao: ContactsDao,
    private val appUtils: AppUtils
) {

    fun getContactList(): Observable<List<Contact>> {
        val hasConnection = appUtils.isConnectedToInternet()
        return if (hasConnection)
            Observable.concatArrayEagerDelayError(
                getContactsFromApi(),
                getContactsFromDb()
            )
        else
            return getContactsFromDb()
    }

    private fun getContactsFromApi(): Observable<List<Contact>> {
        return api.getContacts()
            .filter { it.isNotEmpty() }
            .doOnNext {
                Log.d("CONTACTS_API", "Dispatching ${it.size} contacts from API...")
                storeContactsInDb(it)
            }
    }

    private fun getContactsFromDb(): Observable<List<Contact>> {
        return contactsDao.getContacts()
            .filter { it.isNotEmpty() }
            .toObservable()
            .doOnNext {
                Log.d("CONTACTS_DB", "Dispatching ${it.size} contacts from DB...")
            }

    }

    @SuppressLint("CheckResult")
    private fun storeContactsInDb(contactList: List<Contact>) {
        Observable.fromCallable { contactsDao.insertAll(contactList) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.io())
            .subscribe {
                Log.d("CONTACTS_DB", "Dispatching ${contactList.size} contacts from DB...")
            }
    }
}