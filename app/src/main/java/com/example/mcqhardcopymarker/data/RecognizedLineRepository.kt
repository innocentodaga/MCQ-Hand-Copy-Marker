package com.example.mcqhardcopymarker.data

import kotlinx.coroutines.flow.Flow

class RecognizedLineRepository(private val dao: RecognizedLineDao) {

    val allLines: Flow<List<RecognizedLine>> = dao.getAllLines()

    suspend fun insertLine(line: RecognizedLine) {
        dao.insertLine(line)
    }
}