package com.example.todonoteapp.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.todonoteapp.data.model.NoteData
import com.example.todonoteapp.data.source.local.dao.NoteDao
import com.example.todonoteapp.data.source.local.db.NoteConverter

@Database(entities = [NoteData::class], version = 1, exportSchema = false)
@TypeConverters(NoteConverter::class)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao

}