package com.example.mcqhardcopymarker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answer_key_set")
data class AnswerKeySet(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val subject: String
)
