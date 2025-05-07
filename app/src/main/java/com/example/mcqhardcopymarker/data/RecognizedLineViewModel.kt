package com.example.mcqhardcopymarker.data

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class RecognizedLineViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecognizedLineRepository
    val allLines: LiveData<List<RecognizedLine>>

    init {
        val dao = UserDB.getDB(application).recognizedLineDao()
        repository = RecognizedLineRepository(dao)
        allLines = repository.allLines.asLiveData()
    }

    fun insertLine(text: String) = viewModelScope.launch {
        repository.insertLine(RecognizedLine(textLine = text))
    }
}
