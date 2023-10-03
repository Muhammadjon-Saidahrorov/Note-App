package uz.gita.noteappmn.domain.repository.impl

import androidx.lifecycle.LiveData
import uz.gita.noteappmn.data.model.NoteData
import uz.gita.noteappmn.data.source.local.NoteDatabase
import uz.gita.noteappmn.domain.repository.AppRepository

class AppRepositoryImpl private constructor() : AppRepository {

    companion object {
        private var repository: AppRepositoryImpl? = null

        fun getInstance(): AppRepositoryImpl {
            if (repository == null) {
                repository = AppRepositoryImpl()
            }
            return repository!!
        }
    }

    private val noteDao = NoteDatabase.getInstance().getNoteDao()

    override fun addNote(note: NoteData) {
        noteDao.addNote(note.toNoteEntity())
    }

    override fun updateNote(note: NoteData) {
        noteDao.updateNote(note.toNoteEntity())
    }


    override fun deleteNote(note: NoteData) {
        noteDao.deleteNote(note.toNoteEntity())
    }

    override fun deleteNotes(notes: List<NoteData>) {
        val list = notes.map {
            it.toNoteEntity()
        }.toTypedArray()

        noteDao.deleteNotes(list)
    }

    override fun getNotes(): LiveData<List<NoteData>> {
        return noteDao.getNotes()
//        val data = noteDao.getNotes()
//        return Transformations.map(data) {
//            val mappedList = mutableListOf<NoteData>()
//            it.forEach {
//                mappedList.add(it.toNoteData())
//            }
//            return@map mappedList
    }

    override fun getNotesInTrash(): LiveData<List<NoteData>> {
        return noteDao.getNotesInTrash()
//        val data = noteDao.getNotesInTrash()
//        return Transformations.map(data) {
//            val mappedList = mutableListOf<NoteData>()
//            it.forEach {
//                mappedList.add(it.toNoteData())
//            }
//            return@map mappedList
    }

    override fun getNotesInLock(): LiveData<List<NoteData>> {
        return noteDao.getNotesInLock()
    }

    override fun getNotesByQuery(note: String?): List<NoteData> {
        return noteDao.getNotesByQuery(note)
    }

}