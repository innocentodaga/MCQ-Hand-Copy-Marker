package com.example.mcqhardcopymarker.data

class StudentAnswerRepository(private val dao: StudentAnswerDao) {
    suspend fun insertAnswer(answer: StudentAnswer) {
        dao.insertAnswer(answer)
    }

    suspend fun getAnswers(studentId: String): List<StudentAnswer> {
        return dao.getAnswersForStudent(studentId)
    }
}
