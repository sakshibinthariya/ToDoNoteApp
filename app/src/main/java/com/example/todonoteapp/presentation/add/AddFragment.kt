package com.example.todonoteapp.presentation.add

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.todonoteapp.R
import com.example.todonoteapp.data.model.NoteData
import com.example.todonoteapp.databinding.FragmentAddBinding
import com.example.todonoteapp.presentation.viewmodel.NoteViewModel
import com.example.todonoteapp.presentation.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddFragment : Fragment(R.layout.fragment_add) {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    private val noteVm: NoteViewModel by viewModels()
    private val sharedVm: SharedViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddBinding.bind(view)
        binding.sAddNote.onItemSelectedListener = sharedVm.listener

        binding.btnSave.setOnClickListener {
            insertDataToDatabase()
        }

    }

    private fun insertDataToDatabase() {
        val noteTitle = binding.etAddNoteTitle.text.toString()
        val notePriority = binding.sAddNote.selectedItem.toString()
        val noteDesc = binding.etAddNoteMultiLine.text.toString()

        val validation = sharedVm.verifyDataFromUser(noteTitle, noteDesc)

        if (validation) {
            val newData = NoteData(
                0,
                noteTitle,
                noteDesc,
                sharedVm.parsePriority(notePriority)
            )
            noteVm.insertData(newData)
            Toast.makeText(
                requireContext(),
                R.string.add_success_message,
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(
                requireContext(),
                R.string.empty_field_message,
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}