package com.example.demo_day1.data.remote

import retrofit2.http.GET
import com.example.demo_day1.data.remote.model.Contact
import io.reactivex.Observable

interface ApiInterface {

    @GET("5d355b5b2e0000df77a6b96e/")
    fun getContacts(): Observable<List<Contact>>

}