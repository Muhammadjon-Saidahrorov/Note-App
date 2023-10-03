package uz.gita.noteappmn.presentation.screen.addnote.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.noteappmn.data.model.NoteData

interface AddNoteViewModel {
    fun addNote(noteData: NoteData)
}