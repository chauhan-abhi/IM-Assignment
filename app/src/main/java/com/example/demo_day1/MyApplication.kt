package com.example.demo_day1

import android.app.Application
import com.example.demo_day1.di.Injector

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Injector.init(this)
    }
}