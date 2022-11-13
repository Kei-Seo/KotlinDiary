package com.example.androidpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

                    findViewById<RecyclerView>(R.id.studentRecyclerView).apply {
                        this.adapter = StudentListRecyclerViewAdapter(
                            studentList!!,
                            LayoutInflater.from(this@RetrofitActivity)
                        )
                        this.layoutManager = LinearLayoutManager(this@RetrofitActivity)
                    }
                }
            }


            override fun onFailure(call: Call<ArrayList<StudentFromServer>>, t: Throwable) {
                Log.d("testt", "fail")
            }
        })

        findViewById<TextView>(R.id.createStudent).setOnClickListener{
            val student = HashMap<String,Any>()
            student.put("name", "홍석")
            student.put("intro", "홍석")
            student.put("age", 100)
            retrofitService.createStudent(student).enqueue(object : Callback<StudentFromServer>{

                override fun onResponse(
                    call: Call<StudentFromServer>,
                    response: Response<StudentFromServer>
                ) {
                    val student = response.body()
                    Log.d("testt", "등록한 학생"+student!!.name.toString())
                }

                override fun onFailure(call: Call<StudentFromServer>, t: Throwable) {
                    Log.d("testt", "요청 실패")
                }
            })
        }
        findViewById<TextView>(R.id.easyCreateStudent).setOnClickListener{
            val student = StudentFromServer(name = "서울", age=200, intro = "welcome to seoul")
            retrofitService.easyCreateStudent(student).enqueue(object : Callback<StudentFromServer> {
                override fun onResponse(
                    call: Call<StudentFromServer>,
                    response: Response<StudentFromServer>
                ) {
                    val student = response.body()
                    Log.d("testt", "등록한 학생"+student!!.name.toString())
                }

                override fun onFailure(call: Call<StudentFromServer>, t: Throwable) {
                    Log.d("testt", "요청 실패")
                }
            })
        }


    }
}


class StudentListRecyclerViewAdapter(
    var studentList : ArrayList<StudentFromServer>,
    var inflater: LayoutInflater

):RecyclerView.Adapter<StudentListRecyclerViewAdapter.ViewHolder>(){

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val studentName : TextView
        val studentAge : TextView
        val studentIntro : TextView

        init {
            studentName = itemView.findViewById(R.id.student_name)
            studentAge = itemView.findViewById(R.id.student_age)
            studentIntro = itemView.findViewById(R.id.student_intro)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.student_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.studentName.text = studentList.get(position).name
        holder.studentIntro.text = studentList.get(position).intro
        holder.studentAge.text = studentList.get(position).age.toString()

    }

    override fun getItemCount(): Int {
        return studentList.size
    }
}