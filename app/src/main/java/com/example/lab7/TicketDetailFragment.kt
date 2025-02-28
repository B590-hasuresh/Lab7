package com.example.lab7

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.lab7.databinding.FragmentTicketDetailBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

private const val TAG = "TicketDetailFragment"

class TicketDetailFragment : Fragment(R.layout.fragment_ticket_detail) {
    private val args: TicketDetailFragmentArgs by navArgs()
    private val ticketDetailViewModel: TicketDetailViewModel by viewModels {
        TicketDetailViewModelFactory(args.ticketId)
    }
    private var _binding: FragmentTicketDetailBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access the view because it is null."
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            ticketTitle.doOnTextChanged { text, _, _, _ ->
                ticketDetailViewModel.updateTicket { oldTicket ->
                    oldTicket.copy(title = text.toString())
                }
            }

            ticketSolved.setOnCheckedChangeListener { _, isChecked ->
                ticketDetailViewModel.updateTicket { oldTicket ->
                    oldTicket.copy(isSolved = isChecked)
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                ticketDetailViewModel.ticket.collect { ticket ->
                    ticket?.let { updateUi(it) }
                }
            }
        }

        // Listen for date selection result
        setFragmentResultListener(
            DatePickerFragment.REQUEST_KEY_DATE
        ) { _, bundle ->
            val newDate = bundle.getSerializable(DatePickerFragment.BUNDLE_KEY_DATE) as Date
            ticketDetailViewModel.updateTicket { oldTicket ->
                oldTicket.copy(date = newDate.time)
            }
        }

        // Listen for time selection result
        setFragmentResultListener(
            TimePickerFragment.REQUEST_KEY_TIME
        ) { _, bundle ->
            val selectedTime = bundle.getString(TimePickerFragment.BUNDLE_KEY_TIME)
            binding.ticketTime.text = selectedTime // Update UI
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateUi(ticket: Ticket) {
        val dateFormat = SimpleDateFormat("EEEE, dd MMM yyyy", Locale.getDefault()) // Example: Wednesday, 26 Feb 2025
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault()) // Example: 14:30

        val calendar = Calendar.getInstance().apply { time = Date(ticket.date) }
        val formattedDate = dateFormat.format(calendar.time)
        val formattedTime = timeFormat.format(calendar.time)

        binding.apply {
            if (ticketTitle.text.toString() != ticket.title) {
                ticketTitle.setText(ticket.title)
            }

            ticketDate.text = formattedDate
            ticketTime.text = formattedTime

            ticketDate.setOnClickListener {
                val currentDate = Date(ticket.date)
                findNavController().navigate(TicketDetailFragmentDirections.selectDate(currentDate))
            }

            ticketTime.setOnClickListener {
                findNavController().navigate(TicketDetailFragmentDirections.selectTime())
            }

            ticketSolved.isChecked = ticket.isSolved
        }
    }
}