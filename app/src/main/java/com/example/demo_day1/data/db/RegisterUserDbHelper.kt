package com.example.demo_day1.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

private const val SQL_CREATE_USER_DETAIL_TABLE =
    "CREATE TABLE ${UserContract.User.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${UserContract.User.COLUMN_NAME_FULLNAME} TEXT," +
            "${UserContract.User.COLUMN_NAME_EMAIL} TEXT," +
            "${UserContract.User.COLUMN_NAME_CONTACT} TEXT," +
            "${UserContract.User.COLUMN_NAME_PASSWORD} TEXT," +
            "${UserContract.User.COLUMN_PROFILE_PIC_URI} TEXT)"

private const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${UserContract.User.TABLE_NAME}"

class RegisterUserDbHelper(context: Context?) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_USER_DETAIL_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(SQL_DELETE_TABLE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "User.db"
    }
}