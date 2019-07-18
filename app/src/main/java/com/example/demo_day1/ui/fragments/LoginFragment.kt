package com.example.demo_day1.ui.fragments


import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.provider.BaseColumns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.demo_day1.R
import com.example.demo_day1.ui.activities.MainActivity
import com.example.demo_day1.data.db.RegisterUserDbHelper
import com.example.demo_day1.data.db.UserContract
import com.example.demo_day1.utils.isValidPassword
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
    lateinit var dbHelper: SQLiteOpenHelper
    private var mobile = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_next.setOnClickListener {
            mobile = editTextMobile.text.toString()
            password = editTextPassword.text.toString()
            validateLogin()
        }
        dbHelper = RegisterUserDbHelper(context)
    }

    private fun validateLogin() {
        val cursor = checkExistingUserFromDb()

        with(cursor) {
            if (cursor.count != 0) {
                while (moveToNext()) {
                    moveToFirst()
                    val savedPassword = getString(2)

                    if (editTextPassword.isValidPassword(context!!) &&
                        validatePasswordFromDb(savedPassword)
                    ) {
                        password = editTextPassword.text.toString()
                        startActivity(Intent(context, MainActivity::class.java))
                        activity?.finish()
                    } else {
                        Toast.makeText(context, "Invalid Password", Toast.LENGTH_LONG).show()

                    }
                }
            } else {
                Toast.makeText(context, "Mobile Number not registered", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun checkExistingUserFromDb(): Cursor {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(
            BaseColumns._ID,
            UserContract.User.COLUMN_NAME_CONTACT,
            UserContract.User.COLUMN_NAME_PASSWORD
        )

        val selection = "${UserContract.User.COLUMN_NAME_CONTACT} = ?"
        val selectionArgs = arrayOf(mobile)
        val sortOrder = "${BaseColumns._ID} ASC"

        return db.query(
            UserContract.User.TABLE_NAME,
            projection,
            selection,
            selectionArgs,
            null,
            null,
            sortOrder
        )
    }


    private fun validatePasswordFromDb(password: String?): Boolean =
        (editTextPassword.text.toString() == password)

}
