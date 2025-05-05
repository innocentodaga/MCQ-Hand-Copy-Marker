package com.example.mcqhardcopymarker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "studentTable")
data class Student (
    val name: String,
    @PrimaryKey val studentId: String,
    val studentClass: String,
    val section: String
)