package com.example.lab7

import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.lab7.databinding.ListItemTicketBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


class TicketHolder(
    private val binding: ListItemTicketBinding
) : RecyclerView.ViewHolder(binding.root) {

    private val dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy, HH:mm", Locale.getDefault())

    fun bind(ticket: Ticket) {
        binding.ticketTitle.text = ticket.title
        binding.ticketDate.text = dateFormat.format(Date(ticket.date)) .toString()

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${ticket.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.ticketSolved.visibility = if (ticket.isSolved) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

class TicketListAdapter(
    private val tickets: List<Ticket>
) : RecyclerView.Adapter<TicketHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TicketHolder {
        val inflator = LayoutInflater.from(parent.context)
        val binding = ListItemTicketBinding.inflate(inflator, parent, false)
        return TicketHolder(binding)
    }

    override fun getItemCount(): Int {
        return tickets.size
    }

    override fun onBindViewHolder(holder: TicketHolder, position: Int) {
        val ticket = tickets[position]
        holder.bind(ticket)

    }
}