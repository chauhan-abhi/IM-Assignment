package com.example.demo_day1.data.remote

import retrofit2.http.GET
import com.example.demo_day1.data.remote.model.Contact
import io.reactivex.Observable

interface ApiInterface {

    @GET("/")
    fun getContacts(): Observable<List<Contact>>

}