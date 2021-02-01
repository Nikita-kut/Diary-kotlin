package com.diary.nikita.kut.screens.main

import androidx.lifecycle.ViewModel
import com.diary.nikita.kut.data.DataBase

class MainViewModel : ViewModel() {
    val taskLiveData = DataBase.instance?.taskDao()?.getAllLiveData()
}