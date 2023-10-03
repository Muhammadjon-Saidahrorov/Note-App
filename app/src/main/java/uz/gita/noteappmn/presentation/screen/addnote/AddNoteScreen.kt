package uz.gita.noteappmn.presentation.screen.addnote

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import jp.wasabeef.richeditor.RichEditor
import uz.gita.noteappmn.R
import uz.gita.noteappmn.data.model.NoteData
import uz.gita.noteappmn.databinding.ScreenAddNoteBinding
import uz.gita.noteappmn.presentation.screen.addnote.viewmodel.AddNoteViewModel
import uz.gita.noteappmn.presentation.screen.addnote.viewmodel.impl.AddNoteViewModelImpl
import uz.gita.noteappmn.utils.myApply
import java.text.SimpleDateFormat
import java.util.*

class AddNoteScreen : Fragment(R.layout.screen_add_note) {
    private val viewModel: AddNoteViewModel by viewModels<AddNoteViewModelImpl>()
    private val binding by viewBinding(ScreenAddNoteBinding::bind)

    private val args by navArgs<AddNoteScreenArgs>()
    private var noteDatadd : NoteData? = null


    private lateinit var editor: RichEditor
    private var logicBold = false
    private var logicItalic = false
    private var logicUnderline = false
    private var logicStrikethrough = false
    private var logicBlockquote = false
    private var logicInsertNumbers = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {

        editor = binding.richEditor

        editor = binding.richEditor
        editor.setPlaceholder("Insert note here...")
        editor.setTextColor(Color.parseColor("#FF000000"))
        editor.setPadding(8, 8, 8, 8)

        val noteData = args.noteP

        if (noteData != null) {
            binding.titleEdit.text = Editable.Factory.getInstance().newEditable(noteData.title)
            richEditor.html = noteData.content
            noteDatadd = noteData
        }

        actionUndo.setOnClickListener {
            editor.undo()
        }

        actionRedo.setOnClickListener {
            editor.redo()
        }

        actionBold.setOnClickListener {
            editor.setBold()
            logicBold = if (logicBold) {
                actionBold.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                !logicBold
            } else {
                actionBold.setBackgroundColor(Color.parseColor("#E8E8E8"))
                !logicBold
            }
        }

        actionItalic.setOnClickListener {
            editor.setItalic()
            logicItalic = if (logicItalic) {
                actionItalic.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                !logicItalic
            } else {
                actionItalic.setBackgroundColor(Color.parseColor("#E8E8E8"))
                !logicItalic
            }
        }

        actionIndent.setOnClickListener {
            editor.setIndent()
        }

        actionOutdent.setOnClickListener {
            editor.setOutdent()
        }

        actionUnderline.setOnClickListener {
            editor.setUnderline()
            logicUnderline = if (logicUnderline) {
                actionUnderline.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                !logicUnderline
            } else {
                actionUnderline.setBackgroundColor(Color.parseColor("#E8E8E8"))
                !logicUnderline
            }
        }

        actionStrikethrough.setOnClickListener {
            editor.setStrikeThrough()
            logicStrikethrough = if (logicStrikethrough) {
                actionStrikethrough.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                !logicStrikethrough
            } else {
                actionStrikethrough.setBackgroundColor(Color.parseColor("#E8E8E8"))
                !logicStrikethrough
            }
        }

        actionAlignLeft.setOnClickListener {
            editor.setAlignLeft()
        }

        actionAlignCenter.setOnClickListener {
            editor.setAlignCenter()
        }

        actionAlignRight.setOnClickListener {
            editor.setAlignRight()
        }

        actionBlockquote.setOnClickListener {
            editor.setBullets()
            logicBlockquote = if (logicBlockquote) {
                actionBlockquote.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                !logicBlockquote
            } else {
                actionBlockquote.setBackgroundColor(Color.parseColor("#E8E8E8"))
                !logicBlockquote
            }
        }

        actionInsertNumbers.setOnClickListener {
            editor.setNumbers()
            logicInsertNumbers = if (logicInsertNumbers) {
                actionInsertNumbers.setBackgroundColor(Color.parseColor("#FFFFFFFF"))
                !logicInsertNumbers
            } else {
                actionInsertNumbers.setBackgroundColor(Color.parseColor("#E8E8E8"))
                !logicInsertNumbers
            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        val title = binding.titleEdit.text.toString()
        val content = binding.richEditor.html
        title.trim()
        if (content != null) {
            val sdf = SimpleDateFormat("MMMM dd.yyyy")
            val times = sdf.format(Date()).toString()

            if (noteDatadd != null){
                if (noteDatadd!!.onLock == 1){
                    val noteDataAdd = NoteData(noteDatadd!!.id, title, content, times,0,1)
                    viewModel.addNote(noteDataAdd)
                }else{
                    val noteDataAdd = NoteData(noteDatadd!!.id, title, content, times)
                    viewModel.addNote(noteDataAdd)
                }
            } else{
                val noteDataAdd = NoteData(0, title, content, times)
                viewModel.addNote(noteDataAdd)
            }
        }
    }
}