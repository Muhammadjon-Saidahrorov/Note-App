package uz.gita.noteappmn.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import uz.gita.noteappmn.data.source.local.entity.NoteEntity

data class NoteData(
    val id: Long = 0,
    val title: String,
    val content: String,
    @ColumnInfo(name = "created_at")
    val createdAt: String,
    @ColumnInfo(name = "on_trash")
    val onTrash: Int = 0,
    @ColumnInfo(name = "on_lock")
    val onLock: Int = 0
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt(),
        parcel.readInt()
    ) {
    }

    fun toNoteEntity(): NoteEntity = NoteEntity(
        id, title, content, createdAt, onTrash, onLock
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeString(createdAt)
        parcel.writeInt(onTrash)
        parcel.writeInt(onLock)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoteData> {
        override fun createFromParcel(parcel: Parcel): NoteData {
            return NoteData(parcel)
        }

        override fun newArray(size: Int): Array<NoteData?> {
            return arrayOfNulls(size)
        }
    }
}