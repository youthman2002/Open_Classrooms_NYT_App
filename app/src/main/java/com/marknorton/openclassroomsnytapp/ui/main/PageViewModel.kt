package com.marknorton.openclassroomsnytapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class PageViewModel : ViewModel() {

    private val _index = MutableLiveData<Int>()
    val text: LiveData<String> = Transformations.map(_index) {
        when(it){
            1 -> "Hello world from section: $it"
            2 -> "Howdy"
            3 -> ". . . And 3"
            4 -> "4 Maybe"
            else->"Unknown"
        }
    }

    fun setIndex(index: Int) {
        _index.value = index
    }
}