package uz.gita.noteappmn.app

import android.app.Application
import uz.gita.noteappmn.data.source.LocalStorage
import uz.gita.noteappmn.data.source.local.NoteDatabase

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        LocalStorage.init(this)
        NoteDatabase.init(this)
    }
}