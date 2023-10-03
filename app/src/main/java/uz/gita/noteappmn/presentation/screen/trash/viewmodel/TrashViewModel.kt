package uz.gita.noteappmn.presentation.screen.trash.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.noteappmn.data.model.NoteData

interface TrashViewModel {
    val notesLiveData: LiveData<List<NoteData>>

    fun updateNote(note: NoteData)
    fun deleteAllNotes(notes:List<NoteData>)
    fun deleteNote(note: NoteData)
}