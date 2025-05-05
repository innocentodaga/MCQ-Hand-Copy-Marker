package com.example.mcqhardcopymarker.data

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "answer_keys",
    foreignKeys = [ForeignKey(
        entity = AnswerKeySet::class,
        parentColumns = ["id"],
        childColumns = ["answerKeySetId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class AnswerKey(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    var answerKeySetId: Int, // Foreign Key
    val question: String,
    val correctAnswer: String,
    val points: Int

)