package com.example.notesappusingmvvm.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesappusingmvvm.model.entity.Note


@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao() : NotesDao

    companion object{

        @Volatile
        private var INSTANCE : NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase{
//            If the instance is null, then create the database, and if it is not null, then return it.
            return INSTANCE?: synchronized(this){ // synchronised so that only a single instance of db is created (only one thread is given access to db)
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NotesDatabase::class.java,
                    "notes_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}