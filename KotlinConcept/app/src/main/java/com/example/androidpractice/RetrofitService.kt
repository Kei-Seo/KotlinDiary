package com.example.androidpractice

import retrofit2.Call
import retrofit2.http.*

class StudentFromServer(
    val id : Int, val name : String, val age : Int, val intro : String
){
    constructor(name: String, age: Int, intro: String) : this(0, name , age, intro)
}

class YoutubeItem(
    val id : Int, val title : String, val content: String, val video: String, val thumbnail: String
)


interface RetrofitService {

    @GET("json/students")
    fun getStudentList(): Call<ArrayList<StudentFromServer>>

    @POST("json/students/")
    fun createStudent(
        @Body params: HashMap<String, Any>
    ): Call<StudentFromServer>


    @POST("json/students/")
    fun easyCreateStudent(
        @Body student : StudentFromServer
    ): Call<StudentFromServer>

    @GET("youtube/list/")
    fun getYoutubeItemList(): Call<ArrayList<YoutubeItem>>

}