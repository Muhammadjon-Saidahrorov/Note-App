package uz.gita.noteappmn.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import uz.gita.noteappmn.data.source.local.converter.DateConverter
import uz.gita.noteappmn.data.source.local.dao.NoteDao
import uz.gita.noteappmn.data.source.local.entity.NoteEntity

@Database(entities = [NoteEntity::class], version = 1)
@TypeConverters(DateConverter::class)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    companion object {
        private var noteDatabase: NoteDatabase? = null

        fun init(contect: Context) {
            if (noteDatabase == null) {
                noteDatabase = Room.databaseBuilder(
                    contect,
                    NoteDatabase::class.java,
                    "notess.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
        }

        fun getInstance() = noteDatabase!!
    }
}