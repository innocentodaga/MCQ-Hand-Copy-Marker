package com.example.mcqhardcopymarker.data

import androidx.lifecycle.LiveData

class StudentRepo(private val studentDao: StudentDao) {

    val getAllStudents: LiveData<List<Student>> = studentDao.getAllStudents()

    suspend fun insertStudent(student: Student) {
        studentDao.insertStudent(student)
    }


}