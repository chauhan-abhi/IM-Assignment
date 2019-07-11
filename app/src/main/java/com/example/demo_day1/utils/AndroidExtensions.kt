package com.example.demo_day1.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.demo_day1.R
import org.json.JSONObject
import java.io.InputStream

fun ImageView.loadImg(imageUrl: String?) =
    Glide.with(context)
        .load(imageUrl)
        .placeholder(R.drawable.avatar)
        .into(this)

fun readJSONFromAsset(applicationContext: Context): JSONObject {
    val jsonfile: String = applicationContext.assets.open("ela.json").bufferedReader().use {it.readText()}

    return JSONObject(jsonfile)
}