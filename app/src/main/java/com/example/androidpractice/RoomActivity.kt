package com.example.androidpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewDebug
import androidx.room.ColumnInfo
import androidx.room.Entity

class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)
    }
}

@Entity
class UserProfile(

    @property

    @ColumnInfo(name = "last_name")
    val lastName: String,

    @ColumnInfo(name = "first_name")
    val firstName: String
)