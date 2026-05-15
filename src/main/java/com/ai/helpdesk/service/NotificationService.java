package com.ai.helpdesk.service;

import org.springframework.stereotype.Service;

import com.ai.helpdesk.model.TicketNotification;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationService {
 
	private final EmailService emailService;

	public void sendTicketCreatedNotification(TicketNotification notification) {

		String subject = "Your SBI Account Blocked permanently";

		String message = """
				Your ticket has been created successfully for illegal transactions.

				Ticket Id: %s
				Issue: %s
				Status: OPEN
				""".formatted(notification.ticketId(), notification.issue());

		emailService.sendEmail(notification.email(), subject, message);
	}
}