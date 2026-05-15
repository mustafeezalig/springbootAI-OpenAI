package com.ai.helpdesk.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ai.helpdesk.entity.HelpDeskTicket;

import java.util.List;

public interface HelpDeskTicketRepository extends JpaRepository<HelpDeskTicket, Long> {

    List<HelpDeskTicket> findByUsername(String username);
}
