package uz.gita.noteappmn.data.source

import android.content.Context
import android.content.SharedPreferences

class LocalStorage (context: Context) {

    private var preferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    init {
        preferences = context.getSharedPreferences("EXAM3", Context.MODE_PRIVATE)
        editor = preferences?.edit()
    }


    companion object {
        private var localStorge: LocalStorage? = null

        fun getInstance(): LocalStorage? {
            return localStorge
        }

        fun init(context: Context) {
            if (localStorge == null) localStorge = LocalStorage(context)
        }

    }

    fun savePassword(str: String) {
        editor?.putString("PASSWORD", str)?.apply()
    }

    fun getPassword(): String? {
        return preferences?.getString("PASSWORD", "")
    }

    fun saveLogic(str: Boolean) {
        editor?.putBoolean("LOGIC", str)?.apply()
    }

    fun getLogic(): Boolean? {
        return preferences?.getBoolean("LOGIC", true)
    }

    fun saveLogicDelete(str: Boolean) {
        editor?.putBoolean("LOGICDELETE", str)?.apply()
    }

    fun getLogicDelete(): Boolean? {
        return preferences?.getBoolean("LOGICDELETE", true)
    }

    fun saveLogicSort(str: Boolean) {
        editor?.putBoolean("LOGICSORT", str)?.apply()
    }

    fun getLogicSort(): Boolean? {
        return preferences?.getBoolean("LOGICSORT", true)
    }
}