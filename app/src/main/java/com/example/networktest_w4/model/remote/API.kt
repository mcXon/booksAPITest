package com.example.networktest_w4.model.remote

import com.example.networktest_w4.model.presentation.BookResponse
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    //receive endpoint as a String
    @GET("books/v1/volumes")

    fun getBookByTitle(@Query("q") bookTitle : String,
                       @Query("maxResults") max : Int = 5,
                       @Query("printType") type : String = "books") : Call<BookResponse>

    //To create static reference
    companion object{
        fun initRetrofit(): API{
            return Retrofit.Builder().baseUrl("https://www.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(API::class.java)
        }
    }
}

//1 Adding dependencies  - retrofit / converter [gson - moshi]
//2 Create API interface
//3 define http verbs(GET, POST, PUT, DELETE)
//4 Create retrofit object (using builder)
//5 Invoke the network call (enqueue, execute)