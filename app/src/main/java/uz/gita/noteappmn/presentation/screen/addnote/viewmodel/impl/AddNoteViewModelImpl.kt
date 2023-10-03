package uz.gita.noteappmn.presentation.screen.addnote.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.noteappmn.data.model.NoteData
import uz.gita.noteappmn.domain.repository.AppRepository
import uz.gita.noteappmn.domain.repository.impl.AppRepositoryImpl
import uz.gita.noteappmn.presentation.screen.addnote.viewmodel.AddNoteViewModel

class AddNoteViewModelImpl: AddNoteViewModel, ViewModel() {

    private val repository: AppRepository = AppRepositoryImpl.getInstance()

    override fun addNote(noteData: NoteData) {
        repository.addNote(noteData)
    }


}