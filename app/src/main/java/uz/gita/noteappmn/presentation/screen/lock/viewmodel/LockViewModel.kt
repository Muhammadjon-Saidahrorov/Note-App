package uz.gita.noteappmn.presentation.screen.lock.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.noteappmn.data.model.NoteData

interface LockViewModel {
    val notesLiveData: LiveData<List<NoteData>>
    val openAddNoteScreenLiveDataUpdate: LiveData<NoteData>

    fun updateNote(note: NoteData)
    fun deleteAllNotes(notes:List<NoteData>)
    fun openAddNoteScreenUpdate(note: NoteData)

}