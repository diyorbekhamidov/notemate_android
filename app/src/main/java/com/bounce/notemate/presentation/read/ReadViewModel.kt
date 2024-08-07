package com.bounce.notemate.presentation.read

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bounce.notemate.data.local.entities.NoteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class ReadViewModel @Inject constructor(private val readRepository: ReadRepository) : ViewModel() {

    private val noteLiveData = MutableLiveData<NoteEntity>()

    fun fetchNote(id: Int) {

        viewModelScope.launch(Dispatchers.IO) {
            readRepository.getNoteById(id).collect {
                noteLiveData.postValue(it)
            }
        }
    }

    fun getNoteLiveData(): LiveData<NoteEntity> {
        return noteLiveData
    }
}