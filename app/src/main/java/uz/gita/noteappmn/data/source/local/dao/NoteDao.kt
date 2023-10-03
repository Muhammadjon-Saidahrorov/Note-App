package uz.gita.noteappmn.data.source.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import uz.gita.noteappmn.data.model.NoteData
import uz.gita.noteappmn.data.source.local.entity.NoteEntity

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNote(note: NoteEntity)

    @Update
    fun updateNote(note: NoteEntity)

    @Delete
    fun deleteNote(note: NoteEntity)

    @Delete
    fun deleteNotes(notes: Array<NoteEntity>)

    @Query("SELECT * FROM Notes WHERE on_trash=0 AND on_lock=0 order by id DESC")
    fun getNotes(): LiveData<List<NoteData>>

    @Query("SELECT * FROM Notes WHERE on_trash=1")
    fun getNotesInTrash(): LiveData<List<NoteData>>

    @Query("SELECT * FROM Notes WHERE on_lock=1")
    fun getNotesInLock(): LiveData<List<NoteData>>

    @Query("SELECT * FROM Notes WHERE title LIKE :note AND on_trash = 0 AND on_lock = 0")
    fun getNotesByQuery(note:String?): List<NoteData>

}