package com.example.demo_day1.data.db.room_contactsdb

import androidx.room.*
import com.example.demo_day1.data.remote.model.Contact
import io.reactivex.Single

@Dao
interface ContactsDao {

    @Query("SELECT * from contacts")
    fun getContacts(): Single<List<Contact>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: Contact)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(contacts: List<Contact>)

    @Query("DELETE FROM contacts")
    fun deleteAll()

    @Update
    fun updateContact(contact: Contact)
}
