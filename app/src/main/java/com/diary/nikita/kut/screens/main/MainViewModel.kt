package com.diary.nikita.kut.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.diary.nikita.kut.App
import com.diary.nikita.kut.model.Task

class MainViewModel : ViewModel() {
    val taskLiveData: LiveData<List<Task>> = App().getInstanceCustom().taskDao.getAllLiveData()
}