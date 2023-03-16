package com.example.todonoteapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todonoteapp.data.model.NoteData
import com.example.todonoteapp.domain.usecase.UseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val useCase: UseCase,
) : ViewModel() {

    private val _getAllData = MutableLiveData<List<NoteData>>()
    val getAllData: LiveData<List<NoteData>> = _getAllData


    fun getAllData() {
        viewModelScope.launch {
            useCase.getAllNoteUseCase.invoke().collect {
                _getAllData.postValue(it)
            }
        }
    }


    fun insertData(noteData: NoteData) {
        viewModelScope.launch {
            useCase.insertNoteUseCase.invoke(noteData)
        }
    }

    fun updateData(noteData: NoteData) {
        viewModelScope.launch {
            useCase.updateNoteUseCase.invoke(noteData)
        }
    }

    fun deleteData(noteData: NoteData) {
        viewModelScope.launch {
            useCase.deleteNoteUseCase.invoke(noteData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            useCase.deleteAllNoteUseCase.invoke()
        }
    }

}