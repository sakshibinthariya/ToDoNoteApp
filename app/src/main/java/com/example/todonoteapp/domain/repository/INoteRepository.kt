package com.example.todonoteapp.domain.repository

import com.example.todonoteapp.data.model.NoteData
import kotlinx.coroutines.flow.Flow

interface INoteRepository {
    fun getAllNote(): Flow<List<NoteData>>
    suspend fun insertNote(noteData: NoteData)
    suspend fun updateNote(noteData: NoteData)
    suspend fun deleteNote(noteData: NoteData)
    suspend fun deleteAllNote()
}