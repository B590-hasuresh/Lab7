package com.example.lab7

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Ticket(
    @PrimaryKey val id: String = java.util.UUID.randomUUID().toString(),
    val title: String,
    val date: Long,
    val isSolved: Boolean
)