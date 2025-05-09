package com.example.finalprojectirmameza

import java.sql.Timestamp

data class DiaryEntry(
    val id: Int,
    val title: String,
    val content: String,
    val timestamp: Long = System.currentTimeMillis()
)
