package com.example.networktest_w4.model.remote

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.util.Log
import androidx.fragment.app.FragmentActivity
import com.example.networktest_w4.model.presentation.BookItem
import com.example.networktest_w4.model.presentation.BookResponse
import com.example.networktest_w4.model.presentation.VolumeItem
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


private const val TAG = "NETWORK"

/**
 * Extension funcion "adding" is connected
 * functionality to FragmentActivity class
 * syntax for extension:
 * fun [TARGET].funcName(Args : any): ReturnType { Body }
 */
fun FragmentActivity.isDeviceConnected(): Boolean {
    val manager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo: NetworkInfo? = manager.activeNetworkInfo
    return networkInfo?.isConnected ?: false
}

/**
 * books/v1/volumes?q=pride+prejudice&maxResults=5&printType=books
 */
fun execBookSearch(bookTitle: String) : BookResponse{
    //CreateURI
    val BASE_URL = "https://www.googleapis.com/"
    val ENDPOINT = "books/v1/volumes"
    val Q_ARG = "q"
    val MAX_RES_ARG = "maxResults"
    val PRINT_TYPE_ARG = "printType"
    val bookURI = Uri.parse("$BASE_URL$ENDPOINT?").buildUpon()
        .appendQueryParameter(Q_ARG, bookTitle)
        .appendQueryParameter(MAX_RES_ARG, "5")
        .appendQueryParameter(PRINT_TYPE_ARG, "books").build()

    val url = URL(bookURI.toString())

    val httpURLConnection = url.openConnection() as HttpURLConnection
    httpURLConnection.connectTimeout = 15000
    httpURLConnection.readTimeout = 10000
    httpURLConnection.requestMethod = "GET"
    httpURLConnection.doInput = true

    httpURLConnection.connect()

    val bookIS = httpURLConnection.inputStream
    val bookResponseCode = httpURLConnection.responseCode

    val stringRepresentation = bookIS.convert()

    Log.d(TAG, "execBookSearch: ${stringRepresentation} $bookResponseCode")

    return convertToPresentationData(stringRepresentation)
}

/**
 * Extension function that retrieves the String of an InputStream
 * https://stackoverflow.com/questions/39500045/in-kotlin-how-do-i-read-the-entire-contents-of-an-inputstream-into-a-string
 * USE close the input after the lambda expression **
 *
 * it - anonymous var for the bufferedReader
 * .readText() method to retrieve the content of the bufferedReader as a String
 */
fun InputStream.convert(): String {
    return this.bufferedReader().use {
        it.readText()
    }
}

private fun convertToPresentationData(deserialized: String): BookResponse{
    val json: JSONObject = JSONObject(deserialized)
    val itemArray= json.getJSONArray("items")

    val listOfBooks = mutableListOf<BookItem>()
    for (index in 0 until itemArray.length()){
        val bookItemsJson = itemArray.getJSONObject(index)
        val volInfoJSON = bookItemsJson.getJSONObject("volumeInfo")

        val title = volInfoJSON.getString("title")
        //val description = volInfoJSON.getString("description")
        val authors = volInfoJSON.getJSONArray("authors")

        val authorsList = mutableListOf<String>()
        for (i in 0 until authors.length()){
            authorsList.add(authors.getString(i))
        }

        val volumeItem =
            //VolumeItem(title, emptyList())
            VolumeItem(title, authorsList)

        val bookItem = BookItem(volumeItem)

        listOfBooks.add(bookItem)
    }

    return BookResponse(listOfBooks)
}
