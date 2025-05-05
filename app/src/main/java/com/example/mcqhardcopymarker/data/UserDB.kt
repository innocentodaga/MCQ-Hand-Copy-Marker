package com.example.mcqhardcopymarker.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class, Student::class, AnswerKey::class, AnswerKeySet::class], version = 4, exportSchema = false)
abstract class UserDB : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun studentDao(): StudentDao
    abstract fun answerKeyDao(): AnswerKeyDao
    abstract fun answerKeySetDao(): AnswerKeySetDao

    companion object {
        @Volatile
        private var INSTANCE: UserDB? = null

        fun getDB(context: Context): UserDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDB::class.java,
                    "userdb"
                )
                    // Add fallback to destructive migration if you're still developing
                    // REMOVE THIS IN PRODUCTION
                    .fallbackToDestructiveMigration(false)
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
