package com.example.demo_day1.di

import com.example.demo_day1.MyApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: MyApplication)

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

}