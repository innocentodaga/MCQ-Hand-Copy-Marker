package com.example.mcqhardcopymarker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RecognizedLineDao {
    @Insert
    suspend fun insertLine(line: RecognizedLine)

    @Query("SELECT * FROM recognized_lines")
    fun getAllLines(): Flow<List<RecognizedLine>>
}