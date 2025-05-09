package com.example.finalprojectirmameza

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.room.Room

class DiaryViewModel(application: Application): AndroidViewModel(application) {
    private val noteDao: NoteDao = Room.databaseBuilder(application, NoteDb::class.java, "note_database")
        .build().noteDao()

    private val _diaryEntries = mutableStateListOf<Note>()
    val diaryEntries: SnapshotStateList<Note> get() = _diaryEntries

    init {
        viewModelScope.launch {
            _diaryEntries.addAll(noteDao.getAllNotes())
        }
    }
    suspend fun getNoteById(id: Long): Note? {
        return noteDao.getNoteById(id)
    }

    fun addEntry(content: String) {
        val note = Note(title = content, content = content)
        viewModelScope.launch {
            val insertedId = noteDao.insert(note)
            _diaryEntries.add(note.copy(id = insertedId))
        }
    }
    fun updateNote(updatedNote: Note) {
        viewModelScope.launch {
            noteDao.update(updatedNote)
            val index = _diaryEntries.indexOfFirst { it.id ==updatedNote.id}
            if (index != -1) {
                _diaryEntries[index] = updatedNote
            }
        }
    }
    fun deleteNote(note: Note) {
        viewModelScope.launch {
            noteDao.delete(note)
            _diaryEntries.remove(note)
        }
    }
}
