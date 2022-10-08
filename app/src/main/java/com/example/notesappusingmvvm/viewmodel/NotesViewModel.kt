package com.example.notesappusingmvvm.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.notesappusingmvvm.model.database.NotesDao
import com.example.notesappusingmvvm.model.database.NotesDatabase
import com.example.notesappusingmvvm.model.database.NotesRepository
import com.example.notesappusingmvvm.model.entity.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application) : AndroidViewModel(application) {
     val allNotes : LiveData<List<Note>>
     val repository : NotesRepository

    init {
        val dao = NotesDatabase.getDatabase(application).getNotesDao()
        repository = NotesRepository(dao)
        allNotes = repository.allNotes
    }

    fun deleteNote(note : Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteNote(note)
    }

    fun updateNote(note : Note) = viewModelScope.launch (Dispatchers.IO){
        repository.updateNote(note)
    }

    fun addNote(note : Note) = viewModelScope.launch (Dispatchers.IO){
        repository.insert(note)
    }
}