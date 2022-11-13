package com.example.androidpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewDebug
import android.widget.TextView
import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

class RoomActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room)

        // Room 은 기본적으로 메인 쓰레드에서 돌지 않는다
        // 이유는, 데이터베이스 작업을 휴대폰이 하는 동안 사용자가 기다려야하기 때문에
        // 해결책은, 쓰레드를 이용하여 async 을 해야함
        val database = Room.databaseBuilder(
            applicationContext,
            UserDatabase::class.java,
            "user-database"
        ).allowMainThreadQueries().build()

        findViewById<TextView>(R.id.save).setOnClickListener{

            val userProfile = UserProfile("홍", "길동")
            database.userProfileDao().insert(userProfile)
        }

        findViewById<TextView>(R.id.LOAD).setOnClickListener{
            val userProfile = database.userProfileDao().getAll()
            userProfile.forEach {
                Log.d("testt", "" +it.id +it.firstName)
            }
        }

        findViewById<TextView>(R.id.delete).setOnClickListener{

            database.userProfileDao().delete(1)

        }

    }
}


@Database(entities = [UserProfile::class], version = 1)
abstract class UserDatabase : RoomDatabase(){
    //database_one
    //  UserProfile
    //  UserNumber
    //database_two
    //  CustomerInfo

    abstract fun userProfileDao() : UserProfileDao

}


@Entity
class UserProfile(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "last_name")
    val lastName: String,
    @ColumnInfo(name = "first_name")
    val firstName: String
){
    constructor(lastName: String, firstName: String) : this(0, lastName, firstName)
}


@Dao
interface UserProfileDao{
    // CRUD
    // Query -> Database 조작

    @Insert(onConflict = REPLACE)
    fun insert(userProfile: UserProfile)
    //SELECT last_name FROM youtube WHERE id = 1;

    @Query("DELETE FROM userprofile WHERE id = :userId")
    fun delete(userId: Int)

    @Query("SELECT * FROM userprofile")
    fun getAll(): List<UserProfile>

}