package com.example.androidpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RetrofitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_retrofit)

        // 서버 -> 사람이 읽을 수 없는 데이터터 -> JSON -> Gson
        // GSON -> 읽을 수 없는
       val retrofit = Retrofit.Builder()
            .baseUrl("http://mellowcode.org/")
            .addConverterFactory(GsonConverterFactory.create()) // 서버가 보낸 정보는 알아볼 수 없으므로 JSON 으로 바꿔줘야함
            .build()


        val retrofitService = retrofit.create(RetrofitService::class.java)

        // 줄을 세위놓고 값을 가져오겠다
        retrofitService.getStudentList().enqueue(object : Callback<ArrayList<StudentFromServer>>{

            override fun onResponse(
                call: Call<ArrayList<StudentFromServer>>,
                response: Response<ArrayList<StudentFromServer>>
            ) {
                if (response.isSuccessful){
                    val studentList = response.body()
                    studentList!!.forEach {
                        Log.d("testt", ""+it.name)
                    }
                }
            }


            override fun onFailure(call: Call<ArrayList<StudentFromServer>>, t: Throwable) {
                Log.d("testt", "fail")
            }
        })



    }
}