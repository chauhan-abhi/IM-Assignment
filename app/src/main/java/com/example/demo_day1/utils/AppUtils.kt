package com.example.demo_day1.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

class AppUtils @Inject constructor(private val context: Context){

    fun isConnectedToInternet(): Boolean {
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
}