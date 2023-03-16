package com.example.todonoteapp.presentation.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todonoteapp.R
import com.example.todonoteapp.data.model.NoteData
import com.example.todonoteapp.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar
import com.example.todonoteapp.presentation.viewmodel.NoteViewModel
import com.example.todonoteapp.presentation.viewmodel.SharedViewModel
import com.example.todonoteapp.presentation.list.adapter.ListAdapter
import com.example.todonoteapp.presentation.list.listener.SwipeToDelete
import dagger.hilt.android.AndroidEntryPoint
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

@AndroidEntryPoint
class ListFragment : Fragment(R.layout.fragment_list) {

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val noteAdapter: ListAdapter by lazy { ListAdapter() }

    private val noteVm: NoteViewModel by viewModels()
    private val sharedVm: SharedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentListBinding.bind(view)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.sharedVm = sharedVm

      setupMenu()
        setupRecycler()
        setupViewModel()

    }

    private fun setupMenu() {
        val menuHost: MenuHost = requireActivity()
        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_list, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_delete_all -> confirmDelete()

                }
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun confirmDelete() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(R.string.dialog_title)
        builder.setMessage(R.string.dialog_confirmation_message)
        builder.setPositiveButton(R.string.yes_text) { _, _ ->
            noteVm.deleteAll()
            Toast.makeText(
                requireContext(),
                R.string.data_remove_success_message,
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.create().show()
    }

    private fun setupRecycler() {
        val recyclerView = binding.rvListFragment
        recyclerView.adapter = noteAdapter
        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager =layoutManager
        recyclerView.itemAnimator = SlideInUpAnimator().apply {
            addDuration = 300
        }
        recyclerView.setHasFixedSize(true)
        swipeToDelete(recyclerView)
    }

    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = noteAdapter.noteData[viewHolder.adapterPosition]

                noteVm.deleteData(deletedItem)
                noteAdapter.notifyItemRemoved(viewHolder.adapterPosition)

                restoreDeleteItem(viewHolder.itemView, deletedItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeleteItem(view: View, deletedItem: NoteData) {
        val snackBar = Snackbar.make(
            view,
            "Deleted '${deletedItem.title}'",
            Snackbar.LENGTH_LONG
        )
        snackBar.setAction(R.string.undo) {
            noteVm.insertData(deletedItem)
        }
        snackBar.show()
    }

    private fun setupViewModel() {
        noteVm.getAllData()
        noteVm.getAllData.observe(viewLifecycleOwner) { data ->
            sharedVm.checkIfDatabaseEmpty(data)
            noteAdapter.setData(data)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}