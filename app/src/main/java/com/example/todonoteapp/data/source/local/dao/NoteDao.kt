package com.example.todonoteapp.data.source.local.dao

import androidx.room.*
import com.example.todonoteapp.data.model.NoteData
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {

    @Query("SELECT * FROM note_table ORDER BY id ASC")
    fun getAllData(): Flow<List<NoteData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertData(noteData: NoteData)

    @Update
    suspend fun updateData(noteData: NoteData)

    @Delete
    suspend fun deleteData(noteData: NoteData)

    @Query("DELETE FROM note_table")
    suspend fun deleteAll()

}