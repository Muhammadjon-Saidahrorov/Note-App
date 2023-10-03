package uz.gita.noteappmn.presentation.screen.home.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.noteappmn.data.model.NoteData

interface HomeViewModel {
    val notesLiveData: LiveData<List<NoteData>>
    val openAddNoteScreenLiveData: LiveData<Unit>
    val openAddNoteScreenLiveDataUpdate: LiveData<NoteData>
    val searchedWordsLiveData:LiveData<List<NoteData>>

    fun openAddNoteScreen()
    fun openAddNoteScreenUpdate(note: NoteData)
    fun updateNote(note: NoteData)
    fun searchingNote(s:String)
}