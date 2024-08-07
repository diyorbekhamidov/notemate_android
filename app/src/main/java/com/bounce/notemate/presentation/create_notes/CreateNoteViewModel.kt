package com.akshatbhuhagal.notemate.presentation.create_notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bounce.notemate.data.local.entities.NoteEntity
import com.bounce.notemate.data.repo.NotesRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateNoteViewModel @Inject constructor(private val notesRepo: NotesRepo) : ViewModel() {

    private val noteLiveData = MutableLiveData<NoteEntity>()

    fun getNoteById(id: Int) = viewModelScope.launch(Dispatchers.IO) {
        notesRepo.getNote(id).collect {
            noteLiveData.postValue(it)
        }
    }

    fun updateNote(noteEntity: NoteEntity) = viewModelScope.launch(Dispatchers.IO) {
        notesRepo.updateNotes(noteEntity)
    }

    fun getNoteLiveData() = noteLiveData

}