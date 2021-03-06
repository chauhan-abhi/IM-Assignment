package com.example.demo_day1.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.demo_day1.data.db.room_contactsdb.ContactsDao
import com.example.demo_day1.data.db.room_contactsdb.AppDb
import com.example.demo_day1.data.remote.ApiInterface
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton
import com.example.demo_day1.utils.*
import io.reactivex.schedulers.Schedulers
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory


@Module
class AppModule(private var application: Application) {

    @Provides
    @Singleton
    fun provideContext(app: Application): Context = app.applicationContext

    @Provides
    @Singleton
    internal fun providesApplication(): Application = application

    @Provides
    @Singleton
    fun providesUtils(): AppUtils = AppUtils(application)

    @Provides
    internal fun providesSharedPreferences(applicationContext: Context): SharedPreferences =
        applicationContext.getSharedPreferences(
            PREF_NAME, PRIVATE_MODE
        )

    @Singleton
    @Provides
    internal fun providesApiService(retrofit: Retrofit) = retrofit.create(ApiInterface::class.java)

    @Singleton
    @Provides
    internal fun providesRetrofit() =
        Retrofit.Builder()
            .baseUrl(REMOTE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            //.client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun providesDb(app: Application): AppDb = Room
        .databaseBuilder(app, AppDb::class.java, "contacts.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    internal fun providesContactsDao(db: AppDb): ContactsDao = db.contactsDao()
}