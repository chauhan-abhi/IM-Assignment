package com.example.demo_day1.fragments


import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.demo_day1.R
import com.example.demo_day1.activities.MainActivity
import com.example.demo_day1.utils.MOBILE_KEY
import com.example.demo_day1.utils.PASSWORD_KEY
import com.example.demo_day1.utils.PREF_NAME
import com.example.demo_day1.utils.PRIVATE_MODE
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_login, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        login_next.setOnClickListener {
            validateLogin()
        }
    }

    private fun validateLogin() {
        val sharedPref: SharedPreferences = activity!!.getSharedPreferences(PREF_NAME, PRIVATE_MODE)




        if(validateMobile(sharedPref.getString(MOBILE_KEY, ""))
            && validatePassword(sharedPref.getString(PASSWORD_KEY, ""))) {
            // proceed to HOME ACTIVITY
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
            activity?.finish()
        }
    }


    private fun validateMobile(mobile: String?): Boolean {

        if(editTextMobile.text.toString() == mobile) {
            return true
        }
        editTextMobile.error = "Mobile number not registered"
        return false
    }


    private fun validatePassword(password: String?): Boolean {
        if(editTextPassword.text.toString() == password) {
            return true
        }
        editTextPassword.error = "Password Incorrect"
        return false
    }


}
