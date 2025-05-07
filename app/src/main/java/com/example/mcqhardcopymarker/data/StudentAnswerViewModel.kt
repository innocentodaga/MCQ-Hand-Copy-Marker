package com.example.mcqhardcopymarker.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StudentAnswerViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: StudentAnswerRepository

    init {
        val dao = UserDB.getDB(application).studentAnswerDao()
        repository = StudentAnswerRepository(dao)
    }

    fun insertAnswer(answer: StudentAnswer) = viewModelScope.launch {
        repository.insertAnswer(answer)
    }
}
