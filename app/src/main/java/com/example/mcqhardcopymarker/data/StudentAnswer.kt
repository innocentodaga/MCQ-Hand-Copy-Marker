package com.example.mcqhardcopymarker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_answers")
data class StudentAnswer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val studentId: String,
    val questionNumber: Int,
    val selectedOption: String
)
