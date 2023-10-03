package uz.gita.noteappmn.presentation.screen.lock

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.noteappmn.R
import uz.gita.noteappmn.data.model.NoteData
import uz.gita.noteappmn.data.source.LocalStorage
import uz.gita.noteappmn.databinding.ScreenLockBinding
import uz.gita.noteappmn.databinding.ScreenTrashBinding
import uz.gita.noteappmn.presentation.adapter.LockAdapter
import uz.gita.noteappmn.presentation.adapter.TrashAdapter
import uz.gita.noteappmn.presentation.dialog.EventDialog
import uz.gita.noteappmn.presentation.dialog.LockDialog
import uz.gita.noteappmn.presentation.screen.home.HomeScreenDirections
import uz.gita.noteappmn.presentation.screen.lock.viewmodel.LockViewModel
import uz.gita.noteappmn.presentation.screen.lock.viewmodel.impl.LockViewModelImpl
import uz.gita.noteappmn.presentation.screen.trash.viewmodel.TrashViewModel
import uz.gita.noteappmn.presentation.screen.trash.viewmodel.impl.TrashViewModelImpl
import uz.gita.noteappmn.utils.myApply

class LockScreen: Fragment(R.layout.screen_lock) {
    private val viewModel: LockViewModel by viewModels<LockViewModelImpl>()
    private val binding by viewBinding(ScreenLockBinding::bind)
    private val localStorage = LocalStorage.getInstance()
    private val adapter by lazy { LockAdapter() }
    private var listCount = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openAddNoteScreenLiveDataUpdate.observe(this, openNote)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(requireContext())

        if (localStorage?.getLogicDelete() == false){
            viewModel.notesLiveData.observe(viewLifecycleOwner, notesDeleteObserver)
            localStorage.saveLogicDelete(true)
        }
        viewModel.notesLiveData.observe(viewLifecycleOwner, notesObserver)
    }

    private val notesObserver = Observer<List<NoteData>> {

        listCount = it.size

        if (listCount == 0) {
            binding.placeHolder.visibility = View.VISIBLE
        }else{
            binding.placeHolder.visibility = View.GONE
        }

        adapter.setOnChangeLongClickListener { it1 ->

            val dialogBottomSheet = LockDialog()

            dialogBottomSheet.setClickLockButtonListener {
                viewModel.updateNote(NoteData(it1.id, it1.title, it1.content, it1.createdAt, 0, 0))
            }

            dialogBottomSheet.setClickDeleteButtonListener {
                viewModel.updateNote(NoteData(it1.id, it1.title, it1.content, it1.createdAt, 1, 0))
            }

            dialogBottomSheet.show(requireActivity().supportFragmentManager, "EditDialog")

        }

        adapter.setClickListener { note ->
            viewModel.openAddNoteScreenUpdate(note)
        }

        adapter.submitList(it)
    }

    private val notesDeleteObserver = Observer<List<NoteData>> {
        viewModel.deleteAllNotes(it)
        adapter.submitList(it)
    }

    private val openNote = Observer<NoteData> {
        findNavController().navigate(LockScreenDirections.actionLockScreenToAddNoteScreen(it))
    }

}