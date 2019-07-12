package com.example.demo_day1.fragments


import android.app.Activity
import android.content.SharedPreferences
import android.os.Bundle
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
import com.example.demo_day1.utils.*

/**
 * REGISTER FRAGMENT
 */
class ThirdFragment : Fragment() {

    lateinit var navController: NavController
    private val PREF_NAME = "com.example.demo_day1.PREFERENCE_FILE_KEY"
    private var fullName = ""
    private var email = ""
    private var mobile = ""
    private var password = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third, container, false)
        navController = Navigation.findNavController(activity as Activity, R.id.onboarding_fragment)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        next.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        if (validateFullName() && validateEmail() && validateMobile() && validatePassword()) {
            // save data in shared pref

            val sharedPref: SharedPreferences = activity!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)
            val editor = sharedPref.edit()
            editor.putBoolean(PREF_NAME, true)
            editor.putString(FULL_NAME_KEY, fullName )
            editor.putString(EMAIL_KEY, email)
            editor.putString(MOBILE_KEY, mobile)
            editor.putString(PASSWORD_KEY, password)
            editor.apply()
            val bundle = bundleOf(
                FULL_NAME_KEY to fullName,
                EMAIL_KEY to email,
                MOBILE_KEY to mobile,
                PASSWORD_KEY to password
            )
            // open login

            navController.navigate(R.id.loginFragment, bundle)

        }
    }

    private fun validateFullName(): Boolean {
        if (editTextName.text.toString().isNotBlank()) {
            fullName = editTextName.text.toString()
            return true
        }
        editTextName.error = "Please Enter Full Name"
        return false
    }

    private fun validateEmail(): Boolean {
        if (Patterns.EMAIL_ADDRESS.matcher(editTextEmail.text.toString()).matches()) {
            email = editTextEmail.text.toString()
            return true
        }
        editTextEmail.error = "Please enter valid email"
        return false

    }

    private fun validateMobile(): Boolean {
        val contact = editTextMobile.text.toString()
        if (Patterns.PHONE.matcher(contact).matches() && contact.length == 10) {
            mobile = editTextMobile.text.toString()
            return true
        }
        editTextMobile.error = "Please enter valid contact"
        return false

    }


    private fun validatePassword(): Boolean {
        if (editTextPassword.text.toString().length >4) {
            password = editTextPassword.text.toString()
            return true
        }
        editTextPassword.error = "Password should be atleast 4 characters"
        return false
    }
}


