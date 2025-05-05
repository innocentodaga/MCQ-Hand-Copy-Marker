package com.example.mcqhardcopymarker.data

class AnswerKeyRepo(
    private val answerKeyDao: AnswerKeyDao,
    private val answerKeySetDao: AnswerKeySetDao
)
{
    suspend fun insertAnswerKeys(answerKeys: List<AnswerKey>) {
        answerKeyDao.insertAnswerKeys(answerKeys)
    }

    suspend fun insertAnswerKeySet(answerKeySet: AnswerKeySet): AnswerKeySet {
        val insertedAnswerKeySetId = answerKeySetDao.insertAnswerKeySet(answerKeySet)
        return answerKeySet.copy(id = insertedAnswerKeySetId.toInt())
    }
}