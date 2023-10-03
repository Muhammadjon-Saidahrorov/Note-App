package uz.gita.noteappmn.presentation.screen.home.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.noteappmn.data.model.NoteData
import uz.gita.noteappmn.domain.repository.AppRepository
import uz.gita.noteappmn.domain.repository.impl.AppRepositoryImpl
import uz.gita.noteappmn.presentation.screen.home.viewmodel.HomeViewModel

class HomeViewModelImpl : HomeViewModel, ViewModel() {

    private val repository: AppRepository = AppRepositoryImpl.getInstance()

    override val notesLiveData: LiveData<List<NoteData>> = repository.getNotes()

    override val openAddNoteScreenLiveData = MutableLiveData<Unit>()
    override val openAddNoteScreenLiveDataUpdate = MutableLiveData<NoteData>()
    override val searchedWordsLiveData = MutableLiveData<List<NoteData>>()

    override fun openAddNoteScreen() {
        openAddNoteScreenLiveData.value = Unit
    }

    override fun openAddNoteScreenUpdate(note: NoteData) {
        openAddNoteScreenLiveDataUpdate.value = note
    }

    override fun updateNote(note: NoteData) = repository.updateNote(note)

    override fun searchingNote(s: String) {
        searchedWordsLiveData.value = repository.getNotesByQuery(s)
    }

}