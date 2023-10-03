package uz.gita.noteappmn.presentation.screen.lock.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.noteappmn.data.model.NoteData
import uz.gita.noteappmn.domain.repository.AppRepository
import uz.gita.noteappmn.domain.repository.impl.AppRepositoryImpl
import uz.gita.noteappmn.presentation.screen.lock.viewmodel.LockViewModel

class LockViewModelImpl : LockViewModel, ViewModel() {
    private val repository: AppRepository = AppRepositoryImpl.getInstance()
    override val openAddNoteScreenLiveDataUpdate = MutableLiveData<NoteData>()

    override val notesLiveData: LiveData<List<NoteData>> = repository.getNotesInLock()

    override fun updateNote(note: NoteData) = repository.updateNote(note)

    override fun deleteAllNotes(notes: List<NoteData>) {
        return repository.deleteNotes(notes)
    }

    override fun openAddNoteScreenUpdate(note: NoteData) {
        openAddNoteScreenLiveDataUpdate.value = note
    }

}