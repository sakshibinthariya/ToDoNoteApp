package com.example.todonoteapp.presentation.update

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.todonoteapp.R
import com.example.todonoteapp.data.model.NoteData
import com.example.todonoteapp.databinding.FragmentUpdateBinding
import com.example.todonoteapp.presentation.viewmodel.NoteViewModel
import com.example.todonoteapp.presentation.viewmodel.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateFragment : Fragment(R.layout.fragment_update) {

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val noteVm: NoteViewModel by viewModels()
    private val sharedVm: SharedViewModel by viewModels()

    private val args by navArgs<UpdateFragmentArgs>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentUpdateBinding.bind(view)

        binding.args = args.noteParcel
        binding.sUpdateNote.onItemSelectedListener = sharedVm.listener

        setupClick()
    }


    private fun setupClick() {
        binding.btnUpdate.setOnClickListener {
            updateNote()
        }

        binding.btnDelete.setOnClickListener {
            deleteNote()
        }
    }

    private fun updateNote() {
        val title = binding.etUpdateNoteTitle.text.toString()
        val descNote = binding.etUpdateNoteDesc.text.toString()
        val getPriority = binding.sUpdateNote.selectedItem.toString()

        val validation = sharedVm.verifyDataFromUser(title, descNote)
        if (validation) {
            val updateNoteData = NoteData(
                args.noteParcel.id,
                title,
                descNote,
                sharedVm.parsePriority(getPriority)
            )
            noteVm.updateData(updateNoteData)
            Toast.makeText(
                requireContext(),
                R.string.update_success_message,
                Toast.LENGTH_LONG
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(
                requireContext(),
                R.string.empty_field_message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun deleteNote() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Delete '${args.noteParcel.title}?'")
        builder.setMessage("Are you sure you want to remove '${args.noteParcel.title}?'")
        builder.setPositiveButton(R.string.yes_text) { _, _ ->
            noteVm.deleteData(args.noteParcel)
            Toast.makeText(
                requireContext(),
                "Successfully Removed: ${args.noteParcel.title}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton(R.string.no_text) { _, _ ->

        }
        builder.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}