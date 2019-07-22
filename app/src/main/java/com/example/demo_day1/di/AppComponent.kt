package com.example.demo_day1.di

import com.example.demo_day1.MyApplication
import com.example.demo_day1.ui.contactList.ContactListFragment
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: MyApplication)
    fun inject(contactsListFragment: ContactListFragment)

    @Component.Builder
    interface Builder {
        fun appModule(appModule: AppModule): Builder

        fun build(): AppComponent
    }

}