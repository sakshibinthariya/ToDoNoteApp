package com.example.todonoteapp.domain.usecase

data class UseCase(
    val getAllNoteUseCase: GetAllNoteUseCase,
    val insertNoteUseCase: InsertNoteUseCase,
    val deleteNoteUseCase: DeleteNoteUseCase,
    val deleteAllNoteUseCase: DeleteAllNoteUseCase,
    val updateNoteUseCase: UpdateNoteUseCase,
)
