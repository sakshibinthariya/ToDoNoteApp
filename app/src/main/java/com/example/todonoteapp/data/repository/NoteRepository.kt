package com.example.todonoteapp.data.repository

import com.example.todonoteapp.data.model.NoteData
import com.example.todonoteapp.data.source.local.dao.NoteDao
import com.example.todonoteapp.domain.repository.INoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepository @Inject constructor(private val noteDao: NoteDao) : INoteRepository {

    override fun getAllNote(): Flow<List<NoteData>> = noteDao.getAllData()
    override suspend fun insertNote(noteData: NoteData) = noteDao.insertData(noteData)
    override suspend fun updateNote(noteData: NoteData) = noteDao.updateData(noteData)
    override suspend fun deleteNote(noteData: NoteData) = noteDao.deleteData(noteData)
    override suspend fun deleteAllNote() = noteDao.deleteAll()

}