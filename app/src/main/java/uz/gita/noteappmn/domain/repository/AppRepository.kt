package uz.gita.noteappmn.domain.repository

import androidx.lifecycle.LiveData
import uz.gita.noteappmn.data.model.NoteData

interface AppRepository {

    fun addNote(note: NoteData)

    fun updateNote(note: NoteData)

    fun deleteNote(note: NoteData)

    fun deleteNotes(notes: List<NoteData>)

    fun getNotes(): LiveData<List<NoteData>>
    fun getNotesInTrash(): LiveData<List<NoteData>>
    fun getNotesInLock(): LiveData<List<NoteData>>

    fun getNotesByQuery(note:String?): List<NoteData>


}