package com.bounce.notemate.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bounce.notemate.data.repo.NotesRepo
import com.bounce.notemate.util.MyResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val notesRepo: NotesRepo) : ViewModel() {

    private val notesLiveData = MutableLiveData<MyResult>()

    init {
        fetchAllNotes()
    }

    fun fetchAllNotes() {
        viewModelScope.launch(Dispatchers.IO) {
            notesRepo.getAllNotes().collect {
                notesLiveData.postValue(MyResult.Success(it))
            }
        }
    }

    fun getNotesLiveData(): LiveData<MyResult> {
        return notesLiveData
    }

    fun deleteNoteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        notesRepo.deleteNoteById(id)
    }

}