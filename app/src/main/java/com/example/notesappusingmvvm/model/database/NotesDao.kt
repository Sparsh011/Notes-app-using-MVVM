package com.example.notesappusingmvvm.model.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.notesappusingmvvm.model.entity.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNoteToDatabase(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes_table ORDER BY ID ASC")
    fun getAllNotes() : LiveData<List<Note>>

}