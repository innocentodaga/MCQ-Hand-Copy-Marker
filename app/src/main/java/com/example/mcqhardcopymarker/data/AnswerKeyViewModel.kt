package com.example.mcqhardcopymarker.data


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AnswerKeyViewModel(private val repository: AnswerKeyRepo) : ViewModel() {

    fun insertAnswerKeys(answerKeys: List<AnswerKey>) {
        viewModelScope.launch {
            repository.insertAnswerKeys(answerKeys)
        }
    }

    // Insert an AnswerKeySet and return it with the generated id
    fun insertAnswerKeySet(answerKeySet: AnswerKeySet, callback: (AnswerKeySet) -> Unit) {
        viewModelScope.launch {
            val insertedAnswerKeySet = repository.insertAnswerKeySet(answerKeySet)
            callback(insertedAnswerKeySet)
        }
    }
}

class AnswerKeyViewModelFactory(private val repository: AnswerKeyRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AnswerKeyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AnswerKeyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}