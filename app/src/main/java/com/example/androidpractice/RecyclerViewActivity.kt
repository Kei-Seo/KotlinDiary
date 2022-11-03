package com.example.androidpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val carList = mutableListOf<Car>()

        for(i in 0..100){
            carList.add(Car(""+i+"번째 자동차", ""+i+"번쨰 엔진"))
        }
    }
}

class RecyclerViewAdapter(
    var carList: MutableList<Car>,
    var inflater: LayoutInflater
) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(){
    // 어떤 뷰 홀더를 사용할 지 상속 받을때 말해줘야함
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //inner class
        val carImage: ImageView
        val nthCar : TextView
        val nthEngine: TextView
         init {
             carImage = itemView.findViewById(R.id.carImage)
             nthCar = itemView.findViewById(R.id.nthCar)
             nthEngine = itemView.findViewById(R.id.nthEngine)
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // 아이텝 뷰를 리턴
        val view = inflater.inflate(R.layout.car_item, parent, false)
        return RecyclerViewAdapter.ViewHolder(view
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.nthCar.text = carList.get(position).nthCar
        holder.nthEngine.text = carList.get(position).nthEngine

    }

    override fun getItemCount(): Int {
        return  carList.size
    }

}