package com.example.demo_day1.activities

import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.widget.Toolbar
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.demo_day1.R
import com.example.demo_day1.fragments.FourthFragment
import com.example.demo_day1.interfaces.UpdateProfileInterface
import com.example.demo_day1.utils.*
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, UpdateProfileInterface {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var navigationView: NavigationView
    private lateinit var bundle: Bundle


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigation()
        bundle = getLoggedInUser()
    }


    private fun setUpNavigation() {
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigationView)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)
        NavigationUI.setupWithNavController(navigationView, navController)

        navigationView.setNavigationItemSelectedListener(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(
                this,
                R.id.nav_host_fragment
            ),
            drawerLayout
        )
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        menuItem.isChecked = true
        drawerLayout.closeDrawers()

        when (menuItem.itemId) {
            R.id.first -> {
                navController.navigate(R.id.firstFragment, bundle)
            }
            R.id.second -> navController.navigate(R.id.secondFragment)
            //R.id.third -> navController.navigate(R.id.thirdFragment)
            R.id.fourth -> {
                navController.navigate(R.id.fourthFragment, bundle)
            }
            else -> navController.navigate(R.id.homeFragment)

        }
        return true
    }

    override fun onProfileUpdated() {
        bundle = getLoggedInUser()
        supportActionBar!!.show()
    }

    private fun getLoggedInUser(): Bundle {
        val sharedPref: SharedPreferences = getSharedPreferences(PREF_NAME, PRIVATE_MODE)
        val fullName = sharedPref.getString(FULL_NAME_KEY, "")
        val email = sharedPref.getString(EMAIL_KEY, "")
        val mobile = sharedPref.getString(MOBILE_KEY, "")
        val password = sharedPref.getString(PASSWORD_KEY, "")
        val profilePic = sharedPref.getString(PROFILE_PIC_URI, "")
        return bundleOf(
            FULL_NAME_KEY to fullName,
            EMAIL_KEY to email,
            MOBILE_KEY to mobile,
            PASSWORD_KEY to password,
            PROFILE_PIC_URI to profilePic
        )
    }

    override fun onAttachFragment(fragment: Fragment) {
        if (fragment is FourthFragment) {
            fragment.setUpdateProfileListener(this)
        }
        super.onAttachFragment(fragment)
    }
}
