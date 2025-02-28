package com.example.lab7

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lab7.databinding.FragmentTicketListBinding
import kotlinx.coroutines.launch

private const val TAG = "TicketListFragment"

class TicketListFragment : Fragment() {

    private var _binding: FragmentTicketListBinding? = null
    private val binding
        get() = checkNotNull(_binding) {
            "Cannot access binding because it is null."
        }

    private val ticketListViewModel: TicketListViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTicketListBinding.inflate(inflater, container, false)
        binding.ticketRecyclerView.layoutManager = LinearLayoutManager(context)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.toolbar.inflateMenu(R.menu.fragment_ticket_list)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                ticketListViewModel.tickets.collect { tickets ->
                    if (tickets.isEmpty()) {
                        showEmptyView()
                    } else {
                        showRecyclerView(tickets)
                    }
                }
            }
        }

        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.new_ticket -> {
                    findNavController().navigate(TicketListFragmentDirections.showTicketDetail(null))
                    true
                }
                else -> false
            }
        }

        binding.addTicketButton.setOnClickListener {
            findNavController().navigate(TicketListFragmentDirections.showTicketDetail(null))
        }
    }

    private fun showEmptyView() {
        binding.ticketRecyclerView.visibility = View.GONE
        binding.emptyView.visibility = View.VISIBLE
    }

    private fun showRecyclerView(tickets: List<Ticket>) {
        binding.ticketRecyclerView.visibility = View.VISIBLE
        binding.emptyView.visibility = View.GONE
        binding.ticketRecyclerView.adapter = TicketListAdapter(tickets) { ticketId ->
            findNavController().navigate(TicketListFragmentDirections.showTicketDetail(ticketId))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}