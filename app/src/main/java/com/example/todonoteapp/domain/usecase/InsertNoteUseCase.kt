package com.example.todonoteapp.domain.usecase

import com.example.todonoteapp.data.model.NoteData
import com.example.todonoteapp.domain.repository.INoteRepository
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(private val noteRepository: INoteRepository) {
    suspend operator fun invoke(noteData: NoteData) = noteRepository.insertNote(noteData)
}