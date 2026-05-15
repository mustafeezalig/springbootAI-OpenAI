package com.ai.helpdesk.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ai.helpdesk.entity.HelpDeskTicket;
import com.ai.helpdesk.model.TicketNotification;
import com.ai.helpdesk.model.TicketRequest;
import com.ai.helpdesk.repository.HelpDeskTicketRepository;

@Service
public class HelpDeskTicketService {
	
	@Autowired
	private HelpDeskTicketRepository helpDeskTicketRepository;
	@Autowired
	private  NotificationService notificationService;
	public HelpDeskTicket createTicket(TicketRequest ticketInput, String username) {
		HelpDeskTicket ticket = HelpDeskTicket.builder().issue(ticketInput.issue()).username(username).status("OPEN")
				.createdAt(LocalDateTime.now()).eta(LocalDateTime.now().plusDays(7)).build();
		HelpDeskTicket saved=helpDeskTicketRepository.save(ticket);
		notificationService.sendTicketCreatedNotification(
                new TicketNotification(
                        saved.getId(),
                        saved.getUsername(),
                        saved.getIssue(),
                        "moinali2575@gmail.com",
                        "+919999999999"
                )
        );
		return saved;
	}
;
	public List<HelpDeskTicket> getTicketsByUsername(String username) {
		return helpDeskTicketRepository.findByUsername(username);
	}

}
