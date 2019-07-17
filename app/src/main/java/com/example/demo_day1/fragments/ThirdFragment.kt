package com.example.demo_day1.fragments


import android.app.Activity
import android.content.ContentValues
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_third.*
import com.example.demo_day1.R
import com.example.demo_day1.db.RegisterUserDbHelper
import com.example.demo_day1.db.UserContract
import com.example.demo_day1.utils.*

/**************Register Fragment****************/
class ThirdFragment : Fragment() {

    lateinit var navController: NavController
    lateinit var dbHelper: SQLiteOpenHelper
    private var fullName = ""
    private var email = ""
    private var mobile = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        navController = Navigation.findNavController(activity as Activity, R.id.onboarding_fragment)
        return inflater.inflate(R.layout.fragment_third, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        next.setOnClickListener {
            validateFields()
        }
        dbHelper = RegisterUserDbHelper(context)
    }

    private fun validateFields() {
        if (editTextName.isValidFullName(context!!) &&
            editTextEmail.isValidEmail(context!!) &&
            editTextMobile.isValidContact(context!!) &&
            editTextPassword.isValidPassword(context!!)
        ) {
            fullName = editTextName.text.toString()
            email = editTextEmail.text.toString()
            mobile = editTextMobile.text.toString()
            password = editTextPassword.text.toString()

            // save data in shared pref
            val bundle = bundleOf(
                FULL_NAME_KEY to fullName,
                EMAIL_KEY to email,
                MOBILE_KEY to mobile,
                PASSWORD_KEY to password
            )
            // open login
            saveInDb()
            saveInSharedPref()
            navController.navigate(R.id.loginFragment, bundle)

        }
    }

    private fun saveInSharedPref() {
        activity!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)?.let { pref ->
            pref.edit()?.apply {
                putBoolean(PREF_NAME, true)
                putString(FULL_NAME_KEY, fullName)
                putString(EMAIL_KEY, email)
                putString(MOBILE_KEY, mobile)
                putString(PASSWORD_KEY, password)
                putString(PROFILE_PIC_URI, "")
                apply()
            }

        }

    }

    private fun saveInDb() {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(UserContract.User.COLUMN_NAME_FULLNAME, fullName)
            put(UserContract.User.COLUMN_NAME_EMAIL, email)
            put(UserContract.User.COLUMN_NAME_CONTACT, mobile)
            put(UserContract.User.COLUMN_NAME_PASSWORD, password)
            put(UserContract.User.COLUMN_PROFILE_PIC_URI, "")
        }

        val newRowId = db?.insert(UserContract.User.TABLE_NAME, null, values)
        Log.i("ROWID", newRowId.toString())
    }
}

