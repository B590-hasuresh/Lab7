package com.example.lab7

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date
import java.util.UUID

@Entity
data class Ticket(
    @PrimaryKey val id: UUID = java.util.UUID.randomUUID(),
    val title: String,
    val date: Long,
    val isSolved: Boolean
)