package com.example.mcqhardcopymarker.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UserViewModel(application: Application): AndroidViewModel(application) {

    private val readAllData: LiveData<List<User>>
    private val repository: UserRepo

    init {
        val userDao =  UserDB.getDB(application).userDao()

        repository = UserRepo(userDao)
        readAllData = repository.readAllData
    }

    fun addUser(user: User){
        viewModelScope.launch(Dispatchers.IO){
            repository.addUser(user)
        }
    }

    fun readEmailPassword(email: String, password: String): LiveData<List<User>>{
        return  repository.readEmailPassword(email, password)
    }

    fun readEmail(email: String): LiveData<List<User>>{
        return repository.readEmail(email)
    }

    fun getUserByEmail(email: String):LiveData<User>{
        return  repository.getUserByEmail(email)
    }

    fun updateUser(user: User){
        return repository.updateUser(user)
    }



}