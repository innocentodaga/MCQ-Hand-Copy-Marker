package com.example.mcqhardcopymarker.data

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface AnswerKeyDao {

    @Insert
    suspend fun insertAnswerKeys(answerKeys: List<AnswerKey>)
}
