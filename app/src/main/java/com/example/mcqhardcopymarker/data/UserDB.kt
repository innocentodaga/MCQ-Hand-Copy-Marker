package com.example.mcqhardcopymarker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDB : RoomDatabase(){

    abstract fun userDao(): UserDao

    companion object{

        @Volatile
        private  var INSTANCE: UserDB? = null

        fun getDB(context: Context): UserDB{
            val tempInstance = INSTANCE
            if (tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDB::class.java,
                    "userdb"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}