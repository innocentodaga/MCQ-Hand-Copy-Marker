package com.example.mcqhardcopymarker.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mcqhardcopymarker.AnswerKeys

@Dao
interface AnswerKeySetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAnswerKeySet(answerKeySet: AnswerKeySet): Long // Returns the inserted ID

    @Update
    suspend fun updateAnswerKeySet(answerKeySet: AnswerKeySet)

    @Delete
    suspend fun deleteAnswerKeySet(answerKeySet: AnswerKeySet)

    @Query("SELECT * FROM answer_key_set ORDER BY id DESC")
    fun getAllAnswerKeySets(): LiveData<List<AnswerKeySet>>

    @Query("SELECT * FROM answer_key_set WHERE id = :id LIMIT 1")
    suspend fun getAnswerKeySetById(id: Int): AnswerKeySet?
}
