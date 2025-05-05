package com.example.mcqhardcopymarker.data

import androidx.lifecycle.LiveData

class AnswerKeySetRepository(private val dao: AnswerKeySetDao) {

    val allAnswerKeySets: LiveData<List<AnswerKeySet>> = dao.getAllAnswerKeySets()

    suspend fun insert(answerKeySet: AnswerKeySet): Long {
        return dao.insertAnswerKeySet(answerKeySet)
    }

    suspend fun update(answerKeySet: AnswerKeySet) {
        dao.updateAnswerKeySet(answerKeySet)
    }

    suspend fun delete(answerKeySet: AnswerKeySet) {
        dao.deleteAnswerKeySet(answerKeySet)
    }

    suspend fun getAnswerKeySetById(id: Int): AnswerKeySet? {
        return dao.getAnswerKeySetById(id)
    }
}
