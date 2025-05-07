package com.example.mcqhardcopymarker.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentAnswerDao {
    @Insert
    suspend fun insertAnswer(answer: StudentAnswer)

    @Query("SELECT * FROM student_answers WHERE studentId = :studentId")
    suspend fun getAnswersForStudent(studentId: String): List<StudentAnswer>
}
