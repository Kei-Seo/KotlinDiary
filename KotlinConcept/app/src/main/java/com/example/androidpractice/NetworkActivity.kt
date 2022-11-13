package com.example.androidpractice

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


// NetworkOnMainThreadException
// - 네트워크 같은 경우 메인쓰레드에서 작업하면 사용자의 요청을 처리하지 못하므로 메인스레드에서 처리할 수 없다.

class NetworkActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_network)
        NetworkASyncTask().execute()
    }
}

class NetworkASyncTask() : AsyncTask<Any?, Any?, Any?>(){
    override fun doInBackground(vararg params: Any?): Any? {
        val urlString : String = "http://mellowcode.org/json/students/"
        val url : URL = URL(urlString)
        val connection: HttpURLConnection = url.openConnection() as HttpURLConnection

        connection.requestMethod = "GET"
        connection.setRequestProperty("Content-Type", "application/json")

        var buffer = ""
        if (connection.responseCode ==  HttpURLConnection.HTTP_OK){
            val reader = BufferedReader(
                InputStreamReader(
                    connection.inputStream,
                    "UTF-8"
                )
            )

            buffer = reader.readLine()
            Log.d("Text", buffer)
        }
        return null
    }
}