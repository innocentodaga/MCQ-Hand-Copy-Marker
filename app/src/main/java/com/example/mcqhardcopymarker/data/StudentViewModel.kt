package com.example.mcqhardcopymarker.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentViewModel(application: Application) : AndroidViewModel(application) {

    val getAllStudents: LiveData<List<Student>>

    private val repo: StudentRepo

    init {
        val studentDao = UserDB.getDB(application).studentDao()
        repo = StudentRepo(studentDao)
        getAllStudents = repo.getAllStudents
    }

    fun insertStudent(student: Student) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.insertStudent(student)
        }
    }
}
