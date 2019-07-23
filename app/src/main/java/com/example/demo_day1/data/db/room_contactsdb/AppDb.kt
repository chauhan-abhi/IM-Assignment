package com.example.demo_day1.data.db.room_contactsdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demo_day1.data.remote.model.Contact

@Database(entities = [Contact::class], version = 1)
abstract class AppDb: RoomDatabase() {
    abstract fun contactsDao(): ContactsDao
}