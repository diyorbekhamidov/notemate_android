package com.bounce.notemate.util

import com.bounce.notemate.data.local.entities.NoteEntity

sealed class MyResult {

    object Loading : MyResult()
    data class Success(val value: List<NoteEntity>) : MyResult()
    data class Error(val value: Throwable) : MyResult()
}