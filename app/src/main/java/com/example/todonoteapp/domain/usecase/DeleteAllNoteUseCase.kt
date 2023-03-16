package com.example.todonoteapp.domain.usecase

import com.example.todonoteapp.domain.repository.INoteRepository
import javax.inject.Inject

class DeleteAllNoteUseCase @Inject constructor(private val noteRepository: INoteRepository) {
    suspend operator fun invoke() = noteRepository.deleteAllNote()
}