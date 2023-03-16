package com.example.todonoteapp.domain.usecase

import com.example.todonoteapp.data.model.NoteData
import com.example.todonoteapp.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNoteUseCase @Inject constructor(private val noteRepository: INoteRepository) {
    operator fun invoke(): Flow<List<NoteData>> = noteRepository.getAllNote()
}