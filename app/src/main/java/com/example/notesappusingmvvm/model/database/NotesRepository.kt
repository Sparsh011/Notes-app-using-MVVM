package com.example.notesappusingmvvm.model.database

import androidx.lifecycle.LiveData
import com.example.notesappusingmvvm.model.entity.Note

class NotesRepository(private val notesDao: NotesDao) {

    suspend fun insert(note: Note){
        notesDao.insertNoteToDatabase(note)
    }

    val allNotes: LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun deleteNote(note: Note){
        notesDao.deleteNote(note)
    }

    suspend fun updateNote(note: Note){
        notesDao.updateNote(note)
    }
}