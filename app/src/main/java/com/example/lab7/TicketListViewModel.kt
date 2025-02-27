package com.example.lab7

import androidx.lifecycle.ViewModel
import java.util.Date
import java.util.UUID

class TicketListViewModel : ViewModel() {

    val tickets = mutableListOf<Ticket>()

    init {
        for (i in 0 until 100) {
            val ticket = Ticket(
                id = UUID.randomUUID(),
                title = "ticket #$i",
                date = Date(),
                isSolved = i % 2 == 0,
                requiresManager = i % 3 == 0
            )
            tickets += ticket
        }
    }
}