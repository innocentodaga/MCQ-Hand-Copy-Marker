package com.example.mcqhardcopymarker.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recognized_lines")
data class RecognizedLine(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val textLine: String
)
