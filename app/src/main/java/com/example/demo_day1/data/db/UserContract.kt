package com.example.demo_day1.data.db

import android.provider.BaseColumns

object UserContract {
    object User: BaseColumns {
        const val TABLE_NAME =  "userdetail"
        const val COLUMN_NAME_FULLNAME = "fullname"
        const val COLUMN_NAME_EMAIL = "email"
        const val COLUMN_NAME_CONTACT = "contact"
        const val COLUMN_NAME_PASSWORD = "password"
        const val COLUMN_PROFILE_PIC_URI = "profilepic"
    }
}