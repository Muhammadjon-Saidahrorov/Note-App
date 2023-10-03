package uz.gita.noteappmn.presentation.screen.trash.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import uz.gita.noteappmn.data.model.NoteData
import uz.gita.noteappmn.domain.repository.AppRepository
import uz.gita.noteappmn.domain.repository.impl.AppRepositoryImpl
import uz.gita.noteappmn.presentation.screen.trash.viewmodel.TrashViewModel

class TrashViewModelImpl: TrashViewModel, ViewModel() {
    private val repository: AppRepository = AppRepositoryImpl.getInstance()

    override val notesLiveData: LiveData<List<NoteData>> = repository.getNotesInTrash()

    override fun updateNote(note: NoteData) = repository.updateNote(note)

    override fun deleteAllNotes(notes:List<NoteData>) = repository.deleteNotes(notes)

    override fun deleteNote(note: NoteData) = repository.deleteNote(note)


}