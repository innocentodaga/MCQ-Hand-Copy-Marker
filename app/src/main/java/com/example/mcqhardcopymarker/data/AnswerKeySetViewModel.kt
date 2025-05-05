package com.example.mcqhardcopymarker.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AnswerKeySetViewModel(private val repository: AnswerKeySetRepository) : ViewModel() {

    val allAnswerKeySets: LiveData<List<AnswerKeySet>> = repository.allAnswerKeySets

    fun insert(answerKeySet: AnswerKeySet, callback: (Long) -> Unit) {
        viewModelScope.launch {
            // Insert the AnswerKeySet and return its ID
            val id = repository.insert(answerKeySet)
            callback(id)
        }
    }

    fun update(answerKeySet: AnswerKeySet) {
        viewModelScope.launch {
            repository.update(answerKeySet)
        }
    }

    fun delete(answerKeySet: AnswerKeySet) {
        viewModelScope.launch {
            repository.delete(answerKeySet)
        }
    }

    fun getAnswerKeySetById(id: Int, callback: (AnswerKeySet?) -> Unit) {
        viewModelScope.launch {
            val result = repository.getAnswerKeySetById(id)
            callback(result)
        }
    }

    // Inner ViewModel Factory
    class AnswerKeySetViewModelFactory(private val repository: AnswerKeySetRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AnswerKeySetViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AnswerKeySetViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
