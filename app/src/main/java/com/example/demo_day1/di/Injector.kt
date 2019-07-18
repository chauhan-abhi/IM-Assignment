package com.example.demo_day1.di

import com.example.demo_day1.MyApplication

object Injector {
    lateinit var appComponent: AppComponent

    fun init(app: MyApplication) {
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(app))
            .build()
        appComponent.inject(app)
    }

}