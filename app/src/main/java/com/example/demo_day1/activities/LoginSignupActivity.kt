package com.example.demo_day1.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.demo_day1.R

class LoginSignupActivity : AppCompatActivity() {

    lateinit var navController: NavController
    private var PRIVATE_MODE = 0
    private val PREF_NAME = "com.example.demo_day1.PREFERENCE_FILE_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_signup)
        navController = Navigation.findNavController(this, R.id.onboarding_fragment)
        setUpNavigation()


    }

    private fun setUpNavigation() {
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        if (sharedPref.getBoolean(PREF_NAME, false)) {
            // open login
            navController.navigate(R.id.loginFragment)
        } else {
            //open register
            navController.navigate(R.id.thirdFragment)
        }
    }
}
