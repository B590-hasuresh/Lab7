package com.example.lab7.database

import androidx.room.Dao
import androidx.room.Query
import com.example.lab7.Ticket
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface TicketDao {

    @Query("SELECT * FROM ticket")
    fun getTickets(): Flow<List<Ticket>>

    @Query("SELECT * FROM ticket WHERE id=:id")
    fun getTicket(id: UUID): Flow<Ticket>
}