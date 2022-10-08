package com.example.notesappusingmvvm.views.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesappusingmvvm.R
import com.example.notesappusingmvvm.model.entity.Note

class NotesAdapter(
    private val context: Context,
    private val noteClickInterface: NoteClickInterface,
    private val noteDeleteInterface: NoteDeleteInterface,
) : RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    private val allNotesList = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.note_item_design, parent, false)
        return NotesViewHolder(view)
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        holder.noteTitle.text = allNotesList[position].noteTitle
        holder.noteDescription.text = allNotesList[position].noteDescription
        val timeStamp = context.getString(R.string.last_updated_at) + allNotesList[position].timestamp
        holder.timeStamp.text = timeStamp

        holder.deleteNote.setOnClickListener{
            noteDeleteInterface.onDeleteIconClick(allNotesList[position])
        }

        holder.itemView.setOnClickListener{
            noteClickInterface.onNoteClick(allNotesList[position])
        }
    }

    override fun getItemCount(): Int {
        return allNotesList.size
    }

    fun updateList(newNotesList : ArrayList<Note>){
        allNotesList.clear()
        allNotesList.addAll(newNotesList)
        notifyDataSetChanged()
    }

    class NotesViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val noteTitle : TextView = itemView.findViewById(R.id.tv_note_title)
        val noteDescription: TextView = itemView.findViewById(R.id.tv_note_description)
        val timeStamp: TextView = itemView.findViewById(R.id.tv_timestamp)
        val deleteNote: ImageView = itemView.findViewById(R.id.iv_delete_note)
    }
}

interface NoteDeleteInterface{
    fun onDeleteIconClick(note : Note)
}

interface NoteClickInterface{
    fun onNoteClick(note : Note)
}