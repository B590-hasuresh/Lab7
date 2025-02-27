package com.example.lab7

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.databinding.ListItemTicketBinding
import com.example.lab7.databinding.ListItemTicketManagerBinding

class TicketHolder(
    val binding: ListItemTicketBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ticket: Ticket) {
        binding.ticketTitle.text = ticket.title
        binding.ticketDate.text = ticket.date.toString()
        binding.root.setOnClickListener {
            Toast.makeText(binding.root.context,
                "${ticket.title} clicked!",
                Toast.LENGTH_SHORT)
                .show()
        }
    }
}

class TicketListAdapter(
    private val tickets: List<Ticket>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return if (tickets[position].requiresManager) 1 else 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == 1) {
            val binding = ListItemTicketManagerBinding.inflate(inflater, parent, false)
            SeriousTicketHolder(binding)
        } else {
            val binding = ListItemTicketBinding.inflate(inflater, parent, false)
            TicketHolder(binding)
        }
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val ticket = tickets[position]
        when (holder) {
            is TicketHolder -> holder.bind(ticket)
            is SeriousTicketHolder -> holder.bind(ticket)
        }
    }
}

class SeriousTicketHolder(val binding: ListItemTicketManagerBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ticket: Ticket) {
        binding.ticketTitle.text = ticket.title
        binding.ticketDate.text = ticket.date.toString()

        binding.contactManagerButton.setOnClickListener {
            Toast.makeText(binding.root.context, "Contacting Manager for ${ticket.title}", Toast.LENGTH_SHORT).show()
        }
    }
}