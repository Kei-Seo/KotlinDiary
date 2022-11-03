package com.example.androidpractice

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class ListViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_view)

        // 데이터 준비
        val carList = mutableListOf<Car>()
        for (i in 0..100) {
            carList.add(Car(""+i+"번째 자동차", ""+i+"번ㅒ 엔진"))
        }

        //어답터 준비
        val adapter = ListViewAdapter(
            carList,
            LayoutInflater.from(this),
            this
        )

        val listView = findViewById<ListView>(R.id.ListView)
        listView.adapter = adapter

    }

}

class ListViewAdapter(
    val carList: MutableList<Car>,
    val layoutInflater: LayoutInflater,
    val context: Context
):BaseAdapter(){

    override fun getCount(): Int {
        //전체 데이터의 크기를 리턴
        return carList.size
    }

    override fun getItem(position: Int): Any {
        // 전제 데이터중에 해당데이터 포지션 데이터를 리턴
        return carList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        // 해당번째 리스트를 반환

        val view: View
        val holder: ViewHolder

        if (convertView == null){
            view = layoutInflater.inflate(R.layout.car_item, null)
            holder = ViewHolder()
            holder.carImage = view.findViewById(R.id.carImage)
            holder.nthCar = view.findViewById(R.id.nthCar)
            holder.nthEngine = view.findViewById(R.id.nthEngine)

            view.tag = holder
        } else {
            holder = convertView.tag as ViewHolder
            view = convertView
        }

        var car = carList.get(position)
        holder.carImage?.setImageDrawable(
            context.resources.getDrawable(com.google.android.material.R.drawable.abc_cab_background_internal_bg, context.theme)
        )

        holder.nthCar?.text = car.nthCar
        holder.nthEngine?.text = car.nthEngine

        return view


//
//        val view = layoutInflater.inflate(R.layout.car_item, null)
//        val carImage = view.findViewById<ImageView>(R.id.carImage)
//        val nthCar = view.findViewById<TextView>(R.id.nthCar)
//        val nthEngine = view.findViewById<TextView>(R.id.nthEngine)
//
//        val car = carList.reversed().get(position)
//        carImage.setImageDrawable(
//            context.resources.getDrawable(com.google.android.material.R.drawable.abc_ab_share_pack_mtrl_alpha, context.theme)
//        )
//        nthCar.text = car.nthCar
//        nthEngine.text = car.nthEngine

    }
}

class ViewHolder {
    var carImage : ImageView? = null
    var nthCar : TextView? = null
    var nthEngine : TextView? = null

}

class Car (val nthCar: String, val nthEngine: String)