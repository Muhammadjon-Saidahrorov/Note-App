package uz.gita.noteappmn.presentation.screen.home

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.icu.lang.UCharacter.VerticalOrientation
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import uz.gita.noteappmn.R
import uz.gita.noteappmn.data.model.NoteData
import uz.gita.noteappmn.databinding.ScreenHomeBinding
import uz.gita.noteappmn.presentation.adapter.NoteAdapter
import uz.gita.noteappmn.presentation.dialog.EventDialog
import uz.gita.noteappmn.presentation.screen.home.viewmodel.HomeViewModel
import uz.gita.noteappmn.presentation.screen.home.viewmodel.impl.HomeViewModelImpl
import uz.gita.noteappmn.utils.myApply
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import uz.gita.noteappmn.data.source.LocalStorage

class HomeScreen : Fragment(R.layout.screen_home) {
    private val viewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val binding by viewBinding(ScreenHomeBinding::bind)
    private val adapter by lazy { NoteAdapter() }
    private val localStorage = LocalStorage.getInstance()
    private var searchedCount = 0
    private var count = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.openAddNoteScreenLiveData.observe(this, openAddNoteObserver)
        viewModel.openAddNoteScreenLiveDataUpdate.observe(this, openNote)
    }

    @SuppressLint("ResourceType")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = binding.myApply {

        addNoteBtn.setOnClickListener {
            viewModel.openAddNoteScreen()
        }

        list.adapter = adapter
        if (localStorage?.getLogicSort() == true){
            list.layoutManager = LinearLayoutManager(requireContext())
        }else{
            list.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
        }

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.home_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {

                return when (menuItem.itemId) {

                    R.id.sort -> {
                        if (localStorage?.getLogicSort() == true){
                            list.layoutManager = StaggeredGridLayoutManager(2, VERTICAL)
   //                         menuItem.setIcon(R.drawable.liner2)
                            localStorage.saveLogicSort(!(localStorage.getLogicSort())!!)
                        }else{
                            list.layoutManager = LinearLayoutManager(requireContext())
   //                         menuItem.setIcon(R.drawable.gibrit)
                            localStorage?.saveLogicSort(!(localStorage.getLogicSort())!!)
                        }
                        true
                    }
                    else -> {
                        false
                    }
                }
            }
        }, viewLifecycleOwner)

        viewModel.searchedWordsLiveData.observe(viewLifecycleOwner, searchingNotesObserver)
        viewModel.notesLiveData.observe(viewLifecycleOwner, notesObserver)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (searchView.visibility == View.VISIBLE) {
                    placeHolder.visibility = View.GONE
                }
                query?.let {
                    if (query.isNotEmpty()) {
                        viewModel.searchingNote("%${it.trim()}%")
                    } else {
                        if (count == 0) {
                            placeHolder.visibility = View.VISIBLE
                        }
                        adapter.submitList(viewModel.notesLiveData.value)
                    }
                }

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (searchView.visibility == View.VISIBLE) {
                    placeHolder.visibility = View.GONE
                }
                newText?.let {
                    if (newText.isNotEmpty()) {
                        viewModel.searchingNote("%${it.trim()}%")
                    } else {
                        if (count == 0) {
                            placeHolder.visibility = View.VISIBLE
                        }
                        adapter.submitList(viewModel.notesLiveData.value)
                    }
                }

                return true
            }

        })



    }


    private val notesObserver = Observer<List<NoteData>> {
        count = it.size

        if (count == 0) {
            binding.placeHolder.visibility = View.VISIBLE
        } else {
            binding.placeHolder.visibility = View.GONE
        }

        adapter.setOnChangeLongClickListener { it1 ->

            val dialogBottomSheet = EventDialog()

            dialogBottomSheet.setClickLockButtonListener {
                var logic = localStorage?.getLogic()
                if(logic == false){
                    viewModel.updateNote(NoteData(it1.id, it1.title, it1.content, it1.createdAt, 0, 1))
                }else{
                    Toast.makeText(requireContext(),"Create Password!", Toast.LENGTH_SHORT).show()
                }
            }

            dialogBottomSheet.setClickDeleteButtonListener {
                viewModel.updateNote(NoteData(it1.id, it1.title, it1.content, it1.createdAt, 1))
            }

            dialogBottomSheet.show(requireActivity().supportFragmentManager, "EditDialog")

        }

        adapter.setClickListener { note ->
            viewModel.openAddNoteScreenUpdate(note)
        }

        adapter.submitList(it)
    }

    private val openAddNoteObserver = Observer<Unit> {
        findNavController().navigate(R.id.action_homeScreen_to_addNoteScreen)
    }

    private val openNote = Observer<NoteData> {
        findNavController().navigate(HomeScreenDirections.actionHomeScreenToAddNoteScreen(it))
    }

    private val searchingNotesObserver = Observer<List<NoteData>> {
        searchedCount = it.size
        if (it.isEmpty()) {
            binding.placeHolder.visibility = View.VISIBLE
        } else {
            binding.placeHolder.visibility = View.GONE
        }

        adapter.submitList(it)
    }

}