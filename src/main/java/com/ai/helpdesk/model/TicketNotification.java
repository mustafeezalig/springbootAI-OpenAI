package com.ai.helpdesk.model;

public record TicketNotification(Long ticketId, String username, String issue, String email, String mobile) {
}