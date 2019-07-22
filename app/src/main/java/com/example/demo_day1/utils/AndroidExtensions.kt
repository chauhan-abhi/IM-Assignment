package com.example.demo_day1.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.demo_day1.R
import org.json.JSONObject
import com.google.android.material.snackbar.Snackbar
import android.app.Activity
import android.content.ContextWrapper
import android.content.res.Resources
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.util.Patterns
import android.view.Window
import androidx.appcompat.app.AppCompatActivity


fun View.getParentActivity(): AppCompatActivity? {
    var context = this.context
    while (context is ContextWrapper) {
        if (context is AppCompatActivity) {
            return context
        }
        context = context.baseContext
    }
    return null
}

fun isNetworkStatusAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
    connectivityManager?.let { it ->
        if (android.os.Build.VERSION.SDK_INT > 23) {
            it.getNetworkCapabilities(it.activeNetwork)?.let {
                if (it.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                    return true
                }

            }
        } else {
            it.activeNetworkInfo?.let {
                return it.isConnectedOrConnecting
            }

        }
    }
    return false
}

fun showSnackBar(activity: Activity, message: String) {
    val rootView = activity.window.decorView.findViewById<View>(android.R.id.content)
    Snackbar.make(rootView, message, Snackbar.LENGTH_LONG).show()
}


fun Activity.hideKeyBoard() {
    val imm = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    val view = this.currentFocus
    imm.hideSoftInputFromWindow(view!!.windowToken, 0)
}

fun hideStatusBar(window: Window) {
    // Hide Status Bar
    val decorView = window.decorView
    // Hide Status Bar.
    val uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN
    decorView.systemUiVisibility = uiOptions

}

fun showStatusBar(window: Window) {
    val decorView = window.decorView
    // Show Status Bar.
    val uiOptions = View.SYSTEM_UI_FLAG_VISIBLE
    decorView.systemUiVisibility = uiOptions
}

fun ImageView.loadImg(imageUrl: String?) =
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.avatar)
        .into(this)


fun EditText.isValidFullName(context: Context): Boolean {
    if (this.text.toString().length >= 4) {
        return true
    }
    this.error = context.getString(R.string.name_error)
    return false
}

fun EditText.isValidEmail(context: Context): Boolean {
    if (Patterns.EMAIL_ADDRESS.matcher(this.text.toString()).matches()) return true
    this.error = context.getString(R.string.email_error)
    return false
}

fun EditText.isValidContact(context: Context): Boolean {
    if (Patterns.PHONE.matcher(this.text.toString()).matches()
        && this.text.toString().length == 10
    ) return true
    this.error = context.getString(R.string.contact_error)
    return false

}

fun EditText.isValidPassword(context: Context): Boolean {
    if (this.text.toString().length > 4) return true
    this.error = context.getString(R.string.password_error)
    return false
}

fun readJSONFromAsset(applicationContext: Context): JSONObject {
    val jsonfile: String = applicationContext.assets.open(
        "ela.json"
    ).bufferedReader().use { it.readText() }

    return JSONObject(jsonfile)
}