package com.example.demo_day1.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.demo_day1.R
import org.json.JSONObject
import java.io.InputStream
import android.R.attr.duration
import com.google.android.material.snackbar.Snackbar
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat.getSystemService




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

fun ImageView.loadImg(imageUrl: String?) =
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.avatar)
        .into(this)

fun readJSONFromAsset(applicationContext: Context): JSONObject {
    val jsonfile: String = applicationContext.assets.open("ela.json").bufferedReader().use { it.readText() }

    return JSONObject(jsonfile)
}