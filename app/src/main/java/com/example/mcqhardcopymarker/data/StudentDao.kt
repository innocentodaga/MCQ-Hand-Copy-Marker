package com.example.mcqhardcopymarker.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface StudentDao {
    @Insert
    suspend fun insertStudent(student: Student)

    @Query("SELECT * FROM studentTable ORDER BY studentId ASC")
    fun getAllStudents(): LiveData<List<Student>>
}