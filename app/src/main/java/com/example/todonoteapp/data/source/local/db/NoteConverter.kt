package com.example.todonoteapp.data.source.local.db

import androidx.room.TypeConverter
import com.example.todonoteapp.data.model.NotePriority

class NoteConverter {
    @TypeConverter
    fun fromPriority(notePriority: NotePriority): String {
        return notePriority.name
    }
    @TypeConverter
    fun toPriority(string: String): NotePriority {
        return NotePriority.valueOf(string)
    }

}