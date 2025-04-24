package com.example.mcqhardcopymarker.data

import androidx.room.Entity
import androidx.room.PrimaryKey


// User's Table
@Entity(tableName = "usersTable")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val institution: String
)

// Student's Table
@Entity(tableName = "studentsTable")
data class Students(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val studentNO: String,
    val studentName: String,
)

// Answer's Table
@Entity(tableName = "answerKeys")
data class AnswerKeys(
    @PrimaryKey(autoGenerate = true)
    val int: Int,
)