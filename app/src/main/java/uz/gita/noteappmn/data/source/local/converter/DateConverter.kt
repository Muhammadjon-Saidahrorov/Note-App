package uz.gita.noteappmn.data.source.local.converter

import androidx.room.TypeConverter
import java.util.*

object DateConverter {

    @TypeConverter
    fun fromDateToTimeStamp(date:Date): Long{
        return date.time
    }

    @TypeConverter
    fun fromTimeStampToDate(value: Long): Date{
        return Date(value)
    }
}