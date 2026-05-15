package com.ai.helpdesk.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.helpdesk.entity.HelpDeskTicket;
import com.ai.helpdesk.model.TicketRequest;
import com.ai.helpdesk.repository.HelpDeskTicketRepository;

@Service
public class HelpDeskTicketService {
	
	@Autowired
	private HelpDeskTicketRepository helpDeskTicketRepository;

	public HelpDeskTicket createTicket(TicketRequest ticketInput, String username) {
		HelpDeskTicket ticket = HelpDeskTicket.builder().issue(ticketInput.issue()).username(username).status("OPEN")
				.createdAt(LocalDateTime.now()).eta(LocalDateTime.now().plusDays(7)).build();
		return helpDeskTicketRepository.save(ticket);
	}

	public List<HelpDeskTicket> getTicketsByUsername(String username) {
		return helpDeskTicketRepository.findByUsername(username);
	}

}
