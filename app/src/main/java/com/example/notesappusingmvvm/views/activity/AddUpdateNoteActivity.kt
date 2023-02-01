package com.example.notesappusingmvvm.views.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.notesappusingmvvm.R
import com.example.notesappusingmvvm.model.entity.Note
import com.example.notesappusingmvvm.utils.Constants
import com.example.notesappusingmvvm.viewmodel.NotesViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddUpdateNoteActivity : AppCompatActivity() {
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSaveNote: Button
    private lateinit var viewModel: NotesViewModel
    private var noteId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_update_note)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        initialisingViews()
        val noteType = intent.getStringExtra(Constants.NOTE_TYPE)
        if (noteType.equals(Constants.EDIT_NOTE)){
            val noteTitle = intent.getStringExtra(Constants.NOTE_TITLE)
            val noteDescription = intent.getStringExtra(Constants.NOTE_DESCRIPTION)
            noteId = intent.getIntExtra(Constants.NOTE_ID, -1)

            etTitle.setText(noteTitle)
            etDescription.setText(noteDescription)
            supportActionBar!!.title = "Edit Note"
        }
        else{
            supportActionBar!!.title = "New Note"
        }
        btnSaveNote.setOnClickListener{
            val noteTitle = etTitle.text.toString()
            val noteDescription = etDescription.text.toString()

            if (noteTitle.trim().isEmpty() || noteDescription.trim().isEmpty()){
                Toast.makeText(this, "Field Cannot Be Blank!", Toast.LENGTH_SHORT).show()
            }
            else{
                if (noteType.equals(Constants.EDIT_NOTE)){
                    val format = getString(R.string.simple_date_format)
                    val sdf = SimpleDateFormat(format)
                    val currentDate: String = sdf.format(Date())
                    val updatedNote = Note(noteTitle, noteDescription, currentDate)
                    updatedNote.id = noteId
                    viewModel.updateNote(updatedNote)
                    Toast.makeText(this, "Note Updated!", Toast.LENGTH_SHORT).show()
                }
                else{
                    val format = getString(R.string.simple_date_format)
                    val sdf = SimpleDateFormat(format)
                    val currentDate: String = sdf.format(Date())
                    val newNote = Note(noteTitle, noteDescription, currentDate)
                    viewModel.addNote(newNote)
                    Toast.makeText(this, "Note Added", Toast.LENGTH_SHORT).show()
                }

                startActivity(Intent(this, MainActivity::class.java))
                this.finish()
            }
        }
    }

    private fun initialisingViews(){
        etTitle = findViewById(R.id.et_enter_note_title)
        etDescription = findViewById(R.id.et_enter_note_description)
        btnSaveNote = findViewById(R.id.btn_save_note)
        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NotesViewModel::class.java]
    }
}