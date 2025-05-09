package com.example.finalprojectirmameza

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface NoteDao {
    @Insert
    suspend fun insert(note: Note): Long

    @Query("SELECT * FROM notes")
    suspend fun getAllNotes(): List<Note>

    @Query ("SELECT * FROM notes WHERE id = :id LIMIT 1")
    suspend fun getNoteById(id: Long): Note?

    @Update
    suspend fun update(note: Note)

    @Delete
    suspend fun delete(note: Note)

}