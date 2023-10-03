package uz.gita.noteappmn.presentation.screen.trash

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.noteappmn.R
import uz.gita.noteappmn.data.model.NoteData
import uz.gita.noteappmn.databinding.ScreenTrashBinding
import uz.gita.noteappmn.presentation.adapter.TrashAdapter
import uz.gita.noteappmn.presentation.dialog.TrashDialog
import uz.gita.noteappmn.presentation.screen.trash.viewmodel.TrashViewModel
import uz.gita.noteappmn.presentation.screen.trash.viewmodel.impl.TrashViewModelImpl
import uz.gita.noteappmn.utils.myApply

class TrashScreen : Fragment(R.layout.screen_trash) {

    private val viewModel: TrashViewModel by viewModels<TrashViewModelImpl>()
    private val binding by viewBinding(ScreenTrashBinding::bind)
    private val adapter by lazy { TrashAdapter() }
    private var listCount = 0


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {

        list.adapter = adapter
        list.layoutManager = LinearLayoutManager(requireContext())

        viewModel.notesLiveData.observe(viewLifecycleOwner, notesObserver)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.trash_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {

                    R.id.clear_notes -> {
                        val dialog = AlertDialog.Builder(requireContext())
                            .setCancelable(false)
                            .setTitle("Do you want to really delete all notes? ")
                            .setPositiveButton(
                                "Yes",
                                (DialogInterface.OnClickListener { _, _ ->
                                    viewModel.notesLiveData.observe(viewLifecycleOwner, notesDeleteObserver)
                                    Toast.makeText(requireContext(),"Deleted All!", Toast.LENGTH_SHORT).show()
                                })
                            )
                            .setNegativeButton("No",
                                (DialogInterface.OnClickListener { d, _ ->
                                    d.dismiss()
                                }))
                            .create()
                        dialog.show()
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner)
    }

    private val notesObserver = Observer<List<NoteData>> {

        listCount = it.size

        if (listCount == 0) {
            binding.placeHolder.visibility = View.VISIBLE
        }else{
            binding.placeHolder.visibility = View.GONE
        }

        adapter.setOnChangeLongClickListener { it1 ->

            val dialogBottomSheet = TrashDialog()

            dialogBottomSheet.setClickAddNotesButtonListener {
                viewModel.updateNote(NoteData(it1.id, it1.title, it1.content, it1.createdAt, 0))
            }

            dialogBottomSheet.setClickDeleteButtonListener {
                viewModel.deleteNote(it1)
                Toast.makeText(requireContext(),"Deleted!", Toast.LENGTH_SHORT).show()

            }

            dialogBottomSheet.show(requireActivity().supportFragmentManager, "EditDialog")

        }
        adapter.submitList(it)
    }

    private val notesDeleteObserver = Observer<List<NoteData>> {
        viewModel.deleteAllNotes(it)
        adapter.submitList(it)
    }
}