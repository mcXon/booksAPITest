package com.example.networktest_w4

import android.content.ContentValues.TAG
import android.os.AsyncTask
import android.os.AsyncTask.execute
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.TextView
import com.example.networktest_w4.model.presentation.BookResponse
import com.example.networktest_w4.model.remote.API
import com.example.networktest_w4.model.remote.execBookSearch
import com.example.networktest_w4.model.remote.isDeviceConnected
import com.example.networktest_w4.view.BookListFragment
import com.example.networktest_w4.view.SearchBookFragment
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if(isDeviceConnected()){
            supportFragmentManager.beginTransaction().replace(R.id.container_search, SearchBookFragment()).addToBackStack("FirstFrag").commit()
//            executeRetrofit()
            //executeNetworkCall()
            /**
             * To be able to run the method in the Main thread is necessary to add the ThreadPolicy
             * In this case seems I am no longer receiving the error, but is locked the execution
             *
            val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
            StrictMode.setThreadPolicy(policy);
            execBookSearch("Pride/Prejudice")
             */
        }
        else
            showError()
    }

    private fun showError() {
        Snackbar.make(findViewById(R.id.container_display), "No network found, retry", Snackbar.LENGTH_INDEFINITE).setAction("Retry"){
            //On action listener, the last argument is the Listener so it can be placed as it is (Also interfaces can be executed as this)
            Log.d(TAG, "showError: Retried!")
        }.show()
    }

//    private fun executeNetworkCall() {
//        //Asynctask
//        BookNetwork(findViewById(R.id.container_display)).execute()
//    }

    fun executeRetrofit(bookTitle : String, bookType : String, maxRes : Int){
        API.initRetrofit().getBookByTitle(bookTitle,maxRes,bookType).enqueue(object : Callback<BookResponse>{
            override fun onResponse(call: Call<BookResponse>, response: Response<BookResponse>) {
                if (response.isSuccessful){
//                    findViewById<TextView>(R.id.container_display).text = response.body().toString()
                    inflateDisplayFragment(response.body())
                }else{
                    showError()
                }
            }

            override fun onFailure(call: Call<BookResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    private fun inflateDisplayFragment(dataset : BookResponse?){
        dataset?.let {
            supportFragmentManager.beginTransaction().replace(
                R.id.container_display, BookListFragment.newInstance(it))
                .commit()
        }
    }

    class BookNetwork(private val display : TextView) : AsyncTask<String, Void, BookResponse?>(){
        /**
         * Happen in the worker thread
         * new Thread( new Runnable
         */
        override fun doInBackground(vararg p0: String?): BookResponse {
            return execBookSearch("Pride/Prejudice")
        }

        /**
         * Happens in the main thread
         */
        override fun onPostExecute(result: BookResponse?) {
            super.onPostExecute(result)
            display.text = result.toString() ?: ""
        }
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount <= 1){
            super.onBackPressed()
        }else{
            supportFragmentManager.popBackStack()
        }
    }
}