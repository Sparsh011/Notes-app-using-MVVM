package com.example.notesappusingmvvm.views.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappusingmvvm.R
import com.example.notesappusingmvvm.model.entity.Note
import com.example.notesappusingmvvm.utils.Constants
import com.example.notesappusingmvvm.viewmodel.NotesViewModel
import com.example.notesappusingmvvm.views.adapter.NoteClickInterface
import com.example.notesappusingmvvm.views.adapter.NoteDeleteInterface
import com.example.notesappusingmvvm.views.adapter.NotesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), NoteClickInterface, NoteDeleteInterface {
    private lateinit var rvNotes : RecyclerView
    private lateinit var addNoteFAB: FloatingActionButton
    private lateinit var viewModel : NotesViewModel
    private lateinit var notesAdapter: NotesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialisingViews()


        viewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[NotesViewModel::class.java]

        viewModel.allNotes.observe(this) { list ->
            list?.let {
                notesAdapter.updateList(it as ArrayList<Note>)
            }
        }

        addNoteFAB.setOnClickListener{
            val intent = Intent(this@MainActivity, AddUpdateNoteActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun initialisingViews(){
        rvNotes = findViewById(R.id.rv_notes)
        addNoteFAB = findViewById(R.id.fab_add_note)
        rvNotes.layoutManager = LinearLayoutManager(this)
        notesAdapter = NotesAdapter(this, this, this)
        rvNotes.adapter = notesAdapter
    }

    override fun onNoteClick(note: Note) {
        val intent = Intent(this@MainActivity, AddUpdateNoteActivity::class.java)
        intent.putExtra(Constants.NOTE_TYPE, Constants.EDIT_NOTE)
        intent.putExtra(Constants.NOTE_TITLE, note.noteTitle)
        intent.putExtra(Constants.NOTE_DESCRIPTION, note.noteDescription)
        intent.putExtra(Constants.NOTE_ID, note.id)
        finish()
        startActivity(intent)
    }

    override fun onDeleteIconClick(note: Note) {
        viewModel.deleteNote(note)
        Toast.makeText(this, "Note Deleted!", Toast.LENGTH_SHORT).show()
    }
}