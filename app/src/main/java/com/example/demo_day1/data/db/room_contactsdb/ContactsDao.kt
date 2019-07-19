package com.example.demo_day1.data.db.room_contactsdb

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.demo_day1.data.remote.model.Contact
import io.reactivex.Single

interface ContactsDao {

    @Query("SELECT * from contact")
    fun getContacts(): Single<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(contacts: List<Contact>)

    @Update
    fun updateContact(contact: Contact)
}
