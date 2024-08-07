package com.bounce.notemate.presentation.read

import com.bounce.notemate.data.local.dao.NoteDao
import javax.inject.Inject

class ReadRepository @Inject constructor(private val noteDao: NoteDao) {

    fun getNoteById(id: Int) = noteDao.getSpecificNote(id)

}